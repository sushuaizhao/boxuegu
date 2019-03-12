package utils;

import android.preference.PreferenceActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
    public static  String md5(String text){
        try {
            //使用什么算法 进行加密
            MessageDigest messageDigest=MessageDigest.getInstance("md5");
            byte[] result=messageDigest.digest(text.getBytes());
            StringBuilder stringBuilder=new StringBuilder();
            for (byte b:result ){
                int a=b&0xff;
                String hex=Integer.toHexString(a);
                if (hex.length()==1){
                    stringBuilder.append("0"+hex);
                }
                else{
                    stringBuilder.append(hex);
                }
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
