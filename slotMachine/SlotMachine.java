
/**
 * Write a description of class SlotMachine here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SlotMachine
{
    private List<Wheel> wheels; 
    private boolean visible;
    private boolean ok;
    
    /**
     * Constructor for objects of class SlotMachine
     */
    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        visible = true;
        ok = true;
    }
    

    /**
     * 
     */
    public void addWheel(int pos)
    {   
        Wheel wheel = new Wheel();
        if (pos > wheels.size()){
            wheels.add(wheel);
        }
        else if (pos <= 1){
            wheels.add(0, wheel);
        }
        else {
            wheels.add(pos - 1, wheel);
        }
        ok=true;
    }
    
    /**
     * 
     */
    public void delWheel(int pos)
    {   
        if (!wheels.isEmpty()){
            if (pos <= 0 ){
                wheels.remove(0);
            }
            else if (pos > wheels.size()){
                wheels.remove(wheels.size() - 1);
            }
            else{
                wheels.remove(pos - 1);
            }
            ok = true;
        }
        else {
            ok = false;
        }
    }   
}