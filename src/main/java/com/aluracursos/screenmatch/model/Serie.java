package com.aluracursos.screenmatch.model;


//import com.aluracursos.screenmatch.service.ConsultaChatGPT;
import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;
    private String periodo;

    @Enumerated(EnumType.STRING)
    private Categoria genero;

    private String actores;
    private String sinopsis;
    private String poster;
    private double evaluacion;
    private Integer totalDeTemporadas;

    //@Transient
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios;

    //constructor vacío que es requerimiento de JPA para ejecutar el proceso de recuperación de datos desde DB
    public Serie(){}

    //constructor lleno
    public Serie(DatosSerie datosSerie) {

        titulo = datosSerie.Titulo();
        this.periodo = datosSerie.periodo();
        // Verifica si genero() devuelve null antes de llamar a split()
        String generoString = datosSerie.genero();
        if (generoString != null) {
            this.genero = Categoria.fromString(generoString.split(",")[0].trim());
        }
        this.actores = datosSerie.actores();
        try {
            //this.sinopsis = ConsultaChatGPT.traduce(datosSerie.sinopsis());
            this.sinopsis = datosSerie.sinopsis();
            if(this.sinopsis == null){
                this.sinopsis = datosSerie.sinopsis();
            }
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
            this.sinopsis = datosSerie.sinopsis();
        }
        this.poster = datosSerie.poster();
        this.evaluacion = OptionalDouble.of(Double.valueOf(datosSerie.evaluacion())).orElse(0.0);
        this.totalDeTemporadas = datosSerie.totalDeTemporadas();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        titulo = titulo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Categoria getGenero() {
        return genero;
    }

    public void setGenero(Categoria genero) {
        this.genero = genero;
    }

    public String getActores() {
        return actores;
    }

    public void setActores(String actores) {
        this.actores = actores;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(double evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Integer getTotalDeTemporadas() {
        return totalDeTemporadas;
    }

    public void setTotalDeTemporadas(Integer totalDeTemporadas) {
        this.totalDeTemporadas = totalDeTemporadas;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        //asigna a cada episodio la serie
        episodios.forEach(episodio -> episodio.setSerie(this));
        this.episodios = episodios;
    }

    @Override
    public String toString() {
        return  "SerieID: " + getId() +
                " genero=" + genero + " - " + "Titulo='" + titulo + '\'' +
                ", periodo='" + periodo + '\'' +
                ", actores='" + actores + '\'' +
                ", sinopsis='" + sinopsis + '\'' +
                ", poster='" + poster + '\'' +
                ", evaluacion=" + evaluacion +
                ", totalDeTemporadas=" + totalDeTemporadas +
                ", Episodios=" + episodios;
    }
}
