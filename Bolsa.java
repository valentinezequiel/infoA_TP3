import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/** 
 * Modela la bolsa que utiliza el Personaje para
 * transportar elementos. La capacidad se determina por
 * el peso maximo que soporta.
 * 
 * Todos los objetos se incluyen en un mapa.
 */

public class Bolsa implements Contenedor {
    private final int PESO_MAXIMO;
    private int pesoActual;
    private String nombre;
    private Map<String, Elemento> porNombre;
    private EstadoContenedor estado;

    /**
     * Constructor con parámetros
     * Inicializa las variables de instancia
     * 
     * No olvidar construir el mapa de elementos (TreeMap), ni
     * setear el peso inicial de la bolsa 
     * 
     * @param nombre El nombre de la bolsa.
     * @param peso El peso maximo que puede contener la bolsa
     */
    public Bolsa (String nombre, int peso) {
        this.nombre = nombre;
        PESO_MAXIMO = peso;
        pesoActual = 0;
        porNombre = new TreeMap<>();
        estado = new Vacia();
    }

/*
 ************* Patron State ************* 
 */
    /**
     * Delegate methods para acceder a los metodos apropiados
     * en base al estado del objeto.

     * Agrega un elemento a la bolsa y actualiza el peso total.
     * 
     * No se puede agregar un elemento si 
     *         - su peso excede el limite de la bolsa . Lanza ContenedorLlenoException
     *         - existe un elemento con el mismo nombre . Lanza AccionNoPermitidaException
     * 
     * @param elemento El elemento a agregar en la bolsa.
     * @throws AccionNoPermitidaException
     * @throws ContenedorLlenoException
     */
    @Override
    public void addElemento (Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException {
        estado.addElemento(elemento);
    }

    /**
     * Quita un objeto de la bolsa por su nombre y actualiza
     * el peso total.
     * 
     * @param nombre El nombre del elemento a quitar de la bolsa.
     * @return el elemento eliminado.
     * @throws ContenedorVacioException Cuando no hay elementos en la bolsa.
     * @throws AccionNoPermitidaException Cuando no existe el elemento a remover.
     */
    @Override
    public Elemento getElemento (String nombre) throws ContenedorVacioException, AccionNoPermitidaException {
        return estado.getElemento(nombre);
    }
    @Override
    public Elemento getElemento() throws ContenedorVacioException, AccionNoPermitidaException {
        return estado.getElemento();
    }

    private class Vacia extends EstadoContenedor {     
        @Override
        public void addElemento (Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException {
            if (elemento.getPeso() > getPesoLibre()){
                throw new ContenedorLlenoException(nombre + ": capacidad insuficiene para " + elemento.getNombre());
            }
            else {
                try {
                    Portable p = (Portable)elemento;
                    porNombre.put(elemento.getNombre(), elemento);
                    addPeso(elemento.getPeso());
                    estado = new ConElementos();
                } catch (ClassCastException e) {
                    throw new AccionNoPermitidaException(nombre + ": " + elemento.getNombre() + " no es portable");
                }
            }
        }

        @Override
        public Elemento getElemento (String nombre) throws ContenedorVacioException, AccionNoPermitidaException{
            throw new ContenedorVacioException("Bolsa vacia");
        }

    }

    private class ConElementos extends EstadoContenedor {     
        @Override
        public void addElemento (Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException {
            if (porNombre.containsKey(elemento.getNombre())) {
                throw new AccionNoPermitidaException(nombre +": No se puede agregar " + elemento.getNombre());
            }
            else if (elemento.getPeso() > getPesoLibre()){
                throw new ContenedorLlenoException(nombre + ": capacidad insuficiene para " + elemento.getNombre());
            }
            else {
                try {
                    Portable p = (Portable)elemento;
                    porNombre.put(elemento.getNombre(), elemento);
                    addPeso(elemento.getPeso());
                    if (getPesoLibre() == 0) {
                        estado = new Llena();
                    }
                } catch (ClassCastException e) {
                    throw new AccionNoPermitidaException(nombre + ": " + elemento.getNombre() + " no es portable");
                }
            }
        }

        @Override
        public Elemento getElemento (String nombre) throws ContenedorVacioException, AccionNoPermitidaException{
           if (!porNombre.containsKey(nombre)) {
               throw new AccionNoPermitidaException("No existe " + nombre);
           }
           Elemento tmp = porNombre.remove(nombre);
           addPeso(-tmp.getPeso());
           if (porNombre.size() == 0) {
               estado = new Vacia();
           }
           return tmp;
       }
    }

    private class Llena extends EstadoContenedor {
        public void addElemento (Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException {
            throw new ContenedorLlenoException("Bolsa llena");
        }
  
        @Override
        public Elemento getElemento (String nombre) throws ContenedorVacioException, AccionNoPermitidaException{
            if (!porNombre.containsKey(nombre)) {
                throw new AccionNoPermitidaException("No existe " + nombre);
            }
            Elemento tmp = porNombre.remove(nombre);
            addPeso(-tmp.getPeso());
            estado = new ConElementos();
            return tmp;
       }
    }
/*
 ************* FIN Patron State ************* 
 */


    /**
     * Devuelve una lista con los elementos almacenados en la bolsa.
     * 
     * @return List<Elemento> lista con los elementos de la bolsa.
     */
    public List<Elemento> getListaElementosEnLaBolsa() {
        return new ArrayList<Elemento>(porNombre.values());
    }

    /**
     * Devuelve la lista de elementos almacenados en la bolsa
     * cuyo nombre comienza con el prefijo indicado.
     * 
     * @param pre El prefijo a buscar
     * @return List<Elemento> lista con los elementos de la bolsa que 
     * cumplen con el criterio.
     */
    public List<Elemento> getElementosConPrefijo(String pre) {
        ArrayList<Elemento> lista = new ArrayList<>();
        for (String e : porNombre.keySet()) {
            if (e.startsWith(pre)) {
                lista.add(porNombre.get(e));
            }
        }
        return lista;
    }

    /**
     * Devuelve la lista de elementos almacenadas en la bolsa
     * segun el tipo indicado.
     * 
     * @param tipo El tipo de elemento a listar.
     * @return List<Elemento> lista con las armas de la bolsa.
     */
    // public List<Elemento> getElementosPorTipo(TipoElemento tipo) {
    //     ArrayList<Elemento> lista = new ArrayList<>();
    //     for (Elemento e : porNombre.values()) {
    //         if (e.getTipo().equals(tipo)) {
    //             lista.add(e);
    //         }
    //     }
    //     return lista;
    // }

    /**
     * Devuelve el peso disponible que aún puede almacenarse 
     * en la bolsa en un momento dado. 
     * Cambia según se agregan o quitan objetos.
     * 
     * @return El peso máximo a agregar.
     */
    public int getPesoLibre () {
        return PESO_MAXIMO - pesoActual;
    }

    /**
     * Incrementa el peso total almacenado en la bolsa.
     * Se utiliza para agregar como para quitar objetos.
     *
     *   @param peso El peso a quitar/agregar.
     */
    public void addPeso (int peso) {
        setPesoActual(getPesoActual() + peso);
    }

    public int getPesoActual() {
        return pesoActual;
    }

    public void setPesoActual(int pesoActual) {
        this.pesoActual = pesoActual;
    }

    public String getKeySet () {
        return porNombre.keySet().toString();
    }

    public int getPesoMaximo () {
        return PESO_MAXIMO;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public Map<String,Elemento> getMapaDeElementos(){
        return porNombre;
    }

    public EstadoContenedor getEstado () {
        return estado;
    }

}
