package map.model;

import java.util.ArrayList;
import java.util.List;

public class Province {
	private String name,state,owner;
	private int development,xCenter,yCenter,xStart,xEnd,yStart,yEnd;
	private List<Integer> adjacency;
	public Province(String name,String state,String owner,int development,int xCenter,int yCenter, int xStart,int xEnd,int yStart,int yEnd) {
		this.name = name;
		this.state = state;
		this.owner = owner;
		this.development = development;
		this.xCenter = xCenter;
		this.yCenter = yCenter;
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		adjacency = new ArrayList<Integer>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public int getDevelopment() {
		return development;
	}
	public void setDevelopment(int development) {
		if(development<0)
			throw new IllegalArgumentException("Illegal development");
		this.development = development;
	}
	public int getxCenter() {
		return xCenter;
	}
	public void setxCenter(int xCenter) {
		if(xCenter<0)
			throw new IllegalArgumentException("Illegal development");
		this.xCenter = xCenter;
	}
	public int getyCenter() {
		return yCenter;
	}
	public void setyCenter(int yCenter) {
		if(yCenter<0)
			throw new IllegalArgumentException("Illegal development");
		this.yCenter = yCenter;
	}
	public int getxStart() {
		return xStart;
	}
	public void setxStart(int xStart) {
		if(xStart<0)
			throw new IllegalArgumentException("Illegal development");
		this.xStart = xStart;
	}
	public int getxEnd() {
		return xEnd;
	}
	public void setxEnd(int xEnd) {
		if(xEnd<0)
			throw new IllegalArgumentException("Illegal development");
		this.xEnd = xEnd;
	}
	public int getyStart() {
		return yStart;
	}
	public void setyStart(int yStart) {
		if(yStart<0)
			throw new IllegalArgumentException("Illegal development");
		this.yStart = yStart;
	}
	public int getyEnd() {
		return yEnd;
	}
	public void setyEnd(int yEnd) {
		if(yEnd<0)
			throw new IllegalArgumentException("Illegal development");
		this.yEnd = yEnd;
	}
	public String getAdjacencyString() {
		String output = "";
		for(Integer index:adjacency) {
			output += index.toString()+" ";
		}
		return output.trim();
	}
	private int getIndex(int adjacencyIndex) {
		int i = 0;
		while(i<adjacency.size()&&adjacencyIndex>adjacency.get(i)) {
			++i;
		}
		return i;
	}
	public void addAdjacency(int index) {
		if(index<0)
			throw new IllegalArgumentException("Illegal adjacency index");
		adjacency.add(getIndex(index), index);
	}
	public String getSizeString() {
		// TODO Auto-generated method stub
		return ((Integer)xCenter).toString()+" "+((Integer)yCenter).toString()
				+" "+((Integer)xStart).toString()+" "+((Integer)xEnd).toString()+" "
				+((Integer)yStart).toString()+" "+((Integer)yEnd).toString();
	}
}
