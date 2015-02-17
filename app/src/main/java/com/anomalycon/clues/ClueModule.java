package com.anomalycon.clues;

import android.app.Application;
import android.content.Context;

import com.anomalycon.murdermysterycontest.NewClueActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = NewClueActivity.class
)
public class ClueModule {
    //private Application application;
    private Context context;

    public ClueModule(Application app) {
        //this.application = crm;
        //this.context = this.application.getApplicationContext();
        this.context = app.getApplicationContext();
    }

    @Provides
    //@InjectAndroidApplicationContext
    public Context provideApplicationContext() {
        return this.context;
    }

    @Provides
    @Singleton
    public ClueInterface provideClueInterface(/*@InjectAndroidApplicationContext*/ Context context) {
        return new ClueManager(context);
    }
}
