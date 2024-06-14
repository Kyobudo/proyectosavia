/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatosProductivoFetcher {

    public void buscarDatosProductivo(DefaultTableModel mt_preproductivo, String numeroFactura, String nit, Connection con, JFrame frame) {
        if (con != null) {
            String query = "SELECT * FROM cm_facturas WHERE numero_facturado = ? AND nit = ?";
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, numeroFactura);
                pst.setString(2, nit);
                ResultSet rs = pst.executeQuery();

                mt_preproductivo.setRowCount(0);//limpia las filas de la tabla
                if (rs.next()) {
                    do {
                        Object[] rowData = {
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
                        mt_preproductivo.addRow(rowData);
                    } while (rs.next());
                    System.out.println("Número de filas en la tabla después de agregar datos: " + mt_preproductivo.getRowCount());
                    System.out.println("Número de columnas en la tabla después de agregar datos: " + mt_preproductivo.getColumnCount());
                } else {
                    JOptionPane.showMessageDialog(frame, "No se encontraron resultados en BD Pre productiva", "Resultado", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error al realizar la consulta en BD Pre productiva ", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

