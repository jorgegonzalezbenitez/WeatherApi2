# _Second practice_
Desarrollo de Aplicaciones para Ciencia de datos

Curso 2023-2024

2º Grado en Ciencia e Ingeniería de datos

Universidad Las Palmas de Gran Canaria

## _Functionality_

The software is responsible for acquiring and storing meteorological data for the Canary Islands at a specific time. It relies on a weather service provider (OpenWeatherMap) to obtain the information and send it to a broker that acts as an intermediary, and finally write those messages that we send and receive from ActiveMQ in a directory. This process occurs every 6 hours to update the latest weather forecasts.

## _Resources Used_

#### Development Environments

The project is developed in the Intellij development environment. Java is used for the code implementation.

#### Version Control Tools

I use Git as a version control tool. The use of Git makes it easier to make changes in the code development, providing the assurance that such changes cannot be deleted.

#### Documentation Tools

I use Markdown, providing a quick and concise overview of the project.

## Design

#### Design patterns

The application adheres to the Single Responsibility Principle (SRP), ensuring clear responsibilities for each component and enhancing code clarity. It adopts the Controller design pattern from the Model-View-Controller (MVC) architecture, facilitating efficient management of interactions between the model and the view.

In terms of asynchronous communication, the Observer pattern is employed to receive messages from a JMS topic. The AMQTopicSubscriber class serves as an observer, efficiently handling the reception of messages as they become available. This combination of design principles, patterns, and Lambda architecture establishes a robust foundation for the application, fostering modularity, scalability, and maintainability.

#### Design principle

The design architecture is structured in two independent modules: the "prediction-provider" module is responsible for receiving the data from the openweathermap API and sends it to the broker. On the other hand, there is the "event-store-builder" module that receives the broker's messages and writes them to a directory.

Additionally, the application leverages a Lambda system architecture, dividing operations into real-time and batch processing. This architectural choice enables effective handling of large datasets and provides flexibility for handling continuous streams and periodic batch operations.


#### Project Diagram in StarUml

This is the UML class diagram that represents and illustrates the functionality of the "prediction-Provider" module.
<img width="586" alt="image" src="https://github.com/jorgegonzalezbenitez/WeatherApi/assets/145259600/4832f87e-3210-4d3b-b2c1-49eed6a38eae">

This is the UML class diagram that represents and illustrates the functionality of the "event-store-builder" module.
<img width="538" alt="image" src="https://github.com/jorgegonzalezbenitez/WeatherApi/assets/145259600/4dfc25f4-8f43-4b86-80c9-152257c752d2">

