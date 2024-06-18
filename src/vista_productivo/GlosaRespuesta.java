package vista_productivo;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GlosaRespuesta extends javax.swing.JFrame {

    DefaultTableModel mtglosa = new DefaultTableModel();
    DefaultTableModel mtdetalles = new DefaultTableModel();

    public GlosaRespuesta() {
        initComponents();
        
        
        String[] arregloTablaGlosa = {"Id", "cm facturas id", "Valor cobro detalle", "Valor facturado", "Valor pagado", "Valor pagado EPS", "Valor pendiente", "Valor aceptado IPS",
            "Observacion", "Fecha de creacion"};
        mtglosa.setColumnIdentifiers(arregloTablaGlosa);
        tableglosarespuesta.setModel(mtglosa);
        int[] tamamo_columnas = {80, 150, 200, 200, 200, 200, 200, 200, 300, 200};
        TableColumnModel modeloColumnasGlosa = tableglosarespuesta.getColumnModel();
        for (int i = 0; i < tamamo_columnas.length; i++) {
            TableColumn columna = modeloColumnasGlosa.getColumn(i);
            columna.setPreferredWidth(tamamo_columnas[i]);
            columna.setHeaderRenderer(new CustomTableHeaderRenderer());
        }
        
        
        
        String[] arregloTablaDetalles = {"Id", "cm facturas id","Estado", "Tipo documento", "Documento", "Nombre usuario", "Radicado glosa", "Valor copago", "Valor facturado", "Valor pagado",
            "Valor pendiente", "Valor pendiente actual","Valor aceptado IPS", "Observacion","Valor pagado EPS", "Porcentaje pagado EPS", "Porcentaje aceptado IPS","Observacion respuesta",
            "Fecha de prestacion","Valor glosa","Valor pagado EPS","Valor pendiente","Motivo glosa"};
        mtdetalles.setColumnIdentifiers(arregloTablaDetalles);
        tabledetalles.setModel(mtdetalles);        
        int[] tamamoColumnasDetalles = {80,150,200,200,200,200,200,200,300,200,200,200,200,200,300,300,200,200,200,200,200,300,200};
        TableColumnModel modeloColumnasDetalles = tabledetalles.getColumnModel();
        for (int i = 0; i < tamamoColumnasDetalles.length; i++) {
            TableColumn columna = modeloColumnasDetalles.getColumn(i);
            columna.setPreferredWidth(tamamoColumnasDetalles[i]);
            columna.setHeaderRenderer(new CustomTableHeaderRenderer());
        }       

        //tableglosarespuesta.setShowGrid(true);
        //tableglosarespuesta.setGridColor(Color.BLACK);
        tableglosarespuesta.setDefaultRenderer(Object.class, new CustomTableCellRenderer());

        btnbuscarid.addActionListener(new ActionListener() {//activa la accion en boton buscar

            @Override

            public void actionPerformed(ActionEvent e) {
                String auxiliar = txtnumeroid.getText();
                if (auxiliar.isEmpty()) {
                    JOptionPane.showMessageDialog(GlosaRespuesta.this, "Por favor ingrese un número de Id", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int id = Integer.parseInt(auxiliar);
                    buscarDatosGlosa(id);
                    buscarDatosDetalles(id);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(GlosaRespuesta.this, "Ingrese un número válido para el Id", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

    }

    public class CustomTableHeaderRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBackground(Color.GRAY);
            setForeground(Color.WHITE);
            setFont(new Font("Verdana", Font.BOLD, 12));
            setHorizontalAlignment(SwingConstants.CENTER);
            return this;
        }
    }

    public class CustomTableCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (isSelected) {
                cell.setBackground(table.getSelectionBackground());
                cell.setForeground(table.getSelectionForeground());
            } else {
                cell.setBackground(Color.WHITE);
                cell.setForeground(Color.BLACK);
            }
            return cell;
        }
    }

    public void buscarDatosGlosa(int numeroid) {
        Connection con = conexionesBD.ConexionProductivo.getConnection();
        if (con != null) {
            String query = "SELECT * FROM cm_glosa_respuestas WHERE cm_facturas_id = ? ";
            try ( PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, numeroid);
                ResultSet rs = pst.executeQuery();
                mtglosa.setRowCount(0);
                System.out.println(numeroid);
                if (rs.next()) {
                    do {
                        Object[] rowData = {
                            rs.getInt("id"),
                            rs.getInt("cm_facturas_id"),                            
                            rs.getDouble("valor_cobro_detalle"),
                            rs.getDouble("valor_facturado"),
                            rs.getDouble("valor_pagado"),
                            rs.getDouble("vaor_pagado_eps"),
                            rs.getDouble("valor_pendiente"),
                            rs.getDouble("valor_aceptado_ips"),
                            rs.getString("observacion"),
                            rs.getString("fecha_hora_crea")
                            
                            

                        };
                        mtglosa.addRow(rowData);
                    } while (rs.next());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron resultados en la tabla glosa respuesta", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al realizar la consulta en BD Pre productiva ", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
     public void buscarDatosDetalles(int numeroid) {
        Connection con = conexionesBD.ConexionProductivo.getConnection();
        if (con != null) {
            String query = "SELECT * FROM cm_detalles WHERE cm_facturas_id = ? ";
            try ( PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, numeroid);
                ResultSet rs = pst.executeQuery();
                mtdetalles.setRowCount(0);
      
                if (rs.next()) {
                    do {
                        Object[] rowData = {
                            
                            rs.getInt("id"),
                            rs.getInt("cm_facturas_id"),
                            rs.getInt("estado"),
                            rs.getString("mae_tipo_documento_valor"),
                            rs.getString("documento"),
                            rs.getString("nombre_completo_afiliado"),
                            rs.getInt("radicado_glosa"),
                            rs.getDouble("valor_copago"),
                            rs.getDouble("valor_facturado"),
                            rs.getDouble("valor_pagado"),                         
                            rs.getDouble("valor_pendiente"),                            
                            rs.getDouble("valor_pendiente_actual"),                            
                            rs.getDouble("valor_aceptado_ips"),
                            rs.getString("observacion"),
                            rs.getDouble("valor_pagado_eps"),
                            rs.getDouble("porcentaje_pagado_eps"),
                            rs.getDouble("porcentaje_aceptado_ips"),
                            rs.getString("observacion_respuesta_detalles"),
                            rs.getString("fecha_prestacion"),
                            rs.getDouble("valor_glosa"),                           
                            rs.getDouble("valor_pagado_eps"),
                            rs.getDouble("valor_pendiente"),
                            rs.getDouble("motivo_glosa")
                            
                       

                        };
                        mtdetalles.addRow(rowData);
                    } while (rs.next());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron resultados en la tabla glosa respuesta", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al realizar la consulta en BD Pre productiva ", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } 
    
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtnumeroid = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnbuscarid = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lbldetalleglosa = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableglosarespuesta = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabledetalles = new javax.swing.JTable();
        lbndetalles = new javax.swing.JLabel();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        txtnumeroid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnumeroidActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel1.setText("Ingrese el numero del Id: ");

        btnbuscarid.setText("Buscar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtnumeroid, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84)
                .addComponent(btnbuscarid, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(497, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnumeroid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnbuscarid))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jPanel3.setAutoscrolls(true);
        jPanel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        lbldetalleglosa.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lbldetalleglosa.setText("Respuestas glosa");

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        tableglosarespuesta.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tableglosarespuesta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableglosarespuesta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(tableglosarespuesta);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1078, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lbldetalleglosa, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(lbldetalleglosa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tabledetalles.setAutoCreateRowSorter(true);
        tabledetalles.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tabledetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16", "Title 17", "Title 18", "Title 19", "Title 20", "Title 21", "Title 22", "Title 23", "Title 24"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabledetalles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabledetalles.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabledetalles.setGridColor(new java.awt.Color(204, 204, 204));
        jScrollPane1.setViewportView(tabledetalles);

        lbndetalles.setText("Detalles facturas");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1086, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(lbndetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(lbndetalles)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtnumeroidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnumeroidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnumeroidActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new GlosaRespuesta().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscarid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbldetalleglosa;
    private javax.swing.JLabel lbndetalles;
    private javax.swing.JTable tabledetalles;
    private javax.swing.JTable tableglosarespuesta;
    private javax.swing.JTextField txtnumeroid;
    // End of variables declaration//GEN-END:variables
}
