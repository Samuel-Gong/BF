package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import rmi.RemoteHelper;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OpenPanel extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	JTextArea textArea;
	public OpenPanel() {
		setVisible(true);
		setBounds(420, 250, 301, 173); 
		setLayout(null);
		
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("宋体", Font.PLAIN, 18));
//		textArea.setBounds(22, 0, 279, 133);
		JScrollPane sp  = new JScrollPane(textArea);
		sp.setBounds(22, 0, 279, 133);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(sp);
		
		textField = new JTextField();
		textField.setBounds(10, 143, 132, 21);
		add(textField);
		textField.setColumns(10);
		
		JButton btnOpen = new JButton("open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean hasName = false;
				String username = MainFrame.userDialog.logoutPanel.getUsername();
				String fileName = textField.getText();
				String[] fileNames = null;
				try {
					fileNames = RemoteHelper.getInstance().getIOService().readFileList(username);
				} catch (RemoteException ex) {
					ex.printStackTrace();
				}
				for (int i = 0; i < fileNames.length; i++) {
					if(fileNames[i].equals(fileName))
						hasName = true;
				}
				//输入的名称有误
				if(!hasName){
					JOptionPane.showMessageDialog(MainFrame.userDialog, "Something wrong with the name of file.Please check it out.", "Tip",
							JOptionPane.ERROR_MESSAGE);
				}
				else{
					String code = null;
					MainFrame.fileName = fileName;
					try {
						code = RemoteHelper.getInstance().getIOService().readFile(username, fileName);
					} catch (RemoteException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
					MainFrame.codeArea.setText(code);
					MainFrame.userDialog.setVisible(false);
					textField.setText("");
					JOptionPane.showMessageDialog(MainFrame.frame, "Open succeeded!", "Tip",
							JOptionPane.INFORMATION_MESSAGE	);		
					}
			}
		});
		btnOpen.setFont(new Font("宋体", Font.BOLD, 15));
		btnOpen.setBounds(178, 143, 93, 23);
		add(btnOpen);
	}
	
	public void setTextArea(String username) {
		// TODO 自动生成的方法存根
		//清空上一次
		textArea.setText("");
		textArea.append("File List:\n");
		String[] fileNames = null;
		try {
			fileNames = RemoteHelper.getInstance().getIOService().readFileList(username);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < fileNames.length; i++) {
			if(i!=fileNames.length-1)
				textArea.append(fileNames[i]+"\n");
			else
				textArea.append(fileNames[i]);
		}
	}
}
