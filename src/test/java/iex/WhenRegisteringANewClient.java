package iex;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@RunWith(SerenityRunner.class)
public class WhenRegisteringANewClient {

    @Before
    public void setBaseURL() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Test
    public void eachNewClientShouldBeGivenAUniqueId() {

        String newClient = "{\n" +
                "  \"email\": \"pepe@pig.com\",\n" +
                "  \"firstName\": \"pepe\",\n" +
                "  \"lastName\": \"Pig\"\n" +
                "}";

        given().contentType("application/json")
                .and().body(newClient)
                .when().post("/client")
                .then().statusCode(200)
                .and().body("id", not(equalTo(0)));


        String id = SerenityRest.lastResponse().jsonPath().getString("id");

        given().pathParam("id", id)
                .when().get("/client/{id}")
                .then().statusCode(200);

        Ensure.that("First name is pepe", response -> response.body("firstName", equalTo("pepe")));
        Ensure.that("Last name is Pig", response -> response.body("lastName", equalTo("Pig")));
        Ensure.that("Email is pepe@pig.com", response -> response.body("email", equalTo("pepe@pig.com")));
    }


}
