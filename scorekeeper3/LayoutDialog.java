package scorekeeper3;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.AbstractButton;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.SpinnerNumberModel;
import java.awt.GridLayout;


public class LayoutDialog extends JDialog implements ActionListener,ChangeListener {

	ScoreKeeper parent;
	JButton okButton = new JButton("SetLayout");
	JButton settingsButton = new JButton("Save Settings");
	JPanel fieldPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JPanel spinnerPanel = new JPanel();
	FieldCoordinate[][] coordinatePanels ;
	JSpinner lrSpinner = new JSpinner(new SpinnerNumberModel(6,4,20,1));
	JSpinner udSpinner = new JSpinner(new SpinnerNumberModel(5,2,20,1));
	JCheckBox showCheckBox, twoFFCheckBox ;
	JTextField timelimitField = new JTextField();
	JLabel timelimitLabel = new JLabel("Time Limit");
	
	
	public  LayoutDialog(ScoreKeeper p, String title, boolean modal,boolean includeLayout){
		parent = p;
		setTitle(title);
		setModal(modal);
		setSize(250, 200);
		setAlwaysOnTop(true);
		showCheckBox = new JCheckBox("Show Points Earned Immediately",parent.showPointsImmediately);
		twoFFCheckBox = new JCheckBox("Two Points For First Correct",parent.twoForFirst);
		timelimitField.setText(String.valueOf(parent.cdtimer.timeout));
		timelimitField.setPreferredSize(new Dimension(60,20));
		
		setLayout(new FlowLayout());
		add(showCheckBox);
		add(twoFFCheckBox);
		add(timelimitField);
		add(timelimitLabel);
		add(buttonPanel);
		if(includeLayout){
			add(spinnerPanel);
			add(fieldPanel);
		}
		
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(settingsButton);
		if(includeLayout){
			buttonPanel.add(okButton);
		}
		spinnerPanel.setLayout(new FlowLayout());
		spinnerPanel.add(new JLabel("lr"));
		spinnerPanel.add(lrSpinner);
		spinnerPanel.add(udSpinner);		
		lrSpinner.addChangeListener(this);
		udSpinner.addChangeListener(this);
		timelimitField.addActionListener(this);
		okButton.addActionListener(this);
		settingsButton.addActionListener(this);
		showCheckBox.addChangeListener(this);
		twoFFCheckBox.addChangeListener(this);
		
		coordinatePanels = new FieldCoordinate[1][1];
		coordinatePanels[0][0] = new FieldCoordinate(this,0,0);
		this.stateChanged(new ChangeEvent(lrSpinner));
		//setVisible(true);
		
		
	}
	
	public void registerUse(int x,int y, boolean isUsed){
		if (x<coordinatePanels.length && y<coordinatePanels[0].length){
			coordinatePanels[x+1][y].registerUse(isUsed); 
			coordinatePanels[x][y+1].registerUse(isUsed); 
			coordinatePanels[x+1][y+1].registerUse(isUsed);
		}
	}
	
