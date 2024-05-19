package com.aluracursos.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


//excluye los datos no mapeados
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
        //para lectura y escritura = @JsonProperty
        //solo para lectura = @JsonAlias
        @JsonAlias("Title") String Titulo,
        @JsonAlias("Year") String periodo,
        @JsonAlias("Genre") String genero,
        @JsonAlias("Actors") String actores,
        @JsonAlias("Plot") String sinopsis,
        @JsonAlias("Poster") String poster,
        @JsonAlias("imdbRating") String evaluacion,
        @JsonAlias("totalSeasons") Integer totalDeTemporadas
        ) {
}
