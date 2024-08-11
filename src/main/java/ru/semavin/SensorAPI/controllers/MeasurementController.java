package ru.semavin.SensorAPI.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.semavin.SensorAPI.dto.MeasurementDTO;
import ru.semavin.SensorAPI.dto.SensorDTO;
import ru.semavin.SensorAPI.models.Measurements;
import ru.semavin.SensorAPI.models.Sensor;
import ru.semavin.SensorAPI.services.MeasurementService;
import ru.semavin.SensorAPI.services.SensorService;
import ru.semavin.SensorAPI.util.MeasurementErrorResponse;
import ru.semavin.SensorAPI.util.MeasurementValidException;
import ru.semavin.SensorAPI.util.SensorErrorResponse;
import ru.semavin.SensorAPI.util.SensorNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.util.ClassUtils.isPresent;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, SensorService sensorService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<MeasurementDTO> findAll(){
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList());
    }
    @GetMapping("/rainyDaysCount")
    public String rainyDaysCount(){
        StringBuilder stringBuilder = new StringBuilder();
        List<Measurements> measurements = measurementService.findByIsRainingTrue();

        if (!measurements.isEmpty()){
            for (Measurements measurement : measurements) {
                stringBuilder.append(measurement.getMeaning()).append(" - ")
                        .append(measurement.getDateTime().getDayOfWeek()).append(" - at ")
                        .append(measurement.getDateTime().getHour())
                        .append(";\n");
            }
        }
        return !stringBuilder.isEmpty() ?
                stringBuilder.toString() +  "Count:" + measurements.size()
                : "No rainy days in data base";
    }
    @PostMapping("/add")
    public String addMeas(@RequestBody @Valid MeasurementDTO measurementDTO,
                          BindingResult bindingResult) throws SensorNotFoundException, MeasurementValidException {
        if (bindingResult.hasErrors()){
            StringBuilder str = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                str.append(fieldError.getField()).append(" - ")
                        .append(fieldError.getDefaultMessage()).append("; ");
            }

            throw new MeasurementValidException(str.toString());
        }


        Optional<Sensor> sensor = sensorService.findByName(measurementDTO.getSensorDTO().getName());

        if (sensor.isPresent()){
            Measurements measurements = convertToMeasurement(measurementDTO);
            measurements.setSensor(sensor.get());

            measurementService.save(measurements);

            return measurementDTO.getSensorDTO().getName() + " - temp " + measurementDTO.getMeaning();
        }


        throw new SensorNotFoundException("sensor is not found");
        }

    private Measurements convertToMeasurement(MeasurementDTO measurementDTO){
        return measurementService.enrichMeasurement(modelMapper.map(measurementDTO, Measurements.class));
    }
    private MeasurementDTO convertToMeasurementDTO(Measurements measurements){
        MeasurementDTO measurementDTO = modelMapper.map(measurements, MeasurementDTO.class);
        if (measurements.getSensor() != null)
            measurementDTO.setSensorDTO(modelMapper.map(measurements.getSensor(), SensorDTO.class));
        return measurementDTO;
    }
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handlerException(SensorNotFoundException e){
        return sensorService.handlerException(e);
    }
    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handlerException(MeasurementValidException e){
        MeasurementErrorResponse errorResponse = new MeasurementErrorResponse(e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
