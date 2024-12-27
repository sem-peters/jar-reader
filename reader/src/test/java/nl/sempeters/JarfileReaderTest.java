package nl.sempeters;

import nl.sempeters.reader.InMemoryClassData;
import nl.sempeters.reader.JarReader;
import org.junit.jupiter.api.Test;

import static nl.sempeters.util.ResourceUri.getUriForResource;
import static org.junit.jupiter.api.Assertions.*;

public class JarfileReaderTest {

    @Test
    public void afterConstructorGivesCorrectNumberOfClasses() throws Exception {
        JarReader reader = new JarReader(getUriForResource("example-1-class.jar"));
        assertEquals(1, reader.getClassCount());

        reader = new JarReader(getUriForResource("example-2-classes.jar"));
        assertEquals(2, reader.getClassCount());
    }

    @Test
    public void afterContructorCanGetClassDataByName() throws Exception {
        JarReader reader = new JarReader(getUriForResource("example-2-classes.jar"));
        InMemoryClassData deployableActionImpl = reader.getClassData("nl.sempeters.impl.DeployableActionImpl");
        InMemoryClassData actionUtils = reader.getClassData("nl.sempeters.other.ActionUtils");
        assertNotNull(deployableActionImpl);
        assertNotNull(actionUtils);
        assertNotNull(deployableActionImpl.getData());
        assertNotNull(actionUtils.getData());
    }

    @Test
    public void afterConstructorListIsNotEmpty() throws Exception {
        JarReader reader = new JarReader(getUriForResource("example-2-classes.jar"));
        assertNotNull(reader.getClassData());
        assertFalse(reader.getClassData().isEmpty());
    }

}
