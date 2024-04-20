package com.elmenus.droneia.orders;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.domain.drone.model.DroneEntity;
import com.elmenus.droneia.domain.drone.model.DroneState;
import com.elmenus.droneia.domain.medication.model.MedicationEntity;
import com.elmenus.droneia.domain.order.model.OrderEntity;
import com.elmenus.droneia.domain.order.model.OrderResponse;
import com.elmenus.droneia.domain.order.model.OrderState;
import com.elmenus.droneia.helper.DroneTestingHelper;
import com.elmenus.droneia.helper.MedicationTestingHelper;
import com.elmenus.droneia.helper.OrderTestingHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.with;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderCreationScenarioSteps {

    private BasicResponse<DroneEntity> droneResponseBody;
    private BasicResponse<MedicationEntity> medicationResponseBody;
    private BasicResponse<OrderResponse> orderResponseBasicResponse;

    private BasicResponse<String> errorResponse;


    @Given("Drone valid for the load after POST request {string}")
    public void droneValidForTheLoadAfterPOSTRequest(String path) {
        droneResponseBody = with()
                .body(DroneTestingHelper.getMockDroneRegistrationRequest())
                .contentType(ContentType.JSON)
                .when()
                .request("POST", path)
                .then()
                .statusCode(200)
                .extract()
                .as(new DroneTestingHelper.DroneEntityTypeReference().getType());
    }

    @Given("Medication items are available after POST request {string}")
    public void medicationItemsAreAvailableAfterPOSTRequest(String path) {
        medicationResponseBody = with()
                .body(MedicationTestingHelper.getMockMedicationRegistrationRequest())
                .contentType(ContentType.JSON)
                .when()
                .request("POST", path)
                .then()
                .statusCode(200)
                .extract()
                .as(new MedicationTestingHelper.MedicationEntityTypeReference().getType());
    }

    @When("User creates an order POST request {string}")
    public void userCreatesAnOrderPOSTRequest(String path) {

        orderResponseBasicResponse = with()
                .body(OrderTestingHelper.getMockOrderLoadingRequest(
                        droneResponseBody.getData().getId().toString(),
                        medicationResponseBody.getData().getId().toString(),
                        90
                ))
                .contentType(ContentType.JSON)
                .when()
                .request("POST", path)
                .then()
                .statusCode(200)
                .extract()
                .as(new OrderTestingHelper.OrderResponseType().getType());
    }

    @Then("Order should be created successfully with message {string}")
    public void orderShouldBeCreatedSuccessfullyWithMessage(String message) {
        assertEquals(message, orderResponseBasicResponse.getMessage());
    }

    @And("Drone should be loaded with the order when GET request {string}")
    public void droneShouldBeLoadedWithTheOrderWhenGETRequest(String path) {
        droneResponseBody = with()
                .pathParam("id", droneResponseBody.getData().getId())
                .when()
                .request("GET", path)
                .then()
                .statusCode(200)
                .extract()
                .as(new DroneTestingHelper.DroneEntityTypeReference().getType());
        // get the fancy of steps ,so I'll reassign same variable
        assertEquals(
                droneResponseBody.getData().getState(),
                DroneState.LOADED
        );
    }


    @And("Order should be in the active state when GET request {string}")
    public void orderShouldBeInTheActiveStateWhenGETRequest(String path) {
        BasicResponse<OrderEntity> orderEntityBasicResponse = with()
                .pathParam("id", orderResponseBasicResponse.getData().getOrderId())
                .when()
                .request("GET", path)
                .then()
                .statusCode(200)
                .extract()
                .as(new OrderTestingHelper.OrderEntityType().getType());
        assertEquals(
                orderEntityBasicResponse.getData().getState(),
                OrderState.ACTIVE
        );
    }

    @When("User creates another order POST request {string}")
    public void userCreatesAnotherOrderPOSTRequest(String path) {
        errorResponse = with()
                .body(OrderTestingHelper.getMockOrderLoadingRequest(
                        droneResponseBody.getData().getId().toString(),
                        medicationResponseBody.getData().getId().toString(),
                        90
                ))
                .contentType(ContentType.JSON)
                .when()
                .request("POST", path)
                .then()
                .statusCode(409)
                .extract()
                .as(new OrderTestingHelper.ErrorResponseType().getType());
    }

    @Then("Order should not be created with message {string}")
    public void orderShouldNotBeCreatedWithMessages(String message) {
        assertEquals(message, errorResponse.getMessage());
    }

    @When("Drone Battery Drain to {int}% after POST request {string}")
    public void droneBatteryDrainToAfterPOSTRequest(int percentage, String path) {
        droneResponseBody = with()
                .body(DroneTestingHelper.getMockLowBatteryDroneUpdateRequest(droneResponseBody.getData().getId().toString()))
                .contentType(ContentType.JSON)
                .when()
                .request("PUT", path)
                .then()
                .statusCode(200)
                .extract()
                .as(new DroneTestingHelper.DroneEntityTypeReference().getType());
    }
}
