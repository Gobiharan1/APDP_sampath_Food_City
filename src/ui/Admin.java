/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import db.database;
import java.awt.Color;
import javaswingdev.drawer.Drawer;
import javaswingdev.drawer.DrawerController;
import javaswingdev.drawer.DrawerItem;
import javaswingdev.drawer.EventDrawer;
import javax.swing.ImageIcon;
import ui.Logo_for_Admin_dashboard;
import com.toedter.calendar.JDateChooser;
import java.sql.PreparedStatement;
import javax.swing.JPanel;
import code.Product;
import code.TotalCalculation;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Admin extends javax.swing.JFrame {

    private DrawerController drawer;

    public Admin() {
        initComponents();
        datadisplay();

        drawer = Drawer.newDrawer(this)
                .header(new Logo_for_Admin_dashboard())
                .space(5)
                .background(new Color(255, 255, 20))
                .backgroundTransparent(0.3f)
                .drawerBackground(Color.white)
                .enableScroll(true)
                .enableScrollUI(false)
                .addChild(new DrawerItem("Add items ").icon(new ImageIcon(getClass().getResource("/IMG/add.png"))).build())
                .addChild(new DrawerItem("Product performance").icon(new ImageIcon(getClass().getResource("/IMG/admin.png"))).build())
                .addChild(new DrawerItem("customer behavaiour").icon(new ImageIcon(getClass().getResource("/IMG/panel.png"))).build())
                .addChild(new DrawerItem("Best selling product").icon(new ImageIcon(getClass().getResource("/IMG/best saling product.png"))).build())
                .addChild(new DrawerItem("Sales").icon(new ImageIcon(getClass().getResource("/IMG/sales.png"))).build())
                .addChild(new DrawerItem("Branch").icon(new ImageIcon(getClass().getResource("/IMG/branch.png"))).build())
                .addChild(new DrawerItem("New user / user details").icon(new ImageIcon(getClass().getResource("/IMG/details2.png"))).build())
                .addFooter(new DrawerItem("Log out").icon(new ImageIcon(getClass().getResource("/IMG/logout.png"))).build())
                .event((int index, DrawerItem item) -> {

                    switch (index) {
                        case 0:
                            jTabbedPane1.setSelectedIndex(0);

                            drawer.hide();
                            break;
                        case 1:
                            jTabbedPane1.setSelectedIndex(1);

                            drawer.hide();
                            break;
                        case 2:
                            jTabbedPane1.setSelectedIndex(2);

                            drawer.hide();
                            break;
                        case 3:
                            jTabbedPane1.setSelectedIndex(3);

                            drawer.hide();
                            break;
                        case 4:
                            jTabbedPane1.setSelectedIndex(4);

                            drawer.hide();
                            break;
                        case 5:
                            jTabbedPane1.setSelectedIndex(5);

                            drawer.hide();
                            break;
                        case 6:
                            jTabbedPane1.setSelectedIndex(6);

                            drawer.hide();
                            break;
                        case 7:
                            int response = JOptionPane.showConfirmDialog(null,
                                    "Are you sure you want to log out?",
                                    "Log Out Confirmation",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE);

                            if (response == JOptionPane.YES_OPTION) {

                                Login loginPage = new Login();

                                loginPage.setVisible(true);
                                this.dispose(); //closing dashboard 

                            } else if (response == JOptionPane.NO_OPTION) {

                            } else {

                            }

                            drawer.hide();
                            break;
                        default:
                            break;

                    }
                })
                .build();

    }

    public void datadisplay() {
        // List to store Product objects
        List<Product> productList = new ArrayList<>();

        // Database query
        String query = "SELECT customer_id, product_id, product_name, qty, price_perunit, date, total_price, region FROM products";

        try (Connection con = database.getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            // Populate the Product list
            while (rs.next()) {
                Product p = new Product();
                p.setCutomerid(rs.getInt("customer_id"));
                p.setProductid(rs.getInt("product_id"));
                p.setProductname(rs.getString("product_name"));
                p.setQty(rs.getInt("qty"));
                p.setPriceperunit(rs.getDouble("price_perunit"));
                p.setTotal(rs.getDouble("total_price"));
                p.setRegion(rs.getString("region"));

                // Get the date from the database and set it
                java.sql.Date sqlDate = rs.getDate("date");
                // Convert from java.sql.Date to java.util.Date for setDate
                java.util.Date utilDate = null;
                if (sqlDate != null) {
                    utilDate = new java.util.Date(sqlDate.getTime());
                }

                p.setDate(utilDate); // Set the date in the Product object

                productList.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print full stack trace for debugging
            return; // Exit early if there's an SQL error
        }
        // Convert the Product list to a 2D array
        Object[][] dataArray = new Object[productList.size()][8];
        for (int i = 0; i < productList.size(); i++) {
            Product p = productList.get(i);
            dataArray[i][0] = p.getCutomerid();
            dataArray[i][1] = p.getProductid();
            dataArray[i][2] = p.getProductname();
            dataArray[i][3] = p.getQty();
            dataArray[i][4] = p.getPriceperunit();

            // Convert Date to String (if necessary)
            if (p.getDate() != null) {
                dataArray[i][5] = p.getDate().toString();  // Handle null dates
            } else {
                dataArray[i][5] = "";
            }

            // Convert Date to String (if necessary)
            if (p.getDate() != null) {
                // Use SimpleDateFormat to format the date
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dataArray[i][5] = dateFormat.format(p.getDate());
            } else {
                dataArray[i][5] = "";
            }

            dataArray[i][6] = p.getTotal();
            dataArray[i][7] = p.getRegion();
        }

        // Column names for JTable
        String[] columns = {"Customer ID", "Product ID", "Product Name", "Quantity", "Price per Unit", "Date", "Total Price", "Region"};

        // Update the jTable1 with the data
        DefaultTableModel tableModel = new DefaultTableModel(dataArray, columns);
        iteams_tbl.setModel(tableModel);
    }

    // Replace this with your class definition for product
    class Product {

        private int cutomerid;
        private int productid;
        private String productname;
        private int qty;
        private double priceperunit;
        private java.util.Date date; // Use java.util.Date here
        private double total;
        private String region;

        public int getCutomerid() {
            return cutomerid;
        }

        public void setCutomerid(int cutomerid) {
            this.cutomerid = cutomerid;
        }

        public int getProductid() {
            return productid;
        }

        public void setProductid(int productid) {
            this.productid = productid;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }

        public double getPriceperunit() {
            return priceperunit;
        }

        public void setPriceperunit(double priceperunit) {
            this.priceperunit = priceperunit;
        }

        public java.util.Date getDate() {
            return date;
        }

        public void setDate(java.util.Date date) {
            this.date = date;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        iteams_tbl = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        Region = new javax.swing.JTextField();
        Product_id = new javax.swing.JTextField();
        customer_id = new javax.swing.JTextField();
        Total_Price = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        unit_price = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        Product_name = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        qty_spin = new javax.swing.JSpinner();
        date_id = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        best_sales = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        performance = new javax.swing.JTable();
        jLabel30 = new javax.swing.JLabel();
        bestt_saless = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        customer_tbl = new javax.swing.JTable();
        Customer_id = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        product_tbl = new javax.swing.JTable();
        jLabel32 = new javax.swing.JLabel();
        check_btn = new javax.swing.JButton();
        date_choose = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txt_total = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTable11 = new javax.swing.JTable();
        jLabel40 = new javax.swing.JLabel();
        check_mnth = new javax.swing.JButton();
        choose_month = new com.toedter.calendar.JDateChooser();
        End_month = new com.toedter.calendar.JDateChooser();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        Annual_saless = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        SalesoftheYear_txt = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        check3_btn = new javax.swing.JButton();
        date_txt = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        Total_table = new javax.swing.JTable();
        TotalSales_txt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        check2_btn = new javax.swing.JButton();
        Date_choose1 = new com.toedter.calendar.JDateChooser();
        jPanel12 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        Branch_Performance = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        Total_region = new javax.swing.JTextField();
        jButton13 = new javax.swing.JButton();
        Enter_branch = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        user_txt = new javax.swing.JTextField();
        Email_txt = new javax.swing.JTextField();
        password_txt = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        user_role = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 1040, 50));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1200, 400));

        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 400));
        jPanel1.setLayout(null);

        jButton3.setText("Add");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(510, 230, 80, 40);

        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(600, 230, 80, 40);

        jButton5.setText("edit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5);
        jButton5.setBounds(690, 230, 80, 40);

        iteams_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product ID", "Customer ID", "Date", "Branch", "Unit Price", "Quantity", "Total Price"
            }
        ));
        iteams_tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iteams_tblMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(iteams_tbl);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(210, 290, 840, 220);

        jLabel1.setText("Region ");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(720, 50, 50, 20);

        jLabel8.setText("Total Price");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(710, 180, 90, 20);

        jLabel9.setText("Unit Price");
        jPanel1.add(jLabel9);
        jLabel9.setBounds(720, 90, 70, 20);

        jLabel10.setText("Product ID");
        jPanel1.add(jLabel10);
        jLabel10.setBounds(270, 60, 60, 20);

        jLabel11.setText("Quantity");
        jPanel1.add(jLabel11);
        jLabel11.setBounds(720, 130, 80, 20);

        jLabel12.setText("Customer ID");
        jPanel1.add(jLabel12);
        jLabel12.setBounds(270, 140, 90, 20);
        jPanel1.add(Region);
        Region.setBounds(800, 50, 180, 22);

        Product_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Product_idActionPerformed(evt);
            }
        });
        jPanel1.add(Product_id);
        Product_id.setBounds(360, 60, 180, 22);

        customer_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customer_idActionPerformed(evt);
            }
        });
        jPanel1.add(customer_id);
        customer_id.setBounds(360, 140, 180, 22);
        jPanel1.add(Total_Price);
        Total_Price.setBounds(800, 180, 180, 22);

        jLabel13.setText("Date");
        jPanel1.add(jLabel13);
        jLabel13.setBounds(270, 180, 90, 20);
        jPanel1.add(unit_price);
        unit_price.setBounds(800, 90, 180, 22);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("Iteams");
        jLabel2.setToolTipText("");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(560, 0, 130, 40);
        jPanel1.add(Product_name);
        Product_name.setBounds(360, 100, 180, 22);

        jLabel14.setText("product name");
        jPanel1.add(jLabel14);
        jLabel14.setBounds(270, 100, 80, 20);
        jPanel1.add(qty_spin);
        qty_spin.setBounds(800, 140, 180, 22);
        jPanel1.add(date_id);
        date_id.setBounds(360, 180, 180, 22);

        jTabbedPane1.addTab("tab1", jPanel1);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel3.setText("Product performance");

        jLabel4.setText("Product_id");

        performance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Top performing product", "Sales-Trend", "Catagory-base-anaylsis"
            }
        ));
        jScrollPane3.setViewportView(performance);

        jLabel30.setText("BestSales_Region");

        jButton12.setText("Check");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        clear.setText("Clear");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(484, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3))
                        .addGap(235, 235, 235))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel30))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jButton12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(clear))
                            .addComponent(bestt_saless, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)
                            .addComponent(best_sales))
                        .addGap(310, 310, 310))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(best_sales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(45, 45, 45)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bestt_saless, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(clear))
                .addGap(49, 49, 49)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("tab2", jPanel2);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel6.setText("Customer Behavior");

        customer_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "product_name", "Region", "Total_sales"
            }
        ));
        jScrollPane4.setViewportView(customer_tbl);

        jButton15.setText("Check");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton17.setText("clear field");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });

        jLabel35.setText("Customer ID");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(481, 481, 481)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel35)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(Customer_id, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(113, 113, 113))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(27, 27, 27)
                                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(53, 53, 53)))))
                .addContainerGap(290, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(175, 175, 175))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(98, 98, 98)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Customer_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17)
                    .addComponent(jButton15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        jTabbedPane1.addTab("tab3", jPanel3);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel7.setText("Best-Selling Products");

        product_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product name", "Quantity"
            }
        ));
        jScrollPane5.setViewportView(product_tbl);

        jLabel32.setText("Set Date");

        check_btn.setText("check");
        check_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(424, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(date_choose, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(check_btn)
                        .addGap(350, 350, 350))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(280, 280, 280))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(check_btn)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(date_choose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel32)))
                .addGap(40, 40, 40)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab4", jPanel4);

        jLabel34.setText("Choose Month");

        jLabel36.setText("Total sales of the Year");

        txt_total.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_totalActionPerformed(evt);
            }
        });

        jTable11.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Quantity", "Price per unit", "Total Sold"
            }
        ));
        jScrollPane11.setViewportView(jTable11);

        jLabel40.setText("<---between--->");

        check_mnth.setText("Check");
        check_mnth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_mnthActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(323, 323, 323)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(check_mnth)
                        .addContainerGap(248, Short.MAX_VALUE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(choose_month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(End_month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(234, 234, 234))))
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(213, 213, 213)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(151, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(choose_month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(End_month, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(check_mnth)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(112, 112, 112))
        );

        jTabbedPane2.addTab("Monthly Sales Report", jPanel10);

        Annual_saless.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Quanatity", "Price per unit", "Total Sold"
            }
        ));
        jScrollPane10.setViewportView(Annual_saless);

        jLabel18.setText("Total sales of the Year");

        jLabel33.setText("Choose Date");

        check3_btn.setText("check");
        check3_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check3_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(SalesoftheYear_txt, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                            .addComponent(date_txt))
                        .addGap(32, 32, 32)
                        .addComponent(check3_btn))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(174, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(date_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SalesoftheYear_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(check3_btn)))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95))
        );

        jTabbedPane2.addTab("Annual Sales", jPanel11);

        Total_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product Name", "Region (Branch)", "Customer id", "Quantity", "Price per unit", "Total Sold"
            }
        ));
        jScrollPane9.setViewportView(Total_table);

        jLabel5.setText("Choose Date");

        jLabel15.setText("Total sales of the day");

        check2_btn.setText("Check");
        check2_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check2_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 634, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TotalSales_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(225, 225, 225))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(454, 454, 454)
                        .addComponent(check2_btn)))
                .addContainerGap(182, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Date_choose1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(Date_choose1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(check2_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(TotalSales_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        jTabbedPane2.addTab("Daily Sales Report", jPanel9);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1214, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 184, Short.MAX_VALUE)
        );

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel19.setText("Sales");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(166, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 998, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(455, 455, 455))))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addGap(67, 67, 67))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                    .addContainerGap(441, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("tab5", jPanel5);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel17.setText("Branch performance ");

        Branch_Performance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Product name", "Customer ID", "Quantity", "Total sales"
            }
        ));
        jScrollPane6.setViewportView(Branch_Performance);

        jLabel20.setText("Total of region");

        jButton13.setText("Check");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jLabel31.setText("Enter Branch");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(378, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(350, 350, 350))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Enter_branch, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jButton13)
                                .addGap(83, 83, 83)))
                        .addGap(420, 420, 420))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(Total_region, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(421, 421, 421))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(367, 367, 367))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel17)
                .addGap(29, 29, 29)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Enter_branch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton13)
                .addGap(47, 47, 47)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Total_region, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab6", jPanel6);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel23.setText("User Management");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setText("Users");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel25.setText("Email");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel26.setText("Password");

        Email_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Email_txtActionPerformed(evt);
            }
        });

        password_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                password_txtActionPerformed(evt);
            }
        });

        jButton10.setText("Add");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel27.setText("Users role");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(415, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Email_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(411, 411, 411))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(10, 10, 10))
                                    .addGroup(jPanel7Layout.createSequentialGroup()
                                        .addGap(60, 60, 60)
                                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(password_txt)
                                    .addComponent(user_txt)
                                    .addComponent(user_role, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel23))
                        .addGap(374, 374, 374))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jButton10)
                        .addGap(477, 477, 477))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(user_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(password_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(user_role, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Email_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton10)
                .addGap(284, 284, 284))
        );

        jTabbedPane1.addTab("tab7", jPanel7);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 43, 1200, 600));

        jButton1.setText("|||");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (drawer.isShow()) {
            drawer.hide();
        } else {
            drawer.show();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void customer_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customer_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_customer_idActionPerformed

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        best_sales.setText("");
    }//GEN-LAST:event_clearActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int productId = Integer.parseInt(best_sales.getText().trim());

        JTable table = performance;
        javax.swing.JTextField bestSalesRegionField = bestt_saless;

        productPerformance(productId, table, bestSalesRegionField);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        //customer behaviour
        int customerId = Integer.parseInt(Customer_id.getText()); // Get the customer ID from the text field
        JTable table = customer_tbl; // Reference to your table

        customer(customerId, table);
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        String branchName = Enter_branch.getText(); // Get the branch name from the text field
        JTable table = Branch_Performance; // Reference to your table
        javax.swing.JTextField totalAmountField = Total_region; // Reference to the total amount field

        Branch(branchName, table, totalAmountField);
    }//GEN-LAST:event_jButton13ActionPerformed

    private void password_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_password_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_password_txtActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed

