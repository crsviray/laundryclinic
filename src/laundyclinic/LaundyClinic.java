/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laundyclinic;

import java.awt.event.*;
import javax.swing.*;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;

public class LaundyClinic extends JFrame {
    JDesktopPane desktop;
    public LaundyClinic(){
        setDefaultLookAndFeelDecorated(true);
        setTitle("Ramirez's Laundry Clinic Inventory Management");
        addWindowListener(new MyWindowListener());
        desktop = new JDesktopPane();
        setContentPane(desktop);
        
        setJMenuBar(createMenu());
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private JMenuBar createMenu(){
        JMenuBar menuBar=new JMenuBar();
        
        JMenu file1 = new JMenu("Administrative Tools");
        JMenuItem file11 = new JMenuItem("Services");
        file11.addActionListener(new MenuItemListener());
        JMenuItem file12 = new JMenuItem("Employee");
        file12.addActionListener(new MenuItemListener());
        JMenuItem file13 = new JMenuItem("Transaction");
        file13.addActionListener(new MenuItemListener());
        file1.add(file11);
        file1.add(file12);
        file1.add(file13);
        
        JMenu file2 = new JMenu("View");
        JMenuItem file21 = new JMenuItem("Current Services");
        file21.addActionListener(new MenuItemListener());
        JMenuItem file22 = new JMenuItem("Current Employees");
        file22.addActionListener(new MenuItemListener());
        JMenuItem file23 = new JMenuItem("Current Transactions");
        file23.addActionListener(new MenuItemListener());
        file2.add(file21);
        file2.add(file22);
        file2.add(file23);
        
        menuBar.add(file1);
        menuBar.add(file2);
        return menuBar;
    }
     public static void main(String[] args){
        new LaundyClinic();
    }
    class MenuItemListener implements ActionListener {
      public void actionPerformed(ActionEvent e){ 
         if(e.getActionCommand().equals("Services")){
             laundyclinic.ServiceOfferedProgram category = new laundyclinic.ServiceOfferedProgram();
             desktop.add(category);
         }
         
         if(e.getActionCommand().equals("Employee")){
            laundyclinic.EmployeeProgram employee = new laundyclinic.EmployeeProgram();
             desktop.add(employee);
         }
         
          if(e.getActionCommand().equals("Transaction")){
             laundyclinic.TransactionProgram product = new laundyclinic.TransactionProgram();
             desktop.add(product);
         }
            
         if(e.getActionCommand().equals("Current Services")){
             laundyclinic.ServiceOfferedView cview = new laundyclinic.ServiceOfferedView();
             desktop.add(cview);
         }
                  
         if(e.getActionCommand().equals("Current Employees")){
             laundyclinic.EmployeeView eview = new laundyclinic.EmployeeView();
             desktop.add(eview);
         }
         else if(e.getActionCommand().equals("Current Transactions")){
             laundyclinic.TransactionView sview = new laundyclinic.TransactionView();
             desktop.add(sview);
         }
      }    
    }
    class MyWindowListener extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            System.exit(0);
        }
    }
}
