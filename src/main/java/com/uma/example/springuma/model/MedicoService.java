package com.uma.example.springuma.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicoService {

    private final RepositoryMedico repositoryMedico;

    @Autowired
    public MedicoService(RepositoryMedico repositoryMedico) {
        this.repositoryMedico = repositoryMedico;
    }

    public List<Medico> getAllMedicos() {
        return repositoryMedico.findAll();
    }

    public Medico getMedico(Long id) {
        return repositoryMedico.findById(id).orElseThrow(() -> new ResourceNotFoundException("Medico not found: " + id));
    }

    public Medico addMedico(Medico medico) {
        return repositoryMedico.saveAndFlush(medico);
    }

    public void updateMedico(Medico medico) {
        repositoryMedico.save(medico);
    }

    public void removeMedico(Medico medico) {
        repositoryMedico.delete(medico);
    }

    public void removeMedicoID(Long id) {
        repositoryMedico.deleteById(id);
    }

    public Medico getMedicoByDni(String dni) {
        return repositoryMedico.getMedicoByDni(dni);
    }
}
