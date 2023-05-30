public class Barrica extends Recipiente implements NoPortable {
    private EstadoContenedor estado;
    private Integer cargas;
    
    public Barrica () {
        cargas = 5;
        setNombre("Barrica de vino");
        setPeso(1000);
        estado = new Llena();
    }

/*
 ************* Patron State *************    
 */ 
    /**
     * Delegate methods para acceder a los metodos apropiados
     * en base al estado del objeto.
     * 
     * @return Objeto tipo Vino pero como Elemento.
     * @throws ContenedorVacioException En caso que se haya vaciado
     *               con el mensaje "Barrica sin vino".
     * @throws AccionNoPermitidaException En caso de querer aplicar
     *               los metodos addElemento o getElemento(nombre).
     */
    public Elemento getVino () throws ContenedorVacioException, AccionNoPermitidaException  {
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
    
    private class Llena extends EstadoContenedor {
        /**
         * Devuelve un objeto de tipo vino.
         * 
         * @return Objeto tipo Vino pero como Elemento.
         * @throws ContenedorVacioException No aplica en este estado.
         * @throws AccionNoPermitidaException No aplica.
         */
        @Override
        public Elemento getElemento() throws ContenedorVacioException, AccionNoPermitidaException {
            cargas -= 1;
            estado = new ConCarga();
            return new Vino();
        }

        /**
         * Devuelve un string indicando el estado actual.
         * 
         * @return El estado actual como cadena.
         */
        @Override
        public String toString() {
            return "Llena";
        }
    } 

    private class ConCarga extends EstadoContenedor {
        /**
         * Devuelve un objeto de tipo vino.
         * 
         * @return Objeto tipo Vino pero como Elemento.
         * @throws ContenedorVacioException No aplica en este estado.
         * @throws AccionNoPermitidaException No aplica.
         */
        @Override
        public Elemento getElemento() throws ContenedorVacioException, AccionNoPermitidaException {
            cargas -= 1;
            if (cargas == 0) {
                estado = new Vacia();
            }
            return new Vino();
        }

        /**
         * Devuelve un string indicando el estado actual.
         * 
         * @return El estado actual como cadena.
         */
        @Override
        public String toString() {
            return "Con vino";
        }
    } 

    private class Vacia extends EstadoContenedor {
        /**
         * Por encontrarse vacia, siempre lanza excepcion.
         * 
         * @return No se alcanza en este estado.
         * @throws ContenedorVacioException Lanza siempre la excepcion
         *               ṕor encontrarse vacia.
         * @throws AccionNoPermitidaException No aplica.
         */
        @Override
        public Elemento getElemento() throws ContenedorVacioException, AccionNoPermitidaException {
            if (!super.habilitado) {
                throw new ContenedorVacioException("Barrica sin vino");
            }
            return null;
        }

        /**
         * Devuelve un string indicando el estado actual.
         * 
         * @return El estado actual como cadena.
         */
        @Override
        public String toString() {
            return "Vacia";
        }
    } 

/*
************* FIN Patron State ************* 
*/

    public EstadoContenedor getEstado () {
        return estado;
    }
}
