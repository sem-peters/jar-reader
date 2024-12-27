package nl.sempeters.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceUri {
    public static URI getUriForResource(String resourceName) throws URISyntaxException {
        ClassLoader classLoader = ResourceUri.class.getClassLoader();
        URL resource = classLoader.getResource(resourceName);
        return resource.toURI();
    }

}
