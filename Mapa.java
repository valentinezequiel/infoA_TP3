import java.util.Map;
import java.util.TreeMap;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * Genera el mapa con habitaciones conectadas y
 * objetos en ellas.
 * 
 * Implementa el patron sigleton para evitar
 * multiples instancias.
 */

public class Mapa {
    private static Mapa mapa;
    private Map<String, Habitacion> habitaciones;
    private Habitacion inicio;

    /**
     * El constructor es privado para que no se pueda
     * instanciar por fuera.
     */
    private Mapa () {
        habitaciones = new TreeMap<>();
        generarHabitaciones();
        configurarConexiones();
    }

    /**
     * Este metodo devuelve el Mapa.  Si no se encuentra
     * instanciado, se lo crea.
     * 
     * @return El mapa desde la posicion inicial.
     */
    public static Mapa getInstance() {
        if (mapa == null) {
            mapa = new Mapa();
        }
        return mapa;
    }

    public Habitacion getInicio () {
        return inicio;
    }

    /**
     * Crea las habitaciones que conforman el mapa.
     */
    private void generarHabitaciones () {
        for (LugaresMapa lugar : LugaresMapa.values()) {
            Habitacion hab = new Habitacion(lugar);
            habitaciones.put(lugar.getNombre(), hab);
        }
        inicio = habitaciones.get("Casa");
    }

    /**
     * Configura la salidas de cada habitacion.
     * 
     * Se utiliza reflection para invocar los metodos
     * en base al nombre de la habitacion.
     */
    private void configurarConexiones () {
        for (Habitacion h : habitaciones.values()) {
            try {
                Method method = this.getClass().getDeclaredMethod("config" + h.getNombre(), Habitacion.class);
                method.setAccessible(true);
                method.invoke(this, h);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
            }
        }
    }

/* *** Los siguientes metodos configuran las habitaciones *** */

    private void configCasa (Habitacion h) {
        try {
			h.addElemento(new Botella());
			h.addElemento(new Arco());
			h.addElemento(new Carcaj());
			h.addElemento(new Pan());
        } catch (ContenedorLlenoException | AccionNoPermitidaException e) {
			throw new IllegalStateException(e.getMessage());
		}
        h.establecerSalida(Salida.NORTE, habitaciones.get("Camino"));
    }

    private void configCamino (Habitacion h) {
        h.establecerSalida(Salida.SUR, habitaciones.get("Casa"));
        h.establecerSalida(Salida.OESTE, habitaciones.get("Cruce"));
    }

    private void configCruce (Habitacion h) {
        try {
        	h.addElemento(new Pluma());
        } catch (ContenedorLlenoException | AccionNoPermitidaException e) {
			throw new IllegalStateException(e.getMessage());
		}
        h.establecerSalida(Salida.ESTE, habitaciones.get("Camino"));
        h.establecerSalida(Salida.OESTE, habitaciones.get("Ladera"));
        h.establecerSalida(Salida.NORTE, habitaciones.get("Pueblo"));
    }

    private void configLadera (Habitacion h) {
        try { 
        	h.addElemento(new Arroyo());
        } catch (ContenedorLlenoException | AccionNoPermitidaException e) {
			throw new IllegalStateException(e.getMessage());
		}
        h.establecerSalida(Salida.ESTE, habitaciones.get("Cruce"));
        h.establecerSalida(Salida.OESTE, habitaciones.get("Cueva"));
    }

    private void configCueva (Habitacion h) {
        try {
        	h.addElemento(new Hueso());
        } catch (ContenedorLlenoException | AccionNoPermitidaException e) {
			throw new IllegalStateException(e.getMessage());
		}
        h.establecerSalida(Salida.ESTE, habitaciones.get("Ladera"));
    }

    private void configPueblo (Habitacion h) {
        h.establecerSalida(Salida.SUR, habitaciones.get("Cruce"));
        h.establecerSalida(Salida.OESTE, habitaciones.get("Almacen"));
    }

    private void configAlmacen (Habitacion h) {
        Botella b = new Botella();
        try {
        	b.llenarBotella(new Vino());
            h.addElemento(b);
            // h.addElemento(new Caldero("Caldero chico", 3));
            // h.addElemento(new Caldero("Caldero mediano", 5));
            // h.addElemento(new Caldero("Caldero grande", 8));
        } catch (ContenedorLlenoException | AccionNoPermitidaException e) {
			throw new IllegalStateException(e.getMessage());
		}
        h.establecerSalida(Salida.ESTE, habitaciones.get("Pueblo"));
    }

}
