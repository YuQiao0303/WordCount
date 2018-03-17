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
	//�õ�ָ��·���ļ����������ļ���������·��������׺��
	public static String [] getFileName(String path) 
    { 
        File file = new File(path); 
        String [] fileName = file.list(); 
        return fileName; 
    } 
	//�ݹ�õ�    ָ��·���ļ���   �������ļ�����   �����ļ�����������·��������׺��
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
    
    //����ָ���ļ���������
    public static int getLineNum(String pathname)
    {
    	int lineNum=0;
    	try 
		{ // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw  
    		 
    		File filename = new File(pathname); // Ҫ��ȡ����·�����ļ�  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // ����һ������������reader  
            BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
            String line ;  
            line = br.readLine();  
            //System.out.println(line);
            while (line != null) 
            {  
                line = br.readLine(); // һ�ζ���һ������  
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
    //�����ļ������ַ������ո�Ҳ���ַ���
    public static int getCharNum(String pathname)
    {
    	int charNum=0;
    	try 
		{ // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw  
    		 
    		File filename = new File(pathname); // Ҫ��ȡ����·�����ļ�  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // ����һ������������reader  
            BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
            String line ;  
            line = br.readLine();  
            //System.out.println(line);
            while (line != null) 
            {  
                line = br.readLine(); // һ�ζ���һ������  
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
	//������
	public static void main(String[] args)
	{
		int lines=getLineNum("file1.c");
		System.out.println(lines);
	}

}
