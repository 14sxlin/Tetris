package lin.blocks;

public class IBlock extends Block {

	public IBlock() {
		forms = new Point[][]{
			{new Point(0,0),new Point(1,0),new Point(2,0),new Point(3,0)},
			{new Point(1,0),new Point(1,1),new Point(1,2),new Point(1,3)}
		};
		currentForm%=2;
	}
	
	@Override
	public void setRandomForm() {
		currentForm = ((int)(Math.random()*2))%2;
	}

}
