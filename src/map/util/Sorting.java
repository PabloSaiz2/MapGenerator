package map.util;

import java.util.ArrayList;
import java.util.List;

import map.model.Province;

public class Sorting {
	private static boolean stringIsLess(String a, String b) {
		boolean isLess = true;
		int i = 0;
		while (isLess && i < Math.min(a.length(), b.length())) {
			isLess = a.charAt(i) <= b.charAt(i);
			++i;
		}
		return isLess;
	}

	private static void merge(int init, int middle, int fin, List<Province> sorting) {
		int i = 0;
		int j = 0;
		int s = init;
		List<Province> tempA = new ArrayList<Province>();
		List<Province> tempB = new ArrayList<Province>();
		for (int r = 0; r < middle-init+1; ++r) {
			tempA.add(sorting.get(init+r));
		}
		for (int r = 0; r < fin-middle; ++r) {
			tempB.add(sorting.get(middle+1+r));
		}
		while (i < middle-init+1 && j < fin-middle) {
			if (stringIsLess(tempA.get(i).getName().toLowerCase(), tempB.get(j).getName().toLowerCase())) {

				sorting.set(s, tempA.get(i));
				++i;
			} 
			else {
				sorting.set(s, tempB.get(j));
				++j;
			}
			++s;

		}
		
			for (int r = i; r < middle-init+1; ++r) {
				sorting.set(s, tempA.get(r));
				++s;
			}
	
			for (int r = j; r < fin-middle; ++r) {
				sorting.set(s, tempB.get(r));
				++s;
			}
		
	}
	
	public static void mergeSort(int init, List<Province> sorting, int fin) {
		if (init < fin) {
			int middle = (init + fin) / 2;
			mergeSort(init, sorting, middle);
			mergeSort(middle + 1, sorting, fin);
			merge(init, middle, fin, sorting);
		}
		
	}
}
