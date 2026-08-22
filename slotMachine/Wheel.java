import java.util.Random;
/**
 * La clase Wheel representa una rueda que contiene un símbolo circular en su interior.
 * Se utiliza para simular ruedas con símbolos visibles como en una máquina tragamonedas
 * @author Juan David Rojas and César Morales
 * @version 1.0 (22 agosto 2026)
 */
public class Wheel
{
    private int visibleIndex;
    private Rectangle wheelFigure;
    private Circle symbolFigure;
    private int currentX;
    private int currentY;
     /**
     * Constructor de la clase Wheel.
     * Inicializa la rueda con un número de símbolos y asigna un índice visible aleatorio.
     * También crea las figuras gráficas (rectángulo y círculo) y las posiciona.
     *
     * @param cantSymbols número total de símbolos posibles en la rueda.
     *  Si es mayor que 0, se selecciona un índice aleatorio.
     *  Si es 0 o negativo, el índice visible será 0.
     */
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
    /**
     * Devuelve el índice del símbolo actualmente visible.
     *
     * @return índice del símbolo visible.
     */
    public int getVisibleIndex()
    {
        return visibleIndex;
    }
     /**
     * Establece manualmente el índice del símbolo visible.
     *
     * @param index nuevo índice del símbolo visible.
     */
    public void setVisibleIndex(int index)
    {
        visibleIndex = index;
    }
    /**
     * Muestra la rueda en pantalla. Si el parámetro es verdadero,
     * también muestra el símbolo dentro de la rueda.
     *
     * @param flag true para mostrar el símbolo, false para ocultarlo.
     */
    public void makeVisible(boolean flag){
        wheelFigure.makeVisible();
        if (flag){
            symbolFigure.makeVisible();
        }
    }
     /**
     * Cambia el color del símbolo circular dentro de la rueda.
     *
     * @param color nuevo color del símbolo (ej. "red", "blue", "green").
     */
    public void changeColor(String color){
        symbolFigure.changeColor(color);
    }
    /**
     * Establece la posición de la rueda en coordenadas (x, y).
     * Mueve tanto el rectángulo como el círculo a la nueva ubicación.
     *
     * @param x nueva posición en el eje X.
     * @param y nueva posición en el eje Y.
     */
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
    /**
     * Oculta la rueda y el símbolo de la pantalla.
     */
    public void makeInvisible(){
        wheelFigure.makeInvisible();
        symbolFigure.makeInvisible();
    }
}