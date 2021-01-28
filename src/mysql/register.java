package mysql;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.JPasswordField;


/*
 * Author
 * Anh Tran
 * 2021
 */
public class register extends JFrame 
{

	private JPanel contentPane;
	private JTextField username_textField;
	private static final int MYSQL_DUPLICATE_ERROR = 1062;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					register frame = new register();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public register() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Registration");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		lblNewLabel.setBounds(12, 13, 408, 51);
		contentPane.add(lblNewLabel);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setBounds(12, 77, 98, 31);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(12, 121, 98, 31);
		contentPane.add(lblPassword);
		
		username_textField = new JTextField();
		username_textField.setBounds(122, 77, 266, 31);
		contentPane.add(username_textField);
		username_textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(122, 121, 266, 31);
		contentPane.add(passwordField);
		
		JButton accept_btn = new JButton("Accept");
		accept_btn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{ 
				try 
				{
					//connect to MySQL database
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "Baoanh123!");
					
					//add user to the database
					PreparedStatement prepState = connect.prepareStatement("insert into users(username, password) values(?, ?)");
					prepState.setString(1, username_textField.getText());
					prepState.setString(2, passwordField.getPassword());
					
					int execute_value = prepState.executeUpdate();
					if(execute_value > 0)
					{
						System.out.println("Register done successfully...");
						JOptionPane.showMessageDialog(null, "Register Successful");
						
					}
					else
					{
						System.out.println("Register failed...");
						JOptionPane.showMessageDialog(null, "Register Failed");
					}
				}
				//SQL errors
				catch(SQLException sqlEx)
				{
					System.out.println(sqlEx);
					System.out.println(sqlEx.getErrorCode());
					
					if(sqlEx.getErrorCode() == MYSQL_DUPLICATE_ERROR)
					{
						JOptionPane.showMessageDialog(null, "Username is taken");
					}
					
				}
				//all other errors
				catch(Exception exp)
				{
					System.out.println(exp);
				}
				
			}
		});
		
		accept_btn.setBounds(122, 184, 104, 31);
		contentPane.add(accept_btn);
		
		JButton cancel_btn = new JButton("Cancel");
		cancel_btn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		cancel_btn.setBounds(249, 184, 104, 31);
		contentPane.add(cancel_btn);
		
		
	}
}
