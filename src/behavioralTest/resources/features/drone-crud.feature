# example on simple scenario
# all other curd will follow same approach

Feature: DroneCreation

  Scenario: Create a drone
    Given the user provides valid drone creation request
    When the user sends a POST request to "elmenus/droneia/api/v1/drone/register"
    And the response should contain "Drone registered successfully"