import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.List;


/**
 * A pasture contains sheep, wolves, fences, plants, and possibly
 * other entities. These entities move around in the pasture and try
 * to find food, other entities of the same kind and run away from
 * possible enimies. 
 */
public class Pasture {

	
    private final int   width = 35;
    private final int   height = 25;

    //private final int   dummys = 20;
    
    public static int 	PLANTS = 5;
    public static int 	PLANTSPAWN = 40; 
    public static int	FENCE = 20;
    public static int	WOLF = 10;
    public static int	WOLFSPAWN = 200;
    public static int	WOLFSTARVE = 500;
    public static int	SHEEP = 0;
    public static int	SHEEPSPAWN = -1;
    public static int	SHEEPSTARVE = 150;
    public static int	SECRET = 1;
    public static int	HUMAN = 1;

    private final Set<Entity> world = 
        new HashSet<Entity>();
    private final Map<Point, List<Entity>> grid = 
        new HashMap<Point, List<Entity>>();
    private final Map<Entity, Point> point 
        = new HashMap<Entity, Point>();

    private final PastureGUI gui;
	
    /** 
     * Creates a new instance of this class and places the entities in
     * it on random positions.
     */
    public Pasture() 
    {
        Engine engine = new Engine(this);
        gui = new PastureGUI(width, height, engine);
        
        gui.setFocusable(true);
       
        /* The pasture is surrounded by a fence. Replace Dummy for
         * Fence when you have created that class */
        for (int i = 0; i < width; i++) {
            addEntity(new Fence(this, false), new Point(i,0));
            addEntity(new Fence(this, false), new Point(i, height - 1));
            
        }
        for (int i = 1; i < height-1; i++) {
            addEntity(new Fence(this, false), new Point(0,i));
            addEntity(new Fence(this, false), new Point(width - 1,i));
        }

        /* 
         * Now insert the right number of different entities in the
         * pasture.
         */
        for (int i = 0; i < FENCE; i++) {
            Entity fence = new Fence(this, false);
            
            addEntity(fence, getFreePosition(fence));
            
        }
        for (int i = 0; i < PLANTS; i++)
        {
        	   Entity plant = new Plant(this, PLANTSPAWN);
               addEntity(plant, getFreePosition(plant));
        }
        for (int i = 0; i < SHEEP; i++)
        {
        	Entity sheep = new Sheep(this, SHEEPSPAWN, SHEEPSTARVE);
        	addEntity(sheep, getFreePosition(sheep));
        	
        }
        for (int i = 0; i < WOLF; i++)
        {
        	Entity wolf = new Wolf(this, WOLFSPAWN, WOLFSTARVE);
        	addEntity(wolf, getFreePosition(wolf));
        	
        }
        for (int i = 0; i < SECRET; i++)
        {
        	Entity sec = new Secret(this);
        	addEntity(sec, getFreePosition(sec));
        	
        }
        for (int i = 0; i < HUMAN; i++)
        {
        	Entity hum = new HumanControledSheep(this, 200);
        	addEntity(hum, getFreePosition(hum));
        	
        }
        gui.addKeyListener(null);
        gui.update();
        
    }
    

    public void refresh() {
        gui.update();
    }

    /**
     * Returns a random free position in the pasture if there exists
     * one.
     * 
     * If the first random position turns out to be occupied, the rest
     * of the board is searched to find a free position. 
     */
    private Point getFreePosition(Entity toPlace) 
        throws MissingResourceException {
        Point position = new Point((int)(Math.random() * width),
                                   (int)(Math.random() * height)); 

        int p = position.x+position.y*width;
        int m = height * width;
        int q = 97; //any large prime will do

        for (int i = 0; i<m; i++) {
            int j = (p+i*q) % m;
            int x = j % width;
            int y = j / width;

            position = new Point(x,y);
            boolean free = true;

            Collection <Entity> c = getEntitiesAt(position);
            if (c != null) {
                for (Entity thisThing : c) {
                    if(!toPlace.isCompatible(thisThing)) { 
                        free = false; break; 
                    }
                }
            }
            if (free) return position;
        }
        throw new MissingResourceException(
                  "There is no free space"+" left in the pasture",
                  "Pasture", "");
    }
    
            
    public Point getPosition (Entity e) {
        return point.get(e);
    }

