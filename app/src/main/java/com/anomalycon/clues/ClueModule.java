package com.anomalycon.clues;

import android.content.Context;

import com.anomalycon.murdermysterycontest.MainActivity;
import com.anomalycon.murdermysterycontest.NewClueActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = { MainActivity.class, NewClueActivity.class }
)
public class ClueModule {
    //private Application application;
    private Context context;

    public ClueModule(Context appContext) {
        //this.application = crm;
        //this.context = this.application.getApplicationContext();
        //this.context = app.getApplicationContext();
        this.context = appContext;
    }

    @Provides
    //@InjectAndroidApplicationContext
    public Context provideApplicationContext() {
        return this.context;
    }

    @Provides
    @Singleton
    public ClueInterface provideClueInterface(/*@InjectAndroidApplicationContext */Context context) {
        return new ClueManager(context);
    }
}
