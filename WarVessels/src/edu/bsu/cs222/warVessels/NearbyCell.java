package edu.bsu.cs222.warVessels;

public enum NearbyCell {

	NORTH(0, -1),
	EAST(1, 0),
	SOUTH(0, 1),
	WEST(-1, 0);
	
	private int x;
	private int y;
	
	private NearbyCell(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int newY(int oldY){
		return oldY + y;
	}
	
	public int newX(int oldX){
		return oldX + x;
	}
}
