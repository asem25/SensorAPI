package ru.semavin.SensorAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semavin.SensorAPI.models.Measurements;
import ru.semavin.SensorAPI.repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MeasurementService {
    private final MeasurementRepository repository;
    @Autowired
    public MeasurementService(MeasurementRepository repository) {
        this.repository = repository;
    }

    public List<Measurements> findAll(){
        return repository.findAll();
    }
    public List<Measurements> findByIsRainingTrue(){
        return repository.findByRainingIsTrue();
    }
    public void save(Measurements measurements){
        repository.save(measurements);
    }
    public Measurements enrichMeasurement(Measurements measurements){
        measurements.setDateTime(LocalDateTime.now());
        return measurements;
    }
}
