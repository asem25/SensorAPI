package ru.semavin.SensorAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.semavin.SensorAPI.models.Sensor;

import java.util.Optional;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Integer> {

    Optional<Sensor> findByNameIgnoreCase(String name);
}
