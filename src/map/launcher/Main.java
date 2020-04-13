package map.launcher;

import map.controller.Controller;
import map.model.MapGenerator;
import map.model.Province;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MapGenerator g = new MapGenerator();
		Controller c = new Controller(g);
		c.loadFromFile("resources/output/provinces.txt");
		c.printProvinces();
	}

}
