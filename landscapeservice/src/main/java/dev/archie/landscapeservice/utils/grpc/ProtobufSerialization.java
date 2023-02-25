package dev.archie.landscapeservice.utils.grpc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.util.JsonFormat;

public class ProtobufSerialization {

    public static JsonNode toJson(MessageOrBuilder messageOrBuilder) throws RuntimeException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(JsonFormat.printer().print(messageOrBuilder));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
