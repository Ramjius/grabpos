
package eaterypos.reports;

import eaterypos.Items;
import eaterypos.Login;
import eaterypos.OrderItems;
import eaterypos.Orders;
import eaterypos.Sale;

/**
 *
 * @author ramgm
 */
public class sales_report extends javax.swing.JFrame {

    /**
     * Creates new form sales_report
     */
    public sales_report() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MenuPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Sale = new javax.swing.JButton();
        ItemsBtn = new javax.swing.JButton();
        Orders = new javax.swing.JButton();
        OrdersItemsBtn = new javax.swing.JButton();
        ReportsBtn1 = new javax.swing.JButton();
        ReportsBtn = new javax.swing.JButton();
        ReportsBtn2 = new javax.swing.JButton();
        Logout = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        fromLabel1 = new javax.swing.JLabel();
        toLabel = new javax.swing.JLabel();
        sdate = new com.toedter.calendar.JDateChooser();
        edate = new com.toedter.calendar.JDateChooser();
        fromLabel = new javax.swing.JLabel();
        selectTable = new javax.swing.JComboBox<>();
        usernameTxt = new javax.swing.JLabel();
        username = new javax.swing.JTextField();
        FetchSales = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MenuPanel.setBackground(new java.awt.Color(5, 76, 74));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/80x80.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("REPORTS");

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

