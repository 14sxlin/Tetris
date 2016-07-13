package lin.blocks;

public class RLBlock extends Block {

	public RLBlock() {
		forms = new Point[][]{
			{new Point(0,0),new Point(1,0),new Point(2,0),new Point(0,1)},
			{new Point(0,0),new Point(1,0),new Point(1,1),new Point(1,2)},
			{new Point(2,0),new Point(0,1),new Point(1,1),new Point(2,1)},
			{new Point(0,0),new Point(0,1),new Point(0,2),new Point(1,2)},
		};
	}

}
