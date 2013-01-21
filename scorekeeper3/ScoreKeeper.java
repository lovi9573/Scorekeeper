/**
 *
 
 */

package scorekeeper3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

import java.awt.Component;

import java.io.*;
import java.awt.FileDialog;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.lang.String;
import javax.swing.border.LineBorder;

//import sun.security.provider.JavaKeyStore.CaseExactJKS;

/**  This is the Main JFrame that owns the buttonbar and table frames
 * @author user
 *
 */
public class ScoreKeeper extends JFrame implements 
		ActionListener,MouseListener {

	public static enum finalJeapordyStates{
		NONE,
		WAITINGFORWAGERS,
		WAITINGFORANSWERS;
	};
	private boolean tableAlreadyCorrect,hasNewData = false;
	public static Color GOLD = new Color(207,161,0);
	public static Color BLACK = new Color(0,0,0);
	public static Color WHITE = new Color(255,255,255);
	public static Color WHITE_50 = new Color(255,255,255,191);
	public static Color DARK_GOLD = new Color(111,86,0);
	public static Color LIGHT_GOLD = new Color(207,180,84);
	public static Color DARK_GOLD_25 = new Color(111,86,0,192);
	public static Color DARK_RED = new Color(110,40,0);
	public static int[] CHECK_X = {10,30,90,30};
	public static int[] CHECK_Y = {40,92,14,70};
	public static int[][] X_OUT	=	{{5,42,5,50,95,58,95,50},{5,50,95,58,95,50,5,42}};
	public static int WIDTH = 40;
	public static int HEIGHT = 40;
	public static int BUTTONBARHEIGHT = 30;
	public static int NCELLSX = 3;
	public static int NCELLSY = 2;
	public static String FONT =  "Haettenschweiler";//"Stylus BT";
	public boolean showPointsImmediately = true;
	public boolean twoForFirst = true;
	public boolean editState = false;
	public boolean hasLayout = false;
	public Table[] table; //= new Table[NCELLSX*NCELLSY] ;
	public ButtonBar buttonBar = new ButtonBar("Up","Ro","RS",true,70,BUTTONBARHEIGHT);
	public CountdownTimer cdtimer = new CountdownTimer(this);
	public JPanel rootPanel = new JPanel();
	public JPanel tablesPanel = new JPanel();
	PaletteWindow paletteWindow = new PaletteWindow(this,NCELLSX,NCELLSY);
	//TODO:Move this timer into the animatedbutton that it controls.
	public Timer timer = new Timer(500,buttonBar.finalJeapordy);
	public finalJeapordyStates finalJeapordyState;
	public JFrame gameDisplay ;
	public int xxx = 0;
	public boolean xxxinc = true;
	
	public ScoreKeeper(){

		//		General housekeeping at construction
		setSize(WIDTH*NCELLSX,BUTTONBARHEIGHT+cdtimer.y+44);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("ScoreKeeper");
	
		/*TODO: default UImanager setup must be done before creation of the object being managed
			Since all my setup is inside the ScorekeeperJFrame it cannot be managed since it is already created */
		//setIconImage(new ImageIcon("l").getImage());
		//setUndecorated(true);
		setAlwaysOnTop(true);
		setResizable(false);
		setFocusableWindowState(false);

		FlowLayout layout = new FlowLayout();
		rootPanel.setLayout(layout);
		getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
		finalJeapordyState = finalJeapordyStates.NONE;		
		// Setup the tablePanel and add Tables
		tablesPanel.setPreferredSize(new Dimension(WIDTH*NCELLSX,HEIGHT*NCELLSY));
		//tablesPanel.setLayout(new GridLayout(NCELLSY,NCELLSX,2,2));
		GridBagLayout l = new GridBagLayout();
		//l.columnWidths = 40;
		//l.rowHeights = 40;
		tablesPanel.setLayout(l);
		tablesPanel.setBorder(new LineBorder(new Color(0)));
		//addTables();
		
		// Setup the ButtonBar
		buttonBar.addActionListenerToButtons(this);
		
		// Add in the ButtonBar and table Panel
		getContentPane().add(rootPanel);
		rootPanel.add(buttonBar);
		rootPanel.add(cdtimer);
		rootPanel.add(tablesPanel);
		addMouseListener(this);
				
		rootPanel.validate();
		//Make everything visible
		setVisible(true);
		
		//TODO: testing line
		Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
		gameDisplay = new JFrame();
		gameDisplay.setSize(dm);
		gameDisplay.setFocusableWindowState(false);
		gameDisplay.setResizable(false);
		gameDisplay.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		gameDisplay.setContentPane(new GameDisplay());
		gameDisplay.setVisible(true);

	}
	
	public void redrawTables(){
		
		for (int i=0 ; i<=table.length-1 ; i++){
			table[i].tablePanel.m_WagerText.setVisible(false);	
		}
		for (int i=0 ; i<=table.length-1 ; i++){
			table[i].tablePanel.repaint();	
		}
	}
	
	public void updatePoints(){
		
		for (int i=0 ; i<=table.length-1 ; i++){
			table[i].updatePoints();	
		}
		tableAlreadyCorrect=false;
	}
	
	
	private void endRound(){
			
		updatePoints();
		int bar=0;
		for (int i=0 ; i<=table.length-1 ; i++){
			if (table[i].getPoints() > bar){
					bar = table[i].getPoints();
			}
		}
		for (int i=0 ; i<=table.length-1 ; i++){
			table[i].endRound(bar);
		}	
	}
	
	
	private void reset(){
			
		for (int i=0 ; i<=table.length-1 ; i++){
			table[i].reset(true);
		}
		hasNewData=false;
	}
	
	private void editPoints(){
		// TODO: set HasNewData from the Table point dialog.
		//PointEditorDialog editDialog = new PointEditorDialog(this,"Edit Points", true) ;
		hasNewData = true;		
	}
	
	/* Assigns new points to the Registering table according to correctness
	 * and order.
	 */
	public int registerAnswer(int[] id, boolean isCorrect){
			
		hasNewData = true ;
		if (isCorrect){
			if(!tableAlreadyCorrect && twoForFirst){
				tableAlreadyCorrect = true;
				return 2;
			}
			else {
				tableAlreadyCorrect = true;
				return 1;
			}
		}
		else {
			return 0;
		}
		
	}
	/* Creates Table objects in the Table{}{} array then 
	 * 
	 *adds thier Table Pane representations to the tablePanel container
	 */
	
	public void addTables(TablePoints[] tables){
		
		// 
		tablesPanel.removeAll();
		paletteWindow.tablePanel.removeAll();
		/*{
		JTextField a,b,c;
		a= new JTextField("12");
		b= new JTextField("12");
		c= new JTextField("12");
		a.setSize(50, 50);
		b.setSize(50, 50);
		c.setSize(50, 50);
		tablesPanel.add(a);
		tablesPanel.add(b);
		tablesPanel.add(c);
		}*/
		GridBagConstraints c = new GridBagConstraints();
		JLabel l,m;
		table = new Table[tables.length];
		int[] id={0,0};
		int xmax = 0;
		int ymax = 0;
		
		//Add in tablePanels to main window
		for (int i=0 ; i<=tables.length-1 ; i++){
			id = tables[i].getCoords();	
			table[i]= new Table(i,id ,this,tables[i]);
			c.gridx = id[0];
			if (id[0] > xmax){
				xmax = id[0];
			}
			c.gridy = id[1];
			if (id[1] > ymax){
				ymax = id[1];
			}
			c.gridheight = 2;
			c.gridwidth = 2;
			tablesPanel.add(table[i].getTablePanel(),c);
		}
		int xt,yt = 0;
		//Add in tablePanels to the mini window
		for (int i=0 ; i<=tables.length-1 ; i++){
			xt = xmax+1 - table[i].position[0];
			yt = ymax+1 - table[i].position[1];
			c.gridx = xt;
			c.gridy = yt;
			paletteWindow.tablePanel.add(table[i].getMiniTablePanel(),c);
		}
		
		
		//Add in horizontal spaceholders
		// These go accross the top of the tablesPanel to setup a predictable gridbag grid.
		for (int i=1 ; i<=xmax+1 ; i++){
			l = new JLabel("");
			l.setPreferredSize(new Dimension(WIDTH/2,0));
			l.setBorder(new LineBorder(new Color(0)));	
			c.gridx = i;
			c.gridy = 0;
			c.gridheight = 1;
			c.gridwidth = 1;
			tablesPanel.add(l,c);
			m = new JLabel("");
			m.setPreferredSize(new Dimension(15,0));
			m.setBorder(new LineBorder(new Color(0)));
			paletteWindow.tablePanel.add(m,c);
		}
		
		//Add in vertical spaceholders
		// These go down the left side of the window to set predictable sizes in the gridbag.
		for (int i=1 ; i<=ymax+1 ; i++){
			l = new JLabel("");
			l.setPreferredSize(new Dimension(0,HEIGHT/2));
			l.setBorder(new LineBorder(new Color(0)));	
			c.gridx = 0;
			c.gridy = i;
			c.gridheight = 1;
			c.gridwidth = 1;
			tablesPanel.add(l,c);
			m = new JLabel("");
			m.setPreferredSize(new Dimension(0,15));
			m.setBorder(new LineBorder(new Color(0)));
			paletteWindow.tablePanel.add(m,c);
		}
		//Set the sizes of containers
		setSize(WIDTH*(xmax+1)/2+14,HEIGHT*(ymax+1)/2+14+BUTTONBARHEIGHT+cdtimer.y+6+32);
		tablesPanel.setPreferredSize(new Dimension(WIDTH*(xmax+1)/2+4,HEIGHT*(ymax+1)/2+4));
		paletteWindow.Create(xmax,ymax);
		hasNewData = true;
		hasLayout = true;
		setVisible(true);
		//paletteWindow.setSize(WIDTH*(xmax+1)/2+12,HEIGHT*(ymax+1)/2+12+BUTTONBARHEIGHT+32);
		//paletteWindow.tablePanel.setPreferredSize(new Dimension(WIDTH*(xmax+1)/2+2,HEIGHT*(ymax+1)/2+2));
		//paletteWindow.setVisible(true);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	private void save(){
		
		String path = "";
		String fileName="save.sk";
		try{
			
			JFileChooser fileD = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("ScoreKeeper Files","sk");
			fileD.setFileFilter(filter);
			int returnVal = fileD.showSaveDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
			    fileName = fileD.getSelectedFile().getName();
			    path = fileD.getSelectedFile().getPath();
			}
			if (!path.endsWith(".sk")){
				path += ".sk";
			}
			//open file for writing
			File saveFile = new File(path);
			FileOutputStream saveFileStream = new FileOutputStream(saveFile);
			ObjectOutputStream oOStream = new ObjectOutputStream(saveFileStream);
			saveFileStream.write(table.length);
			//loop through tables and save each
			for (int i=0 ; i<=table.length-1 ; i++){
				oOStream.writeObject(table[i].getTablePoints());
			}
			System.out.println("saved");
			saveFileStream.close();
			System.out.println("save: " +path);
			hasNewData = false;
		}
		catch(FileNotFoundException fnfe){
			JOptionPane.showMessageDialog(this, fnfe, "File Not Found", JOptionPane.WARNING_MESSAGE);
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(this, ioe, "IO Exception", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void load(){
		String path = "";
		try{
			
			JFileChooser fileD = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("ScoreKeeper Files","sk");
			fileD.setFileFilter(filter);
			fileD.setDialogType(JFileChooser.OPEN_DIALOG);
			int returnVal = fileD.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
			    path = fileD.getSelectedFile().getPath();
			}
			//open file for reading
			File loadFile = new File(path);
			FileInputStream loadFileStream = new FileInputStream(loadFile);
			ObjectInputStream oOStream = new ObjectInputStream(loadFileStream);
			//
			int n = loadFileStream.read();
			TablePoints[] ptsTmp = new TablePoints[n];
			//loop through tables and save each
			//
			for (int i=0 ; i<=n-1 ; i++){
					ptsTmp[i]=(TablePoints)oOStream.readObject();
			}
			System.out.println("loaded");
			loadFileStream.close();
			System.out.println("load: " +path);
			hasNewData = false;
			addTables(ptsTmp);
		}
		catch(FileNotFoundException fnfe){
			
			JOptionPane.showMessageDialog(this, fnfe, "File Not Found", JOptionPane.WARNING_MESSAGE);
		}
		catch(IOException ioe){
			JOptionPane.showMessageDialog(this, ioe, "IO Exception", JOptionPane.WARNING_MESSAGE);
		}
		catch(ClassNotFoundException cnfe){
			JOptionPane.showMessageDialog(this, cnfe, "Class Not Found", JOptionPane.WARNING_MESSAGE);
		}
		updatePoints();
	}
		
		


	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if ((source == buttonBar.upDate || source == paletteWindow.getButtonBar().upDate) && hasLayout) {
			updatePoints();
			timer.restart();
			cdtimer.reset();
			if (finalJeapordyState == finalJeapordyStates.WAITINGFORANSWERS){
				finalJeapordyState = finalJeapordyStates.NONE;
				buttonBar.finalJeapordy.setIconAlternation(new ImageIcon(this.getClass().getResource("/resources/final.gif")),new ImageIcon(this.getClass().getResource("/resources/final.gif")));
				setFocusableWindowState(false);
				buttonBar.finalJeapordy.repaint();
			}
		}
		else if (source == buttonBar.finalJeapordy || source == paletteWindow.getButtonBar().finalJeapordy) {
			
			if (finalJeapordyState == finalJeapordyStates.NONE){
				finalJeapordyState = finalJeapordyStates.WAITINGFORWAGERS;
				buttonBar.finalJeapordy.setIconAlternation(new ImageIcon(this.getClass().getResource("/resources/final.gif")),new ImageIcon(this.getClass().getResource("/resources/finalenter.gif")));
				setFocusableWindowState(true);
				cdtimer.pause(true);
				buttonBar.finalJeapordy.repaint();
			}
			else if (finalJeapordyState == finalJeapordyStates.WAITINGFORWAGERS){
				finalJeapordyState = finalJeapordyStates.WAITINGFORANSWERS;
				buttonBar.finalJeapordy.setIconAlternation(new ImageIcon(this.getClass().getResource("/resources/final.gif")),new ImageIcon(this.getClass().getResource("/resources/finalscore.gif")));
				setFocusableWindowState(false);
				cdtimer.pause(false);
				cdtimer.reset();
				buttonBar.finalJeapordy.repaint();
				redrawTables();
			}
			else if (finalJeapordyState == finalJeapordyStates.WAITINGFORANSWERS){
				finalJeapordyState = finalJeapordyStates.NONE;
				buttonBar.finalJeapordy.setIconAlternation(new ImageIcon(this.getClass().getResource("/resources/final.gif")),new ImageIcon(this.getClass().getResource("/resources/final.gif")));
				//buttonBar.finalJeapordy.setBackground(new Color(228,237,245));
				setFocusableWindowState(false);
				buttonBar.finalJeapordy.repaint();
			}
		}
		else if (source == buttonBar.roundEnd || source == paletteWindow.getButtonBar().roundEnd) {
			this.endRound();
		}
		else if (source == buttonBar.reset || source == paletteWindow.getButtonBar().reset) {
			reset();
		}
		if (source == buttonBar.edit){
			editState = buttonBar.edit.isSelected();
			if (editState){
				buttonBar.edit.setIcon(new ImageIcon(this.getClass().getResource("/resources/edit_on.gif")));
			}
			else{
				buttonBar.edit.setIcon(new ImageIcon(this.getClass().getResource("/resources/edit.gif")));
			}
			//editPoints();
		}
		if (source == buttonBar.desklayout){
			// Layout Creation Screen  Output must be an array of order pairs for table locations
			//int tabls[][] = {{1,1},{3,1},{5,1},{2,3},{4,3},{3,5},{5,7},{9,11}};
			//int spcs[][] = {{0,0},{1,0},{2,0},{3,0},{4,0},{5,0}};
			//addTables(tabls);
			if(!hasLayout){
				tablesPanel.removeAll();
				paletteWindow.tablePanel.removeAll();
				tablesPanel.validate();
				paletteWindow.tablePanel.validate();
				LayoutDialog l = new LayoutDialog(this,"Layout Manager",true,true);
			}
			else{
				LayoutDialog l = new LayoutDialog(this,"Layout Manager",true,false);
			}

			
		}
		/*if (source == buttonBar.clear){
			tablesPanel.removeAll();
			paletteWindow.tablePanel.removeAll();
			tablesPanel.validate();
			paletteWindow.tablePanel.validate();
			setVisible(false);
			setVisible(true);
		}*/
		if (source == buttonBar.save){
			if(hasNewData){
				save();
			}
			else {
				load();
			}
		}
	}

	
	
	public void mouseClicked(MouseEvent arg0) {
		//  Auto-generated method stub	
		
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	public void mouseEntered(MouseEvent arg0) {
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
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			//UIManager.put("InternalFrame.border", new LineBorder(new Color(0,0,0),3));
			//UIManager.put("InternalFrame.borderDarkShadow", new Color(0,0,0));
			//UIManager.put("RootPane.plainDialogBorder", new LineBorder(new Color(0,0,0),3));
			UIManager.put("RootPane.frameBorder", new LineBorder(new Color(0,0,0),3));
			UIManager.put("Panel.background", LIGHT_GOLD);
			UIManager.put("Viewport.background", LIGHT_GOLD);
			UIManager.put("InternalFrame.icon",null);
			UIManager.put("InternalFrame.inactiveTitleBackground",LIGHT_GOLD);
			UIManager.put("Button.shadow", BLACK);
			UIManager.put("Button.darkShadow", BLACK);
			UIManager.put("Button.border", new LineBorder(BLACK,1));
			UIManager.put("inactiveCaption", BLACK);
			UIManager.put("InternalFrame.closeIcon", new ImageIcon(new JPanel().getClass().getResource("/resources/close.gif")));
			UIManager.put("InternalFrame.iconifyIcon", new ImageIcon(new JPanel().getClass().getResource("/resources/minimize.gif")));
			UIManager.put("InternalFrame.icon", new ImageIcon(new JPanel().getClass().getResource("/resources/appicon.gif")));
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		
			JFrame.setDefaultLookAndFeelDecorated(true);

		}
		catch(Exception e){
			System.out.println("could not initialize look and feel: " + e);
		}
		ScoreKeeper sk = new ScoreKeeper();

	}

}
