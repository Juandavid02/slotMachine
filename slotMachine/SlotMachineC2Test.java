import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas unitarias para SlotMachine.
 * Verifica el comportamiento del ciclo 1 y 2: agregar/eliminar ruedas y
 * símbolos, ubicar y girar símbolos, bloqueo/desbloqueo de ruedas, y
 * detección de jackpot, incluyendo los casos límites en las posiciones
 * fuera de rango para revisar como esta validandoesos datos el sistema.
 *
 * @author Juan David Rojas y César Morales
 * @version 1.0 (05-09-2026)
 */
public class SlotMachineC2Test
{
    private SlotMachine machine;
    /**
     * Constructor por defecto de la clase de pruebas SlotMachineC2Test.
     * No realiza ninguna inicialización.
     */
    public SlotMachineC2Test()
    {
    }

    /**
     * Prepara el escenario de cada prueba.
     * Se ejecuta antes de cada método de prueba, creando una nueva instancia
     * de SlotMachine para garantizar que cada prueba comience con un
     * estado limpio e independiente.
     */
    @BeforeEach
    public void setUp()
    {
        machine = new SlotMachine();   
    }
    
    
    //Pruebas del metodo configuration()
    
    /**
     * Verifica que {@code configuration()} retorne un arreglo vacío cuando
     * la máquina tragamonedas no tiene ninguna rueda.
     */
    @Test
    public void shouldReturnEmptyConfigurationWhenThereAreNoWheels()
    {
        String[] config = machine.configuration();
    
        assertEquals(0, config.length);
    }
    
    
    //Pruebas del metodo addWheel(int pos)
    
    /**
     * Verifica que {@code addWheel(int pos)} agregue la rueda al final
     * cuando la posición indicada es mayor que el número actual de ruedas.
     */
    @Test 
    public void shouldAddWheelAtEnd()
    {
        machine.addWheel(10);
        
        assertEquals(1, machine.configuration().length);
        assertTrue(machine.ok());
    }
    
    /**
     * Verifica que {@code addWheel(int pos)} agregue la rueda al principio
     * cuando la posición indicada es menor que uno.
     */    
    @Test
    public void shouldAddWheelAtBeginning()
    {
        machine.addWheel(0);
    
        assertEquals(1, machine.configuration().length);
        assertTrue(machine.ok());
    }
    
    /**
     * Verifica que {@code addWheel(int pos)} agregue la rueda en la posición
     * intermedia indicada, desplazando las ruedas existentes.
     */    
    @Test
    public void shouldAddWheelAtPosition()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addWheel(2);
    
