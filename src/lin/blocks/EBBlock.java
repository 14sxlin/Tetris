package lin.blocks;

public class EBBlock extends Block {

	public EBBlock() {
		forms = new Point[][]{
			{new Point(0,0),new Point(0,1),new Point(1,0),new Point(1,1)}
		};
		currentForm = 0;
	}

	@Override
	public void setRandomForm() {
		currentForm = 0;
	}
	
	

}
