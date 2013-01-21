


package scorekeeper3;

import java.awt.*;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.NumberFormatException;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JPanel;


/*  This is as the name suggests the dialog that allows you to manually set points 
 * 
 * */

public class PointEditorDialog extends JDialog implements ActionListener {

	Table parent;
	JTextField[][] editTexts = {{null,null,null},{null,null,null}};
	JButton okButton = new JButton("OK");
	JPanel fieldPanel = new JPanel();
	
	public PointEditorDialog(Table p, String title, boolean modal){
		parent =p;
		setTitle(title);
		setModal(modal);
		setSize(60, 65);
		setLayout(new FlowLayout());
		setAlwaysOnTop(true);
		fieldPanel.setPreferredSize(new Dimension(50,20));
		add(fieldPanel);
		fieldPanel.setLayout(new GridLayout(1,0,2,2));
		editTexts[0][0] = new JTextField(String.valueOf(parent.getPoints()));
		fieldPanel.add(editTexts[0][0]);


		add(okButton);
		okButton.addActionListener(this);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource() == okButton){
			try {
				parent.setPoints(Integer.parseInt(editTexts[0][0].getText()));
			}
			catch (NumberFormatException nfe){
						
			}
			parent.updatePoints();
			this.setVisible(false);
		}
	}

}
