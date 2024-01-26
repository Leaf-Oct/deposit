package cn.leafoct.deposit;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static Context context;
    private static File log_file;
    public static void write(String content){
        if (context==null){
            return;
        }
        log_file=new File(context.getExternalFilesDir("").getAbsoluteFile()+File.separator+"log");
        try (BufferedWriter bw=new BufferedWriter(new FileWriter(log_file,true))){
            bw.write(content);
            bw.write("\n");
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
