package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class LoginAndRegistPanel extends JPanel {
	private JTextField usernameField;
	private JPasswordField passwordField;
	private RemoteHelper remoteHelper;

	public LoginAndRegistPanel(){

		remoteHelper = RemoteHelper.getInstance();

		setVisible(true);
		setLayout(null);
		setBounds(420, 250, 316, 210);

		JLabel usernameLabel = new JLabel("username:");
		usernameLabel.setBounds(41, 37, 62, 22);
		add(usernameLabel);

		JLabel passwordLabel = new JLabel("password:");
		passwordLabel.setBounds(41, 81, 62, 22);
		add(passwordLabel);

		usernameField = new JTextField();
		usernameField.setBounds(113, 38, 142, 21);
		add(usernameField);
		usernameField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setColumns(10);
		passwordField.setBounds(113, 82, 142, 21);
		add(passwordField);

		//登陆操作
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = passwordField.getText();
				try {
					if(remoteHelper.getUserService().login(username, password)){
						UserDialog.card.show(MainFrame.userDialog.cardPanel, "logoutPanel");
						MainFrame.userDialog.logoutPanel.setInfo(username);
						usernameField.setText("");
						passwordField.setText("");
						JOptionPane.showMessageDialog(MainFrame.userDialog, "login succeeded!","Tip",
								JOptionPane.INFORMATION_MESSAGE);
						MainFrame.isLogin = true;
					}
					else{
						JOptionPane.showMessageDialog(MainFrame.userDialog, "username or password wrong!","Tip",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		loginButton.setBounds(41, 127, 93, 23);
		add(loginButton);

		//注册
		JButton registButton = new JButton("Regist");
		registButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = usernameField.getText();
				String password = passwordField.getText();
				try {
					if(password.length()<6||password.length()>16){
						JOptionPane.showMessageDialog(MainFrame.userDialog, "The length of password must be between 6 and 16. Please type it again.", "Tip",
								JOptionPane.ERROR_MESSAGE);
					}else if(remoteHelper.getUserService().register(username, password)){
						usernameField.setText("");
						passwordField.setText("");
						JOptionPane.showMessageDialog(MainFrame.userDialog, "regist succeeded!","Tip",
								JOptionPane.INFORMATION_MESSAGE);
						//为用户创建一个以用户名命名的文件夹，便于用户存储
						remoteHelper.getIOService().makeDir(username);
					}
					else{
						usernameField.setText("");
						passwordField.setText("");
						JOptionPane.showMessageDialog(MainFrame.userDialog, "username occupied!","Tip",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		});
		registButton.setBounds(162, 127, 93, 23);
		add(registButton);

	}
}
