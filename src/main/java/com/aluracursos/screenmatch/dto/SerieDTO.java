package com.aluracursos.screenmatch.dto;

import com.aluracursos.screenmatch.model.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDTO(
        Long Id,
        String titulo,
        String periodo,
        Categoria genero,
        String actores,
        String sinopsis,
        String poster,
        Double evaluacion,
        Integer totalDeTemporadas) {
}
