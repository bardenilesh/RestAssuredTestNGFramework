package com.spotify.oauth2.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateAPlaylist {

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass(){
        String access_token ="BQCkZ9Ufq0BtbPiXF4fpcuVoJtrhjn-v_sbv2aTVAryzCah6oNunZ5BcnqNLxpfdJ7JmVGhfPr2qH0s959bvDcAnYt2_LcsKsyzse7hw5jbnKw5eU4yoqIdlh8hj7ndcDN_jBNMCCouLLv8maaj-WPuNn3Lrid0j_djrbu4Mxq-Z1JX3fTzC7fQkCnHaawQdrvXkmPGwRqzyEYyan7-0O7izXvQPyyd_pbWJLbac0Cm4xBfm";
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com").
                setBasePath("/v1").
                addHeader("Authorization", "Bearer "+ access_token).
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);

        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);

        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void ShouldBeAbleToCreateAPlaylist(){
        String payload ="    {\n" +
                "    \"name\": \"New Playlist 2\",\n" +
                "    \"description\": \"New playlist 2 description\",\n" +
                "    \"public\": false\n" +
                "    }";

        given(requestSpecification).
                body(payload).
        when().
                post("/users/31jpy2ogmwvid3ktbablcric5ojm/playlists").
        then().
                spec(responseSpecification).
                statusCode(201).
                body("name", equalTo("New Playlist 2"),
                        "public", equalTo(false));
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithoutName(){
        String payload ="    {\n" +
                "    \"name\": \"\",\n" +
                "    \"description\": \"New playlist 2 description\",\n" +
                "    \"public\": false\n" +
                "    }";


        given(requestSpecification).
                body(payload).
        when().
                post("/users/31jpy2ogmwvid3ktbablcric5ojm/playlists").
        then().
                spec(responseSpecification).
                statusCode(400).
                body("error.status", equalTo(400),
                        "error.message", equalTo("Missing required field: name"));
    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken(){
        String payload ="    {\n" +
                "    \"name\": \"New Playlist 2\",\n" +
                "    \"description\": \"New playlist 2 description\",\n" +
                "    \"public\": false\n" +
                "    }";


        given().
                baseUri("https://api.spotify.com").
                basePath("/v1").
                header("Authorization", "Bearer 1234").
                contentType(ContentType.JSON).
                log().all().
                body(payload).
        when().
                post("/users/31jpy2ogmwvid3ktbablcric5ojm/playlists").
        then().
                spec(responseSpecification).
                statusCode(401).
                body("error.status", equalTo(401),
                        "error.message", equalTo("Invalid access token"));
    }
}
