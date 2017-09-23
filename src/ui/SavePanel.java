package ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import rmi.RemoteHelper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;

public class SavePanel extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public SavePanel() {
		setVisible(true);
		setBounds(420, 250, 280, 166);
		setLayout(null);

		JLabel lblNameInput = new JLabel("Please name your file:");
		lblNameInput.setBounds(10, 23, 152, 27);
		add(lblNameInput);

		textField = new JTextField();
		textField.setBounds(57, 70, 213, 27);
		add(textField);
		textField.setColumns(10);

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.setBounds(113, 119, 93, 23);
		btnConfirm.addActionListener(new ConfirmListener());
		add(btnConfirm);
	}

	class ConfirmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = MainFrame.codeArea.getText();
			String fileName = MainFrame.fileName = textField.getText();
			String username = MainFrame.userDialog.logoutPanel.getUsername();
			String path = username+"\\"+fileName+"_"+"version"+"\\"+"version_";
			try {
				String tempCode = null;
				//发生重名,提示是否覆盖
				if(RemoteHelper.getInstance().getIOService().isNamed(username, fileName)){
					boolean hasSameCode = false;
					//检查当前代码是否与历史版本代码有重合
					for (int i = 1; i <= 3; i++) {
						tempCode = null;
						try {
							tempCode = RemoteHelper.getInstance().getIOService().readFileThroughPath(path+1);
						} catch (RemoteException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
						}
						if(tempCode.equals(code)){
							hasSameCode = true;
							break;
						}
					}
					//历史版本中有相同code
					if(hasSameCode){
						JOptionPane.showMessageDialog(MainFrame.userDialog, "Same Code in versions.", "Tip",
								JOptionPane.WARNING_MESSAGE);
						textField.setText("");
						MainFrame.userDialog.setVisible(false);
						MainFrame.codeArea.setText("");
						MainFrame.dataInputArea.setText("");
						MainFrame.dataOutputArea.setText("");
					}
					else{
						int option = JOptionPane.showConfirmDialog(MainFrame.userDialog, "File name occupied!Do you want to cover the old one?", "Tip", 
								JOptionPane.YES_NO_OPTION);
						if(option == JOptionPane.YES_OPTION){
							//覆盖的时候将新文件写入version_1,version_1里的写到version_2,version_2里的写到version_3
							RemoteHelper.getInstance().getIOService().writeFile(code,username+"\\"+fileName);
							//version_2到version_3
							tempCode = RemoteHelper.getInstance().getIOService().readFileThroughPath(path+2);
							RemoteHelper.getInstance().getIOService().writeFile(tempCode, path+3);
							//version_1到version_2
							tempCode = RemoteHelper.getInstance().getIOService().readFileThroughPath(path+1);
							RemoteHelper.getInstance().getIOService().writeFile(tempCode, path+2);
							//新文件到version_1
							RemoteHelper.getInstance().getIOService().writeFile(code, path+1);
							textField.setText("");
							MainFrame.userDialog.setVisible(false);
							MainFrame.codeArea.setText("");
							JOptionPane.showMessageDialog(MainFrame.userDialog, "Save succeeded!", "Tip",
									JOptionPane.INFORMATION_MESSAGE);
						}
						else{
							JOptionPane.showMessageDialog(MainFrame.userDialog, "Please input another name.", "Tip",
									JOptionPane.INFORMATION_MESSAGE);
							textField.setText("");
						}
					}
				}
				else{
					//未发生重名，新建一个文件在用户的目录下
					RemoteHelper.getInstance().getIOService().newFile(username+"\\"+fileName);
					RemoteHelper.getInstance().getIOService().writeFile(code,username+"\\"+fileName);
					//同时创建一个文件名+version 的历史版本目录
					RemoteHelper.getInstance().getIOService().makeDir(username+"\\"+fileName+"_"+"version");
					//历史版本在目录下创建三个空文件
					for (int i = 0; i < 3; i++) {
						RemoteHelper.getInstance().getIOService().newFile(path+(i+1));
					}
					//将新文件写到version_1里面
					RemoteHelper.getInstance().getIOService().writeFile(code, path+1);
					JOptionPane.showMessageDialog(MainFrame.frame, "Save succeeded!", "Tip",
							JOptionPane.INFORMATION_MESSAGE);
					textField.setText("");
					MainFrame.userDialog.setVisible(false);
					MainFrame.codeArea.setText("");
					MainFrame.dataInputArea.setText("");
					MainFrame.dataOutputArea.setText("");
				}
			}catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
}
