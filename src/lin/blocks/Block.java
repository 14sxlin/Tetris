package lin.blocks;


public abstract class Block {
	protected int currentForm;
	public Point[][] forms;
	public Block() {
		currentForm = ((int)(Math.random()*4))%4;
	}
	public void transform() {
		currentForm=(currentForm+1)%forms.length;
	};
	public int getNextForm() {
		return (currentForm+1)%forms.length;
	}
	
	public int getCurrentForm() {
		return currentForm;
	}
	public void setCurrentForm(int form) {
		currentForm = form;
	}
	public void setRandomForm() {
		currentForm = ((int)(Math.random()*4));
	}
}
