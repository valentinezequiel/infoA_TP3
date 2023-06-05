/**
 * Esta clase modela un personaje de un juego de rol.
 */
public class Personaje {
    private final Integer MAX_VIDA;
    private final Integer PESO_MAXIMO_BOLSA;
    // nombre -> el nombre del personaje
    private String nombre;
    // vida -> valor actual de vida
    private Integer vida;
    // objeto -> el elemento que tiene en sus manos
    //           Puede tener las manos vacias (null)
    private Elemento objeto;
    // bolsa -> la Bolsa de elementos
    private Bolsa bolsa;
    // El lugar donde se encuentra el personaje
    private Habitacion habitacion;

    /**
     * Crea un personaje con el nombre dado y configura los
     * campos segun vida, MAX_VIDA y PESO_MAXIMO_BOLSA.
     * 
     * El personaje se inicializa sin bolsa y con las manos vacias
     * en la habitacion inicial del mapa (getInicio()).
     * 
     * @param nombre El nombre del personje.
     * @param vida El valor inicial y maximo de vida del personaje.
     * @param peso El peso maximo que puede transportar el personaje.
     */
    public Personaje (String nombre, Integer vida, Integer peso) {
        // TODO - Implementar constructor
        this.nombre=nombre;
        MAX_VIDA=vida;
        this.vida=vida;
        PESO_MAXIMO_BOLSA=peso;
        objeto=null;
        bolsa=null;
        habitacion=Mapa.getInstance().getInicio();

    }

    /**
     * Imprime en pantalla la informacion sobre el lugar
     * donde se encuentra.
     */
    public void mirarAlrededor () {
        System.out.println(habitacion.getDescripcionLarga());
    }

    /**
     * El personaje se mueve a la habitacion en la direccion indicada.
     * Si la direccion no es valida, se queda donde estaba y lo indica
     * imprimiendo el mensaje que trae la excepcion lanzada por habitacion.
     * 
     * @param direccion Direccion por donde salir de la habitacion.
     */
    public void irHacia (Salida direccion) {
        // TODO - Implementar metodo
        
        try {
            setHabitacion(habitacion.getSalida(direccion));
        } catch (AccionNoPermitidaException e) {
            
            e.printStackTrace();
        }
    } 

    /**
     * Guarda el elemento que tiene en sus manos en la bolsa, siempre
     * y cuando haya lugar suficiente.  Las manos quedan vacias (null).
     * 
     * Si las manos estan vacias (null), mostrar el mensaje
     *     "No hay elemento para agregar a la bolsa"
     * 
     * Si no hay bolsa, mostrar "<nombre>: No tiene bolsa"
     * donde <nombre> es el nombre del personaje.
     * 
     * En caso de no poder guardarse el elemento, mostrar el
     * mensaje que trae la excepcion.
     */
    public void guardarElemento() {
        // TODO - Implementar metodo
        if(objeto==null){
            System.out.println("No hay elemento para agregar a la bolsa");
        }
        else if(bolsa==null){
            System.out.println(this.getNombre()+": No tiene bolsa");
        }
        else{
            try {
                bolsa.addElemento(objeto);
                objeto=null;
            } catch (ContenedorLlenoException | AccionNoPermitidaException e) {
              
                e.printStackTrace();
            }
        }
        
    }

    /**
     * Toma un elemento de la bolsa (getElemento) y lo pone
     * en las manos del personaje.
     * 
     * Si tiene un elemento en sus manos, imprimir "Manos ocupadas".
     * 
     * Si no hay bolsa, mostrar "<nombre>: No tiene bolsa"
     * donde <nombre> es el nombre del personaje.
     * 
     * Si no existe el elemento se debe imprimir
     *       "No se cuenta con el <nombre>"
     * donde <nombre> es el nombre del elemento buscado.
     * 
     * Si la bolsa no tiene elementos (vacia), imprime el mensaje
     * que trae la excepcion.
     * 
     * @param nombre El elemento a tomar de la bolsa.
     */
    public void tomarElemento (String nombre)  {
        // TODO - Implementar metodo
        if(objeto!=null){
            System.out.println("Manos ocupadas");
        }
        else if(bolsa==null){
            System.out.println(this.getNombre()+": No tiene bolsa");
        }
        else{            
            try {
                objeto=bolsa.getElemento(nombre);
            } catch (ContenedorVacioException e) {
                
                e.printStackTrace();
            } catch (AccionNoPermitidaException e) {
                System.out.println("No se cuenta con el " + nombre);
            }
        }
    }
    
    /**
     * Toma de la habitacion el elemento indicado con sus manos. 
     * De ser necesario, debe guardar en la bolsa lo que tenga
     * en sus manos.
     *
     * Si el elemento no existe o no se puede tomar, debe seguir
     * sosteniendo el elemento que tenia en sus manos.
     * 
     * En caso de no poder tomar el objeto, mostrar el mensaje que
     * trae la excepcion.
     * 
     * @param nombre Nombre del objeto a recoger de la habitacion.
     */
    public void recogerElemento (String nombre) {
        // TODO - Implementar metodo
        try {
            Elemento ElementoRecogido=habitacion.getElemento(nombre);
            
            if(objeto!=null){
                guardarElemento();//el objeto de las manos a la bolsa
            }            
            objeto=ElementoRecogido;//el objeto de la habitacion a las manos
        } catch (ContenedorVacioException | AccionNoPermitidaException e) {
           
            e.printStackTrace();
        }
        
        
    }

    /**
     * Deja en la habitacion el elemento que tiene en sus manos.
     * Las manos quedan vacias (null).
     * 
     * Si las manos estan vacias imprime el mensaje:
     *      "No hay objeto para dejar"
     */
    public void dejarElemento () {
        // TODO - Implementar metodo
        try {
            habitacion.addElemento(objeto);
            objeto=null;
        } catch (ContenedorLlenoException e) {
            
            e.printStackTrace();
        } catch (AccionNoPermitidaException e) {
            System.out.println("No hay objeto para dejar");
        }
    }


    /**
     * Establece la nueva bolsa para el personaje.
     * 
     * @param bolsa La nueva bolsa del personaje.
     */
    public void setBolsa(Bolsa bolsa) {
        if (bolsa.getPesoMaximo() > PESO_MAXIMO_BOLSA) {
            System.out.println("Bolsa inapropiada");
        }
        else {
            this.bolsa = bolsa;
        }
    }

    /**
     * Devuelve la bolsa del personaje.
     * 
     * @return La bolsa del personaje.
     */
    public Bolsa getBolsa() {
        return bolsa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getVida() {
        return vida;
    }

    public void setVida(Integer vida) {
        this.vida = vida;
    }

    public void resetVida(Integer vida) {
        this.vida = MAX_VIDA;
    }

    public Elemento getElemento () {
        return objeto;
    }

    public void setElemento (Elemento objeto) {
        this.objeto = objeto;
    }
    
    public void setHabitacion(Habitacion habitacion) {
    	this.habitacion=habitacion;  	
    }
    public Habitacion getHabitacion(){
        return habitacion;
    }
}
