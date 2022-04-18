package com.spotify.oauth2.api;


import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Route.API;
import static com.spotify.oauth2.api.Route.TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {

    public static Response get(String path, String token){
        return given(getRequestSpec()).
                auth().oauth2(token).
//                header("Authorization" , "Bearer "+token).
        when().
                get(path).
        then().
                spec(getResponseSpec()).
                extract().
                response();
    }

    public static Response post( String path, String token, Object requestPlaylist){
        return given(getRequestSpec()).
                auth().oauth2(token).
//                header("Authorization" , "Bearer "+token).
                body(requestPlaylist).
        when().
                post(path).
        then().
                spec(getResponseSpec()).
                extract().
                response();
    }

    public static  Response postAccount(HashMap<String, String> formParams){
        return given(getAccountRequestSpec()).
                formParams(formParams).
        when().
                post(API+TOKEN).
        then().
                log().all().
                spec(getResponseSpec()).
                extract().
                response();
    }


    public static Response update(String path, String token, Object requestPlaylist){
        return given(getRequestSpec()).
                auth().oauth2(token).
//                header("Authorization" , "Bearer "+token).
                body(requestPlaylist).
        when().
                put(path).
        then().
                statusCode(200).
                extract().
                response();
    }

}
