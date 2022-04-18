package com.spotify.oauth2.tests;

import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlaylistCases {


    @Test
    public void ShouldBeAbleToCreateAPlaylist(){
        Playlist requestPlaylist = new Playlist();
        requestPlaylist.setName("New playlist pojo");
        requestPlaylist.setDescription("New playlist pojo description");
        requestPlaylist.set_public(false);
        Playlist responsePlaylist = given(getRequestSpec()).
                body(requestPlaylist).
        when().
                post("/users/31jpy2ogmwvid3ktbablcric5ojm/playlists").
        then().
                spec(getResponseSpec()).
                statusCode(201).
                extract().
                response().
                as(Playlist.class);

        assertThat(requestPlaylist.getName(), equalTo(responsePlaylist.getName()));
        assertThat(requestPlaylist.getDescription(), equalTo(responsePlaylist.getDescription()));
        assertThat(requestPlaylist.get_public(), equalTo(responsePlaylist.get_public()));
    }

    @Test
    public void ShouldBeAbleToGetAPlaylist(){
        Playlist responsePlaylist = given(getRequestSpec()).
                when().
                get("playlists/5VK3x3PHUpXkWjjyExe0wi").
                then().
                spec(getResponseSpec()).
                statusCode(200).
                extract().response().as(Playlist.class);

        assertThat(responsePlaylist.getName(), equalTo("Update playlist pojo again"));
        assertThat(responsePlaylist.getDescription(), equalTo("Update playlist pojo description again"));
        assertThat(responsePlaylist.get_public(), equalTo(false));

    }

    @Test
    public void ShouldBeAbleToUpdateAPlaylist(){
        Playlist requestPlaylist = new Playlist();
        requestPlaylist.setName("Update playlist pojo again");
        requestPlaylist.setDescription("Update playlist pojo description again");
        requestPlaylist.set_public(false);

        given(getRequestSpec()).
                body(requestPlaylist).
        when().
                put("/playlists/5VK3x3PHUpXkWjjyExe0wi").
        then().
                statusCode(200);

    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithoutName(){
        Playlist requestPlaylist = new Playlist();
        requestPlaylist.setName("");
        requestPlaylist.setDescription("New playlist pojo description");
        requestPlaylist.set_public(false);

        Error error= given(getRequestSpec()).
                body(requestPlaylist).
        when().
                post("/users/31jpy2ogmwvid3ktbablcric5ojm/playlists").
        then().
                spec(getResponseSpec()).
                statusCode(400).extract().as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(400));
        assertThat(error.getError().getMessage(), equalTo("Missing required field: name"));

    }

    @Test
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken(){
        Playlist requestPlaylist = new Playlist();
        requestPlaylist.setName("New playlist pojo");
        requestPlaylist.setDescription("New playlist pojo description");
        requestPlaylist.set_public(false);

        Error error= given().
                baseUri("https://api.spotify.com").
                basePath("/v1").
                header("Authorization", "Bearer 1234").
                contentType(ContentType.JSON).
                log().all().
                body(requestPlaylist).
        when().
                post("/users/31jpy2ogmwvid3ktbablcric5ojm/playlists").
        then().
                spec(getResponseSpec()).
                statusCode(401).extract().as(Error.class);

        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));

    }
}
