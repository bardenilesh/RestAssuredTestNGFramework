package com.spotify.oauth2.utils;

import java.util.Properties;

public class DataLoader {
    private final Properties properties;
    private static DataLoader configLoader;

    private DataLoader(){
        properties = PropertyUtils.propertyLoader("src/test/resources/data.properties");
    }

    public static DataLoader getInstance(){
        if(configLoader==null){
            configLoader = new DataLoader();
        }
        return configLoader;
    }

    public String getPlaylistId() {
        String prop = properties.getProperty("playlist_id");
        if (prop!=null) return prop;
        else throw new RuntimeException("Property playlist_id is not specified in data.properties file");
    }

    public String getUpdatePlaylistId() {
        String prop = properties.getProperty("update_playlist_id");
        if (prop!=null) return prop;
        else throw new RuntimeException("Property update_playlist_id is not specified in data.properties file");
    }

}
