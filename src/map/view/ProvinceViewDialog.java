package map.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import map.controller.Controller;
import map.model.MapGenObserver;
import map.model.Province;

public class ProvinceViewDialog extends JDialog {
	private JTable table;
	public ProvinceViewDialog(JFrame ventana,Controller ctrl) {
		super(ventana,"Provinces view",true);
		table = new JTable(new ProvinceTableModel(ctrl));
		JPanel p = new JPanel(new BorderLayout());
		p.add(new JScrollPane(table));
		this.add(p);
		this.setPreferredSize(new Dimension(1200,300));
		this.pack();
		this.setVisible(true);
	}
}
