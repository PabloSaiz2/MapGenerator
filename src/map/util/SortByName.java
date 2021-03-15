package map.util;

import java.util.Comparator;

import map.model.Province;

public class SortByName implements Comparator<Province> {
	private boolean stringIsGreater(String a, String b) {
		boolean isSame = true;
		int i = 0;
		int length = Math.min(a.length(), b.length());
		boolean isGreater = false;
		while (isSame && i < length) {
			isSame = a.charAt(i) == b.charAt(i);
			if(!isSame) {
				isGreater = a.charAt(i)>b.charAt(i);
			}
			++i;
		}
		return isGreater;
	}

	@Override
	public int compare(Province arg0, Province arg1) {
		if (!stringIsGreater(arg0.getName(), arg1.getName()))
			return -1;
		else if (stringIsGreater(arg0.getName(), arg1.getName()))
			return 1;
		else
			return 0;
	}

}
