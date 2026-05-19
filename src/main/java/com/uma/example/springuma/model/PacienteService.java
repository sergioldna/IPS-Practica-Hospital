package com.uma.example.springuma.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    private final RepositoryPaciente repositoryPaciente;

    @Autowired
    public PacienteService(RepositoryPaciente repositoryPaciente) {
        this.repositoryPaciente = repositoryPaciente;
    }

    public List<Paciente> getAllPacientes() {
        return repositoryPaciente.findAll();
    }

    public Paciente getPaciente(Long id) {
        return repositoryPaciente.findById(id).orElseThrow(() -> new ResourceNotFoundException("Paciente not found: " + id));
    }

    public Paciente addPaciente(Paciente paciente) {
        return repositoryPaciente.saveAndFlush(paciente);
    }

    public void updatePaciente(Paciente paciente) {
        repositoryPaciente.save(paciente);
    }

    public void removePaciente(Paciente paciente) {
        repositoryPaciente.delete(paciente);
    }

    public void removePacienteID(Long id) {
        repositoryPaciente.deleteById(id);
    }

    public List<Paciente> getPacientesMedico(Long id) {
        return repositoryPaciente.findByMedicoId(id);
    }
}