// Get the text input from the form fields
        String fullName = user_txt.getText();
        String email = Email_txt.getText();
        String userType = user_role.getText();
        String password = password_txt.getText();

// Call the register method
        register(fullName, email, userType, password);


    }//GEN-LAST:event_jButton10ActionPerformed

    private void Email_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Email_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Email_txtActionPerformed

    private void check_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_btnActionPerformed
        // Assuming 'itate_fld' and 'best_selling_product_tbl' are already defined within the JPanel class

        bestSAlproduct(date_choose, product_tbl); //calling data using button
    }//GEN-LAST:event_check_btnActionPerformed

    private void check2_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check2_btnActionPerformed
        daily(Date_choose1, Total_table, TotalSales_txt);
    }//GEN-LAST:event_check2_btnActionPerformed

    private void check_mnthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_mnthActionPerformed
        // TODO add your handling code here:
        monthly(choose_month, End_month, jTable11, txt_total);
    }//GEN-LAST:event_check_mnthActionPerformed

    private void txt_totalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_totalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_totalActionPerformed

    private void check3_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check3_btnActionPerformed
        Annual(date_txt, SalesoftheYear_txt, Annual_saless);
    }//GEN-LAST:event_check3_btnActionPerformed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        Customer_id.setText("");
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int customerId = Integer.parseInt(customer_id.getText());
        int productId = Integer.parseInt(Product_id.getText());
        String productName = Product_name.getText();
        int quantity = (int) qty_spin.getValue(); //JSpinner
        double pricePerUnit = Double.parseDouble(unit_price.getText());
        String branch = Region.getText();
        java.util.Date selectedDate = date_id.getDate();

