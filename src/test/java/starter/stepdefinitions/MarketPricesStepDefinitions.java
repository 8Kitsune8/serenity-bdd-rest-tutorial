package starter.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.java.Before;
import io.restassured.RestAssured;
import net.serenitybdd.rest.SerenityRest;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class MarketPricesStepDefinitions {

    @Before
    public void setupBaseUrl(){
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Given("^(?:.*) is an active trader on IEX")
    public void registeredUserIsAnActiveTraderOnIEX() {
    }

    @And("^(.*) is currently trading at (.*)")
    public void shareIsCurrentlyTradingAt(String symbol, String expectedPrice) {
        RestAssured.given().body(expectedPrice)
                .contentType("application/json")
                .when().post("/stock/{symbol}/price", symbol)
                .then().statusCode(200);
    }

    @When("^(.*) requests the latest price for (.*)")
    public void requestsTheLatestPriceFor(String actor, String symbol) {
        SerenityRest.when().get("/stock/{symbol}/price", symbol)
            .then().statusCode(200);
    }

    @Then("^s?he should see a price of (.*)")
    public void shouldSeeAPriceOf(double expectedPrice) {
        Double price = lastResponse().as(Double.class);

        assertThat(price).isEqualTo(expectedPrice);
    }

    @Given("the market is closed")
    public void theMarketIsClosed() {
       // RestAssured.get("/market/close");
    }

    @And("^(.*) closed at (.*)")
    public void closedAt(String symbol, String closingPrice) {
        RestAssured.given().body(closingPrice)
                .contentType("application/json")
                .when().post("/stock/{symbol}/price", symbol)
                .then().statusCode(200);
    }
}
