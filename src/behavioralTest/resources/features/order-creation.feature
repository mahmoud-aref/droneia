# this is the main core logic of handling drone loading and so on

Feature: OrderCreation

  Scenario: CreateValidOrder
    Given Drone valid for the load after POST request "elmenus/droneia/api/v1/drone/register"
    Given Medication items are available after POST request "elmenus/droneia/api/v1/medication/register"
    When User creates an order POST request "elmenus/droneia/api/v1/order/create"
    Then Order should be created successfully with message "Drone Loaded Successfully"
    And Drone should be loaded with the order when GET request "elmenus/droneia/api/v1/drone/get/{id}"
    And Order should be in the active state when GET request "elmenus/droneia/api/v1/order/get/{id}"
