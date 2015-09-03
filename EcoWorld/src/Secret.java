import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Secret implements Entity {
	private static final ImageIcon image = new ImageIcon("src\\img\\unknown.gif");
	private final Pasture pasture;
	
	public Secret(Pasture pasture)
	{
		this.pasture = pasture;
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub	
	}

	@Override
	public ImageIcon getImage() {
		// TODO Auto-generated method stub
		return image;
	}

	@Override
	public boolean isCompatible(Entity otherEntity) {
		if (otherEntity instanceof Sheep)
			return true;
		
		return false;
	}
	public void atSheep(Entity otherEntity)
	{
		if (isCompatible(otherEntity))
		{
			JOptionPane.showMessageDialog(
	                new JFrame(),
	                "<html>" +
	                        "<div style='width: 300px; margin-bottom: 10px;'>" +
	                        "Inbjudande text." +
	                        "</div>" +
	                        "<div style='width: 300px; margin-bottom: 10px;'>" +
	                        "Övrigt meddelande!" +
	                        "</div>",
	                        "Welcome!",
	                        JOptionPane.PLAIN_MESSAGE
	                );
		}
	}

	

}
