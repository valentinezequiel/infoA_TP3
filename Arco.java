public class Arco extends Elemento implements Portable {
    private Flecha flecha;

    public Arco() {
        setNombre("Arco");
        setPeso(15);
    }

    /**
     * Devuelve la flecha asociada al arco, quedando
     * sin municion.
     * 
     * @return La flecha asociada al arco.
     */
    public Flecha getFlecha() {
        Flecha flecha = this.flecha;
        this.flecha = null;
        return flecha;
    }

    public void setFlecha(Flecha flecha) {
        this.flecha = flecha;
    }
    
}
