/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracion_mascotas;

import Controladores.ClienteJpaController;
import Controladores.EspecieJpaController;
import Controladores.MascotaJpaController;
import Controladores.MascotaJpaController;
import Controladores.RazaJpaController;
import Controladores.exceptions.NonexistentEntityException;
import Entidades.Cliente;
import Entidades.Mascota;
import Entidades.Raza;
import Entidades.*;
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
public class formMascotas extends javax.swing.JFrame {

    //Instancia de controlador clientes
    ClienteJpaController clienteController = new ClienteJpaController();
    Cliente miCliente = new Cliente();

    //Instancia de controlador Razas
    RazaJpaController razaController = new RazaJpaController();
    List<Raza> razas = razaController.findRazaEntities();

    //Instancia de controlador de mascotas
    EspecieJpaController especieController = new EspecieJpaController();
    List<Especie> especies = especieController.findEspecieEntities();

    //instancia de controlador Mascotas
    MascotaJpaController masController = new MascotaJpaController();

    //Instancia de Mascota 
    Mascota mas = new Mascota();

    public formMascotas() {
        initComponents();
        CrearModeloMascotas();
        Cargar_infoMascotas();
        CrearModeloClientes();
        Cargar_infoClientes();

        //Esconder boton 
        btn_restablecer.setVisible(false);

        //Grupo radio button Clientes
        buttonGroup1.add(rdb_id);
        buttonGroup1.add(rdb_nombre);

        //Grupo radio button Mascotas
        buttonGroup2.add(Rdb_id);
        buttonGroup2.add(Rdb_nombre);

        //Datos de combox especie
        for (int i = 0; i < especies.size(); i++) {
            ComboBox_especie.addItem(especies.get(i).getEspecie());
        }

    }

    // Crear tabla a la hora de leer los datos para clientes
    DefaultTableModel modeloClientes;
    // Crear tabla a la hora de leer los datos para clientes
    DefaultTableModel modeloMascotas;

