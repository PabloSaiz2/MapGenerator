package map.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import map.model.MapGenObserver;
import map.model.MapGenerator;
import map.model.Province;
import map.parsers.FileParser;
import map.util.Pair;

public class Controller {
	private MapGenerator gen;
	public Controller(MapGenerator generator) {
		gen = generator;
	}
	public void addProvince(Province prov) {
		gen.addProvince(prov);
	}
	
	public void addObserver(MapGenObserver o) {
		gen.addObserver(o);
	}
	public void removeObserver(MapGenObserver o) {
		gen.removeObserver(o);
	}
	public void addNation(String nation) {
		gen.addNation(nation);
	}
	public void loadFromFile(String filepath) {
		try(FileReader in = new FileReader(new File(filepath)); BufferedReader reader = new BufferedReader(in)){
			Pair<List<Province>,List<Pair<Integer,Integer>>> provincesAndConnections = FileParser.parseFile(reader);
			List<Province> provinces = provincesAndConnections.getFirst();
			for(Province prov :provinces)
				gen.addProvince(prov);
			List<Pair<Integer,Integer>> connections = provincesAndConnections.getSecond();
			for(Pair<Integer,Integer> connection:connections)
				gen.addAdjacencyProvince(connection.getFirst(), connection.getSecond());
		}catch(IOException|IllegalArgumentException ex) {
			ex.printStackTrace();
			gen.notifyError("Error loading file");
		}
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
			gen.notifyError("Error output file");
		}
	}
}
