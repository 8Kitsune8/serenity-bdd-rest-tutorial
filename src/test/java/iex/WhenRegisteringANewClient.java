package iex;

import iex.model.Client;
import iex.steps.PlatformAdminSteps;
import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.SerenityRest.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@RunWith(SerenityRunner.class)
public class WhenRegisteringANewClient {

    @Before
    public void setBaseURL() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Steps
    PlatformAdminSteps pat;

    @Test
    public void eachNewClientShouldBeGivenAUniqueId() {

        Client pepePig = new Client.Builder()
                .setEmail("pepe@pig.com")
                .setFirstName("pepe")
                .setLastName("Pig")
                .createClient();

        String id = pat.registerANewClient(pepePig);

       // Client createdClient = SerenityRest.lastResponse().as(Client.class);

        pat.searchesForAClientById(id);

        pat.findAClientMatching(pepePig);


        /*Client foundClient = SerenityRest.with().pathParam("id", id)
                .get("/client/{id}").as(Client.class);

        assertThat(createdClient).isEqualTo(foundClient);*/

    }


}
