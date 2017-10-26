
package laundyclinic;

import java.sql.*;
import javax.swing.JOptionPane;
public class Transaction {
    public void addTransaction(String query){
        
        Connection con=null;
        Statement st=null;
        ResultSet res=null;
       
        JOptionPane.showMessageDialog(null,"Adding new Transaction");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itse","root","");
            st = con.createStatement();
            st.execute(query);
            JOptionPane.showMessageDialog(null, "The new Transaction was successfully added");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        finally{
            try{
                res=null;
                st.close();
                con.close();

            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e.toString());
            }
        }//finally

    }//add
    public void updateSale(String query){
        
        Connection con=null;
        Statement st=null;
        ResultSet res=null;
        
        JOptionPane.showMessageDialog(null, "Updating current Transaction");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itse","root","");
            st = con.createStatement();
            st.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "The Transaction Information was successfully updated");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        finally{
            try{
                res.close();
                st.close();
                con.close();

            }catch(Exception e){
                //JOptionPane.showMessageDialog(null, e.toString());
            }
        }//finally
    }//update
    
    public void deleteSale(String query){
        
        Connection con=null;
        Statement st=null;
        ResultSet res=null;
        
        JOptionPane.showMessageDialog(null, "Deleting the Transaction");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itse","root","");
            st = con.createStatement();
            st.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "The Transaction Information was successfully deleted");
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }
        finally{
            try{
                res.close();
                st.close();
                con.close();

            }catch(Exception e){
                //JOptionPane.showMessageDialog(null, e.toString());
            }
        }//finally
    }//update
}
