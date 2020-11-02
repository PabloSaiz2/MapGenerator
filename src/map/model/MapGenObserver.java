package map.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MapGenObserver {
	void onProvinceAdded(Map<String,Province> provinces,Province prov);
	void onConnectionAdded(String indexProv,String indexAdj,int x1,int y1,int x2,int y2);
	void onConnectionDelete(int x1,int y1,int x2,int y2);
	void onNationAdded(String nation);
	void onProvinceEdited(Map<String,Province> provinces);
	void onProvinceDeleted(Province prov,Map<String,Province> provinces);
	void onRegister(Map<String,Province> provinces,Set<String> nations);
	void onReset(Map<String,Province> provinces,Set<String> nations);
	void onError(String err);
	void onSelection(Province province);
}
