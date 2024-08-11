package ru.semavin.SensorAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.semavin.SensorAPI.models.Sensor;
import ru.semavin.SensorAPI.repositories.SensorRepository;
import ru.semavin.SensorAPI.util.SensorErrorResponse;
import ru.semavin.SensorAPI.util.SensorNotFoundException;
import ru.semavin.SensorAPI.util.SensorValidException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class SensorService {
    private final SensorRepository sensorRepository;
    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public void save(Sensor sensor){
        sensorRepository.save(sensor);
    }

    public Optional<Sensor> findOne(Integer id){
        return sensorRepository.findById(id);
    }
    public Sensor enrichSensor(Sensor sensor){
        sensor.setCreatedAt(LocalDateTime.now());
        sensor.setLast_meas(LocalDateTime.now());
        return sensor;
    }
    public Optional<Sensor> findByName(String name){
        return sensorRepository.findByNameIgnoreCase(name);
    }
    public ResponseEntity<SensorErrorResponse> handlerException(SensorValidException e){
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(
                e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<SensorErrorResponse> handlerException(SensorNotFoundException e){
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(
                e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.NOT_FOUND);
    }
}
