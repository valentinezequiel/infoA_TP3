public enum Salida {
    NORTE("norte"),
    SUR("sur"),
    ESTE("este"),
    OESTE("oeste");

    private String direccion;

    private Salida(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return direccion;
    }
    
}
