package map.model;

import java.util.List;

public interface MapGenObserver {
	void onProvinceAdded(List<Province> provinces);
	void onNationAdded(String nation);
	void onError(String err);
}
