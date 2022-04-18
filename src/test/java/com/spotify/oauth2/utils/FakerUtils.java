package com.spotify.oauth2.utils;

import com.github.javafaker.Faker;

public class FakerUtils {

    public static String generateName(){
        Faker faker = new Faker();
        String regex= "[A-za-z0-9, _-]{20}";
        return "Playlist" + faker.regexify(regex);
    }

    public static String generateDescription(){
        Faker faker = new Faker();
        String regex= "[A-za-z0-9 _@*+]{30}";
        return "Description" + faker.regexify(regex);
    }
}
