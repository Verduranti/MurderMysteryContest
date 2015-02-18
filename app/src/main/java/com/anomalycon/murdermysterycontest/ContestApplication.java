package com.anomalycon.murdermysterycontest;

import android.app.Application;

import com.anomalycon.clues.ClueModule;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class ContestApplication extends Application {
    private ObjectGraph objectGraph;

    private static ContestApplication sInstance;

    public static ContestApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sInstance.initializeInstance();

        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
    }

    protected void initializeInstance() {
        // do all your initialization here
        //Dagger things
        Object[] modules = getModules().toArray();
        objectGraph = ObjectGraph.create(modules);
    }

    protected List<Object> getModules() {
        return Arrays.<Object>asList(new ClueModule(this.getApplicationContext()));
    }

    public ObjectGraph getObjectGraph() {
        return this.objectGraph;
    }
}
