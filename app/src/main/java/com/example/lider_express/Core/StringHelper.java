package com.example.lider_express.Core;

public class StringHelper {

    public static String join(String[] array, String separator, boolean endSeparator){
        String concatenatedString = "";

        if (endSeparator){
            for(int i = 0; i < array.length; i++){
                concatenatedString += (array[i] + separator);
            }
        }else{
            for(int i = 0; i < array.length; i++){
                if(i == array.length - 1){
                    concatenatedString += (array[i]);
                }else{
                    concatenatedString += (array[i] + separator);
                }
            }
        }

        return concatenatedString;
    }


}
