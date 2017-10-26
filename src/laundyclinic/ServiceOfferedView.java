package laundyclinic;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class ServiceOfferedView extends JInternalFrame{
    MyTools tools = new MyTools();
    JTable table;
    DefaultTableModel model;
    JTextField controlT,prodnoT, proddescT, prodwspT, prodrpT, prodoqT, filtT;
    JButton addB, clearB, deleteB, updateB;
    JComboBox statusCB, filtCB;
    JDateChooser dateOfRegDC;
    ServiceOffered product = new ServiceOffered();
    
    public ServiceOfferedView(){
        super("Services",false,true,true,true);
        Container pane = getContentPane();
        setLayout(null);

        JPanel panelTable = new JPanel(new GridLayout(1,1));
        panelTable.setBorder(BorderFactory.createTitledBorder("Services"));
        
        table = new JTable();
        String query="SELECT Category_ID as CategoryID, Category_Name as Name, Category_Description as Description FROM tblcategory ORDER BY Category_ID";
        tools.tableLoadData(query);
        model = new DefaultTableModel(tools.getData(),tools.getColumnNames());
        table.setModel(model);
        
        //JScrollPane mScrollPane = new JScrollPane(table);
        JScrollPane mScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(2).setPreferredWidth(300); //width of the column name
        
        panelTable.add(mScrollPane);
        panelTable.setBounds(10,10, 475, 275);
        pane.add(panelTable);
        //----------------------------------------------------------------------
        JPanel filtP = new JPanel(new GridLayout(1,2));
        filtT = new JTextField();
        filtT.addKeyListener(new MyKeyListener());
        String [] filterMenu = {"ServiceID", "Name", "Description"};
        filtCB = new JComboBox(filterMenu);
        filtP.setBounds(10,290, 500, 75);
        filtP.add(filtCB);
        filtP.add(filtT);
        pane.add(filtP);
                
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setBounds(100,100,525,400);
        this.setVisible(true);
        //show();
    }
    public static void main(String[] args){
        new ServiceOfferedView();
    }
    class MyKeyListener extends KeyAdapter {
            
        public void keyReleased(KeyEvent e)
        {
           //JOptionPane.showMessageDialog(null, searchCustomerT.getText());
            myCategoryView();
        }
    }
    
    private void myCategoryView(){
       String searchkey = filtT.getText();
       String querySearch="";
       if(searchkey.equals("")){
           querySearch="SELECT Category_ID as CategoryID, Category_Name as Name, Category_Description as Description "
                   + "FROM tblcategory "
                   + "ORDER BY Category_ID";
       }
       else if (filtCB.getSelectedItem().equals("CategoryID")){
           querySearch="SELECT Category_ID as CategoryID, Category_Name as Name, Category_Description as Description "
                   + "FROM tblcategory "
                   + "WHERE Category_ID ="+searchkey;            
       }
       else if (filtCB.getSelectedItem().equals("Name")){
           querySearch="SELECT Category_ID as CategoryID, Category_Name as Name, Category_Description as Description "
                   + "FROM tblcategory "
                   +   "WHERE Category_Name LIKE '%"+searchkey+"%'";              
       }
       else if (filtCB.getSelectedItem().equals("Description")){
           querySearch="SELECT Category_ID as CategoryID, Category_Name as Name, Category_Description as Description "
                   + "FROM tblcategory "
                   +   "WHERE Category_Description LIKE '%"+searchkey+"%'";              
       }
       
        //JOptionPane.showMessageDialog(null, query);
        tools.tableLoadData(querySearch);
        model.fireTableDataChanged();
    }
}

