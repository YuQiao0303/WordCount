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
    
    //_____________________getInfo()����ָ���ļ�inputFile������____________________
    public static void getInfo()
    {
    	if(inputFile==null)
    	{
    		System.out.println("Error: inputFile==null !!");
    		return;
    	}
    	//_____________________��������____________________
    	chars=0;
    	words=0;
    	lines=0;
    	codeLines=0;
    	empLines=0;
    	comLines=0;
    	
    	//ͳ�Ƶ����õı���
    	boolean partition=true;
    	char charNow;
    	
    	//�ж������õı���
    	//boolean isComLine=false;    //��ǰ����ע����
    	//boolean isCodLine=false;    //��ǰ���Ǵ�����
    	//boolean isEmpLine=false;    //��ǰ���ǿ���
    	//boolean mayStartCom=false;  //��ǰ�п����Ƕ���ע�͵ĵ�һ�У���"/*"��
    	//boolean noStartCom=false;   //��ǰ�о��Բ��Ƕ���ע�͵ĵ�һ�У���Ȼ��"/*"�������к�����"*/"��
    	//boolean endCom=false;
    	boolean commentingNow=false;//֮ǰ���ֹ���/*������δ���֡�*/��
    	
    	String regCom = "(\\s*)(\\{|\\})?(\\s*)(//|/\\*)(.*)";     //ע���е�������ʽ
		String regEmp ="(\\s*)(\\{|\\})?(\\s*)";            //���е�������ʽ
		String regComSingle = "(\\s*)(\\{|\\})?(\\s*)(/\\*).*(\\*/)(\\s*)";     //�������Ƕ���ע�͵�һ�е�������ʽ
		String regComBegin = "(\\s*)(\\{|\\})?(\\s*)(/\\*).*";     //�����Ƕ���ע�͵�һ�е�������ʽ
		String regComEnd=".*(\\*/)";    //����ע�ͽ���
		
    	//˵�������noStartCommΪfalse����MayStartCommonsΪtrue����˵����ǰ���Ƕ���ע�͵ĵ�һ��
    	
    	
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
            	//��������������������������������������������ͳ���������ַ���__________
            	chars+=line.length();
            	lines++;
            	//_______________________��ӡ�����кź�����
            	System.out.print(lines);
            	System.out.print("	");
            	System.out.print(line);
            	
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
            	
        		//System.out.println(words);
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
		inputFile="file1.c";
		getInfo();
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
		//�������������ʽ
//		String line="	}/* sf*/	";
//		String regCom = "(\\s*)(\\{|\\})?(\\s*)(//|/\\*)(.*)";     //ע���е�������ʽ
//		String regCod ="(\\s*)(\\{|\\})?(\\s*)";            //���е�������ʽ
//		String regComSingle = "(\\s*)(\\{|\\})?(\\s*)(/\\*).*(\\*/)(\\s*)";     //�������Ƕ���ע�Ϳ�ʼ
//		String regComBegin = "(\\s*)(\\{|\\})?(\\s*)(/\\*).*";     //����ע�Ϳ�ʼ
//		
//		System.out.println(line);
//		System.out.println(line.matches(regCom)?"��ע����":"");   
//		System.out.println(line.matches(regCod)?"�ǿ���":""); 
//		System.out.println(line.matches(regComBegin)?"����ע�Ϳ�ʼ":""); 
//		System.out.println(line.matches(regComSingle)?"�������Ƕ���ע�Ϳ�ʼ":""); 
		//---------------------------------------------------------------------------
	}

}
