package scoreKeeper3;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.Graphics;

/* 
 * This is the JFrame palette window that holds the inverted matrix of miniTables
 * */


public class PaletteWindow extends JFrame {
	
	ButtonBar buttonBar = new ButtonBar("","","",false,70,15);
	public JPanel tablePanel = new JPanel();
	ScoreKeeper parent;
	private static int WIDTH=30, HEIGHT=30;
	public static int BUTTONBARHEIGHT = 30;
	
	public PaletteWindow(ScoreKeeper p, int nx, int ny) {
		//Setup this Palette window
		try{
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
		catch(Exception e){
			System.out.println("could not initialize look and feel: " + e);
		}
		parent = p;
		setSize(WIDTH*nx,HEIGHT*ny+37);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		setAlwaysOnTop(true);
		setResizable(false);
		setFocusableWindowState(false);
		setTitle("ScoreKeeper");
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		FlowLayout layout = new FlowLayout();
		setLayout(layout);
		//Add in the buttonbar
		add(buttonBar);
		buttonBar.addActionListenerToButtons(parent);
		//Setup the tablePanel
		GridBagLayout gridBagLayout = new GridBagLayout();
		tablePanel.setLayout(gridBagLayout);
		tablePanel.setPreferredSize(new Dimension(WIDTH*nx-6,HEIGHT*ny-20));
		tablePanel.setBorder(new LineBorder(new Color(0)));
		add(tablePanel);
	}
	
	public void Create(int xmax,int ymax){
		setSize(WIDTH*(xmax+1)/2+12,HEIGHT*(ymax+1)/2+12+BUTTONBARHEIGHT+16);
		tablePanel.setPreferredSize(new Dimension(WIDTH*(xmax+1)/2+2,HEIGHT*(ymax+1)/2+2));
		setVisible(true);
	}
	
	public ButtonBar getButtonBar(){
		return buttonBar;
	}

	public void setParent(ScoreKeeper p){
		parent = p;
	}
}
