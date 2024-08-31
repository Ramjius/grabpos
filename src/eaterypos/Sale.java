
package eaterypos;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author ramjius muhsin
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
        loadCategoriesFromFile();
    }
    ResultSet Rs = null;
    Connection Con = null;
    Statement St = null;
    static int lastOrderID = 0; // Keeps track of the last Order ID
    
    
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
    
    private void loadCategoriesFromFile() {
        File file = new File("./categories.dat");
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                List<String> categories = (List<String>) ois.readObject();
                for (String category : categories) {
                    FilterCategory.addItem(category);
                }
            } catch (IOException | ClassNotFoundException e) {
            }
        }
    }
    
    // Method to wrap text into multiple lines
    private List<String> wrapText(String text, int maxWidth, FontMetrics metrics) {
        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        for (String word : text.split(" ")) {
            if (metrics.stringWidth(line + word) < maxWidth) {
                line.append(word).append(" ");
            } else {
                lines.add(line.toString().trim());
                line = new StringBuilder(word).append(" ");
            }
        }
        if (!line.toString().isEmpty()) {
            lines.add(line.toString().trim());
        }
        return lines;
    }
    
    public class ReceiptPrinter implements Printable {
    private JTable table;
    private String logoPath;
    private int grandTotal;
    private int amountPaid;
    private int change;
    private String paymentMode;
    private int discount;
    private int orderID;
    

    public ReceiptPrinter(JTable table, String logoPath, int grandTotal, int amountPaid, int change, String paymentMode, int discount, int orderID) {
        this.table = table;
        this.logoPath = logoPath;
        this.grandTotal = grandTotal;
        this.amountPaid = amountPaid;
        this.change = change;
        this.paymentMode = paymentMode;
        this.discount = discount;
        this.orderID = orderID;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        int y = 20; // Starting y position

        // Load and print the logo centered
        try {
            Image logo = Toolkit.getDefaultToolkit().getImage(logoPath);
            int logoWidth = logo.getWidth(null);
            int logoX = (int) (pf.getImageableWidth() - logoWidth) / 2;
            g2d.drawImage(logo, logoX, y, null);
            y += logo.getHeight(null) + 10; // Add space after logo
        } catch (Exception e) {
        }

        // Print title
        g2d.setFont(new Font("Cambria", Font.BOLD, 12));
        g2d.drawString("Grab Garage", (int) (pf.getImageableWidth() / 2 - 40), y);
        y += 20;

        // Print subtitle
        g2d.setFont(new Font("Cambria", Font.ITALIC, 10));
        g2d.drawString("We Fix Your Cravings", (int) (pf.getImageableWidth() / 2 - 50), y);
        y += 20;

        // Print Order Number
        g2d.setFont(new Font("Cambria", Font.PLAIN, 10));
        g2d.drawString("Order # " + orderID, (int) (pf.getImageableWidth() - 80), y);
        y += 20;

        // Print broken line
        g2d.drawString("---------------------------------", 0, y);
        y += 20;

        // Print table contents
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        g2d.setFont(new Font("Cambria", Font.PLAIN, 8));
        FontMetrics metrics = g2d.getFontMetrics();

        int maxItemNameWidth = (int) (pf.getImageableWidth() - 60); // Adjust this value as needed
        int lineHeight = metrics.getHeight();

        for (int i = 0; i < model.getRowCount(); i++) {
            String itemName = model.getValueAt(i, 0).toString();
            String itemPrice = model.getValueAt(i, 3).toString();

            // Wrap the item name if necessary
            List<String> wrappedItemName = wrapText(itemName, maxItemNameWidth, metrics);

            // Print each line of the wrapped item name
            for (String line : wrappedItemName) {
                g2d.drawString(line, 0, y);
                y += lineHeight;
            }

            // Print the price on the same line as the last line of the item name
            g2d.drawString("Kshs " + itemPrice, (int) (pf.getImageableWidth() - 60), y - lineHeight);
            y += 10; // Add additional spacing between items
        }

        // Print dotted line
        g2d.drawString(".............................................................", 0, y);
        y += 20;

        // Print Subtotal
        g2d.setFont(new Font("Cambria", Font.PLAIN, 10));
        g2d.drawString("Subtotal", 0, y);
        g2d.drawString("Kshs " + grandTotal, (int) (pf.getImageableWidth() - 70), y);
        y += 20;
        
        // Print Discount
        //g2d.drawString("Discount", 0, y);
        //g2d.drawString("Kshs " + discount, (int) (pf.getImageableWidth() - 80), y);
        //y += 20;
        
        // Print Total
        g2d.setFont(new Font("Cambria", Font.BOLD, 10));
        g2d.drawString("Total", 0, y);
        g2d.drawString("Kshs " + (grandTotal - discount), (int) (pf.getImageableWidth() - 70), y);
        y += 20;

        // Print continuous line
        g2d.drawString("____________________________", 0, y);
        y += 20;

        // Print Total Paid
        g2d.setFont(new Font("Cambria", Font.PLAIN, 10));
        g2d.drawString("Total Paid", 0, y);
        g2d.drawString("Kshs " + amountPaid, (int) (pf.getImageableWidth() - 60), y);
        y += 20;

        // Print Change
        g2d.drawString("Change", 0, y);
        g2d.drawString("Kshs " + change, (int) (pf.getImageableWidth() - 60), y);
        y += 20;

        // Print Thank You Message
        g2d.setFont(new Font("Cambria", Font.BOLD | Font.ITALIC, 8));
        g2d.drawString("Thank You For Your Business", (int) (pf.getImageableWidth() / 2 - 60), y);
        y += 20;
        
        // Print Date and Time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
        String dateTime = LocalDateTime.now().format(formatter);
        g2d.setFont(new Font("Cambria", Font.PLAIN, 10));
        g2d.drawString(dateTime, (int) (pf.getImageableWidth() / 2 - 50), y);

        return PAGE_EXISTS;
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
        OrdersList = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        ItemName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        ItemPrice = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        FilterCategory = new javax.swing.JComboBox<>();
        AddToOrder = new javax.swing.JButton();
        LogoutBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ItemsList1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        RemoveItemBtn = new javax.swing.JButton();
        Logo = new javax.swing.JLabel();
        ItemQty = new javax.swing.JComboBox<>();
        ClearOrderBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        ChangeValue = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Total = new javax.swing.JTextArea();
        AmountPaid = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        PayBtn = new javax.swing.JButton();
        PaymentMode = new javax.swing.JComboBox<>();
        DiscountField = new javax.swing.JTextField();
        Discount = new javax.swing.JLabel();
        PaymentLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        SourceLabel = new javax.swing.JLabel();
        SourceComboBox = new javax.swing.JComboBox<>();
        OrderItemsBtn = new javax.swing.JButton();
        ExpensesBtn = new javax.swing.JButton();
        ReportsBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Cambria", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(5, 76, 74));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SALES");

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

        OrdersList.setBackground(new java.awt.Color(5, 76, 74));
        OrdersList.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        OrdersList.setForeground(new java.awt.Color(247, 153, 95));
        OrdersList.setModel(new javax.swing.table.DefaultTableModel(
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
        OrdersList.setRowHeight(30);
        OrdersList.setShowGrid(true);
        OrdersList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                OrdersListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(OrdersList);

        jLabel3.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(5, 76, 74));
        jLabel3.setText("ITEM NAME");

        ItemName.setBackground(new java.awt.Color(207, 217, 214));
        ItemName.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        ItemName.setForeground(new java.awt.Color(12, 18, 35));
        ItemName.setToolTipText("");
        ItemName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(249, 188, 44)));

        jLabel5.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(5, 76, 74));
        jLabel5.setText("PRICE");

        ItemPrice.setBackground(new java.awt.Color(207, 217, 214));
        ItemPrice.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        ItemPrice.setForeground(new java.awt.Color(12, 18, 35));
        ItemPrice.setToolTipText("");
        ItemPrice.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(249, 188, 44)));

        jLabel6.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(5, 76, 74));
        jLabel6.setText("QUANTITY");

        jLabel8.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Filter By Category");

        FilterCategory.setBackground(new java.awt.Color(207, 217, 214));
        FilterCategory.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        FilterCategory.setForeground(new java.awt.Color(12, 18, 35));
        FilterCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Categories", "Fries", "Pizza Sleeves", "Wraps", "Chicken Wings", "Burger", "Meatballs", "Sandwiches", "Asian", "Salads", "Dessert", "Extras", "Sausages", "Delivery", "Milk Shakes", "Juice (M)", "Juice (L)", "Cheezy", "Special" }));
        FilterCategory.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(249, 188, 44)));
        FilterCategory.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                FilterCategoryItemStateChanged(evt);
            }
        });

        AddToOrder.setBackground(new java.awt.Color(249, 188, 44));
        AddToOrder.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        AddToOrder.setForeground(new java.awt.Color(12, 18, 35));
        AddToOrder.setText("ADD TO ORDER");
        AddToOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddToOrderMouseClicked(evt);
            }
        });
        AddToOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddToOrderActionPerformed(evt);
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

        ItemsList1.setBackground(new java.awt.Color(5, 76, 74));
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

        RemoveItemBtn.setBackground(new java.awt.Color(249, 188, 44));
        RemoveItemBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        RemoveItemBtn.setForeground(new java.awt.Color(12, 18, 35));
        RemoveItemBtn.setText("REMOVE ITEM");
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

        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/120 x120.png"))); // NOI18N

        ItemQty.setBackground(new java.awt.Color(207, 217, 214));
        ItemQty.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        ItemQty.setForeground(new java.awt.Color(12, 18, 35));
        ItemQty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30" }));
        ItemQty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(249, 188, 44)));
        ItemQty.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ItemQtyItemStateChanged(evt);
            }
        });

        ClearOrderBtn.setBackground(new java.awt.Color(255, 0, 51));
        ClearOrderBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        ClearOrderBtn.setForeground(new java.awt.Color(12, 18, 35));
        ClearOrderBtn.setText("CLEAR ITEMS");
        ClearOrderBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClearOrderBtnMouseClicked(evt);
            }
        });
        ClearOrderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearOrderBtnActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(5, 76, 74));
        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        ChangeValue.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        ChangeValue.setForeground(new java.awt.Color(249, 188, 44));
        ChangeValue.setText("Kshs 0.00");

        jLabel10.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("CHANGE");

        jLabel12.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("PAID");

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

        AmountPaid.setBackground(new java.awt.Color(255, 255, 255));
        AmountPaid.setFont(new java.awt.Font("Cambria", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12)
                    .addComponent(jScrollPane4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel10)
                    .addComponent(ChangeValue)
                    .addComponent(AmountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AmountPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ChangeValue)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addContainerGap())
        );

        AmountPaid.getAccessibleContext().setAccessibleName("");
        AmountPaid.getAccessibleContext().setAccessibleDescription("");

        jPanel3.setBackground(new java.awt.Color(5, 76, 74));

        PayBtn.setBackground(new java.awt.Color(249, 188, 44));
        PayBtn.setFont(new java.awt.Font("Cambria", 1, 18)); // NOI18N
        PayBtn.setForeground(new java.awt.Color(12, 18, 35));
        PayBtn.setText("PAY");
        PayBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PayBtnActionPerformed(evt);
            }
        });

        PaymentMode.setBackground(new java.awt.Color(5, 76, 74));
        PaymentMode.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        PaymentMode.setForeground(new java.awt.Color(249, 188, 44));
        PaymentMode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M-PESA", "CASH", "CARD" }));

        DiscountField.setBackground(new java.awt.Color(255, 255, 255));
        DiscountField.setFont(new java.awt.Font("Cambria", 0, 12)); // NOI18N
        DiscountField.setText("0");
        DiscountField.setToolTipText("");

        Discount.setForeground(new java.awt.Color(255, 255, 255));
        Discount.setText("Discount");

        PaymentLabel.setForeground(new java.awt.Color(255, 255, 255));
        PaymentLabel.setText("Payment Mode");

        jPanel4.setBackground(new java.awt.Color(5, 76, 74));
        jPanel4.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N

        SourceLabel.setForeground(new java.awt.Color(255, 255, 255));
        SourceLabel.setText("Source");

        SourceComboBox.setBackground(new java.awt.Color(5, 76, 74));
        SourceComboBox.setFont(new java.awt.Font("Cambria", 0, 14)); // NOI18N
        SourceComboBox.setForeground(new java.awt.Color(249, 188, 44));
        SourceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "In-House", "Bolt", "Glovo" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(SourceLabel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(SourceComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SourceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SourceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Discount)
                            .addComponent(PaymentLabel)
                            .addComponent(DiscountField)
                            .addComponent(PaymentMode, 0, 147, Short.MAX_VALUE)
                            .addComponent(PayBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PaymentLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(PaymentMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(Discount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DiscountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(PayBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        OrderItemsBtn.setBackground(new java.awt.Color(249, 188, 44));
        OrderItemsBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        OrderItemsBtn.setForeground(new java.awt.Color(12, 18, 35));
        OrderItemsBtn.setText("ORDER ITEMS");
        OrderItemsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrderItemsBtnActionPerformed(evt);
            }
        });

        ExpensesBtn.setBackground(new java.awt.Color(249, 188, 44));
        ExpensesBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        ExpensesBtn.setForeground(new java.awt.Color(12, 18, 35));
        ExpensesBtn.setText("EXPENSES");
        ExpensesBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExpensesBtnMouseClicked(evt);
            }
        });
        ExpensesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExpensesBtnActionPerformed(evt);
            }
        });

        ReportsBtn.setBackground(new java.awt.Color(249, 188, 44));
        ReportsBtn.setFont(new java.awt.Font("Cambria", 1, 12)); // NOI18N
        ReportsBtn.setForeground(new java.awt.Color(12, 18, 35));
        ReportsBtn.setText("REPORTS");
        ReportsBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReportsBtnMouseClicked(evt);
            }
        });
        ReportsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportsBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(FilterCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(LogoutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                    .addComponent(ItemsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(OrdersBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(OrderItemsBtn))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(ReportsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(ExpensesBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(Logo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ItemName)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(AddToOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(ItemPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(ItemQty, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RemoveItemBtn)
                        .addGap(18, 18, 18)
                        .addComponent(ClearOrderBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
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
                                    .addComponent(AddToOrder)
                                    .addComponent(jLabel1)))
                            .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(FilterCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ItemsBtn)
                            .addComponent(OrdersBtn)
                            .addComponent(ReportsBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LogoutBtn)
                            .addComponent(OrderItemsBtn)
                            .addComponent(ExpensesBtn)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(RemoveItemBtn)
                            .addComponent(ClearOrderBtn))
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(24, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
    int Key = 0;
    private void OrdersListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_OrdersListMouseClicked
        try {
        // Get the table model from the OrdersList table
        DefaultTableModel model = (DefaultTableModel) OrdersList.getModel();
        
        // Get the selected row index
        int selectedIndex = OrdersList.getSelectedRow();
        
        // Retrieve data from the selected row and set them to the respective fields
        Key = Integer.parseInt(model.getValueAt(selectedIndex, 0).toString());
        ItemName.setText(model.getValueAt(selectedIndex, 1).toString());
        ItemPrice.setText(model.getValueAt(selectedIndex, 2).toString());
        ItemQty.setSelectedItem(model.getValueAt(selectedIndex, 3).toString());
        
    } catch (NumberFormatException e) {
        // Handle the case where the key is not a valid integer
        System.err.println("Invalid key format: " + e.getMessage());
    } catch (ArrayIndexOutOfBoundsException e) {
        // Handle the case where the selected index is out of bounds
        System.err.println("Selected row index is out of bounds: " + e.getMessage());
    } catch (Exception e) {
        // Handle any other unexpected exceptions
        System.err.println("An error occurred: " + e.getMessage());
    }
    }//GEN-LAST:event_OrdersListMouseClicked

    private void FilterCategoryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_FilterCategoryItemStateChanged
        // Filter By Category
        FilterItems();
    }//GEN-LAST:event_FilterCategoryItemStateChanged
    int GrandT = 0;
    private void AddToOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddToOrderActionPerformed
        // ADD TO ORDERS LIST
        try {
            int itemPrice = Integer.parseInt(ItemPrice.getText());
            int itemQty = Integer.parseInt(ItemQty.getSelectedItem().toString());
            int total = itemPrice * itemQty;
            GrandT += total;
            Total.setText("Kshs " + GrandT);

            DefaultTableModel model = (DefaultTableModel) OrdersList.getModel();

            model.addRow(new Object[]{
                ItemName.getText(),
                itemPrice,
                itemQty,
                total
            });
        } catch (NumberFormatException e) {
            // Handle the exception, maybe show a dialog or message box to the user
            JOptionPane.showMessageDialog(null, "Please Select Item and Quantity.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
        clearFields();
    }//GEN-LAST:event_AddToOrderActionPerformed

    private void LogoutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LogoutBtnActionPerformed
        // TODO add your handling code here:
        new Login().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_LogoutBtnActionPerformed
    
    private void ItemsList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ItemsList1MouseClicked
    // SELECT ORDER ITEM
    DefaultTableModel model = (DefaultTableModel) ItemsList1.getModel();
    int MyIndex = ItemsList1.getSelectedRow();
    ItemName.setText(model.getValueAt(MyIndex, 1).toString());
    ItemPrice.setText(model.getValueAt(MyIndex, 3).toString());
    }//GEN-LAST:event_ItemsList1MouseClicked

    private void RemoveItemBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RemoveItemBtnMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_RemoveItemBtnMouseClicked
    
    private void RemoveItemBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveItemBtnActionPerformed
        // Get the selected row index
        int selectedRow = OrdersList.getSelectedRow();

        // Check if a row is selected
        if (selectedRow == -1) {
            // No row is selected, show a warning message
            javax.swing.JOptionPane.showMessageDialog(null, "Please select a row to remove.", "No row selected", javax.swing.JOptionPane.WARNING_MESSAGE);
        } else {
            // Get the table model
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) OrdersList.getModel();

            // Get the total value of the item being removed
            int itemTotal = Integer.parseInt(model.getValueAt(selectedRow, 3).toString()); // Assuming the total column is at index 3

            // Remove the selected row from the model
            model.removeRow(selectedRow);

            // Update the total value
            GrandT -= itemTotal;

            // Update the displayed total value (assuming you have a JTextField named GrandTotalField)
            Total.setText("Kshs " + GrandT);
        }
    }//GEN-LAST:event_RemoveItemBtnActionPerformed

    private void ItemQtyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ItemQtyItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_ItemQtyItemStateChanged

    private void AddToOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddToOrderMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_AddToOrderMouseClicked

    private void ClearOrderBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClearOrderBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ClearOrderBtnMouseClicked

    private void ClearOrderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearOrderBtnActionPerformed
        // Remove All Items from Orders List
        try {
            DefaultTableModel model = (DefaultTableModel) OrdersList.getModel();
            int rowCount = model.getRowCount();

            for (int i = rowCount - 1; i >= 0; i--) {
                model.removeRow(i);
            }

            GrandT = 0;
            Total.setText("Kshs " + GrandT);
            AmountPaid.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred while trying to clear the list.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ClearOrderBtnActionPerformed

    private void PayBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PayBtnActionPerformed
        Connection conn = null;
        PreparedStatement psOrderItems = null;
        PreparedStatement psOrders = null;
        PreparedStatement psOrderCount = null;
        PreparedStatement psFetchItemID = null;
        ResultSet rsItemID = null;
        ResultSet rsOrderCount = null;

        try {
            DefaultTableModel model = (DefaultTableModel) OrdersList.getModel();
            int rowCount = model.getRowCount();

            if (rowCount == 0) {
                JOptionPane.showMessageDialog(null, "There are no items in the order list.", "Empty Order List", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Establish database connection
            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/grabdb", "root", "admin");

            // Count rows in the orders table to generate new Order ID
            String countOrdersSQL = "SELECT COUNT(*) FROM orders";
            psOrderCount = conn.prepareStatement(countOrdersSQL);
            rsOrderCount = psOrderCount.executeQuery();

            // Get the current date and time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedDate = now.format(dateFormatter);
            String formattedTime = now.format(timeFormatter);

            int orderID = 1; // Default to 1 if there are no rows in the orders table
            if (rsOrderCount.next()) {
                orderID = rsOrderCount.getInt(1) + 1;
            }

            // Get the paid amount and calculate change
            int amountPaid = 0;
            int change = 0;
            try {
                amountPaid = Integer.parseInt(AmountPaid.getText());
                change = amountPaid - GrandT;
                ChangeValue.setText("Kshs " + change);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount paid.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get the payment mode
            String paymentMode = PaymentMode.getSelectedItem().toString();
            String saleBy = SourceComboBox.getSelectedItem().toString();

            // Prepare SQL query to fetch ItemID from items table
            String sqlFetchItemID = "SELECT ItemID FROM items WHERE Name = ?";
            psFetchItemID = conn.prepareStatement(sqlFetchItemID);

            // Prepare SQL query to insert order items
            String sqlOrderItems = "INSERT INTO order_items (order_id, ItemID, item_name, item_price, item_quantity, total, date, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            psOrderItems = conn.prepareStatement(sqlOrderItems);

            for (int i = 0; i < rowCount; i++) {
                String itemName = model.getValueAt(i, 0).toString();
                int itemPrice = Integer.parseInt(model.getValueAt(i, 1).toString());
                int itemQty = Integer.parseInt(model.getValueAt(i, 2).toString());
                int total = Integer.parseInt(model.getValueAt(i, 3).toString());

                // Fetch the ItemID from items table
                psFetchItemID.setString(1, itemName);
                rsItemID = psFetchItemID.executeQuery();

                if (rsItemID.next()) {
                    int itemID = rsItemID.getInt("ItemID");

                    // Insert into order_items table
                    psOrderItems.setInt(1, orderID);
                    psOrderItems.setInt(2, itemID);  // Use the fetched ItemID
                    psOrderItems.setString(3, itemName);
                    psOrderItems.setInt(4, itemPrice);
                    psOrderItems.setInt(5, itemQty);
                    psOrderItems.setInt(6, total);
                    psOrderItems.setString(7, formattedDate);
                    psOrderItems.setString(8, formattedTime);

                    psOrderItems.addBatch(); // Add to batch for execution
                } else {
                    throw new SQLException("ItemID not found for item_name: " + itemName);
                }
            }

            // Execute batch insertion for order items
            psOrderItems.executeBatch();

            // Prepare SQL query to insert order
            String sqlOrders = "INSERT INTO orders (order_id, date, grand_total, amount_paid, payment_mode, change_value, sale_by, time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            psOrders = conn.prepareStatement(sqlOrders);
            psOrders.setInt(1, orderID);
            psOrders.setString(2, formattedDate);
            psOrders.setInt(3, GrandT);
            psOrders.setInt(4, amountPaid);
            psOrders.setString(5, paymentMode);
            psOrders.setInt(6, change);
            psOrders.setString(7, saleBy);
            psOrders.setString(8, formattedTime);

            // Execute insertion for orders
            psOrders.executeUpdate();

            // Optional: Show a confirmation message (replace with logging if necessary)
            JOptionPane.showMessageDialog(null, "Order placed successfully!\nOrder ID: " + orderID + "\nAmount Paid: Kshs " + amountPaid + "\nChange: Kshs " + change, "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);

            // Print the receipt
            String logoPath = "C:\\Users\\hp\\Documents\\grabpos 1.0\\80x80.png"; // Adjust the path to your logo image
            PrinterJob job = PrinterJob.getPrinterJob();
            PageFormat pf = job.defaultPage();
            Paper paper = new Paper();
            double width = 80 * 2.83464567; // 80mm width in points
            double height = paper.getHeight();
            paper.setSize(width, height);
            double leftMargin = 50; // Left margin in points
            double rightMargin = 50; // Right margin in points
            paper.setImageableArea(leftMargin, 0, width - leftMargin - rightMargin, height);
            pf.setPaper(paper);

            // Assuming placeholder values for missing parameters
            int discount = 0;
            int tax = 0; // Example placeholder

            job.setPrintable(new ReceiptPrinter(OrdersList, logoPath, GrandT, amountPaid, change, paymentMode, discount, orderID), pf);

            if (job.printDialog()) {
                try {
                    job.print();
                } catch (PrinterException e) {
                    JOptionPane.showMessageDialog(null, "Printing error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Clear the orders list and reset the total
            ClearOrderBtnActionPerformed(evt);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while processing the payment.", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close resources
            if (rsItemID != null) {
                try {
                    rsItemID.close();
                } catch (SQLException ex) {
                }
            }
            if (rsOrderCount != null) {
                try {
                    rsOrderCount.close();
                } catch (SQLException ex) {
                }
            }
            if (psOrderCount != null) {
                try {
                    psOrderCount.close();
                } catch (SQLException ex) {
                }
            }
            if (psOrderItems != null) {
                try {
                    psOrderItems.close();
                } catch (SQLException ex) {
                }
            }
            if (psOrders != null) {
                try {
                    psOrders.close();
                } catch (SQLException ex) {
                }
            }
            if (psFetchItemID != null) {
                try {
                    psFetchItemID.close();
                } catch (SQLException ex) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                }
            }
        }
    }//GEN-LAST:event_PayBtnActionPerformed

    private void OrderItemsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrderItemsBtnActionPerformed
        // TODO add your handling code here:
        new OrderItems().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_OrderItemsBtnActionPerformed

    private void ExpensesBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExpensesBtnMouseClicked
        new Expenses().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ExpensesBtnMouseClicked

    private void ExpensesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExpensesBtnActionPerformed
        new Expenses().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ExpensesBtnActionPerformed

    private void ReportsBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReportsBtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ReportsBtnMouseClicked

    private void ReportsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportsBtnActionPerformed
        new SelectReport().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ReportsBtnActionPerformed

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
    private javax.swing.JButton AddToOrder;
    private javax.swing.JTextField AmountPaid;
    private javax.swing.JLabel ChangeValue;
    private javax.swing.JButton ClearOrderBtn;
    private javax.swing.JLabel Discount;
    private javax.swing.JTextField DiscountField;
    private javax.swing.JButton ExpensesBtn;
    private javax.swing.JComboBox<String> FilterCategory;
    private javax.swing.JTextField ItemName;
    private javax.swing.JTextField ItemPrice;
    private javax.swing.JComboBox<String> ItemQty;
    private javax.swing.JButton ItemsBtn;
    private javax.swing.JTable ItemsList1;
    private javax.swing.JLabel Logo;
    private javax.swing.JButton LogoutBtn;
    private javax.swing.JButton OrderItemsBtn;
    private javax.swing.JButton OrdersBtn;
    private javax.swing.JTable OrdersList;
    private javax.swing.JButton PayBtn;
    private javax.swing.JLabel PaymentLabel;
    private javax.swing.JComboBox<String> PaymentMode;
    private javax.swing.JButton RemoveItemBtn;
    private javax.swing.JButton ReportsBtn;
    private javax.swing.JComboBox<String> SourceComboBox;
    private javax.swing.JLabel SourceLabel;
    private javax.swing.JTextArea Total;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables

    
}
