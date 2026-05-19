package com.uma.example.springuma.model;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class InformeService {

    private static final Logger log = LoggerFactory.getLogger(InformeService.class);

    private final RepositoryInforme repositoryInforme;

    @Autowired
    public InformeService(RepositoryInforme repositoryInforme) {
        this.repositoryInforme = repositoryInforme;
    }

    public List<Informe> getAllInformes() {
        return repositoryInforme.findAll();
    }

    public Informe getInforme(Long id) {
        return repositoryInforme.findById(id).orElse(null);
    }

    public Informe addInforme(Informe informe) throws IOException, Exception {
        String neew_pred = getNewPrediccion(informe);
        log.debug("New prediction: {}", neew_pred);
        informe.setPrediccion(((neew_pred)));

        return repositoryInforme.saveAndFlush(informe);
    }

    public void updateInforme(Informe informe) throws IOException, Exception {
        Informe existingInforme = repositoryInforme.findById(informe.getId()).orElse(null);
        if (existingInforme != null) {
            existingInforme.setPrediccion(getNewPrediccion(existingInforme));
            existingInforme.setContenido(informe.getContenido());
            repositoryInforme.save(existingInforme);
        }
    }

    public void removeInforme(Informe informe) {
        repositoryInforme.delete(informe);
    }

    public void removeInformeByID(Long id) {
        repositoryInforme.deleteById(id);
    }

    public List<Informe> getInformesImagen(Long id) {
        return repositoryInforme.findByImagenId(id);
    }

    public String getNewPrediccion(Informe informe) throws IOException, Exception {
        /*Map<String, Double> response =  ImagenAPIPredictor
        .query(ImageUtils.decompressImage(repositoryImage.getReferenceById(informe.getImagen().getId())
        .getFile_content()));
        //informe.setPrediccion((String)response.get("0"));
        System.out.println("resp");
        System.out.println( response);
        double score_0 = response.get("LABEL_0");
        double score_1 = response.get("LABEL_1");*/

        double score_0 = Math.random();
        double score_1 = Math.random();
        String resulString;
        if (score_0 > score_1) {
            resulString = "{\"status\": \"Not cancer\",  \"score\": " + score_0 + "}";
        } else {
            resulString = "{\"status\": \"Cancer\",  \"score\": " + score_1 + "}";
        }
        return resulString;
    }

}
