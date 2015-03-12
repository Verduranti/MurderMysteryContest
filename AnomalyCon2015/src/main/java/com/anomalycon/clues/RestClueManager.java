package com.anomalycon.clues;

import android.content.Context;
import android.content.res.Resources;

import com.anomalycon.murdermysterycontest.R;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Singleton Wrapper class designed to guard access to the user's discovered com.anomalycon.clues.
 */
public class RestClueManager implements ClueInterface {

    //private static ClueManager myInstance = null;

    private Map<Key, Clue> foundClueMap;

    //private static Context myContext;

    private RestClueManager() {
        //not used
    }

    public RestClueManager(Context context) {
        foundClueMap = new HashMap<>();
    }

    // this helper method can be used to make JSON parsing a one-line operation
    public static <T> T parseJson(String json, Class<T> clazz) {
        try {
            return defaultMapper().treeToValue(defaultMapper().readTree(json), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
    private ClueCount getClue() {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpContext localContext = new BasicHttpContext(); // Daggerify these?
        final HttpGet get = new HttpGet("http://anomalycon-server.heroku.com/clue");
        try {
            final HttpResponse response = httpClient.execute(get, localContext);

            final HttpEntity entity = response.getEntity();

            if (entity != null) {
                final String retSrc = EntityUtils.toString(entity);

                return parseJson(retSrc, ClueCount.class);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
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

                return parseJson(retSrc, Clue.class);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Method that performs RESTful webservice invocations
     *
     * @param password the password to check
     * @return the clue for the password or null
     */
    private Clue postGuess(String password) {
        final HttpClient httpClient = new DefaultHttpClient();
        final HttpContext localContext = new BasicHttpContext(); // Daggerify these?
        final HttpPost post = new HttpPost("http://anomalycon-server.heroku.com/clue");
        try {
            final HttpResponse response = httpClient.execute(post, localContext);

            final HttpEntity entity = response.getEntity();

            if (entity != null) {
                final String retSrc = EntityUtils.toString(entity);

                return parseJson(retSrc, Clue.class);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<String> getClueNames() {
        List<Key> list = Arrays.asList(foundClueMap.keySet().toArray(new Key[foundClueMap.size()]));

        List<String> nameList = new ArrayList<>();
        for(Key key : list)
        {
            nameList.add(key.getKey());
        }
        return nameList;
    }

    @Override
    public Clue getClue(Key clueName) {
        if(saveClue(clueName).hasClue())
        {
            return foundClueMap.get(clueName);
        }
        return null;
    }

    @Override
    public SaveClueStatus saveClue(Key clueName) {
        boolean returnStatus = false;
        if(foundClueMap.containsKey(clueName)) {
            return SaveClueStatus.DUPLICATE;
        }
        try {
            Clue clue = postClue(clueName.getKey());
            foundClueMap.put(new Key(clue.getName()), clue);
            return SaveClueStatus.SAVED;
        }
        catch (Exception e) {
            System.out.println(e);
            return SaveClueStatus.INVALID;
        }
    }

    @Override
    public int countFoundClues() {
        return foundClueMap.size();
    }

    @Override
    public int countAllClues() {
        try {
            return getClue().getCount();
        }
        catch (Exception e) {
            return Integer.MAX_VALUE;
        }
    }

}
