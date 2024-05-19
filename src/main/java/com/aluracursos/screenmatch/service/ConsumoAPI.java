package com.aluracursos.screenmatch.service;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {


    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
			response = client
					.send(request, HttpResponse.BodyHandlers.ofString());
		}catch(ConnectException e) { // Captura la excepción específica
                // Maneja la excepción de ConnectException aquí
                // Por ejemplo, puedes imprimir un mensaje de error
                System.err.println("Error de conexión: " + e.getMessage());
                throw new RuntimeException("Error de conexión: " + e.getMessage());
            } catch(IOException e){
                throw new RuntimeException(e);
            } catch(InterruptedException e){
                throw new RuntimeException(e);
            }

            String json = response.body();
            return json;
        }


    }
