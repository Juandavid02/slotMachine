import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.Random;
/**
 * Una máquina tragamonedas que puede contener múltiples ruedas y símbolos.
 * Las ruedas pueden girarse aleatoriamente o configurarse para mostrar
 * símbolos específicos. La máquina también puede detectar cuando ocurre
 * un jackpot.
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
    // Variables constantes para dejar espacio tanto arriba como a la derecha
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
     * Agrega una nueva rueda a la máquina tragamonedas en la posición indicada.
     * Si la posición es mayor que el número de ruedas, la rueda se agrega
     * al final. Si la posición es menor que uno, se agrega al principio.
     *
     * @param pos la posición deseada para la nueva rueda
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
     * Elimina una rueda de la máquina tragamonedas en la posición indicada.
     * Si la posición es menor que uno, se elimina la primera rueda.
     * Si la posición es mayor que el número de ruedas, se elimina la última
     * rueda.
     *
     * @param pos la posición de la rueda que se desea eliminar
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
     * Agrega un nuevo símbolo a la máquina tragamonedas en la posición indicada.
     * El símbolo solo se agrega si no existe previamente.
     * Si la posición es mayor que el número de símbolos, se agrega al final.
     * Si la posición es menor o igual a uno, se agrega al principio.
     *
     * @param pos la posición deseada para el nuevo símbolo
     * @param color el color del símbolo que se desea agregar. Los colores
     * disponibles son "red", "black", "blue", "yellow", "green", "white",
     * "orange" y "cyan".
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
     * Elimina un símbolo de la máquina tragamonedas.
     * Si el símbolo indicado no existe, la operación no se realiza
     * y se muestra un mensaje de error.
     *
     * @param color el color del símbolo que se desea eliminar
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
     * Gira una rueda específica y la establece en un símbolo seleccionado
     * aleatoriamente. La posición de la rueda se ajusta a la primera o
     * última rueda si la posición indicada está fuera del rango válido.
     *
     * @param wheel la posición de la rueda que se desea girar
     */
     // Este método es privado porque es una operación interna utilizada
     // por la máquina tragamonedas al realizar un giro y no debe ser
     // llamada directamente desde fuera de la clase.
    private void turnWheel(int wheel){
        if (wheel <= 1){
            wheel = 1;
        }
        if (wheel > wheels.size()){
            wheel = wheels.size();
        }
        //.nextInt es un metodo de Random que genera un número entero aleatorio
        // entre 0 (incluido) y symbols.size() (excluido). Este metodo fue consultado desde la API de JAVA
        int randomIndex = random.nextInt(symbols.size());
        wheels.get(wheel - 1).setVisibleIndex(randomIndex);
    }
    
    /**
     * Gira la rueda indicada de la máquina tragamonedas.
     * La rueda se establece en un símbolo seleccionado aleatoriamente.
     * La operación solo se realiza si hay ruedas y símbolos disponibles.
     *
     * @param wheel la posición de la rueda que se desea girar
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
     * Gira todas las ruedas de la máquina tragamonedas.
     * Cada rueda se establece en un símbolo seleccionado aleatoriamente.
     * La operación solo se realiza si hay ruedas y símbolos disponibles.
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
     * Coloca un símbolo específico en la rueda indicada.
     * El símbolo debe existir entre los símbolos disponibles.
     * Si la posición de la rueda es menor o igual a uno, se selecciona
     * la primera rueda. Si la posición es mayor que el número de ruedas,
     * se selecciona la última rueda.
     *
     * @param wheel la posición de la rueda donde se colocará el símbolo
     * @param symbol el símbolo que se desea colocar en la rueda
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
    
    /**
     * Obtiene la configuración actual de la máquina tragamonedas.
     * La configuración contiene el símbolo que se muestra actualmente
     * en cada rueda, en el mismo orden de las ruedas.
     *
     * @return un arreglo que contiene el símbolo actual de cada rueda
     */
    //String[] es un arreglo con tamaño fijo accediendo con config[i]
    public String[] configuration()
    {
        String [] config = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++){
            int idx = wheels.get(i).getVisibleIndex();
            config[i] = symbols.get(idx);
        }
        return config;
    }

    /**
     * Obtiene todos los símbolos disponibles en la máquina tragamonedas.
     *
     * @return un arreglo que contiene todos los símbolos disponibles
     */ 
    // Uso de IA generativa para comprender el uso de toArray().
    // Este método convierte la lista de símbolos en un arreglo de tipo String[].
    public String[] symbols()
    {
        return symbols.toArray(new String[symbols.size()]);
    }
    
    /**
     * Obtiene el número de símbolos diferentes disponibles en la máquina
     * tragamonedas.
     *
     * @return el número de símbolos diferentes
     */
    public int distinctSymbols()
    {
        return symbols.size();
    }

    /**
     * Comprueba si la máquina tragamonedas tiene un jackpot.
     * Un jackpot ocurre cuando hay al menos dos ruedas y todas muestran
     * el mismo símbolo. Si ocurre un jackpot, la máquina cambia su color
     * a verde y muestra un mensaje de felicitación.
     *
     * @return true si todas las ruedas muestran el mismo símbolo y hay
     * al menos dos ruedas; false en caso contrario
     */
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
    
    /**
     * Comprueba si la última operación fue exitosa.
     *
     * @return true si la última operación fue exitosa; false en caso contrario
     */
    public boolean ok()
    {
        return ok;
    }
    
    /**
     * Hace visible la máquina tragamonedas y sus ruedas.
     * El tamaño de la máquina se ajusta de acuerdo con el número de ruedas.
     * Cada rueda se posiciona y se muestra con su símbolo actual.
     * Si no hay símbolos disponibles, las ruedas se muestran sin un símbolo.
     */
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
    
    /**
     * Hace invisible la máquina tragamonedas y todas sus ruedas.
     */
    public void makeInvisible(){
        machine.makeInvisible();
        for (int i = 0; i < wheels.size(); i++){
            wheels.get(i).makeInvisible();
        }
        
    }
}    