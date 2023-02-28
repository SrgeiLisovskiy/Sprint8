package com.company.http;

import com.company.serves.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class KVTaskClient {
    private final int PORT = 8078;
    private  String url;

    private  String API_TOKEN;
    public final HttpClient client;

    public KVTaskClient(String url) {
        this.client = HttpClient.newHttpClient();
        this.url = url;
        this.API_TOKEN = register(url);

    }



    private String register(String url){
        try{
            URI uri = URI.create("http://" + url + ":" + PORT + "/register");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() !=200){
                throw new ManagerSaveException("Не удалось сохранить запрос, код состояния: " + response.statusCode());
            }
            return response.body();
        }catch (IOException | InterruptedException e){
            throw  new ManagerSaveException("Не удалось сохранить запрос");
        }
    }
    public String load(String key){
        try{
            URI uri = URI.create("http://" + url + ":" + PORT + "/load/" + key + "?API_TOKEN=" + API_TOKEN);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200)
               return response.body();
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка: " + e.getMessage());
        }
        return "Во время запроса произошла ошибка";
    }
    public void put(String key, String json){
        try{
            URI uri = URI.create("http://" + url + ":" + PORT + "/save/" + key + "?API_TOKEN=" + API_TOKEN);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = client.send(request, handler);
                if (response.statusCode() != 200)
                    throw new ManagerSaveException("Не удается сохранить запрос, код состояния" + response.statusCode());
            } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
                System.out.println("Во время выполнения запроса возникла ошибка: " + e.getMessage());
            }
        }


    public void setApiToken(String API_TOKEN) {
        this.API_TOKEN = API_TOKEN;
    }
}

