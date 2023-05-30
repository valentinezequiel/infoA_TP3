import java.util.TreeMap;

/**
 * Clase Habitacion - Una habitacion en un juego de aventuras
 *
 * Esta clase es parte de la apliciacion "World of Zuul". 
 * "World of Zuul" es un juego de aventuras sencillo basado en texto.  
 *
 * Un objeto "Habitacion" representa una ubicacion en el juego. Las
 * habitaciones tienen salidas que conducen a otras habitaciones, indicadas
 * como norte, sur, este y oeste. Para cada direccion, una habitacion 
 * mantiene una referencia a la habitacion vecina, o null si no existe una
 * en es direccion.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 2006.03.30
 */

public class Habitacion implements Contenedor
{
    // private String descripcion;
    // almacena las salidas de esta habitacion
    protected TreeMap<Salida, Habitacion> salidas;
    // almacena los elementos portables y decorado de la habitacion
    protected TreeMap<String, Elemento> elementos;
    private Liquido derramado;
    private EstadoContenedor estado;
    
/* *** Modificacion para el TP **************************** */
/* Informatica Avanzada - 2021 */
    // Esta version utiliza la informacion del enumerado LugaresMapa
    protected LugaresMapa lugar;
    
    /**
     * Crea una habitacion descrita por "descripcion". 
     * Inicialmente, la habitacion no tiene salidas, "descripcion"
     * es algo asi como "una cocina" o "un patio".
     * 
     * Se incorpora el String nombre y el TreeMap de elementos, el
     * liquido derramado y el estado para adaptarlo al TP de
     * Informatica Avanzada.
     * 
     * Se inicializa en estado vacio (ver Arroyo y Barrica a modo
     * de ejemplo de los estados)
     * 
     * @param lugar El nombre y la descripcion de la habitacion.
     */
    public Habitacion (LugaresMapa lugar) 
    {
        // this.descripcion = descripcion;
        salidas = new TreeMap<>();
        // TODO - Modificar metodo
    }

    /**
     * Devuelve una descripcion de la habitacion en la forma:
     * 
     *     El camino que te lleva al pueblo.
     *     Se ha derramado Agua en el piso.
     *     Salidas: norte oeste
     *     Elementos: Pan Pluma Flecha
     * 
     * MODIFICAR el metodo para que la descripcion:
     *  1) incluya la lista de elementos,
     *  2) si el campo derramado contiene un liquido, que
     *     agrege la linea "Se ha derramado <liquido> en el piso".
     * 
     * @return Una descripcion larga de esta habitacion
     */
    public String getDescripcionLarga()
    {
        // TODO - Modificar metodo
        // return "Usted esta en  " + descripcion + ".\n" + getStringDeSalidas()
        ;
        return null;
    }

    /**
     * Devuelve una cadena describiendo las salidas de la habitacion,
     * por ejemplo:
     *    "Elementos: Pan Pluma Flecha".
     * En caso de no haber elementos, devuelve la cadena:
     *    "No hay elementos en la habitacion"
     * 
     * El texto apropiado se obtiene del metodo toString() sobreescrito
     * en las clases privadas Vacia y ConElementos (estados).
     * 
     * @return Detalles de las salidas de la habitacion
     */
    protected String getStringDeElementos ()
    {
        // TODO - Implementar funcionalidad en las clases privadas
        return estado.toString();
    }

