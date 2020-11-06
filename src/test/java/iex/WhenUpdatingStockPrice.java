package iex;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(SerenityRunner.class)
public class WhenUpdatingStockPrice {

    @Before
    public void setBaseURL() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Test
    public void shouldUpdateStockPrice(){


        given().contentType("application/json")
                .and().body("573")
                .pathParam("stockid", "AAPL")
                .when().post("/stock/{stockid}/price")
                .then().statusCode(200);


        given().pathParam("stockid", "AAPL")
                .when().get("/stock/{stockid}/price")
                .then().body(equalTo("573.0"));
    }
}
