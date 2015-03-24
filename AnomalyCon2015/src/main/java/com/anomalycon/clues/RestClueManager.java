package com.anomalycon.clues;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton Wrapper class designed to guard access to the user's discovered com.anomalycon.clues.
 */
public class RestClueManager implements ClueInterface {

    //private static ClueManager myInstance = null;
    private static final String CLUE_FILE = "clueArchive.txt";

    private static final TypeReference<Map<String,Clue>> CLUE_MAP_TYPE = new TypeReference<Map<String,Clue>>() { };
    private static final TypeReference<ClueCount> CLUE_COUNT_TYPE = new TypeReference<ClueCount>() { };
    private static final TypeReference<Clue> CLUE_TYPE = new TypeReference<Clue>() { };

    private final Map<String, Clue> foundClueMap;
    private final File clueFile;

    //private static Context myContext;

    private Map<String, Clue> loadSavedClues() {
        if(clueFile.canRead()) {
            try {
                final String contents = Files.toString(clueFile, Charset.defaultCharset());

                return parseJson(contents, CLUE_MAP_TYPE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>();
    }

    private void saveClues() {
        final String string = toJson(foundClueMap).toString();

        System.out.println(string);

        try {
            Files.write(string, clueFile, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public RestClueManager(Context context) {
        clueFile = new File(context.getFilesDir(), CLUE_FILE);
        foundClueMap = loadSavedClues();
    }

    // this helper method can be used to make JSON parsing a one-line operation
    public static <T> T parseJson(String json, TypeReference<T> clazz) {
        try {
            return defaultMapper().readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // this helper method can be used to make JSON parsing a one-line operation
    public static <T> JsonNode toJson(T object) {
        return defaultMapper().valueToTree(object);
    }

    // re-use a single ObjectMapper so we're not creating multiple object mappers
    private static ObjectMapper sObjectMapper = new ObjectMapper();
    public static ObjectMapper defaultMapper() {
        return sObjectMapper;
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @return the clue count
     */
    private ClueCount getClueCount() {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpContext localContext = new BasicHttpContext(); // Daggerify these?
        final HttpGet get = new HttpGet("http://anomalycon-server.herokuapp.com/clue");
        try {
            final HttpResponse response = httpClient.execute(get, localContext);

            final HttpEntity entity = response.getEntity();

            if (entity != null) {
                final String retSrc = EntityUtils.toString(entity);

                return parseJson(retSrc, CLUE_COUNT_TYPE);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param password the password to check
     * @return the clue for the password or null
     */
    private Clue postClue(String password) {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpContext localContext = new BasicHttpContext(); // Daggerify these?

        try {
            JSONObject json = new JSONObject();
            json.put("password", password);
            StringEntity se = new StringEntity(json.toString());
            se.setContentType("application/json");

            final HttpPost post = new HttpPost("http://anomalycon-server.herokuapp.com/clue");
            post.setEntity(se);

            final HttpResponse response = httpClient.execute(post, localContext);

            if(response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException(response.getStatusLine().toString());
            }

            final HttpEntity entity = response.getEntity();

            if (entity != null) {
                final String retSrc = EntityUtils.toString(entity);

                return parseJson(retSrc, CLUE_TYPE);
            }
            return null;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Drawable getFormulaImage(String password) {
        try {
            final URL url = new URL("http://anomalycon-server.herokuapp.com/img/"+password);
            return Drawable.createFromStream(url.openStream(), "src");
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param guess the guess to check
     * @return the clue for the password or null
     */
    private GuessStatus postGuess(Guess guess) {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpContext localContext = new BasicHttpContext(); // Daggerify these?
        try {
            final JsonNode json = toJson(guess);
            final StringEntity se = new StringEntity(json.toString());
            se.setContentType("application/json");

            final HttpPost post = new HttpPost("http://anomalycon-server.herokuapp.com/guess");
            post.setEntity(se);

            final HttpResponse response = httpClient.execute(post, localContext);

            if(response.getStatusLine().getStatusCode() == 204) { // 201 is bad here, it means the GET method was called
                return GuessStatus.SUBMITTED;
            }
            System.out.println("Guess rejected: "+response.getStatusLine().toString());
            return GuessStatus.REJECTED;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> getClueNames() {
        return Lists.newArrayList(foundClueMap.keySet());
    }

    @Override
    public Clue getClue(Key clueName) {
        return foundClueMap.get(clueName.getKey());
    }

    private void addClueToFoundMap(Clue clue) {
        foundClueMap.put(clue.getName(), clue);
        saveClues();
    }

    @Override
    public SaveClueStatus saveClue(Key clueName) {
        if(foundClueMap.containsKey(clueName.getKey())) {
            saveClues();
            return SaveClueStatus.DUPLICATE;
        }
        try {
            Clue clue = postClue(clueName.getKey());
            addClueToFoundMap(clue);
            return SaveClueStatus.SAVED;
        }
        catch (Exception e) {
            e.printStackTrace();
            return SaveClueStatus.INVALID;
        }
    }

    @Override
    public SaveClueStatus saveClue(Clue clue) {
        if(!foundClueMap.containsKey(clue.getName())) {
            return SaveClueStatus.INVALID;
        }
        addClueToFoundMap(clue);

        return SaveClueStatus.SAVED;
    }

    @Override
    public Drawable getImageForClue(Key clueName) {
        return getFormulaImage(clueName.getKey());
    }

    @Override
    public int countFoundClues() {
        return foundClueMap.size();
    }

    @Override
    public int countAllClues() {
        try {
            return getClueCount().getCount();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Integer.MAX_VALUE;
        }
    }

    @Override
    public GuessStatus makeGuess(Guess guess) {
        guess.setClues(foundClueMap.values());
        try {
            return postGuess(guess);
        } catch (Exception e) {
            return GuessStatus.ERROR;
        }
    }

}
