package scorekeeper3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class MiniTablePanel extends JPanel {
	
	Table parent;
	Graphics2D graphics;
	
	public MiniTablePanel(Table t){
		parent =t;
		addMouseListener(parent);
		setPreferredSize(new Dimension(30,30));
		setBorder(BorderFactory.createLineBorder(ScoreKeeper.BLACK, 1));
	}
	
	public void paintComponent(Graphics g){
		graphics = (Graphics2D)g;
		Rectangle clipBounds = graphics.getClipBounds();
		//Paint background
		graphics.setColor(ScoreKeeper.GOLD);
		graphics.fill(clipBounds);
		//Paint checkmark if needed
		if (parent.getChecked()){
			graphics.setColor(Color.WHITE);
			int[] x = new int[4];
			int[] y = new int[4];
			for (int n=0 ; n<x.length ; n++ ){
				x[n] = (ScoreKeeper.CHECK_X[n]*clipBounds.width)/100;
				y[n] = (ScoreKeeper.CHECK_Y[n]*clipBounds.height)/100;
			}
			graphics.fillPolygon(x, y, x.length);
		}
		if (parent.getEmpty()){
			graphics.setColor(Color.WHITE);
			int[] x = new int[ScoreKeeper.X_OUT[0].length];
			int[] y = new int[ScoreKeeper.X_OUT[1].length];
			for (int n=0 ; n<x.length ; n++ ){
				x[n] = (ScoreKeeper.X_OUT[0][n]*clipBounds.width)/100;
				y[n] = (ScoreKeeper.X_OUT[1][n]*clipBounds.height)/100;
			}
			graphics.fillPolygon(x, y, x.length);
		}
	}
}
