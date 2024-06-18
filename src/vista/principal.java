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
import vistasomosmas.FacturaDetalles;
import vista_productivo.GlosaRespuesta;

public class principal extends JFrame {

    DefaultTableModel mt_productivo = new DefaultTableModel();
    DefaultTableModel mt_somosmas = new DefaultTableModel();
    DefaultTableModel mt_integra = new DefaultTableModel();
    GetSet busqueda = new GetSet();

    public principal() {

        initComponents();

        String[] arreglo_colunm_preproductivo = {"Id", "cm_rips_cargas_id", "NIT", "IPS", "Numero facturado", "Fecha de prestacion", "Fecha radicacion",
            "Valor pendiente", "Valor inicial glosa", "Tipo auditoria", "Valor factura", "Valor pagado factura", "Valor copago", "Valor bruto", "Cuota modetadora", "Estado de factura",
            "Numero de contrato", "Fecha de creacion"};//llena los encabezados de la tabla con el array
        mt_productivo.setColumnIdentifiers(arreglo_colunm_preproductivo);
        tableproductivo.setModel(mt_productivo);
        int[] tamamo_colum_tablapreprocuntivo = {150, 150, 150, 300, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150, 150};//
        TableColumnModel modelo_columna_preproductivo = tableproductivo.getColumnModel();
        for (int i = 0; i < tamamo_colum_tablapreprocuntivo.length; i++) {
            TableColumn column = modelo_columna_preproductivo.getColumn(i);
            column.setPreferredWidth(tamamo_colum_tablapreprocuntivo[i]);
            column.setHeaderRenderer(new CustomTableHeaderRenderer());//se llama el color
        }

        String[] arreglo_column_somosmas = {"Id", "NIT", "Factuta", "Valor glosa inicial",
            "Estado final auditoria", "Valor factura"};
        mt_somosmas.setColumnIdentifiers(arreglo_column_somosmas);
        tablesomosmas.setModel(mt_somosmas);
        int[] tamanoColumSomosMas = {150, 150, 150, 150, 150, 150};//
        TableColumnModel modelo_columna_somosmas = tablesomosmas.getColumnModel();
        for (int i = 0; i < tamanoColumSomosMas.length; i++) {
            TableColumn column = modelo_columna_somosmas.getColumn(i);
            column.setPreferredWidth(tamanoColumSomosMas[i]);
            column.setHeaderRenderer(new CustomTableHeaderRenderer());//se llama el color
        }
       

        String[] arreglo_column_integra = {"Numero de radicado", "NIT", "IPS", "Numero de factura",
            "Fecha de factura", "Valor factura", "Valor pagado", "Valor glosa", "Valor registrado", "Valor copago", "Valor descuento",
            "Observacion radicacion", "Estado de factura", "Motivo de devolucion", "Valor nota debito", "Valor nota credito"};
        mt_integra.setColumnIdentifiers(arreglo_column_integra);
        tableintegra.setModel(mt_integra);
        int[] tamanoColumIntegra = {150, 150, 150, 150, 150, 150, 150, 100, 150, 200, 200, 200, 200, 200, 200, 200};
        TableColumnModel modelo_columna_integra = tableintegra.getColumnModel();
        for (int i = 0; i < tamanoColumIntegra.length; i++) {
            TableColumn column = modelo_columna_integra.getColumn(i);
            column.setPreferredWidth(tamanoColumIntegra[i]);
            column.setHeaderRenderer(new CustomTableHeaderRenderer());
        }
            btndetallessomos.setEnabled(false);
            btnrespuestaglosa.setEnabled(false);
        btnbuscar.addActionListener(new ActionListener() {//activa la accion en boton buscar

            @Override
            public void actionPerformed(ActionEvent e) {
                
                btndetallessomos.setEnabled(false);
                btnrespuestaglosa.setEnabled(false);
                
                
                String nit = txtfnit.getText();
                String numeroFactura = txtfnumero_factura.getText();

                if (numeroFactura.isEmpty() || nit.isEmpty()) {//mensaje en caso de que no ingresen datos para la busqueda
                    JOptionPane.showMessageDialog(principal.this, "Por favor ingrese todos los datos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                busqueda.setNit(nit);
                busqueda.setNumero_factura(numeroFactura);
                System.out.println(busqueda.getNit() + "9999999999999999999999999999999999");
                buscarDatosProductivo(numeroFactura, nit);//se llama buscar datos en la bd preproductiva y se le envian los argumentos para la busqueda
                busquedaDatosSomosMas(numeroFactura, nit);//se llama buscar datos en la bd somos + y se le envian los argumentos para la busqueda
                //busquedaDatosintegra(numeroFactura, nit);//se llama buscar datos en la bd integra y se le envian los argumentos para la busqueda
            }
        });

    }

    public class CustomTableHeaderRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Establece el color de fondo y el color del texto del encabezado de la tabla
            setBackground(Color.gray); // Color de fondo
            setForeground(Color.white); // Color del texto
            setFont(new Font("Roboto", Font.BOLD, 13)); // Fuente y tamaño del texto

            setHorizontalAlignment(SwingConstants.CENTER); // Alineación horizontal del texto
            return this;
        }
    }

