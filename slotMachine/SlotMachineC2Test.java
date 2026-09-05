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
        
    
        @Test
    public void shouldNotSwapWhenThereAreNoWheels()
    {
        machine.swap(1, 2);
    
        assertFalse(machine.ok());
    }
    
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
    
    @Test
    public void shouldNotSwapWhenBothWheelsAreTheSame()
    {
        machine.addSymbol(1, "red");
        machine.addWheel(1);
    
        machine.swap(1, 1);
    
    
        assertFalse(machine.ok());
    }
    
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
    
    
        @Test
    public void shouldNotSetConfigurationWhenThereAreNoWheels()
    {
        machine.setConfiguration(new String[]{"red"});
    
        assertFalse(machine.ok());
    }
    
    @Test
    public void shouldNotSetConfigurationWhenSizeDoesNotMatchWheels()
    {
        machine.addSymbol(1, "red");
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.setConfiguration(new String[]{"red"});
    
        assertFalse(machine.ok());
    }
    
    @Test
    public void shouldNotSetConfigurationWithInvalidSymbol()
    {
        machine.addSymbol(1, "red");
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.setConfiguration(new String[]{"red", "purple"});
    
        assertFalse(machine.ok());
    }
    
    @Test
    public void shouldSetConfigurationSuccessfully()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.setConfiguration(new String[]{"red", "blue"});
    
        assertTrue(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }
    
    @Test
    public void shouldDetectJackpotWhenConfigurationIsForced()
    {
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.addWheel(1);
        machine.addWheel(2);
    
        machine.setConfiguration(new String[]{"red", "red"});
    
        assertTrue(machine.isJackpot());
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