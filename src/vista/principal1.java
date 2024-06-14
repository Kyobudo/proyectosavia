package vista;

import vista_productivo.AuditodiaMotivosGlosaProduc;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.*;
import java.awt.*;
import Getset.GetSet;

public class principal1 extends JFrame {

    DefaultTableModel mt_preproductivo = new DefaultTableModel();
    DefaultTableModel mt_somosmas = new DefaultTableModel();
    DefaultTableModel mt_integra = new DefaultTableModel();
    JTable table_preproductivo;
    public principal1() {
        initComponents();
        
       
        

        // Configuración de la tabla preproductivo
        String[] arreglo_colunm_preproductivo = {"id", "cm_rips_cargas_id", "nit", "ips", "Numero_facturado", "Fecha de prestacion", "Fecha_radicacion",
            "Valor pendiente", "Valor inicial glosa", "tipo auditoria", "valor factura", "valor pagado factura", "Valor copago", "Valor bruto", "Cuota modetadora", "estado de factura",
            "numero de contrato", "Fecha de creacion"};
        mt_preproductivo.setColumnIdentifiers(arreglo_colunm_preproductivo);
        tablepreproductivo.setModel(mt_preproductivo);
        int[] tamamo_colum_tablapreprocuntivo = {100, 150, 100, 100, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150};
        TableColumnModel modelo_columna_preproductivo = tablepreproductivo.getColumnModel();
        for (int i = 0; i < tamamo_colum_tablapreprocuntivo.length; i++) {
            TableColumn column = modelo_columna_preproductivo.getColumn(i);
            column.setPreferredWidth(tamamo_colum_tablapreprocuntivo[i]);
            column.setHeaderRenderer(new CustomTableHeaderRenderer());
        }

        // Configuración de la tabla somos mas
        String[] arreglo_column_somosmas = {"id", "nit_ips", "Factuta", "Valor_glosa_inicial", "estado_final_auditoria", "valor_factura"};
        mt_somosmas.setColumnIdentifiers(arreglo_column_somosmas);
        tablesomosmas.setModel(mt_somosmas);
        int[] tamamo_colum_tablasomosmas = {100, 150, 150, 150, 150, 150};
        TableColumnModel modelo_columna_somosmas = tablesomosmas.getColumnModel();
        for (int i = 0; i < tamamo_colum_tablasomosmas.length; i++) {
            TableColumn column = modelo_columna_somosmas.getColumn(i);
            column.setPreferredWidth(tamamo_colum_tablasomosmas[i]);
            column.setHeaderRenderer(new CustomTableHeaderRenderer());
        }

        // Configuración de la tabla integra
        String[] arreglo_column_integra = {"NUM_RADICA", "NIT_IPS", "RAZON SOCIAL", "NUMERO_FACTURA", "FECHA_FACTURA", "VALOR_FACTURA", "VALOR_PAGADO", "VALOR_GLOSA", "VALOR_REGISTRTADO", "VALOR_COPAGO", "VALOR_DESCUENTO",
            "OBSERVACION RADICACION", "ESTADO DE FACTURA", "MOTIVO DE DEVOLUCION", "VALOR_NOTA_DEBITO", "VALOR_NOTA_CREDITO"};
        mt_integra.setColumnIdentifiers(arreglo_column_integra);
        tableintegra.setModel(mt_integra);
        //int[] tamamo_colum_tablaintegra = {150, 150, 150, 150, 150, 150, 150, 100, 150, 200, 200, 200, 200, 200, 200, 200};
        TableColumnModel modelo_columna_integra = tableintegra.getColumnModel();
        int prueba =16;
        for (int i = 0; i < prueba; i++) {
            TableColumn column = modelo_columna_integra.getColumn(i);
           // column.setPreferredWidth(prueba[i]);
            column.setHeaderRenderer(new CustomTableHeaderRenderer());
        }
        
        
        

        btnbuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nit = txtfnit.getText();
                String numeroFactura = txtfnumero_factura.getText();

                if (numeroFactura.isEmpty() || nit.isEmpty()) {
                    JOptionPane.showMessageDialog(principal1.this, "Por favor ingrese todos los datos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                GetSet busqueda = new GetSet();
                busqueda.setNit(nit);
                busqueda.setNumero_factura(numeroFactura);

                Connection con = conexionesBD.ConexionPreProductivo.getConnection();

                DatosProductivoFetcher fetcher = new DatosProductivoFetcher();
                fetcher.buscarDatosProductivo(mt_preproductivo, numeroFactura, nit, con, principal1.this);

                busquedaDatosSomosMas(numeroFactura, nit);
                busquedaDatosintegra(numeroFactura, nit);
            }
        });
        
        
    }

