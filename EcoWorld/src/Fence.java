import java.awt.event.KeyListener;

import javax.swing.ImageIcon;


public class Fence implements Entity {
	private static final ImageIcon image = new ImageIcon("src\\img\\fence.gif");
	private boolean moving;
	private final Pasture pasture;
	
	public Fence(Pasture pasture, boolean moving)
	{
		this.pasture = pasture;
		this.moving = moving;
		
	}
	@Override
	public void tick() {
		// fence gör inget vid tick
		
	}

	@Override
	public ImageIcon getImage() {
		// TODO Auto-generated method stub
		return image;
	}

	@Override
	public boolean isCompatible(Entity otherEntity) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
}
