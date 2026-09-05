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
    private Rectangle brazoHorizontal;
    private Rectangle brazoVertical;
    private Circle perilla;
    private boolean girada;
    // Variables constantes para dejar espacio tanto arriba como a la derecha
    private static final int MARGIN_X = 60;
    private static final int MARGIN_Y = 30;
    private static final int GAP = 25;
    private static final int WHEEL_WIDTH = 70;
    
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
        girada = false;

        machine = new Rectangle();
        machine.changeColor("gray");
        machine.changeSize(120, 120);
        machine.moveHorizontal(MARGIN_X);
        machine.moveVertical(MARGIN_Y);
        
        brazoVertical = new Rectangle();
        brazoVertical.changeColor("gray");
        brazoVertical.changeSize(100, 15);             
        brazoVertical.moveHorizontal(MARGIN_X - 30);   
        brazoVertical.moveVertical(MARGIN_Y + 10);
        
        brazoHorizontal = new Rectangle();
        brazoHorizontal.changeColor("gray");
        brazoHorizontal.changeSize(15, 30);            
        brazoHorizontal.moveHorizontal(MARGIN_X - 30);
        brazoHorizontal.moveVertical(MARGIN_Y + 95);

        perilla = new Circle();
        perilla.changeColor("red");
        perilla.changeSize(30);
        perilla.moveHorizontal(MARGIN_X - 38);
        perilla.moveVertical(MARGIN_Y);

        actualizar();
        

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
        actualizar();
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
        actualizar();
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
        actualizar();
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
        actualizar();;
    }
    
    /**
     * Gira una rueda específica y la establece en un símbolo seleccionado
     * aleatoriamente, siempre que dicha rueda no esté bloqueada. La posición
     * de la rueda se ajusta a la primera o última rueda si la posición
     * indicada está fuera del rango válido. Si la rueda se encuentra
     * bloqueada (locked), no se modifica su símbolo visible.
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
        if (!wheels.get(wheel - 1).isLocked()){
            //.nextInt es un metodo de Random que genera un número entero aleatorio
            // entre 0 (incluido) y symbols.size() (excluido). Este metodo fue consultado desde la API de JAVA
            int randomIndex = random.nextInt(symbols.size());
            wheels.get(wheel - 1).setVisibleIndex(randomIndex);
        }
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
            girada = true;
        }
        if (!isJackpot()){
                actualizar();
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
            girada = true;
            ok = true;
            if (!isJackpot()){
                actualizar();
            }
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
            actualizar();
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
                girada = true;
                if (!isJackpot()){
                    actualizar();
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
            if (!symbols.isEmpty()){
                int idx = wheels.get(i).getVisibleIndex();
                config[i] = symbols.get(idx);
            }
            else{
                config[i] = null; 
            }
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
        if (!girada || wheels.size() < 2 || symbols.size() < 2){
            return false;
        }
        else if (config.length > 0){
            for (int i=1; i < config.length; i++){
                if (!config[i].equals(config[0])){
                    return false;
                }
            }
            machine.changeColor("green");
            brazoHorizontal.changeColor("green");
            brazoVertical.changeColor("green");
            perilla.changeColor("yellow");
            actualizar();
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
     * Actualiza el estado gráfico completo de la máquina tragamonedas.
     * 
     * Redimensiona el cuerpo de la máquina según la cantidad de ruedas
     * o al tamaño por defecto si no hay ninguna, reposiciona cada rueda
     * de forma igual y actualiza su color según el símbolo que
     * tiene actualmente visible. Finalmente, sincroniza la visibilidad de
     * todos los componentes gráficos (cuerpo, brazos, perilla y ruedas)
     * dependiendo de visble si esta en true o false.
     */
    private void actualizar(){
        if (wheels.isEmpty()){
            machine.changeSize(120, 120);
        }
        else {
            int anchoTotal = GAP * (wheels.size() + 1) + WHEEL_WIDTH * wheels.size();
            machine.changeSize(120, anchoTotal);
            for (int i=0; i < wheels.size(); i++){
                wheels.get(i).setPosition(MARGIN_X + GAP + i * (WHEEL_WIDTH + GAP), MARGIN_Y + 25);
                if (!symbols.isEmpty()){
                    int idx = wheels.get(i).getVisibleIndex();
                    wheels.get(i).changeColor(symbols.get(idx));
                }
            }
        }
    
        if (visible){
            machine.makeVisible();
            brazoVertical.makeVisible();
            brazoHorizontal.makeVisible();
            perilla.makeVisible();
            for (int i=0; i < wheels.size(); i++){
                wheels.get(i).makeVisible(!symbols.isEmpty());
            }
        }
        else {
            machine.makeInvisible();
            brazoVertical.makeInvisible();
            brazoHorizontal.makeInvisible();
            perilla.makeInvisible();
            for (int i=0; i < wheels.size(); i++){
                wheels.get(i).makeInvisible();
            }
        }
    }

    /**
     * Hace visible la máquina tragamonedas junto con todos sus componentes
     * gráficos (cuerpo, brazos, perilla y ruedas). 
     * Ademas cambia el valor de visble a true
     */
    public void makeVisible(){
        visible = true;
        actualizar();
        
    }
    
    /**
     * Hace invisible la máquina tragamonedas junto con todos sus componentes
     * gráficos (cuerpo, brazos, perilla y ruedas).
     * Ademas cambia el valor de visble a false
     */
    public void makeInvisible(){
        visible = false;
        actualizar();
    }
    
    /**
     * Comprueba si la máquina tragamonedas se encuentra actualmente visible o no.
     *
     * @return true si la máquina es visible o false en caso contrario
     */
    public boolean isVisible(){
        return visible;
    }
    
    /**
     * Bloquea la rueda indicada para que no sea modificada por los métodos
     * de giro (spin). Si la posición indicada es menor que uno, se bloquea
     * la primera rueda; si es mayor que el número de ruedas, se bloquea la
     * última, mostrando en ambos casos un mensaje informativo. La operación
     * solo se realiza si la máquina tiene al menos una rueda.
     *
     * @param wheel la posición de la rueda que se desea bloquear
     */ 
    public void lock(int wheel){
        if (!wheels.isEmpty()){
            boolean flag = false;
            String  mensaje  = "";
            if (wheel<1){
                wheel = 1;
                mensaje += "Se va a usar la primera rueda ya que el valor dado es una rueda en una posicion menor que 1.\n";
                flag = true;
            }
            else if (wheel > wheels.size()){
                wheel = wheels.size();
                mensaje += "Se va a usar la ultima rueda ya que el valor dado es una rueda que esta a fuera del tamaño.\n";
                flag = true;
            }
            wheels.get(wheel-1).setLocked(true);
            ok = true;
            if (flag){
                    JOptionPane.showMessageDialog(null, mensaje);
                } 
            actualizar();
        }
        else{
            JOptionPane.showMessageDialog(null, "Accion no permitida: No hay ruedas para bloquear.");
            ok = false;
        }
            
    }
    
    /**
     * Desbloquea la rueda indicada, permitiendo que vuelva a ser modificada
     * por los métodos de giro (spin). Si la posición indicada es menor que
     * uno, se desbloquea la primera rueda; si es mayor que el número de
     * ruedas, se desbloquea la última, mostrando en ambos casos un mensaje
     * informativo. La operación solo se realiza si la máquina tiene al
     * menos una rueda.
     *
     * @param wheel la posición de la rueda que se desea desbloquear
     */
    public void unlock(int wheel){
        if (!wheels.isEmpty()){
            boolean flag = false;
            String  mensaje  = "";
            if (wheel<1){
                wheel = 1;
                mensaje += "Se va a usar la primera rueda ya que el valor dado es una rueda en una posicion menor que 1.\n";
                flag = true;
            }
            else if (wheel > wheels.size()){
                wheel = wheels.size();
                mensaje += "Se va a usar la ultima rueda ya que el valor dado es una rueda que esta a fuera del tamaño.\n";
                flag = true;
            }      
            wheels.get(wheel - 1).setLocked(false);
            ok = true;
            if (flag){
                    JOptionPane.showMessageDialog(null, mensaje);
            } 
            actualizar();
        }
        else {
            JOptionPane.showMessageDialog(null, "Accion no permitida: No hay ruedas para desbloquear.");
            ok = false;
        }
    }
    
    public void swap(int wheel1, int wheel2){
        if (!wheels.isEmpty()){
            boolean flag = false; 
            String mensaje = "";
            if (wheel1<1){
                wheel1 = 1;
                mensaje += "Se va a intercambiar la primera rueda ya que el valor dado es una rueda en una posicion menor que 1.\n";
                flag = true;
            }
            if (wheel2<1){
                wheel2 = 1;
                mensaje += "Se va a intercambiar la primera rueda ya que el valor dado es una rueda en una posicion  menor que 1.\n";
                flag = true;
            }
            if (wheel1 > wheels.size()){
                wheel1 = wheels.size();
                mensaje += "Se va a intercambiar la ultima rueda ya que el valor dado es una rueda en una posicion que es mayor que el tamaño.\n";
                flag = true;
            }
            if (wheel2 > wheels.size()){
                wheel2 = wheels.size();
                mensaje += "Se va a intercambiar la ultima rueda ya que el valor dado es una rueda en una posicion que es mayor que el tamaño.\n";
                flag = true;
            }
            ok = true;
            if (!(wheel1 == wheel2)){
                int idx1 = wheels.get(wheel1 - 1).getVisibleIndex();
                int idx2 = wheels.get(wheel2 - 1).getVisibleIndex();                
                wheels.get(wheel1 - 1).setVisibleIndex(idx2);
                wheels.get(wheel2 - 1).setVisibleIndex(idx1);
                if (flag){
                    JOptionPane.showMessageDialog(null, mensaje);
                } 
                actualizar();
            }
            else {
                ok = false;
                JOptionPane.showMessageDialog(null, "Accion no permitida: Las ruedas son las mismas");
            }
        }
         else{
             JOptionPane.showMessageDialog(null, "Accion no permitida: No hay ruedas para cambiar.");
            ok = false;    
        }       
    }
    
    /**
     * Rota una rueda específica un número determinado de pasos, mostrando
     * el avance paso a paso para simular el efecto físico de rotación.
     * La posición de la rueda se ajusta a la primera o última rueda si la
     * posición indicada está fuera del rango válido
     *
     * @param wheel la posición de la rueda que se desea rotar
     * @param steps el número de pasos que debe avanzar la rueda
     */
    public void spin(int wheel, int steps){
        if (symbols.isEmpty() || wheels.isEmpty()){
            JOptionPane.showMessageDialog(null,
                "Accion no permitida: No se puede rotar la rueda porque esta vacia la ruleta o no hay simbolos disponibles.");
            ok = false;
            return;
        }
        if (wheel <= 1){
            wheel = 1;
        }
        if (wheel > wheels.size()){
            wheel = wheels.size();
        }
        Wheel selected = wheels.get(wheel - 1);
        for (int i = 0; i < steps; i++){
            selected.rotate(1, symbols.size());
            actualizar();
            Canvas.getCanvas().wait(100);
        }
        ok = true;
        isJackpot();
    }
    
    /**
     * Establece la configuración completa de la máquina tragamonedas de una
     * sola vez, asignando a cada rueda el símbolo correspondiente del arreglo
     * recibido, en el mismo orden de las ruedas.
     * La operación solo se realiza si el número de símbolos coincide con el
     * número de ruedas, y si todos los símbolos indicados existen entre los
     * símbolos disponibles.
     *
     * @param config un arreglo con el símbolo que se desea asignar a cada
     * rueda, en el mismo orden que las ruedas
     */
    public void setConfiguration(String[] config){
        if (wheels.isEmpty()){
            JOptionPane.showMessageDialog(null, "Accion no permitida: No hay ruedas en la maquina.");
            ok = false;
        }
        else if (config.length != wheels.size()){
            JOptionPane.showMessageDialog(null, "Accion no permitida: La cantidad de simbolos no coincide con la cantidad de ruedas.");
            ok = false;
        }
        else {
            boolean allValid = true;
            for (int i = 0; i < config.length; i++){
                if (!symbols.contains(config[i])){
                    allValid = false;
                }
            }
            if (!allValid){
                JOptionPane.showMessageDialog(null, "Accion no permitida: Uno o mas simbolos indicados no existen.");
                ok = false;
            }
    
            else {
                for (int i = 0; i < config.length; i++){
                    int index = symbols.indexOf(config[i]);
                    wheels.get(i).setVisibleIndex(index);
                }
                ok = true;
            }
        }
        if (!isJackpot()){
            actualizar();
        }
    }
}