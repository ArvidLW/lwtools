package cn.arvidlw.tools.images.tests;

public class Test {
 public static void main(String[] arg){
	 String tempFile1="d:/360Downloads/test/101.gif";
	 //String tempFile2="D:/temp/2.jpg";
	 String dir="d:/360Downloads/";
	 String fileName1="102.gif";
	 //String fileName2="test2.jpg";
	 String targetDir="d:/360Downloads/test1";
	boolean b= UploadUtil.uploadZip(tempFile1, dir, fileName1, targetDir, 200, 200);
	System.out.print(b);
 }
}
