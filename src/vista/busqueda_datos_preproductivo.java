
package vista;


public class busqueda_datos_preproductivo {
    private JTextField txtfnumero_factura;
    private JTextField txtfnit;
    private DefaultTableModel mt_preproductivo;

    public BusquedaDatosProductivo(JTextField txtfnumero_factura, JTextField txtfnit, DefaultTableModel mt_preproductivo) {
        this.txtfnumero_factura = txtfnumero_factura;
        this.txtfnit = txtfnit;
        this.mt_preproductivo = mt_preproductivo;
    }

    public void buscarDatosProductivo() {
        String numeroFactura = txtfnumero_factura.getText();
        String nit = txtfnit.getText();

        // Resto del código para buscar los datos en la base de datos preproductivo
    }
}



