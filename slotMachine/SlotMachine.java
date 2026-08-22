
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
        machine.changeColor("gray");
        machine.changeSize(120, 120);
        machine.moveHorizontal(MARGIN_X);
        machine.moveVertical(MARGIN_Y);
        makeVisible();
    }
    

    /**
     * 
     */
    public void addWheel(int pos)
    {   
        Wheel wheel = new Wheel(symbols.size());
        if (pos > wheels.size()){
            wheels.add(wheel);
            JOptionPane.showMessageDialog(null, "Se agrego una rueda en la ultima posicion");
        }
        else if (pos < 1){
            wheels.add(0, wheel);
            JOptionPane.showMessageDialog(null, "Se agrego una rueda en la primera posicion");
        }
        else {
            wheels.add(pos - 1, wheel);
        }
        ok=true;
        makeVisible();
    }
    
    /**
     * 
     */
    public void delWheel(int pos)
    {   
        if (!wheels.isEmpty()){
            if (pos < 1 ){
                wheels.get(0).makeInvisible();
                wheels.remove(0);
                JOptionPane.showMessageDialog(null, "Se elimino la primera rueda");
            }
            else if (pos > wheels.size()){
                wheels.get(wheels.size() - 1).makeInvisible();                
                wheels.remove(wheels.size() - 1);
                JOptionPane.showMessageDialog(null, "Se elimino la ultima rueda");
            }
            else{
                wheels.get(pos - 1).makeInvisible();
                wheels.remove(pos - 1);
            }
            ok = true;
        }
        else {
            JOptionPane.showMessageDialog(null, "Accion no permitida: No hay paredes para eliminar");
            ok = false;
        }
        makeVisible();
    }
    
    public void addSymbol(int pos, String color)
    {
        if (symbols.contains(color)){
            JOptionPane.showMessageDialog(null, "Accion no permitida: El color ya se encuentra entre las opciones");
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
        makeVisible();
    }
    
    public void delSymbol(String color)
    {
        ok = symbols.remove(color);
        if (!ok){
            JOptionPane.showMessageDialog(null, "Accion no permitida: No se puede eliminar el símbolo " + color + " porque no existe.");
        }
        makeVisible();
    }
    
    private void turnWheel(int wheel){
        if (wheel <= 1){
            wheel = 1;
        }
        if (wheel > wheels.size()){
            wheel = wheels.size();
        }
        int randomIndex = random.nextInt(symbols.size());
        wheels.get(wheel - 1).setVisibleIndex(randomIndex);
    }
    
    public void spin(int wheel)
    {
        if (symbols.isEmpty() || wheels.isEmpty()){
            JOptionPane.showMessageDialog(null, "Accion no permitida: No se puede girar la ruleta porque esta vacia la ruleta o no hay simbolos disponobles.");
            ok = false;
        }
        else{
            turnWheel(wheel);
            ok = true;
        }
        if (!isJackpot()){
                makeVisible();
        }
    }
    
    public void spin()
    {
        if (symbols.isEmpty() || wheels.isEmpty()){
            JOptionPane.showMessageDialog(null, "Accion no permitida: No se puede girar la ruleta porque esta vacia la ruleta o no hay ruletas disponobles.");
            ok = false;
        }
        else{
           for (int i = 1; i <= wheels.size(); i++){
                turnWheel(i);
            }
            if (!isJackpot()){
                makeVisible();
            }
            ok = true;
        }
    }
    
    public void placeSymbol(int wheel, String symbol)
    {
        if (wheels.isEmpty()){
            ok = false;
            JOptionPane.showMessageDialog(null, "Accion no permitida: No hay ruletas.");
            makeVisible();
        }
        else {
            int index = symbols.indexOf(symbol);
            if (index == -1){
                JOptionPane.showMessageDialog(null, "Accion no permitida: No se encontro el simbolo porque no existe.");
                ok = false;
            }
            else {
                if (wheel <= 1 ){
                    wheel = 1;
                }
                else if (wheel > wheels.size()){
                    wheel = wheels.size();
                }
                wheels.get(wheel-1).setVisibleIndex(index);
                ok = true;
                if (!isJackpot()){
                    makeVisible();
                }
            }
        }

    }
    
    public String[] configuration()
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
        if (wheels.size() < 2){
            return false;
        }
        else if (config.length > 0){
            for (int i=1; i < config.length; i++){
                if (!config[i].equals(config[0])){
                    return false;
                }
            }
            machine.changeColor("green");
            makeVisible();
            JOptionPane.showMessageDialog(null, "¡FELICIDADES HAS GANADO!");
            return true;
        }
        else{
            return false;
        }
    }
    
    public boolean ok()
    {
        return ok;
    }
    
    public void makeVisible(){
        if (wheels.isEmpty()){
            machine.changeSize(120, 120);
            machine.makeVisible();
        }
        else {
            machine.changeSize(120, 120*wheels.size());
            machine.makeVisible();
            for (int i=0; i < wheels.size(); i++){
                wheels.get(i).setPosition(MARGIN_X + ((i+1)*25)+(i*25)+(i*70), MARGIN_Y+25);
                if (!symbols.isEmpty()){
                    int idx = wheels.get(i).getVisibleIndex();
                    wheels.get(i).changeColor(symbols.get(idx));
                    wheels.get(i).makeVisible(true);
                }
                else{
                    wheels.get(i).makeVisible(false);
                }
            }
        }
    }
    
    public void makeInvisible(){
        machine.makeInvisible();
        for (int i = 0; i < wheels.size(); i++){
            wheels.get(i).makeInvisible();
        }
        
    }
}


    