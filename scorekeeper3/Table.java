package scorekeeper3;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import javax.swing.JTextField;


/*
 * Description:  Wrapper for all classes associated with a table of participants.
 * Handles mouse clicks and point calculations.
 */
public class Table implements MouseListener,Serializable {
	
	private int id = 0;
	public int[] position={0,0};
	public TablePoints tablePoints;
	public TablePanel tablePanel = new TablePanel(this);
	public MiniTablePanel miniTablePanel = new MiniTablePanel(this);
	public ScoreKeeper parent;
	private int m_wager;
	private JTextField m_WagerText = new JTextField();
	
	public Table(int idd ,int[] pos, ScoreKeeper p,TablePoints pts){
		id = idd;
		position[0]=pos[0];
		position[1]=pos[1];
		parent = p;
		if (pts != null){
			tablePoints = pts;
		}
		else{
			tablePoints = new TablePoints(pos);
		}
		m_wager=0;
	}
	
	public TablePanel getTablePanel(){
		return tablePanel;
	}
	
	public MiniTablePanel getMiniTablePanel(){
		return miniTablePanel;
	}
	
	public TablePoints getTablePoints(){
		return tablePoints;
	}
	
	public void setTablePoints(TablePoints tp){
		tablePoints = tp;
	}
	
	public boolean getChecked(){
		return tablePoints.isChecked;
	}

	public boolean getEmpty(){
		return tablePoints.isEmpty;
	}
	
	public boolean showPointsImmediately(){
		return parent.showPointsImmediately;
	}
	
	public int getPoints(){
		return tablePoints.points;
	}
	
	public int getLastPointsRecieved(){
		return tablePoints.lastPointsRecieved;
	}

	public int getRoundPoints(){
		return tablePoints.roundPoints;
	}
	
	public void updatePoints(){
		tablePoints.points += tablePoints.newPoints;
		tablePoints.lastPointsRecieved = tablePoints.newPoints;
		tablePoints.newPoints = 0;
		tablePoints.isChecked = false;
		tablePanel.repaint();
		miniTablePanel.repaint();
	}
	
	public void endRound(int bar){
		if (tablePoints.points == bar){
			tablePoints.roundPoints++;
		}
		reset(false);
		tablePanel.repaint();
	}
	
	public void reset(boolean total){
		tablePoints.points = 0;
		tablePoints.lastPointsRecieved=0;
		tablePoints.newPoints = 0;
		tablePoints.isChecked = false;
		if (total==true){
			tablePoints.roundPoints=0;
			tablePoints.isEmpty = false;
		}
		tablePanel.repaint();
		miniTablePanel.repaint();
	}
	
	public void setPoints(int p){
		tablePoints.points = p;
	}
	

	
	public void mouseClicked(MouseEvent e) {
		// 
		if (e.getClickCount()==2){
			tablePoints.isEmpty = !tablePoints.isEmpty;
			if (tablePoints.isEmpty){
				tablePoints.isChecked = false;
			}
			tablePanel.repaint();
			miniTablePanel.repaint();
		}
		if (e.getClickCount()==1 && parent.finalJeapordyState == ScoreKeeper.finalJeapordyStates.WAITINGFORWAGERS){
			
			if (e.getButton() == e.BUTTON1){
				tablePanel.openWagerBox(0);
			}
			else{
				tablePanel.openWagerBox(tablePoints.points);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		// 

	}

	public void mouseExited(MouseEvent e) {
		// 

	}

	public void mousePressed(MouseEvent e) {
		// 
		if (parent.finalJeapordyState != ScoreKeeper.finalJeapordyStates.WAITINGFORWAGERS){
			if (parent.editState) {
				new PointEditorDialog(this,"Enter Adjusted Points", true);
			}
			else if (!tablePoints.isChecked && !tablePoints.isEmpty && !parent.editState){
				boolean isCorrect = false;
				if (e.getButton()==MouseEvent.BUTTON1){
					isCorrect = true;
				}
				tablePoints.newPoints = parent.registerAnswer(position, isCorrect);
				if (parent.finalJeapordyState == ScoreKeeper.finalJeapordyStates.WAITINGFORANSWERS){
					int wageredPoints = Integer.valueOf(tablePanel.m_WagerText.getText());
					if (wageredPoints > tablePoints.points){
						wageredPoints = tablePoints.points;
					}
					tablePoints.newPoints = wageredPoints*(isCorrect?1:-1);
				}
				if (parent.showPointsImmediately){
					tablePoints.lastPointsRecieved = tablePoints.newPoints;
				}
				tablePoints.isChecked = true;
			}
		}
		tablePanel.repaint();
		miniTablePanel.repaint();
		
	}

	public void mouseReleased(MouseEvent e) {
		// 

	}

}
