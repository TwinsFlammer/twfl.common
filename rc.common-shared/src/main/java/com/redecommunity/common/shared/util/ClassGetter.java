package com.redecommunity.common.shared.util;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * created by @SrGutyerrez
 **/
public class ClassGetter {

    /**
     * @param clazz
     * @return ArrayList<Class<?>>
     */
    public static ArrayList<Class<?>> getClassesForPackage(Class clazz) {
        ArrayList<Class<?>> classes = new ArrayList<>();
        CodeSource src = clazz.getProtectionDomain().getCodeSource();

        String packageName = clazz.getPackage().getName();

        if (src != null) {
            URL resource = src.getLocation();
            resource.getPath();
            processJarfile(resource, packageName, classes);
        }
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Class<?>> clazz1 = new ArrayList<>();
        for (Class<?> clazz2 : classes) {
            names.add(clazz2.getSimpleName());
            clazz1.add(clazz2);
        }
        classes.clear();

        names.sort(String.CASE_INSENSITIVE_ORDER);

        for (String name : names)
            for (Class<?> clazz2 : clazz1) {
                if (clazz2.getSimpleName().equals(name)) {
                    classes.add(clazz2);
                    break;
                }
            }

        return classes;
    }

    /**
     * @param className
     * @return Class<?>
     */
    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unexpected ClassNotFoundException loading class '" + className + "'");
        }
    }

    /**
     * @param resource
     * @param pkgname
     * @param classes
     */
    private static void processJarfile(URL resource, String pkgname, ArrayList<Class<?>> classes) {
        String relPath = pkgname.replace('.', '/');
        String resPath = resource.getPath().replace("%20", " ");
        String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
        JarFile jarFile;
        try {
            jarFile = new JarFile(jarPath);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
        }
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();
            String className = null;
            if (entryName.endsWith(".class") && entryName.startsWith(relPath)
                    && entryName.length() > (relPath.length() + "/".length())) {
                className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
            }
            if (className != null) {
                classes.add(loadClass(className));
            }
        }
    }
}