    public void buscarDatosProductivo(String numeroFactura, String nit) {//aqui se busca los datos de la factura en la base de datos preproductivo
        System.out.println(numeroFactura + nit);
        Connection con = conexionesBD.ConexionProductivo.getConnection();//conecta a la BD
        if (con != null) {
            String query = "SELECT * FROM cm_facturas WHERE numero_facturado = ? AND nit = ?";
            try ( PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, numeroFactura);
                pst.setString(2, nit);
                ResultSet rs = pst.executeQuery();

                mt_productivo.setRowCount(0);//limpia las filas de la tabla
                if (rs.next()) {
                    do {
                        Object[] rowData = {//lista de columnas de la cm_facturas que se necesitan
                            rs.getInt("id"),
                            rs.getInt("cm_rips_cargas_id"),
                            rs.getString("nit"),
                            rs.getString("ips"),
                            rs.getString("numero_facturado"),
                            rs.getString("fecha_prestacion"),
                            rs.getString("fecha_radicacion"),
                            rs.getDouble("valor_pendiente_actual"),
                            rs.getDouble("valor_inicial_glosa"),
                            rs.getInt("tipo_auditoria"),
                            rs.getDouble("valor_factura"),
                            rs.getDouble("valor_pagado_factura"),
                            rs.getDouble("valor_copago"),
                            rs.getDouble("valor_bruto"),
                            rs.getDouble("valor_cuota_moderadora"),
                            rs.getInt("estado_factura"),
                            rs.getString("numero_contrato"),
                            rs.getString("fecha_hora_crea")

                        };
                        mt_productivo.addRow(rowData);
                        mt_productivo.fireTableDataChanged();
                    } while (rs.next());

                    System.out.println("Número de filas en la tabla después de agregar datos: " + mt_productivo.getRowCount());//impresiones para validar cuantas columnas cuenta
                    System.out.println("Número de columnas en la tabla después de agregar datos: " + mt_productivo.getColumnCount());//impresiones para validar cuantas columnas cuenta

                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron resultados en BD Pre productiva", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al realizar la consulta en BD Pre productiva ", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }if (mt_productivo.getRowCount() > 0) {

            btnrespuestaglosa.setEnabled(true);
        }
        
        }
        
    

    public void busquedaDatosSomosMas(String numeroFactura, String nit) {//aqui se busca los datos de la factura en la base de datos somos mas
        mt_somosmas.setRowCount(0);
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

                        Object[] rowData = {//columnas en las que se va buscar en la BD
                            rs.getInt("id"),
                            rs.getInt("nit_ips"),
                            rs.getString("factura"),
                            rs.getDouble("valor_glosa_inicial"),
                            rs.getString("estado_final_auditoria"),
                            rs.getDouble("valor_factura")
                        };
                        mt_somosmas.addRow(rowData);

                    } while (rs.next());
                    System.out.println("Número de filas en la tabla después de agregar datos:somos " + mt_somosmas.getRowCount());//impresiones para validar cuantas columnas cuenta-es para pruebas
                    System.out.println("Número de columnas en la tabla después de agregar datos: " + mt_somosmas.getColumnCount());//impresiones para validar cuantas columnas cuenta-es para pruebas
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron resultados en BD Somos +", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al realizar la consulta en BD Somos +", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (mt_somosmas.getRowCount() > 0) {

            btndetallessomos.setEnabled(true);
        }
    }

