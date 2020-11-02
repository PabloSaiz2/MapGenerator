package map.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import map.util.Pair;

public class MapGenerator implements Observable<MapGenObserver> {

	private List<MapGenObserver> observers;
	private Map<String, Province> provinces;
	
	private List<Data> data;
	private Set<String> nations;

	public MapGenerator() {
		nations = new HashSet<String>();
		data = new ArrayList<Data>();
		
		provinces = new HashMap<String, Province>();
		observers = new ArrayList<MapGenObserver>();
	}

	public void reset() {
		nations.clear();
		provinces.clear();
		for (MapGenObserver observer : observers)
			observer.onReset(provinces, nations);
	}

	public void addAdjacencyProvince(String indexProv, String indexAdj) {
			

		try {
			
			provinces.get(indexProv).addAdjacency(provinces.get(indexAdj).getName());
			provinces.get(indexAdj).addAdjacency(provinces.get(indexProv).getName());

			for (MapGenObserver observer : observers)
				observer.onConnectionAdded(indexProv, indexAdj, provinces.get(indexProv).getxCenter(),
						provinces.get(indexProv).getyCenter(), provinces.get(indexAdj).getxCenter(),
						provinces.get(indexAdj).getyCenter());
		} catch ( NullPointerException ex) {
			System.out.println("Error when connecting provinces provinces:"+indexProv+" and "+indexAdj);
			for (MapGenObserver observer : observers)
				observer.onError("Error when connecting provinces provinces:"+indexProv+" and "+indexAdj);
		}
		catch(IllegalArgumentException ex) {
			//for (MapGenObserver observer : observers)
				//observer.onError("Error when connecting provinces provinces:"+indexProv+" and "+indexAdj);
		}
	}

	public void addNation(String nation) {
		nations.add(nation);
		for (MapGenObserver observer : observers)
			observer.onNationAdded(nation);
	}

	public List<Province> getProvinces() {
		List<Province> provincesList = new ArrayList<Province>();
		for (Province prov : provinces.values())
			provincesList.add(prov);
		return provincesList;
	}

	public void addProvince(Province prov) {
		provinces.put(prov.getName(), prov);
		for (MapGenObserver observer : observers)
			observer.onProvinceAdded(provinces, prov);
	}

	public void removeProvince(String adj) {

		for (MapGenObserver observer : observers)
			observer.onProvinceDeleted(provinces.get(adj), provinces);

		List<Pair<Integer, Integer>> points = new ArrayList<Pair<Integer, Integer>>();

		for (Province prov : provinces.values()) {
			if (prov.removeAdjacencyIndex(provinces.get(adj).getName())) {
				Pair<Integer, Integer> point = new Pair<Integer, Integer>(prov.getxCenter(), prov.getyCenter());
				points.add(point);
			}

		}
		for (MapGenObserver observer : observers) {
			for (Pair<Integer, Integer> point : points) {
				observer.onConnectionDelete(provinces.get(adj).getxCenter(), provinces.get(adj).getyCenter(),
						point.getFirst(), point.getSecond());
			}
		}
		provinces.remove(adj);
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
		for (MapGenObserver observer : observers)
			observer.onProvinceEdited(provinces);
		provinces.put(province.getName(), province);
	}

	private String getIndexClosest(int x, int y) {

		String closest = "";
		double distance = Double.MAX_VALUE;
		for (Province prov : provinces.values()) {
			double dist = Math.sqrt(Math.pow((prov.getxCenter() - x), 2) + Math.pow((prov.getyCenter() - y), 2));
			if (dist < distance) {
				closest = prov.getName();
				distance = dist;
			}
		}
		return closest;
	}

	public void connectProvinces(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		if (provinces.size() >= 2) {
			this.addAdjacencyProvince(getIndexClosest(x1, y1), getIndexClosest(x2, y2));
		}
	}

	private List<Province> getProvincesInState(String state) {
		List<Province> output = new ArrayList<Province>();
		for (Province prov : provinces.values()) {
			if (prov.getState().trim().equals(state))
				output.add(prov);
		}
		return output;
	}

	public void changeStateOwner(String state, String owner) {
		// TODO Auto-generated method stub
		List<Province> provincesInState = getProvincesInState(state);
		for (Province prov : provincesInState) {
			prov.setOwner(owner);
		}
	}

	public void setSelectedProvince(int selectedIndex) {
		// TODO Auto-generated method stub
		if (selectedIndex != -1) {
			for (MapGenObserver observer : observers) {
				observer.onSelection(provinces.get(selectedIndex));
			}
		} else {
			for (MapGenObserver observer : observers) {
				observer.onSelection(null);
			}
		}

	}

	public void addData(Data data) {
		// TODO Auto-generated method stub
		this.data.add(data);
	}

	public Province getClosestProv(int x, int y) {
		// TODO Auto-generated method stub
		return provinces.get(this.getIndexClosest(x, y));
	}

	private Data findData(String name) {
		Data dat = null;
		boolean found = false;
		int i = 0;
		while (i < data.size() && !found) {
			if (data.get(i).getName().equals(name)) {
				found = true;
				dat = data.get(i);
			}
			++i;
		}
		return dat;
	}

	public void loadData(String provToChange, String dataName) {
		// TODO Auto-generated method stub
		Province prov = provinces.get(provToChange);
		Data data = findData(dataName);
		try {
			prov.setName(data.getName());
			prov.setLocalResource(data.getResource());
			prov.setDevelopmentBI(data.getDevelopment()[0]);
			prov.setDevelopmentC(data.getDevelopment()[1]);
			prov.setDevelopmentI(data.getDevelopment()[2]);
			prov.setDevelopmentO(data.getDevelopment()[3]);
			prov.setDevelopmentS(data.getDevelopment()[4]);
		} catch (NullPointerException ex) {
			for (MapGenObserver ob : observers) {
				ob.onError("The data " + dataName + " could not be found");
			}
		}
		provinces.remove(provToChange);
		provinces.put(dataName, prov);
		for (String connection : prov.getAdjacencyString().split(";")) {
			System.out.println(connection);
			provinces.get(connection).removeAdjacencyIndex(provToChange);
			provinces.get(connection).addAdjacency(dataName);
		}
	}

}
