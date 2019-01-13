package com.ctrip.framework.cs.code;

import com.ctrip.framework.cs.asm.MethodVisitor;
import com.ctrip.framework.cs.asm.Type;
import com.ctrip.framework.cs.asm.commons.AdviceAdapter;
import com.ctrip.framework.cs.metrics.MetricsCollector;
import com.ctrip.framework.cs.metrics.MetricsValueType;

/**
 * Created by jiang.j on 2017/4/27.
 */
public class ProfileAdapter extends AdviceAdapter {

    private String name;
    private String fullName;
    private int startTimeVar;

    /**
     * Creates a new {@link com.ctrip.framework.cs.asm.commons.AdviceAdapter}.
     *
     * @param api    the ASM API version implemented by this visitor. Must be one
     *               of {@link com.ctrip.framework.cs.asm.Opcodes#ASM4} or {@link com.ctrip.framework.cs.asm.Opcodes#ASM5}.
     * @param mv     the method visitor to which this adapter delegates calls.
     * @param access the method's access flags (see {@link com.ctrip.framework.cs.asm.Opcodes}).
     * @param name   the method's name.
     * @param desc   the method's descriptor (see {@link com.ctrip.framework.cs.asm.Type Type}).
     */
    protected ProfileAdapter(int api, MethodVisitor mv, int access, String name, String desc, String fullName) {
        super(api, mv, access, name, desc);
        this.fullName = fullName.replace('/', '.');
        this.name = name;
    }

    public void visitCode() {
        super.visitCode();
    }

    @Override
    protected void onMethodEnter() {
        super.onMethodEnter();
        startTimeVar = newLocal(Type.LONG_TYPE);
        mv.visitMethodInsn(INVOKESTATIC, "com/ctrip/framework/cs/metrics/MetricsCollector", "getCollector", "()Lcom/ctrip/framework/cs/metrics/MetricsCollector;", false);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/ctrip/framework/cs/metrics/MetricsCollector", "getStartNano", "()J", false);
        mv.visitVarInsn(LSTORE, startTimeVar);
    }

    @Override
    protected void onMethodExit(int opcode) {
        methodTrace();
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

    private void methodTrace() {
        mv.visitMethodInsn(INVOKESTATIC, "com/ctrip/framework/cs/metrics/MetricsCollector", "getCollector", "()Lcom/ctrip/framework/cs/metrics/MetricsCollector;", false);
        String metricName = fullName + "." + name + "##" + MetricsValueType.MicroSec.getValue();
        MetricsCollector.getCollector().addMetricsName(metricName);
        mv.visitLdcInsn(metricName);
        mv.visitVarInsn(LLOAD, startTimeVar);
        mv.visitMethodInsn(INVOKEVIRTUAL, "com/ctrip/framework/cs/metrics/MetricsCollector", "recordNano", "(Ljava/lang/String;J)V", false);
    }

    @Override
    public void visitMaxs(int stack, int locals) {

        super.visitMaxs(stack + 4, locals);
    }

}
