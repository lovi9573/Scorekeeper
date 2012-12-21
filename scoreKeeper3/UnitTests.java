package scoreKeeper3;

public class UnitTests {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GameDom gd = new GameDom();
		gd.parseXmlFile("./scoreKeeper3/test.xml");
		int x =0;
		while(gd.loadNextItemBrute() && x<115){
			//System.out.println(gd.activeNodeStack);
			System.out.println(gd.displayAsString());
			System.out.println("==========================================================");
			x++;
		}
		
	}

}
