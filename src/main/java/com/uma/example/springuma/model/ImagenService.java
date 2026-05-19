package com.uma.example.springuma.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.uma.example.springuma.utils.ImageUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImagenService {

    private final RepositoryImagen repositoryImagen;

    @Autowired
    public ImagenService(RepositoryImagen repositoryImagen) {
        this.repositoryImagen = repositoryImagen;
    }

    public List<Imagen> getAllImagenes() {
        return repositoryImagen.findAll();
    }

    public Imagen getImagen(Long id) {
        return repositoryImagen.findById(id).orElseThrow(() -> new ResourceNotFoundException("Imagen not found: " + id));
    }

    public String getNewPrediccion(Long id) throws IOException, Exception {
        double score_0 = Math.random();
        double score_1 = Math.random();
        String resulString;
        if (score_0 > score_1) {
            resulString = "{'status': 'Not cancer',  'score': " + score_0 + "}";
        } else {
            resulString = "{'status': 'Cancer',  'score': " + score_1 + "}";
        }
        return resulString;
    }

    public Imagen addImagen(Imagen imagen) {
        return repositoryImagen.saveAndFlush(imagen);
    }

    public void updateImagen(Imagen imagen) {
        repositoryImagen.save(imagen);
    }

    public void removeImagen(Imagen imagen) {
        repositoryImagen.delete(imagen);
    }

    public void removeImagenByID(Long id) {
        repositoryImagen.deleteById(id);
    }

    public List<Imagen> getImagenesPaciente(Long id) {
        return repositoryImagen.getByPacienteId(id);
    }

    public String uploadImage(MultipartFile file, Paciente paciente) throws IOException {
        Imagen imagen = new Imagen();
        imagen.setNombre(file.getOriginalFilename());
        imagen.setFileContent(ImageUtils.compressImage(file.getBytes()));
        imagen.setPaciente(paciente);
        imagen.setFecha(LocalDateTime.now());
        imagen = repositoryImagen.saveAndFlush(imagen);
        if (imagen != null) {
            return "{\"response\" : \"file uploaded successfully : " + file.getOriginalFilename() + "\"}";
        }
        return null;
    }

    public byte[] downloadImage(long id) throws IOException {
        Imagen dbImageData = repositoryImagen.findById(id).orElseThrow(() -> new ResourceNotFoundException("Imagen not found: " + id));
        byte[] images = ImageUtils.decompressImage(dbImageData.getFileContent());
        return images;
    }

}
