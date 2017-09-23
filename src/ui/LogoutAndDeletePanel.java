package ui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import rmi.RemoteHelper;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class LogoutAndDeletePanel extends JPanel {

	RemoteHelper remoteHelper;
	JLabel usernameInfoLabel;

	public LogoutAndDeletePanel(){
		remoteHelper = RemoteHelper.getInstance();

		setVisible(true);
		setLayout(null);
		setBounds(420, 250, 316, 172);

		JLabel usernameLabel = new JLabel("username:");
		usernameLabel.setBounds(39, 10, 62, 22);
		add(usernameLabel);

		usernameInfoLabel = new JLabel("");
		usernameInfoLabel.setBounds(111, 46, 142, 21);
		add(usernameInfoLabel);

		
		//登出
		JButton logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(remoteHelper.getUserService().logout()){
						UserDialog.card.show(MainFrame.userDialog.cardPanel, "loginPanel");
						JOptionPane.showMessageDialog(MainFrame.userDialog, "logout succeeded!","Tip",
								JOptionPane.DEFAULT_OPTION);
						setInfo("");
						MainFrame.isLogin = false;
					}
				} catch (RemoteException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		logoutButton.setBounds(39, 112, 82, 23);
		add(logoutButton);
		
		//删除用户
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String username = usernameInfoLabel.getText();
					if(remoteHelper.getUserService().deleteUser(username)){
						int option = JOptionPane.showConfirmDialog(MainFrame.userDialog, "Are you sure to delete this user？","Tip", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.YES_OPTION){
							UserDialog.card.show(MainFrame.userDialog.cardPanel, "loginPanel");
							JOptionPane.showMessageDialog(MainFrame.userDialog, "delete succeeded!","Tip",
									JOptionPane.DEFAULT_OPTION);
							setInfo("");
							remoteHelper.getIOService().deleteDir(username);
							MainFrame.isLogin = false;
						}
					}
				} catch (RemoteException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		deleteButton.setBounds(171, 136, 82, 23);
		add(deleteButton);
		
		//修改密码
		JButton modifyButton = new JButton("Modify");
		modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDialog.card.show(MainFrame.userDialog.cardPanel, "modifyPanel");
			}
		});
		modifyButton.setBounds(171, 87, 82, 23);
		add(modifyButton);
	}

	public void setInfo(String username){
		usernameInfoLabel.setText(username);
	}
	
	public String getUsername(){
		return usernameInfoLabel.getText();
	}
	
}
