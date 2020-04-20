package map.model;

import java.util.ArrayList;
import java.util.List;

public class Province {
	private String name,state,owner;
	private int developmentBI,developmentC,developmentI,developmentO,developmentS,xCenter,yCenter,xStart,xEnd,yStart,yEnd;
	private List<Integer> adjacency;
	public Province(String name,String state,String owner,int developmentBI,int developmentC,int developmentI,int developmentO,int developmentS,int xCenter,int yCenter, int xStart,int xEnd,int yStart,int yEnd) {
		this.name = name;
		this.state = state;
		this.owner = owner;
		this.developmentBI = developmentBI;
		this.developmentC = developmentC;
		this.developmentI = developmentI;
		this.developmentO = developmentO;
		this.developmentS = developmentS;
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
	public String getDevelopmentString() {
		return ((Integer)developmentBI).toString()+" "+((Integer)developmentC).toString()+" "+((Integer)developmentI).toString()+" "+((Integer)developmentO).toString()+" "+((Integer)developmentS).toString();
	}
	public int getDevelopmentBI() {
		return developmentBI;
	}
	public void setDevelopmentBI(int development) {
		if(development<0)
			throw new IllegalArgumentException("Illegal development");
		this.developmentBI = development;
	}
	public int getDevelopmentC() {
		return developmentC;
	}
	public void setDevelopmentC(int development) {
		if(development<0)
			throw new IllegalArgumentException("Illegal development");
		this.developmentC = development;
	}
	public int getDevelopmentI() {
		return developmentI;
	}
	public void setDevelopmentI(int development) {
		if(development<0)
			throw new IllegalArgumentException("Illegal development");
		this.developmentI = development;
	}
	public int getDevelopmentO() {
		return developmentO;
	}
	public void setDevelopmentO(int development) {
		if(development<0)
			throw new IllegalArgumentException("Illegal development");
		this.developmentO = development;
	}
	public int getDevelopmentS() {
		return developmentS;
	}
	public void setDevelopmentS(int development) {
		if(development<0)
			throw new IllegalArgumentException("Illegal development");
		this.developmentS = development;
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
		if(index<0||adjacency.contains(index))
			throw new IllegalArgumentException("Illegal adjacency index");
		adjacency.add(getIndex(index), index);
	}
	public String getSizeString() {
		// TODO Auto-generated method stub
		return ((Integer)xCenter).toString()+" "+((Integer)yCenter).toString()
				+" "+((Integer)xStart).toString()+" "+((Integer)xEnd).toString()+" "
				+((Integer)yStart).toString()+" "+((Integer)yEnd).toString();
	}
	public boolean removeAdjacencyIndex(int index) {
		// TODO Auto-generated method stub
		boolean couldRemove = adjacency.remove((Integer)index);
		
		for(int i = 0;i<adjacency.size();++i) {
			if(adjacency.get(i)>index)
				adjacency.set(i, adjacency.get(i)-1);
				
		}
		return couldRemove;
	}
	public String toString() {
		return name;
	}
}
