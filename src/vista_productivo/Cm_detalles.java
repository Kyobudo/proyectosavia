package vista_productivo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Getset.GetSet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class Cm_detalles extends javax.swing.JFrame {

    DefaultTableModel mt = new DefaultTableModel();

    //talbla auditoria motivos glosa
    public Cm_detalles() {
        initComponents();
        String[] arreglo_colunm = {"id", "cm_facturas_id", "Tipo documento", "Documento", "Nomnre", "Radicado glosa", "Valor facturado", "Valor initario",
            "Valor pagado", "Valor pediente","Valor pendiente actual", "Valor aceptado IPS","Observaciones", "Valor pagado EPS"
        , "Porcentaje pagado EPS","Porcentaje acpetado IPS", "Observacion respuesta detalles","Fecha de prestacion", "Valor glosa", "Motivo glosa"};//llena los encabezados de la tabla con el array
        mt.setColumnIdentifiers(arreglo_colunm);
        tableauditoriamotivos.setModel(mt);
        int[] tamamo_colum_auditoria = {100, 150, 100, 100, 100, 100, 100,100, 150, 100, 100, 100, 100, 100,100, 150, 100, 100, 100,150};//tamaño de las columnas
        TableColumnModel modelo_columna_auditoria = tableauditoriamotivos.getColumnModel();   
        for (int i = 0; i < tamamo_colum_auditoria.length; i++) {//cliclo para asinarle tamaño y color a las columnas
            TableColumn column = modelo_columna_auditoria.getColumn(i);
            column.setPreferredWidth(tamamo_colum_auditoria[i]);
            column.setHeaderRenderer(new CustomTableHeaderRenderer());
        }
        
        GetSet busqueda = new GetSet();//llama la clase GetSet para pruebas
        int prueba = 7;//pueba oara resultado
        busqueda.setCm_detalles_id(prueba);
        System.out.println(busqueda.getCm_detalles_id());
        buscarDatos(prueba);
        
    }
    
        public class CustomTableHeaderRenderer extends DefaultTableCellRenderer {//esta se llama para ponerle color de fondo y encabezado a la tabla integra

        @Override

        //Con esta se le da color al encabezado y tabla de la primera tabla 
        
        
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Establece el color de fondo y el color del texto del encabezado de la tabla
            setBackground(Color.gray); // Cambia el color de fondo aquí
            setForeground(Color.WHITE); // Cambia el color del texto aquí
            setFont(new Font("Verdana", Font.BOLD, 12)); // Opcional: cambia la fuente y el tamaño del texto

            setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto horizontalmente

            return this;
        }
    
        }
    public void buscarDatos(int numeroFactura) {//aqui se busca los datos de la factura en la base de datos

        Connection con = conexionesBD.ConexionPreProductivo.getConnection();//conecta a la BD
        if (con != null) {
            String query = "SELECT * FROM cm_detalles WHERE cm_facturas_id = ? ";
            try ( PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, numeroFactura);
                ResultSet rs = pst.executeQuery();

                mt.setRowCount(0);//limpia las filas de la tabla
                if (rs.next()) {
                    do {
                        Object[] rowData = {//lista de columnas de la cm_auditoria_motivos_glosa que se necesitan
                            rs.getInt("id"),
                            rs.getInt("cm_facturas_id"),
                            rs.getString("mae_tipo_documento_codigo"),
                            rs.getString("documento"),
                            rs.getString("nombre_completo_afiliado"),
                            rs.getInt("radicado_glosa"),
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
                            rs.getString("motivo_glosa")
                        };
                        mt.addRow(rowData);
                    } while (rs.next());
                    //System.out.println("Número de filas en la tabla después de agregar datos: " + mt_preproductivo.getRowCount());//impresiones para validar cuantas columnas cuenta
                    // System.out.println("Número de columnas en la tabla después de agregar datos: " + mt_preproductivo.getColumnCount());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron resultados en BD Pre productiva", "Resultado", JOptionPane.INFORMATION_MESSAGE);
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
        jPanel3 = new javax.swing.JPanel();
        lbldetalleglosa = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableauditoriamotivos = new javax.swing.JTable();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1269, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        jPanel3.setAutoscrolls(true);
        jPanel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        lbldetalleglosa.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        lbldetalleglosa.setText("Auditoria motivos glosa ");

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N

        tableauditoriamotivos.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tableauditoriamotivos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ));
        tableauditoriamotivos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane2.setViewportView(tableauditoriamotivos);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lbldetalleglosa, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(243, Short.MAX_VALUE))
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

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new Cm_detalles().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbldetalleglosa;
    private javax.swing.JTable tableauditoriamotivos;
    // End of variables declaration//GEN-END:variables
}
