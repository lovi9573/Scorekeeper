package scorekeeper3;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.font.LineMetrics;
import java.awt.FontMetrics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import java.util.ListIterator;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;




public class GameDisplay extends JPanel implements MouseListener {
	GameDom gamedom;

	
	public GameDisplay(){
		gamedom = new GameDom();
		gamedom.parseXmlFile("./scoreKeeper3/test.xml");
		//setSize(1600, 600);
		//setResizable(false);
		//setFocusableWindowState(false);
		//getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		this.addMouseListener(this);
		setVisible(true);
	}
	public void showTitle(Element ele){
		//Place this in an array of things to display..(already in the dom)
		Graphics graphics = this.getGraphics();
		graphics.setFont(new Font(ScoreKeeper.FONT,Font.PLAIN,24));
		FontMetrics fm = graphics.getFontMetrics();
		LineMetrics lm = fm.getLineMetrics(ele.getTextContent(), graphics);
		int width = fm.charsWidth(ele.getTextContent().toCharArray(), 0, ele.getTextContent().length());
		System.out.println(ele.getTextContent());
		graphics.drawString(ele.getTextContent(), this.getWidth()/2-width/2, 24);
	}	
	
	public void showRoundNumber(Element ele){
		//Place this in an array of things to display..(already in the dom)
		Graphics graphics = this.getGraphics();
		graphics.setFont(new Font(ScoreKeeper.FONT,Font.PLAIN,24));
		graphics.drawString(ele.getTextContent(), 24, 24);
	}
	
	public void showDirections(Element ele){
		//Place this in an array of things to display..(already in the dom)
		Graphics graphics = this.getGraphics();
		graphics.setFont(new Font(ScoreKeeper.FONT,Font.PLAIN,24));
		graphics.drawString(ele.getTextContent(), 24, 24);
	}
	
	public void showQuestion(Element ele){
		//Place this in an array of things to display..(already in the dom)
		Graphics graphics = this.getGraphics();
		graphics.setFont(new Font(ScoreKeeper.FONT,Font.PLAIN,24));
		graphics.drawString(ele.getTextContent(), 24, 60);
	}
	
	public void showAnswer(Element ele){
		//Place this in an array of things to display..(already in the dom)
		Graphics graphics = this.getGraphics();
		graphics.setFont(new Font(ScoreKeeper.FONT,Font.PLAIN,24));
		graphics.setColor(ScoreKeeper.WHITE);
		graphics.drawString(ele.getTextContent(), 24, 100);
	}
	

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//graphics.fillRect(graphics.getClipBounds().x, graphics.getClipBounds().x, graphics.getClipBounds().width, graphics.getClipBounds().height);
		gamedom.loadNextItemBrute();
		System.out.println("gamedisplay got mouseClicked!!");
		//this.paintComponent(getGraphics());
		//this.repaint();
		this.update(getGraphics());
		//this.paintComponent(getGraphics());
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
	
	public void paintComponent(Graphics g){
		Graphics2D graphics = (Graphics2D)g;
		graphics.setColor(ScoreKeeper.GOLD);
		g.fillRect(0,0,this.getWidth(),this.getHeight());
		ListIterator i = gamedom.displayNodeList.listIterator();
		while (i.hasNext()){
			Node node = (Node)i.next();
			Element ele = (Element)node;
			if(node.getNodeName() == "title"){
				showTitle(ele);
			}
			else if(node.getNodeName() == "number"){
				showRoundNumber(ele);
			}
			else if(node.getNodeName() == "directions"){
				showDirections(ele);
			}
			else if(node.getNodeName() == "question"){
				showQuestion(ele);
			}
			else if(node.getNodeName() == "answer"){
				showAnswer(ele);
			}
		}

	
		System.out.println("gamedisplay repainted!!");
		
	}

}
