package lin.gui;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import lin.game.BlockContainer;
import lin.game.TetrisContext;

public class TestFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	public TestFrame(TetrisContext context,Canvas canvas) {
		this.setBounds(0, 0, 300, 500);
		this.setCanvas(canvas);
		this.getContentPane().add(canvas);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.addKeyListener(new KeyListener() {
			BlockContainer container = context.getContainer();
			@Override
			public void keyTyped(KeyEvent e) {
				System.out.println("type ");
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_UP)
				{
					container.transform();
				}
				if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					container.move(-1);
				}
				if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					container.move(1);
				}
				if(e.getKeyCode()==KeyEvent.VK_DOWN)
				{
					container.fall();
				}
				canvas.paint(getGraphics());
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("pressed");
			}
		});
	}
	public static void main(String[] args) {
		TetrisContext context = new TetrisContext();
		TestFrame frame = new TestFrame(context,new BlockCanvas(context));
		context.start();
		frame.setVisible(true);

	}
	public Canvas getCanvas() {
		return canvas;
	}
	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

}
