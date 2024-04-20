# example on simple scenario
# all other curd will follow same approach

Feature: DroneCreation

  Scenario: Create a drone
    Given the user provides valid drone creation request
    When the user sends a POST request to "elmenus/droneia/api/v1/drone/register"
    And the response should contain "Drone registered successfully"
    And the response should contain drone id
    Then the user should be able GET on "elmenus/droneia/api/v1/drone/get/{id}" drone details by id
    