package map.launcher;

import map.controller.Controller;
import map.model.MapGenerator;
import map.model.Province;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MapGenerator g = new MapGenerator();
		Controller c = new Controller(g);
		g.addProvince(new Province("Madrid","Castilla","PNE",50,100,200,0,1024,0,1024));
		Province prov = new Province("Barcelona","Catalonia","CDR",50,100,200,0,1024,0,1024);
		prov.addAdjacency(1);
		prov.addAdjacency(0);
		
		g.addProvince(prov);
		c.printProvinces();
	}

}
