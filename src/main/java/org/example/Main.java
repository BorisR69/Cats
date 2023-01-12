package org.example;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static ObjectMapper mapper = new ObjectMapper();
    public static void main(String[] args) throws IOException {
        String url = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
//        String url = "https://jsonplaceholder.typicode.com/posts";

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        // Объект запроса
        HttpGet request = new HttpGet(url);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        // Отправка запроса
        CloseableHttpResponse response = httpClient.execute(request);
        // Вывод полученных заголовков
//        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);
        // Чтение тела ответа
//        String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
//        System.out.println(body);

        List <Cat> cat = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cat>>() {});
//        cat.forEach(System.out::println);
        // Создание потока из списка
        Stream <Cat> stream = cat.stream();
        // фильтация стрима на ненулевык значения поля upvotes
        stream.filter(value -> value.getUpvotes() != null).forEach(System.out::println);

        response.close();
        httpClient.close();
    }
}