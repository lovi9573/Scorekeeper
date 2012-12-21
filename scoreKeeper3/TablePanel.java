package scoreKeeper3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.font.*;
import java.awt.Font;
import java.awt.TextField;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import scoreKeeper3.ScoreKeeper.finalJeapordyStates;

/* This is the graphical manager for the Table.  this object is owned by its Table object
 * 
 *  */

//TODO: fix timer running down during wager setting  in final jeapordy?
public class TablePanel extends JPanel {
	
	Graphics2D graphics;
	Font pointsFont,lastPointsFont;
	public TextField m_WagerText = new TextField();
	Table parent;
	int x=40,y=40;
		
	public TablePanel(Table t){
		parent =t;
		addMouseListener(parent);
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED,ScoreKeeper.BLACK,ScoreKeeper.BLACK));
		pointsFont = new Font(ScoreKeeper.FONT,Font.PLAIN,7*x/8);
		lastPointsFont = new Font(ScoreKeeper.FONT,Font.PLAIN,18*x/80);
		setPreferredSize(new Dimension(x,y));
		setMinimumSize(new Dimension(x,y));
		m_WagerText.setBounds(0, 0,30,20); // 3*x/8, 2*y/8); //30,20
		m_WagerText.setText("0");
		m_WagerText.setVisible(false);
		add(m_WagerText);		
		//setVisible(true);
	}
	
	public void paintComponent(Graphics g){
		//m_WagerText.setVisible(false);
		graphics = (Graphics2D)g;
		//Paint background
		graphics.setColor(ScoreKeeper.GOLD);
		graphics.fill(graphics.getClipBounds());
		//Paint last points earned rectangle
		graphics.setColor(ScoreKeeper.WHITE);
		Rectangle rect = graphics.getClipBounds();
		rect.height=2*y/8; //20
		rect.width=5*x/16; //25
		graphics.draw(rect);
		rect.height++;
		rect.width++;
		graphics.draw(rect);
		//Paint the last Points Gotten or wagered points
		String num = new String(String.valueOf(parent.getLastPointsRecieved()));
		graphics.setColor(ScoreKeeper.BLACK);
		if (parent.parent.finalJeapordyState == finalJeapordyStates.WAITINGFORANSWERS){
			graphics.setColor(ScoreKeeper.DARK_RED);
			num = m_WagerText.getText();
			if (Integer.valueOf(num) > parent.getPoints()){num = new String(String.valueOf(parent.getPoints()));}
		}	
		graphics.setFont(lastPointsFont);
		graphics.drawString(num, rect.x+rect.width/3, rect.y+rect.height-3);
		//Paint the current points
		graphics.setColor(ScoreKeeper.BLACK);
		Rectangle tablePanelBounds = graphics.getClipBounds();
		graphics.setFont(pointsFont);
		TextLayout textLayout = new TextLayout(String.valueOf(parent.getPoints()),pointsFont,graphics.getFontRenderContext());
		Rectangle2D fontBounds = textLayout.getBounds();
		int pointsX = tablePanelBounds.x+(tablePanelBounds.width/2)-((int)fontBounds.getWidth())/2;
		//if (parent.getPoints() >9){
			//pointsX -= tablePanelBounds.width/6;
		//}
		graphics.drawString(String.valueOf(parent.getPoints()),pointsX, tablePanelBounds.y+tablePanelBounds.height-5);
		// Paint the Round Points
		Rectangle tickMark = new Rectangle(ScoreKeeper.WIDTH*4/80,ScoreKeeper.HEIGHT*3/16);
		tickMark.x = tablePanelBounds.x+tablePanelBounds.width-1;
		tickMark.y = tablePanelBounds.y+ScoreKeeper.HEIGHT/16;
		for (int i = 0 ; i<parent.getRoundPoints(); i++){
			tickMark.x -= ScoreKeeper.WIDTH/10;
			graphics.fill(tickMark);
		}
		if (parent.getChecked()){
			Rectangle clipBounds = graphics.getClipBounds();
			graphics.setColor(ScoreKeeper.WHITE_50);
			int[] x = new int[4];
			int[] y = new int[4];
			for (int n=0 ; n<x.length ; n++ ){
				x[n] = (ScoreKeeper.CHECK_X[n]*clipBounds.width)/100;
				y[n] = (ScoreKeeper.CHECK_Y[n]*clipBounds.height)/100;
			}
			graphics.fillPolygon(x, y, x.length);
		}
		if (parent.getEmpty()){
			graphics.setColor(ScoreKeeper.DARK_GOLD_25);
			graphics.fill(graphics.getClipBounds());
		}
		
		//System.out.println("tablePanelRepainted");
		
	}
	
	public void openWagerBox(int defaultPoints){
		if (parent.parent.finalJeapordyState == finalJeapordyStates.WAITINGFORWAGERS){
			//TODO:Figure this textbox out!
			m_WagerText.setText(String.valueOf(defaultPoints));
			m_WagerText.setVisible(true);
			m_WagerText.setEditable(true);
			m_WagerText.setEnabled(true);
			m_WagerText.setFocusable(true);
			
		}
		else{
			m_WagerText.setVisible(false);
		}
		
	}
		
}
