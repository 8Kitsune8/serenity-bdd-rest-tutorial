package iex;

import iex.model.Client;
import iex.model.Post;
import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.SerenityRest.given;


@RunWith(SerenityRunner.class)
public class WhenRemovingAnExistingClient {

    @Before
    public void setBaseURL() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }


    @Test
    public void shoudRemovePostFromRegistry(){

        //Given an existing post

        Post newPost = new Post(101, "customTitle1","this is my post");

        given().contentType("application/json")
                .and().body(newPost)
                .when().post("/posts")
                .then().statusCode(201);

        //When I delete a client

        String id = SerenityRest.lastResponse().jsonPath().getString("id");

        SerenityRest
                .given().pathParam("id", id)
                .when().delete("/posts/{id}")
                .then().statusCode(200);

        // Then the client should no longer be found

        SerenityRest.given()
                .pathParam("id", id)
                .when().get("/posts/{id}")
                .then().statusCode(404);
    }
}
