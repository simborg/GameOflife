import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;


public class Wolf extends Animal{
	
	private static final ImageIcon image = new ImageIcon("src\\img\\wolf.gif");

	
	
	public Wolf(Pasture pasture, int spawn, int hunger)
	{
		this.pasture = pasture;
		this.moving = true;
		this.spawn = spawn; 
		this.hunger = hunger;
		this.moveDelay = 10;
		this.resetSpawn = spawn;
		this.resetHunger = hunger;
		this.resetMovement = moveDelay;
		this.notDead = true;
		this.seeingDistance = 3;

		this.lastX = 1;
		this.lastY = 1;
	}

	@Override
	public void resetMovement()
	{
		this.moveDelay = resetMovement; 
	}
	@Override
	public void resetHunger()
	{
		this.hunger = resetHunger;
	}
	@Override
	public ImageIcon getImage()
	{
		return image;
	}
	
	@Override
	public int getDistance() {
		// TODO Auto-generated method stub
		return seeingDistance;
	}

	@Override
	public Entity createNew() {
		this.spawn = this.resetSpawn; 
		return new Wolf(pasture, resetSpawn, resetHunger);
	}

	@Override
	public boolean seeEnemy(Entity otherEntity) {
			return false;
	}

	@Override
	public boolean isCompatible(Entity otherEntity) {
		if ((otherEntity instanceof Sheep) || (otherEntity instanceof Plant)) 
			return true;
		else 
			return false;
	}
	@Override
	public boolean canEat(Entity otherEntity)
	{
		return (otherEntity instanceof Sheep) ? true : false;
			
	}

}
