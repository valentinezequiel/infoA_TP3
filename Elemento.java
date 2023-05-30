/**
 * Modela elementos que representan objetos a utilizar.
 */
public abstract class Elemento {
    private Integer peso;
    private String nombre;

    /**
     * Constructor sin parámetros.
     */
    public Elemento () {}

    /**
     * Construye un objeto de tipo elemento con un nombre a eleccion,
     * el peso correspondiente y el tipo de elemento.
     * 
     * @param nombre El nombre del elemento.
     * @param peso El peso del elemento.
     * @param tipo El tipo de elemento.
     */
    public Elemento (String nombre, Integer peso) {
        this.nombre = nombre;
        this.peso = peso;
    }

    /**
     * Devuelve el peso del elemento.
     * 
     * @return El peso del elemento.
     */
    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    /**
     * Devuelve el nombre del elemento.
     * 
     * @return El nombre del elemento.
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el elemento como texto.
     * 
     * @return El texto que representa al elemento.
     */
    @Override
    public String toString() {
        return getNombre();
    }

}