        Orders.setBackground(new java.awt.Color(249, 188, 44));
        Orders.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        Orders.setForeground(new java.awt.Color(12, 18, 35));
        Orders.setText("ORDERS");
        Orders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrdersActionPerformed(evt);
            }
        });

        OrdersItemsBtn.setBackground(new java.awt.Color(249, 188, 44));
        OrdersItemsBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        OrdersItemsBtn.setForeground(new java.awt.Color(12, 18, 35));
        OrdersItemsBtn.setText("ORDER ITEMS");
        OrdersItemsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrdersItemsBtnActionPerformed(evt);
            }
        });

        ReportsBtn1.setBackground(new java.awt.Color(249, 188, 44));
        ReportsBtn1.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        ReportsBtn1.setForeground(new java.awt.Color(12, 18, 35));
        ReportsBtn1.setText("EXPENSES");
        ReportsBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportsBtn1ActionPerformed(evt);
            }
        });

        ReportsBtn.setBackground(new java.awt.Color(249, 188, 44));
        ReportsBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        ReportsBtn.setForeground(new java.awt.Color(12, 18, 35));
        ReportsBtn.setText("REPORTS");
        ReportsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportsBtnActionPerformed(evt);
            }
        });

        ReportsBtn2.setBackground(new java.awt.Color(249, 188, 44));
        ReportsBtn2.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        ReportsBtn2.setForeground(new java.awt.Color(12, 18, 35));
        ReportsBtn2.setText("PRINT");
        ReportsBtn2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ReportsBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportsBtn2ActionPerformed(evt);
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

        javax.swing.GroupLayout MenuPanelLayout = new javax.swing.GroupLayout(MenuPanel);
        MenuPanel.setLayout(MenuPanelLayout);
        MenuPanelLayout.setHorizontalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MenuPanelLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ItemsBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Orders, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(OrdersItemsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ReportsBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ReportsBtn1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ReportsBtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Logout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(Sale, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50))
        );
        MenuPanelLayout.setVerticalGroup(
            MenuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MenuPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(28, 28, 28)
                .addComponent(Sale)
                .addGap(18, 18, 18)
                .addComponent(ItemsBtn)
                .addGap(18, 18, 18)
                .addComponent(Orders)
                .addGap(18, 18, 18)
                .addComponent(OrdersItemsBtn)
                .addGap(18, 18, 18)
                .addComponent(ReportsBtn1)
                .addGap(18, 18, 18)
                .addComponent(ReportsBtn)
                .addGap(18, 18, 18)
                .addComponent(ReportsBtn2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Logout)
                .addGap(22, 22, 22))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 120, 120));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("SALES REPORT");

        jSeparator2.setForeground(new java.awt.Color(0, 120, 120));

        jPanel2.setBackground(new java.awt.Color(249, 188, 44));

        fromLabel1.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        fromLabel1.setForeground(new java.awt.Color(0, 120, 120));
        fromLabel1.setText("FROM DATE:");

        toLabel.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        toLabel.setForeground(new java.awt.Color(0, 120, 120));
        toLabel.setText("TO DATE:");

        sdate.setBackground(new java.awt.Color(0, 120, 120));
        sdate.setForeground(new java.awt.Color(249, 188, 44));

        edate.setBackground(new java.awt.Color(0, 120, 120));
        edate.setForeground(new java.awt.Color(249, 188, 44));

        fromLabel.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        fromLabel.setForeground(new java.awt.Color(0, 120, 120));
        fromLabel.setText("SELECT TABLE:");

        selectTable.setBackground(new java.awt.Color(0, 120, 120));
        selectTable.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        selectTable.setForeground(new java.awt.Color(249, 188, 44));
        selectTable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Orders (Sales)", "Order Items", "Expenses" }));

        usernameTxt.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        usernameTxt.setForeground(new java.awt.Color(0, 120, 120));
        usernameTxt.setText("ENTER USERNAME:");

        username.setBackground(new java.awt.Color(0, 120, 120));
        username.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        username.setForeground(new java.awt.Color(249, 188, 44));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(edate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fromLabel1)
                    .addComponent(fromLabel)
                    .addComponent(usernameTxt)
                    .addComponent(toLabel)
                    .addComponent(sdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(selectTable, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(58, Short.MAX_VALUE)
                .addComponent(fromLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fromLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(toLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(edate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usernameTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        FetchSales.setBackground(new java.awt.Color(0, 120, 120));
        FetchSales.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        FetchSales.setForeground(new java.awt.Color(249, 188, 44));
        FetchSales.setText("FETCH");
        FetchSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FetchSalesActionPerformed(evt);
            }
        });

        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.setFont(new java.awt.Font("Cambria", 2, 12)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(0, 120, 120));
        jTextField2.setText("Generates a list from the selected table based on the selected date range. ");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(81, 81, 81)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(70, 70, 70)
                                    .addComponent(FetchSales, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(134, 134, 134)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(FetchSales, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(MenuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MenuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(657, 608));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void SaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaleActionPerformed
        // TODO add your handling code here:
        new Sale().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_SaleActionPerformed

    private void ItemsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemsBtnActionPerformed
        // TODO add your handling code here:
        new Items().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ItemsBtnActionPerformed

    private void OrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrdersActionPerformed
        // TODO add your handling code here:
        new Orders().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_OrdersActionPerformed

    private void OrdersItemsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrdersItemsBtnActionPerformed
        // TODO add your handling code here:
        new OrderItems().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_OrdersItemsBtnActionPerformed

    private void ReportsBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportsBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReportsBtn1ActionPerformed

    private void ReportsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportsBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReportsBtnActionPerformed

    private void ReportsBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportsBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReportsBtn2ActionPerformed

    private void LogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseClicked
        // TODO add your handling code here:
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogoutMouseClicked

    private void LogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutActionPerformed
        // TODO add your handling code here:
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogoutActionPerformed

    private void FetchSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FetchSalesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FetchSalesActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

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
            java.util.logging.Logger.getLogger(sales_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(sales_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(sales_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(sales_report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new sales_report().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton FetchSales;
    private javax.swing.JButton ItemsBtn;
    private javax.swing.JButton Logout;
    private javax.swing.JPanel MenuPanel;
    private javax.swing.JButton Orders;
    private javax.swing.JButton OrdersItemsBtn;
    private javax.swing.JButton ReportsBtn;
    private javax.swing.JButton ReportsBtn1;
    private javax.swing.JButton ReportsBtn2;
    private javax.swing.JButton Sale;
    private com.toedter.calendar.JDateChooser edate;
    private javax.swing.JLabel fromLabel;
    private javax.swing.JLabel fromLabel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField2;
    private com.toedter.calendar.JDateChooser sdate;
    private javax.swing.JComboBox<String> selectTable;
    private javax.swing.JLabel toLabel;
    private javax.swing.JTextField username;
    private javax.swing.JLabel usernameTxt;
    // End of variables declaration//GEN-END:variables
}