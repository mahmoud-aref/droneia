#  <img src="https://play-lh.googleusercontent.com/OlpZ50TosR0DCS0WX1Kn6CJVqyv2v0gHeW196O2RqKuBjVJGgjV-ik07w_eB4o0Ndr7E" width="30" height="30"> elmenus Task :: Droneia 🚁


Droneia is a drone management system that allows users to register and manage drones that is used to deliver
medications.

![Java](https://img.shields.io/badge/-Java-red?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-green?style=flat-square&logo=spring)
![Gradle](https://img.shields.io/badge/-Gradle-blue?style=flat-square&logo=gradle)
![R2DBC](https://img.shields.io/badge/-R2DBC-blue?style=flat-square&logo=reactivex)
![WebFlux](https://img.shields.io/badge/-WebFlux-blue?style=flat-square&logo=spring)
![Docker](https://img.shields.io/badge/-Docker-blue?style=flat-square&logo=docker)
![Kotlin](https://img.shields.io/badge/-Kotlin-blue?style=flat-square&logo=kotlin)
![Cucumber](https://img.shields.io/badge/-Cucumber-blue?style=flat-square&logo=cucumber)

----

## Description 📝

This project is a Spring Boot application that uses R2DBC for reactive database access and WebFlux for handling HTTP
requests in a non-blocking way. The application is written in Java and uses Gradle(kotlin) as a build tool.

## Table of Contents 📚

- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [Tests](#tests)
- [Credits](#credits)
- [License](#license)

## Installation 🛠️

To install and run the project, you need to have Java 17 and Gradle installed on your machine. Clone the repository and
navigate to the project directory.

**To Run the application in a docker container, you can use the following command:**

```bash
docker compose up
```

---

## Usage 🚀

**1 : To register a drone, send a POST request to the `/elmenus/droneia/api/v1/drone/register` endpoint.**

**Validations:**

- The serial number max digits of 100.
- The model should be one of the following: LIGHT_WEIGHT, MEDIUM_WEIGHT, HEAVY_WEIGHT.
- The max weight should be between 1 and 500.

**Request Body Example:**

``` json
    {
    "serialNumber": "123456",
    "model": "LIGHT_WEIGHT",
    "maxWeight": 100
    }
```

**2 : To get all drones, send a GET request to the `/elmenus/droneia/api/v1/drone` endpoint.**

To get a drone by its unique id, send a GET request to the `/elmenus/droneia/api/v1/drone/{id}` endpoint.

**Response Example:**

``` json
{
    "message": "Drone data retrieved successfully",
    "data": {
        "id": "a220eb9d-81bf-4f48-98a8-c64e0a3b8298",
        "serialNumber": "333",
        "model": "LIGHT_WEIGHT",
        "maxWeight": 100.0,
        "batteryPercentage": 3,
        "state": "IDLE"
    }
}
```

**3 : To update a drone, send a PUT request to the `/elmenus/droneia/api/v1/drone/update` endpoint.**

**Request Body Example:**

``` json
{
    "id": "a220eb9d-81bf-4f48-98a8-c64e0a3b8298",
    "serialNumber": "333",
    "model": "LIGHT_WEIGHT",
    "maxWeight": 100,
    "batteryPercentage": 3,
    "state": "IDLE"
}
```

_AND SO ON ..._

-----

## Tests 🧪

The project includes multiple test elements :

* Unit tests for the service layer.
* Integration tests for the controller layer.
* Behavioral tests for the API endpoints. (using KHYARA
  framework ![Cucumber](https://img.shields.io/badge/-Cucumber-blue?style=flat-square&logo=cucumber))

### To run Unit and Integration Tests:

```bash
./gradlew test
```

### To run Behavioral Tests:

```bash
./gradlew runCucumberTest
```

## Credits 👏

This project was developed by [mahmoud-aref](https://github.com/mahmoud-aref).



