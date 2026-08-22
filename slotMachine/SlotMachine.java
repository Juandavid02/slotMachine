import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Random;

/**
 * A slot machine that can contain multiple wheels and symbols.
 * The wheels can be spun randomly or configured to display
 * specific symbols. The machine can also detect when a jackpot occurs.
 *
 * @author Juan David Rojas and César Morales
 * @version 1.0 (22 August 2026)
 */
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
     * Creates a new slot machine.
     * Initializes the lists of reels and symbols, the random number generator,
     * and the machine's graphical representation.
     * Initially, the machine is visible, and the "last move" state indicates
     * it has been shifted by an equal amount both horizontally and vertically.
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
     * Add a new wheel to the slot machine at the specified position.
     * If the position is greater than the number of wheels, the wheel is
     * added at the end. If the position is less than one, it is added
     * at the beginning.
     *
     * @param pos the desired position for the new wheel
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
     * Remove a wheel from the slot machine at the specified position.
     * If the position is less than one, the first wheel is removed.
     * If the position is greater than the number of wheels, the last
     * wheel is removed.
     *
     * @param pos the desired position of the wheel to remove
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
    
    
    /**
     * Add a new symbol to the slot machine at the specified position.
     * The symbol is only added if it does not already exist.
     * If the position is greater than the number of symbols, it is added
     * at the end. If the position is less than or equal to one, it is
     * added at the beginning.
     *
     * @param pos the desired position for the new symbol
     * @param color the color of the symbol to add. Available colors are
     * "red", "black", "blue", "yellow", "green", "white", "orange", and "cyan".
     */
    
    public void addSymbol(int pos, String color)
    {
        if (!color.equals("red") &&
            !color.equals("black") &&
            !color.equals("blue") &&
            !color.equals("yellow") &&
            !color.equals("green") &&
            !color.equals("white") &&
            !color.equals("orange") &&
            !color.equals("cyan")){
            JOptionPane.showMessageDialog(null,
                "Accion no permitida: El color no esta disponible.");
            ok = false;
        }
        else if (symbols.contains(color)){
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
    
    /**
     * Remove a symbol from the slot machine.
     * If the specified symbol does not exist, the operation is not performed
     * and an error message is displayed.
     *
     * @param color the color of the symbol to remove
     */
    public void delSymbol(String color)
    {
        ok = symbols.remove(color);
        if (!ok){
            JOptionPane.showMessageDialog(null, "Accion no permitida: No se puede eliminar el símbolo " + color + " porque no existe.");
        }
        makeVisible();
    }
    
    /**
     * Spin a specific wheel and set it to a randomly selected symbol.
     * The wheel position is adjusted to the first or last wheel if the
     * specified position is outside the valid range.
     * @param wheel the position of the wheel to spin
     */
    // This method is private because it is an internal operation used by
    // the slot machine when performing a spin and should not be called
    // directly from outside the class.
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
    
    /**
     * Spin the specified wheel of the slot machine.
     * The wheel is set to a randomly selected symbol.
     * The operation is only performed if there are wheels and symbols available.
     *
     * @param wheel the position of the wheel to spin
     */
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
    
    /**
     * Spin all the wheels of the slot machine.
     * Each wheel is set to a randomly selected symbol.
     * The operation is only performed if there are wheels and symbols available.
     */
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
    
    
    /**
     * Place a specific symbol on the specified wheel.
     * The symbol must exist in the available symbols.
     * If the wheel position is less than or equal to one, the first wheel
     * is selected. If the position is greater than the number of wheels,
     * the last wheel is selected.
     *
     * @param wheel the position of the wheel where the symbol will be placed
     * @param symbol the symbol to place on the wheel
     */
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