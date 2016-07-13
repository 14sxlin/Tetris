package lin.gui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import lin.game.TetrisContext;

public class BlockCanvas extends Canvas {

	/**
	 * 
	 */
	private TetrisContext context;
	private int containerWidth ;
	private int containerHeight ;
	private int widthUnit ;
	private int heightUnit ;
	private Timer timer;
	int t = 0;
	private static final long serialVersionUID = 1L;
	public BlockCanvas(TetrisContext context) {
		this.context = context;
		timer = new Timer(context.getTimer().getDelay()/2, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!context.getContainer().isGameOver())
					paint(getGraphics());
				else timer.stop();
			}
		});
		timer.start();
		
		
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		containerWidth = context.getContainer().getWidth();
		containerHeight = context.getContainer().getHeight();
		widthUnit = this.getWidth()/containerWidth;
		heightUnit = this.getHeight()/containerHeight;
		
		//清空画布
		g.setColor(getBackground());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(getForeground());
		
		//画出坐标系
		for(int i = 0,offsetWidth = widthUnit;i<containerWidth;i++)
		{
			g.drawLine(offsetWidth, 0, offsetWidth, this.getHeight());
			offsetWidth+=widthUnit;
		}
		for(int i = 0,offsetHeight = heightUnit;i<containerHeight;i++)
		{
			g.drawLine(0,offsetHeight, this.getWidth(), offsetHeight);
			offsetHeight+=heightUnit;
		}
		
		//画出方块
		for(int i =  0;i<containerWidth;i++)
		for(int j = 0;j<containerHeight;j++)
		{
			if(context.getContainer().getCanvas()[i][j]==1)
			{
				g.fillRect(i*widthUnit, j*heightUnit, widthUnit, heightUnit);
			}
		}
		
//		context.getContainer().rollBack();
		
	}
	@Override
	public void update(Graphics g) {
		// TODO Auto-generated method stub
		super.update(g);
		// TODO System Output Test Block
		System.out.println(" update ");
		paint(g);
	}
		
}
