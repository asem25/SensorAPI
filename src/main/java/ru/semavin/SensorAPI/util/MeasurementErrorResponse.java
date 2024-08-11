package ru.semavin.SensorAPI.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MeasurementErrorResponse {
    private String message;
    private LocalDateTime time;
}
