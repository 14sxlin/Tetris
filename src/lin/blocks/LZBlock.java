package lin.blocks;

public class LZBlock extends Block {

	public LZBlock() {
		forms = new Point[][]{
			{new Point(0,0),new Point(1,0),new Point(1,1),new Point(2,1)},
			{new Point(1,0),new Point(0,1),new Point(1,1),new Point(0,2)},
		};
		currentForm%=2;
	}
	
	@Override
	public void setRandomForm() {
		currentForm = ((int)(Math.random()*2))%2;
	}

}
