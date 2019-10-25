/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasi.view.menu.laporan;

import aplikasi.config.FieldLimit;
import aplikasi.config.KoneksiDB;
import aplikasi.config.ValueFormatter;
import aplikasi.controller.TableViewController;
import aplikasi.entity.Aset;
import aplikasi.entity.KategoriAset;
import aplikasi.entity.Kepemilikan;
import aplikasi.entity.StatusAset;
import aplikasi.entity.Users;
import aplikasi.repository.RepoAset;
import aplikasi.repository.RepoKategoriAset;
import aplikasi.repository.RepoLokasiAset;
import aplikasi.repository.RepoKepemilikan;
import aplikasi.repository.RepoStatusAset;
import aplikasi.repository.RepoUsers;
import aplikasi.service.ServiceAset;
import aplikasi.service.ServiceKategoriAset;
import aplikasi.service.ServiceLokasiAset;
import aplikasi.service.ServiceKepemilikan;
import aplikasi.service.ServiceStatusAset;
import aplikasi.service.ServiceUsers;
import aplikasi.view.MainMenuView;
import aplikasi.view.menu.aset.DataAsetView;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author laptop
 */
public class LaporanAset extends javax.swing.JInternalFrame {

    private final MainMenuView menuController;
    private final RepoAset repoAset;
    private final TableViewController tableController;

    private final RepoKategoriAset repoKategori = new ServiceKategoriAset(KoneksiDB.getDataSource());
    private final RepoKepemilikan repoKepemilikan = new ServiceKepemilikan(KoneksiDB.getDataSource());
    private final RepoStatusAset repoStatus = new ServiceStatusAset(KoneksiDB.getDataSource());
    private final RepoLokasiAset repoLokasi = new ServiceLokasiAset(KoneksiDB.getDataSource());
    private final RepoUsers repoUser = new ServiceUsers(KoneksiDB.getDataSource());

    private List<Aset> daftarAset = new ArrayList<>();
    private List<KategoriAset> daftarKategori = new ArrayList<>();
    private List<Kepemilikan> daftarKepemilikan = new ArrayList<>();
    private List<StatusAset> daftarStatus = new ArrayList<>();
    private List<String> daftarLokasi = new ArrayList<>();
    private List<Users> daftarUser = new ArrayList<>();

    private Users p;

    public LaporanAset(MainMenuView menuController, Users p) {
        this.menuController = menuController;
        this.repoAset = new ServiceAset(KoneksiDB.getDataSource());
        this.p = p;
        initComponents();
        this.tableController = new TableViewController(tableView);
        refresDataKategoriAset();
        refresDataStatus();
        refresDataCustomer();
        refresDataUser();
        refresDataLokasi();
        textFieldLimit();
        refreshDataTables();
    }

