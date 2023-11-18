# _Práctica1_
Desarrollo de Aplicaciones para Ciencia de datos

Curso 2023-2024

2º Grado en Ciencia e Ingeniería de datos

Universidad Las Palmas de Gran Canaria

## _Funcionalidad_

El software tiene la responsabilidad de adquirir y almacenar datos meteorológicos provenientes de las Islas Canarias en una instancia de tiempo en concreto. Se apoya de un proveedor de servicios climáticos (OpenWeatherMap) para obtener la información y almacenarla en una base de datos SQLite. Este proceso se lleva a cabo cada 6 hora para actualizar las nuevas predicciones del tiempo.

## _Recursos utilizados_

#### Entornos de desarrollos

El desarrollo del proyecto se lleva a cabo en el entorno de desarrollo Intellij. Para la implementación del código, se emplea el lenguaje de programación Java.

#### Herramientas de Control de Versiones

Hago uso de la herramienta Git para el control de versiones. La utilización de Git facilita la realización de cambios en el desarrollo del código , ofreciendo la seguridad de que dichos cambios no pueden ser eliminados.

#### Herramientas de Documentación

Hago uso de Markdown, proporcionando una visión rápida y concisa del proyecto.


## Diseño

#### Patrones de diseños
Se adhiere al Principio de Responsabilidad Única (SRP) para garantizar que cada componente tenga una única responsabilidad, mejorando la claridad y mantenibilidad del código.Por otro lado, se lleva a cabo la implementación del patrón de diseño Controlador, parte del Modelo-Vista-Controlador (MVC), para gestionar eficientemente la interacción entre el modelo y la vista.

#### Principio de diseño

La arquitectura del diseño se articula en dos extensos conjuntos de paquetes:
"org.example.control"; Este paquete aglutina las clases de control, posibilitando la implementación de las funciones del proyecto, incluyendo la recopilación desde una API y el almacenamiento de los datos a una base de datos .
"org.example.model"; En este paquete se albergan las clases del modelo que capturan información meteorológica tanto las características climáticas como su localización exacta.

#### Diagrama del Proyecto en StarUml
<img width="605" alt="image" src="https://github.com/jorgegonzalezbenitez/WeatherApi/assets/145259600/4a305b3c-dbfd-4521-8819-5ddce64ea942">
