
package laundyclinic;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class ServiceOfferedProgram extends JInternalFrame{
    MyTools tools = new MyTools();
    JTable table;
    DefaultTableModel model;
    JTextField catidT, catnT, catdescT;
    JButton addB, clearB, deleteB, updateB;
    JComboBox categoryCB;
    JDateChooser dateOfRegDC;
    ServiceOffered category = new ServiceOffered();
    
    public ServiceOfferedProgram(){
        super("ServiceOffered Program", false,true,true,true);
        Container pane = getContentPane();
        setLayout(null);
        
        //----------------------------------------------------------------------
        //Input Components Designs   
        JPanel inputPanel = new JPanel(new GridLayout(3,2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Service Information"));

        JLabel catidL = new JLabel("Service ID : ", SwingConstants.RIGHT);
        catidT = new JTextField(15);
        catidT.setEditable(true);
        JLabel catnL = new JLabel("Service Name :", SwingConstants.RIGHT);
        catnT = new JTextField(15);
        JLabel catdescL = new JLabel("Service Description :",SwingConstants.RIGHT);
        catdescT = new JTextField(15);
        inputPanel.add(catidL);
        inputPanel.add(catidT);
        inputPanel.add(catnL);
        inputPanel.add(catnT);
        inputPanel.add(catdescL);
        inputPanel.add(catdescT);
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
        panelTable.setBorder(BorderFactory.createTitledBorder("List of Service Offered"));
        
        table = new JTable();
        String query="SELECT Category_ID as ServiceID, Category_Name as Name, Category_Description as Description FROM tblcategory ORDER BY Category_ID";
        tools.tableLoadData(query);
        model = new DefaultTableModel(tools.getData(),tools.getColumnNames());
        table.setModel(model);
        
        //JScrollPane mScrollPane = new JScrollPane(table);
        JScrollPane mScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(2).setPreferredWidth(300); //width of the column name
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
        new ServiceOfferedProgram();
    }
    class MyButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            
            if(e.getSource()==clearB){
                int reply=JOptionPane.showConfirmDialog(null, 
                        "Are you sure to clear the data?",
                        "Category Module",JOptionPane.YES_NO_OPTION);
                if(reply==JOptionPane.YES_OPTION){
                    catnT.setText("");
                    catdescT.setText("");
                }
            }//clear
            else if(e.getSource()==addB){
                if(!catnT.getText().equals("")){
                    int reply=JOptionPane.showConfirmDialog(null, 
                        "Are you sure to add the new Category?",
                        "Category Module",JOptionPane.YES_NO_OPTION);
                    if(reply==JOptionPane.YES_OPTION){                                       
                        String query = "INSERT INTO tblcategory(Category_Name, Category_Description) VALUES('" + catnT.getText()
                                +"','" + catdescT.getText() + "')";
                        category.addCategory(query);
                        myRefresh();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Adding new Category was cancelled");
                    }  
                }// if description
            }
            else if(e.getSource()==updateB){
                int row = table.getSelectedRow();
                String id =table.getModel().getValueAt(row, 1).toString(); 
                if(!id.equals("")){
                    int reply=JOptionPane.showConfirmDialog(null, 
                        "Are you sure to update the Category Information?",
                        "Category Module",JOptionPane.YES_NO_OPTION);
                    if(reply==JOptionPane.YES_OPTION){
                        String query = "UPDATE tblcategory SET Category_Name = '"+catnT.getText()
                                +"', Category_Description = '"+catdescT.getText()
                                +"' WHERE Category_ID = " +catidT.getText();
                        category.updateCategory(query);
                        myRefresh();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Updating the Category Information was cancelled");
                    }  
                }// if description
            }
            else if(e.getSource()==deleteB){               
                int row = table.getSelectedRow();
                String id =table.getModel().getValueAt(row, 0).toString(); 
                if(!id.equals("")){
                    int reply=JOptionPane.showConfirmDialog(null, 
                        "Are you sure to delete the Category Information?",
                        "Category Module",JOptionPane.YES_NO_OPTION);
                    if(reply==JOptionPane.YES_OPTION){
                        String query = "DELETE FROM tblcategory WHERE Category_ID=" + id;
                        category.deleteCategory(query);
                        myRefresh();
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Updating the Service Information was cancelled");
                    }  
                }// if description
            }
        }    
    }
    private void myRefresh(){
        String query="SELECT Category_ID as CategoryID, Category_Name as Name, Category_Description as Description FROM tblcategory ORDER BY Category_ID"; 
        tools.tableLoadData(query);
        model.fireTableDataChanged();
    }
    
    public class MyMouseListener extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            int row = table.getSelectedRow();
            catidT.setText(table.getModel().getValueAt(row, 0).toString());
            catnT.setText(table.getModel().getValueAt(row, 1).toString());
            catdescT.setText(table.getModel().getValueAt(row, 2).toString());
        }
    }
}
