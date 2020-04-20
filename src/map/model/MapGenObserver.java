package map.model;

import java.util.List;
import java.util.Set;

public interface MapGenObserver {
	void onProvinceAdded(List<Province> provinces,Province prov);
	void onConnectionAdded(int indexProv,int indexAdj,int x1,int y1,int x2,int y2);
	void onConnectionDelete(int x1,int y1,int x2,int y2);
	void onNationAdded(String nation);
	void onProvinceEdited(List<Province> provinces);
	void onProvinceDeleted(Province prov,List<Province> provinces);
	void onRegister(List<Province> provinces,Set<String> nations);
	void onReset(List<Province> provinces,Set<String> nations);
	void onError(String err);
}
