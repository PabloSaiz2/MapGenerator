package map.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import map.controller.Controller;

public class MainWindow extends JFrame{
	private Controller _ctrl;
	private Border DEFAULT_BORDER = BorderFactory.createLineBorder(Color.BLACK,1);
	public MainWindow(Controller ctrl) {
		super("Map Generator");
		_ctrl = ctrl;
		initGUI();
	}
	private void initGUI() {
		// TODO Auto-generated method stub
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel mainPanel =  new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
		MapComponent map = new MapComponent(_ctrl);
		mainPanel.add(new ControlPanel(_ctrl,map),BorderLayout.PAGE_START);
		JPanel mapPanel = createViewPanel(map,"Map");
		mainPanel.add(mapPanel,BorderLayout.CENTER);
		this.setVisible(true);
		this.setResizable(false);
		this.pack();
	}
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder(DEFAULT_BORDER, title));
		p.add(new JScrollPane(c));
		return p;
	}
}
