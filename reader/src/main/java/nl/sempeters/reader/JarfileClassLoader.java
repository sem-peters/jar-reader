package nl.sempeters.reader;

import java.io.IOException;
import java.net.URI;

public class JarfileClassLoader extends ClassLoader{

    private JarReader jarReader;
    public JarfileClassLoader(URI jarfile) throws IOException {
        jarReader = new JarReader(jarfile);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        InMemoryClassData classData = jarReader.getClassData(name);
        if (classData == null) throw new ClassNotFoundException(name);
        return defineClass(name, classData.getData(), 0, classData.getData().length);
    }
}