    public void refreshDataTables() {
        int total = 0;
        try {
            tableController.clearData();
            this.daftarAset = repoAset.findAll();
            for (Aset aset : daftarAset) {
                Object[] row = {aset.getKode(), ValueFormatter.getLocalDateShort(aset.getTanggal().toLocalDate()), aset.getNama(), aset.getKategoriAset().getNama_kategori(),
                    aset.getStatusAset().getStatus(), aset.getLokasiAset().getNama_rak(), aset.getLokasiAset().getLokasi(),
                    aset.getKepemilikan().getNama(), aset.getQty(), aset.getSatuan(), aset.getUsers().getUsername()};
                tableController.getModel().addRow(row);
                total = total + aset.getQty();
            }
            lblTotal.setText(" " + total + " aset dies ditemukan");
            tableController.setContentTableAlignment(Arrays.asList(0, 1, 3, 5, 6, 8, 9, 10));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Tidak dapat mendapatkan data aset", getTitle(), JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(LaporanAset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void textFieldLimit() {
        txtNamaAset.setDocument(new FieldLimit(30));
        txtNamaRak.setDocument(new FieldLimit(10));
    }

    private void refresDataKategoriAset() {
        try {
            txtKategori.removeAllItems();
            txtKategori.addItem("%");
            daftarKategori = repoKategori.findAll();
            for (KategoriAset ka : daftarKategori) {
                txtKategori.addItem(ka.getNama_kategori());
            }
            txtKategori.setSelectedItem("%");
        } catch (SQLException ex) {
            Logger.getLogger(DataAsetView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void refresDataStatus() {
        try {
            txtStatus.removeAllItems();
            txtStatus.addItem("%");
            daftarStatus = repoStatus.findAll();
            for (StatusAset ds : daftarStatus) {
                txtStatus.addItem(ds.getStatus());
            }
            txtStatus.setSelectedItem("%");
        } catch (SQLException ex) {
            Logger.getLogger(DataAsetView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void refresDataCustomer() {
        try {
            txtKepemilikan.removeAllItems();
            txtKepemilikan.addItem("%");
            daftarKepemilikan = repoKepemilikan.findAll();
            for (Kepemilikan ds : daftarKepemilikan) {
                txtKepemilikan.addItem(ds.getNama());
            }
            txtKepemilikan.setSelectedItem("%");
        } catch (SQLException ex) {
            Logger.getLogger(DataAsetView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void refresDataLokasi() {
        try {
            txtLokasi.removeAllItems();
            txtLokasi.addItem("%");
            daftarLokasi = repoLokasi.findNamaLokasi();
            for (String ds : daftarLokasi) {
                txtLokasi.addItem(ds);
            }
            txtLokasi.setSelectedItem("%");
        } catch (SQLException ex) {
            Logger.getLogger(DataAsetView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void refresDataUser() {
        try {
            txtUser.removeAllItems();
            txtUser.addItem("%");
            daftarUser = repoUser.findAll();
            for (Users u : daftarUser) {
                txtUser.addItem(u.getUsername());
            }
            txtUser.setSelectedItem("%");
        } catch (SQLException ex) {
            Logger.getLogger(DataAsetView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void refreshDataTablesWithFilter() {
        int total = 0;
        try {

            tableController.clearData();
            this.daftarAset = repoAset.findFilterAlll(txtNamaAset.getText(), txtKategori.getSelectedItem().toString(), txtStatus.getSelectedItem().toString(), txtNamaRak.getText(), txtLokasi.getSelectedItem().toString(), txtKepemilikan.getSelectedItem().toString(), txtQty.getSelectedItem().toString(), txtUser.getSelectedItem().toString());
            for (Aset aset : daftarAset) {

                Object[] row = {aset.getKode(), aset.getNama(), aset.getKategoriAset().getNama_kategori(),
                    aset.getStatusAset().getStatus(), aset.getLokasiAset().getNama_rak(), aset.getLokasiAset().getLokasi(),
                    aset.getKepemilikan().getNama(), aset.getQty(), aset.getSatuan(), aset.getUsers().getUsername()};
                tableController.getModel().addRow(row);
                total = total + aset.getQty();
            }
            lblTotal.setText(" " + total + " aset dies ditemukan");
            tableController.setContentTableAlignment(Arrays.asList(0, 1, 3, 5, 6, 8, 9, 10));
        } catch (Exception ex) {
//            Logger.getLogger(LaporanDataAset.class.getName()).log(Level.SEVERE, null, ex);
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
        pmnuLihatTransaksi = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        txtTanggalAwal = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txtTanggalAkhir = new com.toedter.calendar.JDateChooser();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnFilter = new javax.swing.JButton();
        lblKategori = new javax.swing.JLabel();
        txtKategori = new javax.swing.JComboBox<String>();
        lblKepemilikan = new javax.swing.JLabel();
        txtKepemilikan = new javax.swing.JComboBox<String>();
        jLabel3 = new javax.swing.JLabel();
        btnProses = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableView = new javax.swing.JTable();

        pmnuLihatTransaksi.setText("Lihat Transaksi");
        pmnuLihatTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pmnuLihatTransaksiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(pmnuLihatTransaksi);

        setTitle("Laporan Aset");

        jPanel1.setPreferredSize(new java.awt.Dimension(721, 60));

        jScrollPane2.setBorder(null);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel2.setMaximumSize(new java.awt.Dimension(327867, 328767));
        jPanel2.setPreferredSize(new java.awt.Dimension(2350, 174));

        jToolBar1.setBorder(null);
        jToolBar1.setRollover(true);

        jLabel1.setText(" Tanggal : ");
        jToolBar1.add(jLabel1);

        txtTanggalAwal.setMaximumSize(new java.awt.Dimension(120, 29));
        txtTanggalAwal.setMinimumSize(new java.awt.Dimension(120, 29));
        txtTanggalAwal.setPreferredSize(new java.awt.Dimension(120, 29));
        jToolBar1.add(txtTanggalAwal);

        jLabel2.setText("s/d");
        jToolBar1.add(jLabel2);

        txtTanggalAkhir.setMaximumSize(new java.awt.Dimension(120, 29));
        txtTanggalAkhir.setMinimumSize(new java.awt.Dimension(120, 29));
        txtTanggalAkhir.setPreferredSize(new java.awt.Dimension(120, 29));
        jToolBar1.add(txtTanggalAkhir);

        jSeparator1.setMaximumSize(new java.awt.Dimension(5, 5));
        jSeparator1.setMinimumSize(new java.awt.Dimension(5, 5));
        jSeparator1.setPreferredSize(new java.awt.Dimension(5, 5));
        jSeparator1.setSeparatorSize(new java.awt.Dimension(5, 5));
        jToolBar1.add(jSeparator1);

        btnFilter.setText(">>");
        btnFilter.setFocusable(false);
        btnFilter.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFilter.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFilterActionPerformed(evt);
            }
        });
        jToolBar1.add(btnFilter);

        lblKategori.setText(" Kategori : ");
        jToolBar1.add(lblKategori);

        txtKategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtKategori.setToolTipText("");
        txtKategori.setMaximumSize(new java.awt.Dimension(125, 29));
        txtKategori.setMinimumSize(new java.awt.Dimension(125, 29));
        txtKategori.setPreferredSize(new java.awt.Dimension(125, 29));
        txtKategori.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtKategoriItemStateChanged(evt);
            }
        });
        txtKategori.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtKategoriMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtKategoriMouseReleased(evt);
            }
        });
        txtKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKategoriActionPerformed(evt);
            }
        });
        txtKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtKategoriKeyPressed(evt);
            }
        });
        jToolBar1.add(txtKategori);

        lblKepemilikan.setText(" Kepemilikan : ");
        jToolBar1.add(lblKepemilikan);

        txtKepemilikan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtKepemilikan.setToolTipText("");
        txtKepemilikan.setMaximumSize(new java.awt.Dimension(180, 29));
        txtKepemilikan.setMinimumSize(new java.awt.Dimension(180, 29));
        txtKepemilikan.setPreferredSize(new java.awt.Dimension(180, 29));
        txtKepemilikan.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txtKepemilikanItemStateChanged(evt);
            }
        });
        jToolBar1.add(txtKepemilikan);