    /**
     * Devuelve la habitacion a la que se llega si vamos desde esta
     * habitacion en la direccion indicada. Si no hay habitacion en
     * esa direccion, lanza excepcion con el mensaje:
     *    "No hay salida en direccion <direccion>"
     * donde <direccion> es la direccion indicada.
     * 
     * @param direccion La direccion de salida
     * @return La habitacion en la direccion indicada
     */
    public Habitacion getSalida (Salida direccion) throws AccionNoPermitidaException
    {
        // TODO - Modificar metodo
        return salidas.get(direccion);
    }

/*
 ************* Patron State *************    
 */ 
    /**
     * Delegate methods para acceder a los metodos apropiados
     * en base al estado del objeto.
     * 
     * Agrega un elemento a la habitacion.
     * 
     * Si el elemento es de la familia de los liquidos, no se agrega
     * al mapa, sino al campo "derramado", y se debe cambiar la
     * descripcion de la habitacion.
     * 
     * @param elemento El elemento a agregar.
     * @throws ContenedorLlenoException No aplica.
     * @throws AccionNoPermitidaException No aplica.
     */
    @Override
    public void addElemento (Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException{
        estado.addElemento(elemento);
    }

    /**
     * Delegate methods para acceder a los metodos apropiados
     * en base al estado del objeto.
     * 
     * Brinda acceso a un elemento en la habitacion. Si el objeto es portable,
     * lo quita del mapa.  Si es decorado, devuelve la referencia sin quitarlo.
     * 
     * @param nombre El nombre del elemento a acceder.
     * @return El elemento a acceder.
     * @throws ContenedorVacioException En caso que la habitacion este vacia
     *               con el mensaje: "No hay elementos en la habitacion".
     * @throws AccionNoPermitidaException En caso que el elemento no exista
     *               con el mensaje: "No existe el elemento <nombre>"
     *               (<nombre> == nombre del elemento).
     */
    public Elemento getElemento (String nombre) throws ContenedorVacioException, AccionNoPermitidaException {
        return estado.getElemento(nombre);
    }
    /**
     * Se utiliza la version implementada en EstadoContenedor,
     * que siempre lanza la excepcion AccionNoPermitidaException.
     */    
    public Elemento getElemento () throws ContenedorVacioException, AccionNoPermitidaException {
        return estado.getElemento();
    }

    /**
     * Implementar los metodos de las clases privadas que sean
     * necesarios para cada estado.
     * 
     * No olvidar:
     *  1) contemplar las situaciones que producen el cambio a
     *     otro estado,
     *  2) sobreescribir el metodo toString() para reportar los
     *     elementos de la habitacion.
     * 
     * Ver lo implementado en Arroyo y Barrica. Considerar
     * las pautas en Botella.
     */

    /**
     * La clase VACIA indica el estado de la Habitacion sin elementos.
     * Debe cambiar al estado CONELEMENTOS al agregarse un elemento
     * no liquido.
     */
     // TODO - Implementar la clase privada
    private class Vacia extends EstadoContenedor {
    }

    /**
     * La clase CONELEMENTOS indica el estado de la Habitacion con
     * al menos un (1) elemento.
     * Debe cambiar al estado VACIA al quitarse el ultimo elemento.
     */
     // TODO - Implementar la clase privada
     private class ConElementos extends EstadoContenedor {
    }

/*
************* FIN Patron State ************* 
*/

    /**
     * Devuelve el nombre de la habitacion.
     * 
     * @return El nombre de la habitacion.
     */
    public String getNombre () {
        return lugar.getNombre();
    }

    /**
     * @return La descripcion corta de esta habitacion
     * (la que se definio en el constructor).
     */
    public String getDescripcionCorta()
    {
        return lugar.getDescripcion();
    }

    /**
     * Define las salidas de esta habitacion.
     * @param direccion La direccion de la salida
     * @param vecina  La habitacion a la cual esta salida conduce.
     */
    public void establecerSalida (Salida direccion, Habitacion vecina) 
    {
        salidas.put(direccion, vecina);
    }

    /**
     * Devuelve una cadena describiendo las salidas de la habitacion,
     * por ejemplo
     * 
     * "Salidas: norte oeste".
     * 
     * @return Detalles de las salidas de la habitacion
     */
    private String getStringDeSalidas ()
    {
        String cadena = "Salidas:";
        for(Salida salida : salidas.keySet()) {
            cadena += " " + salida;
        }
        return cadena;
    }

    public EstadoContenedor getEstado () {
        return estado;
    }
}

