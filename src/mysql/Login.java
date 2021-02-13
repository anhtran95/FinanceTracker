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
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;



/*
 * @author Anh Tran
 * 
 * Description:
 * Create Login UI, authenticate user from database
 */

public class Login extends JFrame {
	private JPasswordField field_password;

	//private JPanel contentPane;
	//private JTextField textField;
	//private JTextField textField_1;

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
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 294);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblLogin.setBounds(12, 13, 408, 41);
		contentPane.add(lblLogin);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setBounds(12, 67, 129, 41);
		contentPane.add(lblUsername);
		
		JTextField field_username = new JTextField();
		field_username.setBounds(123, 67, 297, 41);
		contentPane.add(field_username);
		field_username.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(12, 121, 129, 41);
		contentPane.add(lblPassword);
		
		JButton btn_login = new JButton("Login");
		btn_login.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				
				try 
				{
					//check database for user
					Connection connect = DatabaseHelper.createConnection();
					PreparedStatement prepState = connect.prepareStatement("select * from users where username = ?");
					prepState.setString(1, field_username.getText());
					ResultSet results = prepState.executeQuery();
					
					//user exist
					if(results.next())
					{
						System.out.println("Checking password for user: " + results.getString(2));
						
						//compare password
						String db_password = results.getString(3);
						String db_salt = results.getString(4);
						String input_password = String.valueOf(field_password.getPassword());
						
						//questions: store byte[] as varbinary/blob/varchar
						
						
						
						
						
						
					}
					else
					{
						System.out.println("User: " + field_username.getText() + " do not exist...");
						JOptionPane.showMessageDialog(null, "User: " + field_username.getText() + " do not exist...");
					}
					
				}
				catch(SQLException sqlEx)
				{
					if(sqlEx.getErrorCode() == DatabaseHelper.MYSQL_DUPLICATE_ERROR)
					{
						JOptionPane.showMessageDialog(null, "Username is taken");
						System.out.println("Username already exist");
						DebugHelper.getCurrentLine();
						DebugHelper.getDirPath("Login.java");
					}
					
					System.out.println(sqlEx);
					System.out.println(sqlEx.getErrorCode());
				}
				catch(Exception exp)
				{
					System.out.println(exp);
					DebugHelper.getCurrentLine();
					DebugHelper.getDirPath("Login.java");
				}
			}
		});
		btn_login.setBounds(12, 195, 129, 41);
		contentPane.add(btn_login);
		
		JButton btn_quit = new JButton("Quit");
		btn_quit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				System.exit(0);
			}
		});
		btn_quit.setBounds(294, 195, 129, 41);
		contentPane.add(btn_quit);
		
		JButton btn_register = new JButton("Register");
		btn_register.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				dispose();
				Register register_window = new Register();
				register_window.setVisible(true);
			}
		});
		
		btn_register.setBounds(153, 195, 129, 41);
		contentPane.add(btn_register);
		
		field_password = new JPasswordField();
		field_password.setBounds(123, 121, 297, 41);
		contentPane.add(field_password);
	}
}
