package iex;

import iex.model.Client;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.Matchers.equalTo;


@RunWith(SerenityRunner.class)
public class WhenMaintainingClientRegistry {

    Client client;
    String id;

    @Before
    public void prepareTestData() {
        RestAssured.baseURI = "http://localhost:8080/api";

        //Given an existing client

         client = new Client.Builder()
                .setEmail("pepe@pig.com")
                .setFirstName("pepe")
                .setLastName("Pig")
                .createClient();

        given().contentType("application/json")
                .and().body(client)
                .when().post("/client")
                .then().statusCode(200);

         id = SerenityRest.lastResponse().jsonPath().getString("id");
    }


    @Test
    public void shouldBeAbleToUpdateExistingClient(){

        Client updatedClient = new Client.Builder()
                .setEmail("pepe@pig.com")
                .setFirstName("Pepito")
                .setLastName("Pig")
                .createClient();

        given().contentType("application/json")
                .and().body(updatedClient)
                .pathParam("id", id)
                .when().put("/client/{id}")
                .then().statusCode(200);

        //check value was updated
        given().pathParam("id", id)
                .when().get("/client/{id}")
                .then().body("firstName", equalTo("Pepito"));

    }

    @Test
    public void shoudRemoveClientFromRegistry(){

        //When I delete a client

        SerenityRest
                .given().pathParam("id", id)
                .when().delete("/client/{id}")
                .then().statusCode(204);

        // Then the client should no longer be found

        SerenityRest.given()
                .pathParam("id", id)
                .when().get("/client/{id}")
                .then().statusCode(404);
    }
}
