package com.example.bankcards.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CardNumberMaskSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (s == null) {
            jsonGenerator.writeNull();
            return;
        }

        int unmaskedLength = 4;
        int maskedLength = s.length() - unmaskedLength;
        String masked = "*".repeat(Math.max(0, maskedLength)) + s.substring(maskedLength);

        jsonGenerator.writeString(masked);
    }
}
