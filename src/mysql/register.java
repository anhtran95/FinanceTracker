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
 * Create register UI, add user to database
 */


public class Register extends JFrame 
{
	//debug
	private static final String file_name = "Register.java";


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
		setBounds(100, 100, 450, 220);
		setTitle("Register");
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setBounds(12, 13, 129, 41);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(12, 67, 129, 41);
		contentPane.add(lblPassword);
		
		JTextField field_username = new JTextField();
		field_username.setBounds(122, 13, 297, 41);
		contentPane.add(field_username);
		field_username.setColumns(10);
		
		JPasswordField field_password = new JPasswordField();
		field_password.setBounds(122, 67, 297, 41);
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
					
					//add user to the database
					Connection connect = DatabaseHelper.createConnection();
					PreparedStatement prepState = connect.prepareStatement("insert into users(username, password, salt) values(?, ?, ?)");
					prepState.setString(1, field_username.getText());
					prepState.setBytes(2, hash);
					prepState.setBytes(3, salt);
					
					int execute_value = prepState.executeUpdate();
					if(execute_value > 0)
					{
						System.out.println("Register successful...");
						JOptionPane.showMessageDialog(null, "Register Successful");
						field_username.setText("");
						field_password.setText("");
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
						DebugHelper.getCurrentLineAndDir(file_name);
					}
					
					System.out.println("Database error...");
					System.out.println(sqlEx);
					System.out.println(sqlEx.getErrorCode());
					
					
					
				}
				catch(Exception exp)
				{
					DebugHelper.getCurrentLineAndDir(file_name);
					System.out.println(exp);
					
				}
			}
		});
		
		btn_accept.setBounds(12, 121, 129, 41);
		contentPane.add(btn_accept);
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("Quit Register...");
				System.exit(0);
			}
		});
		btn_cancel.setBounds(150, 121, 129, 41);
		contentPane.add(btn_cancel);
		
		JButton btn_login = new JButton("Login");
		btn_login.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.out.println("Transition to Login window...");
				dispose();
				Login login_window = new Login();
				login_window.setVisible(true);
			}
		});
		btn_login.setBounds(290, 121, 129, 41);
		contentPane.add(btn_login);
		
		
	}
}
