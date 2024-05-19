package com.aluracursos.screenmatch.repositoriy;

import com.aluracursos.screenmatch.model.Categoria;
import com.aluracursos.screenmatch.model.Episodio;
import com.aluracursos.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

        /* Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie); */
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();
    //List<Serie> findByGenero(Categoria categoria);
    //List<Serie> findByTotalDeTemporadasGreaterThanEqualAndGeneroOrderByEvaluacionDesc(int totalTemporadas, Categoria categoria);


    //personalizada (Pero no es tan flexible, porque usa lenguage SQL directo)
    //Si el rendimiento es una prioridad y necesitas un control detallado sobre tus consultas,
    // SQL Nativo puede ser la mejor opción.
    @Query(value = "SELECT * FROM series " +
            "WHERE series.total_de_temporadas <=6 " +
            "AND series.evaluacion >=7"
            , nativeQuery = true)
    List<Serie> seriesPorGeneroYEvaluacion();

    //Mas personalizada, para cuando no se sabe si la BD va a ser la misma siempre
    //Mayor flexibilidad/Abstraccion Usando JPQL; (se agrega la clase Serie con un alias s; atributos de la clase)
    //@Query("SELECT * FROM Serie s WHERE s.genero = :categoria AND s.evaluacion >= :evaluacion ORDER BY s.evaluacion DESC")
    @Query("SELECT s FROM Serie s WHERE s.genero = :categoria " +
            "AND s.evaluacion >= :evaluacion ORDER BY s.evaluacion DESC")
    List<Serie> seriesPorGeneroYEvaluacionJPQL(Categoria categoria, Double evaluacion);

    //Buscar episodios por serie
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:nombreEpisodio%")
    List<Episodio> episodiosPorNombre(String nombreEpisodio);

    //Buscar episodios por evaluación dentro de una serie

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.evaluacion DESC LIMIT 5")
    List<Episodio>top5Episodios(Serie serie);

    @Query("SELECT s FROM Serie s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.fechaDeLanzamiento) DESC LIMIT 5")
    List<Serie>episodiosMasRecientes();

}
