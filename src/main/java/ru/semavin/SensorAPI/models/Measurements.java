package ru.semavin.SensorAPI.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "measurements")
@Entity
@Getter
@Setter
public class Measurements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "meaning")
    @NotNull(message = "Enter the meaning")
    private double meaning;
    @NotNull(message = "is raining?")
    @Column(name = "is_raining")
    private boolean raining;
    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")

    private Sensor sensor;
    @Column(name = "time_of_meaning")
    private LocalDateTime dateTime;
}
