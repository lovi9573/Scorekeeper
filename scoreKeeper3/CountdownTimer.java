package scoreKeeper3;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.lang.*;
import java.awt.*;

public class CountdownTimer extends JPanel implements ActionListener,MouseListener {
	
	int timeout = 30;
	int r,g,b = 0;
	int time = 0;
	Timer timer = new Timer(1000,this);
	Font timeFont;
	Graphics2D graphics;
	int x = 60;
	int y = 20;
	ScoreKeeper parent;
	boolean timedout = true;
	boolean paused = true;
	
	public CountdownTimer(ScoreKeeper sk)
	{
		parent = sk;
		timeFont = new Font(ScoreKeeper.FONT,Font.PLAIN,20);
		timer.restart();
		addMouseListener(this);
		this.setPreferredSize(new Dimension(x,y));
	}
	
	public void paintComponent(Graphics gr)
	{
		//Get graphics context
		graphics = (Graphics2D)gr;
		//Fill in background
		graphics.setColor(new Color(r,g,b));
		graphics.fill(graphics.getClipBounds());
		//Draw timer countdown
		graphics.setColor(ScoreKeeper.BLACK);
		String timeleft = String.valueOf((timeout - time)/60)+":"+(((timeout-time)%60<10)?"0":"")+String.valueOf((timeout - time)%60);
		graphics.setFont(timeFont);
		Rectangle rect = graphics.getClipBounds();
		graphics.drawString(timeleft, rect.x+2+15, rect.y+y-2);
	}
	
	//public void repaint()
	//{
		//this.setBackground(new Color(r,g,b));
		//super.repaint();

	//}
	
	public void actionPerformed(ActionEvent evt)
	{
		
		if(!timedout && !paused){
			if (time < timeout)
			{
				time ++;
			}
			else if(!timedout)
			{
				parent.actionPerformed( new ActionEvent(parent.buttonBar.upDate, 0, "none") );		
				timedout = true;
			}
		}
		r=((255*time)/timeout);
		g=255-((255*time)/timeout);
		b=0; //255-((255*time)/timeout);
		this.repaint();
		
	}
	
	public void reset()
	{
		time = 0;
		r=0;
		g=255;
		timedout = false;
		timer.restart();
		this.repaint();
	}
	
	public void pause(boolean b){
		paused = false;
		if(b){paused = true;};
	}
	
	public void mouseClicked(MouseEvent arg0) {
		//  Auto-generated method stub
		System.out.println("Mouse clicked");
		if(arg0.getSource() == this){
			System.out.println("cdtimer clicked");
			this.reset(); 
		}

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent e) {
		//  Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	public void mouseExited(MouseEvent e) {
		// Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	public void mousePressed(MouseEvent e) {
		//  Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	public void mouseReleased(MouseEvent e) {
		//  Auto-generated method stub

	}

}
