package map.parsers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import map.model.Province;
import map.util.Pair;

public class FileParser {
	private static List<Integer> parseSize(String line) {
		List<Integer> size = new ArrayList<Integer>();
		String[] sizes = line.trim().split(" ");
		for (String sizeString : sizes)
			size.add(Integer.parseInt(sizeString));
		return size;
	}

	public static Pair<List<Province>, List<Pair<String, String>>> parseFile(BufferedReader input)
			throws NumberFormatException, IOException {
		int numProvinces;
		numProvinces = Integer.parseInt(input.readLine());
		List<Province> provinces = new ArrayList<Province>();
		List<Pair<String, String>> connections = new ArrayList<Pair<String, String>>();
		for (int i = 0; i < numProvinces; ++i) {
			String name;
			String state;
			String owner;
			String resource;
			String sizeString;
			int xCenter = 0, yCenter = 0, xStart = 0, yStart = 0, xEnd = 0, yEnd = 0;

			String connectionsString;
			String developmentString;
			name = input.readLine();
			state = input.readLine();
			owner = input.readLine();
			resource = input.readLine();
			sizeString = input.readLine();
			//System.out.println(name);
			List<Integer> parsedSizes = parseSize(sizeString);
			if (parsedSizes.size() == 6) {
				xCenter = parsedSizes.get(0);
				yCenter = parsedSizes.get(1);
				xStart = parsedSizes.get(2);
				xEnd = parsedSizes.get(3);
				yStart = parsedSizes.get(4);
				yEnd = parsedSizes.get(5);
			} else
				throw new IllegalArgumentException("Sizes string is wrong");

			connectionsString = input.readLine();
			List<Pair<String, String>> parsedConnections;
			if(name.split("_").length==2&&name.split("_")[1].equals("HE"))
				parsedConnections = parseConnections(connectionsString, name.split("_")[0]);
			else
				parsedConnections = parseConnections(connectionsString, name);
			for (Pair<String, String> connection : parsedConnections)
				connections.add(connection);
			developmentString = input.readLine();
			int devBI, devC, devI, devO, devS;
			List<Integer> parsedDevelopments = parseDevelopments(developmentString);
			if (parsedDevelopments.size() == 5) {
				devBI = parsedDevelopments.get(0);
				devC = parsedDevelopments.get(1);
				devI = parsedDevelopments.get(2);
				devO = parsedDevelopments.get(3);
				devS = parsedDevelopments.get(4);
			} else
				throw new IllegalArgumentException("Development string is wrong");
			String[] nameSplitted = name.split("_");
			if (nameSplitted.length == 2 && nameSplitted[1].equals("HE")) {
				Province currentProv = new Province(nameSplitted[0], state, owner, resource, devBI, devC, devI, devO,
						devS, xCenter, yCenter, xStart, xEnd, yStart, yEnd, true);
				provinces.add(currentProv);
			} else {
				Province currentProv = new Province(name, state, owner, resource, devBI, devC, devI, devO, devS,
						xCenter, yCenter, xStart, xEnd, yStart, yEnd, false);
				provinces.add(currentProv);
			}

		}
		Pair<List<Province>, List<Pair<String, String>>> provincesAndConnections = new Pair<List<Province>, List<Pair<String, String>>>(
				provinces, connections);
		return provincesAndConnections;
	}

	private static List<Integer> parseDevelopments(String line) {
		// TODO Auto-generated method stub
		List<Integer> developments = new ArrayList<Integer>();
		String[] sizes = line.trim().split(" ");
		for (String development : sizes)
			developments.add(Integer.parseInt(development));
		return developments;
	}

	private static List<Pair<String, String>> parseConnections(String line, String currentProv) {
		List<Pair<String, String>> connections =new ArrayList<Pair<String, String>>();
		if (!line.trim().equals("")) {
		   
			String[] connectionsStrings = line.trim().split(";");
			for (String indexAdj : connectionsStrings) {
				connections.add(new Pair<String, String>(currentProv, indexAdj));
			}
		}
		return connections;
	}

}