        jLabel3.setText("  ");
        jToolBar1.add(jLabel3);

        btnProses.setText("Proses");
        btnProses.setFocusable(false);
        btnProses.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProses.setMaximumSize(new java.awt.Dimension(100, 29));
        btnProses.setMinimumSize(new java.awt.Dimension(100, 29));
        btnProses.setPreferredSize(new java.awt.Dimension(100, 29));
        btnProses.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesActionPerformed(evt);
            }
        });
        jToolBar1.add(btnProses);

        btnCetak.setText("Cetak");
        btnCetak.setEnabled(false);
        btnCetak.setFocusable(false);
        btnCetak.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCetak.setMaximumSize(new java.awt.Dimension(100, 29));
        btnCetak.setMinimumSize(new java.awt.Dimension(100, 29));
        btnCetak.setPreferredSize(new java.awt.Dimension(100, 29));
        btnCetak.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });
        jToolBar1.add(btnCetak);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 1060, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1290, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 120, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 2068, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Daftar Aset"));
        jPanel3.setMaximumSize(new java.awt.Dimension(721, 52));
        jPanel3.setMinimumSize(new java.awt.Dimension(721, 52));
        jPanel3.setPreferredSize(new java.awt.Dimension(721, 52));

        tableView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode", "Tanggal", "Nama Aset", "Kategori", "Status", "No Rak", "Lokasi Rak", "Kepemilikan", "Qty", "Satuan", "Diinput"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tableViewMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tableView);
        if (tableView.getColumnModel().getColumnCount() > 0) {
            tableView.getColumnModel().getColumn(0).setPreferredWidth(75);
            tableView.getColumnModel().getColumn(0).setMaxWidth(75);
            tableView.getColumnModel().getColumn(1).setPreferredWidth(70);
            tableView.getColumnModel().getColumn(1).setMaxWidth(70);
            tableView.getColumnModel().getColumn(2).setMinWidth(150);
            tableView.getColumnModel().getColumn(2).setPreferredWidth(150);
            tableView.getColumnModel().getColumn(3).setPreferredWidth(150);
            tableView.getColumnModel().getColumn(3).setMaxWidth(150);
            tableView.getColumnModel().getColumn(5).setPreferredWidth(80);
            tableView.getColumnModel().getColumn(5).setMaxWidth(80);
            tableView.getColumnModel().getColumn(6).setPreferredWidth(80);
            tableView.getColumnModel().getColumn(6).setMaxWidth(80);
            tableView.getColumnModel().getColumn(8).setPreferredWidth(50);
            tableView.getColumnModel().getColumn(8).setMaxWidth(50);
            tableView.getColumnModel().getColumn(9).setPreferredWidth(50);
            tableView.getColumnModel().getColumn(9).setMaxWidth(50);
            tableView.getColumnModel().getColumn(10).setPreferredWidth(50);
            tableView.getColumnModel().getColumn(10).setMaxWidth(50);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 2052, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void pmnuLihatTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pmnuLihatTransaksiActionPerformed
        if (tableController.isSelected()) {
            Aset aset = daftarAset.get(tableController.getRowSelected());
            LaporanLihatTransaksi view = new LaporanLihatTransaksi(menuController, aset, true);
            view.setLocationRelativeTo(null);
            view.setTitle("Histori Transaksi");
            view.setResizable(false);
            view.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Data Aset belum dipilih!", getTitle(), JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_pmnuLihatTransaksiActionPerformed

    private void btnFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFilterActionPerformed
        if (btnFilter.getText().equals(">>")) {
            btnFilter.setText("<<");
            showFilter();
        } else {
            btnFilter.setText(">>");
            txtKategori.setSelectedIndex(0);
            txtKepemilikan.setSelectedIndex(0);
            hideFilter();
        }
    }//GEN-LAST:event_btnFilterActionPerformed

    private void txtKategoriItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtKategoriItemStateChanged
        //        refreshDataTablesWithFilter();
    }//GEN-LAST:event_txtKategoriItemStateChanged

    private void txtKategoriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtKategoriMouseClicked

    }//GEN-LAST:event_txtKategoriMouseClicked

    private void txtKategoriMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtKategoriMouseReleased

    }//GEN-LAST:event_txtKategoriMouseReleased

    private void txtKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKategoriActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKategoriActionPerformed

    private void txtKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKategoriKeyPressed

    }//GEN-LAST:event_txtKategoriKeyPressed

    private void txtKepemilikanItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txtKepemilikanItemStateChanged
        //        refreshDataTablesWithFilter();
    }//GEN-LAST:event_txtKepemilikanItemStateChanged

    private void btnProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesActionPerformed
        String valueTglAwal = ValueFormatter.getDateSQL(txtTanggalAwal.getDate());
        String valueTglAkhir = ValueFormatter.getDateSQL(txtTanggalAkhir.getDate());
        refreshDataPeminjaman(Date.valueOf(valueTglAwal), Date.valueOf(valueTglAkhir));
    }//GEN-LAST:event_btnProsesActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        if (daftarPeminjamanDetail.size() > 0) {
            try {
                String url = "/laporan/PeminjamanAset.jasper";
                Map<String, Object> map = new HashMap<>();
                map.put("tglAwal", txtTanggalAwal.getDate());
                map.put("tglAkhir", txtTanggalAkhir.getDate());
                map.put("pengguna", p.getNama());
                map.put("jabatan", p.getJabatan().toString());
                JasperPrint print = JasperFillManager.fillReport(
                    getClass().getResourceAsStream(url),
                    map,
                    new JRBeanCollectionDataSource(daftarPeminjamanDetail));
                JasperViewer view = new JasperViewer(print, false);
                view.setLocationRelativeTo(null);
                view.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                view.setVisible(true);
            } catch (JRException ex) {
                Logger.getLogger(LaporanSirkulasiAsetView.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Data belum diproses", getTitle(), JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnCetakActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnFilter;
    private javax.swing.JButton btnProses;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblKategori;
    private javax.swing.JLabel lblKepemilikan;
    private javax.swing.JMenuItem pmnuLihatTransaksi;
    private javax.swing.JTable tableView;
    private javax.swing.JComboBox<String> txtKategori;
    private javax.swing.JComboBox<String> txtKepemilikan;
    private com.toedter.calendar.JDateChooser txtTanggalAkhir;
    private com.toedter.calendar.JDateChooser txtTanggalAwal;
    // End of variables declaration//GEN-END:variables
}
