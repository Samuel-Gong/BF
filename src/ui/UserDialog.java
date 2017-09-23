package ui;

import java.awt.CardLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class UserDialog extends JDialog{
	LoginAndRegistPanel loginPanel ;
	LogoutAndDeletePanel logoutPanel;
	ModifyPasswordPanel modifyPasswordPanel;
	SavePanel savePanel;
	OpenPanel openPanel;
	JPanel cardPanel;
	static CardLayout card;
	
	public UserDialog(Frame frame){
		super(frame);
		setVisible(true);
		setBounds(420, 250, 316, 210);
		
		card = new CardLayout();
		cardPanel = new JPanel();
		loginPanel = new LoginAndRegistPanel();
		logoutPanel = new LogoutAndDeletePanel();
		modifyPasswordPanel = new ModifyPasswordPanel();
		savePanel = new SavePanel();
		openPanel = new OpenPanel();
		cardPanel.setVisible(true);
		cardPanel.setLayout(card);
		
		getContentPane().add(cardPanel);
		
		cardPanel.add(loginPanel);
		cardPanel.add(logoutPanel);
		cardPanel.add(modifyPasswordPanel);
		cardPanel.add(savePanel);
		cardPanel.add(openPanel);
		
		card.addLayoutComponent(loginPanel, "loginPanel");
		card.addLayoutComponent(logoutPanel, "logoutPanel");
		card.addLayoutComponent(modifyPasswordPanel, "modifyPanel");
		card.addLayoutComponent(savePanel, "savePanel");
		card.addLayoutComponent(openPanel, "openPanel");
		
		card.show(cardPanel, "loginPanel");
	}
}
