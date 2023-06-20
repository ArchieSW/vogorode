package dev.archie.rancherservice.field.dto;

import lombok.Data;

import java.util.List;

@Data
public class CreatingFieldDto {
    Double latitude;
    Double longitude;
    Double area;
    List<String> jobs;
    String address;
}