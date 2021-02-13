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
import java.util.Base64;

import javax.swing.JPasswordField;



/*
 * @author Anh Tran
 * 
 * Description:
 * Create register UI, create user to add to database
 * Hashing password and store salt
 */


public class Register extends JFrame 
{
	private static final int MYSQL_DUPLICATE_ERROR = 1062;
	
	//private JPanel contentPane;
	//private JTextField username_textField;
	//private JPasswordField passwordField;

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
					Register frame = new Register();
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
	public Register() 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 323);
		JPanel contentPane = new JPanel();
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
		lblUsername.setBounds(12, 74, 129, 41);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(12, 142, 129, 41);
		contentPane.add(lblPassword);
		
		JTextField username_textField = new JTextField();
		username_textField.setBounds(122, 77, 297, 41);
		contentPane.add(username_textField);
		username_textField.setColumns(10);
		
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(122, 145, 297, 41);
		contentPane.add(passwordField);
		
		JButton accept_btn = new JButton("Accept");
		accept_btn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{ 
				try 
				{
					//create binary hashed-salt password
					byte[] salt = PBKDF2WithHmacSHA512.salt();
					String passwordStr = String.copyValueOf(passwordField.getPassword());
					byte[] hash = PBKDF2WithHmacSHA512.hash(passwordStr, salt);
					
					String saltStr = Base64.getEncoder().encodeToString(salt);
					String hashStr = Base64.getEncoder().encodeToString(hash);
					
					//connect to database
					Class.forName("com.mysql.cj.jdbc.Driver");
					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "Baoanh123!");
					
					//add user to the database
					PreparedStatement prepState = connect.prepareStatement("insert into users(username, password, salt) values(?, ?, ?)");
					prepState.setString(1, username_textField.getText());
					prepState.setString(2, hashStr);
					prepState.setString(3, saltStr);
					
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
						DebugHelper.printDebugLocation("Register.java");
					}
					
				}
				//all other errors
				catch(Exception exp)
				{
					System.out.println(exp);
					DebugHelper.printDebugLocation("Register.java");
				}
			}
		});
		
		accept_btn.setBounds(12, 222, 129, 41);
		contentPane.add(accept_btn);
		
		JButton cancel_btn = new JButton("Cancel");
		cancel_btn.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		cancel_btn.setBounds(150, 222, 129, 41);
		contentPane.add(cancel_btn);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login login_window = new Login();
				login_window.setVisible(true);
			}
		});
		btnLogin.setBounds(291, 222, 129, 41);
		contentPane.add(btnLogin);
		
		
	}
}
