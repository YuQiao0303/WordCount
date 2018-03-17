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
	public static String inputFile;    //Ҫͳ�Ƶ��ļ���
	public static String outputFile;   //�����Ϣ���ļ���
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
    
    //����ָ���ļ�������
    public static void getInfo()
    {
    	chars=0;
    	words=0;
    	lines=0;
    	codeLines=0;
    	empLines=0;
    	comLines=0;
    	try 
		{ // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw  
    		File filename = new File(inputFile); // Ҫ��ȡ��·�����ļ�  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // ����һ������������reader  
            BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
            String line ;  
            line = br.readLine();  
            while (line != null) 
            {  
            	chars+=line.length();
            	lines++;
                line = br.readLine(); // һ�ζ���һ������  
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


	//������
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
