package org.example.Utils;

import java.util.HashSet;
import java.util.Set;

public class ServletUtils {
    public static final Set<String> whiteList = new HashSet<>();
    static {//白名单防盗链简易实现
        whiteList.add("http://localhost:8080//LTMusic/login.html");
        whiteList.add("http://localhost:8080//LTMusic/list.html");
        whiteList.add("http://localhost:8080//LTMusic/MV.html");
        whiteList.add("http://localhost:8080//LTMusic/loveMusic.html");
        whiteList.add("http://localhost:8080//LTMusic/uploadSuccess.html");
    }

//    public static final Set<String> blackList = new HashSet<>();
//    static {//黑名单防盗链简易实现
//
//    }
    //音乐资源存储路径
    public static final String PATH = "F:\\MyProject\\MyPorject\\LTMusic\\src\\main\\webapp\\music";
    //MV资源存储路径
    public static final String MvPATH = "F:\\MyProject\\MyPorject\\LTMusic\\src\\main\\webapp\\video";

}
