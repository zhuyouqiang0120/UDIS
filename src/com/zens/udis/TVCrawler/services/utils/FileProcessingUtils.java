package com.zens.udis.TVCrawler.services.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FileProcessingUtils {	
	
    ///////////////////  该方法用来删除某个文件夹及其目录下所有子文件 （开始）  ////////////////////	
	public void deleteFolder(String path){
		File f = new File(path);
		deleteFolder_del(f);
	}
				
	public void deleteFolder_del(File f){
		if(f.exists()){
			System.out.println("存在");
			try{
			    for(File fi:f.listFiles()){
			        if(fi.isDirectory()){
			        	deleteFolder_del(fi);
			        }
			        else{
			            fi.delete();
			        }
			    }
			    f.delete();
			    }
			    catch(NullPointerException n){
			        System.out.println("Sorry,No such file");
			    }

		}else{
			System.out.println("不存在");
		}
	}
    ///////////////////  该方法用来删除某个文件夹及其目录下所有子文件 （结束）  ////////////////////	
	
	
	
	
	
    /////////////////////////  在本地创建文件 （开始）  //////////////////////////
	public static boolean fileWriter_createNewFile(String filePath) {  
        boolean isSuccess = true;  
        // 如有则将"\\"转为"/",没有则不产生任何变化  
        String filePathTurn = filePath.replaceAll("\\\\", "/");  
        // 先过滤掉文件名  
        int index = filePathTurn.lastIndexOf("/");  
        String dir = filePathTurn.substring(0, index);  
        // 再创建文件夹  
        File fileDir = new File(dir);  
        isSuccess = fileDir.mkdirs();  
        // 创建文件  
        File file = new File(filePathTurn);  
        try {  
            isSuccess = file.createNewFile();  
        } catch (IOException e) {  
            isSuccess = false;  
            e.printStackTrace();  
        }  
        return isSuccess;  
    }  
	
	
	//该方法用来在制定路径下新建文本文件并写入内容。
	public boolean fileWriter_writeIntoFile(String content, String filePath,  
            boolean isAppend) {  
        boolean isSuccess = true;  
        filePath = filePath.replaceAll("\\\\", "/"); //将传进来的路径中的所有的\\替换为/
        // 先过滤（截取）掉文件名  
        int index = filePath.lastIndexOf("/");  
        String dir = filePath.substring(0, index);  
        // 创建出文件的路径  
        File fileDir = new File(dir); //注意，这里路径用的是dir,结尾是不含文件名的。  
        fileDir.mkdirs(); //这句代码用来创建文件夹。 
        // 再创建路径下的文件  
        File file = null;  
        try {  
            file = new File(filePath); //注意，这里路径用的是filePath,结尾是含有文件名的。   
            file.createNewFile(); //这句代码用来创建文件。  
        } catch (IOException e) {  
            isSuccess = false;  
            e.printStackTrace();  
        }  
        // 写入文件  
        FileWriter fileWriter = null;  
        try {  
            fileWriter = new FileWriter(file, isAppend);  
            fileWriter.write(content);  
            fileWriter.flush();  
        } catch (IOException e) {  
            isSuccess = false;  
            e.printStackTrace();  
        } finally {  
            try {  
                if (fileWriter != null)  
                    fileWriter.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
  
        return isSuccess;  
    } 
    /////////////////////////  在本地创建文件 （结束）  //////////////////////////
	
	
	
	
	
    ////////////////////  获取本地某文件最新更新时间 （开始）  /////////////////////
	public String getFileLatestUpdateTime(String path){
	//public static void main(String args[]){
		
		 File f = new File(path);               
		//File f = new File("D:\\jjjj.txt");
		 Calendar cal = Calendar.getInstance();   
		 long time = f.lastModified();   
		 cal.setTimeInMillis(time);
		 SimpleDateFormat ddf = new SimpleDateFormat("yyyy-M-d");
		 
		 //System.out.println("修改时间： " + cal.getTime().toLocaleString()); //toLocaleString()方法现在过时了。	 
		 System.out.println("修改时间： " + ddf.format(cal.getTime()));
		 String modifiedTime = ddf.format(cal.getTime());
		 return modifiedTime;	
	}
    //////////////////// 获取本地某文件最新更新时间 （结束）  /////////////////////
	
	
	
	
	
    ////////////////////  以字符串的形式获得本地txt文件中的内容 （开始）  /////////////////////
    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     * @param filePath
     */
	public  String readTxt_string="";
    public String readTxt(String filePath){
        try {
                String encoding="UTF-8";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);                  
                    String lineTxt = "";
					while((lineTxt = bufferedReader.readLine()) != null){
						readTxt_string += lineTxt;						
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
            return readTxt_string;
    }	
    ////////////////////  以字符串的形式获得本地txt文件中的内容 （开始）  /////////////////////	
	
	
	
	
	

}
