package scorekeeper3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.border.LineBorder;
import javax.swing.JPanel;

public class FieldCoordinate extends JPanel implements MouseListener {

	LayoutDialog parent;
	int x,y;
	boolean isUsed,isRep;
	
	public FieldCoordinate(LayoutDialog p, int xx, int yy){
		parent = p;
		x=xx;
		y=yy;
		isUsed = isRep = false;
		setBorder(new LineBorder(new Color(0)));
		setPreferredSize(new Dimension(20,20));
		addMouseListener(this);
		repaint();
	}
	
	public void paintComponent(Graphics g){
		Graphics2D graphics;
		graphics = (Graphics2D)g;
		//Paint background
		if (!isRep){
			graphics.setColor((isUsed)?ScoreKeeper.GOLD:ScoreKeeper.WHITE);
		}
		else{
			graphics.setColor(ScoreKeeper.DARK_GOLD);
		}
		graphics.fill(graphics.getClipBounds());
	}
	
	public void registerUse(boolean is){
		isUsed = is;
		repaint();
	}
	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (!isUsed || isRep){
			isRep = !isRep;
		}
		isUsed = !isUsed;
		registerUse(isUsed);
		parent.registerUse(x,y,isUsed);
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
