//package com.aluracursos.screenmatch.principal;
//
//import com.aluracursos.screenmatch.model.*;
//
//import com.aluracursos.screenmatch.repositoriy.SerieRepository;
//import com.aluracursos.screenmatch.service.ConsumoAPI;
//import com.aluracursos.screenmatch.service.ConvierteDatos;
//import org.springframework.dao.DataIntegrityViolationException;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//
//public class Principal {
//    private Scanner input = new Scanner(System.in);
//    private ConsumoAPI consumoAPI = new ConsumoAPI();
//    private ConvierteDatos conversor = new ConvierteDatos();
//
//    private final String URL_BASE = "https://www.omdbapi.com/?t=";
//    private final String API_KEY = "&apikey=" + System.getenv("DB_OMDB_PASS");
//
//
//    private List<DatosSerie> listaDeDatosSerie = new ArrayList<>();
//    private List<Serie> listaDeSeries = new ArrayList<>();
//    private Optional<Serie> serieBuscada;
//
//    private SerieRepository repositorio;
//
//    private String menu = """
//            Elija una opción:
//            1 - Buscar series y guardar
//            2 - Buscar episodios y guardar
//            3 - Mostrar series buscadas
//            4 - Buscar series por Título
//            5 - Top 5 Mejores series
//            6 - Mostrar su lista de series seleccionadas
//            7 - Mostrar nuestras recomendadas
//            8 - Busca Episodios por nombre
//            9 - Top 5 mejores episodios por serie
//            0 - salir
//            ->
//            """;
//
//    //Constructor de la clase Principal que recibe una instancia de la Interface SerieRepository
//    public Principal(SerieRepository repository) {
//        this.repositorio = repository;
//    }
//
//    public void muestraMenu() {
//        var opcion = -1;
//        while (opcion != 0) {
//            System.out.print(menu);
//            opcion = input.nextInt();
//            input.nextLine();
//
//            switch (opcion) {
//                case 1:
//                    buscarSerieWeb();
//                    break;
//                case 2:
//                    buscarEpisodioPorSerie();
//                    break;
//                case 3:
//                    mostrarSeriesGuardadasenBDD();
//                    break;
//                case 4:
//                    buscarSeriesPorTitulo();
//                    break;
//                case 5:
//                    top5MejoresSeries();
//                    break;
//                case 6:
//                    mostrarSeriesPorGeneroYEvaluacionJPQL();
//                    break;
//                case 7:
//                    mostrarNuestraSeleccion();
//                    break;
//                case 8:
//                    buscarEpisodiosPorTitulo();
//                    break;
//                case 9:
//                    top5MejoresEpisodios();
//                    break;
//                case 0:
//                    System.out.println("Hasta pronto!");
//                    break;
//                default:
//                    System.out.println("Ingrese una opción válida");
//            }
//        }
//    }
//
//
//
//    private DatosSerie getListaDatosSerie() {
//        System.out.print("Ingrese el nombre de la serie que desea buscar: ");
//        var nombreSerie = input.nextLine();
//        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
//        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
//        return datos;
//    }
//
//    public void buscarSerieWeb() {
//        try {
//            DatosSerie d = getListaDatosSerie();
//            if (d == null) {
//                throw new NullPointerException("No se ha encontrado la serie");
//            }
//            //para guardar en la lista de DatosSerie:
//            listaDeDatosSerie.add(d);
//            //para guardar en Base de Datos Series, por medio de la Interface SerieRepository
//            Serie serieEncontrada = new Serie(d);
//            repositorio.save(serieEncontrada);
//
//            System.out.println(listaDeDatosSerie);
//
//        } catch (NullPointerException e) {
//            System.out.println("Se produjo un error: " + e.getMessage());
//        } catch (IllegalArgumentException e) {
//            System.out.println("Se produjo un error: " + e.getMessage());
//        } catch (DataIntegrityViolationException e) {
//            System.out.println("El título buscado no es una serie y se ha producido un error: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Error inesperado" + e.getMessage());
//        }
//    }
//
//    public void buscarEpisodioPorSerie() {
//        mostrarSeriesGuardadasenBDD();
//        System.out.println("Elige una serie de la lista");
//        var nombreSerie = input.nextLine();
//        Optional<Serie> serie = listaDeSeries.stream()
//                .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
//                .peek(s -> System.out.println("Primer filtro (nombreSerie) - " + s))
//                .findFirst();
//
//        if (serie.isPresent()) {
//            var serieEncontrada = serie.get();
//            System.out.println("La serie encontrada es: " + serieEncontrada.getTitulo().toUpperCase());
//            List<DatosTemporada> temporadas = new ArrayList<>();
//            //System.out.println("La lista de temporadas de " + serieEncontrada.getTitulo() + " contiene " + temporadas.size() + " temporadas.");
//            System.out.println("");
//            for (int i = 1; i < serieEncontrada.getTotalDeTemporadas(); i++) {
//                var json = consumoAPI.obtenerDatos((URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&Season=" + i + API_KEY));
//                DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
//                temporadas.add(datosTemporada);
//                //System.out.println("La serie " + serieEncontrada.getTitulo().toUpperCase() + " contiene " + temporadas.size() + " temporadas.");
//            }
//            System.out.println("");
//            System.out.println("Información sobre las temporadas de: " + serieEncontrada.getId() + " - " + serieEncontrada.getTitulo());
//            temporadas.forEach(System.out::println);
//
//                List<Episodio> episodios = temporadas.stream()
//                        .flatMap(temporada -> temporada.episodios().stream()
//                                //.peek(episodio -> System.out.println("Primer mapeo " + episodio.titulo().toUpperCase()))
//                                .map(episodio -> new Episodio(temporada.numeroTemporada(), episodio)))
//                        //.peek(episodio -> System.out.println("Segundo mapeo " + episodio.getTitulo()))
//                        .collect(Collectors.toList());
//
//                serieEncontrada.setEpisodios(episodios);
//
//                repositorio.save(serieEncontrada);
//        }
//    }
//
//    public void mostrarSeriesGuardadasenBDD() {
//        listaDeSeries = repositorio.findAll();
//        listaDeSeries.stream()
//                .sorted(Comparator.comparing(Serie::getGenero))
//                .forEach(System.out::println);
//    }
//
//    private Optional<Serie> buscarSeriesPorTitulo() {
//        System.out.print("Por favor ingrese el nombre de la serie (o una parte del nombre): ");
//        String nombreSerie = input.nextLine();
//
//        serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);
//        if(serieBuscada.isPresent()){
//            System.out.println(serieBuscada.get().getId() + " - " + serieBuscada.get().getTitulo() + " [" + serieBuscada.get().getEvaluacion() + "]");
//            return serieBuscada;
//        } else {
//            System.out.println("No se ha encontrado una serie con ese nombre");
//        }
//        return null;
//    }
//
//    private void top5MejoresSeries(){
//    List<Serie> listaMejoresSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
//    listaMejoresSeries.forEach(s -> System.out.println(s.getTitulo().toUpperCase() + " [" + s.getEvaluacion() + "]."));
//    }
//
////    private void buscarSeriesPorCategoria(){
////        System.out.print("Por favor ingrese una categoría de serie: ");
////        String categoríaSerie = input.nextLine();
////        var categoria = Categoria.fromEspanol(categoríaSerie);
////
////        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria) ;
////        if(!seriesPorCategoria.isEmpty()){
////            System.out.println("Serie(s) de la categoría " + categoríaSerie);
////            seriesPorCategoria.forEach(s -> System.out.println(s.getTitulo().toUpperCase() + " [" + s.getEvaluacion() + "]."));
////        } else {
////            System.out.println("No se ha encontrado ninguna serie de esa categoría");
////        }
////    }
//
//    private void mostrarSeriesPorGeneroYEvaluacionJPQL(){
//        System.out.print("Ingrese el género de la series que desea ver: ");
//        String categoriaSerie = input.nextLine();
//        var categoria = Categoria.fromEspanol(categoriaSerie);
//
//        System.out.print("Ingrese la evaluación mínima: ");
//        Double evaluacion = input.nextDouble();
//
//        List<Serie> seriesSeleccionadas = repositorio.seriesPorGeneroYEvaluacionJPQL(categoria,evaluacion);
//        if(!seriesSeleccionadas.isEmpty()){
//            System.out.println("");
//            System.out.println("Nuestras series recomendadas");
//            seriesSeleccionadas.forEach(s -> System.out.println(s.getId() + " - " + s.getTitulo().toUpperCase() + " [" + s.getEvaluacion() + "]" + "\nPlot: " + s.getSinopsis() + "\nProtagonizada por: " + s.getActores() + "\n"));
//        } else {
//            System.out.println("No se ha encontrado una serie con esas características");
//        }
//    }
//
////    private void mostrarSeleccionadas(){
////        List<Serie> seriesSeleccionadas = repositorio.findByTotalDeTemporadasGreaterThanEqualAndGeneroOrderByEvaluacionDesc(5,Categoria.fromString("Crime"));
////        if(!seriesSeleccionadas.isEmpty()){
////            System.out.println("");
////            System.out.println("Nuestras series recomendadas");
////            seriesSeleccionadas.forEach(s -> System.out.println(s.getTitulo().toUpperCase() + " [" + s.getEvaluacion() + "]" + "\nPlot: " + s.getSinopsis() + "\nProtagonizada por: " + s.getActores() + "\n"));
////        } else {
////            System.out.println("No se ha encontrado una serie con ese nombre");
////        }
////    }
//
//        private void mostrarNuestraSeleccion(){
//        List<Serie> seriesSeleccionadas = repositorio.seriesPorGeneroYEvaluacion();
//        if(!seriesSeleccionadas.isEmpty()){
//            System.out.println("");
//            System.out.println("Nuestras series recomendadas");
//            seriesSeleccionadas.forEach(s -> System.out.println(s.getId() + " - " + s.getTitulo().toUpperCase() + " [" + s.getEvaluacion() + "]" + "\nPlot: " + s.getSinopsis() + "\nProtagonizada por: " + s.getActores() + "\n"));
//        } else {
//            System.out.println("No se ha encontrado una serie con esas características");
//        }
//    }
//
//
////    private void mostrarSeriesPorCategoriaYTemporadas(){
////        System.out.print("Ingrese el género de la series que desea ver: ");
////        String categoriaSerie = input.nextLine();
////        var categoria = Categoria.fromEspanol(categoriaSerie);
////
////        System.out.print("Ingrese la cantidad de temporadas mínima: ");
////        int cantidadDeTemporadas = input.nextInt(); input.next();
////
////        List<Serie> seriesSeleccionadas = repositorio.findByTotalDeTemporadasGreaterThanEqualAndGeneroOrderByEvaluacionDesc(cantidadDeTemporadas, categoria);
////        if(!seriesSeleccionadas.isEmpty()){
////            System.out.println("");
////            System.out.println("Nuestras series recomendadas");
////            seriesSeleccionadas.forEach(s -> System.out.println(s.getTitulo().toUpperCase() + " [" + s.getEvaluacion() + "]" + "\nPlot: " + s.getSinopsis() + "\nProtagonizada por: " + s.getActores() + "\n"));
////        } else {
////            System.out.println("No se ha encontrado una serie con ese nombre");
////        }
////    }
//
//    private void buscarEpisodiosPorTitulo() {
//        System.out.print("Ingrese el nombre del episodio a buscar: ");
//        String nombreEpisodio = input.nextLine();
//
//        List<Episodio> episodiosEncontrados = repositorio.episodiosPorNombre(nombreEpisodio);
//        if(!episodiosEncontrados.isEmpty()){
//            System.out.println("");
//            episodiosEncontrados.forEach(e ->
//                    System.out.printf("Serie: %s (S%d - E%d) %n Episodio: %s [%s]%n",
//                            e.getSerie().getTitulo(), e.getTemporada(), e.getNumero(), e.getTitulo(), e.getEvaluacion()));
//        } else {
//            System.out.println("No se ha encontrado una serie con esas características");
//        }
//        System.out.println("");
//    }
//
//    private void top5MejoresEpisodios() {
//        buscarSeriesPorTitulo();
//        if (serieBuscada.isPresent()){
//            Serie serie = serieBuscada.get();
//            List<Episodio> top5Episodios = repositorio.top5Episodios(serie);
//            top5Episodios.forEach(e ->
//                    System.out.printf("Serie: %s (S%d - E%d) - Episodio: %s [%s] %n",
//                            e.getSerie().getTitulo(), e.getTemporada(), e.getNumero(), e.getTitulo(), e.getEvaluacion()));
//        }
//
//    }
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
////Deprecado desde 12/5
////    public void buscarEpisodioPorDatosSerie() {
////        DatosSerie d = getListaDatosSerie();
////        List<DatosTemporada> temporadas = new ArrayList<>();
////
////        for (int i = 1; i < d.totalDeTemporadas(); i++) {
////            var json = consumoAPI.obtenerDatos(URL_BASE + d.Titulo().replace(" ", "+") + API_KEY);
////            DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
////            temporadas.add(datosTemporada);
////        }
////
////        temporadas.forEach(System.out::println);
////    }
//
////    public void mostrarSeriesBuscadas() {
////        List<Serie> series = new ArrayList<>();
////        series = listaDeDatosSerie.stream()
////                .map(d -> new Serie(d))
////                .collect(Collectors.toList());
////        series.stream()
////                .sorted(Comparator.comparing(Serie::getGenero))
////                .forEach(System.out::println);
////    }
//
//
//}
////DEPRECADO 09/05/2024
////    public void muestraMenu() {
////        System.out.print("Por favor escribe el nombre de la serie buscada: ");
////        var nombreSerie = String.valueOf(input.nextLine().toLowerCase().replace(" ", "+"));
////
////        //muestra los datos generales de la serie
////        var json = consumoAPI.obtenerDatos(URL_BASE + nombreSerie + API_KEY);
////        var datos = conversor.obtenerDatos(json, DatosSerie.class);
//////        System.out.println("");
//////        System.out.println("Información básica sobre " + datos.Titulo().toUpperCase());
//////        System.out.println(datos);
////
////        //muestra los datos de las temporadas:
////        List<DatosTemporada> temporadas = new ArrayList<>();
////        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
////            var json4 = consumoAPI.obtenerDatos(URL_BASE + nombreSerie + "&Season=" + i + API_KEY);
////            var datosTemporadas = conversor.obtenerDatos(json4, DatosTemporada.class);
////            temporadas.add(datosTemporadas);
////        }
//////        System.out.println("");
//////        System.out.println("Información de las " +datos.totalDeTemporadas() + " temporadas de " + datos.Titulo().toUpperCase());
//////        temporadas.forEach(System.out::println);
////
////        //mostrar sólo el título de los episodios para las temporadas
////        for (int i = 0; i < datos.totalDeTemporadas(); i++) {
////            List<DatosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
////            for (int j = 0; j < episodiosTemporada.size(); j++) {
////                System.out.println("Temporada " + (Integer.parseInt(String.valueOf(i)) + 1) +
////                        " - Episodio " + (Integer.parseInt(String.valueOf(j)) + 1) + " - "
////                        + episodiosTemporada.get(j).titulo());
////            }
////        }
////
////        //función lambda (equivalente a línea 38-43)
//////        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println("S" + t.numeroTemporada() + "E" + e.numeroEpisodio() + " - " + e.titulo())));
////
////        //Convertir las informaciones a una lista de DatosEpisodio
//////        List<DatosEpisodio> datosEpisodios = temporadas.stream()
//////                .flatMap(t -> t.episodios().stream()
//////                .map(e -> new DatosEpisodio(e.titulo(), e.numeroEpisodio(), String.valueOf(t.numeroTemporada()), e.evaluacion())))
//////                .collect(Collectors.toList());
////
////
////        //Mapear los datos de los episodios a partir de los datos temporadas
////        List<DatosEpisodio> datosEpisodios = temporadas.stream()
////                .flatMap(t -> t.episodios().stream()
////                        .map(e -> new DatosEpisodio(e.titulo(), e.numeroEpisodio(), String.valueOf(t.numeroTemporada()), e.evaluacion())))
////                .toList();
////
////        //Top 5 episodios - .peek permite ver lo que se va haciendo
//////        System.out.println("");
//////        System.out.println("TOP 5 episodios");
//////        datosEpisodios.stream()
//////                .filter(e-> !e.evaluacion().equalsIgnoreCase("N/A"))
//////                //.peek(e -> System.out.println("Primer filtro (N/A) - " + e))
//////                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//////                //.peek(e -> System.out.println("Segundo Ordenamiento (M a m) - " + e))
//////                .map(e -> e.titulo().toUpperCase())
//////                //.peek(e -> System.out.println("Tercer Mayúsculas - " + e))
//////                .limit(5)
//////                //.forEach(e -> System.out.println("S" + t.  + "E" + e.numeroEpisodio() + " - " + e.titulo() + " (" + e.evaluacion() + ")"));
//////                //.forEach(e -> System.out.println("E" + e.numeroEpisodio() + " - " + e.titulo() + " (" + e.evaluacion() + ")"));
//////                .forEach(System.out::println);
////
////
////
////        //Convirtiendo los datosEpisodio a una lista de episodios
////        List<Episodio> episodios = temporadas.stream()
////                .flatMap(t -> t.episodios().stream()
////                        .map(d -> new Episodio(t.numeroTemporada(), d)))
////                .toList();
////
//////        System.out.println("");
//////        System.out.println("Información de las " +datos.totalDeTemporadas() + " temporadas de " + datos.Titulo().toUpperCase());
//////        episodios.forEach(System.out::println);
////
////        //Filtrando episodios por fecha
//////        System.out.println(" ");
//////        System.out.println("Vamos a filtrar episodios...");
//////        System.out.print("a partir del año...");
//////        var fechaI = input.nextInt();
//////        input.nextLine();
//////        LocalDate fechaDeBusquedaI = LocalDate.of(fechaI, 1,1);
//////        System.out.print(", y hasta el año...");
//////        var fechaF = input.nextInt();
//////        input.nextLine();
//////        LocalDate fechaDeBusquedaF = LocalDate.of(fechaF, 12,31);
//////        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//////        episodios.stream()
//////                .filter(e -> e.getFechaDeLanzamiento() != null && e.getFechaDeLanzamiento().isAfter(fechaDeBusquedaI) && e.getFechaDeLanzamiento().isBefore(fechaDeBusquedaF))
//////                .forEach(e -> System.out.println(
//////                        "Temporada: " + e.getTemporada() +
//////                                " - Episodio: " + e.getTitulo() +
//////                                " (" + e.getFechaDeLanzamiento().format(dtf) + ")"
//////
//////                ));
////
////        //Búsqueda por parte del nombre del episodio
////        System.out.println("Vamos a buscar episodios por nombre...");
////        System.out.println("Ingrese al menos una parte del nombre del episodio buscado");
////        var parteDelNombreDeUnEpisodio = input.nextLine();
////
////        //versión que devuelve una lista de coincidencias
////        //Usar null es una práctica común, pero puede llevar a errores como NullPointerException.
////        List<Episodio> episodiosEncontrados = new ArrayList<>();
////        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
////        episodios.stream()
////                .filter(e -> e.getTitulo().toLowerCase().contains(parteDelNombreDeUnEpisodio.toLowerCase()))
////                .forEach(episodiosEncontrados::add);
////        if (!episodiosEncontrados.isEmpty()) {
////            System.out.println("Resultados de la búsqueda");
////            //episodiosEncontrados.forEach(System.out::println);
////            episodiosEncontrados.stream()
////                    .sorted(Comparator.comparing(Episodio::getEvaluacion).reversed())
////                    .forEach(e -> System.out.println(
////                    "Temporada: " + e.getTemporada() +
////                            " - Episodio: " + e.getTitulo() +
////                            " (" + e.getFechaDeLanzamiento().format(dtf) + ")" +
////                            " [" + e.getEvaluacion() + "]"
////            ));
////        } else {
////            System.out.println("No hubo coincidencias");
////        }
////
////        //versión con contenedor Optional (Puede contener un valor único o ningún valor.)
//////        Optional<Episodio> episodioBuscado = episodios.stream()
//////                .filter(e -> e.getTitulo().toLowerCase().contains(parteDelNombreDeUnEpisodio.toLowerCase()))
//////                .findFirst();
//////
//////        if (episodioBuscado.isPresent()) {
//////            System.out.println("Resultado de la búsqueda");
//////            System.out.println(episodioBuscado.get());
//////        } else {
//////            System.out.println("No hubo coincidencias");
//////        }
////
////        //Optional.ofNullable crea un Optional que contiene el nombre SI NO ES NULL. Si es null ->Optional vacío
//////        public Optional<String> getNombre() {
//////            // El nombre puede ser null
//////            return Optional.ofNullable(nombre);
//////        }
////        //Buenas Prácticas:
////        //Prefiera el retorno de Optional en lugar de devolver null: Esto hace que sus intenciones sean claras y evita errores.
////        //No use Optional.get() sin Optional.isPresent(): Optional.get() lanzará un error si el valor no está presente. Por lo tanto, es mejor verificar antes si el valor está presente.
////        //No use Optional para campos de la clase o parámetros del método: Optional debe usarse principalmente para retornos de métodos que pueden no tener valor.
//////        Optional<String> optionalNombre = getNombre();
//////        optionalNombre.ifPresent(System.out::println); // Imprimirá el nombre solo si no es null
//////        String nombre = optionalNombre.orElse("Nombre no disponible"); // Devolverá "Nombre no disponible" si nombre es null
////
////
////
////    }
////
////
////    //Intento para poder mostrar el número de la temporada a la que pertenece el episodio== sin éxito
//////        datosEpisodios.stream()
//////                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
//////                .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
//////                .limit(5)
//////                .forEach(e -> {
//////                    //para que pueda mostrar el número de la temporada a la que pertenece el episodio
//////                    DatosTemporada temporadaDelEpisodio = temporadas.stream()
//////                                    .filter(t -> t.episodios().contains(e)) //busca la temporada en cuya lista de episodios, esté contenido el episodio en cuestión
//////                                            .findFirst()
//////                                                    .orElseThrow(() -> new NoSuchElementException("No se encontró ninguna temporada que contenga el episodio " + e));; //maneja la excepción
//////                    System.out.println("S" + temporadaDelEpisodio.numeroTemporada() + "-E" + e.numeroEpisodio() + " - " + e.titulo() + " (" + e.evaluacion() + ")");
//////                });
//
//
