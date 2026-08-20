
/**
 * Write a description of class SlotMachine here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Random;

public class SlotMachine
{
    private List<Wheel> wheels; 
    private List<String> symbols;
    private boolean visible;
    private boolean ok;
    private Random random;
    private Rectangle machine;
    private static final int MARGIN_X = 30;
    private static final int MARGIN_Y = 30;
    
    /**
     * Constructor for objects of class SlotMachine
     */
    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        symbols = new ArrayList<String>();
        visible = true;
        ok = true;
        random = new Random();
        machine = new Rectangle();
        machine.changeColor("black");
        machine.changeSize(120, 120);
        machine.moveHorizontal(MARGIN_X);
        machine.moveVertical(MARGIN_Y);
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
            if (pos <= 1 ){
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
    
    public void addSymbol(int pos, String color)
    {
        if (symbols.contains(color)){
            ok = false;
        } 
        else {
                if (pos > symbols.size()){
                    symbols.add(color);
                }
                else if (pos <= 1){
                    symbols.add(0, color);
                }
                else {
                    symbols.add(pos - 1, color);
                }
            ok = true;
        }
    }
    
    public void delSymbol(String color)
    {
        ok = symbols.remove(color);
    }
    
    public void spin(int wheel)
    {
        if (symbols.isEmpty() || wheels.isEmpty()){
            ok = false;
        }
        else{
            if (wheel <= 1 ){
                wheel = 1;
            }
            if (wheel > wheels.size()){
                wheel = wheels.size();
            }
            int randomIndex = random.nextInt(symbols.size());
            wheels.get(wheel - 1).setVisibleIndex(randomIndex);
            ok = true;
        }
    }
    
    public void spin()
    {
        for (int i = 1; i <= wheels.size(); i++){
            spin(i);
        }
    }
    
    public void placeSymbol(int wheel, String symbol)
    {
        if (wheels.isEmpty()){
            ok = false;
        }
        else {
            int index = symbols.indexOf(symbol);
            if (index == -1){
                ok = false;
            }
            else {
                if (wheel <= 1 ){
                    wheel = 1;
                }
                if (wheel > wheels.size()){
                    wheel = wheels.size();
                }
                wheels.get(wheel-1).setVisibleIndex(index);
                ok = true;
            }
        }
    }
    
    public String [] configuration()
    {
        String [] config = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++){
            int idx = wheels.get(i).getVisibleIndex();
            config[i] = symbols.get(idx);
        }
        return config;
    }

    public String[] symbols()
    {
        return symbols.toArray(new String[symbols.size()]);
    }
    
    public int distinctSymbols()
    {
        return symbols.size();
    }

    public boolean isJackpot(){
        String [] config = configuration();
        if (config.length > 0){
            for (int i=1; i < config.length; i++){
                if (!config[i].equals(config[0])){
                    return false;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    
    public void makeVisible(){
        machine.changeSize(120, 120*wheels.size());
        machine.makeVisible();
        for (int i=0; i < wheels.size(); i++){
            int idx = wheels.get(i).getVisibleIndex();
            wheels.get(i).changeColor(symbols.get(idx));
            wheels.get(i).setPosition(MARGIN_X + ((i+1)*25)+(i*25)+(i*70), MARGIN_Y+25);
            wheels.get(i).makeVisible();
        }
        
    }
}


    