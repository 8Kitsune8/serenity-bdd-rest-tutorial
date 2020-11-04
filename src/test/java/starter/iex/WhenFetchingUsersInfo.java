package starter.iex;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.serenitybdd.rest.SerenityRest.when;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class WhenFetchingUsersInfo {


    @Test
    public void shouldReturnUserNameAndMail(){
        when().get("https://jsonplaceholder.typicode.com/users/1")
                .then().statusCode(200)
                .and().body("username", equalTo("Bret"))
                .and().body("email", equalTo("Sincere@april.biz"));

    }
}
