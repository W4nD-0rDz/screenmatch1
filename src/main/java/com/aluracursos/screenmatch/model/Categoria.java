package com.aluracursos.screenmatch.model;

public enum Categoria {

    ACCION ("Action", "Acción"),
    BIOPIC ("Biography", "Biografía"),
    COMEDIA ("Comedy", "Comedia"),
    DRAMA ("Drama", "Drama"),
    MISTERIO ("Mistery", "Misterio"),
    POLICIAL ("Crime", "Policial"),
    ROMANCE ("Romance", "Romance"),
    CORTO ("Short", "Corto"),
    TERROR ("Horror", "Terror");


        private String  categoriasOmdb;
        private String categoriaEspanol;

    Categoria (String categoriasOmdb, String categoriaEspanol){
        this.categoriasOmdb = categoriasOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }
    //Para transformar la categoría de OMDB , si coinciden, devuelve la del Enum, sino manda el error.
    public static Categoria fromString(String text){
        for(Categoria categoria : Categoria.values()){
            if(categoria.categoriasOmdb.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoría encontrada: " + text);
    }

    public static Categoria fromEspanol(String text){
        for(Categoria categoria : Categoria.values()){
            if(categoria.categoriaEspanol.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna categoría encontrada: " + text);
    }




}
