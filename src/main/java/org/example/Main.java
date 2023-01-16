package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws IOException {
        String url = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        // Объект запроса
        HttpGet request = new HttpGet(url);
        // Отправка запроса
        CloseableHttpResponse response = httpClient.execute(request);
        List <Cat> cat = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cat>>() {});
        // Создание потока из списка
        Stream <Cat> stream = cat.stream();
        // фильтация стрима на ненулевык значения поля upvotes и вывод результата в консоль
        stream.filter(value -> value.getUpvotes() != null).forEach(System.out::println);
        response.close();
        httpClient.close();
    }
}