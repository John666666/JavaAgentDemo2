## 概述
本工程用于javaagent技术练手，其中包含常用的三个示例场景
- 通过直接替换class字节码的方式，在运行过程中替换目标类字节码
- 通过asm库，在运行过程中修改目标类加载进来的字节码文件，实现对原有逻辑的修改/增强
- 通过javassist库，在运行过程中修改目标类加载进来的字节码文件，实现对原有逻辑的修改/增强

## 运行说明
1. 编译出javaagent jar包（修改resources/MANIFEST.MF中的`Premain-Class`可指定用哪个Agent入口类）
   ```
   mvn clean package
   ```
2. 使用编译出的agent运行目标类
    ```
   java -javaagent:./target/JavaAgentDemo2-1.0-jar-with-dependencies.jar -cp ./target/classes com.Test
   ```

## 特别说明
- `-javaagent`参数可以指定多个，但是Agent jar中入口类的类名不能重复，否则会被覆盖掉
- 如果要测试指定多个`-javaagent`参数，可以将构建出来的agent jar包拷贝到其他地方，然后修改`resource/MANIFEST.MF`文件中的`Premain-Class`，再次构建，这样就可以得到两个不冲突的agent jar
   示例命令：`java -javaagent:E:/agent1.jar -javaagent:E:/agent2.jar -cp ./target/classes com.Test`
