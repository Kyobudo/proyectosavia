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
import vista_productivo.FacturaDetallesProductivo;
import vistasomosmas.FacturaDetalles;
import vista_productivo.GlosaRespuesta;

public class Principal extends JFrame {

    DefaultTableModel mt_productivo;
    DefaultTableModel mt_somosmas;    
    DefaultTableModel mt_integra;
    GetSet busqueda = new GetSet();
 
    public Principal() {

        initComponents();
        //bucle para aplicarle color a los encabezados y tipo de letra a tabla productiva       
        mt_productivo =(DefaultTableModel)tableproductivo.getModel();       
        TableColumnModel modelo_columna_preproductivo = tableproductivo.getColumnModel();
        for (int i = 0; i <18; i++) {
            TableColumn column = modelo_columna_preproductivo.getColumn(i);           
            column.setHeaderRenderer(new CustomTableHeaderRenderer());//se llama el color
        }
       
        tableproductivo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //bucle para aplicarle color a los encabezados y tipo de letra a tabla somos mas  
        mt_somosmas=(DefaultTableModel)tablesomosmas.getModel();
        TableColumnModel modelo_columna_somosmas = tablesomosmas.getColumnModel();
        for (int i = 0; i < 6; i++) {
            TableColumn column = modelo_columna_somosmas.getColumn(i);           
            column.setHeaderRenderer(new CustomTableHeaderRenderer());//se llama el color
        }       

        //bucle para aplicarle color a los encabezados y tipo de letra a tabla integra  
        mt_integra=(DefaultTableModel)tableintegra.getModel();
        TableColumnModel modelo_columna_integra = tableintegra.getColumnModel();
        for (int i = 0; i < 16; i++) {
            TableColumn column = modelo_columna_integra.getColumn(i);           
            column.setHeaderRenderer(new CustomTableHeaderRenderer());
        }
            btndetallessomos.setEnabled(false);
            btnDetallesFacturaProductivo.setEnabled(false); 
        btnbuscar.addActionListener(new ActionListener() {//activa la accion en boton buscar

            @Override
            public void actionPerformed(ActionEvent e) {
                
                btndetallessomos.setEnabled(false);
                btnDetallesFacturaProductivo.setEnabled(false);                           
                
                String nit = txtfnit.getText();
                String numeroFactura = txtNumFactura.getText();

                if (numeroFactura.isEmpty() || nit.isEmpty()) {//mensaje en caso de que no ingresen datos para la busqueda
                    JOptionPane.showMessageDialog(Principal.this, "Por favor ingrese todos los datos", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                busqueda.setNit(nit);
                busqueda.setNumero_factura(numeroFactura);               
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

          btnDetallesFacturaProductivo.setEnabled(true);
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
        jPanel4 = new javax.swing.JPanel();
        panelEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNumFactura = new javax.swing.JTextField();
        lblnit = new javax.swing.JLabel();
        txtfnit = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnbuscar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablesomosmas = new javax.swing.JTable();
        btndetallessomos = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableintegra = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableproductivo = new javax.swing.JTable();
        btnDetallesFacturaProductivo = new javax.swing.JButton();

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
        setBackground(new java.awt.Color(255, 255, 255));
        setSize(new java.awt.Dimension(800, 600));

        panelEncabezado.setBackground(new java.awt.Color(0, 153, 153));
        panelEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Busqueda de datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        panelEncabezado.setForeground(new java.awt.Color(255, 255, 255));
        panelEncabezado.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        panelEncabezado.setPreferredSize(new java.awt.Dimension(1000, 120));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ingrese el numero de factura: ");

        txtNumFactura.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        txtNumFactura.setForeground(new java.awt.Color(102, 102, 102));
        txtNumFactura.setPreferredSize(new java.awt.Dimension(65, 22));
        txtNumFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumFacturaActionPerformed(evt);
            }
        });

        lblnit.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        lblnit.setForeground(new java.awt.Color(255, 255, 255));
        lblnit.setText("Ingrese en NIT:");

        txtfnit.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        txtfnit.setForeground(new java.awt.Color(102, 102, 102));
        txtfnit.setPreferredSize(new java.awt.Dimension(65, 22));

        jPanel1.setBackground(new java.awt.Color(0, 153, 153));

        btnbuscar.setBackground(new java.awt.Color(102, 102, 102));
        btnbuscar.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnbuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnbuscar.setText("Buscar");
        btnbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnbuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnbuscar)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelEncabezadoLayout = new javax.swing.GroupLayout(panelEncabezado);
        panelEncabezado.setLayout(panelEncabezadoLayout);
        panelEncabezadoLayout.setHorizontalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lblnit, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtfnit, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(362, Short.MAX_VALUE))
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtNumFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblnit)
                        .addComponent(txtfnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "BD Somos +", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N
        jPanel3.setPreferredSize(new java.awt.Dimension(200, 250));

        jScrollPane7.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane7.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane7.setPreferredSize(new java.awt.Dimension(452, 200));

        tablesomosmas.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tablesomosmas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "NIT", "Numero de factura", "Valor glosa inicial", "Estado final auditoria", "Valor factura"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablesomosmas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tablesomosmas.setPreferredSize(new java.awt.Dimension(2500, 90));
        tablesomosmas.setRowSelectionAllowed(false);
        tablesomosmas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablesomosmas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tablesomosmas.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tablesomosmas);
        tablesomosmas.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tablesomosmas.getColumnModel().getColumnCount() > 0) {
            tablesomosmas.getColumnModel().getColumn(0).setPreferredWidth(200);
            tablesomosmas.getColumnModel().getColumn(0).setHeaderValue("Id");
            tablesomosmas.getColumnModel().getColumn(1).setPreferredWidth(200);
            tablesomosmas.getColumnModel().getColumn(1).setHeaderValue("cm_facturas_id");
            tablesomosmas.getColumnModel().getColumn(2).setPreferredWidth(200);
            tablesomosmas.getColumnModel().getColumn(2).setHeaderValue("NIT");
            tablesomosmas.getColumnModel().getColumn(3).setPreferredWidth(200);
            tablesomosmas.getColumnModel().getColumn(3).setHeaderValue("IPS");
            tablesomosmas.getColumnModel().getColumn(4).setPreferredWidth(200);
            tablesomosmas.getColumnModel().getColumn(4).setHeaderValue("Numero factura");
            tablesomosmas.getColumnModel().getColumn(5).setPreferredWidth(200);
            tablesomosmas.getColumnModel().getColumn(5).setHeaderValue("Fecha prestacion");
        }

        btndetallessomos.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btndetallessomos.setText("Ver detalles de las facturas");
        btndetallessomos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndetallessomosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(btndetallessomos, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btndetallessomos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "BD Integra", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jScrollPane5.setPreferredSize(new java.awt.Dimension(2500, 100));

        tableintegra.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero de radicado", "NIT", "IPS", "Numero de factura", "Valor factura", "Valor pagado", "Valor glosa", "Valor registrado", "Valor copago", "Valor descuento", "Observacion radicacion", "Estado factura", "Motivo devolucion", "Valor nota debito", "Valor nota credito", "null"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableintegra.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableintegra.setPreferredSize(new java.awt.Dimension(2500, 90));
        tableintegra.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tableintegra.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane5.setViewportView(tableintegra);
        if (tableintegra.getColumnModel().getColumnCount() > 0) {
            tableintegra.getColumnModel().getColumn(0).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(0).setHeaderValue("Id");
            tableintegra.getColumnModel().getColumn(1).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(1).setHeaderValue("cm_facturas_id");
            tableintegra.getColumnModel().getColumn(2).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(2).setHeaderValue("NIT");
            tableintegra.getColumnModel().getColumn(3).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(3).setHeaderValue("IPS");
            tableintegra.getColumnModel().getColumn(4).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(4).setHeaderValue("Numero factura");
            tableintegra.getColumnModel().getColumn(5).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(5).setHeaderValue("Fecha prestacion");
            tableintegra.getColumnModel().getColumn(6).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(6).setHeaderValue("Fecha radicacion");
            tableintegra.getColumnModel().getColumn(7).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(7).setHeaderValue("Valor pendiente");
            tableintegra.getColumnModel().getColumn(8).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(8).setHeaderValue("Valor inicial glosa");
            tableintegra.getColumnModel().getColumn(9).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(9).setHeaderValue("Tipo auditoria");
            tableintegra.getColumnModel().getColumn(10).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(10).setHeaderValue("Valor factura");
            tableintegra.getColumnModel().getColumn(11).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(11).setHeaderValue("Valor pagado factura");
            tableintegra.getColumnModel().getColumn(12).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(12).setHeaderValue("Valor copago");
            tableintegra.getColumnModel().getColumn(13).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(13).setHeaderValue("Valor bruto");
            tableintegra.getColumnModel().getColumn(14).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(14).setHeaderValue("Estado factura");
            tableintegra.getColumnModel().getColumn(15).setPreferredWidth(200);
            tableintegra.getColumnModel().getColumn(15).setHeaderValue("Numero de contrato");
        }

        jScrollPane4.setViewportView(jScrollPane5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                .addGap(49, 49, 49))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)), "BD Productiva", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setFocusable(false);
        jScrollPane1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jScrollPane1.setHorizontalScrollBar(null);
        jScrollPane1.setPreferredSize(new java.awt.Dimension(2500, 100));
        jScrollPane1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jScrollPane1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        tableproductivo.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tableproductivo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "cm_rips_cargas_ id", "NIT", "IPS", "Numero factura", "Fecha prestacion", "Fecha radicacion", "Valor pendiente", "Valor inicial glosa", "Tipo auditoria", "Valor factura", "Valor pagado factura", "Valor copago", "Valor bruto", "Cuota moderradora", "Estado factura", "Numero de contrato", "Fecha de creacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableproductivo.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableproductivo.setColumnSelectionAllowed(true);
        tableproductivo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableproductivo.setPreferredSize(new java.awt.Dimension(2600, 90));
        tableproductivo.setRowSelectionAllowed(false);
        tableproductivo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tableproductivo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tableproductivo.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableproductivo);
        tableproductivo.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tableproductivo.getColumnModel().getColumnCount() > 0) {
            tableproductivo.getColumnModel().getColumn(0).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(1).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(2).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(3).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(4).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(5).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(6).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(7).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(8).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(9).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(10).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(11).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(12).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(13).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(14).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(15).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(16).setPreferredWidth(200);
            tableproductivo.getColumnModel().getColumn(17).setPreferredWidth(250);
        }

        jScrollPane3.setViewportView(jScrollPane1);

        btnDetallesFacturaProductivo.setText("Ver detalles facturas");
        btnDetallesFacturaProductivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetallesFacturaProductivoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1166, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(btnDetallesFacturaProductivo, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDetallesFacturaProductivo)
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1188, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumFacturaActionPerformed

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnbuscarActionPerformed

    private void jScrollPane1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jScrollPane1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1AncestorAdded

    private void btndetallessomosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndetallessomosActionPerformed
     FacturaDetalles FacturaDetalles=new FacturaDetalles(busqueda);
     FacturaDetalles.setVisible(true);
     
    }//GEN-LAST:event_btndetallessomosActionPerformed

    private void btnDetallesFacturaProductivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetallesFacturaProductivoActionPerformed
      FacturaDetallesProductivo FacturaDetallesProductivo =new FacturaDetallesProductivo();
      FacturaDetallesProductivo.setVisible(true);
    }//GEN-LAST:event_btnDetallesFacturaProductivoActionPerformed

    public static void main(String args[]) {//metodo pricipal
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {

                new Principal().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetallesFacturaProductivo;
    private javax.swing.JButton btnbuscar;
    private javax.swing.JButton btndetallessomos;
    private conexionesBD.ConexionProductivo conexion1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblnit;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JTable tableintegra;
    private javax.swing.JTable tableproductivo;
    private javax.swing.JTable tablesomosmas;
    private javax.swing.JTextField txtNumFactura;
    private javax.swing.JTextField txtfnit;
    // End of variables declaration//GEN-END:variables
}
