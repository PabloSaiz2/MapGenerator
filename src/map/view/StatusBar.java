package map.view;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;

import map.controller.Controller;
import map.model.MapGenObserver;
import map.model.Province;

public class StatusBar extends JPanel implements MapGenObserver{
	private JLabel msgLabel;
	public StatusBar(Controller ctrl) {
		msgLabel = new JLabel("Nothing to report");
		this.add(msgLabel);
		ctrl.addObserver(this);
	}
	@Override
	public void onProvinceAdded(Map<String,Province> provinces,Province prov) {
		// TODO Auto-generated method stub
		msgLabel.setText("Province added: "+prov.getName());
	}

	@Override
	public void onConnectionAdded(String indexProv, String indexAdj, int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNationAdded(String nation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(Map<String,Province> provinces, Set<String> nations) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(String err) {
		msgLabel.setText(err);
		
	}
	@Override
	public void onRegister(Map<String,Province> provinces, Set<String> nations) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProvinceDeleted(Province prov,Map<String,Province> provinces) {
		// TODO Auto-generated method stub
		msgLabel.setText("Province deleted: " +prov.getName());
	}
	@Override
	public void onConnectionDelete(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onProvinceEdited(Map<String,Province> provinces) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSelection(Province province) {
		// TODO Auto-generated method stub
		
	}

}
