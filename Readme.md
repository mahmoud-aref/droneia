# Droneia 🚁

Droneia is a drone management system that allows users to register and manage drones.

## Description 📝

This project is a Spring Boot application that uses R2DBC for reactive database access and WebFlux for handling HTTP requests in a non-blocking way. The application is written in Java and uses Gradle as a build tool. The project also includes behavioral tests written in Cucumber.

The application allows users to register drones by sending a POST request to the `/elmenus/droneia/api/v1/drone/register` endpoint. The response will contain a message indicating that the drone was registered successfully.

The project uses several technologies and libraries such as Spring Security for authentication and authorization, MapStruct for object mapping, Lombok for reducing boilerplate code, and AWS SDK for interacting with AWS services.

## Table of Contents 📚

- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [Tests](#tests)
- [Credits](#credits)
- [License](#license)

## Installation 🛠️

To install and run the project, you need to have Java 17 and Gradle installed on your machine. Clone the repository and navigate to the project directory. Run the following command to build the project:

```bash
gradle build
```

## Usage 🚀

To start the application, run the following command:

```bash
gradle bootRun
```

To register a drone, send a POST request to the `/elmenus/droneia/api/v1/drone/register` endpoint.

## Contributing 🤝

Contributions are welcome. Please feel free to submit a pull request or open an issue.

## Tests 🧪

The project includes behavioral tests written in Cucumber. To run the tests, use the following command:

```bash
gradle test
```

## Credits 👏

This project was developed by [mahmoud-aref](https://github.com/mahmoud-aref).

## License 📄

This project is licensed under the GPL License - see the [LICENSE](LICENSE) file for details.

## Badges 🏅

![Java](https://img.shields.io/badge/-Java-red?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-green?style=flat-square&logo=spring)
![Gradle](https://img.shields.io/badge/-Gradle-blue?style=flat-square&logo=gradle)
![GitHub](https://img.shields.io/github/license/mahmoud-aref/droneia)
![GitHub contributors](https://img.shields.io/github/contributors/mahmoud-aref/droneia)
![GitHub issues](https://img.shields.io/github/issues/mahmoud-aref/droneia)
