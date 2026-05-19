package com.uma.example.springuma.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uma.example.springuma.model.Paciente;
import com.uma.example.springuma.model.PacienteService;
import com.uma.example.springuma.model.ResourceNotFoundException;

@RestController
public class PacienteController {

    private static final Logger log = LoggerFactory.getLogger(PacienteController.class);

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<Paciente> getPaciente(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(pacienteService.getPaciente(id));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/paciente/medico/{id}")
    public List<Paciente> getPacientes(@PathVariable("id") Long id) {
        return pacienteService.getPacientesMedico(id);
    }

    @PostMapping(value = "/paciente", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> savePaciente(@RequestBody Paciente paciente) {
        try {
            pacienteService.addPaciente(paciente);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            log.error("Failed to save paciente", e);
            return ResponseEntity.status(409).body("El paciente ya existe");
        }
    }

    @PutMapping(value = "/paciente", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateCuenta(@RequestBody Paciente paciente) {
        try {
            pacienteService.updatePaciente(paciente);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error updating paciente", e);
            return ResponseEntity.internalServerError().body("Error al actualizar el paciente ");
        }
    }

    @DeleteMapping("/paciente/{id}")
    public ResponseEntity<?> deleteCuenta(@PathVariable("id") Long id) {
        try {
            Paciente paciente = pacienteService.getPaciente(id);
            log.debug("Deleting paciente: {}", paciente);
            pacienteService.removePaciente(paciente);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting paciente", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
