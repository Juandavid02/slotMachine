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
    
    
    
    
    
    
    /**
     * Verifica que placeSymbol(int, String) rechace la operación cuando
     * la máquina no tiene ninguna rueda registrada, sin importar el
     * símbolo indicado.
     */
    @Test
    public void shouldNotPlaceSymbolWhenThereAreNoWheels()
    {
        machine.placeSymbol(1, "red");
    
        assertFalse(machine.ok());
    }
    
    /**
     * Verifica que placeSymbol(int, String) rechace la operación cuando
     * el símbolo indicado no existe entre los símbolos disponibles en
     * la máquina, aunque sí exista al menos una rueda.
     */
    @Test
    public void shouldNotPlaceSymbolWhenSymbolDoesNotExist()
    {
        machine.addWheel(1);
    
        machine.placeSymbol(1, "red");
    
        assertFalse(machine.ok());
    }
    
    /**
     * Verifica que placeSymbol(int, String) asigne correctamente el
     * símbolo indicado a la rueda especificada, cuando tanto el símbolo
     * como la posición de la rueda son válidos.
     */
    @Test
    public void shouldPlaceSymbolAtGivenWheel()
    {
        machine.addSymbol(1, "red");
        machine.addWheel(1);
    
        machine.placeSymbol(1, "red");
    
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }
    
    /**
     * Verifica que placeSymbol(int, String) ajuste automáticamente a la
     * primera rueda cuando la posición indicada es menor a uno, y
     * asigne el símbolo correctamente a esa rueda.
     */
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
    
    /**
     * Verifica que placeSymbol(int, String) ajuste automáticamente a la
     * última rueda cuando la posición indicada excede el número de
     * ruedas existentes, y asigne el símbolo correctamente a esa rueda.
     */
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
    

  
    
    
    
    
    /**
     * Verifica que symbols() retorne un arreglo vacío cuando la máquina
     * recién creada aún no tiene ningún símbolo agregado.
     */
    @Test
    public void shouldReturnEmptySymbolsWhenNoneAdded()
    {
        String[] symbols = machine.symbols();
    
        assertEquals(0, symbols.length);
    }
    
    /**
     * Verifica que symbols() retorne todos los símbolos agregados a la
     * máquina, respetando el mismo orden en que fueron insertados.
     */
    @Test
    public void shouldReturnAllAddedSymbolsInOrder()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
    
        String[] symbols = machine.symbols();
    
        assertEquals("red", symbols[0]);
        assertEquals("blue", symbols[1]);
    }
    
    
    
    
    
    
    /**
     * Verifica que distinctSymbols() retorne cero cuando la máquina
     * recién creada aún no tiene ningún símbolo agregado.
     */
    @Test
    public void shouldReturnZeroDistinctSymbolsInitially()
    {
        assertEquals(0, machine.distinctSymbols());
    }
    
    /**
     * Verifica que distinctSymbols() retorne la cantidad correcta de
     * símbolos diferentes después de agregar dos símbolos distintos
     * a la máquina.
     */
    @Test
    public void shouldReturnCorrectDistinctSymbolsCount()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
    
        assertEquals(2, machine.distinctSymbols());
    }
    
    
    
    
    
    
    /**
     * Verifica que isJackpot() retorne false cuando la máquina tiene
     * menos de dos ruedas, sin importar el símbolo que muestre la
     * única rueda existente, ya que se requieren al menos dos ruedas
     * para que un jackpot sea posible.
     */
    @Test
    public void shouldNotBeJackpotWithLessThanTwoWheels()
    {
        machine.addSymbol(1, "red");
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
    
        assertFalse(machine.isJackpot());
    }
    
    /**
     * Verifica que isJackpot() retorne true cuando hay al menos dos
     * ruedas y todas muestran exactamente el mismo símbolo.
     */
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
    
    
    /**
     * Verifica que isJackpot() retorne false cuando las ruedas muestran
     * símbolos distintos entre sí, aunque haya al menos dos ruedas y
     * más de un símbolo disponible en la máquina.
     */
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
     * Verifica que swap(int, int) rechace la operación cuando la máquina
     * no tiene ninguna rueda registrada.
     */
    @Test
    public void shouldNotSwapWhenThereAreNoWheels()
    {
        machine.swap(1, 2);
    
        assertFalse(machine.ok());
    }
    
    /**
     * Verifica que swap(int, int) intercambie correctamente los símbolos
     * visibles entre dos ruedas distintas y válidas, dejando cada rueda
     * con el símbolo que antes tenía la otra.
     */
    @Test
    public void shouldSwapSymbolsBetweenTwoWheels()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
    
        machine.swap(1, 2);
    
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
    }
    
    /**
     * Verifica que swap(int, int) rechace la operación cuando ambos
     * parámetros indicados corresponden a la misma rueda, ya que no
     * tiene sentido intercambiar una rueda consigo misma.
     */
    @Test
    public void shouldNotSwapWhenBothWheelsAreTheSame()
    {
        machine.addSymbol(1, "red");
        machine.addWheel(1);
    
        machine.swap(1, 1);
    
        assertFalse(machine.ok());
    }
    
    /**
     * Verifica que swap(int, int) ajuste automáticamente a la primera
     * rueda cuando la posición indicada es menor a uno, e intercambie
     * su símbolo correctamente con la otra rueda especificada.
     */
    @Test
    public void shouldAdjustFirstWheelWhenPositionLessThanOne()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
    
        machine.swap(0, 2);
    
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
    }
    
    /**
     * Verifica que swap(int, int) ajuste automáticamente a la última
     * rueda cuando la posición indicada excede el número de ruedas
     * existentes, e intercambie su símbolo correctamente con la otra
     * rueda especificada.
     */
    @Test
    public void shouldAdjustLastWheelWhenPositionGreaterThanSize()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
    
        machine.swap(1, 10);
    
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
        assertEquals("red", machine.configuration()[1]);
    }
    
    /**
     * Verifica que swap(int, int) rechace por completo la operación
     * cuando al menos una de las dos ruedas involucradas está bloqueada,
     * y que ninguna de las dos ruedas cambie su símbolo original como
     * resultado del intento.
     */
    @Test
    public void shouldNotSwapWhenOneWheelIsLocked()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.lock(1);
    
        machine.swap(1, 2);
    
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }
    
    
    

    
    
        /**
     * Verifica que spin(String[]) rechace la operación cuando la máquina
     * no tiene ninguna rueda registrada, sin importar el contenido del
     * arreglo de símbolos recibido.
     */
    @Test
    public void shouldNotSpinConfigurationWhenThereAreNoWheels()
    {
        machine.spin(new String[]{"red"});
    
        assertFalse(machine.ok());
    }
    
    /**
     * Verifica que spin(String[]) rechace la operación cuando la cantidad
     * de símbolos del arreglo recibido no coincide con la cantidad de
     * ruedas existentes en la máquina.
     */
    @Test
    public void shouldNotSpinConfigurationWhenSizeDoesNotMatchWheels()
    {
        machine.addSymbol(1, "red");
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.spin(new String[]{"red"});
    
        assertFalse(machine.ok());
    }
    
    /**
     * Verifica que spin(String[]) rechace la operación cuando alguno de
     * los símbolos indicados en el arreglo no existe entre los símbolos
     * disponibles en la máquina.
     */
    @Test
    public void shouldNotSpinConfigurationWithInvalidSymbol()
    {
        machine.addSymbol(1, "red");
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.spin(new String[]{"red", "purple"});
    
        assertFalse(machine.ok());
    }
    
    /**
     * Verifica que spin(String[]) asigne correctamente cada símbolo del
     * arreglo a su rueda correspondiente, en el mismo orden, cuando la
     * cantidad de símbolos coincide con la cantidad de ruedas y todos
     * los símbolos son válidos.
     */
    @Test
    public void shouldSpinConfigurationSuccessfully()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.spin(new String[]{"red", "blue"});
    
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }
    
    /**
     * Verifica que, al forzar una configuración donde todas las ruedas
     * quedan con el mismo símbolo mediante spin(String[]), isJackpot()
     * detecte correctamente el jackpot resultante.
     */
    @Test
    public void shouldDetectJackpotWhenConfigurationIsForced()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.spin(new String[]{"red", "red"});
    
        assertTrue(machine.isJackpot());
    }
    
    /**
     * Verifica que spin(String[]) omita la asignación de símbolo en una
     * rueda bloqueada, dejando su símbolo original intacto, mientras que
     * las ruedas no bloqueadas sí reciben el símbolo indicado en el
     * arreglo. La operación se considera exitosa (ok = true) aunque una
     * rueda haya sido omitida por estar bloqueada.
     */
    @Test
    public void shouldSkipLockedWheelWhenSpinningConfiguration()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "blue");
        machine.lock(2);
    
        machine.spin(new String[]{"red", "red"});
    
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }

    
    
    
    
    
    
    @Test
    public void shouldNotSpinStepsWhenThereAreNoWheelsOrSymbols()
    {
        machine.spin(1, 3);
    
        assertFalse(machine.ok());
    }
    
    @Test
    public void shouldSpinStepsSuccessfully()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
    
        machine.spin(1, 2);
    
        assertTrue(machine.ok());
    }
    
    @Test
    public void shouldNotSpinStepsWhenWheelIsLocked()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.placeSymbol(1, "red");
        machine.lock(1);
    
        machine.spin(1, 3);
    
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }
    
    
    
    
    
    
    @Test
    public void shouldBeOkInitially()
    {
        assertTrue(machine.ok());
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