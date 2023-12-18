package paymentstracking;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class PaymentsTrackingGUI extends javax.swing.JFrame {
    
    Connection con;
    public PaymentsTrackingGUI() {
        initComponents();
        connectionPayment();

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                int selectedColumn = table.getSelectedColumn();

                if (selectedRow != -1 && selectedColumn == 1) { // Assuming balance is in column 1
                    String name = (String) table.getValueAt(selectedRow, 0);
                    double bal = (double) table.getValueAt(selectedRow, 1);
                    String paymentMethod = (String) table.getValueAt(selectedRow, 2);

                    if (bal == 0) {
                        JOptionPane.showMessageDialog(PaymentsTrackingGUI.this, name + " is Fully Paid");
                    } else {
                        JOptionPane.showMessageDialog(PaymentsTrackingGUI.this, name + " has a remaining balance of " + bal);
                    }
                }
            }
        });
    }

    
    
    private boolean isAttendeeExists(String name) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) FROM attendee_balance WHERE fullname = ?");
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        stmt.close();

        return count > 0;
    }
    
    
    void connectionPayment(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/finaloop","root","root");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaymentsTracking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentsTracking.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        input = new javax.swing.JTextField();
        addAttendeeButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        balance = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        method = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        refresh = new javax.swing.JButton();
        payButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputActionPerformed(evt);
            }
        });

        addAttendeeButton.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        addAttendeeButton.setText("Add Attendee");
        addAttendeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAttendeeButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel1.setText("Attendee");

        jLabel2.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel2.setText("Balance");

        method.setFont(new java.awt.Font("Segoe UI Symbol", 1, 14)); // NOI18N
        method.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "GCash", "Cash On Hand", "Pera Padala" }));
        method.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                methodActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        jLabel3.setText("Payment Method");

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Full Name", "Balance", "Payment Method"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
        }

        refresh.setText("Refresh");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        payButton.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        payButton.setText("Pay");
        payButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payButtonActionPerformed(evt);
            }
        });

        deleteButton.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(input, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(balance, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(method, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel1)
                                .addGap(106, 106, 106)
                                .addComponent(jLabel2)
                                .addGap(115, 115, 115)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(addAttendeeButton)))
                        .addGap(0, 47, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(payButton, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addComponent(refresh)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(input)
                    .addComponent(method, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(balance))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addAttendeeButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(payButton)
                    .addComponent(deleteButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inputActionPerformed

    private void addAttendeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addAttendeeButtonActionPerformed
        try {
            String name = input.getText().trim(); // Trim to remove leading and trailing spaces
            String paymentMethod = method.getSelectedItem().toString();
            String balanceText = balance.getText().trim();

            if (name.isEmpty() || balanceText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please provide both name and balance.");
                return; 
            }

            // Check if attendee with the same fullname already exists
            if (isAttendeeExists(name)) {
                JOptionPane.showMessageDialog(this, "Attendee with the same Full Name already exists.");
                return;
            }

            double _bal;
            try {
                _bal = Double.parseDouble(balanceText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric balance.");
                return; // Stop further processing
            }

            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            String bal = decimalFormat.format(_bal);

            PreparedStatement stmt = con.prepareStatement("INSERT INTO attendee_balance VALUES (?,?,?)");
            stmt.setString(1, name);
            stmt.setString(2, bal);
            stmt.setString(3, paymentMethod);
            stmt.execute();
            stmt.close();

            System.out.println(name + " is added with the balance of " + bal + " by " + paymentMethod);

            refreshActionPerformed(evt);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentsTrackingGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_addAttendeeButtonActionPerformed

    private void methodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_methodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_methodActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);

        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM attendee_balance");
            while(rs.next()){
                String name = rs.getString("fullname");
                double bal = rs.getDouble("balance");
                String paymentMethod = rs.getString("payment_method");

                // Format the balance with two decimal places
                String formattedBalance = String.format("%.2f", bal);

                System.out.println(name + " " + formattedBalance + " " + paymentMethod);

                // Use a Double array to store data in the table model
                tableModel.addRow(new Object[]{name, Double.valueOf(formattedBalance), paymentMethod});
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(PaymentsTrackingGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_refreshActionPerformed

    private void payButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payButtonActionPerformed
        try {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select an attendee from the table.");
                return;
            }

            String name = (String) table.getValueAt(selectedRow, 0);
            double currentBalance = (double) table.getValueAt(selectedRow, 1);

            String amountStr = JOptionPane.showInputDialog(this, "Enter the amount to be deducted for " + name + ":");
            if (amountStr == null || amountStr.trim().isEmpty()) {
                return;
            }

            double deductionAmount = Double.parseDouble(amountStr);

            if (deductionAmount <= 0 || deductionAmount > currentBalance) {
                JOptionPane.showMessageDialog(this, "Invalid deduction amount.");
                return;
            }

            PreparedStatement updateStmt = con.prepareStatement("UPDATE attendee_balance SET balance = balance - ? WHERE fullname = ?");
            updateStmt.setDouble(1, deductionAmount);
            updateStmt.setString(2, name);
            updateStmt.executeUpdate();
            updateStmt.close();

            double remainingBalance = currentBalance - deductionAmount;

            // Format the remaining balance with two decimal places
            String formattedRemainingBalance = String.format("%.2f", remainingBalance);

            if (remainingBalance == 0.0) {
                JOptionPane.showMessageDialog(this, name + " is fully paid!");
            } else {
                JOptionPane.showMessageDialog(this, name + " has partially paid. Remaining balance: " + formattedRemainingBalance);
            }

            refreshActionPerformed(evt);

            System.out.println("Deducted " + deductionAmount + " from the balance of " + name);
        } catch (SQLException | NumberFormatException ex) {
            Logger.getLogger(PaymentsTrackingGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_payButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        try {
            int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an attendee from the table.");
            return;
        }

        String name = (String) table.getValueAt(selectedRow, 0);
        Double balanceValue = (Double) table.getValueAt(selectedRow, 1);

        double currentBalance;

        if (balanceValue == 0.0) {
            currentBalance = 0.0; 
        } else {
            currentBalance = balanceValue;
        }

        if (currentBalance > 0) {
            int confirmRemainingBalance = JOptionPane.showConfirmDialog(this,
                        "Attendee " + name + " has a remaining balance of " + currentBalance
                                + ". Are you sure you want to delete?",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                if (confirmRemainingBalance == JOptionPane.NO_OPTION) {
                    return; // User chose not to delete with remaining balance
                }
            }

            int confirmDialogResult = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete the attendee " + name + "?",
                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

            if (confirmDialogResult == JOptionPane.YES_OPTION) {
                PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM attendee_balance WHERE fullname = ?");
                deleteStmt.setString(1, name);
                deleteStmt.executeUpdate();
                deleteStmt.close();

                JOptionPane.showMessageDialog(this, "Attendee " + name + " deleted successfully.");

                refreshActionPerformed(evt);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentsTrackingGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(PaymentsTrackingGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaymentsTrackingGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaymentsTrackingGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaymentsTrackingGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaymentsTrackingGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAttendeeButton;
    private javax.swing.JTextField balance;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField input;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> method;
    private javax.swing.JButton payButton;
    private javax.swing.JButton refresh;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