    private void CrearModeloClientes() {
        try {
            modeloClientes = (new DefaultTableModel(
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
            tablaClientes.setModel(modeloClientes);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.toString() + "error2");
        }
    }

    private void Cargar_infoClientes() {

        try {
            Object Tupla[] = null;

            List<Cliente> listaClientes = clienteController.findClienteEntities();

            for (int i = 0; i < listaClientes.size(); i++) {

                float numero = listaClientes.get(i).getIdentCliente();
                int num = (int) numero;
                //Listar Clientes retornados en la lista
                modeloClientes.addRow(Tupla);
                modeloClientes.setValueAt(num, i, 0);
                modeloClientes.setValueAt(listaClientes.get(i).getNombreCliente(), i, 1);
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    private void CrearModeloMascotas() {
        try {
            modeloMascotas = (new DefaultTableModel(
                    null, new String[]{
                        "id", "Nombre", "Dueño", "Raza"}) {

                Class[] types = new Class[]{
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class
                };
                boolean[] canEdit = new boolean[]{
                    false, false, false, false
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
            tablaMascotas.setModel(modeloMascotas);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.toString() + "error2");
        }
    }

    //llenar tabla con mascotas
    private void Cargar_infoMascotas() {

        try {

            Object Tupla[] = null;

            List<Object[]> listaMascotas = masController.getAllMascotas_join();

            int i = 0;
            for (Object[] result : listaMascotas) {

                modeloMascotas.addRow(Tupla);
                modeloMascotas.setValueAt(result[0], i, 0);
                modeloMascotas.setValueAt(result[1], i, 1);
                modeloMascotas.setValueAt(result[2], i, 2);
                modeloMascotas.setValueAt(result[3], i, 3);
                i++;
            }
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    //limpiar tabla para poner registros de consulta
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jButton_Guardar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btn_restablecer = new javax.swing.JButton();
        txt_dueño = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMascotas = new javax.swing.JTable();
        btn_buscar = new javax.swing.JButton();
        txt_nombreMascota = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        ComboBox_raza = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        ComboBox_especie = new javax.swing.JComboBox<>();
        Rdb_nombre = new javax.swing.JRadioButton();
        Rdb_id = new javax.swing.JRadioButton();
        btn_eliminar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_select = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        Txt_idMascota = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        btn_selectDueno = new javax.swing.JButton();
        rdb_nombre = new javax.swing.JRadioButton();
        rdb_id = new javax.swing.JRadioButton();
        txt_buscarDueno = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btn_buscarDueno = new javax.swing.JButton();
        txt_restablecerClientes = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        btn_regresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Administrar Mascotas"));

        jButton_Guardar.setText("Guardar");
        jButton_Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GuardarActionPerformed(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(102, 102, 102));

        btn_restablecer.setText("Restablecer ");
        btn_restablecer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_restablecerActionPerformed(evt);
            }
        });

        txt_dueño.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dueñoActionPerformed(evt);
            }
        });

        jLabel4.setText("Raza:");

        jLabel3.setText("Dueño:");

        tablaMascotas.setBackground(java.awt.Color.lightGray);
        tablaMascotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaMascotas);

        btn_buscar.setText("Buscar");
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });

        txt_nombreMascota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombreMascotaActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre Mascota:");

        ComboBox_raza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox_razaActionPerformed(evt);
            }
        });

        jLabel1.setText("Especie");

        ComboBox_especie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBox_especieActionPerformed(evt);
            }
        });

        Rdb_nombre.setText("Nombre");

        Rdb_id.setText("ID");
        Rdb_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Rdb_idActionPerformed(evt);
            }
        });

        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        btn_actualizar.setText("Actualizar");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });

        btn_select.setText("Seleccionar");
        btn_select.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_selectActionPerformed(evt);
            }
        });

        jLabel6.setText("ID Mascota");

        Txt_idMascota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Txt_idMascotaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_dueño, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                            .addComponent(txt_nombreMascota, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ComboBox_raza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Rdb_id)
                                .addGap(29, 29, 29)
                                .addComponent(Rdb_nombre))
                            .addComponent(ComboBox_especie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Txt_idMascota))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton_Guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_actualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btn_restablecer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_select, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton_Guardar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_nombreMascota)
                        .addComponent(btn_buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Rdb_nombre)
                    .addComponent(Rdb_id))
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_dueño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_eliminar)
                    .addComponent(btn_actualizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(Txt_idMascota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(btn_select)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ComboBox_especie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_restablecer)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ComboBox_raza, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Clientes"));

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
        jScrollPane2.setViewportView(tablaClientes);

        btn_selectDueno.setText("Seleccionar");
        btn_selectDueno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_selectDuenoActionPerformed(evt);
            }
        });

        rdb_nombre.setText("Nombre");
        rdb_nombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdb_nombreActionPerformed(evt);
            }
        });

        rdb_id.setText("Id");
        rdb_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdb_idActionPerformed(evt);
            }
        });

        jLabel5.setText("Buscar por:");

        btn_buscarDueno.setText("Buscar");
        btn_buscarDueno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarDuenoActionPerformed(evt);
            }
        });

        txt_restablecerClientes.setText("Restablecer");
        txt_restablecerClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_restablecerClientesActionPerformed(evt);
            }
        });

        jSeparator2.setForeground(new java.awt.Color(102, 102, 102));

        btn_regresar.setBackground(new java.awt.Color(102, 255, 0));
        btn_regresar.setText("Regresar");
        btn_regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(rdb_id)
                                .addGap(18, 18, 18)
                                .addComponent(rdb_nombre))
                            .addComponent(txt_buscarDueno, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_regresar, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btn_selectDueno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_buscarDueno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txt_restablecerClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_buscarDueno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_buscarDueno))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btn_selectDueno))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdb_id)
                            .addComponent(rdb_nombre))))
                .addGap(18, 18, 18)
                .addComponent(txt_restablecerClientes)
                .addGap(18, 18, 18)
                .addComponent(btn_regresar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rdb_nombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdb_nombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdb_nombreActionPerformed

    private void rdb_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdb_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdb_idActionPerformed

    private void btn_buscarDuenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarDuenoActionPerformed

        if (rdb_id.isSelected() == true) {

            Float id = Float.parseFloat(txt_buscarDueno.getText());
            miCliente = clienteController.findCliente(id);

            if (miCliente != null) {

                txt_dueño.setText(miCliente.getIdentCliente().toString());

            } else {

                JOptionPane.showMessageDialog(null, "El usuario no se encuentra en la base de datos");
            }
        } else if (rdb_nombre.isSelected() == true) {

            String nombre = txt_buscarDueno.getText();
            List<Cliente> cl = clienteController.findCliente(nombre);
            if (cl.isEmpty() == false) {

                Object Tupla[] = null;
                limpiarTabla(tablaClientes);
                for (int i = 0; i < cl.size(); i++) {

                    //Listar Clientes retornados en la lista
                    modeloClientes.addRow(Tupla);
                    modeloClientes.setValueAt(cl.get(i).getIdentCliente(), i, 0);
                    modeloClientes.setValueAt(cl.get(i).getNombreCliente(), i, 1);

                }

                btn_restablecer.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "El usuario no se encuentra en la base de datos");
            }

        } else {

            JOptionPane.showMessageDialog(null, "Seleccione un criterio de busqueda en el radio boton");
        }
    }//GEN-LAST:event_btn_buscarDuenoActionPerformed

    private void btn_selectDuenoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_selectDuenoActionPerformed

        Integer id_cliente = (int) tablaClientes.getValueAt(tablaClientes.getSelectedRow(), 0);

        txt_dueño.setText(id_cliente.toString());
    }//GEN-LAST:event_btn_selectDuenoActionPerformed

    private void txt_restablecerClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_restablecerClientesActionPerformed

        Cargar_infoClientes();
        btn_restablecer.setVisible(false);

    }//GEN-LAST:event_txt_restablecerClientesActionPerformed

    private void ComboBox_especieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox_especieActionPerformed

        String idEspecie = (String) ComboBox_especie.getSelectedItem();
        ComboBox_raza.removeAllItems();
        List<Object[]> list = razaController.RazaXespecie(idEspecie);
        for (Object[] result : list) {

            ComboBox_raza.addItem(result[0].toString() + "-" + result[1].toString());

        }
    }//GEN-LAST:event_ComboBox_especieActionPerformed

    private void ComboBox_razaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBox_razaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBox_razaActionPerformed

    private void txt_nombreMascotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombreMascotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombreMascotaActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        String petName = txt_nombreMascota.getText();
        int i = 0;
        Object Tupla[] = null;
        List<Object[]> listaMascotas;

        if (!(petName.isEmpty()) && Rdb_nombre.isSelected()) {

            listaMascotas = masController.findMascota(petName);
            limpiarTabla(tablaMascotas);
            if (listaMascotas.isEmpty()) {
                Cargar_infoMascotas();
            } else {

                for (Object[] result : listaMascotas) {

                    modeloMascotas.addRow(Tupla);
                    modeloMascotas.setValueAt(result[0], i, 0);
                    modeloMascotas.setValueAt(result[1], i, 1);
                    modeloMascotas.setValueAt(result[2], i, 2);
                    modeloMascotas.setValueAt(result[3], i, 3);
                    i++;
                }

                btn_restablecer.setVisible(true);
            }

        } else {

            if (!(petName.isEmpty()) && Rdb_id.isSelected()) {

                try {

                    Long idmascota = Long.parseLong(txt_nombreMascota.getText());
                    listaMascotas = masController.findMascota(idmascota);
                    if (listaMascotas.isEmpty()) {

                        JOptionPane.showMessageDialog(null, "La mascota no esta registrada");
                        Cargar_infoMascotas();

                    } else {

                        limpiarTabla(tablaMascotas);
                        for (Object[] result : listaMascotas) {

                            modeloMascotas.addRow(Tupla);
                            modeloMascotas.setValueAt(result[0], i, 0);
                            modeloMascotas.setValueAt(result[1], i, 1);
                            modeloMascotas.setValueAt(result[2], i, 2);
                            modeloMascotas.setValueAt(result[3], i, 3);
                            i++;
                        }

                        btn_restablecer.setVisible(true);
                    }

                } catch (Exception e) {

                    throw e;
                }

            }

        }


    }//GEN-LAST:event_btn_buscarActionPerformed

    private void txt_dueñoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dueñoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dueñoActionPerformed

    private void btn_restablecerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_restablecerActionPerformed
        Cargar_infoMascotas();
        btn_restablecer.setVisible(false);
    }//GEN-LAST:event_btn_restablecerActionPerformed

    private void jButton_GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GuardarActionPerformed
        // TODO add your handling code here:
        String rz = ComboBox_raza.getItemAt(ComboBox_raza.getSelectedIndex());
        String[] rzArray = rz.split("-");

        // Objeto Raza 
        Long idrz = Long.valueOf(rzArray[0]);
        Raza miRaza = razaController.findRaza(idrz);

        //Objeto cliente
        float idc = Float.parseFloat(txt_dueño.getText());
        Cliente myCliente = clienteController.findCliente(idc);

        //Instanciar Objeto a Mascota a Guardar
        Mascota miMascota = new Mascota();
        miMascota.setNombre(txt_nombreMascota.getText().trim());
        miMascota.setIdRaza(miRaza);
        miMascota.setIdentCliente(myCliente);

        try {
            masController.create(miMascota);
            JOptionPane.showMessageDialog(null, "El " + miRaza.getRaza() + " " + miMascota.getNombre() + " Ha sido registrado exitosamente");
            Cargar_infoMascotas();
        } catch (Exception ex) {
            Logger.getLogger(formMascotas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton_GuardarActionPerformed

    private void Rdb_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Rdb_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Rdb_idActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        // TODO add your handling code here:

        if (mas.getId() != null) {

            int seleccion = JOptionPane.showOptionDialog(
                    this,
                    "Esta Seguro de eliminar la mascota" + tablaMascotas.getValueAt(tablaMascotas.getSelectedRow(), 1),
                    "Confirmacion",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null, // null para icono por defecto.
                    new Object[]{"Si", "No"}, // null para YES, NO y CANCEL
                    "opcion 1");

            if (seleccion != -1) {

                if (seleccion == 0) {

                    long i = mas.getId();
                    try {
                        masController.destroy(i);
                        Cargar_infoMascotas();
                        JOptionPane.showMessageDialog(null, "El registro se elimino correctamente");
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(formMascotas.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }
        }

    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        // TODO add your handling code here:
        //Instancia Mascota a editar
        Long idmascota = Long.valueOf(Txt_idMascota.getText());
        Mascota Emascota = masController.findMascota_id(idmascota);

        String rz = ComboBox_raza.getItemAt(ComboBox_raza.getSelectedIndex());
        String[] rzArray = rz.split("-");

        // Objeto Raza 
        Long idrz = Long.valueOf(rzArray[0]);
        Raza miRaza = razaController.findRaza(idrz);

        //Objeto cliente
        float idc = Float.parseFloat(txt_dueño.getText());
        Cliente myCliente = clienteController.findCliente(idc);

        //Nuevos parametros de la mascota
        Emascota.setNombre(txt_nombreMascota.getText().trim());
        Emascota.setIdRaza(miRaza);
        Emascota.setIdentCliente(myCliente);

        try {
            masController.edit(Emascota);
            JOptionPane.showMessageDialog(null, "El " + miRaza.getRaza() + " " + Emascota.getNombre() + " Ha sido Editado corrrectamente");
            Cargar_infoMascotas();
        } catch (Exception ex) {
            Logger.getLogger(formMascotas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void btn_selectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_selectActionPerformed
        // TODO add your handling code here:

        try {

            String Petname;
            Long masId = Long.valueOf(tablaMascotas.getValueAt(tablaMascotas.getSelectedRow(), 0).toString());
            Petname = tablaMascotas.getValueAt(tablaMascotas.getSelectedRow(), 1).toString();
            mas.setId(masId);
            mas.setNombre(Petname);
            txt_nombreMascota.setText(Petname);
            Txt_idMascota.setText(masId.toString());
            Txt_idMascota.setEnabled(false);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }//GEN-LAST:event_btn_selectActionPerformed

    private void Txt_idMascotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Txt_idMascotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Txt_idMascotaActionPerformed

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
            java.util.logging.Logger.getLogger(formMascotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formMascotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formMascotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formMascotas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formMascotas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBox_especie;
    private javax.swing.JComboBox<String> ComboBox_raza;
    private javax.swing.JRadioButton Rdb_id;
    private javax.swing.JRadioButton Rdb_nombre;
    private javax.swing.JTextField Txt_idMascota;
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_buscarDueno;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_regresar;
    private javax.swing.JButton btn_restablecer;
    private javax.swing.JButton btn_select;
    private javax.swing.JButton btn_selectDueno;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton_Guardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JRadioButton rdb_id;
    private javax.swing.JRadioButton rdb_nombre;
    private javax.swing.JTable tablaClientes;
    private javax.swing.JTable tablaMascotas;
    private javax.swing.JTextField txt_buscarDueno;
    private javax.swing.JTextField txt_dueño;
    private javax.swing.JTextField txt_nombreMascota;
    private javax.swing.JButton txt_restablecerClientes;
    // End of variables declaration//GEN-END:variables
}
