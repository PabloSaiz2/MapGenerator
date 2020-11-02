package map.model;

public class Data {
	String name,resource;
	int [] development;
	public Data(String name, String resource, int [] development) {
		this.name = name;
		this.resource = resource;
		this.development = development;
	}
	public String getName() {
		return name;
	}
	public String getResource() {
		return resource;
	}
	public int[] getDevelopment() {
		return development;
	}
	
}
