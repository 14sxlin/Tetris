package lin.game;

import lin.blocks.Block;
import lin.blocks.Point;

public class BlockContainer {
	private Block currentBlock;
	private int[][] lastCanvas;   //记录上一个画布状态,用来回退
	private int[][] canvas;		 
	private int width,height;
	private boolean blockFalling = false;
	private boolean gameOver = false;
	private final Point pointToPutBlock;
	private Point tracePoint;
	
	public BlockContainer(int width,int height) {
		canvas = new int[width][height];
		lastCanvas = new int[width][height];
		this.width = width;
		this.height = height;
		pointToPutBlock = new Point(width/2, 0);
		tracePoint = new Point(width/2, 0);
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init() {
		clearCanvas();
		clearLastCanvas();
		blockFalling = false;
		gameOver = false;
		tracePoint = new Point(width/2, 0);
	}
	
	/**
	 * 是否产生碰撞,只包括和其他方块碰撞
	 * 如果和边界碰撞的话,会抛出数组越界的错误
	 * @return
	 */
	private boolean isCrash() {
		int count = 0,countLast = 0;
		for(int i = 0;i<width;i++)
			for(int j = 0;j<height;j++)
			{
				if(canvas[i][j]==1)
					count++;
				if(lastCanvas[i][j]==1)
					countLast++;
			}
		TetrisContext.logger.debug(getClass(), " count, countLast "+count+" , "+countLast);
		if(count==0&&countLast==0)
			return false;
		else if(count-countLast==4)
			return false;
		else
			return countLast!=count;
	}
	
	/**
	 * 检查是否可以消除
	 * @return
	 */
	public boolean checkDispel() {
		for(int j = height -1; j>=0 ; j--)
		{
			boolean should = true;
			for(int i = width-1 ; i>=0 ; i--)
			{
				if(canvas[i][j]==0)
					should = false;
			}
			if(should) return true;
		}
		return false;
	}
	
	/**
	 * 左右移动
	 * @param i 1表示右移,-1表示左移
	 */
	public void move(int i)
	{
		try {
			removeLastBlock();
			tracePoint.setX(tracePoint.getX()+i);
			removeLastBlock();
			putBlock();
		} catch (ArrayIndexOutOfBoundsException e) {
			try {
				tracePoint.setX(tracePoint.getX()-i);
				removeLastBlock();
				putBlock();
			} catch (ArrayIndexOutOfBoundsException e1) {
				rollBack();
			}
		}
		if(isCrash())
		{
			rollBack();
		}
	}
	
	/**
	 * 变换形状
	 */
	public void transform() {
		int lastForm = currentBlock.getCurrentForm();
		save();
		try {
			removeLastBlock();
			currentBlock.transform();
			putBlock();
		} catch (ArrayIndexOutOfBoundsException e) {
			rollBack();
			currentBlock.setCurrentForm(lastForm);
		}
		if(isCrash())
		{
			rollBack();
			currentBlock.setCurrentForm(lastForm);
		}
	}
	/**
	 * 放置方块
	 * @param tracePoint
	 */
 	private void putBlock() {
		int x0 = tracePoint.getX();
		int y0 = tracePoint.getY();
		int formNum = currentBlock.getCurrentForm();
		for(int i = 0;i<4;i++)
		{
			int x = currentBlock.forms[formNum][i].getX();
			int y = currentBlock.forms[formNum][i].getY();
			canvas[x0+x][y+y0] = 1;
		}
	}
	/**
	 * 移除上一个位置
	 */
	private void removeLastBlock() {
		int x0 = tracePoint.getX();
		int y0 = tracePoint.getY();
		int formNum = currentBlock.getCurrentForm();
		for(int i = 0;i<4;i++)
		{
			int x = currentBlock.forms[formNum][i].getX();
			int y = currentBlock.forms[formNum][i].getY();
			canvas[x0+x][y+y0] = 0;
		}
	}
	
	/**
	 * 使当前的方块往下下落一格
	 */
	public void fall() {
		TetrisContext.logger.debug(getClass(), "try to fall");
		if(!blockFalling) return;
		save();
		try {
			removeLastBlock();
			tracePoint.increaseY();
			int x0 = tracePoint.getX();
			int y0 = tracePoint.getY();
			int formNum = currentBlock.getCurrentForm();
			for(int i = 0;i<4;i++)
			{
				int x = currentBlock.forms[formNum][i].getX();
				int y = currentBlock.forms[formNum][i].getY();
				canvas[x0+x][y+y0] = 1;
			}
			if(isCrash())
			{
				rollBack();
				blockFalling = false;
			}
			
		} catch (ArrayIndexOutOfBoundsException e) {
			rollBack();
			blockFalling = false;
			save();
		}
		
	}

	/**
	 * 消除满行的方块
	 */
	public void dispel() {
		for(int j = height -1; j>=0 ; j--)
		{
			boolean should = true;
			for(int i = width-1 ; i>=0 ; i--)
			{
				if(canvas[i][j]==0)
					should = false;
			}
			if(should)
			{
				for(int h = j; h>=1; h--)
					for(int k = 0;k<width;k++)
						canvas[k][h] = canvas[k][h-1];
				for(int k = 0;k>width;k++)
					canvas[k][0] = 0;
				j++;
			}
		}
	}
	
	/**
	 * 添加新的方块
	 * @param block
	 */
	public void addBlock(Block block)
	{
		if(!blockFalling)
			save();
		this.currentBlock = block;
		
		int x0 = pointToPutBlock.getX();
		int y0 = pointToPutBlock.getY();
		tracePoint.setX(x0);
		tracePoint.setY(y0);
		putBlock();
		if(isCrash())
		{	gameOver = true;
			TetrisContext.logger.debug(getClass(), "game over");
		}
		blockFalling = true;
	}
	
	/**
	 * 清空画布
	 */
	public void clearCanvas() {
		for(int i = 0;i<width;i++)
			for(int j = 0;j<height;j++)
				canvas[i][j] = 0;
	}
	
	/**
	 * 清空回滚画布
	 */
	public void clearLastCanvas() {
		for(int i = 0;i<width;i++)
			for(int j = 0;j<height;j++)
				lastCanvas[i][j] = 0;
	}

	/**
	 * 回退到上一个状态
	 */
	public void rollBack() {
		for(int i = 0;i<width;i++)
			for(int j = 0;j<height;j++)
				canvas[i][j] = lastCanvas[i][j];
	}
	
	/**
	 * 保存当前状态
	 */
	private void save() {
		for(int i = 0;i<width;i++)
			for(int j = 0;j<height;j++)
				lastCanvas[i][j] = canvas[i][j];
	}
	
	public Block getCurrentBlock() {
		return currentBlock;
	}
	public void setCurrentBlock(Block currentBlock) {
		this.currentBlock = currentBlock;
	}
	public int[][] getCanvas() {
		return canvas;
	}
	public void setCanvas(int[][] canvas) {
		this.canvas = canvas;
	}

	public boolean isBlockFalling() {
		return blockFalling;
	}

	public void setBlockFalling(boolean blockFalling) {
		this.blockFalling = blockFalling;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Point getPointToPutBlock() {
		return pointToPutBlock;
	}

	
	public void printCanvas() {
		System.out.println("//////////////////////////////");
		for(int i =0;i<height;i++)
		{
			for(int j = 0;j<width;j++)
			{
				System.out.print(""+canvas[j][i]);
			}
			System.out.print("     ");
			for(int j = 0;j<width;j++)
			{
				System.out.print(""+lastCanvas[j][i]);
			}
			System.out.println("");
		}
		System.out.println("//////////////////////////////");
	}
}
