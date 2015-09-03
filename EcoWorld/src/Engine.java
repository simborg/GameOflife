import java.util.*;
import javax.swing.Timer;
import java.awt.event.*;

/**
 * The simulation is run by an internal timer that sends out a 'tick'
 * with a given interval. One tick from the timer means that each
 * entity in the pasture should obtain a tick. When an entity obtains
 * a tick, this entity is allowed to carry out their tasks according
 * to what kind they are. This could mean moving the entity, making
 * the entity starve from hunger, or producing a new offspring.
 */

public class Engine implements ActionListener {
    
    private final int  SPEED_REFERENCE = 1000; /* 1000 */
    private final int speed          = 10;
    private final Timer      timer   = new Timer(SPEED_REFERENCE/speed,this);
    private int              time    = 0;

    private Pasture pasture;


    public Engine (Pasture pasture) {
        this.pasture = pasture;
    }

    public void actionPerformed(ActionEvent event) {
     
        List<Entity> queue = pasture.getEntities();
        for (Entity e : queue) {
            e.tick();
        }
        pasture.refresh();
        time++;
    }

    public void setSpeed(int speed) {
        timer.setDelay(SPEED_REFERENCE/speed);
    }

    public void start() {
        setSpeed(speed);
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public int getTime () {
        return time;
    }

}
