package com.aluracursos.conversormonedas;

import com.google.gson.Gson;

public class Conversor {
    private final ConsumoAPI consumoAPI;
    private final Gson gson;

    // URL base de la API de ExchangeRate-API
    //https://v6.exchangerate-api.com/v6/b22fd85899cfe35656fc4f2d/latest/USD
    private static final String API_BASE_URL = "https://v6.exchangerate-api.com/v6/b22fd85899cfe35656fc4f2d/latest/";

    /**
     * Constructor que recibe una instancia de ConsumoAPI. Esto se llama "inyección de dependencia".
     * @param consumoAPI Instancia de ConsumoAPI para realizar las llamadas HTTP.
     */
    public Conversor(ConsumoAPI consumoAPI) {
        this.consumoAPI = consumoAPI;
        this.gson = new Gson(); // Inicializa Gson.
    }

    /**
     * Obtiene las últimas tasas de cambio para una moneda base específica.
     * Convierte el JSON obtenido de ConsumoAPI a un objeto Monedas.
     *
     * @param baseCurrencyCode El código de la moneda base (ej. "USD", "EUR").
     * @return Un objeto Monedas que contiene todas las tasas y metadatos.
     * @throws ApiCallException Si ocurre un error al consumir la API o al parsear el JSON.
     */
    public Monedas getLatestRates(String baseCurrencyCode) throws ApiCallException {
        String url = API_BASE_URL + baseCurrencyCode.toUpperCase();

        try {
            // 1. Obtener el String JSON de la API usando ConsumoAPI.
            // ConsumoAPI puede lanzar RuntimeException, que capturamos aquí.
            String jsonResponse = consumoAPI.obtenerDatos(url);

            // Verificar si la respuesta es un error antes de intentar parsear
            if (jsonResponse.contains("\"result\":\"error\"")) {
                // Se puede parsear esto a un objeto de error si la API tiene un formato consistente
                // o simplemente lanzar una excepción.
                throw new ApiCallException("La API devolvió un error para " + baseCurrencyCode + ": " + jsonResponse, null);
            }

            // 2. Parsear el String JSON a un objeto Monedas usando Gson.
            Monedas response = gson.fromJson(jsonResponse, Monedas.class);

            // Verificar si el resultado es éxitoso y si contiene tasas
            if (!"success".equalsIgnoreCase(response.result()) || response.conversionRates() == null) {
                throw new ApiCallException("Respuesta de la API inesperada o incompleta para " + baseCurrencyCode, null);
            }

            return response;

        } catch (RuntimeException e) { // Capturamos las RuntimeException de ConsumoAPI o errores de Gson.
            // Envolvemos la RuntimeException en nuestra ApiCallException para darle más contexto.
            throw new ApiCallException("Error al obtener o parsear las tasas de cambio: " + e.getMessage(), e);
        }
        // No se necesita un 'catch (Exception e)' genérico aquí si ya capturamos RuntimeException,
        // ya que la mayoría de los errores de Gson o API se manifestarán como RuntimeException.
    }
    /**
     * Realiza la conversión de un monto entre dos monedas.
     * @param amount El monto a convertir.
     * @param sourceRates Un objeto Monedas que contiene las tasas de la moneda de origen.
     * @param targetCurrencyCode El código de la moneda de destino.
     * @return El monto convertido.
     * @throws ApiCallException Si la tasa de la moneda de destino no está disponible.
     */
    public double convert(double amount, Monedas sourceRates, String targetCurrencyCode) throws ApiCallException {
        Double rateToTarget = sourceRates.getRateForCurrency(targetCurrencyCode);
        if (rateToTarget == null) {
            throw new ApiCallException("No se encontró la tasa de conversión para " + targetCurrencyCode + " desde " + sourceRates.baseCode(), null);
        }
        return amount * rateToTarget;
    }
}