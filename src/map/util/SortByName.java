package map.util;

import java.util.Comparator;

import map.model.Province;

public class SortByName implements Comparator<Province> {
	private boolean stringIsLess(String a, String b) {
		boolean isLess = true;
		int i = 0;
		if (a.length() == b.length()) {
			while (isLess && i < a.length()) {
				isLess = a.charAt(i) < b.charAt(i);
				++i;
			}
		} else if (a.length() > b.length()) {

			isLess = false;
		}
		return isLess;
	}

	private boolean stringIsGreater(String a, String b) {
		boolean isGreater = true;
		int i = 0;
		if (a.length() == b.length()) {
			while (isGreater && i < a.length()) {
				isGreater = a.charAt(i) > b.charAt(i);
				++i;
			}
		} else if (a.length() < b.length()) {

			isGreater = false;
		}
		return isGreater;
	}

	@Override
	public int compare(Province arg0, Province arg1) {
		if (stringIsLess(arg0.getName(), arg1.getName()))
			return -1;
		else if (stringIsGreater(arg0.getName(), arg1.getName()))
			return 1;
		else
			return 0;
	}

}
