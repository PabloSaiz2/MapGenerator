package map.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import map.model.Data;
import map.model.MapGenObserver;
import map.model.MapGenerator;
import map.model.Province;
import map.parsers.FileParser;
import map.util.Pair;

public class Controller {
	private MapGenerator gen;
	public Controller(MapGenerator generator) {
		gen = generator;
		loadData();
	}
	public void addProvince(Province prov) {
		gen.addProvince(prov);
	}
	public void removeProvince(String index) {
		try {
			gen.removeProvince(index);
		}
		catch(IndexOutOfBoundsException ex) {
			gen.notifyError("Province index out of bounds");
		}
	}
	public void addObserver(MapGenObserver o) {
		gen.addObserver(o);
	}
	public void removeObserver(MapGenObserver o) {
		gen.removeObserver(o);
	}
	public void addAdjacency(String indexProv,String indexAdj) {
		try {
		gen.addAdjacencyProvince(indexProv, indexAdj);
		}
		catch(IllegalArgumentException ex) {
			gen.notifyError(ex.getMessage());
		}
	}
	public void loadData() {
		try(FileInputStream in = new FileInputStream(new File("resources/data/data.csv")); InputStreamReader inReader = new InputStreamReader(in,StandardCharsets.UTF_8);BufferedReader reader = new BufferedReader(inReader)){
			System.out.println(reader.readLine());
			int provincesDataNum = Integer.parseInt("1561");
			for(int i = 0;i<provincesDataNum;++i) {
				String name,resource;
				int[] development = new int[5];
				for(int m = 0;m<5;++m) {
					development[m]=0;
				}
				String dataLine = reader.readLine();
				String [] data =dataLine.split(";");
				if(data.length==11) {
					name = data[2].trim();
					resource = data[3].trim();
					for(int j = 4;j<9;++j) {
						if(!data[j].trim().equals(""))
							development[j-4]= Integer.parseInt(data[j]);
					}
					gen.addData(new Data(name,resource,development));
				}
			}
		}catch(IOException|IllegalArgumentException ex) {
			ex.printStackTrace();
			gen.notifyError("Error loading file");
		}
	}
	public void addNation(String nation) {
		gen.addNation(nation);
	}
	public void loadFromFile(File file) {
		try(FileInputStream in = new FileInputStream(file); InputStreamReader inReader = new InputStreamReader(in,StandardCharsets.UTF_8);BufferedReader reader = new BufferedReader(inReader)){
			Pair<List<Province>,List<Pair<String,String>>> provincesAndConnections = FileParser.parseFile(reader);
			List<Province> provinces = provincesAndConnections.getFirst();
			for(Province prov :provinces) {
				gen.addProvince(prov);
			}
			List<Pair<String,String>> connections = provincesAndConnections.getSecond();
			for(Pair<String,String> connection:connections)
				gen.addAdjacencyProvince(connection.getFirst(), connection.getSecond());
		}catch(IOException|IllegalArgumentException ex) {
			ex.printStackTrace();
			gen.notifyError("Error loading file");
		}
	}
	public void printProvinces() {
		try(OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(new File("resources/output/provinces.txt")),StandardCharsets.UTF_8)){
			BufferedWriter p = new BufferedWriter(stream);
			p.write(((Integer)(gen.size())).toString());
			p.newLine();
			for(Province prov :gen.getProvinces()) {
				if(!prov.isInHE()) {
					p.write(prov.getName());
					p.newLine();
				}
				else {
					p.write(prov.getName()+"_HE");
					p.newLine();
				}
				p.write(prov.getState());
				p.newLine();
				p.write(prov.getOwner());
				p.newLine();
				p.write(prov.getLocalResource());
				p.newLine();
				p.write(prov.getSizeString());
				p.newLine();
				p.write(prov.getAdjacencyString());
				p.newLine();
				p.write(prov.getDevelopmentString());
				p.newLine();
			}
			p.close();
		}catch(IOException ex) {
			System.out.println("Error output file");
			gen.notifyError("Error output file");
		}

	}
	public void reset() {
		// TODO Auto-generated method stub
		gen.reset();
	}
	public void setProvince(int index, Province province) {
		// TODO Auto-generated method stub
		try {
			gen.setProvince(index,province);
		}
		catch(IllegalArgumentException ex)
		{
			gen.notifyError("Error setting province");
		}
		
	}
	public void connectProvinces(int x1, int y1, int x2, int y2) {
		gen.connectProvinces(x1,y1,x2,y2);
		
	}
	public void changeOwner(String state, String owner) {
		// TODO Auto-generated method stub
		gen.changeStateOwner(state,owner);
	}
	public void setSelectedProvince(String selectedIndex) {
		// TODO Auto-generated method stub
		gen.setSelectedProvince(selectedIndex);
	}
	public void loadDataToProvince(String provToChange, String dataName) {
		// TODO Auto-generated method stub
		gen.loadData(provToChange,dataName);
	}
	public Province getCloserProvinceTo(int x, int y) {
		return gen.getClosestProv(x,y);
	}
}
