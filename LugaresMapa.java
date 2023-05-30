public enum LugaresMapa {
    CASA ("Casa", "Estas en tu casa. Nada como el hogar para descansar.\nLa necesidad de aventura te impulsa a salir."),
    CAMINO ("Camino", "El camino que te lleva al pueblo."),
    CRUCE ("Cruce", "Este cruce te permite ir a tu casa, al pueblo\no a la ladera del cerro."),
    LADERA ("Ladera", "En la ladera puedes ver la entrada a una caverna.\nEl arroyo con agua fresca completa el paisaje."),
    CUEVA ("Cueva", "Parace que la cueva es donde duerme un oso."),
    PUEBLO ("Pueblo", "Un pueblito tranquilo donde puedes conseguir\nlo que necesitas para tu aventura."),
    ALMACEN ("Almacen", "Este almacen posee varios elementos utiles para el viaje.")
    ;

    private final String nombre;
    private final String descripcion;

    private LugaresMapa (String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre () {
        return nombre;
    }
    
    public String getDescripcion () {
        return descripcion;
    }
    
}
