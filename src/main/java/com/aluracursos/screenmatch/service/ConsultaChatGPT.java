//package com.aluracursos.screenmatch.service;
//
//import com.theokanning.openai.completion.CompletionRequest;
//import com.theokanning.openai.service.OpenAiService;
//import retrofit2.HttpException;
//
//public class ConsultaChatGPT {
//    public static String traduce (String texto) {
//        try{
//        OpenAiService service = new OpenAiService(System.getenv("CHATGPT_PASS"));
//
//        CompletionRequest solicitud = CompletionRequest.builder()
//                .model("gpt-3.5-turbo-instruct")
//                .prompt("traduce al español el siguiente texto: " + texto)
//                .maxTokens(1000)
//                .temperature(0.7)
//                .build();
//        var respuesta = service.createCompletion(solicitud);
//        return respuesta.getChoices().get(0).getText();
//        } catch (HttpException e) {
//            // Manejo de la excepción HTTP 429 (Demasiadas solicitudes)
//            System.err.println("Error: Traducción no disponible. Por favor, inténtalo más tarde.");
//            return null; // Otra acción adecuada, como lanzar una excepción personalizada
//        } catch (Exception e) {
//            // Manejo de otras excepciones
//            System.err.println("Traducción no disponible.");
//            return null; // Otra acción adecuada, como lanzar una excepción personalizada
//        }
//    }
//}
