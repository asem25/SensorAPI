package ru.semavin.SensorAPI.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SensorErrorResponse {
    private String message;
    private LocalDateTime time;
}
