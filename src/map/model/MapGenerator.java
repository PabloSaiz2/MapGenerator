package map.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import map.util.Pair;

public class MapGenerator implements Observable<MapGenObserver> {

	private List<MapGenObserver> observers;
	private List<Province> provinces;
	private Set<String> nations;

	public MapGenerator() {
		nations = new HashSet<String>();
		provinces = new ArrayList<Province>();
		observers = new ArrayList<MapGenObserver>();
	}

	public void reset() {
		nations.clear();
		provinces.clear();
		for (MapGenObserver observer : observers)
			observer.onReset(provinces, nations);
	}

	public void addAdjacencyProvince(int indexProv, int indexAdj) {
		if (indexProv < 0 || indexProv >= provinces.size())
			throw new IllegalArgumentException("Invalid index");
		provinces.get(indexProv).addAdjacency(indexAdj);
		for (MapGenObserver observer : observers)
			observer.onConnectionAdded(indexProv, indexAdj, provinces.get(indexProv).getxCenter(),
					provinces.get(indexProv).getyCenter(), provinces.get(indexAdj).getxCenter(),
					provinces.get(indexAdj).getyCenter());
	}

	public void addNation(String nation) {
		nations.add(nation);
		for (MapGenObserver observer : observers)
			observer.onNationAdded(nation);
	}

	public List<Province> getProvinces() {
		List<Province> provincesList = new ArrayList<Province>();
		for (Province prov : provinces)
			provincesList.add(prov);
		return provincesList;
	}

	public void addProvince(Province prov) {
		provinces.add(prov);
		for (MapGenObserver observer : observers)
			observer.onProvinceAdded(provinces, prov);
	}

	public void removeProvince(int index) {
		
		for (MapGenObserver observer : observers)
			observer.onProvinceDeleted(provinces.get(index),provinces);
		
		List<Pair<Integer, Integer>> points = new ArrayList<Pair<Integer, Integer>>();

		for (Province prov : provinces) {
			if (prov.removeAdjacencyIndex(index)) {
				Pair<Integer, Integer> point = new Pair<Integer, Integer>(prov.getxCenter(), prov.getyCenter());
				points.add(point);
			}
			
		}
		for (MapGenObserver observer : observers) {
			for (Pair<Integer, Integer> point : points) {
				observer.onConnectionDelete(provinces.get(index).getxCenter(),provinces.get(index).getyCenter(), point.getFirst(), point.getSecond());
			}
		}
		provinces.remove(index);
	}

	@Override
	public void addObserver(MapGenObserver o) {
		// TODO Auto-generated method stub
		observers.add(o);
		o.onRegister(provinces, nations);
	}

	@Override
	public void removeObserver(MapGenObserver o) {
		// TODO Auto-generated method stub
		observers.remove(o);
	}

	public void notifyError(String message) {
		// TODO Auto-generated method stub
		for (MapGenObserver observer : observers)
			observer.onError(message);
	}

	public int size() {
		// TODO Auto-generated method stub
		return provinces.size();
	}

	public void setProvince(int index, Province province) {
		// TODO Auto-generated method stub
		for(MapGenObserver observer:observers)
			observer.onProvinceEdited(provinces);
		provinces.set(index, province);
	}

}
