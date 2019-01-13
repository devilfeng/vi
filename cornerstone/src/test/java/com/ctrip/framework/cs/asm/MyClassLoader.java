package com.ctrip.framework.cs.asm;

import com.ctrip.framework.cs.code.ProfileClassVisitor;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jiang.j on 2017/5/2.
 */
public class MyClassLoader extends ClassLoader {

    private ClassLoader superLoader;

    public MyClassLoader(ClassLoader superLoader) {
        this.superLoader = superLoader;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("com.ctrip.framework.cs") && !name.startsWith("com.ctrip.framework.cs.metrics")) {
            return findClass(name);
        } else {
            return this.superLoader.loadClass(name);
        }
    }

    private InputStream getClassStream(String name) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(name.replace('.', '/') + ".class");
        return inputStream;

    }

    @Override
    public Class findClass(String name) {
        try (InputStream input = getClassStream(name)) {
            ClassReader cr = new ClassReader(input);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            //TraceClassVisitor cv = new TraceClassVisitor(cw, new PrintWriter(System.out));
            ProfileClassVisitor mr = new ProfileClassVisitor(cw, name);

            cr.accept(mr, ClassReader.EXPAND_FRAMES);
            byte[] b = cw.toByteArray();
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
