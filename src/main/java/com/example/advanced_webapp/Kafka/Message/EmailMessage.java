package com.example.advanced_webapp.Kafka.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailMessage {
    String email;
    String link;

    public EmailMessage(@JsonProperty("name") String email, @JsonProperty("age") String link) {
        this.email = email;
        this.link = link;
    }
}
