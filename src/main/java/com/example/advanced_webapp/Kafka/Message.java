package com.example.advanced_webapp.Kafka;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
    String email;
    String link;

    public Message(@JsonProperty("name") String email, @JsonProperty("age") String link) {
        this.email = email;
        this.link = link;
    }
}
