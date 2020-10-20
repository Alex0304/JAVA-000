package com.weekwork.classloader;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader{


    private static final String FILE_PATH = "D:\\code\\JAVA-000\\Week_01\\demo\\Hello.xlass";
    private static final String FILE_PATH1 = "D:\\code\\JAVA-000\\Week_01\\demo\\Hello.class";

    public static void main(String[] args) {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> aClass;
        Object o;
        try {
            aClass = myClassLoader.findClass("Hello");
             o = aClass.newInstance();
            Method hello = aClass.getDeclaredMethod("hello");
            hello.invoke(o,null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }



    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File(FILE_PATH);
        if(!file.exists()){
            throw new ClassNotFoundException(FILE_PATH1+" FILE NOT FOUND");
        }
        try(FileInputStream fis =new FileInputStream(file);
            ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            int data;
            while ((data = fis.read())!=-1){
                baos.write(255-data);
            }
            baos.flush();
            return super.defineClass(name,baos.toByteArray(),0,baos.toByteArray().length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