	private void adjustNumPanels(int x, int y){
		//
		System.out.println("x-->"+String.valueOf(coordinatePanels.length));
		FieldCoordinate[][] temp = new FieldCoordinate[x][y];
		//if the new array is to be larger than the old
		if (x > coordinatePanels.length || y > coordinatePanels[0].length){
			//copy existing coordinates to temp
			for (int i=0; i <coordinatePanels.length; i++){
				for (int j=0; j<coordinatePanels[i].length; j++){
					System.out.print("copy"+String.valueOf(x-1)+":"+String.valueOf(y-1)+"<-"+String.valueOf(i)+":"+String.valueOf(j)+"\n");
					temp[i][j]=coordinatePanels[i][j];
				}
			}
			System.out.println("_______________END________________");
			//add in new row/column
			if (x > coordinatePanels.length){
				for (int i = coordinatePanels.length; i<x; i++){
					for (int j=0; j<y;j++){
						temp[i][j] = new FieldCoordinate(this,x-1,j);
					}
				}
			}
			else{
				for (int j = coordinatePanels[0].length; j<y; j++){
					for (int i=0; i<x;i++){
						temp[i][j] = new FieldCoordinate(this,i,j);
					}
				}
			}
			coordinatePanels = new FieldCoordinate[x][y];
			coordinatePanels = temp;
		}
		//If the new array is to be smaller than the old one
		else{
			//copy only what will fit into the new temp array
			for (int i=0; i <x; i++){
				for (int j=0; j<y; j++){
					temp[i][j]=coordinatePanels[i][j];
				}
			}
			coordinatePanels = new FieldCoordinate[x][y];
			coordinatePanels = temp;
		}
	}
	
	
	public void actionPerformed(ActionEvent arg0) {
		// 
		if(arg0.getSource() == okButton){
			// Find out how many master coordinates there are
			int nMasters = 0;
			for (int xx=0 ;  xx<coordinatePanels.length ;  xx++){
				for (int yy=0 ;  yy<coordinatePanels[0].length ;  yy++){
					if (coordinatePanels[xx][yy].isRep){
						nMasters++;
					}
				}
			}
			//populate TablePoints array with masters
			TablePoints[] tabls= new TablePoints[nMasters];
			int i = 0;
			int[] tmpPoint = new int[2];
			for (int xx=0 ;  xx<coordinatePanels.length ;  xx++){
				for (int yy=0 ;  yy<coordinatePanels[0].length ;  yy++){
					if (coordinatePanels[xx][yy].isRep){
						tmpPoint[0]=coordinatePanels[xx][yy].x+1;
						tmpPoint[1]=coordinatePanels[xx][yy].y+1;
						tabls[i]= new TablePoints(tmpPoint);
						i++;
					}
				}
			}
			//int tabls[][] = {{1,1},{3,1},{5,1},{2,3},{4,3},{3,5},{5,7},{9,11}};
			//int spcs[][] = {{0,0},{1,0},{2,0},{3,0},{4,0},{5,0}};
			//
			parent.addTables(tabls);
			this.setVisible(false);
		}
		if(arg0.getSource() == settingsButton){
			parent.cdtimer.timeout = Integer.valueOf(timelimitField.getText());
			parent.cdtimer.reset();
			this.setVisible(false);
			this.dispose();
		}
		if(arg0.getSource() == timelimitField){
			parent.cdtimer.timeout = Integer.valueOf(timelimitField.getText());
		}
	}
	
	public void stateChanged(ChangeEvent e){
		Object source = e.getSource();
		if (source == lrSpinner || source == udSpinner){
			fieldPanel.removeAll();
			int x = (Integer)lrSpinner.getValue();
			int y = (Integer)udSpinner.getValue();
			//int w;
			//w?100:x*20+6;
			setSize((x*20+12>250)?x*20+12:250, (y*20+204>210)?y*20+204:210);
			fieldPanel.setPreferredSize(new Dimension(x*20+2,y*20+2));
			// Add or Remove FieldCoordinates from local array
			//coordinatePanels = new FieldCoordinate[x][y];
			adjustNumPanels(x,y);
			fieldPanel.setLayout(new GridLayout(y,x,0,0));
			for (int yy=0 ;  yy<y ;  yy++){
				for (int xx=0 ;  xx<x ;  xx++){
					coordinatePanels[xx][yy] = new FieldCoordinate(this, xx,yy);
					fieldPanel.add(coordinatePanels[xx][yy]);
				}
			}
			setVisible(true);
		}
		else if (source == showCheckBox){
			parent.showPointsImmediately = ((AbstractButton) source).isSelected() ;
		}
		else if (source == twoFFCheckBox){
			parent.twoForFirst =  ((AbstractButton) source).isSelected() ;
		}
	}

}
