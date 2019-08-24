import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
public class ToggleButton extends Canvas
{
	private boolean isSelected;
	private String lblName;
	private int fontSize=13;
	private String font="Calibri";//"Monospaced"
	private int xOffset=2,yOffset=20;
public ToggleButton(String label)
{
	lblName=label;
	isSelected=true;
	this.setSize(42, 42);
	repaint();
	//addMouseListener(this);
}
@Override
public void paint(Graphics g)
{
	if(isSelected)
		ifTrue(g);
	else
		ifFalse(g);
}
private void ifTrue(Graphics g)
{
	g.setColor(new Color(255, 255, 0));
	g.fillRect(0, 0, 40, 40);
	g.setColor(new Color(0,0,0));
	g.setFont(new Font(font, Font.PLAIN, fontSize));
	g.drawString(lblName, xOffset, yOffset);
}
private void ifFalse(Graphics g)
{
	g.setColor(new Color(0, 0, 0));
	g.fillRect(0, 0, 40, 40);
	g.setColor(new Color(255,255,255));
	g.setFont(new Font(font, Font.PLAIN, fontSize));
	g.drawString(lblName, xOffset, yOffset);
}
public void setSelected(boolean b)
{
	if(isSelected!=b)
	{
		isSelected=b;
		repaint();
	}
}
public boolean isSelected()
{
	return isSelected;
}
}
