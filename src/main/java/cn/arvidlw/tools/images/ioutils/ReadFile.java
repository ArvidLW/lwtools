package cn.arvidlw.tools.images.ioutils;

import cn.arvidlw.tools.images.apps.ImagesCompressor;

import java.io.File;
import java.io.IOException;

/**
 * Created by lw_co on 2017/1/6.
 */
public class ReadFile {
    /**
     *
     * @param path 目录形式是“d:/x”,或“d:\\x”最后没有斜杠
     * @return boolean
     * @throws IOException
     */
    public static boolean readfile(String path) throws IOException {

        File dir=new File(path);
        if(!dir.isDirectory()){
            throw new IOException("这不是一个目录");
        }
        String[] filelist=dir.list();
        String newdirpath=path.substring(0,path.lastIndexOf("\\"))+path.substring(path.lastIndexOf("\\"),path.length());
        String newpath=newdirpath+"-small";

        System.out.println(newpath);
        boolean flag=true;//标志路径是否建立
        for (String f:filelist) {
            File oldfile=new File(path+"\\"+f);
            if(oldfile.length()==0){
                continue;
            }
            File newfile=new File(newpath+"\\"+f.substring(0,f.indexOf("."))+".jpg");
            System.out.println(newfile);
            if(flag){
                if(!newfile.getParentFile().exists()){
                    newfile.getParentFile().mkdirs();
                }
                flag=false;
            }
            ImagesCompressor.zipImageFile(oldfile,newfile,600,600,0);
        }
        return true;
    }

    public static void main(String[] args)  {
        try{
            if(args.length<0||args.length>1){
                throw new IOException("please input only one parameter!");
            }
            //readfile("D:\\360Downloads\\test");
            readfile(args[0]);
            System.out.println("完毕ok-arvidlw");
        }catch (Exception e){
            System.out.println("请按如下格式输入：\n java -jar **.jar [参数]");
            e.printStackTrace();
        }
    }
}
