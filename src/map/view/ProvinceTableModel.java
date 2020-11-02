package map.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import map.controller.Controller;
import map.model.MapGenObserver;
import map.model.Province;

public class ProvinceTableModel extends AbstractTableModel implements MapGenObserver{

	private List<Province> provinces;
	private String [] colNames = new String[] {"Num","Name","State","Owner","Resource","Center&Bounds","Development","Adjacency"};
	public ProvinceTableModel(Controller ctrl) {
		provinces = new ArrayList<Province>();
		ctrl.addObserver(this);
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return colNames.length;
	}
	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return provinces.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		// TODO Auto-generated method stub
		switch(col) {
			case 0:
				return ((Integer)row).toString();
			case 1:
				return provinces.get(row).getName();
			case 2:
				return provinces.get(row).getState();
			case 3:
				return provinces.get(row).getOwner();
			case 4:
				return provinces.get(row).getLocalResource();
			case 5:
				return provinces.get(row).getSizeString();
			case 6:
				return provinces.get(row).getDevelopmentString();
			case 7:
				return provinces.get(row).getAdjacencyString();
			default:
				return null;
		}
		
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
		this.provinces = new ArrayList<Province>(provinces.values());
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
