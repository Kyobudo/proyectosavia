
package vista;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class detallesglosa extends javax.swing.JFrame {
DefaultTableModel mt = new DefaultTableModel();



   
    public detallesglosa(String numeroFactura, String nit) {
        initComponents(); 
        String[] arreglo_colunm = {"Detalle glosa", "Motivo glosa", "Valor inicial","Tecnologia","numero_factura","nit"};//llena los encabezados de la tabla con el array
        mt.setColumnIdentifiers(arreglo_colunm);
        tabladetalles.setModel(mt);
        buscarDatos(numeroFactura,nit);
      
    }
     
    public void buscarDatos(String numeroFactura, String nit){
        
        //System.out.print(nit+numero_factura);
        Connection con = conexiones_a_BD.ConexionPreProductivo.getConnection();//conecta a la BD
        if (con != null) {
            String query = "SELECT * FROM motivoGlosa2 WHERE numero_factura= ? AND nit = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, numeroFactura);
                pst.setString(2, nit);              
                ResultSet rs = pst.executeQuery();
                
                mt.setRowCount(0);//limpia las filas de la tabla
                 System.out.println( numeroFactura+nit);
                if (rs.next()) {
                    do {
                        Object[] rowData = {        
                            
                            
                            rs.getString("detalleglosa"),                            
                            rs.getString("motivoglosa"),
                            rs.getInt("valorinicial"),
                            rs.getString("tecnologia"),
                            rs.getString("numero_factura"),
                            rs.getString("nit")
                            
                        };
                      mt.addRow(rowData);
                    } while (rs.next());
                     System.out.println("Número de filas en la tabla después de agregar datos: " + mt.getRowCount());
                     System.out.println("Número de columnas en la tabla después de agregar datos: " + mt.getColumnCount());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron resultados", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al realizar la consulta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        lbldetalleglosa = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabladetalles = new javax.swing.JTable();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lbldetalleglosa.setText("Detalle glosa preproductivo");

        tabladetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        jScrollPane1.setViewportView(tabladetalles);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(lbldetalleglosa, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lbldetalleglosa, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

 
  public static void main(String args[]) {      
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            
            new detallesglosa("valorFactura", "valorNit").setVisible(true);
        }
    });
}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbldetalleglosa;
    private javax.swing.JTable tabladetalles;
    // End of variables declaration//GEN-END:variables
}
