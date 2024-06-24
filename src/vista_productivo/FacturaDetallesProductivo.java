package vista_productivo;

import java.sql.Connection;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Getset.GetSet;
import java.sql.SQLException;
import vista.Principal;

public class FacturaDetallesProductivo extends javax.swing.JFrame {

    DefaultTableModel mtDetalles;
    DefaultTableModel mtGlosaRespuesta;

    public FacturaDetallesProductivo() {
        initComponents();
        //bucle para aplicarle color a los encabezados y tipo de letra a tabla productiva       
        mtDetalles = (DefaultTableModel) tableDetalles.getModel();
        TableColumnModel modelo_columna_preproductivo = tableDetalles.getColumnModel();
        for (int i = 0; i <=23; i++) {
            TableColumn columnasDetalles = modelo_columna_preproductivo.getColumn(i);
            columnasDetalles.setHeaderRenderer(new CustomTableHeaderRenderer());//se llama el color
        }
        mtGlosaRespuesta = (DefaultTableModel) tableGlosaRespuesta.getModel();
        TableColumnModel glosaRespuestaPro = tableGlosaRespuesta.getColumnModel();
        for (int i = 0; i <= 9; i++) {
           TableColumn columnasglosa = glosaRespuestaPro.getColumn(i);
            columnasglosa.setHeaderRenderer(new CustomTableHeaderRenderer());//se llama el color
        }
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

    public void busquedaDetalles(String idBuscado, String query) {//aqui se busca los datos de la factura en la base de datos somos mas

        Connection con = conexionesBD.ConexionProductivo.getConnection();//conecta a la BD
        if (con != null) {
            try ( PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, idBuscado);
                ResultSet rs = pst.executeQuery();
                mtDetalles.setRowCount(0);//limpia las filas de la tabla
                if (rs.next()) {
                    do {
                        Object[] rowData = {//columnas en las que se va buscar en la BD
                            rs.getInt("id"),
                            rs.getInt("cm_facturas_id"),
                            rs.getString("mae_tipo_documento_valor"),
                            rs.getString("documento"),
                            rs.getString("nombre_completo_afiliado"),
                            rs.getString("ma_servicio_valor"),
                            rs.getDouble("radicado_glosa"),
                            rs.getDouble("valor_copago"),
                            rs.getDouble("valor_facturado"),
                            rs.getDouble("valor_unitario"),
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
                            rs.getDouble("valor_copago_item"),
                            rs.getDouble("valor_glosa"),
                            rs.getString("concepto_contable"),
                            rs.getString("fecha_hora_crea"),};
                        mtDetalles.addRow(rowData);
                    } while (rs.next());
                    System.out.println("Número de filas en la tabla después de agregar datos: " + mtDetalles.getRowCount());//impresiones para validar cuantas columnas cuenta-es para pruebas
                    System.out.println("Número de columnas en la tabla después de agregar datos: " + mtDetalles.getColumnCount());//impresiones para validar cuantas columnas cuenta-es para pruebas
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron resultados en BD productivo, puedes realiazar otra busqueda", "Resultado", JOptionPane.INFORMATION_MESSAGE);

                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al realizar la consulta en BD Somos +", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void buscarDatosGlosa(String numeroid) {
        Connection con = conexionesBD.ConexionProductivo.getConnection();
        if (con != null) {
            String query = "SELECT * FROM cm_glosa_respuestas WHERE cm_facturas_id = ? ";
            try ( PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, numeroid);
                ResultSet rs = pst.executeQuery();
                mtGlosaRespuesta.setRowCount(0);
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
                        mtGlosaRespuesta.addRow(rowData);
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

        panelEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtIdDetalle = new javax.swing.JTextField();
        lblnit = new javax.swing.JLabel();
        txtIdFactura = new javax.swing.JTextField();
        btnPaginaIncial = new javax.swing.JButton();
        btnBusquedafactura = new javax.swing.JButton();
        btnBusquedaDetalle = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableGlosaRespuesta = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableDetalles = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelEncabezado.setBackground(new java.awt.Color(0, 153, 153));
        panelEncabezado.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Busqueda de datos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        panelEncabezado.setForeground(new java.awt.Color(255, 255, 255));
        panelEncabezado.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        panelEncabezado.setPreferredSize(new java.awt.Dimension(1000, 120));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Buscar por numero de id del detalle: ");

        txtIdDetalle.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        txtIdDetalle.setForeground(new java.awt.Color(102, 102, 102));
        txtIdDetalle.setPreferredSize(new java.awt.Dimension(65, 22));
        txtIdDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdDetalleActionPerformed(evt);
            }
        });

        lblnit.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        lblnit.setForeground(new java.awt.Color(255, 255, 255));
        lblnit.setText("Buscar por id de la factura:");

        txtIdFactura.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        txtIdFactura.setForeground(new java.awt.Color(102, 102, 102));
        txtIdFactura.setPreferredSize(new java.awt.Dimension(65, 22));
        txtIdFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdFacturaActionPerformed(evt);
            }
        });

        btnPaginaIncial.setBackground(new java.awt.Color(102, 102, 102));
        btnPaginaIncial.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnPaginaIncial.setForeground(new java.awt.Color(255, 255, 255));
        btnPaginaIncial.setText("Volver a la pagina incial");
        btnPaginaIncial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaginaIncialActionPerformed(evt);
            }
        });

        btnBusquedafactura.setBackground(new java.awt.Color(102, 102, 102));
        btnBusquedafactura.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnBusquedafactura.setForeground(new java.awt.Color(255, 255, 255));
        btnBusquedafactura.setText("Buscar");
        btnBusquedafactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusquedafacturaActionPerformed(evt);
            }
        });

        btnBusquedaDetalle.setBackground(new java.awt.Color(102, 102, 102));
        btnBusquedaDetalle.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        btnBusquedaDetalle.setForeground(new java.awt.Color(255, 255, 255));
        btnBusquedaDetalle.setText("Buscar");
        btnBusquedaDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBusquedaDetalleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEncabezadoLayout = new javax.swing.GroupLayout(panelEncabezado);
        panelEncabezado.setLayout(panelEncabezadoLayout);
        panelEncabezadoLayout.setHorizontalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPaginaIncial, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtIdDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBusquedaDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(lblnit, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIdFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBusquedafactura, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtIdDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblnit)
                    .addComponent(txtIdFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPaginaIncial, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBusquedafactura)
                    .addComponent(btnBusquedaDetalle))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)), "Glosa respuesta", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        jScrollPane3.setPreferredSize(new java.awt.Dimension(2500, 102));

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

        tableGlosaRespuesta.setBackground(new java.awt.Color(255, 255, 255));
        tableGlosaRespuesta.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tableGlosaRespuesta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "cm_facturas_id", "Valor cobre detalle", "Valor facturado", "Valor pagado", "Valor pagado EPS", "Valor pendiente", "Valor aceptado IPS", "Observacion", "Fecha de creacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableGlosaRespuesta.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableGlosaRespuesta.setColumnSelectionAllowed(true);
        tableGlosaRespuesta.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableGlosaRespuesta.setPreferredSize(new java.awt.Dimension(2500, 150));
        tableGlosaRespuesta.setRowSelectionAllowed(false);
        tableGlosaRespuesta.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tableGlosaRespuesta.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableGlosaRespuesta.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tableGlosaRespuesta);
        tableGlosaRespuesta.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tableGlosaRespuesta.getColumnModel().getColumnCount() > 0) {
            tableGlosaRespuesta.getColumnModel().getColumn(0).setPreferredWidth(150);
            tableGlosaRespuesta.getColumnModel().getColumn(1).setPreferredWidth(150);
            tableGlosaRespuesta.getColumnModel().getColumn(2).setPreferredWidth(200);
            tableGlosaRespuesta.getColumnModel().getColumn(3).setPreferredWidth(200);
            tableGlosaRespuesta.getColumnModel().getColumn(4).setPreferredWidth(250);
            tableGlosaRespuesta.getColumnModel().getColumn(5).setPreferredWidth(200);
            tableGlosaRespuesta.getColumnModel().getColumn(6).setPreferredWidth(200);
            tableGlosaRespuesta.getColumnModel().getColumn(7).setPreferredWidth(200);
            tableGlosaRespuesta.getColumnModel().getColumn(8).setPreferredWidth(200);
        }

        jScrollPane3.setViewportView(jScrollPane1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)), "Detalles factura BD productiva", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Verdana", 1, 12), new java.awt.Color(102, 102, 102))); // NOI18N

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setFocusable(false);
        jScrollPane2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jScrollPane2.setHorizontalScrollBar(null);
        jScrollPane2.setPreferredSize(new java.awt.Dimension(3500, 100));
        jScrollPane2.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jScrollPane2AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        tableDetalles.setBackground(new java.awt.Color(255, 255, 255));
        tableDetalles.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        tableDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "cm_facturas_id", "Tipo documento afiliado", "Numero de documento", "Nombre afiliado", "ma_servicio_valor", "Radicado glosa", "Valor copago", "Valor facturado", "Valor unitario", "Valor pagado", "Valor pendiente", "Valor pendiente actual", "Valor aceptado IPS", "Observacion", "Valor pagado EPS", "Porcentaje pagado EPS", "Porcentaje aceptado IPS", "Observacion respuesta detalles", "Fecha de prestacion", "Valor copago item", "Valor glosa", "Concepto contable", "Fecha creacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableDetalles.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableDetalles.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tableDetalles.setPreferredSize(new java.awt.Dimension(2600, 150));
        tableDetalles.setRowSelectionAllowed(false);
        tableDetalles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        tableDetalles.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tableDetalles.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tableDetalles);
        tableDetalles.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tableDetalles.getColumnModel().getColumnCount() > 0) {
            tableDetalles.getColumnModel().getColumn(0).setPreferredWidth(150);
            tableDetalles.getColumnModel().getColumn(1).setPreferredWidth(150);
            tableDetalles.getColumnModel().getColumn(2).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(3).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(4).setPreferredWidth(250);
            tableDetalles.getColumnModel().getColumn(5).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(6).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(7).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(8).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(9).setPreferredWidth(300);
            tableDetalles.getColumnModel().getColumn(9).setHeaderValue("Fecha creacion");
            tableDetalles.getColumnModel().getColumn(10).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(11).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(12).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(13).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(14).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(15).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(16).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(17).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(18).setPreferredWidth(300);
            tableDetalles.getColumnModel().getColumn(19).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(20).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(21).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(22).setPreferredWidth(200);
            tableDetalles.getColumnModel().getColumn(23).setPreferredWidth(200);
        }

        jScrollPane4.setViewportView(jScrollPane2);

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
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 48, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelEncabezado, javax.swing.GroupLayout.DEFAULT_SIZE, 1265, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 281, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(136, 136, 136)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(409, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtIdDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdDetalleActionPerformed
        // TODO add your handling code here
    }//GEN-LAST:event_txtIdDetalleActionPerformed

    private void btnPaginaIncialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaginaIncialActionPerformed
        Principal InterfazIncial=new Principal();
        InterfazIncial.setVisible(true);  
    }//GEN-LAST:event_btnPaginaIncialActionPerformed

    private void jScrollPane1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jScrollPane1AncestorAdded
     
    }//GEN-LAST:event_jScrollPane1AncestorAdded

    private void btnBusquedafacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusquedafacturaActionPerformed

        String numeroIdFactura = txtIdFactura.getText();;
        if (numeroIdFactura.isEmpty()) {//mensaje en caso de que no ingresen datos para la busqueda
            JOptionPane.showMessageDialog(FacturaDetallesProductivo.this, "Por favor ingrese todos los datos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String query = "SELECT * FROM cm_detalles WHERE cm_facturas_id = ? ";
        busquedaDetalles(numeroIdFactura, query);
        buscarDatosGlosa(numeroIdFactura);
    }//GEN-LAST:event_btnBusquedafacturaActionPerformed

    private void btnBusquedaDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBusquedaDetalleActionPerformed
        String query = "SELECT * FROM cm_detalles WHERE id= ? ";
        String numeroIdDetalle = txtIdDetalle.getText();;
        busquedaDetalles(numeroIdDetalle, query);
    }//GEN-LAST:event_btnBusquedaDetalleActionPerformed

    private void txtIdFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdFacturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdFacturaActionPerformed

    private void jScrollPane2AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jScrollPane2AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane2AncestorAdded

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FacturaDetallesProductivo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBusquedaDetalle;
    private javax.swing.JButton btnBusquedafactura;
    private javax.swing.JButton btnPaginaIncial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblnit;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JTable tableDetalles;
    private javax.swing.JTable tableGlosaRespuesta;
    private javax.swing.JTextField txtIdDetalle;
    private javax.swing.JTextField txtIdFactura;
    // End of variables declaration//GEN-END:variables
}
