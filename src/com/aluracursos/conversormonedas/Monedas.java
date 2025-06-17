package com.aluracursos.conversormonedas;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public record Monedas(
        String result,
        String documentation,
        @SerializedName("terms_of_use") String termsOfUse,
        @SerializedName("time_last_update_unix") long timeLastUpdateUnix,
        @SerializedName("time_last_update_utc") String timeLastUpdateUtc,
        @SerializedName("time_next_update_unix") long timeNextUpdateUnix,
        @SerializedName("time_next_update_utc") String timeNextUpdateUtc,
        @SerializedName("base_code") String baseCode,
        @SerializedName("conversion_rates") Map<String, Double> conversionRates // Aquí está tu mapa de tasas
) {
    // Añadimos un método útil para obtener una tasa de conversión específica.
    public Double getRateForCurrency(String currencyCode) {
        if (conversionRates == null) {
            return null;
        }
        return conversionRates.get(currencyCode.toUpperCase()); // Asegura que el código sea mayúsculas.
    }
}