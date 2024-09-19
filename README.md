Weather API

Este proyecto es una API REST desarrollada con Spring Boot que consume datos de AccuWeather y almacena información 
climática relevante en una base de datos. A continuación se detallan las instrucciones para configurar y ejecutar el proyecto, 
así como para consumir la API.

1. Requisitos previos

Antes de empezar, asegurarse de tener instalado lo siguiente en tu entorno:

    Java 17 o superior
    Maven 3.6+ para gestionar dependencias
    Una cuenta en AccuWeather para obtener la API key
    
2.  Configuración del Proyecto

2.1. Clonar el repositorio

	git clone https://github.com/usuario/proyecto-weather-api.git

2.2. Configuración de AccuWeather API

	Debes configurar tu clave de AccuWeather API en la clase WeatherService. 
	Abre el archivo WeatherService.java y modifica la constante API_KEY:

	private static final String API_KEY = "TU_API_KEY";
	
	TODO: Aplicacion de buena practica pasar la KEY por properties

2.3. Configuración de la base de datos (H2)

	El proyecto está configurado para utilizar H2, una base de datos en memoria. 
	No necesitas realizar ninguna instalación adicional para configurar la base de datos. 
	Los datos se almacenan temporalmente durante la ejecución de la aplicación y se perderán al detenerla.

	La configuración para H2 ya está incluida en el archivo application.properties:
	
	
2.4. Acceso a la consola H2
	Puedes acceder a la consola web de H2 para visualizar los datos almacenados durante la ejecución de la aplicación accediendo a http://localhost:8080/h2-console.
	Puedes obtener la URL exacta al consultar la consola de ejecucion y extraer el siguiente dato de ejemplo y reemplazarlo por jdbc:h2:mem:weather_db
	H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:e3b4003f-e524-4251-b027-660680a7d016'
	
	Durante la ejecución de la aplicación, puedes acceder a la consola web de H2 en http://localhost:8080/h2-console.

    URL JDBC: jdbc:h2:mem:weather_db
    Usuario: sa
    Contraseña: admin

2.5. Estructura de la tabla

	La tabla weather tiene la siguiente estructura:
	Campo----------------------Tipo----------------Descripción
	id-------------------------Long----------------Identificador único del registro
	observationDateTime--------String--------------Fecha y hora de la observación
	weatherText----------------String--------------Descripción del clima
	hasPrecipitation-----------Boolean-------------Indica si hubo o no precipitación
	isDayTime------------------Boolean-------------Indica el tiempo del dia (true=day, false=night)
	precipitationType----------String--------------Indica el tipo de precipitación. Si no existe devuelve Null
	epochTime------------------Long----------------Tiempo en formato Epoch
	temperatureMetric----------Double--------------Temperatura en grados Celsius
	temperatureImperial--------Double--------------Temperatura en grados Fahrenheit
	temperatureMetricValue-----Double--------------Valor de temperatura
	temperatureMetricUnit------String--------------Indica el tipo de unidad de medida
	temperatureMetricUnitType--Int-----------------Indica ID numerico asociado con el tipo de unidad
    
2.6. Construir y ejecutar la aplicación

	Para construir y ejecutar el proyecto, utiliza los siguientes comandos en la raíz del proyecto:

	# Compilar el proyecto
	mvn clean install

	# Ejecutar el proyecto
	mvn spring-boot:run

3. Consumo de la API
3.1. Obtener y guardar datos climáticos

	Puedes hacer una solicitud GET para obtener los datos climáticos de una ubicación y guardarlos en la base de datos en memoria (H2).

    Endpoint: api/weather/save?locationKey=
    Método: GET
    Parámetros:
        locationKey: La clave de ubicación de AccuWeather. 
        
        Ejemplo de solicitud:

	GET http://localhost:8080/api/weather/save?locationKey=123456
	
	Respuesta exitosa:

	{
	  "weather": {
	    "LocalObservationDateTime": "2024-09-18T19:35:00-03:00",
	    "EpochTime": 1726760100,
	    "WeatherText": "Clear",
	    "HasPrecipitation": false,
	    "Temperature": {
	      "Metric": {
	        "Value": 18.9,
	        "Unit": "C",
	        "UnitType": 17
	      },
	      "Imperial": {
	        "Value": 66,
	        "Unit": "F",
	        "UnitType": 18
	      }
	    }
	  }
	}
  
3.2. Consultar todos los datos climáticos almacenados

	Puedes obtener todos los registros climáticos almacenados en la base de datos H2.

    Endpoint: /api/weather/all
    Método: GET

	Ejemplo de solicitud:

	GET http://localhost:8080/api/weather/all

	Respuesta:

	json
	
	[
	  {
	    "observationDateTime": "2024-09-18T19:35:00-03:00",
	    "weatherText": "Clear",
	    "temperatureMetric": 18.9,
	    "temperatureImperial": 66.0,
	    "hasPrecipitation": false
	  }
	]
	
	
4. Otros componentes
	
	RestTemplate: Se utiliza para consumir la API de AccuWeather.
	Jackson: Se utiliza para procesar las respuestas JSON de AccuWeather.
	H2 Database: Base de datos en memoria utilizada para almacenar los datos temporalmente durante la ejecución de la aplicación.
	
5. Testing

	Puedes usar herramientas como Postman o cURL para probar la API. Asegúrate de que el servidor esté ejecutándose localmente en http://localhost:8080.
	        
        
        
        
        
        
        