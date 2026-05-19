package com.uma.example.springuma.controller;

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

import com.uma.example.springuma.model.Medico;
import com.uma.example.springuma.model.MedicoService;
import com.uma.example.springuma.model.ResourceNotFoundException;

@RestController
public class MedicoController {

    private static final Logger log = LoggerFactory.getLogger(MedicoController.class);

    private final MedicoService medicoService;

    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }

    @GetMapping("/medico/{id}")
    public ResponseEntity<Medico> getMedico(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(medicoService.getMedico(id));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/medico", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> saveMedico(@RequestBody Medico medico) {
        try {
            medicoService.addMedico(medico);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            log.error("Failed to save medico", e);
            return ResponseEntity.status(409).body("El medico ya existe");
        }
    }

    @PutMapping(value = "/medico", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateMedico(@RequestBody Medico medico) {
        try {
            medicoService.updateMedico(medico);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error updating medico", e);
            return ResponseEntity.internalServerError().body("Error al actualizar el medico");
        }
    }

    @DeleteMapping("/medico/{id}")
    public ResponseEntity<?> deleteMedico(@PathVariable("id") Long id) {
        try {
            Medico medico = medicoService.getMedico(id);
            log.debug("Deleting medico: {}", medico);
            medicoService.removeMedicoID(id);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting medico", e);
            return ResponseEntity.internalServerError().body("Error al eliminar el medico");
        }
    }

    // Buscar un medico por su dni
    @GetMapping("/medico/dni/{dni}")
    public ResponseEntity<Medico> getMedicoByDni(@PathVariable("dni") String dni) {
        Medico medico = medicoService.getMedicoByDni(dni);
        if (medico != null) {
            return ResponseEntity.ok(medico);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
