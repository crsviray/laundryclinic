package laundyclinic;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class TransactionView extends JInternalFrame{
    MyTools tools = new MyTools();
    JTable table;
    DefaultTableModel model;
    JTextField controlT,prodnoT, proddescT, prodwspT, prodrpT, prodoqT, filtT;
    JButton addB, clearB, deleteB, updateB;
    JComboBox statusCB, filtCB;
    JDateChooser dateOfRegDC;
    Transaction product = new Transaction();
    
    public TransactionView(){
        super("Transactions",false,true,true,true);
        Container pane = getContentPane();
        setLayout(null);

        JPanel panelTable = new JPanel(new GridLayout(1,1));
        panelTable.setBorder(BorderFactory.createTitledBorder("Transactions"));
        
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
        panelTable.add(mScrollPane);
        panelTable.setBounds(10,10, 500, 275);
        pane.add(panelTable);
        //----------------------------------------------------------------------
        JPanel filtP = new JPanel(new GridLayout(1,2));
        filtT = new JTextField();
        filtT.addKeyListener(new MyKeyListener());
        String [] filterMenu = {"TransactionID", "EmployeeName", "Date"};
        filtCB = new JComboBox(filterMenu);
        filtP.setBounds(10,290, 500, 75);
        filtP.add(filtCB);
        filtP.add(filtT);
        pane.add(filtP);
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setBounds(100,100,550,400);
        this.setVisible(true);
        //show();
    }
    public static void main(String[] args){
        new TransactionView();
    }
    class MyKeyListener extends KeyAdapter {
            
        public void keyReleased(KeyEvent e)
        {
           //JOptionPane.showMessageDialog(null, searchCustomerT.getText());
            myTransactionView();
        }
    }
    
    private void myTransactionView(){
       String searchkey = filtT.getText();
       String querySearch="";
       if(searchkey.equals("")){
           querySearch="SELECT Sale_ID as TransactionID, (SELECT CONCAT(Employee_Fname, ' ' , Employee_Lname)) as EmployeeName, Date as Date "
                   +   "INNER JOIN tblemployee ON tblsale.Employee_ID = tblemployee.Employee_ID  "
                   +   "ORDER BY Sale_ID ";
       }
       else if (filtCB.getSelectedItem().equals("TransactionID")){
           querySearch="SELECT Sale_ID as SaleID, (SELECT CONCAT(Employee_Fname, ' ' , Employee_Lname)) as EmployeeName, Date as Date "
                   +   "INNER JOIN tblemployee ON tblsale.Employee_ID = tblemployee.Employee_ID  "
                   +   "WHERE Sale_ID = "+searchkey;              
       }
      
       else if (filtCB.getSelectedItem().equals("EmployeeName")){
           querySearch="SELECT Sale_ID as SaleID, (SELECT CONCAT(Employee_Fname, ' ' , Employee_Lname)) as EmployeeName, Date as Date "
                   +   "INNER JOIN tblemployee ON tblsale.Employee_ID = tblemployee.Employee_ID  "
                   +   "WHERE (SELECT CONCAT(Employee_Fname, ' ' , Employee_Lname)) LIKE '%"+searchkey+"%'";              
       }
       else if (filtCB.getSelectedItem().equals("Date")){
           querySearch="SELECT Sale_ID as SaleID, (SELECT CONCAT(Employee_Fname, ' ' , Employee_Lname)) as EmployeeName, Date as Date "
                   +   "INNER JOIN tblemployee ON tblsale.Employee_ID = tblemployee.Employee_ID  "
                   +   "WHERE Date LIKE '%"+searchkey+"%'";     
       }
       
        //JOptionPane.showMessageDialog(null, query);
        tools.tableLoadData(querySearch);
        model.fireTableDataChanged();
    }
}
