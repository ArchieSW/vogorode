package dev.archie.rancherservice.landscape.dto;

import lombok.Data;
import org.locationtech.jts.geom.Geometry;

@Data
public class FieldDto {

    private Long id;

    private String address;

    private Double latitude;

    private Double longitude;

    private Geometry area;

}
