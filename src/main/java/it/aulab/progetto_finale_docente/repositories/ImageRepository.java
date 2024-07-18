package it.aulab.progetto_finale_docente.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import it.aulab.progetto_finale_docente.models.Image;
import jakarta.transaction.Transactional;

public interface ImageRepository extends JpaRepository<Image,Long> {
    @Transactional
    void deleteByPath(String imageUrl);
}