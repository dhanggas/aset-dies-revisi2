/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi.view.menu.user;

import aplikasi.config.KoneksiDB;
import aplikasi.controller.TableViewController;
import aplikasi.entity.Users;
import aplikasi.repository.RepoUsers;
import aplikasi.service.ServiceUsers;
import aplikasi.view.MainMenuView;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author dhiskar
 */
public class DaftarUserView extends javax.swing.JInternalFrame {

    private final TableViewController tableController;
    private final MainMenuView menuController;
    private List<Users> daftarUser;
    private final RepoUsers repo;
    private final JFrame frame;

    /**
     * Creates new form DaftarAsetView
     *
     * @param menuController
     */
    public DaftarUserView(MainMenuView menuController) {
        initComponents();
        this.menuController = menuController;
        this.tableController = new TableViewController(tableView);
        this.daftarUser = new ArrayList<>();
        this.repo = new ServiceUsers(KoneksiDB.getDataSource());
        this.frame = menuController;
        refreshDataTable();

    }

    public void refreshDataTable() {
        try {
            tableController.clearData();
            daftarUser = repo.findAll();
            for (Users u : daftarUser) {
                Object[] row = {u.getId_user(), u.getNama(), u.getUsername(), u.getJabatan(), u.getStatus()};
                tableController.getModel().addRow(row);
            }
            tableController.setContentTableAlignment(Arrays.asList(0, 1, 2, 3, 4));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Tidak dapat menampilkan data user", "Daftar Useer", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DaftarUserView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void selectLastRow() {
        tableView.setRowSelectionInterval(tableView.getRowCount() - 1, tableView.getRowCount() - 1);
        tableView.scrollRectToVisible(new Rectangle(tableView.getCellRect(tableView.getRowCount() - 1, 0, true)));
    }

    public void refreshDataTableByName() {
        try {
            tableController.clearData();
            daftarUser = repo.findByNama(txtCari.getText());
            for (Users u : daftarUser) {
                Object[] row = {u.getId_user(), u.getNama(), u.getUsername(), u.getJabatan(), u.getStatus()};
                tableController.getModel().addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Tidak dapat menampilkan data user", "Daftar Useer", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(DaftarUserView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        pmnuUbah = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        pmnuTambah = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        btnUbah = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtCari = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableView = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtUsername = new javax.swing.JTextField();
        txtJabatan = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();

        pmnuUbah.setText("Ubah");
        pmnuUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pmnuUbahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(pmnuUbah);
        jPopupMenu1.add(jSeparator1);

        pmnuTambah.setText("Tambah");
        pmnuTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pmnuTambahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(pmnuTambah);

        setTitle("Master User");
        setMinimumSize(new java.awt.Dimension(53, 22));
        setPreferredSize(new java.awt.Dimension(710, 490));

        jToolBar1.setRollover(true);
        jToolBar1.setPreferredSize(new java.awt.Dimension(100, 45));

        btnUbah.setText("Ubah");
        btnUbah.setFocusable(false);
        btnUbah.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUbah.setMaximumSize(new java.awt.Dimension(120, 35));
        btnUbah.setMinimumSize(new java.awt.Dimension(120, 35));
        btnUbah.setPreferredSize(new java.awt.Dimension(120, 35));
        btnUbah.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });
        jToolBar1.add(btnUbah);

        btnTambah.setText("Tambah");
        btnTambah.setToolTipText("Tambah data user baru");
        btnTambah.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTambah.setMaximumSize(new java.awt.Dimension(120, 35));
        btnTambah.setMinimumSize(new java.awt.Dimension(120, 35));
        btnTambah.setPreferredSize(new java.awt.Dimension(120, 35));
        btnTambah.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });
        btnTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                btnTambahKeyReleased(evt);
            }
        });
        jToolBar1.add(btnTambah);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_END);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Daftar User"));

        txtCari.setToolTipText("Pencarian berdasarkan username");
        txtCari.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtCariCaretUpdate(evt);
            }
        });

        jLabel11.setText("Cari");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        tableView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode", "Nama", "Username", "Jabatan", "Status Aktif"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableViewMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableViewMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tableView);
        if (tableView.getColumnModel().getColumnCount() > 0) {
            tableView.getColumnModel().getColumn(0).setMinWidth(50);
            tableView.getColumnModel().getColumn(0).setPreferredWidth(50);
            tableView.getColumnModel().getColumn(0).setMaxWidth(50);
            tableView.getColumnModel().getColumn(3).setPreferredWidth(100);
            tableView.getColumnModel().getColumn(4).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCari, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
        jPanel1.getAccessibleContext().setAccessibleName("Daftar Aset");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Data User"));
        jPanel4.setAutoscrolls(true);
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 429));

        jLabel12.setText("Kode");

        jLabel13.setText("Nama");

        jLabel14.setText("Username");

        jLabel15.setText("Password");

        jLabel16.setText("Jabatan");

        jLabel17.setText("Status Aktif");

        txtKode.setEditable(false);

        txtNama.setEditable(false);

        txtUsername.setEditable(false);

        txtJabatan.setEditable(false);
        txtJabatan.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        txtStatus.setEditable(false);
        txtStatus.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        txtPassword.setEditable(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
                        .addComponent(txtUsername, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtJabatan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                        .addComponent(txtStatus, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(txtPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtJabatan, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(221, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, java.awt.BorderLayout.LINE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        if (tableController.isSelected()) {
            Users u = daftarUser.get(tableController.getRowSelected());
            DataUserView view = new DataUserView(menuController, this, u, true);
            view.setLocationRelativeTo(null);
            view.setResizable(false);
            view.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Data users belum dipilih!", getTitle(), JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        DataUserView view = new DataUserView(menuController, this, true);
        view.setResizable(false);
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }//GEN-LAST:event_btnTambahActionPerformed

    private void txtCariCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtCariCaretUpdate
        refreshDataTableByName();
    }//GEN-LAST:event_txtCariCaretUpdate

    private void tableViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableViewMouseClicked
        if (tableController.isSelected()) {
            Users u = daftarUser.get(tableController.getRowSelected());
            setFields(u);
        } else {
            clearFields();
        }
    }//GEN-LAST:event_tableViewMouseClicked

    private void pmnuUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmnuUbahActionPerformed
        btnUbahActionPerformed(evt);
    }//GEN-LAST:event_pmnuUbahActionPerformed

    private void pmnuTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmnuTambahActionPerformed
        btnTambahActionPerformed(evt);
    }//GEN-LAST:event_pmnuTambahActionPerformed

    private void tableViewMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableViewMouseReleased
        if (evt.isPopupTrigger()) {
            JTable source = (JTable) evt.getSource();
            int row = source.rowAtPoint(evt.getPoint());
            int column = source.columnAtPoint(evt.getPoint());
            if (!source.isRowSelected(row)) {
                source.changeSelection(row, column, false, false);
            }
            jPopupMenu1.show(evt.getComponent(), evt.getX(), evt.getY());
        }
    }//GEN-LAST:event_tableViewMouseReleased

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        if (evt.getClickCount() == 2) {
            txtCari.setText("");
            txtCari.requestFocus();
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void btnTambahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTambahKeyReleased
        if (evt.getKeyCode()== KeyEvent.VK_ENTER) {
            btnTambahActionPerformed(null);
        }
    }//GEN-LAST:event_btnTambahKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem pmnuTambah;
    private javax.swing.JMenuItem pmnuUbah;
    private javax.swing.JTable tableView;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtJabatan;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNama;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

    public void setFields(Users u) {
        this.txtKode.setText(u.getId_user().toString());
        this.txtNama.setText(u.getNama());
        this.txtUsername.setText(u.getUsername());
        this.txtPassword.setText(u.getPassword());
        this.txtJabatan.setText(u.getJabatan().toString());
        this.txtStatus.setText(u.getStatus().toString());
    }

    private void clearFields() {
        this.txtKode.setText("");
        this.txtNama.setText("");
        this.txtUsername.setText("");
        this.txtPassword.setText("");
        this.txtJabatan.setText("");
        this.txtStatus.setText("");
    }

    void btnTambahRequestFocus() {
        btnTambah.requestFocus();
    }
}
