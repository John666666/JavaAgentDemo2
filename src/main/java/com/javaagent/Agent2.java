package com.javaagent;

import com.javaagent.transformers.ReplaceMethodTransformer;

import java.lang.instrument.Instrumentation;

/**
 * JavaAgent技术HelloWorld
 * 实现：当加载的类是Test时，将其替换为resources中的Test.class， 可拿项目根目录下的Test.class做测试
 * 1、cd D:/IdeaProjects/JavaAgentDemo2
 * 2、java -javaagent:./target/JavaAgentDemo2-1.0-SNAPSHOT.jar Test
 */
public class Agent2 {
    public static void premain(String args, Instrumentation instrumentation) {
        // 示例一：用事先准备好的类替换目标类
//        instrumentation.addTransformer(new ReplaceClassTransformer());
        // 示例二：使用asm在目标方法执行前后输出系统时间
//        instrumentation.addTransformer(new TimeTracingTransformer());
        // 示例三：使用javassist库修改目标方法
        instrumentation.addTransformer(new ReplaceMethodTransformer());
    }
}
