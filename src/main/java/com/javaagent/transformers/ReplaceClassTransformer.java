package com.javaagent.transformers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.ProtectionDomain;

/**
 * 用事先准备好的类替换目标类
 */
public class ReplaceClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("loader: " + loader + " -> " + className);
        if ("Test".equals(className)) {
            Path path = Paths.get("D:/IdeaProjects/JavaAgentDemo2/src/main/resources/Test.class");
            try (FileInputStream in = new FileInputStream(path.toFile()); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                return out.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }
}