    /**
     * Add a new entity to the pasture.
     */
    public void addEntity(Entity entity, Point pos) {

        world.add(entity);

        List<Entity> l = grid.get(pos); 
        if (l == null) {
            l = new  ArrayList<Entity>();
            grid.put(pos, l);
        }
        l.add(entity);

        point.put(entity,pos);

        gui.addEntity(entity, pos);
    }
    
    public void moveEntity(Entity e, Point newPos) {
        
        Point oldPos = point.get(e);
        List<Entity> l = grid.get(oldPos);
        if (!l.remove(e)) 
            throw new IllegalStateException("Inconsistent stat in Pasture");
        /* We expect the entity to be at its old position, before we
           move, right? */
                
        l = grid.get(newPos);
        if (l == null) {
            l = new ArrayList<Entity>();
            grid.put(newPos, l);
        }
        l.add(e);

        point.put(e, newPos);

        gui.moveEntity(e, oldPos, newPos);
    }

    /**
     * Remove the specified entity from this pasture.
     */
    public void removeEntity(Entity entity) { 

        Point p = point.get(entity);
        world.remove(entity); 
        grid.get(p).remove(entity);
        point.remove(entity);
        gui.removeEntity(entity, p);
        
        

    }

    /**
     * Various methods for getting information about the pasture
     */

    public List<Entity> getEntities() {
        return new ArrayList<Entity>(world);
    }
        
    public Collection<Entity> getEntitiesAt(Point lookAt) {

        Collection<Entity> l = grid.get(lookAt);
        
        if (l==null) {
            return null;
        }
        else {
            return new ArrayList<Entity>(l);
        }
    }


    public List<Point> getFreeNeighbours(Entity entity) {
        List<Point> free = new ArrayList<Point>();

        int entityX = getEntityPosition(entity).x; // kolla på för check surroundings 
        int entityY = getEntityPosition(entity).y;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                Point p = new Point(entityX + x,
                          entityY + y);
                if (freeSpace(p, entity))
                    free.add(p);
            }
        }
        return free;
    }

    private boolean freeSpace(Point p, Entity e) {
                              
        List <Entity> l = grid.get(p);
        if ( l == null  ) return true;
        for (Entity old : l ) 
            if (! old.isCompatible(e)) return false;
        return true;
    }

    public Point getEntityPosition(Entity entity) {
        return point.get(entity);
    }


    public static void main (String [] arg) {
    	/*JFrame f = new JFrame();
    	Param p = new Param();
   
		p.add("PLANTS", "Number of plants", "40");
    	p.add("PLANTSPAWN", "Hur snabbt skapas fler plantor", "10");
    	p.add("FENCE", "Number of Fence", "40");
    	p.add("WOLF", "Number of Wolves", "10");
    	p.add("WOLFSPAWN", "speed WOLFSPAWN", "201");
    	p.add("WOLFSTARVE", "time for WOLFSTARVE", "200");
    	p.add("SHEEP", "number of sheep", "20");
    	p.add("SHEEPSPAWN", "SHEEPSPAWN", "101");
    	p.add("SHEEPSTARVE", "SHEEPSTARVE", "100");
 
    	JButton klar = new JButton("Klar!");

    	p.add(klar);
    	klar.addActionListener(p);

    	f.add(p);
    	f.pack();
    	
    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    	f.setLocation(screenSize.width/2 - f.getWidth()/2,
    		      screenSize.height/2 - f.getHeight()/2);


    	f.setVisible(true);
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
    	
    	Pasture p = new Pasture();
    	
    	
    	
    }


}


