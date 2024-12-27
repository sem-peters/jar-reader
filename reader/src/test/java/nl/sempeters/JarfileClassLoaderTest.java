package nl.sempeters;

import nl.sempeters.api.DeployableAction;
import nl.sempeters.reader.JarfileClassLoader;
import org.junit.jupiter.api.Test;

import static nl.sempeters.util.ResourceUri.getUriForResource;
import static org.junit.jupiter.api.Assertions.*;

public class JarfileClassLoaderTest {

    private static final String DEPLOYABLE_ACTION_IMPL = "nl.sempeters.impl.DeployableActionImpl";

    @Test
    public void canFindClassByName() throws Exception {
        ClassLoader jfcl = new JarfileClassLoader(getUriForResource("example-1-class.jar"));
        Class<?> clazz = jfcl.loadClass(DEPLOYABLE_ACTION_IMPL);
        assertNotNull(clazz);
        assertEquals(DEPLOYABLE_ACTION_IMPL, clazz.getName());
    }

    @Test
    public void throwsClassNotFoundExceptionWhenClassNotFound() throws Exception {
        ClassLoader jfcl = new JarfileClassLoader(getUriForResource("example-1-class.jar"));
        assertThrows(ClassNotFoundException.class, () -> jfcl.loadClass("nl.sempeters.other.ActionUtils"));
    }

    @Test
    public void foreignImplClassInstanceOfLocalInterface() throws Exception {
        ClassLoader jfcl = new JarfileClassLoader(getUriForResource("example-1-class.jar"));
        Class<?> deployableActionImpl = jfcl.loadClass(DEPLOYABLE_ACTION_IMPL);

        Class<?>[] interfaces = deployableActionImpl.getInterfaces();
        assertEquals(DeployableAction.class, interfaces[0]);
    }

    @Test
    public void canCallMethodOnForeignClass() throws Exception {
        ClassLoader jfcl = new JarfileClassLoader(getUriForResource("example-1-class.jar"));
        Class<?> deployableActionImpl = jfcl.loadClass(DEPLOYABLE_ACTION_IMPL);
        DeployableAction deployableAction = (DeployableAction) deployableActionImpl.getConstructor().newInstance();
        deployableAction.run();
        assertEquals("I have run!", deployableAction.getResult());
    }

}
