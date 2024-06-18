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
import Getset.GetSet;

public class FacturaDetalles extends javax.swing.JFrame {
DefaultTableModel mt_facturaDetalles = new DefaultTableModel();
GetSet busqueda = new GetSet();


    public FacturaDetalles(GetSet busqueda) {
        initComponents();        
      
        
 
        //Se instancia el contenido de la tabla factura detalle de somos +
        String[] arreglo_colunm_fac_detalles= {"id", "nit ips", "Factura", "Documento afiliado", "Nombre afiliado", "Descripcion tecnologia", "Motivo glosa",
            "Detalle glosa", "Valor glosa inicial"};//llena los encabezados de la tabla con el array
        mt_facturaDetalles.setColumnIdentifiers(arreglo_colunm_fac_detalles);
        tablefactura_detalles.setModel(mt_facturaDetalles);
        int[] tamamo_colum_tablapreprocuntivo = {80, 100, 150, 150, 250, 200, 150, 400, 100};//tamaño de las columnas
        TableColumnModel modelo_columna_fact_detalles= tablefactura_detalles.getColumnModel();
        for (int i = 0; i < tamamo_colum_tablapreprocuntivo.length; i++) {
            TableColumn column = modelo_columna_fact_detalles.getColumn(i);
            column.setPreferredWidth(tamamo_colum_tablapreprocuntivo[i]);
            column.setHeaderRenderer(new CustomTableHeaderRenderer());//se llama el color de encabezado
        }        
         //se guarda el numero de la factura y el nit en la clase GetSet
                   
             String numeroFactura=busqueda.getNumero_factura();
             String nit=busqueda.getNit();
                    
             busquedaDatosSomosMasDetalles(numeroFactura,nit);        
    
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
     
    

     public void busquedaDatosSomosMasDetalles(String numeroFactura, String nit) {//aqui se busca los datos de la factura en la base de datos somos mas
          System.out.println("3333399"+numeroFactura+nit);
        Connection conexionSomos = conexionesBD.ConexionSomos.getConnection();//conecta a la BD
        if (conexionSomos != null) {
            String query = "SELECT * FROM factura_detalles WHERE factura = ? AND nit_ips = ?";
            try ( PreparedStatement pst = conexionSomos.prepareStatement(query)) {
                pst.setString(1, numeroFactura);
                pst.setString(2, nit);
                ResultSet rs = pst.executeQuery();
                System.out.println(numeroFactura+nit+"dddddddddddddddd");
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
                    JOptionPane.showMessageDialog(this, "No se encontraron resultados en BD Somos +", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al realizar la consulta en BD Somos +", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablefactura_detalles = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel1.setText("Detalles factura Somos +");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        tablefactura_detalles.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tablefactura_detalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablefactura_detalles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablefactura_detalles.setDragEnabled(true);
        tablefactura_detalles.setGridColor(new java.awt.Color(153, 153, 153));
        tablefactura_detalles.setShowGrid(true);
        tablefactura_detalles.getTableHeader().setResizingAllowed(false);
        tablefactura_detalles.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tablefactura_detalles);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 976, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                GetSet busqueda = new GetSet();
                new FacturaDetalles(busqueda).setVisible(true); 
               
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablefactura_detalles;
    // End of variables declaration//GEN-END:variables
}
