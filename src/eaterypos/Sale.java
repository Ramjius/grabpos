
package eaterypos;

import java.awt.print.PrinterException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.jar.Attributes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import javax.swing.ImageIcon;

/**
 *
 * @author ramgm
 */
public class Sale extends javax.swing.JFrame {

    /**
     * Creates new form Sale
     */
    public Sale() {
        initComponents();
        ShowItems();
        ItemPrice.setEditable(false);
        ItemName.setEditable(false);
    }
    ResultSet Rs = null, Rs1 = null;
    Connection Con = null;
    Statement St = null, St1 = null;
    
    private void ShowItems()
    {
    	try {
            Con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/grabdb", "root", "admin");
            St = Con.createStatement();
            Rs = St.executeQuery("SELECT * FROM items");
            ItemsList1.setModel(DbUtils.resultSetToTableModel(Rs));
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
    private void clearFields() 
    {
                ItemName.setText("");
                ItemPrice.setText("");
                ItemQty.setSelectedIndex(-1);
                Key = 0;
    }
    
    private void FilterItems() {
        try (Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/grabdb", "root", "admin");
             Statement st = con.createStatement()) {

            String selectedCategory = FilterCategory.getSelectedItem().toString();
            String query;

            if (!"All Categories".equals(selectedCategory)) {
                query = "SELECT * FROM items WHERE Category = '" + selectedCategory + "'";
            } else {
                query = "SELECT * FROM items";
            }
            ResultSet rs = st.executeQuery(query);
            ItemsList1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }
    
    private void InsertSale(java.awt.event.ActionEvent evt) {                                           
        // Check if the JTable is not empty
        if (Sales.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "No items to add to sales.");
            return;
        }

        Connection con = null;
        PreparedStatement pst = null;

        try {
            // Establish connection
            con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/grabdb", "root", "admin");

            // Prepare the insert query
            String insertQuery = "INSERT INTO sales (ItemName, Quantity, ItemPrice, Total) VALUES (?, ?, ?, ?)";
            pst = con.prepareStatement(insertQuery);

            // Iterate over each row in the JTable
            for (int i = 0; i < Sales.getRowCount(); i++) {
                // Assuming column indices: 0 = SaleID, 1 = ItemName, 2 = Quantity, 3 = ItemPrice
                String itemName = Sales.getValueAt(i, 1).toString(); // Column 1 for Item Name
                int itemQty = Integer.parseInt(Sales.getValueAt(i, 2).toString()); // Column 2 for Quantity
                double itemPrice = Double.parseDouble(Sales.getValueAt(i, 3).toString()); // Column 3 for Item Price
                double total = itemQty * itemPrice;

                // Set parameters for the insert query
                pst.setString(1, itemName);
                pst.setInt(2, itemQty);
                pst.setDouble(3, itemPrice);
                pst.setDouble(4, total);

                // Add the current item to the batch
                pst.addBatch();
            }

            // Execute batch update
            int[] rows = pst.executeBatch();

            if (rows.length > 0) {
                JOptionPane.showMessageDialog(this, "Sales Added Successfully");
                ShowItems();
                clearFields(); // Clear text fields after successful insertion
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add sales");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Quantity and Price");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                // Handle close exception if necessary
            }
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ItemsBtn = new javax.swing.JButton();
        OrdersBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Sales = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        ItemName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ItemPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        FilterCategory = new javax.swing.JComboBox<>();
        AddToBill = new javax.swing.JButton();
        LogoutBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ItemsList1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        PrintBtn = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        RemoveItemBtn = new javax.swing.JButton();
        FetchOrderTxt = new javax.swing.JTextField();
        FetchBillBtn = new javax.swing.JButton();
        Logo = new javax.swing.JLabel();
        ItemQty = new javax.swing.JComboBox<>();
        ClearBillBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Total = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(12, 18, 35));
        jLabel1.setText("POS");

        ItemsBtn.setBackground(new java.awt.Color(75, 173, 206));
        ItemsBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        ItemsBtn.setForeground(new java.awt.Color(12, 18, 35));
        ItemsBtn.setText("ITEMS");
        ItemsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemsBtnActionPerformed(evt);
            }
        });

        OrdersBtn.setBackground(new java.awt.Color(75, 173, 206));
        OrdersBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        OrdersBtn.setForeground(new java.awt.Color(12, 18, 35));
        OrdersBtn.setText("ORDERS");
        OrdersBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrdersBtnActionPerformed(evt);
            }
        });

        Sales.setBackground(new java.awt.Color(12, 18, 35));
        Sales.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        Sales.setForeground(new java.awt.Color(247, 153, 95));
        Sales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "Item Price", "Quantity", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        Sales.setRowHeight(30);
        Sales.setShowGrid(true);
        Sales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SalesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Sales);

        jLabel3.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(247, 153, 95));
        jLabel3.setText("ITEM NAME");

        ItemName.setBackground(new java.awt.Color(207, 217, 214));
        ItemName.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        ItemName.setForeground(new java.awt.Color(12, 18, 35));
        ItemName.setToolTipText("");
        ItemName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(247, 153, 95)));

        jLabel5.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(247, 153, 95));
        jLabel5.setText("PRICE");

        ItemPrice.setBackground(new java.awt.Color(207, 217, 214));
        ItemPrice.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        ItemPrice.setForeground(new java.awt.Color(12, 18, 35));
        ItemPrice.setToolTipText("");
        ItemPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(247, 153, 95)));

        jLabel6.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(247, 153, 95));
        jLabel6.setText("QUANTITY");

        jLabel8.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Filter By Category");

        FilterCategory.setBackground(new java.awt.Color(207, 217, 214));
        FilterCategory.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        FilterCategory.setForeground(new java.awt.Color(12, 18, 35));
        FilterCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Categories", "Fries", "Pizza Sleeves", "Wraps", "Chicken Wings", "Burger", "Meatballs", "Sandwiches", "Asian", "Salads", "Dessert" }));
        FilterCategory.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(247, 153, 95)));
        FilterCategory.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FilterCategoryItemStateChanged(evt);
            }
        });

        AddToBill.setBackground(new java.awt.Color(75, 173, 206));
        AddToBill.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        AddToBill.setForeground(new java.awt.Color(12, 18, 35));
        AddToBill.setText("ADD TO ORDER");
        AddToBill.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddToBillMouseClicked(evt);
            }
        });
        AddToBill.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddToBillActionPerformed(evt);
            }
        });

        LogoutBtn.setBackground(new java.awt.Color(255, 0, 51));
        LogoutBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        LogoutBtn.setForeground(new java.awt.Color(12, 18, 35));
        LogoutBtn.setText("LOGOUT");
        LogoutBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LogoutBtnActionPerformed(evt);
            }
        });

        ItemsList1.setBackground(new java.awt.Color(12, 18, 35));
        ItemsList1.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        ItemsList1.setForeground(new java.awt.Color(247, 153, 95));
        ItemsList1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Item ID", "Name", "Category", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        ItemsList1.setRowHeight(30);
        ItemsList1.setShowGrid(true);
        ItemsList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ItemsList1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(ItemsList1);

        jLabel4.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("ORDERS LIST");

        PrintBtn.setBackground(new java.awt.Color(75, 173, 206));
        PrintBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        PrintBtn.setForeground(new java.awt.Color(12, 18, 35));
        PrintBtn.setText("PRINT ORDER");
        PrintBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintBtnActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Fetch Order");

        RemoveItemBtn.setBackground(new java.awt.Color(51, 255, 51));
        RemoveItemBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        RemoveItemBtn.setForeground(new java.awt.Color(12, 18, 35));
        RemoveItemBtn.setText("REMOVE");
        RemoveItemBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RemoveItemBtnMouseClicked(evt);
            }
        });
        RemoveItemBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveItemBtnActionPerformed(evt);
            }
        });

        FetchOrderTxt.setBackground(new java.awt.Color(207, 217, 214));
        FetchOrderTxt.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        FetchOrderTxt.setForeground(new java.awt.Color(12, 18, 35));
        FetchOrderTxt.setText("Enter Order #");
        FetchOrderTxt.setToolTipText("");
        FetchOrderTxt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(247, 153, 95)));

        FetchBillBtn.setBackground(new java.awt.Color(51, 255, 51));
        FetchBillBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        FetchBillBtn.setForeground(new java.awt.Color(12, 18, 35));
        FetchBillBtn.setText("GO");
        FetchBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FetchBillBtnActionPerformed(evt);
            }
        });

        Logo.setIcon(new javax.swing.ImageIcon("C:\\Users\\ramgm\\Documents\\NetBeansProjects\\eaterypos\\src\\Logo\\120 x120.png")); // NOI18N

        ItemQty.setBackground(new java.awt.Color(207, 217, 214));
        ItemQty.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        ItemQty.setForeground(new java.awt.Color(12, 18, 35));
        ItemQty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
        ItemQty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(247, 153, 95)));
        ItemQty.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ItemQtyItemStateChanged(evt);
            }
        });

        ClearBillBtn.setBackground(new java.awt.Color(255, 0, 51));
        ClearBillBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        ClearBillBtn.setForeground(new java.awt.Color(12, 18, 35));
        ClearBillBtn.setText("CLEAR");
        ClearBillBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClearBillBtnMouseClicked(evt);
            }
        });
        ClearBillBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearBillBtnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("TOTAL");

        Total.setEditable(false);
        Total.setBackground(new java.awt.Color(204, 204, 204));
        Total.setColumns(1);
        Total.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        Total.setForeground(new java.awt.Color(255, 0, 51));
        Total.setRows(1);
        Total.setTabSize(4);
        Total.setText("Kshs 0.00");
        jScrollPane4.setViewportView(Total);

        jLabel10.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("CHANGE:");

        jLabel11.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 153, 0));
        jLabel11.setText("Kshs 0.00");

        jLabel12.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("PAY");

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Logo, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ItemName)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(AddToBill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(ItemPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(ItemQty, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(FilterCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(LogoutBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ItemsBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(118, 118, 118)
                                .addComponent(OrdersBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(FetchOrderTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(FetchBillBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(RemoveItemBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                                        .addComponent(ClearBillBtn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(27, 27, 27)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel12))))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PrintBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(154, 154, 154)
                            .addComponent(jLabel10))))
                .addGap(16, 16, 16))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ItemName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ItemPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ItemQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(AddToBill)
                                    .addComponent(jLabel1)))
                            .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(FilterCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(OrdersBtn)
                            .addComponent(ItemsBtn)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(RemoveItemBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addComponent(ClearBillBtn))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(FetchOrderTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addComponent(PrintBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LogoutBtn)
                    .addComponent(FetchBillBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ItemsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemsBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ItemsBtnActionPerformed

    private void OrdersBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrdersBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OrdersBtnActionPerformed
    
    private void SalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_SalesMouseClicked

    private void FilterCategoryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FilterCategoryItemStateChanged
        // Filter By Category
        FilterItems();
    }//GEN-LAST:event_FilterCategoryItemStateChanged
    int GrandT = 0;
    private void AddToBillActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddToBillActionPerformed
        // ADD TO SALE
        try {
            int itemPrice = Integer.parseInt(ItemPrice.getText());
            int itemQty = Integer.parseInt(ItemQty.getSelectedItem().toString());
            int total = itemPrice * itemQty;
            GrandT += total;
            Total.setText("Kshs "+GrandT);

            DefaultTableModel model = (DefaultTableModel) Sales.getModel();

            model.addRow(new Object[]{
                ItemName.getText(),
                itemPrice,
                itemQty,
                total
            });
        } catch (NumberFormatException e) {
            // Handle the exception, maybe show a dialog or message box to the user
            JOptionPane.showMessageDialog(null, "Please enter valid numbers for price and quantity.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_AddToBillActionPerformed

    private void LogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LogoutBtnActionPerformed
    
    private void ItemsList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ItemsList1MouseClicked
    // SELECT ORDER ITEM
    DefaultTableModel model = (DefaultTableModel) ItemsList1.getModel();
    int MyIndex = ItemsList1.getSelectedRow();
    ItemName.setText(model.getValueAt(MyIndex, 1).toString());
    ItemPrice.setText(model.getValueAt(MyIndex, 3).toString());
    }//GEN-LAST:event_ItemsList1MouseClicked

    private void PrintBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintBtnActionPerformed
        // TODO add your handling code here:
        InsertSale(evt);
    }//GEN-LAST:event_PrintBtnActionPerformed

    private void RemoveItemBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RemoveItemBtnMouseClicked
        // REFRESH ITEMS TABLE
        ShowItems();
    }//GEN-LAST:event_RemoveItemBtnMouseClicked
    int Key= 0;
    private void RemoveItemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveItemBtnActionPerformed
        // REMOVE AN ITEM from Sales List
        if (Key == 0) {
            JOptionPane.showMessageDialog(this, "Please select an item to delete");
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this item?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/grabdb", "root", "admin");
                    String deleteQuery = "DELETE FROM Sales WHERE SaleID = ?";

                    try (PreparedStatement Pst = Con.prepareStatement(deleteQuery)) {
                        Pst.setInt(1, Key);

                        int row = Pst.executeUpdate();

                        if (row > 0) {
                            JOptionPane.showMessageDialog(this, "Item Deleted Successfully");
                            ShowItems();
                            clearFields();
                        } else {
                            JOptionPane.showMessageDialog(this, "Failed to Delete Item");
                        }
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
                } finally {
                    if (Con != null) {
                        try {
                            Con.close();
                        } catch (SQLException ex) {
                            // Handle close exception if necessary
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_RemoveItemBtnActionPerformed

    private void FetchBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FetchBillBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FetchBillBtnActionPerformed

    private void ItemQtyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ItemQtyItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ItemQtyItemStateChanged

    private void AddToBillMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddToBillMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_AddToBillMouseClicked

    private void ClearBillBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearBillBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ClearBillBtnMouseClicked

    private void ClearBillBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearBillBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ClearBillBtnActionPerformed

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
            java.util.logging.Logger.getLogger(Sale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sale.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Sale().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddToBill;
    private javax.swing.JButton ClearBillBtn;
    private javax.swing.JButton FetchBillBtn;
    private javax.swing.JTextField FetchOrderTxt;
    private javax.swing.JComboBox<String> FilterCategory;
    private javax.swing.JTextField ItemName;
    private javax.swing.JTextField ItemPrice;
    private javax.swing.JComboBox<String> ItemQty;
    private javax.swing.JButton ItemsBtn;
    private javax.swing.JTable ItemsList1;
    private javax.swing.JLabel Logo;
    private javax.swing.JButton LogoutBtn;
    private javax.swing.JButton OrdersBtn;
    private javax.swing.JButton PrintBtn;
    private javax.swing.JButton RemoveItemBtn;
    private javax.swing.JTable Sales;
    private javax.swing.JTextArea Total;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
