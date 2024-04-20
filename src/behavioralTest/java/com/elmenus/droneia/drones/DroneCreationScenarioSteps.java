package com.elmenus.droneia.drones;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.drone.model.DroneEntity;
import com.elmenus.droneia.domain.drone.model.DroneRegistrationRequest;
import com.elmenus.droneia.helper.DroneTestingHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

import java.util.UUID;

import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DroneCreationScenarioSteps {

    private DroneRegistrationRequest droneRegistrationRequest;
    private BasicResponse<DroneEntity> droneResponseBody;
    private UUID droneId;

    @Given("the user provides valid drone creation request")
    public void the_user_provides_valid_drone_creation_request() {
        this.droneRegistrationRequest = DroneTestingHelper.getMockDroneRegistrationRequest();
    }

    @When("the user sends a POST request to {string}")
    public void the_user_sends_a_post_request_to(String path) {
        droneResponseBody = with()
                .body(droneRegistrationRequest)
                .contentType(ContentType.JSON)
                .when()
                .request("POST", path)
                .then()
                .statusCode(200)
                .extract()
                .as(new DroneTestingHelper.DroneEntityTypeReference().getType());
    }

    @And("the response should contain {string}")
    public void the_response_should_contain(String message) {
        assertEquals(message, droneResponseBody.getMessage());
    }

    @And("the response should contain drone id")
    public void theResponseShouldContainDroneId() {
        assertNotNull(droneResponseBody.getData().getId());
        droneId = droneResponseBody.getData().getId();
    }


    @Then("the user should be able GET drone details by id")
    public void theUserShouldBeAbleGETDroneDetailsById() {
        with()
                .pathParam("id", droneId)
                .when()
                .request("GET", "/drones/{id}")
                .then()
                .statusCode(200);
    }

    @Then("the user should be able GET on {string} drone details by id")
    public void theUserShouldBeAbleGETOnDroneDetailsById(String path) {
        with()
                .pathParam("id", droneId)
                .when()
                .request("GET", path)
                .then()
                .statusCode(200);
        assertEquals(droneResponseBody.getData().getId(), droneId);
    }
}
