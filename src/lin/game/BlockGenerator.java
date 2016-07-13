package lin.game;

import java.util.ArrayList;
import java.util.List;

import lin.blocks.Block;
import lin.blocks.EBBlock;
import lin.blocks.IBlock;
import lin.blocks.LLBlock;
import lin.blocks.LZBlock;
import lin.blocks.RLBlock;
import lin.blocks.RZBlock;
import lin.blocks.TBlock;

public class BlockGenerator {
	private List<Block> blockList;
	public BlockGenerator() {
		blockList = new ArrayList<>(7);
		blockList.add(new TBlock());
		blockList.add(new EBBlock());
		blockList.add(new RZBlock());
		blockList.add(new LZBlock());
		blockList.add(new RLBlock());
		blockList.add(new LLBlock());
		blockList.add(new IBlock());
	}
	
	public Block generateBlock() {
		int random = ((int)(Math.random()*7))%7;
		Block block = blockList.get(random);
		block.setRandomForm();
		return block;
	}

}
