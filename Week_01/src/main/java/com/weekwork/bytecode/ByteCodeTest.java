package com.weekwork.bytecode;

public class ByteCodeTest {

    private static String str = "JAVA";

    private int count = 65;

    public static void main(String[] args) {
        ByteCodeTest byteCodeTest = new ByteCodeTest();
        byteCodeTest.calNum(str);
    }

    public int calNum(String str){
        byte[] bytes = str.getBytes();
        int result = 0;
        for (byte b : bytes) {
            if(b==count){
                result++;
            }
        }
        return result;
    }
}
