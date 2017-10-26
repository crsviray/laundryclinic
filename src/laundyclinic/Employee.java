
package laundyclinic;

import java.sql.*;
import javax.swing.JOptionPane;
public class Employee {
    public void addEmployee(String query){
        
        Connection con=null;
        Statement st=null;
        ResultSet res=null;
       
        JOptionPane.showMessageDialog(null,"Adding new Employee");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itse","root","");
            st = con.createStatement();
            st.execute(query);
            JOptionPane.showMessageDialog(null, "The new Employee was successfully added");
            
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
    public void updateEmployee(String query){
        
        Connection con=null;
        Statement st=null;
        ResultSet res=null;
        
        JOptionPane.showMessageDialog(null, "Updating current Employee");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itse","root","");
            st = con.createStatement();
            st.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "The Employee Information was successfully updated");
            
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
    
    public void deleteEmployee(String query){
        
        Connection con=null;
        Statement st=null;
        ResultSet res=null;
        
        JOptionPane.showMessageDialog(null, "Deleting the Employee");
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itse","root","");
            st = con.createStatement();
            st.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "The Employee Information was successfully deleted");
            
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
