/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import db.database;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;


/**
 *
 * @author GOBIHARAN
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        lbl_logo = new javax.swing.JLabel();
        lbl_heading = new javax.swing.JLabel();
        lbl_Email = new javax.swing.JLabel();
        txt_username = new javax.swing.JTextField();
        lbl_password = new javax.swing.JLabel();
        radio_btn = new javax.swing.JRadioButton();
        btn_login = new javax.swing.JButton();
        btn_clear = new javax.swing.JButton();
        cmb_ac_type = new javax.swing.JComboBox<>();
        txt_password = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 0, 0));
        jPanel2.setToolTipText("");
        jPanel2.setLayout(null);

        lbl_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMG/Logo.png"))); // NOI18N
        jPanel2.add(lbl_logo);
        lbl_logo.setBounds(60, 40, 310, 230);

        lbl_heading.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbl_heading.setText("Login");

        lbl_Email.setText("name");

        txt_username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_usernameActionPerformed(evt);
            }
        });

        lbl_password.setText("Password");

        radio_btn.setText("Show password");
        radio_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radio_btnMouseClicked(evt);
            }
        });
        radio_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radio_btnActionPerformed(evt);
            }
        });

        btn_login.setText("Login");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        btn_clear.setText("Clear");
        btn_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_clearActionPerformed(evt);
            }
        });

        cmb_ac_type.setBackground(new java.awt.Color(255, 0, 0));
        cmb_ac_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Employee" }));
        cmb_ac_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb_ac_typeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_password)
                            .addComponent(lbl_Email))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(cmb_ac_type, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btn_clear)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                                        .addComponent(btn_login))
                                    .addComponent(txt_username)
                                    .addComponent(txt_password, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(50, 50, 50))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(radio_btn)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_heading)
                        .addGap(146, 146, 146))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 36, Short.MAX_VALUE)
                .addComponent(lbl_heading, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_Email)
                    .addComponent(txt_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_password)
                    .addComponent(txt_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radio_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmb_ac_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_login)
                    .addComponent(btn_clear))
                .addGap(49, 49, 49))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void radio_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radio_btnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radio_btnActionPerformed

    private void btn_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_clearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_clearActionPerformed

    private void txt_usernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_usernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_usernameActionPerformed

    private void cmb_ac_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb_ac_typeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb_ac_typeActionPerformed

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed

        String username = txt_username.getText();
        String password = txt_password.getText();
        String userType = String.valueOf(cmb_ac_type.getSelectedItem());
        
        login(username, password, userType);
    }//GEN-LAST:event_btn_loginActionPerformed

    private void radio_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radio_btnMouseClicked
       if (radio_btn.isSelected()) {
            txt_password.setEchoChar((char) 0);
        } else {
            txt_password.setEchoChar('*');
        }
    
    }//GEN-LAST:event_radio_btnMouseClicked

    public String login(String username, String password, String userType) {

        if (username.isEmpty() || password.isEmpty() || userType.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields.");
            return "empty fields";  // Return empty fields if any field is empty
        }

        try {
            String query = "SELECT * FROM register WHERE user=? and user_role=?  and password=?";
            Connection con = database.getConnection();
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, username);
            pst.setString(2, userType);
            pst.setString(3, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String dbUserType = rs.getString("user_role"); // Get the user type from the database

                JOptionPane.showMessageDialog(null, "Email and password matched. You are logged in as " + dbUserType);

                if (dbUserType.equalsIgnoreCase("admin")) { // Case-insensitive comparison
                    Admin dashbaord = new Admin();
                    dashbaord.setVisible(true);
                    return "success";  // Return success for admin login
                } else if (dbUserType.equalsIgnoreCase("Employee")) { // Case-insensitive comparison

                    Employee userdashboard = new Employee();
                    userdashboard.setVisible(true);
                    return "success";  // Return success for employee login
                }
                return "invalid user type";  // Return invalid user type
            } else {
                JOptionPane.showMessageDialog(null, "Email and Password do not match.");

                return "invalid credentials";  // Return invalid credentials
            }
        } catch (SQLException | HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            return "error";  // Return error if exception occurs
        }
    }
      


    
    
    
    
    
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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_clear;
    private javax.swing.JButton btn_login;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmb_ac_type;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lbl_Email;
    private javax.swing.JLabel lbl_heading;
    private javax.swing.JLabel lbl_logo;
    private javax.swing.JLabel lbl_password;
    private javax.swing.JRadioButton radio_btn;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
