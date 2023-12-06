/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ejaystudioforms;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.regex.PatternSyntaxException;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;




/**
 *
 * @author lala
 */
public class Forms extends javax.swing.JFrame {

    /**
     * Creates new form Forms
     */
    private static final String username = "root";
    private static final String password = "EleiseSQL123!";
    private static final String dataConn = "jdbc:mysql://localhost:3306/connector";
    
    Connection sqlConn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    int q, i, id, deleteItem;
    
    public Forms() {
        initComponents();
        updateDB();
        updateFormsDB();
        
        mainDB.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    selectedRowIndex = mainDB.getSelectedRow();

                    // Populate the input fields with data from the selected row
                    if (selectedRowIndex >= 0) {
                        idtxtField.setText(mainDB.getValueAt(selectedRowIndex, 0).toString());
                        nametxtField.setText(mainDB.getValueAt(selectedRowIndex, 1).toString());
                        agetxtField.setText(mainDB.getValueAt(selectedRowIndex, 2).toString());
                        contacttxtField.setText(mainDB.getValueAt(selectedRowIndex, 3).toString());
                        coachtxtField.setText(mainDB.getValueAt(selectedRowIndex, 4).toString());
                        coursetxtField.setText(mainDB.getValueAt(selectedRowIndex, 5).toString());
                        datetxtField.setText(mainDB.getValueAt(selectedRowIndex, 6).toString());
                    }
                }
            }
        });
    }
    
    private int selectedRowIndex = -1;


    
    
    /////////////////FUNCTIONS////////////////////////////
    
    public void updateDB()
    {
        try
        {
               Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
               sqlConn = DriverManager.getConnection(dataConn, username, password);
               pst = sqlConn.prepareStatement("select * from connector.mainEJ");
               
               rs = pst.executeQuery();
               ResultSetMetaData stData = rs.getMetaData();
               
               q = stData.getColumnCount();
                
               DefaultTableModel RecordTable = (DefaultTableModel)mainDB.getModel();
               RecordTable.setRowCount(0);
               
               while(rs.next())
               {
                   Vector columnData = new Vector();
                   
                   for(i = 1; i <= q; i++)
                   {
                       columnData.add(rs.getString("ID"));
                       columnData.add(rs.getString("Name"));
                       columnData.add(rs.getString("Age"));
                       columnData.add(rs.getString("Contact"));
                       columnData.add(rs.getString("Coach"));
                       columnData.add(rs.getString("Course"));
                       columnData.add(rs.getString("Date"));
                 

                   }
                   
                   RecordTable.addRow(columnData);
               }
               
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    
    private void insertData(int id, String name, int age, String contact, String coach, String course, String date) {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        sqlConn = DriverManager.getConnection(dataConn, username, password);
        pst = sqlConn.prepareStatement("insert into connector.mainEJ(ID, Name, Age, Contact, Coach, Course, Date)values(?,?,?,?,?,?,?)");
        
        pst.setInt(1, id);
        pst.setString(2, name);
        pst.setInt(3, age);
        pst.setString(4, contact);
        pst.setString(5, coach);
        pst.setString(6, course);
        pst.setString(7, date);

        pst.executeUpdate();
        updateDB(); // Refresh the table after inserting the data
        
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, ex);
    } finally {
        try {
            if (pst != null) {
                pst.close();
            }
            if (sqlConn != null) {
                sqlConn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    
    
    private void clearTable() {
    DefaultTableModel model = (DefaultTableModel) mainDB.getModel();
    model.setRowCount(0); // Clear the table
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        sqlConn = DriverManager.getConnection(dataConn, username, password);
        PreparedStatement clearStatement = sqlConn.prepareStatement("DELETE FROM connector.mainEJ");
        clearStatement.executeUpdate();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, ex);
    } finally {
        try {
            if (sqlConn != null) {
                sqlConn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
// Declare a class variable to store the selected row index




// Add an updateData method similar to your insertData method
private void updateData(int id, String name, int age, String contact, String coach, String course, String date) {
    try {
        // Similar to your insertData method but with an UPDATE statement
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        sqlConn = DriverManager.getConnection(dataConn, username, password);
        pst = sqlConn.prepareStatement("UPDATE connector.mainEJ SET Name=?, Age=?, Contact=?, Coach=?, Course=? , Date=? WHERE ID=?");
        
      
        
        pst.setString(1, name);
        pst.setInt(2, age);
        pst.setString(3, contact);
        pst.setString(4, coach);
        pst.setString(5, course);
        pst.setString(6, date);
        pst.setInt(7, id);
     
        

        pst.executeUpdate();
        updateDB(); // Refresh the table after updating the data

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, ex);
    } finally {
        try {
            if (pst != null) {
                pst.close();
            }
            if (sqlConn != null) {
                sqlConn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



////////////////////////FORMS FUNCTIONS////////////////////////////

public void updateFormsDB()
    {
        try
        {
               Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
               sqlConn = DriverManager.getConnection(dataConn, username, password);
               pst = sqlConn.prepareStatement("select * from connector.studentsForm");
               
               rs = pst.executeQuery();
               ResultSetMetaData stData = rs.getMetaData();
               
               q = stData.getColumnCount();
                
               DefaultTableModel RecordTable = (DefaultTableModel)formTable.getModel();
               RecordTable.setRowCount(0);
               
               while(rs.next())
               {
                   Vector columnData = new Vector();
                   
                   for(i = 1; i <= q; i++)
                   {
                       columnData.add(rs.getString("LessonNum"));
                       columnData.add(rs.getString("DateOfLesson"));
                       columnData.add(rs.getString("Attendance"));
                       columnData.add(rs.getString("Payment"));
                 

                   }
                   
                   RecordTable.addRow(columnData);
               }
               
        }
        catch (Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }



private void filterTableByColumn() {
        
        DefaultTableModel model = (DefaultTableModel) formTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        formTable.setRowSorter(sorter);

        List<RowFilter<Object, Object>> filters = new ArrayList<>();
        int columnIndex = 0; // Initialize with an invalid value
        String stringID = mainDB.getValueAt(selectedRowIndex, 0).toString();
        String name = mainDB.getValueAt(selectedRowIndex, 1).toString();
        String age = mainDB.getValueAt(selectedRowIndex, 2).toString();
        String contact = mainDB.getValueAt(selectedRowIndex, 3).toString();
        String coach = mainDB.getValueAt(selectedRowIndex, 4).toString();
        String course = mainDB.getValueAt(selectedRowIndex, 5).toString();
        String date = mainDB.getValueAt(selectedRowIndex, 6).toString();

        

        if (columnIndex != -1) {
            try {
                filters.add(RowFilter.regexFilter(stringID, columnIndex));
               
                contactFormtxt.setText(contact);
      
                courseFormtxt.setText(course);
                nameFormtxt.setText(name);
          
                dateFormtxt.setText(date);
                

            } catch (PatternSyntaxException ex) {
                return;
            }

            RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
            sorter.setRowFilter(combinedFilter);
        }

}





////////////////////////FORMS FUNCTIONS////////////////////////////

    
    /////////////////FUNCTIONS////////////////////////////

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addForm = new javax.swing.JFrame();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        contacttxtField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        idtxtField = new javax.swing.JTextField();
        nametxtField = new javax.swing.JTextField();
        agetxtField = new javax.swing.JTextField();
        coachtxtField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        datetxtField = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        coursetxtField = new javax.swing.JTextField();
        addBtn = new javax.swing.JButton();
        cancelbtn = new javax.swing.JButton();
        editFrame = new javax.swing.JFrame();
        editnametxtField = new javax.swing.JTextField();
        editagetxtField = new javax.swing.JTextField();
        editcontacttxtField = new javax.swing.JTextField();
        editcoachtxtField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        editbtn = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        editcoursetxtField = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        editdatetxtField = new javax.swing.JTextField();
        studentForm = new javax.swing.JFrame();
        formName = new javax.swing.JLabel();
        formstudentID = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        formTable = new javax.swing.JTable();
        dateFormtxt = new javax.swing.JLabel();
        nameFormtxt = new javax.swing.JLabel();
        formName1 = new javax.swing.JLabel();
        formName2 = new javax.swing.JLabel();
        courseFormtxt = new javax.swing.JLabel();
        contactFormtxt = new javax.swing.JLabel();
        formInsertBtn = new javax.swing.JToggleButton();
        formInsertBtn1 = new javax.swing.JToggleButton();
        formInsertBtn2 = new javax.swing.JToggleButton();
        AdminLogIn = new javax.swing.JFrame();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        panel1 = new java.awt.Panel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        mainDB = new javax.swing.JTable();
        Delete = new javax.swing.JPanel();
        insertBtn = new javax.swing.JButton();
        clearBtn = new javax.swing.JButton();
        maineditbtn = new javax.swing.JButton();
        formbtn = new javax.swing.JButton();
        deletebtn = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();

        jLabel1.setText("Add New Student");

        contacttxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contacttxtFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("Name:");

        jLabel3.setText("ID:");

        jLabel4.setText("Age:");

        jLabel5.setText("Coach:");

        nametxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nametxtFieldActionPerformed(evt);
            }
        });

        coachtxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coachtxtFieldActionPerformed(evt);
            }
        });

        jLabel6.setText("Contact:");

        jLabel16.setText("Date:");

        datetxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datetxtFieldActionPerformed(evt);
            }
        });

        jLabel17.setText("Course:");

        coursetxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coursetxtFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(contacttxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(agetxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nametxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(58, 58, 58)
                                .addComponent(idtxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(coursetxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addComponent(coachtxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(datetxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(idtxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(nametxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(agetxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(contacttxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(coachtxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(datetxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(coursetxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        addBtn.setText("Add");
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        cancelbtn.setText("Cancel");
        cancelbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout addFormLayout = new javax.swing.GroupLayout(addForm.getContentPane());
        addForm.getContentPane().setLayout(addFormLayout);
        addFormLayout.setHorizontalGroup(
            addFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addFormLayout.createSequentialGroup()
                .addGroup(addFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addFormLayout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel1))
                    .addGroup(addFormLayout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(addBtn)
                        .addGap(18, 18, 18)
                        .addComponent(cancelbtn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addFormLayout.createSequentialGroup()
                .addGap(0, 54, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
        addFormLayout.setVerticalGroup(
            addFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addFormLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(addFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBtn)
                    .addComponent(cancelbtn))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        editnametxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editnametxtFieldActionPerformed(evt);
            }
        });

        editagetxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editagetxtFieldActionPerformed(evt);
            }
        });

        editcoachtxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editcoachtxtFieldActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel7.setText("EDIT");

        jLabel8.setText("Name:");

        jLabel9.setText("Age:");

        jLabel10.setText("Contact:");

        jLabel11.setText("Coach:");

        editbtn.setText("SAVE");
        editbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editbtnActionPerformed(evt);
            }
        });

        jLabel18.setText("Course:");

        editcoursetxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editcoursetxtFieldActionPerformed(evt);
            }
        });

        jLabel19.setText("Date:");

        editdatetxtField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editdatetxtFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout editFrameLayout = new javax.swing.GroupLayout(editFrame.getContentPane());
        editFrame.getContentPane().setLayout(editFrameLayout);
        editFrameLayout.setHorizontalGroup(
            editFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editFrameLayout.createSequentialGroup()
                .addGap(171, 171, 171)
                .addComponent(jLabel7)
                .addContainerGap(177, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editFrameLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(editFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editFrameLayout.createSequentialGroup()
                        .addComponent(editbtn)
                        .addGap(162, 162, 162))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, editFrameLayout.createSequentialGroup()
                        .addGroup(editFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(editFrameLayout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(editdatetxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(editFrameLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(editcoursetxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(editFrameLayout.createSequentialGroup()
                                .addGroup(editFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(18, 18, 18)
                                .addGroup(editFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(editcontacttxtField)
                                    .addComponent(editagetxtField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(editnametxtField, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(editcoachtxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(59, 59, 59))))
        );
        editFrameLayout.setVerticalGroup(
            editFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editFrameLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editnametxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editagetxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editcontacttxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editcoachtxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(editcoursetxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(editdatetxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(editbtn)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        formName.setText("NAME:");

        formstudentID.setText("DATE ENROLLED:");

        formTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "LESSON #", "DATE OF LESSON", "ATTENDANCE", "PAYMENT"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(formTable);

        dateFormtxt.setText("date");

        nameFormtxt.setText("name");

        formName1.setText("CONTACT #:");

        formName2.setText("COURSE & PACKAGE AVAILED:");

        courseFormtxt.setText("package");

        contactFormtxt.setText("package");

        formInsertBtn.setText("Insert");
        formInsertBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formInsertBtnActionPerformed(evt);
            }
        });

        formInsertBtn1.setText("Edit");
        formInsertBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formInsertBtn1ActionPerformed(evt);
            }
        });

        formInsertBtn2.setText("Delete");
        formInsertBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formInsertBtn2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout studentFormLayout = new javax.swing.GroupLayout(studentForm.getContentPane());
        studentForm.getContentPane().setLayout(studentFormLayout);
        studentFormLayout.setHorizontalGroup(
            studentFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentFormLayout.createSequentialGroup()
                .addGroup(studentFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(studentFormLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 815, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                        .addGroup(studentFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(formInsertBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(formInsertBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(formInsertBtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addGroup(studentFormLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(studentFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(formName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(formstudentID, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(studentFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dateFormtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nameFormtxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(studentFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(formName2)
                            .addComponent(formName1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(studentFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(contactFormtxt, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                            .addComponent(courseFormtxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        studentFormLayout.setVerticalGroup(
            studentFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentFormLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(studentFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nameFormtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(formName2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(courseFormtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(studentFormLayout.createSequentialGroup()
                        .addComponent(formName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(studentFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(formstudentID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateFormtxt)
                            .addComponent(formName1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactFormtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(studentFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(studentFormLayout.createSequentialGroup()
                        .addComponent(formInsertBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(formInsertBtn1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(formInsertBtn2)))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        AdminLogIn.setBackground(new java.awt.Color(51, 51, 51));

        jLabel13.setText("Admin Log-In");

        jLabel14.setText("username:");

        jLabel15.setText("passwords:");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton1.setText("Sign-In");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AdminLogInLayout = new javax.swing.GroupLayout(AdminLogIn.getContentPane());
        AdminLogIn.getContentPane().setLayout(AdminLogInLayout);
        AdminLogInLayout.setHorizontalGroup(
            AdminLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLogInLayout.createSequentialGroup()
                .addGroup(AdminLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AdminLogInLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(AdminLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(AdminLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(AdminLogInLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel13))))
                    .addGroup(AdminLogInLayout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jButton1)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        AdminLogInLayout.setVerticalGroup(
            AdminLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdminLogInLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel13)
                .addGap(47, 47, 47)
                .addGroup(AdminLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(AdminLogInLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(jButton1)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        panel1.setBackground(new java.awt.Color(102, 102, 102));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.GreyInline"));

        jPanel2.setBackground(java.awt.Color.gray);

        mainDB.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        mainDB.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Age", "Contact No.", "Coach", "Course", "Date Enrolled"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        mainDB.setEnabled(false);
        mainDB.setFocusTraversalKeysEnabled(false);
        mainDB.setFocusable(false);
        mainDB.setGridColor(new java.awt.Color(51, 0, 51));
        mainDB.setMixingCutoutShape(null);
        mainDB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mainDB.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        mainDB.setShowGrid(true);
        jScrollPane2.setViewportView(mainDB);
        mainDB.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (mainDB.getColumnModel().getColumnCount() > 0) {
            mainDB.getColumnModel().getColumn(0).setResizable(false);
            mainDB.getColumnModel().getColumn(1).setResizable(false);
            mainDB.getColumnModel().getColumn(2).setResizable(false);
            mainDB.getColumnModel().getColumn(3).setResizable(false);
            mainDB.getColumnModel().getColumn(4).setResizable(false);
            mainDB.getColumnModel().getColumn(5).setResizable(false);
            mainDB.getColumnModel().getColumn(6).setResizable(false);
        }

        insertBtn.setText("Insert");
        insertBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertBtnActionPerformed(evt);
            }
        });

        clearBtn.setText("Clear");
        clearBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBtnActionPerformed(evt);
            }
        });

        maineditbtn.setText("Edit");
        maineditbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maineditbtnActionPerformed(evt);
            }
        });

        formbtn.setText("Forms");
        formbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                formbtnActionPerformed(evt);
            }
        });

        deletebtn.setText("Delete");
        deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DeleteLayout = new javax.swing.GroupLayout(Delete);
        Delete.setLayout(DeleteLayout);
        DeleteLayout.setHorizontalGroup(
            DeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeleteLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(DeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(deletebtn)
                    .addGroup(DeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(formbtn)
                        .addComponent(maineditbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(clearBtn)
                        .addComponent(insertBtn)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        DeleteLayout.setVerticalGroup(
            DeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DeleteLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(insertBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(maineditbtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(formbtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deletebtn)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Helvetica Neue", 0, 36)); // NOI18N
        jLabel12.setText("STUDENT MANAGEMENT");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                        .addGap(50, 50, 50)
                        .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jLabel12)))
                .addGap(73, 73, 73))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Delete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void insertBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertBtnActionPerformed
        // TODO add your handling code here:
        /*
        String value = insertField.getText(); // Get the value from the insertField
        insertData(value); // Insert the value into the database
        insertField.setText(""); // Clear the insertField after insertion

        // Refresh the table after inserting the data
        updateDB();

        JOptionPane.showMessageDialog(this, "Database updated successfully!");
        */
        idtxtField.setText("");
        nametxtField.setText("");
        agetxtField.setText("");
        contacttxtField.setText("");
        coachtxtField.setText("");
        coursetxtField.setText("");
        datetxtField.setText("");
        
        addForm.setSize(400, 400); 
        addForm.setVisible(!addForm.isVisible());
     

    }//GEN-LAST:event_insertBtnActionPerformed

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        // TODO add your handling code here:
        clearTable();
    }//GEN-LAST:event_clearBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        // TODO add your handling code here:
        String idStr = idtxtField.getText(); // Get the value from the insertField
        String name = nametxtField.getText();
        String age = agetxtField.getText();
        String contact = contacttxtField.getText();
        String coach = coachtxtField.getText();
        String course = coursetxtField.getText();
        String date = datetxtField.getText();
       
        
         try {
        // Parse ID as an integer
        int id = Integer.parseInt(idStr);

        // Insert the data into the database
        insertData(id , name, Integer.parseInt(age), (contact), coach, (course), date);

        // Clear the text fields after insertion
        idtxtField.setText("");
        nametxtField.setText("");
        agetxtField.setText("");
        contacttxtField.setText("");
        coachtxtField.setText("");
        coursetxtField.setText("");
        datetxtField.setText("");
        
         System.out.println(" " + name); 
        

        // Refresh the table after inserting the data
        updateDB();

        // Close the current JFrame
        addForm.dispose();

        JOptionPane.showMessageDialog(this, "Database updated successfully!");
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Invalid ID, Age, or Contact format. Please enter valid integers.");
    }
    }//GEN-LAST:event_addBtnActionPerformed

    private void coachtxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coachtxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_coachtxtFieldActionPerformed

    private void nametxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nametxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nametxtFieldActionPerformed

    private void contacttxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contacttxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contacttxtFieldActionPerformed

    private void cancelbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelbtnActionPerformed
        // TODO add your handling code here:
        addForm.dispose();
    }//GEN-LAST:event_cancelbtnActionPerformed

    private void maineditbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maineditbtnActionPerformed
       
        
        if (selectedRowIndex >= 0){
        
        editFrame.setSize(400, 400); 
        editFrame.setVisible(!editFrame.isVisible());
        String id = mainDB.getValueAt(selectedRowIndex, 0).toString();
        String name = mainDB.getValueAt(selectedRowIndex, 1).toString();
        String age = mainDB.getValueAt(selectedRowIndex, 2).toString();
        String contact = mainDB.getValueAt(selectedRowIndex, 3).toString();
        String coach = mainDB.getValueAt(selectedRowIndex, 4).toString();
        String course = mainDB.getValueAt(selectedRowIndex, 5).toString();
        String date = mainDB.getValueAt(selectedRowIndex, 6).toString();
        
       

            editnametxtField.setText(name);
            editagetxtField.setText(age);
            editcontacttxtField.setText(contact);
            editcoachtxtField.setText(coach);
            editcoursetxtField.setText(course);
            editdatetxtField.setText(date);
        }
        else{ JOptionPane.showMessageDialog(this, "Please select a row to update.");}
     
        
    }//GEN-LAST:event_maineditbtnActionPerformed

    private void editagetxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editagetxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editagetxtFieldActionPerformed

    private void editcoachtxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editcoachtxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editcoachtxtFieldActionPerformed

    private void editbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbtnActionPerformed
        // TODO add your handling code here:
        
        String id = idtxtField.getText();
        String name = editnametxtField.getText();
        String age = editagetxtField.getText();
        String contact = editcontacttxtField.getText();
        String coach = editcoachtxtField.getText();
        String course = editcoursetxtField.getText();
        String date = editdatetxtField.getText();

        try {
            // Parse ID as an integer
            int parsedID = Integer.parseInt(id);

            // Update the data in the database for the selected row
            updateData(parsedID, name, Integer.parseInt(age), (contact), coach, course, date);

            // Clear the text fields after updating
            //editidtxtField.setText("");
            editnametxtField.setText("");
            editagetxtField.setText("");
            editcontacttxtField.setText("");
            editcoachtxtField.setText("");
            editcoursetxtField.setText("");
            editdatetxtField.setText("");

            // Refresh the table after updating the data
            updateDB();

            // Reset the selected row index
            selectedRowIndex = -1;
            editFrame.dispose();
            JOptionPane.showMessageDialog(this, "Database updated successfully!");
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid ID, Age, or Contact format. Please enter valid integers.");
        }
        
    }//GEN-LAST:event_editbtnActionPerformed

    private void datetxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datetxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_datetxtFieldActionPerformed

    private void coursetxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coursetxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_coursetxtFieldActionPerformed

    private void formbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formbtnActionPerformed
        // TODO add your handling code here:
        if (selectedRowIndex >= 0){
            
            String id_string = mainDB.getValueAt(selectedRowIndex, 0).toString();
            studentForm.setSize(1000, 600); 
            studentForm.setVisible(!editFrame.isVisible());

            filterTableByColumn();
            updateFormsDB();
        }
        
        
        
    }//GEN-LAST:event_formbtnActionPerformed

    private void editcoursetxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editcoursetxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editcoursetxtFieldActionPerformed

    private void editnametxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editnametxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editnametxtFieldActionPerformed

    private void editdatetxtFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editdatetxtFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editdatetxtFieldActionPerformed

    private void formInsertBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formInsertBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_formInsertBtnActionPerformed

    private void formInsertBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formInsertBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_formInsertBtn1ActionPerformed

    private void formInsertBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_formInsertBtn2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_formInsertBtn2ActionPerformed

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deletebtnActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Forms.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Forms.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Forms.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Forms.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Forms().setVisible(true);
            }
        });
        
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame AdminLogIn;
    private javax.swing.JPanel Delete;
    private javax.swing.JButton addBtn;
    private javax.swing.JFrame addForm;
    private javax.swing.JTextField agetxtField;
    private javax.swing.JButton cancelbtn;
    private javax.swing.JButton clearBtn;
    private javax.swing.JTextField coachtxtField;
    private javax.swing.JLabel contactFormtxt;
    private javax.swing.JTextField contacttxtField;
    private javax.swing.JLabel courseFormtxt;
    private javax.swing.JTextField coursetxtField;
    private javax.swing.JLabel dateFormtxt;
    private javax.swing.JTextField datetxtField;
    private javax.swing.JButton deletebtn;
    private javax.swing.JFrame editFrame;
    private javax.swing.JTextField editagetxtField;
    private javax.swing.JButton editbtn;
    private javax.swing.JTextField editcoachtxtField;
    private javax.swing.JTextField editcontacttxtField;
    private javax.swing.JTextField editcoursetxtField;
    private javax.swing.JTextField editdatetxtField;
    private javax.swing.JTextField editnametxtField;
    private javax.swing.JToggleButton formInsertBtn;
    private javax.swing.JToggleButton formInsertBtn1;
    private javax.swing.JToggleButton formInsertBtn2;
    private javax.swing.JLabel formName;
    private javax.swing.JLabel formName1;
    private javax.swing.JLabel formName2;
    private javax.swing.JTable formTable;
    private javax.swing.JButton formbtn;
    private javax.swing.JLabel formstudentID;
    private javax.swing.JTextField idtxtField;
    private javax.swing.JButton insertBtn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTable mainDB;
    private javax.swing.JButton maineditbtn;
    private javax.swing.JLabel nameFormtxt;
    private javax.swing.JTextField nametxtField;
    private java.awt.Panel panel1;
    private javax.swing.JFrame studentForm;
    // End of variables declaration//GEN-END:variables
}
