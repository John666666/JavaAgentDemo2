package com.javaagent.transformers;

import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * 使用字节码操作库csm直接动态增强目标类：在目标方法执行前后输出系统时间
 *
 */
public class TimeTracingTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        if ("Test".equals(className)) {
            ClassReader cr = new ClassReader(classfileBuffer);
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
            ClassVisitor cv = new TimeTracingClassVisitor(cw);
            cr.accept(cv, ClassReader.SKIP_FRAMES);
            return cw.toByteArray();
        }
        return classfileBuffer;
    }


    private static class TimeTracingClassVisitor extends ClassVisitor {

        private final String METHOD_NAME = "main";

        public TimeTracingClassVisitor(ClassVisitor cv) {
            super(Opcodes.ASM5, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (!METHOD_NAME.equals(name)) {
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            return new TimeTracingMethodVisitor(mv);
        }
    }

    private static class TimeTracingMethodVisitor extends MethodVisitor {

        public TimeTracingMethodVisitor(MethodVisitor mv) {
            super(Opcodes.ASM5, mv);
        }

        @Override
        public void visitCode() {
            mv.visitCode();
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("Start: " + System.currentTimeMillis());
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) {
                mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                mv.visitLdcInsn("End: " + System.currentTimeMillis());
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            }
            mv.visitInsn(opcode);
        }
    }
}