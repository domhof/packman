package at.ac.tuwien.foop.ui;

public class CanvasState {

    private int fieldSize;
    private int tileSize, pointSize;
    private int height;
    private int width;

    public CanvasState(int width, int height, int fieldSize, int tileSize, int pointSize){
    	
    	this.width = width; 
    	this.height = height; 
    	this.fieldSize = fieldSize;
    	this.tileSize = tileSize; 
    	this.pointSize = pointSize; 
    }

	public int getFieldSize() {
		return fieldSize;
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getPointSize() {
		return pointSize;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
    
    
    
    
}