public class CustomTableHeaderRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setBackground(Color.gray);
            setForeground(Color.WHITE);
            setFont(new Font("Verdana", Font.BOLD, 12));
            setHorizontalAlignment(SwingConstants.CENTER);
            return this;
        }
    }
   
   
   public void busquedaDatosSomosMas(String numeroFactura, String nit) {//aqui se busca los datos de la factura en la base de datos somos mas

        Connection conexionSomos = conexionesBD.ConexionSomos.getConnection();//conecta a la BD
        if (conexionSomos != null) {
            String query = "SELECT * FROM facturas WHERE factura = ? AND nit_ips = ?";
            try ( PreparedStatement pst = conexionSomos.prepareStatement(query)) {
                pst.setString(1, numeroFactura);
                pst.setString(2, nit);
                ResultSet rs = pst.executeQuery();

                mt_somosmas.setRowCount(0);//limpia las filas de la tabla
               
                if (rs.next()) {
                    do {

                        Object[] rowData = {
                            rs.getInt("id"),
                            rs.getInt("nit_ips"),
                            rs.getString("factura"),
                            rs.getDouble("valor_glosa_inicial"),
                            rs.getString("estado_final_auditoria"),
                            rs.getDouble("valor_factura")
                        };
                        mt_somosmas.addRow(rowData);
                    } while (rs.next());
                    System.out.println("Número de filas en la tabla después de agregar datos: " + mt_somosmas.getRowCount());
                    System.out.println("Número de columnas en la tabla después de agregar datos: " + mt_somosmas.getColumnCount());
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron resultados en BD Somos +", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al realizar la consulta en BD Somos +", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void busquedaDatosintegra(String numeroFactura, String nit) {
    Connection conexion_integra = conexionesBD.ConexionIntegra.getConnection();
    if (conexion_integra != null) {
        String query = "SELECT * FROM dbo.FACTURAS_CUENTAS_MEDICAS WHERE `NUMERO FACTURA` = ? AND `NIT IPS` = ?";
        try (PreparedStatement pst = conexion_integra.prepareStatement(query)) {
            pst.setString(1, numeroFactura);
            pst.setString(2, nit);
            ResultSet rs = pst.executeQuery();

            mt_integra.setRowCount(0);
            if (rs.next()) {
                do {
                    Object[] rowData = {
                        rs.getInt("NUM_RADICA"),
                        rs.getString("NIT IPS"),
                        rs.getString("RAZON SOCIAL"),
                        rs.getString("NUMERO FACTURA"),
                        rs.getString("FECHA FACTURA"),
                        rs.getDouble("VALOR FACTURA"),
                        rs.getDouble("VALOR PAGADO"),
                        rs.getDouble("VALOR GLOSA"),
                        rs.getDouble("VALOR REGISTRADO"),
                        rs.getDouble("VALOR COPAGO"),
                        rs.getDouble("VALOR DESCUENTO"),
                        rs.getString("OBSERVACION RADICACION"),
                        rs.getString("ESTADO DE LA FACTURA"),
                        rs.getString("MOTIVO DEVOLUCION"),
                        rs.getDouble("VALOR_NOTA_DEBITO"),
                        rs.getDouble("VALOR_NOTA_CREDITO")
                    };
                    mt_integra.addRow(rowData);
                } while (rs.next());
                System.out.println("Número de filas en la tabla después de agregar datos: " + mt_integra.getRowCount());
                System.out.println("Número de columnas en la tabla después de agregar datos: " + mt_integra.getColumnCount());
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron resultados en BD Integra", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Error al realizar la consulta en BD Integra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo establecer conexión con la BD Integra", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        conexion1 = new conexionesBD.ConexionPreProductivo();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblnumero_factura = new javax.swing.JLabel();
        txtfnumero_factura = new javax.swing.JTextField();
        lblnit = new javax.swing.JLabel();
        txtfnit = new javax.swing.JTextField();
        btnbuscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btndetalleglosa = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablepreproductivo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablesomosmas = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableintegra = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        lblnumero_factura.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        lblnumero_factura.setText("Ingrese el numero de factura:");

        txtfnumero_factura.setBackground(new java.awt.Color(255, 255, 255));
        txtfnumero_factura.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtfnumero_factura.setForeground(new java.awt.Color(153, 153, 153));
        txtfnumero_factura.setBorder(null);

        lblnit.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        lblnit.setText("Ingrese en NT:");

        txtfnit.setBackground(new java.awt.Color(255, 255, 255));
        txtfnit.setForeground(new java.awt.Color(153, 153, 153));
        txtfnit.setBorder(null);

        btnbuscar.setText("Buscar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblnumero_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfnumero_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblnit)
                .addGap(18, 18, 18)
                .addComponent(txtfnit, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblnumero_factura)
                    .addComponent(txtfnumero_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblnit)
                    .addComponent(txtfnit, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnbuscar))
                .addContainerGap(50, Short.MAX_VALUE))
        );

        btndetalleglosa.setText("Ver detalle glosa");
        btndetalleglosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndetalleglosaActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane1.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        tablepreproductivo.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tablepreproductivo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16", "Title 17", "Title 18", "Title 19"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablepreproductivo.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablepreproductivo.setDragEnabled(true);
        tablepreproductivo.setGridColor(new java.awt.Color(153, 153, 153));
        tablepreproductivo.setShowGrid(true);
        tablepreproductivo.getTableHeader().setResizingAllowed(false);
        tablepreproductivo.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tablepreproductivo);

        jLabel1.setText("Respuestas base de datos preproductiva");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btndetalleglosa)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1204, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btndetalleglosa)
                .addGap(24, 24, 24))
        );

        jLabel2.setText("Respuestas  base de datos Somos +");

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane3.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        tablesomosmas.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(153, 153, 153)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12))); // NOI18N
        tablesomosmas.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tablesomosmas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablesomosmas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tablesomosmas.setDragEnabled(true);
        tablesomosmas.setGridColor(new java.awt.Color(153, 153, 153));
        tablesomosmas.setShowGrid(true);
        tablesomosmas.getTableHeader().setResizingAllowed(false);
        tablesomosmas.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tablesomosmas);

        jButton1.setText("Factura detalles");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(200);
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(200);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(500);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        jLabel3.setText("Respuestas bases de datos integra");

        jScrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane4.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N

        tableintegra.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(153, 153, 153)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12))); // NOI18N
        tableintegra.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tableintegra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableintegra.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableintegra.setDragEnabled(true);
        tableintegra.setGridColor(new java.awt.Color(153, 153, 153));
        tableintegra.setShowGrid(true);
        tableintegra.getTableHeader().setResizingAllowed(false);
        tableintegra.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tableintegra);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1885, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel2))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jButton1))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 118, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 2464, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 684, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btndetalleglosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndetalleglosaActionPerformed
        String numeroFactura = txtfnumero_factura.getText();
        String nit = txtfnit.getText();
        AuditodiaMotivosGlosaProduc detal = new AuditodiaMotivosGlosaProduc(); // Pasa los valores ingresados por el usuario
        detal.setVisible(true);
    }//GEN-LAST:event_btndetalleglosaActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
              
                new principal1().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton btndetalleglosa;
    private conexionesBD.ConexionPreProductivo conexion1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblnit;
    private javax.swing.JLabel lblnumero_factura;
    private javax.swing.JTable tableintegra;
    private javax.swing.JTable tablepreproductivo;
    private javax.swing.JTable tablesomosmas;
    private javax.swing.JTextField txtfnit;
    private javax.swing.JTextField txtfnumero_factura;
    // End of variables declaration//GEN-END:variables
}
