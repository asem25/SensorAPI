package ru.semavin.SensorAPI.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.semavin.SensorAPI.models.Measurements;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurements, Integer> {
    List<Measurements> findByRainingIsTrue();
}
