import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Animal extends LivingEntity{
	protected int hunger;
	protected int moveDelay;
	protected int resetMovement;
	protected int resetHunger;
	protected int seeingDistance; //hur långt kan djuret se
	protected int lastX; 
	protected int lastY;
	
	public abstract int getDistance();
	public abstract boolean isCompatible(Entity otherEntity);
	public abstract boolean seeEnemy(Entity otherEntity);
	
	public abstract void resetMovement(); //ges uppdrag att returnera värde i arvsklasserna
	public abstract void resetHunger();
	
	public void move()
	{
		moveDelay--;
		
		if (moveDelay <= 0)
		{
			Point p = getDirection();
			p = secondaryDirection(p);
			if (p != null)
				pasture.moveEntity(this, p);
				
			resetMovement();
		
		}	
		
	}
	//värderar varje håll efter alla entitys inom området. Ju högre poäng desto bättre plats att vara på. 
	private Point getDirection()
	{
		List<Entity> surroundings = getSurrounding(pasture.getPosition(this), this.getDistance());
		Map<Double, Point> seenNeighbours = new HashMap<Double, Point>();
		Point origin = pasture.getPosition(this);
		for (Point neighbour : getAllNeighbours(origin))
			{
				Double value = 0.0;								
				for (Entity e : surroundings)
				{
					double distance = neighbour.distance(pasture.getPosition(e));
					if (this.seeEnemy(e))
						value += 100.0*distance; //ju längre bort den är desto högre värde

					if (this.canEat(e))
						value += 100.0/(1.0+distance); // ju närmre denna är desto högre värde 				
				}
				seenNeighbours.put(value, neighbour);
			}
		Map.Entry<Double, Point> maxPos = null;
		
		//Gå igenom alla positioner som kan flyttas till och välj den som har högst poäng. 
		for (Map.Entry<Double, Point>entry : seenNeighbours.entrySet())
		{
			if (maxPos == null || entry.getKey().compareTo(maxPos.getKey()) >= 0)
			{
				maxPos = entry;		
			}	
		}
		if (maxPos == null)
			return origin;
		return maxPos.getValue();
	}
	//Om Den valda riktningen inte är möjlig, försök då i första hand gå mot samma riktnig sen tidigare,
	//annars välj ny random riktning. Slutligen sätter den så att riktningen är konsekvent
	private Point secondaryDirection(Point p)
	{
		Point direction = p;
		if (!pasture.getFreeNeighbours(this).contains(direction))
		{
			direction = new Point ((int)pasture.getPosition(this).getX()+this.lastX,
									 (int)pasture.getPosition(this).getY()+this.lastY);
		}
		
		if (!pasture.getFreeNeighbours(this).contains(direction))
		{
			direction = getRandomMember(pasture.getFreeNeighbours(this));
		}

		if (direction != null)
		{
			this.lastX = (int)direction.getX() -(int)pasture.getPosition(this).getX();
			this.lastY = (int)direction.getY() -(int)pasture.getPosition(this).getY();
		}
		return direction;
	}
	
	//tar alla entitys i world och returnerar de som ryms inom avståndet som ett Animal kan se
	public List<Entity> getSurrounding(Point here, int distance)
	{
		List<Entity>inArea = new ArrayList<Entity>();
		List<Entity>inDistance = new ArrayList<Entity>();
		inArea = pasture.getEntities();
				
		for (Entity e : inArea)
		{
			if (here.distance(pasture.getPosition(e)) <= this.getDistance())
				if(this.isCompatible(e))
								inDistance.add(e);
		}
		return inDistance;
	}
	
	// samma som i pasture. 
	public List<Point> getAllNeighbours (Point here)
	{
		ArrayList<Point> surrounding = new ArrayList<Point>();
		int hereX = here.x;
		int hereY = here.y;
		
		for (int x = -1; x<=1; x++)
			for (int y = -1; y<=1; y++){
				Point p = new Point (hereX +x, hereY +y);
						surrounding.add(p);
			}
		return surrounding;
	}
		
	
	public void eat()
	{
		if (hunger < (resetHunger/2))
		{
			Point position = pasture.getEntityPosition(this);
			ArrayList<Entity> entityList = (ArrayList<Entity>)pasture.getEntitiesAt(position); //metod i Pasture
			for (Entity entity : entityList)
			{
				if (this.canEat(entity))
				{
					LivingEntity food = (LivingEntity)entity;
					food.notDead = false;
					resetHunger(); //återställer hunger till startvärde
				}
			}
		}
	}
	public void die()
	{
		hunger--;
	
		if (hunger == 0) 
			pasture.removeEntity(this);
		// tick i Living Entity tar bort övriga döda objekt (uppätna)
			
	}
	
	public void tick()
	{
		if (this.notDead)
		{
		breed();
		move();
		eat();
		die();
		}
		else
			pasture.removeEntity(this);
	
	}	
	
}
