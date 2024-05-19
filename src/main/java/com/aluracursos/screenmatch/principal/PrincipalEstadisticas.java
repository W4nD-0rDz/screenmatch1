//package com.aluracursos.screenmatch.principal;
//
//import com.aluracursos.screenmatch.model.DatosEpisodio;
//import com.aluracursos.screenmatch.model.DatosSerie;
//import com.aluracursos.screenmatch.model.DatosTemporada;
//import com.aluracursos.screenmatch.model.Episodio;
//import com.aluracursos.screenmatch.service.ConsumoAPI;
//import com.aluracursos.screenmatch.service.ConvierteDatos;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//public class PrincipalEstadisticas {
//    private Scanner input = new Scanner(System.in);
//    private ConsumoAPI consumoAPI = new ConsumoAPI();
//    private final String URL_BASE = "https://www.omdbapi.com/?t=";
//    private final String API_KEY = "&apikey=59530ab5";
//    private ConvierteDatos conversor = new ConvierteDatos();
//
//    public void calculaEstadisticas() {
//        System.out.print("Por favor escribe el nombre de la serie buscada: ");
//        var nombreSerie = String.valueOf(input.nextLine().toLowerCase().replace(" ", "+"));
//
//        //captura los datos generales de la serie
//        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie + API_KEY);
//        var datos = conversor.obtenerDatos(json, DatosSerie.class);
//        System.out.println("");
//        System.out.println("Información básica sobre " + datos.Titulo().toUpperCase());
//        System.out.println(datos);
//
//        //muestra los datos de las temporadas:
//        List<DatosTemporada> temporadas = new ArrayList<>();
//        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
//            var json4 = consumoAPI.obtenerDatos(URL_BASE + nombreSerie + "&Season=" + i + API_KEY);
//            var datosTemporadas = conversor.obtenerDatos(json4, DatosTemporada.class);
//            temporadas.add(datosTemporadas);
//        }
//
//        //Mapear los datos de los episodios a partir de los datos temporadas
//        List<DatosEpisodio> datosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                        .map(e -> new DatosEpisodio(e.titulo(), e.numeroEpisodio(), String.valueOf(t.numeroTemporada()), e.evaluacion())))
//                .toList();
//
//        //Convirtiendo los datosEpisodio a una lista de episodios
//        List<Episodio> episodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream()
//                        .map(d -> new Episodio(t.numeroTemporada(), d)))
//                .toList();
//
//        //Calculando estadísticas
//        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
//                .filter(e->e.getEvaluacion() > 0.0)
//                .collect(Collectors.groupingBy(Episodio::getTemporada,
//                        Collectors.averagingDouble(Episodio::getEvaluacion)));
//        System.out.println(evaluacionesPorTemporada);
//
//        DoubleSummaryStatistics est = episodios.stream()
//                .filter(e->e.getEvaluacion() > 0.0)
//                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
//        //System.out.println(est);
//        System.out.println("Media de las evaluaciones: " + est.getAverage());
//        System.out.println("Episodio mejor evaluado: " + est.getMax());
//        System.out.println("Episodio peor evaluado: " + est.getMin());
//
//        DoubleSummaryStatistics estTemporada = evaluacionesPorTemporada.values().stream()
//                .mapToDouble(Double::doubleValue)
//                .summaryStatistics();
//        System.out.println("Media por temporada: " + estTemporada.getAverage());
//
//        //para obtener la temporada según el análisis de las estadísticas
//        Optional<Integer> mejorTemporada = evaluacionesPorTemporada.entrySet().stream()
//                .max(Map.Entry.comparingByValue())
//                .map(Map.Entry::getKey);
//
//
//        mejorTemporada.ifPresent(max -> System.out.println("Mejor Temporada: " + max));
//
//        //para obtener la peor temporada según el análisis de las estadísticas
//        Optional<Integer> peorTemporada = evaluacionesPorTemporada.entrySet().stream()
//                .min(Map.Entry.comparingByValue())
//                .map(Map.Entry::getKey);
//        peorTemporada.ifPresent(min -> System.out.println("Peor Temporada: " + min));
//
//
//
//
//
//    }
//
//
//}
