package ui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import rmi.RemoteHelper;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.awt.event.ActionEvent;

public class ModifyPasswordPanel extends JPanel {

	RemoteHelper remoteHelper;
	private JPasswordField newPasswordField;
	private JPasswordField confirmField;

	public ModifyPasswordPanel() {
		remoteHelper = RemoteHelper.getInstance();

		setVisible(true);
		setLayout(null);
		setBounds(420, 250, 299, 178);

		JLabel lblNewPassword = new JLabel("New Password:");
		lblNewPassword.setBounds(44, 10, 80, 21);
		add(lblNewPassword);

		newPasswordField = new JPasswordField();
		newPasswordField.setBounds(94, 40, 145, 21);
		add(newPasswordField);
		newPasswordField.setColumns(10);

		confirmField = new JPasswordField();
		confirmField.setColumns(10);
		confirmField.setBounds(94, 91, 145, 21);
		add(confirmField);

		JLabel lblComfirm = new JLabel("Comfirm:");
		lblComfirm.setBounds(44, 60, 80, 21);
		add(lblComfirm);

		JButton btnModify = new JButton("Modify");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newPassword = newPasswordField.getText();
				String confirmPassword = confirmField.getText();
				if(!newPassword.equals(confirmPassword)){
					JOptionPane.showMessageDialog(MainFrame.userDialog, "The new and confirmed passwords do not match. Please type them again.", "Tip",
							JOptionPane.ERROR_MESSAGE);
					newPasswordField.setText("");
					confirmField.setText("");
				}
				else{
					if(newPassword.length()<6||newPassword.length()>16){
						JOptionPane.showMessageDialog(MainFrame.userDialog, "The length of password must be between 6 and 16. Please type them again.", "Tip",
								JOptionPane.ERROR_MESSAGE);
						newPasswordField.setText("");
						confirmField.setText("");
					}
					else{
						String username = MainFrame.userDialog.logoutPanel.getUsername();
						try {
							remoteHelper.getUserService().modifyPassword(username, newPassword);
							JOptionPane.showMessageDialog(MainFrame.userDialog, "modify succeeded!Please login again", "Tip",
								JOptionPane.INFORMATION_MESSAGE);
							newPasswordField.setText("");
							confirmField.setText("");
							UserDialog.card.show(MainFrame.userDialog.cardPanel, "loginPanel");
							MainFrame.userDialog.logoutPanel.setInfo("");
							MainFrame.isLogin = false;
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btnModify.setBounds(146, 134, 93, 23);
		add(btnModify);
		
		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDialog.card.show(MainFrame.userDialog.cardPanel, "logoutPanel");
				newPasswordField.setText("");
				confirmField.setText("");
			}
		});
		btnReturn.setBounds(44, 134, 93, 23);
		add(btnReturn);
	}
}
