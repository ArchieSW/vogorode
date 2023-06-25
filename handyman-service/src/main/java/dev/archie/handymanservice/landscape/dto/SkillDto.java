package dev.archie.handymanservice.landscape.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class SkillDto {

    private Long id;

    private String name;

    @JsonIgnoreProperties("skills")
    private HandymanDto handyman;
}
