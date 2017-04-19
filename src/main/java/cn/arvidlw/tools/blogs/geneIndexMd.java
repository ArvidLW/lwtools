package cn.arvidlw.tools.blogs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by lw_co on 2017/4/18.
 */
public class geneIndexMd {
    private static String root;
    private static StringBuilder contents=new StringBuilder();
    private static String[] forbidName;
    //静态初始化块
    static {
        contents.append("# 目录\n\n***\n\n");
        root="https://arvidlw.github.io/";
        forbidName=new String[]{".git","_config.yml","favicon.ico","index.md","images"};
    }
    public static void main(String[] args) {
        try{
            if(args.length<0||args.length>1){
                throw new IOException("please input only one parameter!");
            }
            //args[0]="D:\\技术书籍\\arvidlw.github.io";
            readFile(args[0]);
            FileWriter fw=new FileWriter(args[0]+"/index.md");
            fw.write(contents.toString());
            fw.close();
            System.out.println(contents);
        }catch (Exception e){
            System.out.println("请按如下格式输入：\n java -jar **.jar [参数],参数个数为一个，为指定的目录名");
            e.printStackTrace();
        }

    }
    private static void readFile(String path) throws Exception {
        File dir=new File(path);
        if(!dir.isDirectory()){
            throw new Exception("path is not a direction!");
        }
        //支持path下两层目录！
        File[] files=dir.listFiles();
        int k=0;
        //第一层
        for(File f:files){
//            if(f.getName().charAt(0)=='.'){
//                continue;
//            }
            if(Arrays.asList(forbidName).contains(f.getName())){
                continue;
            }
            //第二层
            if(f.isDirectory()){
                String[] fname=f.list();
                contents.append("# ")
                        .append(f.getName())
                        .append("\n\n***\n\n");
                int i=0;
                for(String s:fname){
                    String subFn=s.substring(0,s.lastIndexOf("."));
                    contents.append(++i + ". [").append(subFn).append("]("+root+f.getName()+"/"+subFn+")").append("\n\n");
                }
            }
            else {
                String subFn=f.getName().substring(0,f.getName().lastIndexOf("."));
                contents.append(++k + ". [").append(subFn).append("]("+root+subFn+")").append("\n\n");
            }
        }
    }
}
