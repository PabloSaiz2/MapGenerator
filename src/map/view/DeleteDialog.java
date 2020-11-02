package map.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import map.controller.Controller;
import map.model.MapGenObserver;
import map.model.Province;

public class DeleteDialog extends JDialog implements MapGenObserver{

	private JComboBox provinces;
	private DialogStatus status;
	public DeleteDialog(JFrame ventana,Controller ctrl) {
		super(ventana,"Province deleter",true);
		status = DialogStatus.WAITING;
		provinces = new JComboBox();
		ctrl.addObserver(this);
		this.add(provinces,BorderLayout.LINE_START);
		JButton ok = new JButton("Delete");
		ok.setToolTipText("Deletes the province");
		ok.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(provinces.getSelectedItem()!=null) {
					status = DialogStatus.ACCEPTED;
					dispose();
				}
			}
			
		});
		this.add(ok,BorderLayout.LINE_END);
		this.setPreferredSize(new Dimension(200,100));
		this.pack();
		this.setVisible(true);
		
		
	}
	public String getName() {
		return (String)provinces.getSelectedItem();
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
		for(Province prov:provinces.values()) {
			this.provinces.addItem(prov.getName());
		}
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
