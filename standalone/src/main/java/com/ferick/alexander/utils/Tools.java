package com.ferick.alexander.utils;

public class Tools {

    public static void delay(long timeOut){
        try {
            Thread.sleep(timeOut);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
