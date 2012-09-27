import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * The driver for the AStar pathfinding application.
 * @author Brandon Headrick, Jonathan Cherry
 *
 */
public class RunProg implements Constants
{
	public static void main(String[] args)
	{
		Map map = new Map();
		
		JFrame frame = new JFrame("hi there guys");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		UI panel = new UI(map);
		
		frame.getContentPane().add(panel);
		
		frame.pack();
		frame.setVisible(true);
	}
	
}
