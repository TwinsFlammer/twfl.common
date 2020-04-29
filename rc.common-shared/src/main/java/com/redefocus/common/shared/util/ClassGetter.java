package com.redefocus.common.shared.util;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
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
    public static ArrayList<Class<?>> getClassesForPackage(Class clazz, Class... blacklisted) {
        ArrayList<Class<?>> classes = new ArrayList<>();
        CodeSource src = clazz.getProtectionDomain().getCodeSource();

        String packageName = clazz.getPackage().getName();

        if (src != null) {
            URL resource = src.getLocation();
            resource.getPath();
            ClassGetter.processJarfile(resource, packageName, classes, blacklisted);
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
    private static Class<?> loadClass(String className, Class... blacklisted) {
        if (Arrays.stream(blacklisted).anyMatch(clazz3 -> {
            System.out.println(">> " + className);
            System.out.println(">>> " + clazz3.getName());

            System.out.println("Equal " + clazz3.getName().equals(className));

            return clazz3.getName().equals(className);
        })) {
            System.out.println("blacklisted: " + className);
            return null;
        }

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
    private static void processJarfile(URL resource, String pkgname, ArrayList<Class<?>> classes, Class... blacklisted) {
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
                Class<?> loadedClass = ClassGetter.loadClass(className, blacklisted);

                if (loadedClass != null)
                    classes.add(loadedClass);
            }
        }
    }
}