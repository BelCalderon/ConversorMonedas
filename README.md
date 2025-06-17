# ConversorMonedas
Practicando con Java: Challenge Conversor de Monedas
Aplicación de Conversión de Monedas
Esta es una aplicación de línea de comandos en Java que permite a los usuarios convertir cantidades entre diferentes monedas utilizando las tasas de cambio más recientes de la API ExchangeRate-API.

# Características
Obtiene las tasas de cambio actuales de una API externa.

Muestra una lista de monedas disponibles.

Permite al usuario seleccionar una moneda de origen, una moneda de destino y un monto a convertir.

Realiza la conversión y muestra el resultado.

Manejo básico de errores para problemas de red o datos de API inválidos.

## Clases Principales
El proyecto está estructurado en varias clases para separar las responsabilidades:

* ApiCallException.java: Una excepción personalizada utilizada para errores que ocurren al interactuar con la API.

* ConsumoAPI.java: Encapsula la lógica para realizar peticiones HTTP a la API de tasas de cambio y obtener la respuesta en formato JSON.

* Monedas.java: Un record de Java que representa la estructura de la respuesta JSON de la API. Incluye los códigos de moneda, las tasas de conversión y metadatos.

* Conversor.java: La clase de servicio que integra ConsumoAPI y Monedas. Es responsable de obtener y parsear los datos de la API, y de realizar el cálculo de la conversión de monedas.

* Principal.java: La clase principal (punto de entrada) de la aplicación. Maneja la interacción con el usuario a través de la consola, coordina las llamadas al Conversor y muestra los resultados.

## Configuración y Ejecución
Para ejecutar esta aplicación, necesitarás tener instalado el Kit de Desarrollo de Java (JDK) y la librería Gson para el manejo de JSON.

## Requisitos
Java Development Kit (JDK) (versión 11 o superior recomendada).

Gson Library: Una librería para la serialización/deserialización de objetos Java a/desde JSON.

## Uso
Una vez que la aplicación se esté ejecutando, sigue las instrucciones en la consola:

La aplicación intentará obtener y mostrar una lista de las monedas disponibles.

Se te pedirá que ingreses el código de la moneda de origen (ej. USD, EUR).

Se te pedirá que ingreses el código de la moneda de destino (ej. JPY, GBP).

Se te pedirá que ingreses el monto que deseas convertir.

La aplicación mostrará el monto convertido y la fecha de la última actualización de las tasas.


