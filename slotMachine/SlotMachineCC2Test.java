import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas compartida entre los grupos
 * Ciclo 2 del proyecto SlotMachine.
 *
 * @author Juan David Rojas Heredia y César Morales Sandoval
 * @version 1.0 (05-09-2026)
 */
public class SlotMachineCC2Test
{
    private SlotMachine machine;

    /**
     * Constructor por defecto de la clase de pruebas SlotMachineCC2Test.
     * No realiza ninguna inicialización.
     */
    public SlotMachineCC2Test()
    {
    }

    /**
     * Prepara el escenario de cada prueba. Se ejecuta antes de cada
     * método de prueba, creando una nueva instancia de SlotMachine en
     * modo invisible, para garantizar que cada prueba comience con un
     * estado limpio e independiente y sin abrir ningún elemento gráfico.
     */
    @BeforeEach
    public void setUp()
    {
        machine = new SlotMachine();
        machine.makeInvisible();
    }
    
    /**
     * Verifica que, al intercambiar dos ruedas válidas con símbolos
     * distintos mediante swap(int, int), cada una termine mostrando el
     * símbolo que antes tenía la otra.
     */
    @Test
    public void accordingMsRhShouldSwapSymbolsBetweenTwoValidWheels(){
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");

        machine.swap(1, 2);

        assertEquals("blue", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
    }

    /**
     * Verifica que una rueda bloqueada con lock(int) no cambie su
     * símbolo visible al intentar girarla con spin(int).
     */
    @Test
    public void accordingMsRhShouldNotChangeLockedWheelWhenSpinning()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.placeSymbol(1, "red");

        machine.lock(1);
        machine.spin(1);

        assertEquals("red", machine.configuration()[0]);
    }

     /**
     * Libera el escenario de pruebas. Se ejecuta después de cada
     * método de prueba, eliminando la referencia a la instancia de
     * SlotMachine utilizada.
     */
    @AfterEach
    public void tearDown()
    {
        machine = null;
    }
}