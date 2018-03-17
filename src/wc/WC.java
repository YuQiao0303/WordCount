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
	//////////////////////////attributes
	public static String inputFile;    //要统计的文件名
	public static String outputFile;   //输出信息的文件名
	public static String stopList;     //

	
	public static int chars;
	public static int words;
	public static int lines;
	public static int codeLines;
	public static int empLines;
	public static int comLines;

	public static boolean needC;
	public static boolean needW;
	public static boolean needL;
	public static boolean needO;

	public static boolean needS;
	public static boolean needA;
	public static boolean needE;

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
    
    //返回指定文件的数据
    public static void getInfo()
    {
    	chars=0;
    	words=0;
    	lines=0;
    	codeLines=0;
    	empLines=0;
    	comLines=0;
    	try 
		{ // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  
    		File filename = new File(inputFile); // 要读取该路径的文件  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line ;  
            line = br.readLine();  
            while (line != null) 
            {  
            	chars+=line.length();
            	lines++;
                line = br.readLine(); // 一次读入一行数据  
//                if(line==null)
//                	break;
//                lines++;
            } 
            br.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
    	
    }


	//主方法
	public static void main(String[] args)
	{
		inputFile="file1.c";
		getInfo();
		System.out.print("lines: ");
		System.out.println(lines);
		System.out.print("characters: ");
		System.out.println(chars);
	}

}
