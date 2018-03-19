package wc;

import java.io.File;  
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileInputStream;  
import java.io.FileWriter; 
import java.io.IOException;
import java.util.List;

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
	 
	//____________________________________s()___________________________
	 
	//�ݹ�õ�    ָ��·���ļ���   �������ļ�����   ���з����û�Ҫ����ļ�����������·��������׺��
	public static void s(ArrayList<String> fileNames)    //ʵ�֡�-s�����ܵĺ���
	{
		//System.out.println(inputFile);//****************************
		//inputFile="C:/Users/yuqiao/Documents/Visual Studio 2015/Projects/BookStore1/BookStore1/*.cpp";
		String path;
		String type=inputFile.substring((inputFile.lastIndexOf(".") + 1), inputFile.length()).toLowerCase();
		String fileType;
		ArrayList<File> listFileName = new ArrayList<File>();  
		//System.out.println(inputFile);//**************
		
		//����Ǿ���·��
		if (inputFile.matches("[A-Z]:[\\s\\S]*\\*\\.[\\s\\S]*"))
		{
			path=inputFile.substring(0,inputFile.lastIndexOf("*")-1);
			
		}
		//��������·��
		else path=".";
		
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
            chars+=lines-1;    //���ڻ��з�Ҳ�����ַ��������
            br.close();
		}
    	
		catch (IOException e) 
		{
			e.printStackTrace();
		}     	
    }
    //________________________getStopList()__________________________
    
    //��ͣ�ôʱ��еĵ�����������wordsIgnored
    public static void getStopList(ArrayList<String> wordsIgnored)
    {
    	
		try 
		{ // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw  

            /* ����stopList */  
            File filename = new File(stopList); // Ҫ��ȡ����·����input��txt�ļ�  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // ����һ������������reader  
            BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
            String line ;  
            line = br.readLine();  
            
            String reg1 = "\\s+";                       
            while (line != null) 
            {  
            	//����ȡ���зָ�ɸ������� 
            	String str[] = line.split(reg1);
            	
            	for(int i=0;i<str.length;i++)
            	{
            		wordsIgnored.add(str[i]);  //��ͣ�ôʱ��еĵ�����������wordsIgnored
            	}
                line = br.readLine(); // һ�ζ���һ������
            } 
            br.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 

    
    }
  //_______________getBasicInfoWithSL()����ָ���ļ�FileName������(��ͣ�ôʱ�____________________
    public static void getBasicInfoWithSL(String fileName)
    {
    	//_____________________��������____________________
    	chars=0;
    	words=0;
    	lines=0;
    	ArrayList<String> wordsIgnored = new ArrayList<String>();
    	getStopList(wordsIgnored);
    	///////////////////////////////////
    	//System.out.print("stoplist:");
    	//System.out.println(wordsIgnored);
    	///////////////////////////////////////////
    	//����һ��map���ϱ���stoplist�еĵ���
    	TreeMap<String,Integer> tm = new TreeMap<String,Integer>(); 
    	
    	for(int i=0;i<wordsIgnored.size();i++)
    	{
    		String word=wordsIgnored.get(i);
    		tm.put(word,1); /////////
    	}
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
            	//System.out.println(lines);
//            	System.out.print("	");
//            	System.out.print(line);
            	
            	//________________________ͳ�Ƶ���_____________________________
            	
            	line.toLowerCase();
            	String reg1 = "\\s+|,+";   //�ÿո�tab�򶺺����ָ�� 
            	//String regEmp = "\\s+";
            	//����ȡ���ı����зָ� 
            	String str[] = line.split(reg1); 
            	
            	for(String s: str)
            	{ 
            		if(!s.equals(""))
            		{
            			
               	     //�ж�tm��stopList�����Ƿ��Ѿ����ڸõ��ʣ�����������򵥴�����һ���������򲻱�
               			if(!tm.containsKey(s))   //tm���Ƿ�����õ���
               			{ 
               				//System.out.println(s);
               				words++;
               			}
            		}
            		
            	 } 
            	
            	//System.out.println(tm);
            	//-----------------------------------------------------------------
                line = br.readLine(); // ��ȡ��һ������  
            } 
            chars+=lines-1;    //���ڻ��з�Ҳ�����ַ��������
            br.close();
		}
    	
		catch (IOException e) 
		{
			e.printStackTrace();
		}     	
    }
    
    //_______________________________getComInfo()____________________________
    public static void getComInfo(String fileName)
    {
    	//_____________________��������____________________
    	codeLines=0;
    	empLines=0;
    	comLines=0;
    	
    	//boolean commentingNow=false;//֮ǰ���ֹ���/*������δ���֡�*/��
    	int comFlag=0;  //ÿ����һ����/*����+1��ÿ����һ����*/�����һ��Ϊ��˵�������ڶ���ע��֮��
    	
    	String regCom = "(\\s*)(\\{|\\})?(\\s*)(//|/\\*)([\\s\\S]*)";     //����ע���л����ע����ʼ�е�������ʽ
		String regEmp ="(\\s*)(\\{|\\}|;)?(\\s*)";            //���е�������ʽ
		//String regComSingle = "(\\s*)(\\{|\\})?(\\s*)(/\\*)[\\s\\S]*(\\*/)(\\s*)";     //�������Ƕ���ע�͵�һ�е�������ʽ
		String regComBegin = "(\\s*)(\\{|\\})?(\\s*)(/\\*)[\\s\\S]*";     //�����Ƕ���ע�͵�һ�е�������ʽ
		//String regComEnd=".*(\\*/)";    //����ע�ͽ���
		
    	//˵����MayStartCommonsΪtrue����˵����ǰ�г�����/*
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
            	
            	//_______________________��ӡ�����кź�����
            	//System.out.print(lines);
            	//System.out.print("	");
            	System.out.print(line);
            	
        		//__________________�жϱ������ͣ������С����С�ע����_____________________
        		
        		if(comFlag<=0)  //���Ŀǰ���ڶ���ע�͵���
        		{
        			if(line.matches(regCom))  //�ж��ǲ���ע����
            		{
            			
            			comLines++;
            			System.out.print("	");
                    	System.out.print("comment");
                    	
            			//�����ж��ǲ��Ƕ���ע�͵Ŀ�ʼ
            			//ͳ�ơ�/*���͡�*/���ĸ�����Ӧ�ĸı�comFlag��ֵ
            			String s=line;
            			
            			int i=0;
            			
            			while(i<s.length())
            			{
            				if(s.indexOf("/*",i)==i)
            				{
            					comFlag++;
            					
            					i+=2;
            				}
            				else if(s.indexOf("*/",i)==i)
            				{
            					comFlag--;
//            					System.out.print("i;m here");
//            					System.out.print(s.indexOf("*/",i));
//            					System.out.print(i);
            					i+=2;
            				}
            				else i++;

            			}
            			System.out.println(comFlag);
            			
            		}
            		else if (line.matches(regEmp))
            		{
            			//isEmpLine=true;
            			empLines++;
            			System.out.print("	");
                    	System.out.print("emp");
                    	System.out.println(comFlag);
            			//System.out.println("\tempty");
            		}
            		else
            		{
            			//isCodLine=true;
            			codeLines++;
            			System.out.print("	");
                    	System.out.print("code");
                    	System.out.println(comFlag);
            			//System.out.println("\tcode");
            		}
        		}
        		else  //�����ǰ���ڶ���ע�͵���
        		{
        			//�ж��Ƿ��иö���ע�ͽ����ġ�*/��
    				//System.out.println("\tcomment");

        			//ͳ�ơ�/*���͡�*/���ĸ�����Ӧ�ĸı�comFlag��ֵ
        			//int countLeft=0,countRight=0;
        			String s=line;
        			int i=0;
        			i=0;
        			while(i<s.length())
        			{
        				if(s.indexOf("/*",i)==i)
        				{
        					comFlag++;
        					i+=2;
        				}
        				else if(s.indexOf("*/",i)==i)
        				{
        					comFlag--;
        					i+=2;
        				}
        				else i++;

        			}
        			
        			if(comFlag<=0) //��������˶���ע�ͽ����� ��*/��
        			{
        				String sub=line.substring(line.lastIndexOf("*/")+2);
        				
        				if(sub.matches(regEmp)) //���*/������Ӵ������Ͽ����������������Ϊע����
        					{
        							comLines++;
//        							System.out.print("	");
//        	                    	System.out.print("comment   ");
//        	                    	System.out.print(sub);
//        	                    	System.out.println(comFlag);
        					}
        				else 
        				{
        					codeLines++;   
        					System.out.print("	");
                        	System.out.print("code   ");
                        	System.out.print(sub);
                        	System.out.println(comFlag);
        				}   
        			}
        			else //���Ƕ���ע�͵Ľ�������һ��Ϊע����
        			{
        				comLines++;
						System.out.print("	");
                    	System.out.print("comment");
                    	System.out.println(comFlag);
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
		
		//
		inputFile="";
		
		for(int i=0;i<args.length;i++)
		{
			//System.out.println(args[i]);
			//�жϲ������
			switch(args[i])
			{
				case "-c" : needC=true;break;
				case "-w" : needW=true;break;
				case "-l" : needL=true;break;
				case "-o" : needO=true;outputFile=args[i+1];break;
				
				case "-s" : needS=true;break;
				case "-a" : needA=true;break;
				case "-e" : needE=true;stopList=args[i+1];break;
				
				default :
					if(!args[i-1].equals("-e")&&!args[i-1].equals("-o"))
						{
							
							inputFile=args[i];
						}
			}
			
		}
//		System.out.print("inputFile is:");
//		System.out.println(inputFile);
//		
//		System.out.print("stopList is:");
//		System.out.println(stopList);
		//���ݲ�������������������ַ���
		String outputStr="";
		ArrayList<String> fileNames =new ArrayList<String>();//Ҫͳ�Ƶ��ļ�����
		
		if(!needS)                  
		{
			fileNames.add(inputFile);
		}
		else
		{
			s(fileNames);
		}
		int len=fileNames.size();
		String fn;
		
		for(int i=0;i<len;i++)   
		{
			fn=fileNames.get(i);    //����ÿһ��Ҫͳ�Ƶ��ļ�
			//System.out.println(fn);
			String fileShortName=fn.substring(fn.lastIndexOf("\\")+1, fn.length());
			
			 //������Ϣ
			if(needC||needW||needL)    
			{
				//ͳ�ƻ�����Ϣ
				if(needE)
					getBasicInfoWithSL(fn);
				else getBasicInfo(fn);
				
				//��������Ϣд����ַ���
				
				if(needC)
				{
					//file.c, �ַ���: 50
					outputStr+=fileShortName;
					outputStr+=", �ַ���: ";
					outputStr+=chars;
					outputStr+="\r\n";
				}
				if(needW)
				{
					//file1.c, ������: 30
					outputStr+=fileShortName;
					outputStr+=", ������: ";
					outputStr+=words;
					outputStr+="\r\n";
				}
				if(needL)
				{
					//file.c, ����: 10
					outputStr+=fileShortName;
					outputStr+=", ����: ";
					outputStr+=lines;
					outputStr+="\r\n";
				}
			}
		
			
			if(needA)
			{
				getComInfo(fn);//ͳ�Ƹ�����Ϣ
				//file1.c, ������/����/ע����: 5/2/3
				outputStr+=fileShortName;
				outputStr+=", ������/����/ע����: ";
				outputStr+=codeLines;
				outputStr+="/";
				outputStr+=empLines;
				outputStr+="/";
				outputStr+=comLines;
				outputStr+="\r\n";
			}
			
		}
		
		System.out.println(outputStr);
		//д���ļ�
		if(!needO)
		{
			outputFile="result.txt";
		}
		try 
		{
			
	        File writename = new File(outputFile); // ���û����Ҫ����һ���µ�output��txt�ļ�  
	        writename.createNewFile(); // �������ļ�  
	        BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
	        out.write(outputStr); // \r\n��Ϊ����  
	        out.flush(); // �ѻ���������ѹ���ļ�  
	        out.close(); // ���ǵùر��ļ�  
			//System.out.println("already written the output.txt"); 
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		
	


		//---------------------------------------------------------------------------
		
		//---------------------------------------------------------------------------
	}

}