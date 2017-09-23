package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import rmi.RemoteHelper;
import java.awt.Font;
import javax.swing.SwingConstants;


public class MainFrame extends JFrame {

	static JFrame frame;
	static UserDialog userDialog;
	static boolean isLogin;
	static String fileName;

	static JTextArea dataInputArea = new JTextArea();
	static JTextArea dataOutputArea = new JTextArea();
	static JTextArea codeArea = new JTextArea();
	JMenuBar menuBar = new JMenuBar();

	public MainFrame() {
		// 创建窗体
		frame = new JFrame("BF Client");
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().setForeground(Color.BLACK);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int option = JOptionPane.showConfirmDialog(MainFrame.frame, "Are you sure to exit？","Tip", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(549, 430);
		frame.setLocation(300, 150);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		frame.setResizable(false);
		codeArea.setIgnoreRepaint(true);

		codeArea.setFont(new Font("宋体", Font.PLAIN, 22));
		JScrollPane sp1  = new JScrollPane(codeArea);
		sp1.setBounds(10, 34, 523, 236);
		sp1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(sp1);
		dataInputArea.setIgnoreRepaint(true);
		dataInputArea.setFont(new Font("宋体", Font.BOLD, 20));

		dataInputArea.setForeground(Color.BLACK);
		JScrollPane sp2  = new JScrollPane(dataInputArea);
		sp2.setBounds(10, 274, 260, 117);
		sp2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(sp2);
		dataOutputArea.setEditable(false);
		dataOutputArea.setIgnoreRepaint(true);
		dataOutputArea.setFont(new Font("宋体", Font.BOLD, 20));
		dataOutputArea.setForeground(Color.BLACK);
		JScrollPane sp3  = new JScrollPane(dataOutputArea);
		sp3.setBounds(273, 274, 260, 117);
		sp3.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sp3.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(sp3);


		menuBar.setFont(new Font("宋体", Font.BOLD, 15));
		menuBar.setBounds(10, 0, 436, 31);
		frame.getContentPane().add(menuBar);
		JMenu runMenu = new JMenu("Run");
		JMenu versionMenu = new JMenu("Version");
		JMenu fileMenu = new JMenu("File");

		menuBar.add(fileMenu);
		menuBar.add(runMenu);
		menuBar.add(versionMenu);

		//fileMenu
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);


		//runMenu
		JMenuItem executeMenuItem = new JMenuItem("Execute");
		runMenu.add(executeMenuItem);

		//versionMenu
		JMenuItem mntmVersion_1 = new JMenuItem("version_1");

		versionMenu.add(mntmVersion_1);
		JMenuItem mntmVersion_2 = new JMenuItem("version_2");

		versionMenu.add(mntmVersion_2);
		JMenuItem mntmVersion_3 = new JMenuItem("version_3");

		versionMenu.add(mntmVersion_3);

		JButton LogButton = new JButton("Login");
		LogButton.setHorizontalAlignment(SwingConstants.LEADING);
		LogButton.setFont(new Font("华文彩云", Font.BOLD, 13));
		LogButton.setBounds(462, 0, 71, 31);
		frame.getContentPane().add(LogButton);

		//ActionListener				

		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new MenuItemActionListener());
		exitMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new MenuItemActionListener());
		executeMenuItem.addActionListener(new MenuItemActionListener());
		LogButton.addActionListener(new LogButtonActionListener());
		mntmVersion_1.addActionListener(new MenuItemActionListener());
		mntmVersion_2.addActionListener(new MenuItemActionListener());
		mntmVersion_3.addActionListener(new MenuItemActionListener());



	}

	//登陆按钮的监听器
	class LogButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(!isLogin){
				if(userDialog == null){
					userDialog = new UserDialog(frame);
				}
				else{
					userDialog.setVisible(true);
				}
			}
			else{
				userDialog.setVisible(true);
				UserDialog.card.show(MainFrame.userDialog.cardPanel, "logoutPanel");
			}
		}

	}
	class MenuItemActionListener implements ActionListener {
		/**
		 * 子菜单响应事件
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			//new
			if (cmd.equals("New")){
				if(isLogin){
					int option = JOptionPane.showConfirmDialog(frame, "Do you want to save the code first?", "Tip", 
							JOptionPane.YES_NO_OPTION);
					if(option == JOptionPane.YES_OPTION){
						userDialog.setVisible(true);
						UserDialog.card.show(userDialog.cardPanel, "savePanel");
					}
					else{
						codeArea.setText("");
					}
				}
				else{
					codeArea.setText("");
				}
			}
			//open
			else if(cmd.equals("Open")) {
				if(isLogin){
					userDialog.setVisible(true);
					UserDialog.card.show(userDialog.cardPanel, "openPanel");
					String username = userDialog.logoutPanel.getUsername();
					userDialog.openPanel.setTextArea(username);
				}
				else{
					JOptionPane.showMessageDialog(frame, "Please login first!", "Tip",
							JOptionPane.WARNING_MESSAGE);
				}
			}
			//save
			else if(cmd.equals("Save")){
				if(isLogin){
					userDialog.setVisible(true);
					UserDialog.card.show(userDialog.cardPanel, "savePanel");
				}
				else{
					JOptionPane.showMessageDialog(frame, "Please login first!", "Tip",
							JOptionPane.WARNING_MESSAGE);
				}

			}
			//exit
			else if (cmd.equals("Exit")) {
				int option = JOptionPane.showConfirmDialog(frame, "Are you sure to exit？","Tip", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (option == JOptionPane.YES_OPTION)
					System.exit(0);
			} 
			//execute
			else if(cmd.equals("Execute")){
				String code = codeArea.getText().trim();
				String param = dataInputArea.getText();
				String result = null;
				try {
					result = RemoteHelper.getInstance().getIOService().execute(code, param);
				} catch (RemoteException e1) {
					JOptionPane.showMessageDialog(frame, "Something wrong with your code or input!", "Tip", 
							JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				dataOutputArea.setText(result);
				System.out.println(result);
			}else if(isLogin){
				String username = userDialog.logoutPanel.getUsername();
				String path = username+"\\"+fileName+"_"+"version"+"\\"+"version_";
				String code = null;
				if(cmd.equals("version_1")){
					try {
						code = RemoteHelper.getInstance().getIOService().readFileThroughPath(path+1);
						codeArea.setText(code);
					} catch (RemoteException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}else if(cmd.equals("version_2")){
					try {
						code = RemoteHelper.getInstance().getIOService().readFileThroughPath(path+2);
						codeArea.setText(code);
					} catch (RemoteException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}else if(cmd.equals("version_3")){
					try {
						code = RemoteHelper.getInstance().getIOService().readFileThroughPath(path+3);
						codeArea.setText(code);
					} catch (RemoteException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}
		}
	}
}