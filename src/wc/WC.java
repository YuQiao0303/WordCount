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
	//________________________________attributes________________________________
	public static String inputFile;    //�û�������ļ�·��
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
	//___________________________________methods_________________________________
	

	
	//____________________________________getFileName___________________________
	
	//�õ�ָ��·���ļ����������ļ���������·��������׺��
	public static String [] getFileName(String path) 
    { 
        File file = new File(path); 
        String [] fileName = file.list(); 
        return fileName; 
    } 
	//____________________________________getAllFileName___________________________
	
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
	 
	//____________________________________s___________________________
	 
	//�ݹ�õ�    ָ��·���ļ���   �������ļ�����   ���з����û�Ҫ����ļ�����������·��������׺��
	public static void s(ArrayList<String> fileNames)    //ʵ�֡�-s�����ܵĺ���
	{
		System.out.println(inputFile);
		//inputFile="C:/Users/yuqiao/Documents/Visual Studio 2015/Projects/BookStore1/BookStore1/*.cpp";
		String path;
		String type=inputFile.substring((inputFile.lastIndexOf(".") + 1), inputFile.length()).toLowerCase();
		String fileType;
		ArrayList<File> listFileName = new ArrayList<File>();  
		if (inputFile.charAt(0)=='*')
		{
			path=".";
		}
		else path=inputFile.substring(0,inputFile.lastIndexOf("*")-1);
		
        getAllFileName(path,listFileName); 
        String fnStr;
        for(File fileName:listFileName)   //���ڵݹ��ҵ������������ļ�
        { 
        	fnStr=fileName.getAbsolutePath();
        	fileType = fnStr.substring((fnStr.lastIndexOf(".") + 1), fnStr.length()).toLowerCase();
            if(fileType.equals(type))
            	fileNames.add(fnStr);
        }
        int len=fileNames.size();
        for(int i=0;i<len;i++)
        {
        	fnStr=fileNames.get(i);
        	getBasicInfo(fnStr);
        }
	}
    
	//_____________________getBasicInfo()����ָ���ļ�FileName������____________________
    public static void getBasicInfo(String fileName)
    {
    	//_____________________��������____________________
    	chars=0;
    	words=0;
    	lines=0;
    	//ͳ�Ƶ����õı���
    	boolean partition=true;
    	char charNow;
    	try 
		{ // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw  
    		File filename = new File(fileName); // Ҫ��ȡ��·�����ļ�  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // ����һ������������reader  
            BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
            String line ;  
            line = br.readLine(); 
            while (line != null) 
            {  
            	//��������������������������������������������ͳ���������ַ���__________
            	chars+=line.length();
            	lines++;
            	//_______________________��ӡ�����кź�����
//            	System.out.print(lines);
//            	System.out.print("	");
//            	System.out.print(line);
            	
            	//________________________ͳ�Ƶ���_____________________________
            	
            	partition=true;
            	
            	for(int i=0;i<line.length();i++)
            	{
            		charNow=line.charAt(i);
            		if(partition==true&&charNow!=' '&&charNow!='\t'&&charNow!=','&&charNow!='��')
            			{
            				words++;
            				partition=false;
            			}
            		if(charNow==' '||charNow=='\t'||charNow==','||charNow=='��')
            		{
            			partition=true;
            		}
            		
            	}
            	//-----------------------------------------------------------------
                line = br.readLine(); // ��ȡ��һ������  
            } 
            br.close();
		}
    	
		catch (IOException e) 
		{
			e.printStackTrace();
		}     	
    }
    //_______________________________getComInfo____________________________
    public static void getComInfo(String fileName)
    {
    	//_____________________��������____________________
    	codeLines=0;
    	empLines=0;
    	comLines=0;
    	
    	boolean commentingNow=false;//֮ǰ���ֹ���/*������δ���֡�*/��
    	
    	String regCom = "(\\s*)(\\{|\\})?(\\s*)(//|/\\*)(.*)";     //ע���е�������ʽ
		String regEmp ="(\\s*)(\\{|\\})?(\\s*)";            //���е�������ʽ
		String regComSingle = "(\\s*)(\\{|\\})?(\\s*)(/\\*).*(\\*/)(\\s*)";     //�������Ƕ���ע�͵�һ�е�������ʽ
		String regComBegin = "(\\s*)(\\{|\\})?(\\s*)(/\\*).*";     //�����Ƕ���ע�͵�һ�е�������ʽ
		String regComEnd=".*(\\*/)";    //����ע�ͽ���
		
    	//˵�������noStartCommΪfalse����MayStartCommonsΪtrue����˵����ǰ���Ƕ���ע�͵ĵ�һ��
    	try 
		{ // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw  
    		File filename = new File(fileName); // Ҫ��ȡ��·�����ļ�  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // ����һ������������reader  
            BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
            String line ;  
            line = br.readLine(); 
            while (line != null) 
            {  
            	
//            	//_______________________��ӡ�����кź�����
//            	System.out.print(lines);
//            	System.out.print("	");
//            	System.out.print(line);
            	
        		//__________________�жϱ������ͣ������С����С�ע����_____________________
        		
        		if(commentingNow==false)  //���Ŀǰ���ڶ���ע�͵���
        		{
        			if(line.matches(regCom))  //�ж��ǲ���ע����
            		{
            			//isComLine=true;
            			comLines++;
            			
            			//�����ж��ǲ��Ƕ���ע�͵Ŀ�ʼ
            			if(!line.matches(regComSingle)&&line.matches(regComBegin))
            			{
            				commentingNow=true;
            				
            				System.out.print("\tcommentstart");
            			}
            			System.out.println("\tcomment");
            		}
            		else if (line.matches(regEmp))
            		{
            			//isEmpLine=true;
            			empLines++;
            			System.out.println("\tempty");
            		}
            		else
            		{
            			//isCodLine=true;
            			codeLines++;
            			System.out.println("\tcode");
            		}
        		}
        		else  //�����ǰ���ڶ���ע�͵���,��ǰ�б�Ϊע����
        		{
        			comLines++;
    				System.out.println("\tcomment");
        			if(line.matches(regComEnd))   //���ƥ�����ע�ͽ������ʽ����endCom=1,com=0
        			{
        				commentingNow=false;
        				
        			}
        		}
            	//-----------------------------------------------------------------
                line = br.readLine(); // ��ȡ��һ������  

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
		//�ݹ���ҵ�Ŀ¼����Ŀ¼�����з����������ļ���
		inputFile="file1.c";
		//ArrayList<String> fileFound=new ArrayList<String>();
		//s(fileFound);
		getBasicInfo(inputFile);
		getComInfo(inputFile);
		//System.out.println(fileFound);
		
//		inputFile="file1.c";
//		getInfo();
		System.out.print("lines: ");
		System.out.println(lines);
		System.out.print("characters: ");
		System.out.println(chars);
		System.out.print("words: ");
		System.out.println(words);
		System.out.print("empLines: ");
		System.out.println(empLines);
		System.out.print("comLines: ");
		System.out.println(comLines);
		System.out.print("codeLines: ");
		System.out.println(codeLines);
		//---------------------------------------------------------------------------
		
		//---------------------------------------------------------------------------
	}

}
