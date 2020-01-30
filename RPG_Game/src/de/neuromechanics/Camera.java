package de.neuromechanics;

public class Camera {

	private int xOffset, yOffset;
	private int sizeX, sizeY;

	public Camera(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	public void centerOnEntity(final Entity e){
		xOffset = e.getEntityX() - Game.SCREEN_WIDTH / 2 + e.getWidth() / 2;
		if(xOffset < 0) {
			xOffset = 0;
		} else {
			int t = sizeX * TileSet.TILEWIDTH - (Game.SCREEN_WIDTH);
			if(xOffset > t) {
				xOffset = t;
			}
		}
		yOffset = e.getEntityY() - Game.SCREEN_HEIGHT / 2 + e.getHeight() / 2;
		if(yOffset < 0) {
			yOffset = 0;
		} else {
			int t = sizeY * TileSet.TILEHEIGHT - (Game.SCREEN_HEIGHT);
			if(yOffset > t) {
				yOffset = t;
			}
		}
	}

	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}
}