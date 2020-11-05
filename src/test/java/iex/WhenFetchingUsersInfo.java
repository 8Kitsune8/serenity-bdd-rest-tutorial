package iex;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.Ensure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SerenityRunner.class)
public class WhenFetchingUsersInfo {

    @Before
    public void setBaseUri(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void shouldReturnUserNameAndMail(){
        given().pathParam("symbol", "users")
        .when().get("/{symbol}/1")
                .then().statusCode(200);

            Ensure.that("user name is returned", response -> response.body("username", equalTo("Bret")));
            Ensure.that("email is returned", response -> response.body("email", equalTo("Sincere@april.biz")));
    }

    @Test
    public void shouldRetrieveEmailForGivenUser(){
        given().queryParam("username", "Bret")
        .when().get("/users");

        Ensure.that(" User email is Sincere@april.biz", response ->  response.body("email[0]", equalTo("Sincere@april.biz")))
                .andThat( " User id is not zero" , response -> response.body("id[0]", greaterThan(0)));
    }

}
