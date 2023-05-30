/**
 * Esta interface se utiliza para implementar funcionalidad a
 * los contenedores.  Como puede verse, este rol es transversal
 * a varias jerarquias de herencia.
 * 
 * En cada clase que implemente esta inteface se debe implementar
 * el patron "state" (relacionado con maquina de estados).
 * Este patron de diseño facilitar el uso, mantenimiento y
 * extension del software.
 * 
 * Ref. https://blogs.oracle.com/javamagazine/the-state-pattern
 * 
 * Ver clases Arroyo y Barrica como ejemplos.
 */

public interface Contenedor {
    /**
     * El contenedor recibe un elemento para agregar.
     * 
     * Solo admite el elemento indicado si se corresponde con el
     * tipo esperado a la clase que lo implementa y si se encuentra
     * en uno de los estados no lleno.
     * 
     * @param elemento El elemento a agregar.
     * @throws ContenedorLlenoException En caso que el contenedor no
     *         tenga lugar disponible.
     * @throws AccionNoPermitidaException En caso que el elemento no
     *         se corresponda con el contenedor.
     */
    public void addElemento (Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException;

    /**
     * El contenedor entrega un elemento.
     * 
     * @return El elemento a entregar.
     * @throws ContenedorVacioException En caso que el contenedor se
     *         encuentre vacio.
     */
    public Elemento getElemento () throws ContenedorVacioException,AccionNoPermitidaException;

    /**
     * El contenedor entrega un elemento.
     * 
     * @param nombre El nombre del objeto a entregar.
     * @return El elemento a entregar.
     * @throws AccionNoPermitidaException En caso que el contenedor
     *         no tenga almacenado el elemento solicitado.
     */
    public Elemento getElemento (String nombre) throws ContenedorVacioException, AccionNoPermitidaException;
}
