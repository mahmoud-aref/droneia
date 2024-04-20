package com.elmenus.droneia.drones;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.drone.model.DroneRegistrationRequest;
import com.elmenus.droneia.factory.FeaturesMockingFactory;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DroneCreationFeatureSteps {

    private DroneRegistrationRequest droneRegistrationRequest;
    private BasicResponse responseBody;

    @Given("the user provides valid drone creation request")
    public void the_user_provides_valid_drone_creation_request() {
        this.droneRegistrationRequest = FeaturesMockingFactory.getMockDroneRegistrationRequest();
    }

    @When("the user sends a POST request to {string}")
    public void the_user_sends_a_post_request_to(String path) {
        responseBody = with()
                .body(droneRegistrationRequest)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", path)
                .then()
                .statusCode(200)
                .extract()
                .as(BasicResponse.class);
    }

    @And("the response should contain {string}")
    public void the_response_should_contain(String message) {
        assertEquals(message, responseBody.getMessage());
    }
}
