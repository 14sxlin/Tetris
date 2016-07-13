package lin.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import lin.blocks.Block;
import lin.logger.MyLogger;

public class TetrisContext  {
	public static  MyLogger logger = new MyLogger();
	
	private int state = GameState.STOP;
	private BlockContainer container;
	private BlockGenerator generator;
	private Timer timer;
	public TetrisContext() {
		container = new BlockContainer(15, 15);
		generator = new BlockGenerator();
		
		timer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!container.isBlockFalling())
				{
					Block block = generator.generateBlock();
					container.addBlock(block);
				}
				container.fall();
				if(container.checkDispel())
					container.dispel();
				if(container.isGameOver())
				{
					logger.debug(getClass(), "game over");
					timer.stop();
				}
				
				container.printCanvas();
			}
		});
		
	}
	
	public void start() {
		timer.start();
		if(timer.isRunning())
			state = GameState.START;
	}
	
	public void pause() {
		if(state==GameState.START)
		{	timer.stop();
			state = GameState.PAUSE;
		}
	}
	
	public void resume() {
		if(state==GameState.PAUSE)
		{
			timer.restart();
			state = GameState.START;
		}
	}
	
	public void stop() {
		timer.stop();
		state = GameState.STOP;
	}
	
	/**
	 * 控制下落的速度
	 * @param delay
	 */
	public void setSpeed(int delay) {
		timer.setDelay(delay);
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public BlockContainer getContainer() {
		return container;
	}

	public void setContainer(BlockContainer container) {
		this.container = container;
	}

	public BlockGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(BlockGenerator generator) {
		this.generator = generator;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
}
