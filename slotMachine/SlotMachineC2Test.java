import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class SlotMachineC2Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class SlotMachineC2Test
{
    private SlotMachine machine;
    /**
     * Default constructor for test class SlotMachineC2Test
     */
    public SlotMachineC2Test()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
        machine = new SlotMachine();
        
    }
    
    @Test
    public void shouldReturnEmptyConfigurationWhenThereAreNoWheels()
    {
        String[] config = machine.configuration();
    
        assertEquals(0, config.length);
    }
    
    @Test 
    public void shouldAddWheelAtEnd()
    {
        machine.addWheel(10);

        assertEquals(1, machine.configuration().length);
        assertTrue(machine.ok());
    }
    
    @Test
    public void shouldAddWheelAtBeginning()
    {
        machine.addWheel(0);
    
        assertEquals(1, machine.configuration().length);
        assertTrue(machine.ok());
    }
    
    @Test
    public void shouldAddWheelAtPosition()
    {
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.addWheel(2);
    
        assertEquals(3, machine.configuration().length);
        assertTrue(machine.ok());
    }
    
    @Test
    public void shouldNotDeleteWheelWhenThereAreNone()
    {
        machine.delWheel(1);
 
        assertFalse(machine.ok());
        assertEquals(0, machine.configuration().length);
    }
 
    @Test
    public void shouldDeleteFirstWheelWhenPositionLessThanOne()
    {
        machine.addWheel(1);
        machine.addWheel(2);
 
        machine.delWheel(0);
 
        assertEquals(1, machine.configuration().length);
        assertTrue(machine.ok());
    }
 
    @Test
    public void shouldDeleteLastWheelWhenPositionGreaterThanSize()
    {
        machine.addWheel(1);
        machine.addWheel(2);
 
        machine.delWheel(10);
 
        assertEquals(1, machine.configuration().length);
        assertTrue(machine.ok());
    }
    
    @Test
    public void shouldAddSymbolAtEnd()
    {
        machine.addSymbol(10, "red");
 
        assertEquals(1, machine.distinctSymbols());
        assertTrue(machine.ok());
    }
 
    @Test
    public void shouldAddSymbolAtBeginning()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
 
        String[] symbols = machine.symbols();
 
        assertEquals("blue", symbols[0]);
        assertTrue(machine.ok());
    }
 
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
 
    @Test
    public void shouldNotAddSymbolWithInvalidColor()
    {
        machine.addSymbol(1, "purple");
 
        assertEquals(0, machine.distinctSymbols());
        assertFalse(machine.ok());
    }
 
    @Test
    public void shouldNotAddDuplicateSymbol()
    {
        machine.addSymbol(1, "red");
 
        machine.addSymbol(2, "red");
 
        assertEquals(1, machine.distinctSymbols());
        assertFalse(machine.ok());
    }
    
       @Test
    public void shouldDeleteExistingSymbol()
    {
        machine.addSymbol(1, "red");
 
        machine.delSymbol("red");
 
        assertEquals(0, machine.distinctSymbols());
        assertTrue(machine.ok());
    }
 
    @Test
    public void shouldNotDeleteNonExistingSymbol()
    {
        machine.addSymbol(1, "red");
 
        machine.delSymbol("blue");
 
        assertEquals(1, machine.distinctSymbols());
        assertFalse(machine.ok());
    }
    
    
    
    
    
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
         machine = null;
    }
}