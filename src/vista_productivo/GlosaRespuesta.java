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

        btnbuscarid = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(1000, 1000), new java.awt.Dimension(1000, 1000), new java.awt.Dimension(1000, 1000));
        jPanel1 = new javax.swing.JPanel();
        panelencabezado = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtnumeroid = new javax.swing.JTextField();
        btnbuscarid1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        paneldetalles = new javax.swing.JPanel();
        lbnrespuesta = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableglosarespuesta = new javax.swing.JTable();
        panelglosa = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabledetalles = new javax.swing.JTable();
        lbndetalles = new javax.swing.JLabel();

        btnbuscarid.setText("Buscar");
        btnbuscarid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscaridActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(1000, 600));

        jPanel1.setPreferredSize(new java.awt.Dimension(1000, 600));
        jPanel1.setLayout(new java.awt.BorderLayout());

        panelencabezado.setBackground(new java.awt.Color(0, 153, 153));
        panelencabezado.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel1.setText("Ingrese el numero de id: ");

        btnbuscarid1.setText("Buscar");
        btnbuscarid1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarid1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtnumeroid, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnbuscarid1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(452, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtnumeroid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscarid1))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        panelencabezado.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel1.add(panelencabezado, java.awt.BorderLayout.PAGE_START);

        jPanel4.setPreferredSize(new java.awt.Dimension(1000, 200));
        jPanel4.setLayout(new java.awt.BorderLayout());

        paneldetalles.setPreferredSize(new java.awt.Dimension(1000, 300));

        lbnrespuesta.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        lbnrespuesta.setText("Respuesta glosa");

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jScrollPane2.setPreferredSize(new java.awt.Dimension(600, 100));

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

        javax.swing.GroupLayout paneldetallesLayout = new javax.swing.GroupLayout(paneldetalles);
        paneldetalles.setLayout(paneldetallesLayout);
        paneldetallesLayout.setHorizontalGroup(
            paneldetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldetallesLayout.createSequentialGroup()
                .addGroup(paneldetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneldetallesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbnrespuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paneldetallesLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 918, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        paneldetallesLayout.setVerticalGroup(
            paneldetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneldetallesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbnrespuesta)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(paneldetalles, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 64, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 141, Short.MAX_VALUE)
        );

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(600, 412));

        tabledetalles.setAutoCreateRowSorter(true);
        tabledetalles.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tabledetalles.setForeground(new java.awt.Color(255, 102, 102));
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
        tabledetalles.setGridColor(new java.awt.Color(0, 0, 204));
        jScrollPane1.setViewportView(tabledetalles);

        lbndetalles.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lbndetalles.setText("Detalles facturas");

        javax.swing.GroupLayout panelglosaLayout = new javax.swing.GroupLayout(panelglosa);
        panelglosa.setLayout(panelglosaLayout);
        panelglosaLayout.setHorizontalGroup(
            panelglosaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelglosaLayout.createSequentialGroup()
                .addGroup(panelglosaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelglosaLayout.createSequentialGroup()
                        .addComponent(lbndetalles, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(735, 735, 735))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelglosaLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 908, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelglosaLayout.setVerticalGroup(
            panelglosaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelglosaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelglosaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelglosaLayout.createSequentialGroup()
                        .addComponent(lbndetalles)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jPanel4.add(panelglosa, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnbuscaridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscaridActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnbuscaridActionPerformed

    private void btnbuscarid1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarid1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnbuscarid1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new GlosaRespuesta().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscarid;
    private javax.swing.JButton btnbuscarid1;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbndetalles;
    private javax.swing.JLabel lbnrespuesta;
    private javax.swing.JPanel paneldetalles;
    private javax.swing.JPanel panelencabezado;
    private javax.swing.JPanel panelglosa;
    private javax.swing.JTable tabledetalles;
    private javax.swing.JTable tableglosarespuesta;
    private javax.swing.JTextField txtnumeroid;
    // End of variables declaration//GEN-END:variables
}
