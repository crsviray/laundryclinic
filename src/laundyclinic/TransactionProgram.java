package laundyclinic;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

public class TransactionProgram extends JInternalFrame{
    MyTools tools = new MyTools();
    JTable table;
    DefaultTableModel model;
    JTextField saleidT;
    JComboBox empCB, prodCB;
    JButton addB, clearB, deleteB, updateB;
    JDateChooser dateOfRegDC;
    Transaction sale = new Transaction();
    Date date = new Date();
    
    public TransactionProgram(){
        super("TransactionProgram", false,true,true,true);
        Container pane = getContentPane();
        setLayout(null);
        
        //----------------------------------------------------------------------
        //Input Components Designs   
        JPanel inputPanel = new JPanel(new GridLayout(4,2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Transaction Information"));

        JLabel saleidL = new JLabel("Transaction : ", SwingConstants.RIGHT);
        saleidT = new JTextField(15);
        saleidT.setEditable(false);
        JLabel empL = new JLabel("Employee :",SwingConstants.RIGHT);
        empCB = new JComboBox();
        tools.empComboData(empCB,"Select Employee_ID, Employee_Fname, Employee_Lname from tblemployee","Employee_ID","Employee_Fname","Employee_Lname");
        empCB.updateUI();
        JLabel dateOfRegL = new JLabel("Date: ", SwingConstants.RIGHT);
        dateOfRegDC = new JDateChooser("yyyy-MM-dd","####-##-##",'_');
        dateOfRegDC.setDate(date);
        inputPanel.add(saleidL);
        inputPanel.add(saleidT);
        inputPanel.add(empL);
        inputPanel.add(empCB);
        inputPanel.add(dateOfRegL);
        inputPanel.add(dateOfRegDC);
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
        panelTable.setBorder(BorderFactory.createTitledBorder("List of Transactions"));
        
        table = new JTable();
        String query="SELECT Sale_ID as TransactionID, (SELECT CONCAT(Employee_Fname, ' ' , Employee_Lname)) as EmployeeName, Date as Date FROM tblsale INNER JOIN tblemployee ON tblsale.Employee_ID = tblemployee.Employee_ID  ORDER BY Sale_ID ";
        tools.tableLoadData(query);
        model = new DefaultTableModel(tools.getData(),tools.getColumnNames());
        table.setModel(model);
        
        //JScrollPane mScrollPane = new JScrollPane(table);
        JScrollPane mScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(1).setPreferredWidth(100); //width of the column name
        table.getColumnModel().getColumn(2).setPreferredWidth(250); //width of the column name
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
    public static void main(String[] args){
        new TransactionProgram();
    }
    class MyButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            
            if(e.getSource()==clearB){
                int reply=JOptionPane.showConfirmDialog(null, 
                        "Are you sure to clear the data?",
                        "Transaction Module",JOptionPane.YES_NO_OPTION);
                if(reply==JOptionPane.YES_OPTION){
                    saleidT.setText("");
                }
            }//clear
            else if(e.getSource()==addB){
                if(!prodCB.getSelectedItem().equals("")){
                    int reply=JOptionPane.showConfirmDialog(null, 
                        "Are you sure to add the new transaction?",
                        "Transaction Module",JOptionPane.YES_NO_OPTION);
                    if(reply==JOptionPane.YES_OPTION){ 
                         //get the date in string format
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateofreg = formatter.format(dateOfRegDC.getDate());
                        
                        //get the position ID
                        Item selected_item = (Item) empCB.getSelectedItem();
                        int prodid = selected_item.getId();
                        
                        //get the position ID
                        Item selected_item2 = (Item) empCB.getSelectedItem();
                        int employeeid = selected_item.getId();
                        
                        String query = "INSERT INTO tblsale(Product_ID, Employee_ID, Date) VALUES('" + prodid
                                +"','"  + employeeid 
                                +"','" + dateofreg + "')";
                        sale.addTransaction(query);
                        myRefresh();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Adding new Transaction was cancelled");
                    }  
                }// if description
            }
            else if(e.getSource()==updateB){
                int row = table.getSelectedRow();
                String id =table.getModel().getValueAt(row, 1).toString(); 
                if(!id.equals("")){
                    int reply=JOptionPane.showConfirmDialog(null, 
                        "Are you sure to update the Transaction Information?",
                        "Transaction Module",JOptionPane.YES_NO_OPTION);
                    if(reply==JOptionPane.YES_OPTION){
                          //get the date in string format
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String dateofreg = formatter.format(dateOfRegDC.getDate());
                        
                        //get the position ID
                        Item selected_item = (Item) empCB.getSelectedItem();
                        int prodid = selected_item.getId();
                        
                        //get the position ID
                        Item selected_item2 = (Item) empCB.getSelectedItem();
                        int employeeid = selected_item.getId();
                        
                        String query = "UPDATE tblsale SET Product_ID = '"+prodid
                                +"', Employee_ID = '"+employeeid
                                +"', Date = '"+dateofreg
                                +"' WHERE Sale_ID = " +saleidT.getText();
                        sale.updateSale(query);
                        myRefresh();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Updating the Transaction Information was cancelled");
                    }  
                }// if description
            }
            else if(e.getSource()==deleteB){               
                int row = table.getSelectedRow();
                String id =table.getModel().getValueAt(row, 0).toString(); 
                if(!id.equals("")){
                    int reply=JOptionPane.showConfirmDialog(null, 
                        "Are you sure to delete the Transaction Information?",
                        "Transaction Module",JOptionPane.YES_NO_OPTION);
                    if(reply==JOptionPane.YES_OPTION){
                        String query = "DELETE FROM tblsale WHERE Sale_ID=" + id;
                        sale.deleteSale(query);
                        myRefresh();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Updating the Transaction Information was cancelled");
                    }  
                }// if description
            }
        }    
    }
    private void myRefresh(){
        String query="SELECT Sale_ID as SaleID, tblproduct.Product_Name as ProductName, (SELECT CONCAT(Employee_Fname, ' ' , Employee_Lname)) as EmployeeName, Date as DateSold FROM tblsale INNER JOIN tblproduct ON tblsale.Product_ID = tblproduct.Product_ID INNER JOIN tblemployee ON tblsale.Employee_ID = tblemployee.Employee_ID  ORDER BY Sale_ID "; 
        tools.tableLoadData(query);
        model.fireTableDataChanged();
    }
    
    public class MyMouseListener extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            int row = table.getSelectedRow();
            saleidT.setText(table.getModel().getValueAt(row, 0).toString());
            String prod = table.getModel().getValueAt(row, 1).toString();
            prodCB.setSelectedIndex(tools.setSelectedValue(prodCB, prod));
            prodCB.updateUI();
            String emp = table.getModel().getValueAt(row, 2).toString();
            empCB.setSelectedIndex(tools.setSelectedValue(empCB, emp));
            empCB.updateUI();
            String dateofreg = table.getModel().getValueAt(row, 3).toString();
            try{
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateofreg);
                dateOfRegDC.setDate(date);
            }
            catch(Exception ex){
            }
            
        }
    }
}
