package com.wills.spring.example.singleton;

import com.wills.spring.example.entity.User;
import com.wills.spring.resource.Resources;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName UserSingle
 * @Date 2021/8/13 13:11
 * @Author 王帅
 * @Version 1.0
 * @Description
 * 使用 FileReader 获取两个User对象，保持系统内的单例
 */
public class Singleton {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 自动转换文件中的一行称为一个User对象
     */
    public static List<User> getUserByFile() throws Exception{
        List<User> data = new ArrayList<>();
        String fileUrl = "/Users/wills/Desktop/wills-spring/src/main/resources/user.txt";
        FileReader reader = new FileReader(fileUrl);
        BufferedReader buffer = new BufferedReader(reader);
        while (buffer.ready()){
            String[] split = buffer.readLine().split(" ");
            Date date = sdf.parse(split[4] + " " + split[5]);
            User user = new User(Integer.parseInt(split[0]),split[1],Double.parseDouble(split[2]),Double.parseDouble(split[3]),date);
            data.add(user);
        }
        return data;
    }

    public static User getUserById(Integer user) throws Exception{
        List<User> users = getUserByFile();
        for (User u : users) {
            if(u.getId() == user){
                return u;
            }
        }
        return null;
    }

    public static boolean transferInFile(User from,User to){
        String fromStr = getUserStr(from);
        String toStr =  getUserStr(to);
        FileWriter writer = null;
        try{
            // 拼接字符串，然后更新到文件中
            String datasource = "/Users/wills/Desktop/wills-spring/src/main/resources/user.txt";
            writer = new FileWriter(datasource,false);
            writer.write(fromStr);
            writer.write("\r\n");
            writer.write(toStr);
            writer.flush();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if(writer != null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getUserStr(User data){
        StringBuilder sb = new StringBuilder();
        sb.append(data.getId());
        sb.append(" ");
        sb.append(data.getName());
        sb.append(" ");
        sb.append(data.getTotal());
        sb.append(" ");
        sb.append(data.getTansfer());
        sb.append(" ");
        sb.append(sdf.format(data.getTransferDate()));

        return sb.toString();
    }
}
