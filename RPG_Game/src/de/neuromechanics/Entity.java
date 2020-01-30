package de.neuromechanics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Entity {
	public static final int DEFAULT_WIDTH = 64;
	public static final int DEFAULT_HEIGHT = 64;

	protected String name;
	protected int entityX;
	protected int entityY;
	protected int width;
	protected int height;
	protected BufferedImage image;

	public Entity(String name, BufferedImage image, int x, int y, int width, int height) {
		this.name = name;
		this.image = image;
		this.entityX = x;
		this.entityY = y;
		this.width = width;
		this.height = height;
	}
	protected abstract void update();
	protected void render(Graphics g) {
		g.drawImage(image, entityX, entityY, null);
	}
	protected void setEntityImage(BufferedImage image) {
		this.image = image;
	}
	public int getEntityX() {
		return entityX;
	}
	public void setEntityX(int entityX) {
		this.entityX = entityX;
	}
	public int getEntityY() {
		return entityY;
	}
	public void setEntityY(int entityY) {
		this.entityY = entityY;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	
}