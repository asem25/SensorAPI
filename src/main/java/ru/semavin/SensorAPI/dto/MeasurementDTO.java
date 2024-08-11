package ru.semavin.SensorAPI.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeasurementDTO {
    @Min(value = -100, message = "min = -100")
    @Max(value = 100, message = "max = 100")
    @NotNull

    private Double meaning;
    @NotNull
    private Boolean raining;
    @NotNull
    private SensorDTO sensor;
    public SensorDTO getSensorDTO() {
        return sensor;
    }
    public void setSensorDTO(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
