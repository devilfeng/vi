package com.ctrip.framework.cs.code.debug;

import com.ctrip.framework.cs.asm.ClassVisitor;
import com.ctrip.framework.cs.asm.MethodVisitor;
import com.ctrip.framework.cs.asm.Opcodes;

/**
 * Created by jiang.j on 2017/7/12.
 */
public class DebugClassVisitor extends ClassVisitor {
    private final ClassMetadata classMetadata;
    private String className;
    private DebugInfo debugInfo;

    public DebugClassVisitor(final ClassVisitor cv, final String className, final DebugInfo debugInfo, final ClassMetadata classMetadata) {
        super(Opcodes.ASM5, cv);
        this.className = className;
        this.debugInfo = debugInfo;
        this.classMetadata = classMetadata;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        if (cv != null) {

            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            if (!"<clinit>".equals(name)) {
                mv = new DebugMethodVisitor(this.className, this.debugInfo, name, desc, access, mv, this.classMetadata);
            }
            return mv;
        }
        return null;
    }
}
