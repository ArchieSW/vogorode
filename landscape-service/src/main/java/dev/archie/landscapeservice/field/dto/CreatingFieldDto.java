package dev.archie.landscapeservice.field.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.locationtech.jts.geom.Geometry;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;

import java.util.List;

@Data
public class CreatingFieldDto {
    private Double latitude;
    private Double longitude;
    private Double fieldArea;
    private List<String> jobs;
    private String address;

    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    private Geometry area;
}
