package org.example;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        // Объект запроса
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        // Отправка запроса
        CloseableHttpResponse response = httpClient.execute(request);
        // Вывод полученных заголовков
        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
        // Чтение тела ответа
//        String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
//        System.out.println("Body: " + body);


    }
}