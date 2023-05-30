/**
 * Esta clase abstracta es una implementacion basica de la
 * interface Contenedor para facilitar su uso.
 * 
 * La implementacion del patron state en las clases que
 * implementan la interface Contenedor requiere definir los
 * estados. Heredando desde esta clase, solo hace falta definir
 * los metodos necesarios por sobreescritura y no su totalidad.
 * 
 * El estado inicial no permite el uso de las funcionalidades.
 */
public abstract class EstadoContenedor implements Contenedor {
    protected Boolean habilitado = false;

    @Override
    public void addElemento(Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException {
        throw new AccionNoPermitidaException("Contenedor no acepta elementos");        
    }

    @Override
    public Elemento getElemento() throws ContenedorVacioException,AccionNoPermitidaException {
        if (!habilitado) {
            throw new ContenedorVacioException("Contenedor no entrega elementos");
        }
        return null;
    }

    @Override
    public Elemento getElemento(String nombre) throws ContenedorVacioException, AccionNoPermitidaException {
        if (!habilitado) {
            throw new AccionNoPermitidaException("Contenedor no entrega elementos por nombre");
        }
        return null;
    }

}
