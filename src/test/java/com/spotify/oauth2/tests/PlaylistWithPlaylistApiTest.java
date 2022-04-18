package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.utils.FakerUtils.generateDescription;
import static com.spotify.oauth2.utils.FakerUtils.generateName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Test a playlist API")
@Feature("Playlist API")
public class PlaylistWithPlaylistApiTest extends BaseTest{

    @Link("https://example.org")
    @Link(name = "allure", type = "mylink")
    @Issue("1234")
    @TmsLink("1234567")
    @Description("Checking if user is able to create a new playlist")
    @Severity(SeverityLevel.CRITICAL)
    @Test(description = "Should be able to create a new playlist")
    @Story("Verify creation of a playilst")
    public void ShouldBeAbleToCreateAPlaylist(){
        Playlist requestPlaylist = playlistBuilder(generateName(),
                generateDescription(), false);
        Response response =  PlaylistApi.post(requestPlaylist);
        Playlist responsePlaylist = response.as(Playlist.class);
        assertStatusCode(response.statusCode(), StatusCode.CODE_201);
        assertPlaylistEqual(responsePlaylist, requestPlaylist);
    }


    @Description("Checking if user is able to fetch a playlist")
    @Test(description = "Should be able to get a playlist")
    public void ShouldBeAbleToGetAPlaylist(){
        Playlist requestPlaylist = playlistBuilder("New playlist pojo",
                "New playlist pojo description", false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getPlaylistId());
        Playlist responsePlaylist = response.as(Playlist.class);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
        assertPlaylistEqual(responsePlaylist, requestPlaylist);
    }

    @Description("Checking if user is able to update a playlist")
    @Test(description = "Should be able to update a playlist")
    public void ShouldBeAbleToUpdateAPlaylist(){
        Playlist requestPlaylist = playlistBuilder(generateName(),
                generateDescription(), false);
        Response response =  PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(), requestPlaylist);
        assertStatusCode(response.statusCode(), StatusCode.CODE_200);
    }

    @Story("Verify creation of a playilst")
    @Description("Checking if it is allowed to create a playlist wihout name")
    @Test(description = "Should not be able to create a playlist without name")
    public void ShouldNotBeAbleToCreateAPlaylistWithoutName(){
        Playlist requestPlaylist = playlistBuilder("",
                generateDescription(), false);
        Response response =  PlaylistApi.post(requestPlaylist);
        assertErrorEqual(response.as(Error.class), StatusCode.CODE_400);
   }

   @Story("Verify creation of a playilst")
   @Description("Checking if expired token is not allowed to create a playlist")
    @Test(description = "Should not be able to create a playlist with expired token")
    public void ShouldNotBeAbleToCreateAPlaylistWithExpiredToken(){
        Playlist requestPlaylist = playlistBuilder(generateName(),
                generateDescription(), false);
        Response response =  PlaylistApi.post("12345", requestPlaylist);
        Error error = response.as(Error.class);
        assertErrorEqual(error, StatusCode.CODE_401);
    }

    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public){
        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.set_public(_public);
        return playlist;
    }

    @Step
    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(requestPlaylist.getName(), equalTo(responsePlaylist.getName()));
        assertThat(requestPlaylist.getDescription(), equalTo(responsePlaylist.getDescription()));
        assertThat(requestPlaylist.get_public(), equalTo(responsePlaylist.get_public()));
    }

    @Step
    public void assertErrorEqual(Error error, StatusCode statusCode){
        assertThat(error.getError().getStatus(), equalTo(statusCode.code));
        assertThat(error.getError().getMessage(), equalTo(statusCode.msg));
    }

    @Step
    public void assertStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode, equalTo(statusCode.code));
    }


}
