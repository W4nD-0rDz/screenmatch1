package com.aluracursos.screenmatch.service;

import com.aluracursos.screenmatch.dto.SerieDTO;
import com.aluracursos.screenmatch.model.Serie;
import com.aluracursos.screenmatch.repositoriy.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class SerieService {
    @Autowired
    private SerieRepository repository;

    public List<SerieDTO> obtenerTodasLasSeries(){
        return convierteDatos(repository.findAll());
    }

    public List<SerieDTO> obtenerTop5() {
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDTO> obtenerLanzamientosMasRecientes(){
        return convierteDatos(repository.episodiosMasRecientes());
    }

    public SerieDTO obtenerPorId(long id){
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDTO(s.getId(), s.getTitulo(), s.getPeriodo(), s.getGenero(),  s.getActores(), s.getSinopsis(),
                    s.getPoster(), s.getEvaluacion(), s.getTotalDeTemporadas());
        }
            return null;
    }

    private List<SerieDTO> convierteDatos(List<Serie> series){
        return series.stream()
                .map(s->new SerieDTO(s.getId(), s.getTitulo(), s.getPeriodo(),
                        s.getGenero(),  s.getActores(), s.getSinopsis(),
                        s.getPoster(), s.getEvaluacion(), s.getTotalDeTemporadas()))
                .collect(Collectors.toList());
    }
}
