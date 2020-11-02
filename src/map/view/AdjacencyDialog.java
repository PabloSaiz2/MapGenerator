package map.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import map.controller.Controller;
import map.model.MapGenObserver;
import map.model.Province;

public class AdjacencyDialog extends JDialog implements MapGenObserver{
	private JComboBox prov,adj;
	private DialogStatus status;
	public AdjacencyDialog(JFrame ventana,Controller ctrl) {
		super(ventana,"Adjacency connector",true);
		status = DialogStatus.WAITING;
		initGUI();
		this.setPreferredSize(new Dimension(400,200));
		this.pack();
		ctrl.addObserver(this);
		this.setVisible(true);
	}
	private void initGUI() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		JPanel mainPanel = new JPanel(new GridLayout(2,1));
		JPanel panel = new JPanel(new GridLayout(1,4));
		panel.add(new JLabel("From:"));
		prov = new JComboBox();
		adj = new JComboBox();
		panel.add(prov);
		panel.add(new JLabel("To"));
		panel.add(adj);
		mainPanel.add(panel);
		JPanel panelButtons = new JPanel(new GridLayout(1,2));
		JButton ok = new JButton("Connect");
		ok.setToolTipText("Connect provinces");
		panelButtons.add(ok);
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(prov.getSelectedItem()!=null&&adj.getSelectedItem()!=null) {
					status = DialogStatus.ACCEPTED;
					dispose();
				}
			}
			
		});
		JButton cancel = new JButton("Cancel");
		cancel.setToolTipText("Cancel connection");
		panelButtons.add(cancel);
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				status = DialogStatus.CANCEL;
				dispose();
			}
			
		});
		mainPanel.add(panelButtons);
		this.add(mainPanel);
			
	}
	public int getProv() {
		return prov.getSelectedIndex();
	}
	public int getAdj() {
		return adj.getSelectedIndex();
	}
	public DialogStatus getStatus() {
		return status;
	}
	@Override
	public void onProvinceAdded(Map<String, Province> provinces, Province prov) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnectionAdded(String indexProv, String indexAdj, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConnectionDelete(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onNationAdded(String nation) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProvinceEdited(Map<String, Province> provinces) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProvinceDeleted(Province prov, Map<String, Province> provinces) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRegister(Map<String, Province> provinces, Set<String> nations) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(Map<String, Province> provinces, Set<String> nations) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSelection(Province province) {
		// TODO Auto-generated method stub
		
	}
	
}
