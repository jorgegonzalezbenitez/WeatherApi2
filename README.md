# _First practice_
Desarrollo de Aplicaciones para Ciencia de datos

Curso 2023-2024

2º Grado en Ciencia e Ingeniería de datos

Universidad Las Palmas de Gran Canaria

## _Functionality_

The software is responsible for acquiring and storing meteorological data from the Canary Islands at a specific point in time. It relies on a weather service provider (OpenWeatherMap) to obtain the information and store it in an SQLite database. This process occurs every 6 hours to update the latest weather forecasts.

## _Resources Used_

#### Development Environments

The project is developed in the Intellij development environment. Java is used for the code implementation.

#### Version Control Tools

I use Git as a version control tool. The use of Git makes it easier to make changes in the code development, providing the assurance that such changes cannot be deleted.

#### Documentation Tools

I use Markdown, providing a quick and concise overview of the project.

## Design

#### Design patterns

It adheres to the Single Responsibility Principle (SRP) to ensure that each component has a single responsibility, enhancing code clarity and maintainability. Additionally, the implementation follows the Controller design pattern, a part of the Model-View-Controller (MVC) architecture, to efficiently manage the interaction between the model and the view.

#### Design principle

The design architecture is structured into two extensive sets of packages:
"org.example.control": This package consolidates control classes, enabling the implementation of project functions, including the retrieval from an API and the storage of data in a database.
"org.example.model": In this package, model classes are housed, capturing meteorological information, both climatic characteristics and precise location.


#### Project Diagram in StarUml
<img width="605" alt="image" src="https://github.com/jorgegonzalezbenitez/WeatherApi/assets/145259600/4a305b3c-dbfd-4521-8819-5ddce64ea942">