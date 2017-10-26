package laundyclinic;

import java.sql.*;
import java.util.Vector;
import javax.swing.*;

public class MyTools {
    // this code is used to manipulate the JTable
    // columnNames and data should be return to the caller to fill in the 
    // columns and rows of the table
    private Vector columnNames = new Vector();
    private Vector data = new Vector();
    private String myDatabaseName;
    public void setMyDatabase(String myDatabaseName){
        this.myDatabaseName=myDatabaseName;
    }
    public Vector getColumnNames(){
        return columnNames;
    }
    public Vector getData(){
        return data;
    }
    
    public void tableLoadData(String query){
        Statement st=null;
        Connection con=null;
        //String query=null;
        ResultSet res=null;
        ResultSetMetaData rsmd=null;
        //columnNames=new Vector();
        columnNames.clear();
        //data=new Vector();
        data.clear();
        //JOptionPane.showMessageDialog(null, query);
        try{
            Class.forName("com.mysql.jdbc.Driver");
            //String x = "jdbc:mysql://localhost:3306/sales" + "," + "root" + "," + "";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itse","root","");
            //con = DriverManager.getConnection(x);
            st = con.createStatement();
            
            res = st.executeQuery(query);
            rsmd = res.getMetaData();
            int column = rsmd.getColumnCount();         

            for(int i=1; i<=column; i++){
                columnNames.add(rsmd.getColumnLabel(i));
            }
            while(res.next()) {
                Vector row = new Vector(column);
                for(int i=1; i<=column; i++) {
                    row.addElement(res.getObject(i));
                }
                data.addElement(row);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            try{
                rsmd=null;
                
                query=null;
                st.close();
                res.close();
                con.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Finally Error:" + e.toString());
            }
        }
    }//reloadData
    
    //This code is used to manipulate the JComboBox
    //the parameters are:
    //JComboBox --> is where the data will be stored
    //query --> code to get data to the database. It should have only two columns
    //         the id and description
    //column1 --> description of column 1
    //column2 --> description of column 2
    public void loadComboData(JComboBox comboBox,String query,String column1, String column2){
        Connection con=null;
        Statement st=null;
        ResultSet res=null;
        comboBox.removeAll();
        
        try{
           Class.forName("com.mysql.jdbc.Driver");
           //String Base = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)}; DBQ=SL.mdb";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itse","root","");
           st = con.createStatement();
           //String query="Select posID,posDescription from tblposition";
           //res = st.executeQuery("Select posID,posDescription from tblposition");
           res = st.executeQuery(query);
            while(res.next()) {
                //positionCB.addItem(new Item(res.getInt("posID"),res.getString("posDescription")));
                //comboBox.addItem(new Item(res.getInt("posID"),res.getString("posDescription")));
                comboBox.addItem(new Item(res.getInt(column1),res.getString(column2)));
           }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            try{
                st.close();
                res.close();
                con.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Finally Error:" + e.toString());
            }
        }
    }
    // used to select a particular data in the combo box
    
     public void empComboData(JComboBox comboBox,String query,String column1, String column2, String column3){
        Connection con=null;
        Statement st=null;
        ResultSet res=null;
        comboBox.removeAll();
        
        try{
           Class.forName("com.mysql.jdbc.Driver");
           //String Base = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)}; DBQ=SL.mdb";
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itse","root","");
           st = con.createStatement();
           //String query="Select posID,posDescription from tblposition";
           //res = st.executeQuery("Select posID,posDescription from tblposition");
           res = st.executeQuery(query);
            while(res.next()) {
                //positionCB.addItem(new Item(res.getInt("posID"),res.getString("posDescription")));
                //comboBox.addItem(new Item(res.getInt("posID"),res.getString("posDescription")));
                comboBox.addItem(new Item(res.getInt(column1),res.getString(column2)+" "+res.getString(column3)));
           }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.toString());
        }finally{
            try{
                st.close();
                res.close();
                con.close();
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Finally Error:" + e.toString());
            }
        }
    }
    // used to select a particular data in the combo box
     
     
    public int setSelectedValue(JComboBox comboBox, String value){
        Item itemCombo;
        int i=0;
        for(i=0;i<comboBox.getItemCount();i++){
            itemCombo = (Item) comboBox.getItemAt(i);
            if(itemCombo.getDescription().equals(value)){
                //comboBox.setSelectedIndex(i);
                break;
            }
        }
        return i;
        //comboBox.updateUI();
    }//setSelectedValue
}//class
