package com.javaagent.transformers;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 本类基于javassist库，实现直接动态修改目标类中目标方法内容
 */
public class ReplaceMethodTransformer implements ClassFileTransformer {

    // ClassPool 是一个保存 CtClass 对象的容器，它可以从字节码文件或现有类中读取类信息。
    ClassPool classPool = ClassPool.getDefault();

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if("com/Test".equals(className)) {
            try {
                // 通过classPool获取到目标类字节码
                CtClass ctClass = classPool.get(className.replace("/", "."));
                // 遍历目标类中声明的方法
                for(CtMethod method : ctClass.getDeclaredMethods()) {
                    if("sum".equals(method.getName())) {
                        method.setBody("{return 666;}");
                        break;
                    }
                    if("sayHello".equals(method.getName())) {
                        method.setBody("{System.out.println(\"Hello, JavaAgent!\");}");
                    }
                }
                byte[] clazzByte = ctClass.toBytecode();
                ctClass.defrost();
                return clazzByte;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return classfileBuffer;
    }
}
