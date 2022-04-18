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

public class GetAPlaylist {

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
    public void ShouldBeAbleToGetAPlaylist(){

        given(requestSpecification).
        when().
                get("playlists/5VK3x3PHUpXkWjjyExe0wi").
        then().
                spec(responseSpecification).
                statusCode(200).
                body("name", equalTo("Updated Playlist Name"),
                        "description", equalTo("Updated playlist description"),
                        "public", equalTo(false));
    }


}
