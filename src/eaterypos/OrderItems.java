
package eaterypos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import net.proteanit.sql.DbUtils;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author ramjius muhsin
 */
public class OrderItems extends javax.swing.JFrame {

    /**
     * Creates new form OrderItems
     */
    public OrderItems() {
        initComponents();
        ShowItems();
    }
    
    ResultSet Rs = null;
    Connection Con = null;
    Statement St = null;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        OrderItemsTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Sale = new javax.swing.JButton();
        ItemsBtn = new javax.swing.JButton();
        OrdersBtn = new javax.swing.JButton();
        Logout = new javax.swing.JButton();
        Logo = new javax.swing.JLabel();
        OrdersItemsBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        OrderItemsTable.setBackground(new java.awt.Color(5, 76, 74));
        OrderItemsTable.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        OrderItemsTable.setForeground(new java.awt.Color(249, 188, 44));
        OrderItemsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Item Name", "Item Price", "Quantity", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        OrderItemsTable.setRowHeight(30);
        OrderItemsTable.setShowGrid(true);
        jScrollPane1.setViewportView(OrderItemsTable);

        jLabel1.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(12, 18, 35));
        jLabel1.setText("ORDER ITEMS");

        Sale.setBackground(new java.awt.Color(249, 188, 44));
        Sale.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        Sale.setForeground(new java.awt.Color(12, 18, 35));
        Sale.setText("SALE");
        Sale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaleActionPerformed(evt);
            }
        });

        ItemsBtn.setBackground(new java.awt.Color(249, 188, 44));
        ItemsBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        ItemsBtn.setForeground(new java.awt.Color(12, 18, 35));
        ItemsBtn.setText("ITEMS");
        ItemsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemsBtnActionPerformed(evt);
            }
        });

        OrdersBtn.setBackground(new java.awt.Color(249, 188, 44));
        OrdersBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        OrdersBtn.setForeground(new java.awt.Color(12, 18, 35));
        OrdersBtn.setText("ORDERS");
        OrdersBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrdersBtnActionPerformed(evt);
            }
        });

        Logout.setBackground(new java.awt.Color(255, 0, 51));
        Logout.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        Logout.setForeground(new java.awt.Color(12, 18, 35));
        Logout.setText("LOGOUT");
        Logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutMouseClicked(evt);
            }
        });
        Logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutActionPerformed(evt);
            }
        });

        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/80x80.png"))); // NOI18N

        OrdersItemsBtn.setBackground(new java.awt.Color(249, 188, 44));
        OrdersItemsBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        OrdersItemsBtn.setForeground(new java.awt.Color(12, 18, 35));
        OrdersItemsBtn.setText("ORDER ITEMS");
        OrdersItemsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrdersItemsBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Sale, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ItemsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(OrdersBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(OrdersItemsBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(Logo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 532, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Sale)
                        .addGap(18, 18, 18)
                        .addComponent(ItemsBtn)
                        .addGap(18, 18, 18)
                        .addComponent(OrdersBtn)
                        .addGap(18, 18, 18)
                        .addComponent(OrdersItemsBtn)
                        .addGap(18, 18, 18)
                        .addComponent(Logo)
                        .addGap(51, 51, 51)
                        .addComponent(Logout))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
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
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void ShowItems()
    {
    	try {
            Con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/grabdb", "root", "admin");
            St = Con.createStatement();
            Rs = St.executeQuery("SELECT * FROM order_items");
            OrderItemsTable.setModel(DbUtils.resultSetToTableModel(Rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        } finally {
            if (Rs != null) {
                try {
                    Rs.close();
                } catch (SQLException ex) {
                    // Handle close exception if necessary
                }
            }

            if (St != null) {
                try {
                    St.close();
                } catch (SQLException ex) {
                    // Handle close exception if necessary
                }
            }

            if (Con != null) {
                try {
                    Con.close();
                } catch (SQLException ex) {
                    // Handle close exception if necessary
                }
            }
        }
    }
    private void SaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaleActionPerformed
        // Change to Sale
        new Sale().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_SaleActionPerformed

    private void ItemsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemsBtnActionPerformed
        // TODO add your handling code here:
        new Items().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ItemsBtnActionPerformed

    private void OrdersBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrdersBtnActionPerformed
        // TODO add your handling code here:
        new Orders().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_OrdersBtnActionPerformed

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LogoutActionPerformed

    private void LogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseClicked
        // TODO add your handling code here:
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogoutMouseClicked

    private void OrdersItemsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrdersItemsBtnActionPerformed
        // TODO add your handling code here:
        new OrderItems().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_OrdersItemsBtnActionPerformed

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
            java.util.logging.Logger.getLogger(OrderItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderItems.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new OrderItems().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ItemsBtn;
    private javax.swing.JLabel Logo;
    private javax.swing.JButton Logout;
    private javax.swing.JTable OrderItemsTable;
    private javax.swing.JButton OrdersBtn;
    private javax.swing.JButton OrdersItemsBtn;
    private javax.swing.JButton Sale;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
