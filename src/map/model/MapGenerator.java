package map.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MapGenerator implements Observable<MapGenObserver>{

	private List<MapGenObserver> observers;
	private List<Province> provinces;
	private Set<String> nations;
	public MapGenerator() {
		nations = new HashSet<String>();
		provinces = new ArrayList<Province>();
		observers = new ArrayList<MapGenObserver>();
	}
	public void addAdjacencyProvince(int indexProv,int indexAdj) {
		if(indexProv<0||indexProv>=provinces.size())
			throw new IllegalArgumentException("Invalid index");
		provinces.get(indexProv).addAdjacency(indexAdj);
	}
	public void addNation(String nation) {
		nations.add(nation);
		for(MapGenObserver observer:observers)
			observer.onNationAdded(nation);
	}
	public List<Province> getProvinces(){
		List<Province> provincesList = new ArrayList<Province>();
		for(Province prov :provinces)
			provincesList.add(prov);
		return provincesList;
	}
	public void addProvince(Province prov) {
		provinces.add(prov);
		for(MapGenObserver observer:observers)
			observer.onProvinceAdded(provinces);
	}
	
	public void removeProvince(int index) {
		provinces.remove(index);
	}
	@Override
	public void addObserver(MapGenObserver o) {
		// TODO Auto-generated method stub
		observers.add(o);
	}

	@Override
	public void removeObserver(MapGenObserver o) {
		// TODO Auto-generated method stub
		observers.remove(o);
	}
	public void notifyError(String message) {
		// TODO Auto-generated method stub
		for(MapGenObserver observer:observers)
			observer.onError(message);
	}
	public int size() {
		// TODO Auto-generated method stub
		return provinces.size();
	}

}
