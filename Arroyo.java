public class Arroyo extends Recipiente implements NoPortable {
    private EstadoContenedor estado;
    
    public Arroyo() {
        setNombre("Arroyo de agua fresca");
        setPeso(1000);
        estado = new Lleno();
    }    

    /**
     * Delegate methods para acceder a los metodos apropiados
     * en base al estado del objeto.
     * 
     * @return Objeto tipo Agua pero como Elemento.
     * @throws ContenedorVacioException No aplica porque es una
     *               fuente infinita.
     * @throws AccionNoPermitidaException En caso de querer aplicar
     *               los metodos addElemento o getElemento(nombre).
     */
    public Elemento getAgua () throws ContenedorVacioException, AccionNoPermitidaException  {
        return estado.getElemento();
    }
    public Elemento getElemento () throws ContenedorVacioException, AccionNoPermitidaException  {
        return estado.getElemento();
    }
    /**
     * Se utiliza la version implementada en EstadoContenedor,
     * que siempre lanza la excepcion AccionNoPermitidaException.
     */    
    public void addElemento (Elemento elemento) throws ContenedorLlenoException, AccionNoPermitidaException  {
        estado.addElemento(elemento);
    }
    /**
     * Se utiliza la version implementada en EstadoContenedor,
     * que siempre lanza la excepcion AccionNoPermitidaException.
     */    
    public Elemento getElemento (String nombre) throws ContenedorVacioException, AccionNoPermitidaException  {
        return estado.getElemento(nombre);
    }
    
 /*
 ************* Patron State *************    
 */ 
    private class Lleno extends EstadoContenedor {
        /**
         * Devuelve un objeto de tipo agua.
         * 
         * @return Objeto tipo Agua pero como Elemento.
         * @throws ContenedorVacioException No aplica porque es una
         *               fuente infinita.
         * @throws AccionNoPermitidaException No aplica.
         */
        @Override
        public Elemento getElemento() throws ContenedorVacioException, AccionNoPermitidaException {
            return new Agua();
        }

        /**
         * Devuelve un string indicando el estado actual.
         * 
         * @return El estado actual como cadena.
         */
        @Override
        public String toString() {
            return "Lleno";
        }
    } 
/*
************* FIN Patron State ************* 
*/

    public EstadoContenedor getEstado () {
        return estado;
    }

}
