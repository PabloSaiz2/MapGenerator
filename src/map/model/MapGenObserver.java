package map.model;

import java.util.List;

public interface MapGenObserver {
	void onProvinceAdded(List<Province> provinces);
	void onConnectionAdded(int indexProv,int indexAdj,int x1,int y1,int x2,int y2);
	void onNationAdded(String nation);
	void onError(String err);
}
