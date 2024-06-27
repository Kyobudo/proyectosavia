package vistasomosmas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.*;
import java.awt.*;


public class FacturaDetalles extends javax.swing.JFrame {

    DefaultTableModel mt_facturaDetalles;

    public FacturaDetalles() {
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        //bucle para aplicarle color a los encabezados y tipo de letra a tabla detalles
        mt_facturaDetalles = (DefaultTableModel) tableFacturaDetalles.getModel();
        TableColumnModel modelo_columna_fact_detalles = tableFacturaDetalles.getColumnModel();
        for (int i = 0; i < 9; i++) {
            TableColumn column = modelo_columna_fact_detalles.getColumn(i);
            column.setHeaderRenderer(new CustomTableHeaderRenderer());//se llama el color de encabezado
        }       

    }

    public class CustomTableHeaderRenderer extends DefaultTableCellRenderer {//esta se llama para ponerle color de fondo y encabezado a la tabla integra
        //Con esta se le da color al encabezado y tabla de la primera tabla de preproductivo

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Establece el color de fondo y el color del texto del encabezado de la tabla
            setBackground(Color.gray); // Cambia el color de fondo aquí
            setForeground(Color.WHITE); // Cambia el color del texto aquí
            setFont(new Font("Roboto", Font.BOLD, 13)); // Opcional: cambia la fuente y el tamaño del texto

            setHorizontalAlignment(SwingConstants.CENTER); // Centra el texto horizontalmente

            return this;
        }
    }
    //metodo para buscar la informacion de la factura el la base de datos

    public void busquedaDatosSomosMasDetalles(String numeroFactura, String nit) {//aqui se busca los datos de la factura en la base de datos somos mas

        Connection conexionSomos = conexionesBD.ConexionSomos.getConnection();//conecta a la BD
        if (conexionSomos != null) {
            String query = "SELECT * FROM factura_detalles WHERE factura = ? AND nit_ips = ?";
            try ( PreparedStatement pst = conexionSomos.prepareStatement(query)) {
                pst.setString(1, nit);
                pst.setString(2, numeroFactura);
                ResultSet rs = pst.executeQuery();
                mt_facturaDetalles.setRowCount(0);//limpia las filas de la tabla
                if (rs.next()) {
                    do {

                        Object[] rowData = {//columnas en las que se va buscar en la BD
                            rs.getInt("id"),
                            rs.getInt("nit_ips"),
                            rs.getString("factura"),
                            rs.getString("documento_afiliado"),
                            rs.getString("nombre_afiliado"),
                            rs.getString("descripcion_tecnologia"),
                            rs.getString("motivo_glosa"),
                            rs.getString("detalle_glosa"),
                            rs.getInt("valor_glosa_inicial")
                        };
                        mt_facturaDetalles.addRow(rowData);

                    } while (rs.next());
                    System.out.println("Número de filas en la tabla después de agregar datos: " + mt_facturaDetalles.getRowCount());//impresiones para validar cuantas columnas cuenta-es para pruebas
                    System.out.println("Número de columnas en la tabla después de agregar datos: " + mt_facturaDetalles.getColumnCount());//impresiones para validar cuantas columnas cuenta-es para pruebas
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron resultados en BD Somos +, puedes realiazar otra busqueda", "Resultado", JOptionPane.INFORMATION_MESSAGE);

                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al realizar la consulta en BD Somos +", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public boolean Estado() {
        boolean estado = true;
        if (mt_facturaDetalles.getRowCount() > 0) {
            estado = true;
        } else {
            estado = false;
        }
        return estado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        panelEncabezado = new javax.swing.JPanel();
        btn_cerrar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableFacturaDetalles = new javax.swing.JTable();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelEncabezado.setBackground(new java.awt.Color(0, 153, 153));
        panelEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Base de datos Somos +", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        panelEncabezado.setForeground(new java.awt.Color(255, 255, 255));
        panelEncabezado.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        panelEncabezado.setPreferredSize(new java.awt.Dimension(1000, 120));

        btn_cerrar.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        btn_cerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/signo.png"))); // NOI18N
        btn_cerrar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_cerrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_cerrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn_cerrar.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        btn_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEncabezadoLayout = new javax.swing.GroupLayout(panelEncabezado);
        panelEncabezado.setLayout(panelEncabezadoLayout);
        panelEncabezadoLayout.setHorizontalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEncabezadoLayout.createSequentialGroup()
                .addGap(0, 922, Short.MAX_VALUE)
                .addComponent(btn_cerrar))
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addComponent(btn_cerrar)
                .addGap(0, 29, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)), "Detalles factura Somos +", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setFocusable(false);
        jScrollPane1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jScrollPane1.setHorizontalScrollBar(null);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1500, 100));

        tableFacturaDetalles.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tableFacturaDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "NIT", "Numero factura", "Documento del afiliado", "Nombre del afiliado", "Descripcion tecnologia", "Motivo glosa", "Detalle glosa", "Valor inical glosa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableFacturaDetalles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableFacturaDetalles.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableFacturaDetalles.setPreferredSize(new java.awt.Dimension(1600, 90));
        tableFacturaDetalles.setRowSelectionAllowed(false);
        tableFacturaDetalles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tableFacturaDetalles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableFacturaDetalles.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableFacturaDetalles);
        if (tableFacturaDetalles.getColumnModel().getColumnCount() > 0) {
            tableFacturaDetalles.getColumnModel().getColumn(0).setPreferredWidth(100);
            tableFacturaDetalles.getColumnModel().getColumn(1).setPreferredWidth(100);
            tableFacturaDetalles.getColumnModel().getColumn(2).setPreferredWidth(200);
            tableFacturaDetalles.getColumnModel().getColumn(3).setPreferredWidth(200);
            tableFacturaDetalles.getColumnModel().getColumn(4).setPreferredWidth(200);
            tableFacturaDetalles.getColumnModel().getColumn(5).setPreferredWidth(200);
            tableFacturaDetalles.getColumnModel().getColumn(6).setPreferredWidth(200);
            tableFacturaDetalles.getColumnModel().getColumn(7).setPreferredWidth(200);
            tableFacturaDetalles.getColumnModel().getColumn(8).setPreferredWidth(100);
        }

        jScrollPane3.setViewportView(jScrollPane1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 64, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        int HIDE_ON_CLOSE1 = FacturaDetalles.HIDE_ON_CLOSE;
        if (HIDE_ON_CLOSE1>0) {
            hide();
        }
    }//GEN-LAST:event_btn_cerrarActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FacturaDetalles().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cerrar;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    public javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JTable tableFacturaDetalles;
    // End of variables declaration//GEN-END:variables
}
