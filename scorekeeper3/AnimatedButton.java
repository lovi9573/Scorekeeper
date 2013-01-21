package scorekeeper3;
//TODO: implement a vectored color class
//TODO: Implement gif animation of icon
//TODO: move the timer that fires events into this class rather than in the scorekeeper.

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.Timer;

public class AnimatedButton extends JButton implements ActionListener {

	
	public int Ri,Gi,Bi,Re,Ge,Be,R,G,B =0;
	public int R_increment,G_increment,B_increment = 10;
	public boolean Rinc, Ginc, Binc = true;
	public ImageIcon imagea,imageb;
	boolean imagecycle = true;
	

	
	public void setIconAlternation(ImageIcon a,ImageIcon b){
		imagea = a;
		imageb = b;
		this.setIcon(imagea);
	}
	

	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (true);  //arg0.getClass().isInstance(new Timer(0, actionListener)))
		if(imagecycle){
			this.setIcon(imagea);
		}
		else{
			this.setIcon(imageb);
		}
		imagecycle = !imagecycle;
		this.repaint();
	}

}
