package com.anomalycon.clues;

import android.content.Context;

import com.anomalycon.murdermysterycontest.AllClueActivity;
import com.anomalycon.murdermysterycontest.MainActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

//Dagger Stuff: If more classes/activities need access to Clues, add them here.
@Module(
        injects = { MainActivity.class, AllClueActivity.class}
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
    public Context provideApplicationContext() {
        return this.context;
    }

    @Provides
    @Singleton
    public ClueInterface provideClueInterface(Context context) {
        //return new ClueManager(context);
        return new RestClueManager(context);
    }
}
