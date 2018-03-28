package test;
import java.io.File;  

import java.util.ArrayList;
import java.util.Arrays;



public class test 
{
	public static String [] getFileName(String path) 
    { 
        File file = new File(path); 
        String [] fileName = file.list(); 
        return fileName; 
    } 

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
	
	public static void main(String[] args)
	{
		String fileName="D:\\study\\HelloWorld\\新建文件夹\\新建 Microsoft Office Word 文档.docx";
		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
		System.out.println(fileType);
	}
}
