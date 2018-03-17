package wc;

import java.io.File;  
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileInputStream;  
import java.io.FileWriter; 
import java.io.IOException;

public class WC 
{
	//得到指定路径文件夹中所有文件名（不含路径，含后缀）
	public static String [] getFileName(String path) 
    { 
        File file = new File(path); 
        String [] fileName = file.list(); 
        return fileName; 
    } 
	//递归得到    指定路径文件夹   及其子文件夹中   所有文件名（含绝对路径，含后缀）
    public static void getAllFileName(String path,ArrayList<File> fileName) 
    { 
        File file = new File(path); 
        File [] files = file.listFiles(); 
        File [] subfiles = file.listFiles(); 
        if(subfiles != null) 
        fileName.addAll(Arrays.asList(subfiles)); 
        for(File a:files) 
        { 
            if(a.isDirectory()) 
            { 
                getAllFileName(a.getAbsolutePath(),fileName); 
            } 
        } 
    }
    
    //返回指定文件的总行数
    public static int getLineNum(String pathname)
    {
    	int lineNum=0;
    	try 
		{ // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  
    		 
    		File filename = new File(pathname); // 要读取以上路径的文件  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line ;  
            line = br.readLine();  
            //System.out.println(line);
            while (line != null) 
            {  
                line = br.readLine(); // 一次读入一行数据  
                //if(line==null)
                	//break;
                lineNum++;
            } 
            br.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
    	return lineNum;
    }
    //返回文件的总字符数（空格也算字符）
    public static int getCharNum(String pathname)
    {
    	int charNum=0;
    	try 
		{ // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  
    		 
    		File filename = new File(pathname); // 要读取以上路径的文件  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line ;  
            line = br.readLine();  
            //System.out.println(line);
            while (line != null) 
            {  
                line = br.readLine(); // 一次读入一行数据  
                //if(line==null)
                	//break;
                
            } 
            br.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
    	return charNum;
    }
	//主方法
	public static void main(String[] args)
	{
		int lines=getLineNum("file1.c");
		System.out.println(lines);
	}

}
