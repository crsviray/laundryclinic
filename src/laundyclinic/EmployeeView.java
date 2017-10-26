package laundyclinic;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class EmployeeView extends JInternalFrame{
    MyTools tools = new MyTools();
    JTable table;
    DefaultTableModel model;
    JTextField controlT,prodnoT, proddescT, prodwspT, prodrpT, prodoqT, filtT;
    JButton addB, clearB, deleteB, updateB;
    JComboBox statusCB, filtCB;
    JDateChooser dateOfRegDC;
    Employee product = new Employee();
    
    public EmployeeView(){
        super("Employees",false,true,true,true);
        Container pane = getContentPane();
        setLayout(null);

        JPanel panelTable = new JPanel(new GridLayout(1,1));
        panelTable.setBorder(BorderFactory.createTitledBorder("Employees"));
        
        table = new JTable();
        String query="SELECT Employee_ID as EmployeeID, Employee_Fname as FirstName, Employee_Mname as MiddleName, Employee_Lname as LastName, tblgender.GenderName as  Gender, DateOfBirth as Birthday, Employee_Address as Address, Employee_Phone as PhoneNumber FROM tblemployee INNER JOIN tblgender ON tblemployee.Gender_ID = tblgender.Gender_ID ORDER BY Employee_ID";
        tools.tableLoadData(query);
        model = new DefaultTableModel(tools.getData(),tools.getColumnNames());
        table.setModel(model);
        
        //JScrollPane mScrollPane = new JScrollPane(table);
        JScrollPane mScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(6).setPreferredWidth(200); //width of the column name
        table.getColumnModel().getColumn(7).setPreferredWidth(100); //width of the column name
        panelTable.add(mScrollPane);
        panelTable.setBounds(10,10, 700, 275);
        pane.add(panelTable);
        //----------------------------------------------------------------------
        JPanel filtP = new JPanel(new GridLayout(1,2));
        filtT = new JTextField();
        filtT.addKeyListener(new MyKeyListener());
        String [] filterMenu = {"EmployeeID", "FirstName", "MiddleName", "LastName", "Gender", "Birthday", "Address", "PhoneNumber"};
        filtCB = new JComboBox(filterMenu);
        filtP.setBounds(10,290, 500, 75);
        filtP.add(filtCB);
        filtP.add(filtT);
        pane.add(filtP);
                
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setBounds(100,100,750,400);
        this.setVisible(true);
        //show();
    }
    public static void main(String[] args){
        new EmployeeView();
    }
    class MyKeyListener extends KeyAdapter {
            
        public void keyReleased(KeyEvent e)
        {
           //JOptionPane.showMessageDialog(null, searchCustomerT.getText());
            myEmployeeView();
        }
    }
    
    private void myEmployeeView(){
       String searchkey = filtT.getText();
       String querySearch="";
       if(searchkey.equals("")){
           querySearch="SELECT Employee_ID as EmployeeID, Employee_Fname as FirstName, Employee_Mname as MiddleName, Employee_Lname as LastName, tblgender.GenderName as  Gender, DateOfBirth as Birthday, Employee_Address as Address, Employee_Phone as PhoneNumber "
                   + "FROM tblemployee INNER JOIN tblgender ON tblemployee.Gender_ID = tblgender.Gender_ID "
                   + "ORDER BY Employee_ID";
       }
       else if (filtCB.getSelectedItem().equals("EmployeeID")){
           querySearch="SELECT Employee_ID as EmployeeID, Employee_Fname as FirstName, Employee_Mname as MiddleName, Employee_Lname as LastName, tblgender.GenderName as  Gender, DateOfBirth as Birthday, Employee_Address as Address, Employee_Phone as PhoneNumber "
                   + "FROM tblemployee INNER JOIN tblgender ON tblemployee.Gender_ID = tblgender.Gender_ID "
                   + "WHERE Employee_ID ="+searchkey;            
       }
       else if (filtCB.getSelectedItem().equals("FirstName")){
           querySearch="SELECT Employee_ID as EmployeeID, Employee_Fname as FirstName, Employee_Mname as MiddleName, Employee_Lname as LastName, tblgender.GenderName as  Gender, DateOfBirth as Birthday, Employee_Address as Address, Employee_Phone as PhoneNumber "
                   + "FROM tblemployee INNER JOIN tblgender ON tblemployee.Gender_ID = tblgender.Gender_ID "
                   +   "WHERE Employee_Fname LIKE '%"+searchkey+"%'";              
       }
       else if (filtCB.getSelectedItem().equals("MiddleName")){
           querySearch="SELECT Employee_ID as EmployeeID, Employee_Fname as FirstName, Employee_Mname as MiddleName, Employee_Lname as LastName, tblgender.GenderName as  Gender, DateOfBirth as Birthday, Employee_Address as Address, Employee_Phone as PhoneNumber "
                   + "FROM tblemployee INNER JOIN tblgender ON tblemployee.Gender_ID = tblgender.Gender_ID "
                   +   "WHERE Employee_Mname LIKE '%"+searchkey+"%'";              
       }
       else if (filtCB.getSelectedItem().equals("LastName")){
           querySearch="SELECT Employee_ID as EmployeeID, Employee_Fname as FirstName, Employee_Mname as MiddleName, Employee_Lname as LastName, tblgender.GenderName as  Gender, DateOfBirth as Birthday, Employee_Address as Address, Employee_Phone as PhoneNumber "
                   + "FROM tblemployee INNER JOIN tblgender ON tblemployee.Gender_ID = tblgender.Gender_ID "
                   +   "WHERE Employee_Lname LIKE '%"+searchkey+"%'";              
       }
       else if (filtCB.getSelectedItem().equals("Gender")){
           querySearch="SELECT Employee_ID as EmployeeID, Employee_Fname as FirstName, Employee_Mname as MiddleName, Employee_Lname as LastName, tblgender.GenderName as  Gender, DateOfBirth as Birthday, Employee_Address as Address, Employee_Phone as PhoneNumber "
                   + "FROM tblemployee INNER JOIN tblgender ON tblemployee.Gender_ID = tblgender.Gender_ID "
                   +   "WHERE tblgender.GenderName LIKE '%"+searchkey+"%'";              
       }
       else if (filtCB.getSelectedItem().equals("Birthday")){
           querySearch="SELECT Employee_ID as EmployeeID, Employee_Fname as FirstName, Employee_Mname as MiddleName, Employee_Lname as LastName, tblgender.GenderName as  Gender, DateOfBirth as Birthday, Employee_Address as Address, Employee_Phone as PhoneNumber "
                   + "FROM tblemployee INNER JOIN tblgender ON tblemployee.Gender_ID = tblgender.Gender_ID "
                   +   "WHERE DateOfBirth LIKE '%"+searchkey+"%'";              
       }
       else if (filtCB.getSelectedItem().equals("Address")){
           querySearch="SELECT Employee_ID as EmployeeID, Employee_Fname as FirstName, Employee_Mname as MiddleName, Employee_Lname as LastName, tblgender.GenderName as  Gender, DateOfBirth as Birthday, Employee_Address as Address, Employee_Phone as PhoneNumber "
                   + "FROM tblemployee INNER JOIN tblgender ON tblemployee.Gender_ID = tblgender.Gender_ID "
                   +   "WHERE Employee_Address LIKE '%"+searchkey+"%'";              
       }
       else if (filtCB.getSelectedItem().equals("PhoneNumber")){
           querySearch="SELECT Employee_ID as EmployeeID, Employee_Fname as FirstName, Employee_Mname as MiddleName, Employee_Lname as LastName, tblgender.GenderName as  Gender, DateOfBirth as Birthday, Employee_Address as Address, Employee_Phone as PhoneNumber "
                   + "FROM tblemployee INNER JOIN tblgender ON tblemployee.Gender_ID = tblgender.Gender_ID "
                   +   "WHERE Employee_Phone LIKE '%"+searchkey+"%'";              
       }
        //JOptionPane.showMessageDialog(null, query);
        tools.tableLoadData(querySearch);
        model.fireTableDataChanged();
    }
}
