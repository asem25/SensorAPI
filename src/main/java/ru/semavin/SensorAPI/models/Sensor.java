package ru.semavin.SensorAPI.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="sensors")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sensor_name")
    @NotEmpty
    @Size(min = 2, max = 30, message = "Size of name >=2 & <=30")
    private String name;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="last_meas")
    private LocalDateTime last_meas;

    @OneToMany(mappedBy = "sensor", fetch = FetchType.EAGER)
    private List<Measurements> measurements;
}
