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
	//private static final int MYSQL_DUPLICATE_ERROR = 1062;
	
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
		
		JTextField field_username = new JTextField();
		field_username.setBounds(122, 77, 297, 41);
		contentPane.add(field_username);
		field_username.setColumns(10);
		
		JPasswordField field_password = new JPasswordField();
		field_password.setBounds(122, 145, 297, 41);
		contentPane.add(field_password);
		
		JButton btn_accept = new JButton("Accept");
		btn_accept.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{ 
				try 
				{
					//create binary hashed-salt password
					byte[] salt = PBKDF2WithHmacSHA512.salt();
					String passwordStr = String.copyValueOf(field_password.getPassword());
					byte[] hash = PBKDF2WithHmacSHA512.hash(passwordStr, salt);
					
//					String saltStr = Base64.getEncoder().encodeToString(salt);
//					String hashStr = Base64.getEncoder().encodeToString(hash);
					
					String saltStr = PBKDF2WithHmacSHA512.byteConvertToString(salt);
					String hashStr = PBKDF2WithHmacSHA512.byteConvertToString(hash);
					
					//create user
//					Class.forName("com.mysql.cj.jdbc.Driver");
//					Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/userlogin", "root", "Baoanh123!");
					Connection connect = DatabaseHelper.createConnection();
					
					//add user to the database
					PreparedStatement prepState = connect.prepareStatement("insert into users(username, password, salt) values(?, ?, ?)");
					prepState.setString(1, field_username.getText());
					prepState.setString(2, hashStr);
					prepState.setString(3, saltStr);
					
					int execute_value = prepState.executeUpdate();
					if(execute_value > 0)
					{
						System.out.println("Register successful...");
						JOptionPane.showMessageDialog(null, "Register Successful");
						
					}
					else
					{
						System.out.println("Register failed...");
						JOptionPane.showMessageDialog(null, "Register Failed");
					}
				}
				catch(SQLException sqlEx)
				{
					if(sqlEx.getErrorCode() == DatabaseHelper.MYSQL_DUPLICATE_ERROR)
					{
						JOptionPane.showMessageDialog(null, "Username is taken");
						System.out.println("Username already exist...");
						DebugHelper.getCurrentLine();
						DebugHelper.getDirPath("Register.java");
					}
					
					System.out.println("Database error...");
					System.out.println(sqlEx);
					System.out.println(sqlEx.getErrorCode());
					
					
					
				}
				catch(Exception exp)
				{
					DebugHelper.getCurrentLine();
					DebugHelper.getDirPath("Register.java");
					System.out.println(exp);
					
				}
			}
		});
		
		btn_accept.setBounds(12, 222, 129, 41);
		contentPane.add(btn_accept);
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		btn_cancel.setBounds(150, 222, 129, 41);
		contentPane.add(btn_cancel);
		
		JButton btn_login = new JButton("Login");
		btn_login.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
				Login login_window = new Login();
				login_window.setVisible(true);
			}
		});
		btn_login.setBounds(291, 222, 129, 41);
		contentPane.add(btn_login);
		
		
	}
}
