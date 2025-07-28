package com.example.bankcards.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateUtils {

    public String generateCardNumber(){
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