    public void busquedaDatosintegra(String numeroFactura, String nit) {//busqueda  de datos solicitados en la BD de integra
        Connection conexion_integra = conexionesBD.ConexionIntegra.getConnection();
        if (conexion_integra != null) {
            String query = "SELECT * FROM dbo.FACTURAS_CUENTAS_MEDICAS WHERE `NUMERO FACTURA` = ? AND `NIT IPS` = ?";//consulta en la BD integra
            try ( PreparedStatement pst = conexion_integra.prepareStatement(query)) {
                pst.setString(1, numeroFactura);
                pst.setString(2, nit);
                ResultSet rs = pst.executeQuery();

                mt_integra.setRowCount(0);
                if (rs.next()) {
                    do {
                        Object[] rowData = {//columnas a las cuales se les va a extraer el dato en la BD de integra
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
                    System.out.println("Número de filas en la tabla después de agregar datos: " + mt_integra.getRowCount());//impresiones para validar cuantas columnas cuenta-es para pruebas
                    System.out.println("Número de columnas en la tabla después de agregar datos: " + mt_integra.getColumnCount());//impresiones para validar cuantas columnas cuenta-es para pruebas
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

        conexion1 = new conexionesBD.ConexionProductivo();
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
        tableproductivo = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnrespuestaglosa = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablesomosmas = new javax.swing.JTable();
        btndetallessomos = new javax.swing.JButton();
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
        txtfnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfnitActionPerformed(evt);
            }
        });

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

        tableproductivo.setAutoCreateRowSorter(true);
        tableproductivo.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tableproductivo.setForeground(new java.awt.Color(0, 0, 0));
        tableproductivo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Id", "cm_rips_cargas_id", "NIT", "IPS", "Numero factura", "Fecha de prestacion", "Fecha radicacion", "Valor pendiente", "Valor inicial glosa", "tipo auditoria", "Valor factura", "Valor pagado factura", "Copago", "Valor bruto", "Cuota moderadora", "Estado de la factura", "Numero de contrato", "Fecha de creacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableproductivo.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tableproductivo.setDragEnabled(true);
        tableproductivo.setGridColor(new java.awt.Color(153, 153, 153));
        tableproductivo.setShowGrid(true);
        tableproductivo.getTableHeader().setResizingAllowed(false);
        tableproductivo.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableproductivo);
        if (tableproductivo.getColumnModel().getColumnCount() > 0) {
            tableproductivo.getColumnModel().getColumn(0).setMinWidth(100);
            tableproductivo.getColumnModel().getColumn(0).setPreferredWidth(100);
            tableproductivo.getColumnModel().getColumn(1).setMinWidth(180);
            tableproductivo.getColumnModel().getColumn(1).setPreferredWidth(180);
            tableproductivo.getColumnModel().getColumn(2).setMinWidth(100);
            tableproductivo.getColumnModel().getColumn(2).setPreferredWidth(100);
            tableproductivo.getColumnModel().getColumn(3).setPreferredWidth(500);
            tableproductivo.getColumnModel().getColumn(4).setMinWidth(100);
            tableproductivo.getColumnModel().getColumn(4).setPreferredWidth(100);
            tableproductivo.getColumnModel().getColumn(5).setMinWidth(100);
            tableproductivo.getColumnModel().getColumn(5).setPreferredWidth(100);
            tableproductivo.getColumnModel().getColumn(6).setMinWidth(100);
            tableproductivo.getColumnModel().getColumn(6).setPreferredWidth(100);
        }

        jLabel1.setText("Respuestas base de datos preproductiva");

        btnrespuestaglosa.setText("Respuestas glosa");
        btnrespuestaglosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnrespuestaglosaActionPerformed(evt);
            }
        });

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1204, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnrespuestaglosa, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(170, 170, 170)
                        .addComponent(btndetalleglosa, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btndetalleglosa)
                    .addComponent(btnrespuestaglosa))
                .addGap(43, 43, 43))
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
                "Id", "Nit IPS", "Numero factura", "Valor glosa incial", "Estodo final auditoria", "Valor factura"
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

        btndetallessomos.setText("Ver detalles de la factura");
        btndetallessomos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndetallessomosActionPerformed(evt);
            }
        });

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
                        .addGap(41, 41, 41)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 897, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btndetallessomos, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(861, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btndetallessomos)
                .addContainerGap(9, Short.MAX_VALUE))
        );

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
                "Numero radicacion", "Nit IPS", "Razon social", "Numero factura", "Fecha factura", "Valor factura", "Valor pagado", "Valor glosa", "Valor registrado", "Valor copago", "Valor descuento", "Observacion radicacion", "Estado factura", "Motivo devolucion", "Valor nota debito", "Valor nota credito"
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
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(363, 363, 363))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btndetalleglosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndetalleglosaActionPerformed
        AuditodiaMotivosGlosaProduc detal = new AuditodiaMotivosGlosaProduc(); // Pasa los valores ingresados por el usuario
        detal.setVisible(true);
    }//GEN-LAST:event_btndetalleglosaActionPerformed

    private void btndetallessomosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndetallessomosActionPerformed
        FacturaDetalles FacturaDetalles = new FacturaDetalles(busqueda);
        FacturaDetalles.setVisible(true);
    }//GEN-LAST:event_btndetallessomosActionPerformed

    private void txtfnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfnitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfnitActionPerformed

    private void btnrespuestaglosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnrespuestaglosaActionPerformed
      GlosaRespuesta GlosaRespuesta= new GlosaRespuesta();
      GlosaRespuesta.setVisible(true);
    }//GEN-LAST:event_btnrespuestaglosaActionPerformed

    public static void main(String args[]) {//metodo pricipal
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new principal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton btndetalleglosa;
    private javax.swing.JButton btndetallessomos;
    private javax.swing.JButton btnrespuestaglosa;
    private conexionesBD.ConexionProductivo conexion1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblnit;
    private javax.swing.JLabel lblnumero_factura;
    private javax.swing.JTable tableintegra;
    private javax.swing.JTable tableproductivo;
    private javax.swing.JTable tablesomosmas;
    private javax.swing.JTextField txtfnit;
    private javax.swing.JTextField txtfnumero_factura;
    // End of variables declaration//GEN-END:variables
}
