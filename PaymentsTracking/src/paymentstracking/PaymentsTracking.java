/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package paymentstracking;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 *
 * @author acer
 */
public class PaymentsTracking {

    public static void main(String[] args) throws SQLException {
        PaymentsTracking play = new PaymentsTracking();
        play.connectionPayment();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PaymentsTrackingGUI().setVisible(true);
            }
        });
        
    }
    
    void connectionPayment(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/finaloop","root","root");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM attendee_balance");
            while(rs.next()){
                String name = rs.getString("fullname");
                Double bal = rs.getDouble("balance");
                String paymentMethod = rs.getString("payment_method");
                System.out.println(name+" "+ bal +" "+paymentMethod);
            }
            stmt.close();
            System.out.println("Connection success");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(PaymentsTracking.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
