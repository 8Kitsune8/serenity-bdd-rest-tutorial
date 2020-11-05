package iex;

import iex.model.Address;
import iex.model.Company;
import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.when;
import static org.assertj.core.api.Assertions.assertThat;
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

        String userEmail = SerenityRest.lastResponse().jsonPath().getString("email");
        assertThat(userEmail).isEqualTo("Sincere@april.biz");

    }

    @Test
    public void shouldRetrieveEmailForGivenUser(){
        given().queryParam("username", "Bret")
        .when().get("/users");

        Ensure.that(" Address email is Sincere@april.biz", response ->  response.body("email[0]", equalTo("Sincere@april.biz")))
                .andThat( " Address id is not zero" , response -> response.body("id[0]", greaterThan(0)));
    }

    @Test
    public void allUsersShouldHaveAZipCode(){
        when().get("/users")
                .then().statusCode(200);

        List<String> zipCode = SerenityRest.lastResponse().jsonPath().getList("address.zipcode");
        zipCode.forEach(
                zipcode -> assertThat(zipcode).isNotEmpty());
    }

    @Test
    public void allUsersShouldHaveACompanyWithClass(){
        when().get("/users")
                .then().statusCode(200);

        List<Company> companies = SerenityRest.lastResponse().jsonPath().getList("company" , Company.class);
        companies.forEach(
                company -> assertThat(company.getName()).isNotEmpty());
    }

}
