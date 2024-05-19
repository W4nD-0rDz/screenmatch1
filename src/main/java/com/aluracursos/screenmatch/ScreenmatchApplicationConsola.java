//package com.aluracursos.screenmatch;
//
//
//import com.aluracursos.screenmatch.principal.Principal;
//import com.aluracursos.screenmatch.repositoriy.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//
//@SpringBootApplication
//public class ScreenmatchApplicationConsola implements CommandLineRunner {
//
//	@Autowired //para que Spring haga la inyección de dependencias de manera autónoma
//	private SerieRepository repository;
//
//	public static void main(String[] args) {
//		SpringApplication.run(ScreenmatchApplicationConsola.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//
//		Principal principal = new Principal(repository);
//		principal.muestraMenu();
//
//
//
//
////		var consumoApi = new ConsumoAPI();
////		ConvierteDatos conversor = new ConvierteDatos();
////		System.out.println("Hola Mundo desde Spring!");
////		var json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=X-files&Season=1&Episode=1&apikey=59530ab5");
////		System.out.println(json);
////		var json1 = consumoApi.obtenerDatos("https://coffee.alexflipnote.dev/random.json");
////		System.out.println(json1);
////		System.out.println("Información Serie");
////		var json2 = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=X-files&apikey=59530ab5");
////		var datos = conversor.obtenerDatos(json2, DatosSerie.class);
////		System.out.println(datos);
////		System.out.println("");
////		System.out.println("Informacion Temporadas");
////		List<DatosTemporada> temporadas = new ArrayList<>();
////		for (int i = 1; i <=datos.totalDeTemporadas(); i++) {
////			var json4 = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=X-files&Season=" + i + "&apikey=59530ab5");
////			var datosTemporadas = conversor.obtenerDatos(json4, DatosTemporada.class);
////			temporadas.add(datosTemporadas);
////		}
////		temporadas.forEach(System.out::println);
////
////		System.out.println("");
////		System.out.println("Información Temporada 1");
////		var json3 = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=X-files&Season=1&apikey=59530ab5");
////		DatosTemporada temporada = conversor.obtenerDatos(json3, DatosTemporada.class);
////		System.out.println(temporada);
////		List<DatosEpisodio> episodiosTemporada1 = new ArrayList<>();
////		for (int i = 1; i <= temporada.episodios().size() ; i++) {
////			var json5 = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=X-files&Season=1&Episode=" + i + "&apikey=59530ab5");
////			var datosTemporada = conversor.obtenerDatos(json5, DatosEpisodio.class);
////			episodiosTemporada1.add(datosTemporada);
////		}
////		episodiosTemporada1.forEach(System.out::println);
////
////
////		System.out.println("");
////		System.out.println("Información Episodio 1");
////		var json4 = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=X-files&Season=1&Episode=1&apikey=59530ab5");
////		DatosEpisodio episodio = conversor.obtenerDatos(json4, DatosEpisodio.class);
////		System.out.println(episodio);
//
//	}
//
//
//
//
//
//
//
//
//
//
//
//
//}
