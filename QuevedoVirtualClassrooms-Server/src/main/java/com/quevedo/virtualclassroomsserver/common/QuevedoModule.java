package com.quevedo.virtualclassroomsserver.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.enterprise.inject.Produces;

public class QuevedoModule {

    @Produces
    public Gson producesGson(){
        return new GsonBuilder().setPrettyPrinting().create();
    }
}
