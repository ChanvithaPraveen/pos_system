import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;

public class POSSystem {
    private JPanel Main;
    private JTextField textName;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JTextField textPrice;
    private JTextField textQty;
    private JButton searchButton;
    private JTextField textpid;

    public static void main(String[] args) {
        JFrame frame = new JFrame("POSSystem");
        frame.setContentPane(new POSSystem().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public POSSystem() {

        Connect();

        //save button when added details
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name, price, qty;

                name = textName.getText();
                price = textPrice.getText();
                qty = textQty.getText();

                try {
                    pst = con.prepareStatement("insert into products(pname, price, qty) values(?,?,?)");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Added!!!");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(null,"Invalid Inputs!!!");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();
                }
            }
        });

        //search items through pid
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String pid = textpid.getText();

                    pst = con.prepareStatement("select pname, price, qty from products where pid = ?");
                    pst.setString(1, pid);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {
                        String name = rs.getString(1);
                        String price = rs.getString(2);
                        String qty = rs.getString(3);

                        textName.setText(name);
                        textPrice.setText(price);
                        textQty.setText(qty);
                    }
                    else
                    {
                        textName.setText("");
                        textPrice.setText("");
                        textQty.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");

                    }
                }

                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        //update the product table rows
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String pid,name,price,qty;

                name = textName.getText();
                price = textPrice.getText();
                qty = textQty.getText();
                pid = textpid.getText();

                try {

                    pst = con.prepareStatement("update products set pname = ?, price = ?, qty = ? where pid = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, pid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updated!!!");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();
                    textpid.setText("");
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });


        //delete rows in the product table

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String bid;

                bid = textpid.getText();

                try {
                    pst = con.prepareStatement("delete from products  where pid = ?");
                    pst.setString(1, bid);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleted!!!");

                    textName.setText("");
                    textPrice.setText("");
                    textQty.setText("");
                    textName.requestFocus();
                    textpid.setText("");
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }

            }
        });
    }

    Connection con;
    PreparedStatement pst;

    public void Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/" + "pos_system", "root", "");
            System.out.println("Success");
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
