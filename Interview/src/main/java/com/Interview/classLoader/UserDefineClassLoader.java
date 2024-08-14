package com.Interview.classLoader;

import java.io.*;

public class UserDefineClassLoader extends ClassLoader {
    private String classPath;

    public UserDefineClassLoader(String classPath) {
        this.classPath = classPath;
    }
    /**
     * 重写findClass方法
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //转换为文件路径
        String filePath = classToFilePath(name);
        //获取指定路径的class文件对应的字节码数组
        byte[] bytesFromPath = getBytesFromPath(filePath);
        //自定义classLoader 内部调用几个defineClass方法
        return defineClass(name, bytesFromPath, 0, bytesFromPath.length);
    }

    private byte[] getBytesFromPath(String filePath) {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;

        try {
            fis = new FileInputStream(filePath);
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String classToFilePath(String name) {
        return classPath + "\\" + name.replace(".", "\\") + ".class";
    }

    public static void main(String[] args) {
        try {
            UserDefineClassLoader userDefineClassLoader = new UserDefineClassLoader("D:\\code\\practice\\Interview\\target\\classes\\");
            Class<?> aClass = userDefineClassLoader.findClass("com.Interview.StringDemo");
            System.out.println(aClass);

            UserDefineClassLoader userDefineClassLoader2 = new UserDefineClassLoader("D:\\code\\practice\\Interview\\target\\classes\\");
            Class<?> aClass2 = userDefineClassLoader2.findClass("com.Interview.StringDemo");
            System.out.println(aClass2);

            System.out.println(aClass == aClass2);
            System.out.println(aClass.getClassLoader());
            String s = new String();
            String s1 = new String();
            System.out.println(s.getClass()==s1.getClass());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
