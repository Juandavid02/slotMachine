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