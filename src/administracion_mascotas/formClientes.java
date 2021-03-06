/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracion_mascotas;

import Controladores.ClienteJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Cliente;
import java.awt.HeadlessException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author palac
 */
public class formClientes extends javax.swing.JFrame {

    /**
     * Creates new form formClientes
     */
    public formClientes() {
        initComponents();
        CrearModelo();
        Cargar_infoClientes();
        //Esconder boton 
        btn_restablecer.setVisible(false);
        //Grupo radio button
        buttonGroup2.add(rdb_id);
        buttonGroup2.add(rdb_nombre);

    }

    //Instancia de controlador
    ClienteJpaController clienteController = new ClienteJpaController();
    Cliente miCliente = new Cliente();

    //Cargar la informacion de la BD
    public  void Cargar_infoClientes() {

        try {
            Object Tupla[] = null;
            
            List<Cliente> listaClientes = clienteController.findClienteEntities();

            for (int i = 0; i < listaClientes.size(); i++) {
                
                float numero= listaClientes.get(i).getIdentCliente();
                int num = (int) numero;
                //Listar Clientes retornados en la lista
                modelo.addRow(Tupla);
                modelo.setValueAt(num, i, 0);
                modelo.setValueAt(listaClientes.get(i).getNombreCliente(), i, 1);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    // Crear tabla a la hora de leer los datos
    DefaultTableModel modelo;

    public void CrearModelo() {
        try {
            modelo = (new DefaultTableModel(
                    null, new String[]{
                        "id", "Nombre"}) {

                Class[] types = new Class[]{
                    java.lang.String.class,
                    java.lang.String.class
                };
                boolean[] canEdit = new boolean[]{
                    false, false
                };

                @Override
                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }

                @Override
                public boolean isCellEditable(int rowIndex, int colIndex) {
                    return canEdit[colIndex];
                }
            });
            tablaClientes.setModel(modelo);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.toString() + "error2");
        }
    }
    public void Limpiar()
    {
        txt_id.setText("");
        txt_nombre.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup2 = new javax.swing.ButtonGroup();
        jPopupMenu = new javax.swing.JPopupMenu();
        pMnModificar = new javax.swing.JMenuItem();
        pMnEliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        btn_buscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        txt_id = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_nombre = new javax.swing.JTextField();
        jButton_Guardar = new javax.swing.JButton();
        txt_buscar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        rdb_id = new javax.swing.JRadioButton();
        rdb_nombre = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        btn_restablecer = new javax.swing.JButton();
        btn_borrar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_seleccionar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btn_orderby_name = new javax.swing.JButton();
        btnOrderby_ID = new javax.swing.JButton();
        btn_regresar = new javax.swing.JButton();

        pMnModificar.setText("Modificar");
        pMnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pMnModificarActionPerformed(evt);
            }
        });
        jPopupMenu.add(pMnModificar);

        pMnEliminar.setText("Eliminar");
        pMnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pMnEliminarActionPerformed(evt);
            }
        });
        jPopupMenu.add(pMnEliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Administracion Clientes"));

        btn_buscar.setText("Buscar");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre Cliente:");

        tablaClientes.setBackground(java.awt.Color.lightGray);
        tablaClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaClientes.setColumnSelectionAllowed(true);
        tablaClientes.setComponentPopupMenu(jPopupMenu);
        tablaClientes.setDoubleBuffered(true);
        jScrollPane1.setViewportView(tablaClientes);

        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });

        jLabel3.setText("Identificación:");

        txt_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombreActionPerformed(evt);
            }
        });

        jButton_Guardar.setText("Guardar");
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });

        jLabel4.setText("Buscar por:");

        rdb_id.setText("Id");
        rdb_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdb_idActionPerformed(evt);
            }
        });

        rdb_nombre.setText("Nombre");
        rdb_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdb_nombreActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(102, 102, 102));

        btn_restablecer.setText("Restablecer");
        btn_restablecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_restablecerActionPerformed(evt);
            }
        });

        btn_borrar.setText("Borrar");
        btn_borrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_borrarActionPerformed(evt);
            }
        });

        btn_actualizar.setText("Actualizar");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });

        btn_seleccionar.setText("Seleccionar");
        btn_seleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seleccionarActionPerformed(evt);
            }
        });

        jButton1.setText("Limpiar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btn_orderby_name.setText("Ordenar por nombre");
        btn_orderby_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_orderby_nameActionPerformed(evt);
            }
        });

        btnOrderby_ID.setText("Ordenar por ID");
        btnOrderby_ID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderby_IDActionPerformed(evt);
            }
        });

        btn_regresar.setBackground(new java.awt.Color(204, 255, 102));
        btn_regresar.setText("Regresar");
        btn_regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdb_id)
                                .addGap(18, 18, 18)
                                .addComponent(rdb_nombre)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txt_id)
                            .addComponent(txt_nombre)
                            .addComponent(txt_buscar))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_Guardar, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                            .addComponent(btn_borrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_regresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btn_actualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_seleccionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(19, 19, 19)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_orderby_name)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOrderby_ID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_restablecer, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(txt_nombre))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jButton_Guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_borrar)
                    .addComponent(btn_seleccionar))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_buscar)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdb_id)
                    .addComponent(rdb_nombre)
                    .addComponent(btn_regresar))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_orderby_name)
                    .addComponent(btnOrderby_ID)
                    .addComponent(btn_restablecer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 489, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void txt_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreActionPerformed

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        // TODO add your handling code here:

        //Instancia de cliente
        Cliente cliente = new Cliente();
        String nombre = txt_nombre.getText();
        Float id = Float.parseFloat(txt_id.getText());

        if (nombre != null && id != null) {

            if (clienteController.findCliente(id) != null) {

                JOptionPane.showMessageDialog(null, "El cliente " + clienteController.findCliente(id).getNombreCliente() + " ya esxiste");

            } else {
                cliente.setNombreCliente(nombre);
                cliente.setIdentCliente(id);
                try {
                    clienteController.create(cliente);
                    Cargar_infoClientes();
                    JOptionPane.showMessageDialog(null, "El cliente " + cliente.getNombreCliente() + " Ha sido guardado");
                } catch (Exception ex) {
                    Logger.getLogger(formClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Revise los datos");
        }

    }//GEN-LAST:event_jButton_GuardarActionPerformed

    public void limpiarTabla(JTable tabla) {
        try {
            DefaultTableModel modelo1 = (DefaultTableModel) tabla.getModel();
            int filas = tabla.getRowCount();
            for (int i = 0; filas > i; i++) {
                modelo1.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }
    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed

        if (rdb_id.isSelected() == true) {

            Float id = Float.parseFloat(txt_buscar.getText());
            miCliente = clienteController.findCliente(id);
            if (miCliente != null) {
                txt_nombre.setText(miCliente.getNombreCliente());
                txt_id.setText(miCliente.getIdentCliente().toString());
            } else {

                JOptionPane.showMessageDialog(null, "El usuario no se encuentra en la base de datos");
            }
        } else if (rdb_nombre.isSelected() == true) {

            String nombre = txt_buscar.getText();
            List<Cliente> cl = clienteController.findCliente(nombre);
            if (cl.isEmpty() == false) {

                if (cl.size() == 1) {
                    txt_nombre.setText(cl.get(0).getNombreCliente());
                    txt_id.setText(cl.get(0).getIdentCliente().toString());
                } else {

                    Object Tupla[] = null;
                    limpiarTabla(tablaClientes);
                    for (int i = 0; i < cl.size(); i++) {

                        //Listar Clientes retornados en la lista
                        modelo.addRow(Tupla);
                        modelo.setValueAt(cl.get(i).getIdentCliente(), i, 0);
                        modelo.setValueAt(cl.get(i).getNombreCliente(), i, 1);

                        //System.out.println(listaClientes.get(i).getNombreCliente());
                    }

                    btn_restablecer.setVisible(true);

                }

            } else {
                JOptionPane.showMessageDialog(null, "El usuario no se encuentra en la base de datos");
            }

        } else {

            JOptionPane.showMessageDialog(null, "Seleccione un criterio de busqueda en el radio boton");
        }

    }//GEN-LAST:event_btn_buscarActionPerformed

    private void rdb_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdb_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdb_nombreActionPerformed

    private void rdb_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdb_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdb_idActionPerformed

    private void btn_restablecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_restablecerActionPerformed
        Cargar_infoClientes();
        btn_restablecer.setVisible(false);
        txt_buscar.setText("");
    }//GEN-LAST:event_btn_restablecerActionPerformed

    private void btn_borrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_borrarActionPerformed
        // TODO add your handling code here:
        String id_cliente;
        String id_definitivo[];
        int val;
        if (tablaClientes.getSelectedRowCount() > 0) {
            id_cliente = (tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 0).toString());
            id_definitivo = id_cliente.split("\\.", 2);
        } else {

            id_cliente = txt_id.getText();
            id_definitivo = id_cliente.split("\\.");
        }

        try {
            if (id_cliente != null) {

                int seleccion = JOptionPane.showOptionDialog(
                        this,
                        "Esta Seguro de eliminar el cliente " + tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 1),
                        "Confirmacion",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, // null para icono por defecto.
                        new Object[]{"Si", "No"}, // null para YES, NO y CANCEL
                        "opcion 1");

                if (seleccion != -1) {

                    if (seleccion == 0) {
                        Double idf = Double.parseDouble(id_definitivo[0]);
                        clienteController.destroy(idf);
                        Cargar_infoClientes();

                        JOptionPane.showMessageDialog(null, "El registro se elimino correctamente");

                        Cargar_infoClientes();

                    }

                }
            }
        } catch (HeadlessException e) {

            JOptionPane.showMessageDialog(null, e.getCause());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(formClientes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btn_borrarActionPerformed

    private void btn_seleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seleccionarActionPerformed
        // TODO add your handling code here:
        try {
            String name, id;

            id = tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 0).toString();
            name = tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 1).toString();

            if (tablaClientes.getSelectedRowCount() > 0) {

                if (!(name.isEmpty() && id.isEmpty())) {

                    id = tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 0).toString();
                    name = tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 1).toString();
                    txt_id.setText(id);
                    txt_id.setEnabled(false);
                    txt_nombre.setText(name);
                  
                    
                }
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }

    }//GEN-LAST:event_btn_seleccionarActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        // TODO add your handling code here:
        //Instancia de Objeto a editar
        Cliente Ecliente;
        String name;
        Float id;

        try {
            
            id = Float.parseFloat(txt_id.getText().trim());
            name = txt_nombre.getText().trim();
            Ecliente = clienteController.findCliente(id);
            
            if (!(name.isEmpty() && id.isNaN())) {                
                
                Ecliente.setNombreCliente(name);
                clienteController.edit(Ecliente);
                JOptionPane.showMessageDialog(null, "El cliente " + Ecliente.getNombreCliente() + " Ha sido Actualizado");
                Cargar_infoClientes();
                Limpiar();

            }
            else
            {
                JOptionPane.showMessageDialog(null, "no haz selecionado un dato");
            }

        } catch (Exception ex) {

            Logger.getLogger(formClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        txt_buscar.setText("");
        txt_nombre.setText("");
        txt_id.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_orderby_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_orderby_nameActionPerformed
        // TODO add your handling code here:
        List<Cliente> OrderBy_clientes = clienteController.OrderBy_clientes("nombre");
        Object Tupla[] = null;
        for (int i = 0; i < OrderBy_clientes.size(); i++) {

            //Listar Clientes retornados en la lista
            modelo.addRow(Tupla);
            modelo.setValueAt(OrderBy_clientes.get(i).getIdentCliente(), i, 0);
            modelo.setValueAt(OrderBy_clientes.get(i).getNombreCliente(), i, 1);

            //System.out.println(listaClientes.get(i).getNombreCliente());
        }
    }//GEN-LAST:event_btn_orderby_nameActionPerformed

    private void btnOrderby_IDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderby_IDActionPerformed

        List<Cliente> OrderBy_clientes = clienteController.OrderBy_clientes("id");
        Object Tupla[] = null;
        for (int i = 0; i < OrderBy_clientes.size(); i++) {

            //Listar Clientes retornados en la lista
            modelo.addRow(Tupla);
            modelo.setValueAt(OrderBy_clientes.get(i).getIdentCliente(), i, 0);
            modelo.setValueAt(OrderBy_clientes.get(i).getNombreCliente(), i, 1);

        }
    }//GEN-LAST:event_btnOrderby_IDActionPerformed

    private void pMnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pMnModificarActionPerformed
        int fila=tablaClientes.getSelectedRow();
        if (fila>=0)
        {
            txt_id.setText(tablaClientes.getValueAt(fila, 0).toString());
            txt_nombre.setText(tablaClientes.getValueAt(fila, 1).toString());           
            txt_id.setEnabled(false);
        }
        else{
            JOptionPane.showMessageDialog(null, "por favor seleccione la fila");
        }
    }//GEN-LAST:event_pMnModificarActionPerformed

    private void pMnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pMnEliminarActionPerformed
        int fila=tablaClientes.getSelectedRow();
        if (fila>=0)
        {
            try {
                clienteController.destroy(Double.parseDouble(tablaClientes.getValueAt(fila, 0).toString()));
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(formClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
            Limpiar();
            Cargar_infoClientes();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "No selecciono fila");
        }
        
        
    }//GEN-LAST:event_pMnEliminarActionPerformed

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        // TODO add your handling code here:
        Inicio formInicio = new Inicio();
        this.setVisible(false);
        formInicio.setVisible(true);
    }//GEN-LAST:event_btn_regresarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(formClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOrderby_ID;
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_borrar;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_orderby_name;
    private javax.swing.JButton btn_regresar;
    private javax.swing.JButton btn_restablecer;
    private javax.swing.JButton btn_seleccionar;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton_Guardar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuItem pMnEliminar;
    private javax.swing.JMenuItem pMnModificar;
    private javax.swing.JRadioButton rdb_id;
    private javax.swing.JRadioButton rdb_nombre;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTextField txt_buscar;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nombre;
    // End of variables declaration//GEN-END:variables
}
