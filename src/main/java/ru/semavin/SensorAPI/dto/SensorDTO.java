package ru.semavin.SensorAPI.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorDTO {
    @NotEmpty
    @NotNull
    @Size(min = 2, max = 30, message = "min 2, max 30")
    private String name;
}
