/**
 * 
 */
package scoreKeeper3;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.event.*;

import scoreKeeper3.ScoreKeeper.finalJeapordyStates;

/**Guess what....its a button bar that is owned by the ScoreKeeper JFrame
 * @author user
 *
 */
public class ButtonBar extends JPanel {
	
	public JButton upDate = new JButton();
	public AnimatedButton finalJeapordy = new AnimatedButton();
	public JButton roundEnd = new JButton();
	public JButton reset = new JButton();
	//TODO:  Subclass a regular button to make my own toggle button that respects preffered size requests.
	public JToggleButton edit	= new JToggleButton();
	public JButton desklayout = new JButton();
	public JButton clear = new JButton();
	public JButton save = new JButton();
	//public JButton load = new JButton
	
	
	public ButtonBar(String up, String rnd, String rst, boolean edt , int x , int y){
		setPreferredSize(new Dimension(x,y));
		Dimension buttonDim = new Dimension(x/3,y/(edt?2:1));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		if (edt){	
			JPanel apanel = new JPanel();
			apanel.setLayout(new BoxLayout(apanel,BoxLayout.X_AXIS));
			apanel.setPreferredSize(new Dimension(x,y/2));
			apanel.setMinimumSize(new Dimension(x,y/2));
			apanel.setMaximumSize(new Dimension(x,y/2));
			edit.setIcon(new ImageIcon(this.getClass().getResource("/resources/edit.gif")));
			edit.setPreferredSize(buttonDim);
			edit.setMaximumSize(buttonDim);
			edit.setMinimumSize(buttonDim);
			edit.setBorder(new LineBorder(ScoreKeeper.BLACK, 1));
			apanel.add(edit);
			desklayout.setIcon(new ImageIcon(this.getClass().getResource("/resources/desklayout.gif")));
			desklayout.setPreferredSize(buttonDim);
			desklayout.setMinimumSize(buttonDim);
			desklayout.setMaximumSize(buttonDim);
			apanel.add(desklayout);
			//clear.setIcon(new ImageIcon(this.getClass().getResource("/resources/clear.gif")));
			//clear.setPreferredSize(buttonDim);
			//add(clear);
			save.setIcon(new ImageIcon(this.getClass().getResource("/resources/save.gif")));
			save.setPreferredSize(buttonDim);
			save.setMinimumSize(buttonDim);
			save.setMaximumSize(buttonDim);
			apanel.add(save);
			//load.setIcon(new ImageIcon("load.gif"));
			//load.setPreferredSize(buttonDim);
			//add(load);
			add(apanel);
		}	
		JPanel bpanel = new JPanel();
		bpanel.setLayout(new BoxLayout(bpanel,BoxLayout.X_AXIS));
		bpanel.setPreferredSize(new Dimension(x,y/(edt?2:1)));
		bpanel.setMinimumSize(new Dimension(x,y/(edt?2:1)));
		bpanel.setMaximumSize(new Dimension(x,y/(edt?2:1)));
		upDate.setIcon(new ImageIcon(this.getClass().getResource("/resources/update.gif")));
		upDate.setPreferredSize(buttonDim);
		upDate.setMinimumSize(buttonDim);
		upDate.setMaximumSize(buttonDim);
		finalJeapordy.setIconAlternation(new ImageIcon(this.getClass().getResource("/resources/final.gif")),new ImageIcon(this.getClass().getResource("/resources/final.gif")));
		finalJeapordy.setPreferredSize(buttonDim);
		finalJeapordy.setMinimumSize(buttonDim);
		finalJeapordy.setMaximumSize(buttonDim);
		finalJeapordy.setToolTipText("Final Jeapordy | Gray:enter wager | Red: Mark C/I | Silver: Off");
		roundEnd.setIcon(new ImageIcon(this.getClass().getResource("/resources/roundend.gif")));
		roundEnd.setPreferredSize(buttonDim);
		roundEnd.setMinimumSize(buttonDim);
		roundEnd.setMaximumSize(buttonDim);
		//reset.setIcon(new ImageIcon(this.getClass().getResource("/resources/reset.gif")));
		//reset.setPreferredSize(buttonDim);
		bpanel.add(upDate);
		bpanel.add(finalJeapordy);
		bpanel.add(roundEnd);
		//add(reset);
		add(bpanel);
		setVisible(true);
	}
	
	public void addActionListenerToButtons(ScoreKeeper p){
		upDate.addActionListener(p);
		finalJeapordy.addActionListener(p);
		roundEnd.addActionListener(p);
		//reset.addActionListener(p);
		edit.addActionListener(p);
		desklayout.addActionListener(p);
		//clear.addActionListener(p);
		save.addActionListener(p);
		
	}
	

}
