package com.example.kafka.controller;

import com.example.kafka.entity.Address;
import com.example.kafka.entity.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private KafkaTemplate<String, UserDto> kafkaTemplate;

    @PostMapping
    public void sendOrder(String messageId, UserDto message) {
        message.setAddress(new Address("PL", "Warsaw", "Swietokrzyska", 1L, 1L));
        ListenableFuture<SendResult<String, UserDto>> future = kafkaTemplate.send("message", messageId, message);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }
}
