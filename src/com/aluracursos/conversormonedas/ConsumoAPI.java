package com.aluracursos.conversormonedas;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    public String obtenerDatos(String url){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            // Envuelve la excepción original en RuntimeException para simplificar.
            throw new RuntimeException("Error de I/O al obtener datos: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            // Envuelve la excepción original y maneja la interrupción del hilo.
            Thread.currentThread().interrupt(); // Restaura el estado de interrupción del hilo.
            throw new RuntimeException("La operación fue interrumpida: " + e.getMessage(), e);
        }
        String json = response.body();
        return json;
    }
}
