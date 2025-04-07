/**
 * 
 */
package driver;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import backend.EmergencyRoomSystem;
/**
 * 
 */
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EmergencyRoomSystem system = new EmergencyRoomSystem();
		JFrame frame = new JFrame();
		JPanel panel = new ERPanel(system);
		frame.setTitle("Emergency Room Patient Management");
		frame.setSize(600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.WEST);
		frame.setVisible(true);
	}

}
