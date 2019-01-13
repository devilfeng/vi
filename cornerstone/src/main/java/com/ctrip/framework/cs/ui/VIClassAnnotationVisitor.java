package com.ctrip.framework.cs.ui;

import com.ctrip.framework.cs.asm.AnnotationVisitor;
import com.ctrip.framework.cs.asm.ClassVisitor;

/**
 * Created by jiang.j on 2017/4/10.
 */

public class VIClassAnnotationVisitor extends ClassVisitor {

    private final String igniteAnnotation = "Lcom/ctrip/framework/cs/annotation/Ignite;";
    private final String componentAnnotation = "Lcom/ctrip/framework/cs/annotation/ComponentStatus;";
    private final int api;
    private AnnotationType annotationType = AnnotationType.NONE;
    public VIClassAnnotationVisitor(int api) {
        super(api);
        this.api = api;
    }

    public AnnotationType annotationType() {
        return annotationType;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        if (desc.equals(igniteAnnotation) || desc.equals(componentAnnotation)) {
            super.visitEnd();
            final AnnotationType type = desc.equals(componentAnnotation) ? AnnotationType.COMPONENT : AnnotationType.IGNITE;
            return new AnnotationVisitor(api) {
                @Override
                public void visit(String name, Object value) {
                    if ("auto".equals(name) && ((boolean) value)) {
                        annotationType = type;
                        super.visitEnd();
                    } else {
                        super.visit(name, value);
                    }
                }
            };
        }
        return super.visitAnnotation(desc, visible);
    }

    enum AnnotationType {
        NONE, IGNITE, COMPONENT
    }
}