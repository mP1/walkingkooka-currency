package test;


import com.google.j2cl.junit.apt.J2clTestInput;
import org.junit.Assert;
import org.junit.Test;

// copied parse Sample
@J2clTestInput(J2clTest.class)
public class J2clTest {

    @Test
    public void testAssertEquals() {
        checkEquals(
            "",
            ""
        );
    }
}


