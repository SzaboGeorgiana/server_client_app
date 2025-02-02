package org.example;


import festival.model.Spectecol;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

public class StartRestClient {
    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
//Adding an article
        LocalDateTime date= LocalDateTime.parse("2024-06-21T20:00:00");
        Spectecol article=new Spectecol(1L,"NUSTIU", date,30);
        try{
            Spectecol articleId= restTemplate.postForObject("http://localhost:8080/festival/spectacol",article, Spectecol.class);
// Updating article ...
            int id= Integer.parseInt(String.valueOf(Long.valueOf(articleId.getId())));
            article.setLocatie("New title");
            restTemplate.put("http://localhost:8080/festival/spectacol/"+id,
                    article);
        }catch(RestClientException ex){
            System.out.println("Exception ... "+ex.getMessage());
        }
    }
}