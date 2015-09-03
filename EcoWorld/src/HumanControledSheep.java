import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;


public class HumanControledSheep extends Animal implements KeyListener{
	
	private static final ImageIcon image = new ImageIcon("src\\img\\sheep.gif");

	
	public HumanControledSheep(Pasture pasture, int hunger)
	{
		this.pasture = pasture;
		this.moving = true;
		this.hunger = hunger;
		this.moveDelay = 2;
		this.resetHunger = hunger;
		resetMovement = moveDelay;
		this.notDead = true;	
		
	}
	@Override
	public void tick()
	{
		if (this.notDead)
		{
		eat();
		die();
		}
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
		return seeingDistance;
	}

	@Override
	public Entity createNew() {

		return null;
	}

	@Override
	public boolean seeEnemy(Entity otherEntity) {

			return false;
	}

	@Override
	public boolean isCompatible(Entity otherEntity) {
	
		if ((otherEntity instanceof Wolf) || (otherEntity instanceof Plant || otherEntity instanceof Secret))
			return true;
		else 
			return false;
	}
	
	@Override
	public boolean canEat(Entity otherEntity)
	{
		return (otherEntity instanceof Plant) ? true : false;
	}
	
	public void keyPressed(KeyEvent e)
	{
		
	    if (e.getKeyCode() == KeyEvent.VK_LEFT){
	    	pasture.moveEntity(this, new Point(15,15));
	    System.out.print("nåttt händer");
	    }
	    else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
	    	pasture.moveEntity(this, new Point(6,6));
	    else if (e.getKeyCode() == KeyEvent.VK_UP)
	    	pasture.moveEntity(this, new Point(7,7));
	    else if (e.getKeyCode() == KeyEvent.VK_DOWN)
	    	pasture.moveEntity(this, new Point(8,8));
	}	
	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void moveThis()
	{
		moveDelay--;
		
		if (moveDelay <= 0)
		{
			
			
			resetMovement();
		
		}	
		
	}
	
}