        assertEquals(3, machine.configuration().length);
        assertTrue(machine.ok());
    }
    
    
    //Pruebas del metodo delWheel(int pos)
    
    /**
     * Verifica que {@code delWheel(int pos)} no realice ninguna eliminación
     * y marque la operación como no exitosa cuando la máquina no tiene
     * ninguna rueda.
     */
    @Test
    public void shouldNotDeleteWheelWhenThereAreNone()
    {
        machine.delWheel(1);
 
        assertFalse(machine.ok());
        assertEquals(0, machine.configuration().length);
    }
 
    /**
     * Verifica que {@code delWheel(int pos)} elimine la primera rueda
     * cuando la posición indicada es menor que uno.
     */    
    @Test
    public void shouldDeleteFirstWheelWhenPositionLessThanOne()
    {
        machine.addWheel(1);
        machine.addWheel(2); 
        machine.delWheel(0);
 
        assertEquals(1, machine.configuration().length);
        assertTrue(machine.ok());
    }
 
    /**
     * Verifica que {@code delWheel(int pos)} elimine la última rueda
     * cuando la posición indicada es mayor que el número de ruedas.
     */
    @Test
    public void shouldDeleteLastWheelWhenPositionGreaterThanSize()
    {
        machine.addWheel(1);
        machine.addWheel(2); 
        machine.delWheel(10);
 
        assertEquals(1, machine.configuration().length);
        assertTrue(machine.ok());
    }
    
    
    // Pruebas del metodo addSymbol(int pos, String color)}
    
    /**
     * Verifica que {@code addSymbol(int pos, String color)} agregue el
     * símbolo al final cuando la posición indicada es mayor que el número
     * actual de símbolos.
     */    
    @Test
    public void shouldAddSymbolAtEnd()
    {
        machine.addSymbol(10, "red");
 
        assertEquals(1, machine.distinctSymbols());
        assertTrue(machine.ok());
    }

    /**
     * Verifica que {@code addSymbol(int pos, String color)} agregue el
     * símbolo al principio cuando la posición indicada es menor o igual
     * a uno.
     */
    @Test
    public void shouldAddSymbolAtBeginning()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
 
        String[] symbols = machine.symbols();
        assertEquals("blue", symbols[0]);
        assertTrue(machine.ok());
    }

    /**
     * Verifica que {@code addSymbol(int pos, String color)} agregue el
     * símbolo en la posición intermedia indicada, desplazando los
     * símbolos existentes.
     */
    @Test
    public void shouldAddSymbolAtPosition()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addSymbol(2, "green");
 
        String[] symbols = machine.symbols();
        assertEquals("green", symbols[1]);
        assertTrue(machine.ok());
    }
    
    /**
     * Verifica que {@code addSymbol(int pos, String color)} no agregue el
     * símbolo y marque la operación como no exitosa cuando el color
     * indicado no está entre los colores disponibles.
     */    
    @Test
    public void shouldNotAddSymbolWithInvalidColor()
    {
        machine.addSymbol(1, "purple");
 
        assertEquals(0, machine.distinctSymbols());
        assertFalse(machine.ok());
    }
     
    /**
     * Verifica que {@code addSymbol(int pos, String color)} no agregue un
     * símbolo duplicado y marque la operación como no exitosa cuando el
     * color ya existe entre los símbolos disponibles.
     */
    @Test
    public void shouldNotAddDuplicateSymbol()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");
 
        assertEquals(1, machine.distinctSymbols());
        assertFalse(machine.ok());
    }
    
    
    // Pruebas del metodo delSymbol(String color)
    
    /**
    * Verifica que {@code delSymbol(String color)} elimine correctamente
    * un símbolo que sí existe entre los símbolos disponibles.
    */
    @Test
    public void shouldDeleteExistingSymbol()
    {
        machine.addSymbol(1, "red"); 
        machine.delSymbol("red");
 
        assertEquals(0, machine.distinctSymbols());
        assertTrue(machine.ok());
    }
    
    /**
     * Verifica que {@code delSymbol(String color)} no realice ninguna
     * eliminación y marque la operación como no exitosa cuando el símbolo
     * indicado no existe entre los símbolos disponibles.
     */ 
    @Test
    public void shouldNotDeleteNonExistingSymbol()
    {
        machine.addSymbol(1, "red");
        machine.delSymbol("blue");
 
        assertEquals(1, machine.distinctSymbols());
        assertFalse(machine.ok());
    }
    
    
    // Pruebas del metodo spin(int wheel)
    
    /**
     * Verifica que {@code spin(int wheel)} no realice ningún giro y marque
     * la operación como no exitosa cuando no hay ruedas ni símbolos
     * disponibles.
     */
    @Test
    public void shouldNotSpinWheelWhenThereAreNoWheelsOrSymbols()
    {
        machine.spin(1);
    
        assertFalse(machine.ok());
    }
    
    /**
     * Verifica que {@code spin(int wheel)} gire correctamente una rueda
     * existente cuando hay ruedas y símbolos disponibles, dejando la
     * operación como exitosa.
     */
    @Test
    public void shouldSpinExistingWheel()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.spin(1);
    
        assertTrue(machine.ok());
        assertNotNull(machine.configuration()[0]);
    }
    
    /**
     * Verifica que {@code spin(int wheel)} no modifique el símbolo visible
     * de una rueda que se encuentra bloqueada.
     */
    @Test
    public void shouldNotChangeSymbolOfLockedWheelWhenSpinning()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.lock(1);
        machine.spin(1);
    
        assertEquals("red", machine.configuration()[0]);
        assertTrue(machine.ok());
    }
    
    /**
     * Verifica que {@code spin(int wheel)} ajuste la posición a la primera
     * rueda cuando el valor indicado es menor que uno.
     */
    @Test
    public void shouldSpinFirstWheelWhenPositionLessThanOne()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.spin(0);
    
        assertTrue(machine.ok());
    }
    
    /**
     * Verifica que {@code spin(int wheel)} ajuste la posición a la última
     * rueda cuando el valor indicado es mayor que el número de ruedas.
     */
    @Test
    public void shouldSpinLastWheelWhenPositionGreaterThanSize()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
    
        machine.spin(10);
    
        assertTrue(machine.ok());
    }
    
    
    // Pruebas del metodo spin()
    
    /**
     * Verifica que {@code spin()} no realice ningún giro y marque la
     * operación como no exitosa cuando no hay ruedas ni símbolos
     * disponibles.
     */
    @Test
    public void shouldNotSpinAllWheelsWhenThereAreNoWheelsOrSymbols()
    {
        machine.spin();
    
        assertFalse(machine.ok());
    }
    
    /**
     * Verifica que {@code spin()} gire todas las ruedas existentes,
     * dejando cada una con un símbolo válido y la operación como exitosa.
     */
    @Test
    public void shouldSpinAllWheels()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.spin();
    
        assertTrue(machine.ok());
        assertNotNull(machine.configuration()[0]);
        assertNotNull(machine.configuration()[1]);
    }
    
    /**
     * Verifica que {@code spin()} no modifique el símbolo visible de una
     * rueda bloqueada, dejando las demás ruedas con un símbolo válido.
     */
    @Test
    public void shouldNotChangeSymbolOfLockedWheelWhenSpinningAll()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.lock(1);
        machine.spin();
    
        assertEquals("red", machine.configuration()[0]);
        assertTrue(machine.ok());
    }
    
    
    // Pruebas de visibilidad
    
    /**
     * Verifica que {@code makeVisible()} deje la máquina tragamonedas en
     * estado visible.
     */
    @Test
    public void shouldVisble(){
        machine.makeVisible();
        assertTrue(machine.isVisible());
        
    }
        
    /**
     * Verifica que {@code makeInvisible()} deje la máquina tragamonedas en
     * estado no visible.
     */    
    @Test
    public void shouldInVisble(){
        machine.makeInvisible();
        assertFalse(machine.isVisible());
        
    }
    
    
    //Pruebas del metodo lock(int wheel)
    
    /**
     * Verifica que {@code lock(int wheel)} no realice ninguna acción y
     * marque la operación como no exitosa cuando la máquina no tiene
     * ninguna rueda.
     */    
    @Test
    public void shouldNotLockWheelWhenThereAreNone()
    {
        machine.lock(1);
 
        assertFalse(machine.ok());
    }
    
    /**
    * Verifica que {@code lock(int wheel)} bloquee la primera rueda cuando
    * la posición indicada es menor que uno, y que dicha rueda permanezca
    * con su símbolo original tras intentar girarla.
    */
    @Test
    public void shouldLockFirstWheelWhenPositionLessThanOne()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
 
        machine.lock(0);
        machine.spin(1);
 
        assertEquals("red", machine.configuration()[0]);
        assertTrue(machine.ok());
    }
    
    /**
     * Verifica que {@code lock(int wheel)} bloquee la última rueda cuando
     * la posición indicada es mayor que el número de ruedas, y que dicha
     * rueda permanezca con su símbolo original tras intentar girarla.
     */    
    @Test
    public void shouldLockLastWheelWhenPositionGreaterThanSize()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(2, "blue");
 
        machine.lock(10);
        machine.spin(2);
 
        assertEquals("blue", machine.configuration()[1]);
        assertTrue(machine.ok());
    }
 
    
    //Pruebas del metodo unlock(int wheel)
    
    /**
     * Verifica que {@code unlock(int wheel)} no realice ninguna acción y
     * marque la operación como no exitosa cuando la máquina no tiene
     * ninguna rueda.
     */    
    @Test
    public void shouldNotUnlockWheelWhenThereAreNone()
    {
        machine.unlock(1);
 
        assertFalse(machine.ok());
    }
    
    /**
     * Verifica que {@code unlock(int wheel)} desbloquee la primera rueda
     * cuando la posición indicada es menor que uno, permitiendo que
     * {@code spin} vuelva a modificarla.
     */
    @Test
    public void shouldUnlockFirstWheelWhenPositionLessThanOne()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "red");
        machine.lock(1);
 
        machine.unlock(0);
        machine.spin(1);
 
        assertTrue(machine.ok());
    }
    
    /**
     * Verifica que {@code unlock(int wheel)} desbloquee la última rueda
     * cuando la posición indicada es mayor que el número de ruedas,
     * permitiendo que {@code spin} vuelva a modificarla.
     */
    @Test
    public void shouldUnlockLastWheelWhenPositionGreaterThanSize()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(2, "blue");
        machine.lock(2);
 
        machine.unlock(10);
        machine.spin(2);
 
        assertTrue(machine.ok());
    }
    
    
    @Test
    public void shouldNotPlaceSymbolWhenThereAreNoWheels()
    {
        machine.placeSymbol(1, "red");
    
        assertFalse(machine.ok());
    }
    
    @Test
    public void shouldNotPlaceSymbolWhenSymbolDoesNotExist()
    {
        machine.addWheel(1);
    
        machine.placeSymbol(1, "red");
    
        assertFalse(machine.ok());
    }
    
    @Test
    public void shouldPlaceSymbolAtGivenWheel()
    {
        machine.addSymbol(1, "red");
        machine.addWheel(1);
    
        machine.placeSymbol(1, "red");
    
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }
    
    @Test
    public void shouldPlaceSymbolAtFirstWheelWhenPositionLessThanOne()
    
    {
        machine.addSymbol(1, "red");
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.placeSymbol(0, "red");
    
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }
    
    @Test
    public void shouldPlaceSymbolAtLastWheelWhenPositionGreaterThanSize()
    {
        machine.addSymbol(1, "red");
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.placeSymbol(10, "red");
    
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[1]);
    }
    
    @Test
    public void shouldReturnEmptySymbolsWhenNoneAdded()
    {
        String[] symbols = machine.symbols();
    
        assertEquals(0, symbols.length);
    }
    
    @Test
    public void shouldReturnAllAddedSymbolsInOrder()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
    
        String[] symbols = machine.symbols();
    
        assertEquals("red", symbols[0]);
        assertEquals("blue", symbols[1]);
    }
    
    
    @Test
    public void shouldReturnZeroDistinctSymbolsInitially()
    {
        assertEquals(0, machine.distinctSymbols());
    }
    
    @Test
    public void shouldReturnCorrectDistinctSymbolsCount()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
    
        assertEquals(2, machine.distinctSymbols());
        
    }
    
    
    @Test
    public void shouldNotBeJackpotWithLessThanTwoWheels()
    {
        machine.addSymbol(1, "red");
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
    
        assertFalse(machine.isJackpot());
    }
    
    @Test
    public void shouldBeJackpotWhenAllWheelsShowSameSymbol()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");

        assertTrue(machine.isJackpot());
    }
    
    @Test
    public void shouldNotBeJackpotWhenWheelsShowDifferentSymbols()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
    
        assertFalse(machine.isJackpot());
    }
    
    /**
     * Libera el escenario de pruebas.
     * Se ejecuta después de cada método de prueba, eliminando la
     * referencia a la instancia de SlotMachine utilizada.
     */
    @AfterEach
    public void tearDown()
    {
         machine = null;
    }
}