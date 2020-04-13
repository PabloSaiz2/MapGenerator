package map.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import map.model.MapGenerator;
import map.model.Province;

public class Controller {
	private MapGenerator gen;
	public Controller(MapGenerator generator) {
		gen = generator;
	}
	public void addProvince(Province prov) {
		gen.addProvince(prov);
	}
	public void addNation(String nation) {
		gen.addNation(nation);
	}
	public void printProvinces() {
		try(FileOutputStream stream = new FileOutputStream(new File("resources/output/provinces.txt"))){
			PrintStream p = new PrintStream(stream);
			p.println(gen.size());
			for(Province prov :gen.getProvinces()) {
				p.println(prov.getName());
				p.println(prov.getState());
				p.println(prov.getOwner());
				p.println(prov.getSizeString());
				p.println(prov.getAdjacencyString());
				p.println(prov.getDevelopment());
			}
		}catch(IOException ex) {
			System.out.println("Error output file");
			gen.notifyError(ex.getMessage());
		}
	}
}
