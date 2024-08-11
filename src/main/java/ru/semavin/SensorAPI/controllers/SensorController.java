package ru.semavin.SensorAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.semavin.SensorAPI.dto.SensorDTO;
import ru.semavin.SensorAPI.models.Sensor;
import ru.semavin.SensorAPI.services.SensorService;
import ru.semavin.SensorAPI.util.SensorErrorResponse;
import ru.semavin.SensorAPI.util.SensorNotFoundException;
import ru.semavin.SensorAPI.util.SensorValidException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    @Autowired
    public SensorController(ModelMapper modelMapper, SensorService sensorService) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
    }

    @PostMapping("/registration")
    public String registration(@RequestBody @Valid SensorDTO sensorDTO,
                               BindingResult bindingResult) throws SensorValidException {
        if (bindingResult.hasErrors()){
            throw new SensorValidException("Sensors field has errors. Check valid");
        }

        sensorService.save(convertToSensor(sensorDTO));
        return sensorDTO.getName() + " saved";
    }
    @GetMapping("/{id}")
    public SensorDTO findOne(@PathVariable int id) throws SensorNotFoundException {
        Optional<Sensor> sensor = sensorService.findOne(id);
        if (sensor.isEmpty()){
            throw new SensorNotFoundException("Sensor with id: " + id + " not found");
        }

        return convertToSensorDTO(sensorService.findOne(id).get());
    }
    private Sensor convertToSensor(SensorDTO sensorDTO){
        return sensorService.enrichSensor(modelMapper.map(sensorDTO, Sensor.class));
    }
    private SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor, SensorDTO.class);
    }
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handlerException(SensorValidException e){
        return sensorService.handlerException(e);
    }
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handlerException(SensorNotFoundException e){
        return sensorService.handlerException(e);
    }



}