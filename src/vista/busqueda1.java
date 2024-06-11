package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class busqueda1 extends JFrame {
   DefaultTableModel mt = new DefaultTableModel();    
   

   public busqueda1() {
      
        initComponents(); 
        String[] arreglo_colunm_integra = {"Nit", "Numero factura", "Valor Factura","Total glosa","Total glosa","Total glosa","Total glosa","Total glosa","Total glosa","Total glosa","Total glosa","Total glosa","Total glosa"};//llena los encabezados de la tabla con el array
        mt.setColumnIdentifiers(arreglo_colunm_integra);
        tableintegra.setModel(mt);
         
          btnbuscar.addActionListener(new ActionListener() {//activa la accion en boton buscar
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarDatos();
                }
            });
            }
   
           public void buscarDatos() {
            String  numeroFactura  = txtfnumero_factura.getText();
            String nit= txtfnit.getText();

            if (numeroFactura.isEmpty() || nit.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese todos los datos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
        Connection con = conexiones_a_BD.ConexionPreProductivo.getConnection();//conecta a la BD
        if (con != null) {
            
            String query = "select * from cm_facturas where numero_facturado= ? and nit=? ";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, numeroFactura);
                pst.setString(2, nit);              
                ResultSet rs = pst.executeQuery();
                
                mt.setRowCount(0);//limpia las filas de la tabla
                System.out.println( numeroFactura+nit);
                if (rs.next()) {
                    do {
                        Object[] rowData = {
                            rs.getString("nit"),
                            rs.getString("numero_facturado"),
                            rs.getInt("tipo_auditoria"),
                            rs.getInt("marcacion")
                            
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

        conexion1 = new conexiones_a_BD.ConexionPreProductivo();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        btndetalleglosa = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblnumero_factura = new javax.swing.JLabel();
        txtfnumero_factura = new javax.swing.JTextField();
        lblnit = new javax.swing.JLabel();
        txtfnit = new javax.swing.JTextField();
        btnbuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableintegra = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btndetalleglosa.setText("Ver detalle glosa");
        btndetalleglosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndetalleglosaActionPerformed(evt);
            }
        });
        jPanel1.add(btndetalleglosa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, -1, -1));

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblnumero_factura.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lblnumero_factura.setText("Ingrese el numero de factura:");
        lblnumero_factura.setMaximumSize(new java.awt.Dimension(158, 16));
        jPanel3.add(lblnumero_factura, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 190, 30));
        jPanel3.add(txtfnumero_factura, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 149, -1));

        lblnit.setText("Ingrese en NT:");
        jPanel3.add(lblnit, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));
        jPanel3.add(txtfnit, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 20, 137, -1));

        btnbuscar.setText("Buscar");
        jPanel3.add(btnbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 20, 96, -1));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 70));

        tableintegra.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tableintegra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16", "Title 17", "Title 18", "Title 19", "Title 20", "Title 21", "Title 22", "Title 23", "Title 24", "Title 25", "Title 26", "Title 27", "Title 28", "Title 29", "Title 30", "Title 31", "Title 32", "Title 33"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableintegra.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableintegra.setAutoscrolls(false);
        tableintegra.setGridColor(new java.awt.Color(51, 51, 51));
        tableintegra.setMaximumSize(new java.awt.Dimension(2147483647, 23));
        tableintegra.setMinimumSize(new java.awt.Dimension(495, 23));
        tableintegra.setRowHeight(3);
        tableintegra.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tableintegra.setShowGrid(true);
        tableintegra.getTableHeader().setResizingAllowed(false);
        tableintegra.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableintegra);
        if (tableintegra.getColumnModel().getColumnCount() > 0) {
            tableintegra.getColumnModel().getColumn(0).setResizable(false);
            tableintegra.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableintegra.getColumnModel().getColumn(1).setResizable(false);
            tableintegra.getColumnModel().getColumn(1).setPreferredWidth(50);
            tableintegra.getColumnModel().getColumn(2).setResizable(false);
            tableintegra.getColumnModel().getColumn(2).setPreferredWidth(50);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 740, 140));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 863, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(267, 267, 267)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btndetalleglosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndetalleglosaActionPerformed
        String numeroFactura = txtfnumero_factura.getText();
        String nit = txtfnit.getText();
         detallesglosa detal = new detallesglosa(numeroFactura, nit); // Pasa los valores ingresados por el usuario
        detal.setVisible(true);          
                
    }//GEN-LAST:event_btndetalleglosaActionPerformed

   
  public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new busqueda1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton btndetalleglosa;
    private conexiones_a_BD.ConexionPreProductivo conexion1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblnit;
    private javax.swing.JLabel lblnumero_factura;
    private javax.swing.JTable tableintegra;
    private javax.swing.JTextField txtfnit;
    private javax.swing.JTextField txtfnumero_factura;
    // End of variables declaration//GEN-END:variables
}
