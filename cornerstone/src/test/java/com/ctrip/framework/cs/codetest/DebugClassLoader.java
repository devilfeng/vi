package com.ctrip.framework.cs.codetest;

import com.ctrip.framework.cs.asm.ClassReader;
import com.ctrip.framework.cs.asm.ClassWriter;
import com.ctrip.framework.cs.asm.util.CheckClassAdapter;
import com.ctrip.framework.cs.code.debug.ClassMetadata;
import com.ctrip.framework.cs.code.debug.DebugClassVisitor;
import com.ctrip.framework.cs.code.debug.DebugInfo;
import com.ctrip.framework.cs.code.debug.MetadataCollector;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jiang.j on 2017/7/12.
 */
public class DebugClassLoader extends ClassLoader {

    ClassLoader superLoader;
    DebugInfo debugInfo;

    public DebugClassLoader(ClassLoader superLoader, DebugInfo debugInfo) {
        this.superLoader = superLoader;
        this.debugInfo = debugInfo;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("com.ctrip.framework.cs.") && !name.startsWith("com.ctrip.framework.cs.code.") && !name.startsWith("com.ctrip.framework.cs.metrics.")) {
            return findClass(name);
        } else {
            return this.superLoader.loadClass(name);
        }
    }

    public InputStream getClassStream(String name) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name.replace('.', '/') + ".class");
        return is;

    }

    @Override
    public Class findClass(String name) {
        try (InputStream input = getClassStream(name)) {
            ClassReader cr = new ClassReader(input);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            //TraceClassVisitor cv = new TraceClassVisitor(cw, new PrintWriter(System.out));
            ClassMetadata classMetadata = new ClassMetadata();
            DebugClassVisitor mr = new DebugClassVisitor(new CheckClassAdapter(cw), name, this.debugInfo, classMetadata);

            cr.accept(new MetadataCollector(classMetadata), ClassReader.SKIP_FRAMES);
            cr.accept(mr, ClassReader.SKIP_FRAMES);
            byte[] b = cw.toByteArray();
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
