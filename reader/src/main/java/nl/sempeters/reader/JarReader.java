package nl.sempeters.reader;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarReader {

    private final URI resource;
    private final Map<String, InMemoryClassData> classFileData;

    private List<InMemoryClassData> cachedClassfileList;

    public JarReader(URI resource) throws IOException {
        this.resource = resource;
        this.classFileData = new HashMap<>();

        File file = new File(resource.getPath());
        readClassnamesFromFile(file);
    }

    public int getClassCount() {
        return this.classFileData.size();
    }

    public InMemoryClassData getClassData(String className) {
        return this.classFileData.get(className);
    }

    public List<InMemoryClassData> getClassData() {
        if (this.cachedClassfileList == null) {
            this.cachedClassfileList = new ArrayList<>(this.classFileData.values());
        }
        return this.cachedClassfileList;
    }

    private void readClassnamesFromFile(File file) throws IOException {
        if (!file.exists() || !file.canRead()) {
            throw new IllegalStateException(resource + " is not readable (or does not exist)");
        }

        try (JarFile jarFile = new JarFile(file)) {
            var jarEntryIterator = jarFile.entries().asIterator();
            while (jarEntryIterator.hasNext()) {
                JarEntry jarEntry = jarEntryIterator.next();
                if (jarEntry.getName().endsWith(".class")) {
                    addClassData(jarFile, jarEntry);
                }
            }
        }
    }

    private void addClassData(JarFile jarFile, JarEntry jarEntry) throws IOException {
        byte[] bytes = jarFile.getInputStream(jarEntry).readAllBytes();
        InMemoryClassData inMemoryClassData = new InMemoryClassData();
        inMemoryClassData.setData(bytes);
        String classname = jarEntry.getName().replace('/', '.').substring(0, jarEntry.getName().length() - 6);

        this.classFileData.put(classname, inMemoryClassData);
    }
}
