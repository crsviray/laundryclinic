
package laundyclinic;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class EmployeeProgram extends JInternalFrame{
    MyTools tools = new MyTools();
    JTable table;
    DefaultTableModel model;
    JTextField empidT, empfnT, empmnT, emplnT,empaT, emppT;
    JComboBox genderCB;
    JButton addB, clearB, deleteB, updateB;
    JDateChooser dateOfBirthDC;
    Employee employee = new Employee();
    
    public EmployeeProgram(){
        super("Employee Program", false,true,true,true);
        Container pane = getContentPane();
        setLayout(null);
        
        //----------------------------------------------------------------------
        //Input Components Designs   
        JPanel inputPanel = new JPanel(new GridLayout(8,2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Employee Information"));

        JLabel empidL = new JLabel("Employee ID :",SwingConstants.RIGHT);
        empidT = new JTextField(15);
        empidT.setEditable(false);
        JLabel empfnL = new JLabel("Employee First Name : ", SwingConstants.RIGHT);
        empfnT = new JTextField(15);
        JLabel empmnL = new JLabel("Employee Middle Name :", SwingConstants.RIGHT);
        empmnT = new JTextField(15);
        JLabel emplnL = new JLabel("Employee Last Name :",SwingConstants.RIGHT);
        emplnT = new JTextField(15);
        JLabel empgL = new JLabel("Gender : ", SwingConstants.RIGHT);
        genderCB = new JComboBox();
        tools.loadComboData(genderCB,"Select Gender_ID,GenderName from tblgender","Gender_ID","GenderName");
        genderCB.updateUI();
        JLabel dateOfBirthL = new JLabel("Birthday : ", SwingConstants.RIGHT);
        dateOfBirthDC = new JDateChooser("yyyy-MM-dd","####-##-##",'_');
        JLabel empaL = new JLabel("Address :",SwingConstants.RIGHT);
        empaT = new JTextField(15);
        JLabel emppL = new JLabel("Contact Number :",SwingConstants.RIGHT);
        emppT = new JTextField(15);
        inputPanel.add(empidL);
        inputPanel.add(empidT);
        inputPanel.add(empfnL);
        inputPanel.add(empfnT);
        inputPanel.add(empmnL);
        inputPanel.add(empmnT);
        inputPanel.add(emplnL);
        inputPanel.add(emplnT);
        inputPanel.add(empgL);
        inputPanel.add(genderCB);
        inputPanel.add(dateOfBirthL);
        inputPanel.add(dateOfBirthDC);
        inputPanel.add(empaL);
        inputPanel.add(empaT);
        inputPanel.add(emppL);
        inputPanel.add(emppT);
        inputPanel.setBounds(10,10, 350, 200);        
        pane.add(inputPanel);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1,4));
        clearB = new JButton("Clear");
        clearB.addActionListener(new MyButtonHandler());
        addB = new JButton("Add");
        addB.addActionListener(new MyButtonHandler());
        updateB = new JButton("Update");
        updateB.addActionListener(new MyButtonHandler());
        deleteB = new JButton("Delete");
        deleteB.addActionListener(new MyButtonHandler());
        
        buttonPanel.add(clearB);
        buttonPanel.add(addB);
        buttonPanel.add(updateB);
        buttonPanel.add(deleteB);
        
        buttonPanel.setBounds(10,210, 350, 70);
        pane.add(buttonPanel);
        
        //----------------------------------------------------------------------
        //Table Design
        JPanel panelTable = new JPanel(new GridLayout(1,1));
        panelTable.setBorder(BorderFactory.createTitledBorder("List of Employees"));
        
        table = new JTable();
        String query="SELECT Employee_ID as EmployeeID, Employee_Fname as FirstName, Employee_Mname as MiddleName, Employee_Lname as LastName, tblgender.GenderName as  Gender, DateOfBirth as Birthday, Employee_Address as Address, Employee_Phone as PhoneNumber FROM tblemployee INNER JOIN tblgender ON tblemployee.Gender_ID = tblgender.Gender_ID ORDER BY Employee_ID";
        tools.tableLoadData(query);
        model = new DefaultTableModel(tools.getData(),tools.getColumnNames());
        table.setModel(model);
        
        //JScrollPane mScrollPane = new JScrollPane(table);
        JScrollPane mScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(6).setPreferredWidth(300); //width of the column name
        table.addMouseListener(new MyMouseListener());
        
        panelTable.add(mScrollPane);
        panelTable.setBounds(370,10, 450, 275);
        pane.add(panelTable);
        //----------------------------------------------------------------------
                
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setBounds(100,100,850,330);
        this.setVisible(true);
        //show();
    }
    class MyButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            
            if(e.getSource()==clearB){
                int reply=JOptionPane.showConfirmDialog(null, 
                        "Are you sure to clear the data?",
                        "Product Module",JOptionPane.YES_NO_OPTION);
                if(reply==JOptionPane.YES_OPTION){
                    empidT.setText("");
                    empfnT.setText("");
                    empmnT.setText("");
                    emplnT.setText("");
                    empaT.setText("");
                    emppT.setText("");
                }
            }//clear
            else if(e.getSource()==addB){
                if(!empfnT.getText().equals("")){
                    int reply=JOptionPane.showConfirmDialog(null, 
                        "Are you sure to add the new Employee?",
                        "Employee Module",JOptionPane.YES_NO_OPTION);
                    if(reply==JOptionPane.YES_OPTION){                 
                        //get the date in string format
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateofbirth = formatter.format(dateOfBirthDC.getDate());
                        
                        //get the position ID
                        Item selected_item = (Item) genderCB.getSelectedItem();
                        int genderid = selected_item.getId();
                        
                        String query = "INSERT INTO tblemployee(Employee_Fname, Employee_Mname, Employee_Lname, Gender_ID, DateOfBirth, Employee_Address, Employee_Phone) VALUES('" + empfnT.getText()
                                +"','"  + empmnT.getText() 
                                +"','" + emplnT.getText()
                                +"','" + genderid
                                +"','" + dateofbirth
                                +"','" + empaT.getText()
                                +"','" + emppT.getText() + "')";
                           
                        employee.addEmployee(query);
                        myRefresh();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Adding new Employee was cancelled");
                    }  
                }// if description
            }
            else if(e.getSource()==updateB){
                int row = table.getSelectedRow();
                String id =table.getModel().getValueAt(row, 1).toString(); 
                if(!id.equals("")){
                    int reply=JOptionPane.showConfirmDialog(null, 
                        "Are you sure to update the Employee Information?",
                        "Employee Module",JOptionPane.YES_NO_OPTION);
                    if(reply==JOptionPane.YES_OPTION){
                        //get the date in string format
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateofbirth = formatter.format(dateOfBirthDC.getDate());
                        
                        //get the position ID
                        Item selected_item = (Item) genderCB.getSelectedItem();
                        int genderid = selected_item.getId();
                        
                        String query = "UPDATE tblemployee SET Employee_Fname = '"+empfnT.getText()
                                +"', Employee_Mname = '" + empmnT.getText() 
                                +"', Employee_Lname = '"+emplnT.getText()
                                +"', Gender_ID = '"+genderid
                                +"', DateOfBirth = '"+dateofbirth
                                +"', Employee_Address = '"+empaT.getText()
                                +"', Employee_Phone = '"+emppT.getText()
                                +"' WHERE Employee_ID = " + empidT.getText();
                        employee.updateEmployee(query);
                        myRefresh();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Updating the Employee Information was cancelled");
                    }  
                }// if description
            }
            else if(e.getSource()==deleteB){               
                int row = table.getSelectedRow();
                String id =table.getModel().getValueAt(row, 0).toString(); 
                if(!id.equals("")){
                    int reply=JOptionPane.showConfirmDialog(null, 
                        "Are you sure to delete the Employee Information?",
                        "Employee Module",JOptionPane.YES_NO_OPTION);
                    if(reply==JOptionPane.YES_OPTION){
                        String query = "DELETE FROM tblemployee WHERE Employee_ID=" + id;
                        employee.deleteEmployee(query);
                        myRefresh();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Updating the Employee Information was cancelled");
                    }  
                }// if description
            }
        }    
    }
    private void myRefresh(){
        String query="SELECT Employee_ID as EmployeeID, Employee_Fname as FirstName, Employee_Mname as MiddleName, Employee_Lname as LastName, tblgender.GenderName as  Gender, DateOfBirth as Birthday, Employee_Address as Address, Employee_Phone as PhoneNumber FROM tblemployee INNER JOIN tblgender ON tblemployee.Gender_ID = tblgender.Gender_ID ORDER BY Employee_ID"; 
        tools.tableLoadData(query);
        model.fireTableDataChanged();
    }
    
    public class MyMouseListener extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            int row = table.getSelectedRow();
            empidT.setText(table.getModel().getValueAt(row, 0).toString());
            empfnT.setText(table.getModel().getValueAt(row, 1).toString());
            empmnT.setText(table.getModel().getValueAt(row, 2).toString());
            emplnT.setText(table.getModel().getValueAt(row, 3).toString());
            String gender = table.getModel().getValueAt(row, 4).toString();
            genderCB.setSelectedIndex(tools.setSelectedValue(genderCB, gender));
            genderCB.updateUI();
            String dateofbirth = table.getModel().getValueAt(row, 5).toString();
            try{
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateofbirth);
                dateOfBirthDC.setDate(date);
            }
            catch(Exception ex){
            }
            empaT.setText(table.getModel().getValueAt(row, 6).toString());
            emppT.setText(table.getModel().getValueAt(row, 7).toString());       
        }
    }
}
