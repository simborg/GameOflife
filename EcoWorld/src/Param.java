// A simple program to illustrate how one can input a bunch of
// parameters without writing too much code. 

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;



public class Param extends JPanel implements ActionListener{

    Param() {
    }

    void init () {
	setLayout(new GridLayout(m.size()+1, 2));
    }

    private Map<String, JTextField> m = new HashMap<String, JTextField> ();
    // The values for all parameters are stored in the map m
    
    public void add (String param, String description, String _default) {
	JLabel label = new JLabel(description);
	JTextField jtf = new JTextField(_default);

	add(label);
	add(jtf);

	m.put(param, jtf);

	setLayout(new GridLayout(m.size()+1, 2, 10, 0));
    }


    //det här är verkligen inte vacker kod, men missade helt denna del av uppgiften innan, så det blev ett snabbt ihopkast... 
   
	public void actionPerformed(ActionEvent e) {
    	String plant = " ";
    	String plantSpawn = " ";
    	int plants = 40;
    	int plantSpawns = 10;
    	String fence = " ";
    	int fences = 40;
    	String wolf = " ";
    	int wolfs = 10;
    	String wolfSpawn = " ";
    	int wolfSpawns = 201;
    	String wolfStarve = " ";
    	int wolfStarves = 200;
    	String sheep = " ";
    	int sheeps = 20;
    	String sheepStarve = " ";
    	int sheepStarves = 100;
    	String sheepSpawn = " ";
    	int sheepsSpawns = 101;
    	
	for (Map.Entry<String, JTextField> entry : m.entrySet()) {
	
	    if (entry.getKey().contentEquals("PLANTS")) 
	    	plant = entry.getValue().getText();
	    if (entry.getKey().contentEquals("PLANTSPAWN")) 
	    	plantSpawn = entry.getValue().getText();
	    if (entry.getKey().contentEquals("FENCE")) 
	    	fence = entry.getValue().getText();
	    if (entry.getKey().contentEquals("WOLF")) 
	    	wolf = entry.getValue().getText();
	    if (entry.getKey().contentEquals("WOLFSPAWN")) 
	    	wolfSpawn = entry.getValue().getText();
	    if (entry.getKey().contentEquals("WOLFSTARVE")) 
	    	wolfStarve = entry.getValue().getText();
	    if (entry.getKey().contentEquals("SHEEP")) 
	    	sheep = entry.getValue().getText();
	    if (entry.getKey().contentEquals("SHEEPSPAWN")) 
	    	sheepSpawn = entry.getValue().getText();
	    if (entry.getKey().contentEquals("SHEEPSTARVE")) 
	    	sheepStarve = entry.getValue().getText();
	    
	}
	try{
	plants = Integer.parseInt(plant);
	fences = Integer.parseInt(fence);
	plantSpawns = Integer.parseInt(plantSpawn);
	wolfs = Integer.parseInt(wolf);
	wolfSpawns = Integer.parseInt(wolfSpawn);
	wolfStarves = Integer.parseInt(wolfStarve);
	sheeps = Integer.parseInt(sheep);
	sheepsSpawns = Integer.parseInt(sheepSpawn);
	sheepStarves = Integer.parseInt(sheepStarve);
	}catch(NumberFormatException u) {
		System.out.println("input should be in numbers, resets to default for wrong input parameters");
    } 
	Pasture.PLANTS = plants;
	Pasture.PLANTSPAWN = plantSpawns;
	Pasture.FENCE = fences;
	Pasture.WOLF = wolfs;
	Pasture.WOLFSPAWN = wolfSpawns;
	Pasture.WOLFSTARVE = wolfStarves;
	Pasture.SHEEP = sheeps;
	Pasture.SHEEPSPAWN = sheepsSpawns;
	Pasture.SHEEPSTARVE = sheepStarves;
	
	
	//new Pasture();
	//javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
		
    }
    
   
}