// Assuming DefaultTableModel is already set up for the JTable "item_details"
        DefaultTableModel model = (DefaultTableModel) iteams_tbl.getModel();

// Call the insertRecord method, passing txt_total_price as a parameter
        input(customerId, productId, productName, quantity, pricePerUnit, branch, selectedDate, model, Total_Price);

// Call the insertRecord method, passing txt_total_price as a parameter

    }//GEN-LAST:event_jButton3ActionPerformed

    private void Product_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Product_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Product_idActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Product_id.setText("");
        Total_Price.setText("");
        customer_id.setText("");
        unit_price.setText("");
        Product_name.setText("");
        Region.setText("");
        date_id.setDate(null); // Clear the date picker
        qty_spin.setValue(0); // Reset spinner to default value

        try {
            // Get the selected row
            int selectedRow = iteams_tbl.getSelectedRow(); // Replace 'tblProducts' with your JTable variable name
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                return;
            }

            // Get the primary key or unique identifier (e.g., customer_id or product_id)
            String customer_id = iteams_tbl.getValueAt(selectedRow, 0).toString(); // Column 0 as example
            String Product_id = iteams_tbl.getValueAt(selectedRow, 1).toString(); // Column 1 as example

            // Confirm deletion
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this record?");
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            // Delete from database
            String query = "DELETE FROM products WHERE customer_id = ? AND product_id = ?"; // Update table and column names
            Connection con = database.getConnection(); // Ensure this method works correctly in your database class
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, customer_id);
            pst.setString(2, Product_id);
            pst.executeUpdate();

            // Remove row from JTable
            DefaultTableModel model = (DefaultTableModel) iteams_tbl.getModel();
            model.removeRow(selectedRow);

            // Success message
            JOptionPane.showMessageDialog(null, "Record deleted successfully!");

            // Close connections
            pst.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    // TODO add your handling code here:

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            // Get inputs from text fields
            String customerId = customer_id.getText();
            String productId = Product_id.getText();
            String productName = Product_name.getText();
            String unitPrice = unit_price.getText();
            String region = Region.getText();
            String qty = qty_spin.getValue().toString();

            // Convert quantity and unit price to numbers
            int quantity = Integer.parseInt(qty);
            float unitPriceValue = Float.parseFloat(unitPrice);

            // Calculate total price
            TotalCalculation calculator = new TotalCalculation();
            double totalPrice = calculator.total_calculation(quantity, unitPriceValue);

            // Display the calculated total price in txt_totalprice
            Total_Price.setText(String.format("%.2f", totalPrice));

            // Validate and convert date
            java.util.Date utilDate = date_id.getDate();
            if (utilDate == null) {
                JOptionPane.showMessageDialog(null, "Please select a valid date.");
                return;
            }
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            // Establish connection to the database
            Connection con = database.getConnection();
            if (con == null) {
                JOptionPane.showMessageDialog(null, "Failed to connect to the database.");
                return;
            }

            // Prepare SQL query
            String query = "UPDATE products SET product_id=?, product_name=?, qty=?, price_perunit=?, date=?, total_price=?, region=? WHERE customer_id=?";
            PreparedStatement pst = con.prepareStatement(query);

            // Set values
            pst.setInt(1, Integer.parseInt(productId));
            pst.setString(2, productName);
            pst.setInt(3, quantity);
            pst.setFloat(4, unitPriceValue);
            pst.setDate(5, sqlDate);
            pst.setDouble(6, totalPrice);
            pst.setString(7, region);
            pst.setInt(8, Integer.parseInt(customerId));

            // Execute update
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Product Successfully Updated");

            // Refresh the table
            DefaultTableModel model = (DefaultTableModel) iteams_tbl.getModel();
            model.setRowCount(0);
            datadisplay();

            // Clear the form fields
            customer_id.setText("");
            Product_id.setText("");
            Product_name.setText("");
            qty_spin.setValue(0);
            unit_price.setText("");
            Region.setText("");
            Total_Price.setText("");
            date_id.setDate(null);

            // Close resources
            pst.close();
            con.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "An error occurred while updating the product. Please try again.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An unexpected error occurred: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void iteams_tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iteams_tblMouseClicked
        // Get the table model and selected row
        DefaultTableModel df = (DefaultTableModel) iteams_tbl.getModel();
        int selectedIndex = iteams_tbl.getSelectedRow();

        // Check if a valid row is selected
        if (selectedIndex != -1) {
            // Set the values in the text fields from the selected row
            customer_id.setText(df.getValueAt(selectedIndex, 0).toString());
            Product_id.setText(df.getValueAt(selectedIndex, 1).toString());
            Product_name.setText(df.getValueAt(selectedIndex, 2).toString());
            qty_spin.setValue(Integer.parseInt(df.getValueAt(selectedIndex, 3).toString()));
            unit_price.setText(df.getValueAt(selectedIndex, 4).toString());

            // Handle the date field (column 5)
            Object dateObj = df.getValueAt(selectedIndex, 5);
            if (dateObj != null) {
                if (dateObj instanceof java.sql.Date) {
                    java.sql.Date sqlDate = (java.sql.Date) dateObj;
                    // Convert from java.sql.Date to java.util.Date
                    java.util.Date utilDate = new java.util.Date(sqlDate.getTime());
                    // Assuming 'date_id' is a JDateChooser or similar date picker component
                    date_id.setDate(utilDate);  // Set the util date
                } else if (dateObj instanceof String) {
                    try {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date parsedDate = sdf.parse(dateObj.toString());
                        // Assuming 'date_id' is a JDateChooser or similar date picker component
                        date_id.setDate(parsedDate);  // Set parsed date to the date picker
                    } catch (java.text.ParseException e) {
                        JOptionPane.showMessageDialog(null, "Error parsing date: " + e.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Unexpected date format.");
                }
            } else {
                // If dateObj is null, you can set the date to null, or use a default value
                date_id.setDate(null);  // Use this if 'date_id' is a JDateChooser
            }
            Total_Price.setText(df.getValueAt(selectedIndex, 6).toString());
            Region.setText(df.getValueAt(selectedIndex, 7).toString());
        }

    }//GEN-LAST:event_iteams_tblMouseClicked

    //Best saleing product start
    public void bestSAlproduct(JDateChooser dateChooser, JTable btable) {
        if (dateChooser.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Please enter the date first!");
            return;
        }

        java.util.Date selectedDate = dateChooser.getDate();
        java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

        DefaultTableModel model = (DefaultTableModel) btable.getModel();
        model.setRowCount(0); // Clear existing data but keep column headers.

        try (Connection con = database.getConnection(); // Make sure database class exists and returns valid connection
                 PreparedStatement stmt = con.prepareStatement("SELECT product_name, SUM(qty) AS total_quantity "
                        + "FROM products WHERE date = ? "
                        + "GROUP BY product_name ORDER BY total_quantity DESC LIMIT 1")) {

            stmt.setDate(1, sqlDate);
            try (ResultSet rs = stmt.executeQuery()) {
                boolean dataFound = false;
                if (model.getColumnCount() == 0) { // Ensure table column headers are initialized once
                    model.setColumnIdentifiers(new String[]{"Product", "Quantity Sold"});
                }

                while (rs.next()) {
                    dataFound = true;
                    Product product = new Product();
                    product.setProductname(rs.getString("product_name"));
                    product.setQty(rs.getInt("total_quantity"));

                    model.addRow(new Object[]{
                        product.getProductname(),
                        product.getQty()
                    });
                }

                if (!dataFound) {
                    JOptionPane.showMessageDialog(null, "No data found for the selected date.");
                    // Optional: clear table visually
                    model.setRowCount(0); // Clear existing data but keep column headers.
                }
            }

        } catch (SQLException ex) {
            // Log the exception for detailed debugging
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    //Best saleing product end
    //SAles
    //Daily sales 
    public void daily(JDateChooser date, JTable jTable1, JTextField daily_sales_txtfld) {
        try {

            // Convert date from JDateChooser
            java.util.Date selectedDate = date.getDate();
            if (selectedDate == null) {
                JOptionPane.showMessageDialog(null, "Please select a date.");
                return;
            }

            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

            // Database query to fetch daily sales data
            String query = "SELECT product_name, qty, price_perunit, (qty * price_perunit) AS total_sold "
                    + "FROM products WHERE date = ?";

            try (Connection con = database.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {

                // Bind the date parameter to the query
                stmt.setDate(1, sqlDate);

                try (ResultSet rs = stmt.executeQuery()) {
                    // Define table model with the correct column names
                    DefaultTableModel model = new DefaultTableModel(new String[]{"Product Name", "Quantity", "Unit Price", "Total Sold"}, 0);
                    jTable1.setModel(model);

                    double overallTotal = 0;
                    boolean dataFound = false;  // Flag to track if any data was found

                    // Process ResultSet and populate the table using encapsulation
                    while (rs.next()) {
                        String productName = rs.getString("product_name");
                        int quantity = rs.getInt("qty");
                        double pricePerUnit = rs.getDouble("price_perunit");
                        double totalSold = rs.getDouble("total_sold");

                        // Add data to table model
                        model.addRow(new Object[]{productName, quantity, pricePerUnit, totalSold});

                        // Update the overall total
                        overallTotal += totalSold;
                        dataFound = true;  // Set flag to true if data is found
                    }

                    // If no data found, set the daily sales text field to empty
                    if (!dataFound) {
                        daily_sales_txtfld.setText(""); // Empty the text field
                    } else {
                        // Set the overall total in the daily sales text field
                        daily_sales_txtfld.setText(String.format("%.2f", overallTotal));  // Corrected line
                    }
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    //Daily sales
    //Monthly sales
    public void monthly(JDateChooser date_start, JDateChooser date_end, JTable jTable1, JTextField monthly_sales_txtfld) {
        try {
            // Ensure both date pickers are initialized
            if (date_start == null || date_end == null) {
                JOptionPane.showMessageDialog(null, "Date pickers are not initialized.");
                return;
            }

            // Convert dates from JDateChooser
            java.util.Date selectedStartDate = date_start.getDate();
            java.util.Date selectedEndDate = date_end.getDate();

            if (selectedStartDate == null || selectedEndDate == null) {
                JOptionPane.showMessageDialog(null, "Please select both start and end dates.");
                return;
            }

            // Check if start date is after end date (invalid range)
            if (selectedStartDate.after(selectedEndDate)) {
                JOptionPane.showMessageDialog(null, "Start date cannot be after end date.");
                monthly_sales_txtfld.setText("");  // Clear the total sales field if the date range is invalid
                return;
            }

            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlStartDate = new java.sql.Date(selectedStartDate.getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(selectedEndDate.getTime());

            // Database query to fetch monthly sales data between the selected date range
            String query = "SELECT product_name, qty, price_perunit, (qty * price_perunit) AS total_sold "
                    + "FROM products WHERE date BETWEEN ? AND ?";

            try (Connection con = database.getConnection(); PreparedStatement stmt = con.prepareStatement(query)) {

                // Bind the start and end date parameters to the query
                stmt.setDate(1, sqlStartDate);
                stmt.setDate(2, sqlEndDate);

                try (ResultSet rs = stmt.executeQuery()) {
                    // Define table model with the correct column names
                    DefaultTableModel model = new DefaultTableModel(new String[]{"Product Name", "Quantity", "Unit Price", "Total Sold"}, 0);
                    jTable1.setModel(model);

                    double overallTotal = 0;

                    // Process ResultSet and populate the table
                    while (rs.next()) {
                        String productName = rs.getString("product_name");
                        int quantity = rs.getInt("qty");
                        double pricePerUnit = rs.getDouble("price_perunit");
                        double totalSold = rs.getDouble("total_sold");

                        // Add data to table model
                        model.addRow(new Object[]{productName, quantity, pricePerUnit, totalSold});

                        // Update the overall total
                        overallTotal += totalSold;
                    }

                    // Set the overall total in the monthly sales text field
                    monthly_sales_txtfld.setText(String.format("%.2f", overallTotal));  // Display the total in the text field
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }

    //AnualSales 
    public void Annual(JTextField yearField, JTextField annualSalesField, JTable table) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // Validate year input
            String yearInput = yearField.getText().trim();
            if (yearInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid year!", "Error", JOptionPane.ERROR_MESSAGE);
                annualSalesField.setText(""); // Clear annual sales field
                return;
            }

            int year = Integer.parseInt(yearInput); // Parse the year from the text field

            // Establish the database connection
            con = database.getConnection();

            // Corrected SQL query
            String query = "SELECT product_name, SUM(qty) AS total_quantity, price_perunit, "
                    + "SUM(qty * price_perunit) AS total_sold "
                    + "FROM products WHERE YEAR(date) = ? GROUP BY product_name, price_perunit";
            pst = con.prepareStatement(query);
            pst.setInt(1, year);
            rs = pst.executeQuery();

            // Prepare the table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear previous data

            double totalSalesAmount = 0; // Initialize the overall sales total
            boolean hasData = false; // Flag to check if there is any data for the year

            // Process the result set and populate the table
            while (rs.next()) {
                String productName = rs.getString("product_name");
                int quantity = rs.getInt("total_quantity");
                double unitPrice = rs.getDouble("price_perunit");
                double totalSold = rs.getDouble("total_sold");

                // Add row data to the table
                model.addRow(new Object[]{productName, quantity, unitPrice, totalSold});

                // Accumulate total sales
                totalSalesAmount += totalSold;

                hasData = true; // Data found, set flag to true
            }

            // Set the overall total sales in the text field or leave empty if no data
            if (hasData) {
                annualSalesField.setText(String.format("%.2f", totalSalesAmount));
            } else {
                annualSalesField.setText(""); // Clear annual sales field if no data
                JOptionPane.showMessageDialog(null, "Invalid year, no data found.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid year format! Please enter a numeric value.", "Error", JOptionPane.ERROR_MESSAGE);
            annualSalesField.setText(""); // Clear annual sales field
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //AnualSales end
    //sales
    //Branch performance start
    public void Branch(String branchName, JTable table, javax.swing.JTextField totalAmountField) {
        // Check if the branch name is empty
        if (branchName == null || branchName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter the branch name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // Establish the connection
            con = database.getConnection();

            // Query to fetch data from the database
            String query = "SELECT product_name, customer_id, qty, price_perunit FROM products WHERE region = ?";
            pst = con.prepareStatement(query);
            pst.setString(1, branchName);
            rs = pst.executeQuery();

            // Table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear the table

            double totalAmount = 0;
            boolean dataFound = false; // Flag to check if any data is found

            // Loop through the result set
            while (rs.next()) {
                dataFound = true; // Data found in the result set
                String productName = rs.getString("product_name");
                int customerId = rs.getInt("customer_id");
                int quantity = rs.getInt("qty");
                double pricePerUnit = rs.getDouble("price_perunit");

                // Calculate the total sales for the product
                double totalSales = new TotalCalculation().total_calculation(quantity, pricePerUnit);

                // Add the data to the table
                model.addRow(new Object[]{productName, customerId, quantity, totalSales});

                // Add to the total amount
                totalAmount += totalSales;
            }

            // Check if no data was found
            if (!dataFound) {
                JOptionPane.showMessageDialog(null, "No data found for the branch: " + branchName, "No Data", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Set the total amount in the text field
                totalAmountField.setText(String.valueOf(totalAmount));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    //Branch performance end
    //adding new user
    public String register(String user, String password, String userrole, String Email) {
        try {
            // Check if any of the fields are empty
            if (user.isEmpty() || password.isEmpty() || userrole.isEmpty() || Email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields.");
                return "empty fields";  // Return if any field is empty
            }

            // Validate userType
            if (!"admin".equalsIgnoreCase(userrole) && !"employee".equalsIgnoreCase(userrole)) {
                JOptionPane.showMessageDialog(null, "Invalid UserType! Please enter 'admin' or 'employee'.", "Error", JOptionPane.ERROR_MESSAGE);
                return "invalid usertype";  // Return invalid usertype
            }

            // Database connection
            Connection con = database.getConnection();
            Statement st = con.createStatement();

            // Construct SQL query to insert a new user into the database
            String query = "INSERT INTO register (user, email, user_role, password) "
                    + "VALUES ('" + user + "', '" + password + "', '" + userrole + "', '" + Email + "')";

            // Execute the query
            st.execute(query);

            // Show success message and clear the input fields
            JOptionPane.showMessageDialog(null, "New User is Registered Successfully! Thank you.");
            return "success";  // Return success if registration is successful

        } catch (SQLException | HeadlessException e) {
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return "error";  // Return error if an exception occurs
        }
    }

    //New registar end
    //customer behaviour
    public void customer(int customerId, JTable table) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // Establish the connection
            con = database.getConnection();

            // Query to fetch customer behavior data and calculate total_spent dynamically
            String query = "SELECT product_name, region, (qty * price_perunit) AS total_spent FROM products WHERE customer_id = ?";
            pst = con.prepareStatement(query);
            pst.setInt(1, customerId);
            rs = pst.executeQuery();

            // Table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear the table

            // Loop through the result set
            while (rs.next()) {
                String productName = rs.getString("product_name");
                String region = rs.getString("region");
                double totalSpent = rs.getDouble("total_spent");

                // Add the data to the table
                model.addRow(new Object[]{productName, region, totalSpent});
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    //customer behaviour end
    //Product performance

    public void productPerformance(int productId, JTable table, javax.swing.JTextField best_sales_region_txtf) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // Establish the connection
            con = database.getConnection();
            if (con == null) {
                System.out.println("Database connection failed.");
                return;
            }

            // Query to fetch data from the database
            String query = "SELECT product_name, region, SUM(qty) AS quantity_sold, SUM(qty * price_perunit) AS total_sales "
                    + "FROM products WHERE product_id = ? GROUP BY product_name, region ORDER BY total_sales DESC";
            pst = con.prepareStatement(query);
            pst.setInt(1, productId);
            rs = pst.executeQuery();

            // Table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear the table

            String bestRegion = null;
            double maxSales = 0;

            // Loop through the result set
            while (rs.next()) {
                String productName = rs.getString("product_name");
                String region = rs.getString("region");
                int quantitySold = rs.getInt("quantity_sold");
                double totalSales = rs.getDouble("total_sales");

                // Update the best region if this region has higher sales
                if (totalSales > maxSales) {
                    maxSales = totalSales;
                    bestRegion = region;
                }

                // Add only product name, quantity sold, and total sales to the table
                model.addRow(new Object[]{productName, quantitySold, totalSales});
            }

            // Display the best sales region in the text field
            if (bestRegion != null) {
                best_sales_region_txtf.setText(bestRegion);
            } else {
                best_sales_region_txtf.setText("No sales data found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
//end projects
//items 

    public void input(int customerId, int productId, String productName, int quantity,
            double pricePerUnit, String branch, java.util.Date selectedDate,
            DefaultTableModel model, JTextField txtTotalPrice) {
        // Validate inputs
        try {
            // Calculate total price using the total_calculation class
            TotalCalculation calc = new TotalCalculation();
            double totalPrice = calc.total_calculation(quantity, pricePerUnit);

            // Set the calculated total price in the txt_total_price field
            txtTotalPrice.setText(String.valueOf(totalPrice)); // Update the text field with the calculated total price

            // Convert java.util.Date to java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());

            // SQL query for insertion
            String query = "INSERT INTO products (customer_id, product_id, product_name, qty, price_perunit, region, date, total_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // Execute the query
            try (Connection con = database.getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
                pstmt.setInt(1, customerId);
                pstmt.setInt(2, productId);
                pstmt.setString(3, productName);
                pstmt.setInt(4, quantity);
                pstmt.setDouble(5, pricePerUnit);
                pstmt.setString(6, branch);
                pstmt.setDate(7, sqlDate);
                pstmt.setDouble(8, totalPrice);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Record successfully added!");

                    // Add the new record to the JTable
                    model.addRow(new Object[]{
                        customerId, // customer_id
                        productId, // product_id
                        productName, // product_name
                        quantity, // quantity
                        pricePerUnit,// price_per_unit
                        sqlDate, // date (java.sql.Date to match the table column type)
                        totalPrice, // total_price (calculated automatically)
                        branch // region
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "No record was inserted.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage());
        }
    }

//items end
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Annual_saless;
    private javax.swing.JTable Branch_Performance;
    private javax.swing.JTextField Customer_id;
    private com.toedter.calendar.JDateChooser Date_choose1;
    private javax.swing.JTextField Email_txt;
    private com.toedter.calendar.JDateChooser End_month;
    private javax.swing.JTextField Enter_branch;
    private javax.swing.JTextField Product_id;
    private javax.swing.JTextField Product_name;
    private javax.swing.JTextField Region;
    private javax.swing.JTextField SalesoftheYear_txt;
    private javax.swing.JTextField TotalSales_txt;
    private javax.swing.JTextField Total_Price;
    private javax.swing.JTextField Total_region;
    private javax.swing.JTable Total_table;
    private javax.swing.JTextField best_sales;
    private javax.swing.JTextField bestt_saless;
    private javax.swing.JButton check2_btn;
    private javax.swing.JButton check3_btn;
    private javax.swing.JButton check_btn;
    private javax.swing.JButton check_mnth;
    private com.toedter.calendar.JDateChooser choose_month;
    private javax.swing.JButton clear;
    private javax.swing.JTextField customer_id;
    private javax.swing.JTable customer_tbl;
    private com.toedter.calendar.JDateChooser date_choose;
    private com.toedter.calendar.JDateChooser date_id;
    private javax.swing.JTextField date_txt;
    private javax.swing.JTable iteams_tbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable11;
    private javax.swing.JTextField password_txt;
    private javax.swing.JTable performance;
    private javax.swing.JTable product_tbl;
    private javax.swing.JSpinner qty_spin;
    private javax.swing.JTextField txt_total;
    private javax.swing.JTextField unit_price;
    private javax.swing.JTextField user_role;
    private javax.swing.JTextField user_txt;
    // End of variables declaration//GEN-END:variables
}
