
/**
 * Write a description of class Wheel here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.Random;

public class Wheel
{
    private int visibleIndex;
    private Rectangle wheelFigure;
    private Circle symbolFigure;
    private int currentX;
    private int currentY;
    
    public Wheel(int cantSymbols)
    {
        Random random = new Random();
        if (cantSymbols > 0){
            visibleIndex = random.nextInt(cantSymbols);
        } else {
            visibleIndex = 0;
        }
        wheelFigure = new Rectangle();
        symbolFigure = new Circle();
        wheelFigure.changeSize(70, 70);
        currentX = 0;
        currentY = 0;
        symbolFigure.moveHorizontal(20);
        symbolFigure.moveVertical(20);
    }

    public int getVisibleIndex()
    {
        return visibleIndex;
    }

    public void setVisibleIndex(int index)
    {
        visibleIndex = index;
    }
    
    public void makeVisible(boolean flag){
        wheelFigure.makeVisible();
        if (flag){
            symbolFigure.makeVisible();
        }
    }
    
    public void changeColor(String color){
        symbolFigure.changeColor(color);
    }
    
    public void setPosition(int x, int y)
    {
        wheelFigure.moveHorizontal(-currentX);
        wheelFigure.moveVertical(-currentY);
        symbolFigure.moveHorizontal(-currentX);
        symbolFigure.moveVertical(-currentY);
        wheelFigure.moveHorizontal(x);
        wheelFigure.moveVertical(y);
        symbolFigure.moveHorizontal(x);
        symbolFigure.moveVertical(y);
        currentX = x;
        currentY = y;
    }
    
    public void makeInvisible(){
        wheelFigure.makeInvisible();
        symbolFigure.makeInvisible();
    }
}