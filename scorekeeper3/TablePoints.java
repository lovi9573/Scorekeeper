package scorekeeper3;

import java.io.Serializable;

public class TablePoints implements Serializable {
	public int[] position={0,0};
	public boolean isChecked = false;
	public boolean isEmpty = false;
	public int points=0,roundPoints=0,lastPointsRecieved=0,newPoints=0;
	
	public TablePoints(int[] d){
		position[0]=d[0];
		position[1]=d[1];
	}
	
	public int[] getCoords(){
		return position;
	}
}
