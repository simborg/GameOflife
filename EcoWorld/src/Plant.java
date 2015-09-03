import javax.swing.ImageIcon;


public class Plant extends LivingEntity {
	
	private static final ImageIcon image = new ImageIcon("src\\img\\plant.gif");

	
	public Plant(Pasture pasture, int spawn)
	{
		this.pasture = pasture;
		this.spawn = spawn;
		moving = false;
		notDead = true;
		resetSpawn = spawn;
	}
	@Override
	public ImageIcon getImage()
	{
		return image;
	}
	@Override
	public boolean isCompatible(Entity otherEntity)
	{
		if ((otherEntity instanceof Sheep) || (otherEntity instanceof Wolf))
			return true;
		else 
			return false;
	}
	@Override
	public Entity createNew() 
	{
		return new Plant(pasture, resetSpawn);
	}

}
