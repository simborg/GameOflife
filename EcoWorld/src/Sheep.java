import javax.swing.ImageIcon;


public class Sheep extends Animal{
	
	private static final ImageIcon image = new ImageIcon("src\\img\\sheep.gif");

	
	public Sheep(Pasture pasture, int spawn, int hunger)
	{
		this.pasture = pasture;
		this.moving = true;
		this.spawn = spawn; 
		this.hunger = hunger;
		this.moveDelay = 10;
		this.resetHunger = hunger;
		resetSpawn = spawn;
		resetMovement = moveDelay;
		this.notDead = true;
		this.seeingDistance = 2;
	
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
		return seeingDistance;
	}

	@Override
	public Entity createNew() {

		this.spawn = this.resetSpawn; 
		return new Sheep(pasture, resetSpawn, resetHunger);
	}

	@Override
	public boolean seeEnemy(Entity otherEntity) {
		if (otherEntity instanceof Wolf)
			return true;
		else
			return false;
	}

	@Override
	public boolean isCompatible(Entity otherEntity) {
	
		if ((otherEntity instanceof Wolf) || (otherEntity instanceof Plant))
			return true;
		else 
			return false;
	}
	
	@Override
	public boolean canEat(Entity otherEntity)
	{
		return (otherEntity instanceof Plant) ? true : false;
	}


}
