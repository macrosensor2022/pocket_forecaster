/*import  model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestStore {
    @Test
    void teststorecreation()
    {
        store s= new store("amazon");

        assertEquals("amazon", s.getName());
        assertTrue(s.getPhones().isEmpty());
    }

    @Test
    void testaddphone()
    {
        store s = new store("amazon");
        phone p1 = new phone("ROG8","ASUS",999,"Android","gaming","good mobile ","https://buy.com/rog8",85);
        phone p2 = new phone("Ace 3 ","oneplus",888,"Android","casual","mid range only ","https://buy.com/ace3",45);

        s.addPhone(p1);
        s.addPhone(p2);

        List<phone> phones = s.getPhones();
        assertEquals(2,phones.size());
        assertTrue(phones.contains(p1));
        assertTrue(phones.contains(p2));

    }

    @Test
    void testtostring()
    {
        store s = new store("amazon");
        phone p1 = new phone("ROG8","ASUS",999,"Android","gaming","good mobile ","https://buy.com/rog8",85);
        s.addPhone(p1);
        String expected= "Store: amazon| Phones: 1";
        assertEquals(expected,s.toString());
    }


    @Test
    void nullphone()
    {
        store s= new store("amazon");
        s.addPhone(null);
        assertEquals(1,s.getPhones().size());
        assertNull(s.getPhones().get(0));
    }


}*/
