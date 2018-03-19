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
	public static String inputFile;    //用户输入的文件路径
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
	//___________________________________methods_________________________________
	

	
	//____________________________________getFileName___________________________
	
	//得到指定路径文件夹中所有文件名（不含路径，含后缀）
	public static String [] getFileName(String path) 
    { 
        File file = new File(path); 
        String [] fileName = file.list(); 
        return fileName; 
    } 
	//____________________________________getAllFileName___________________________
	
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
	 
	//____________________________________s()___________________________
	 
	//递归得到    指定路径文件夹   及其子文件夹中   所有符合用户要求的文件名（含绝对路径，含后缀）
	public static void s(ArrayList<String> fileNames)    //实现“-s”功能的函数
	{
		//System.out.println(inputFile);//****************************
		//inputFile="C:/Users/yuqiao/Documents/Visual Studio 2015/Projects/BookStore1/BookStore1/*.cpp";
		String path;
		String type=inputFile.substring((inputFile.lastIndexOf(".") + 1), inputFile.length()).toLowerCase();
		String fileType;
		ArrayList<File> listFileName = new ArrayList<File>();  
		//System.out.println(inputFile);//**************
		
		//如果是绝对路径
		if (inputFile.matches("[A-Z]:[\\s\\S]*\\*\\.[\\s\\S]*"))
		{
			path=inputFile.substring(0,inputFile.lastIndexOf("*")-1);
			
		}
		//如果是相对路径
		else path=".";
		
        getAllFileName(path,listFileName); 
        String fnStr;
        for(File fileName:listFileName)   //对于递归找到的所有类型文件
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
    
	//_____________________getBasicInfo()返回指定文件FileName的数据____________________
    public static void getBasicInfo(String fileName)
    {
    	//_____________________变量声明____________________
    	chars=0;
    	words=0;
    	lines=0;
    	//统计单词用的变量
    	boolean partition=true;
    	char charNow;
    	try 
		{ // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  
    		File filename = new File(fileName); // 要读取该路径的文件  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line ;  
            line = br.readLine(); 
            while (line != null) 
            {  
            	//——————————————————————统计行数和字符数__________
            	chars+=line.length();
            	lines++;
            	
            	//_______________________打印该行行号和内容
//            	System.out.print(lines);
//            	System.out.print("	");
//            	System.out.print(line);
            	
            	//________________________统计单词_____________________________
            	
            	partition=true;
            	
            	for(int i=0;i<line.length();i++)
            	{
            		charNow=line.charAt(i);
            		if(partition==true&&charNow!=' '&&charNow!='\t'&&charNow!=','&&charNow!='，')
            			{
            				words++;
            				partition=false;
            			}
            		if(charNow==' '||charNow=='\t'||charNow==','||charNow=='，')
            		{
            			partition=true;
            		}
            		
            	}
            	//-----------------------------------------------------------------
                line = br.readLine(); // 读取下一行数据  
            } 
            chars+=lines-1;    //由于换行符也算做字符，故如此
            br.close();
		}
    	
		catch (IOException e) 
		{
			e.printStackTrace();
		}     	
    }
    //________________________getStopList()__________________________
    
    //将停用词表中的单词让入数组wordsIgnored
    public static void getStopList(ArrayList<String> wordsIgnored)
    {
    	
		try 
		{ // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  

            /* 读入stopList */  
            File filename = new File(stopList); // 要读取以上路径的input。txt文件  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line ;  
            line = br.readLine();  
            
            String reg1 = "\\s+";                       
            while (line != null) 
            {  
            	//将读取的行分割成各个单词 
            	String str[] = line.split(reg1);
            	
            	for(int i=0;i<str.length;i++)
            	{
            		wordsIgnored.add(str[i]);  //将停用词表中的单词让入数组wordsIgnored
            	}
                line = br.readLine(); // 一次读入一行数据
            } 
            br.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		} 

    
    }
  //_______________getBasicInfoWithSL()返回指定文件FileName的数据(含停用词表）____________________
    public static void getBasicInfoWithSL(String fileName)
    {
    	//_____________________变量声明____________________
    	chars=0;
    	words=0;
    	lines=0;
    	ArrayList<String> wordsIgnored = new ArrayList<String>();
    	getStopList(wordsIgnored);
    	///////////////////////////////////
    	//System.out.print("stoplist:");
    	//System.out.println(wordsIgnored);
    	///////////////////////////////////////////
    	//定义一个map集合保存stoplist中的单词
    	TreeMap<String,Integer> tm = new TreeMap<String,Integer>(); 
    	
    	for(int i=0;i<wordsIgnored.size();i++)
    	{
    		String word=wordsIgnored.get(i);
    		tm.put(word,1); /////////
    	}
    	try 
		{ // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  
    		File filename = new File(fileName); // 要读取该路径的文件  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line ;  
            line = br.readLine(); 
            while (line != null) 
            {  
            	//——————————————————————统计行数和字符数__________
            	chars+=line.length();
            	lines++;
            	//_______________________打印该行行号和内容
            	//System.out.println(lines);
//            	System.out.print("	");
//            	System.out.print(line);
            	
            	//________________________统计单词_____________________________
            	
            	line.toLowerCase();
            	String reg1 = "\\s+|,+";   //用空格、tab或逗号来分割单词 
            	//String regEmp = "\\s+";
            	//将读取的文本进行分割 
            	String str[] = line.split(reg1); 
            	
            	for(String s: str)
            	{ 
            		if(!s.equals(""))
            		{
            			
               	     //判断tm即stopList中中是否已经存在该单词，如果不存在则单词数加一；若存在则不变
               			if(!tm.containsKey(s))   //tm中是否包含该单词
               			{ 
               				//System.out.println(s);
               				words++;
               			}
            		}
            		
            	 } 
            	
            	//System.out.println(tm);
            	//-----------------------------------------------------------------
                line = br.readLine(); // 读取下一行数据  
            } 
            chars+=lines-1;    //由于换行符也算做字符，故如此
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
    	//_____________________变量声明____________________
    	codeLines=0;
    	empLines=0;
    	comLines=0;
    	
    	//boolean commentingNow=false;//之前出现过“/*”且尚未出现“*/”
    	int comFlag=0;  //每遇到一个“/*”则+1，每遇到一个“*/”则减一；为正说明正处于多行注释之中
    	
    	String regCom = "(\\s*)(\\{|\\})?(\\s*)(//|/\\*)([\\s\\S]*)";     //单行注释行或多行注释起始行的正则表达式
		String regEmp ="(\\s*)(\\{|\\}|;)?(\\s*)";            //空行的正则表达式
		//String regComSingle = "(\\s*)(\\{|\\})?(\\s*)(/\\*)[\\s\\S]*(\\*/)(\\s*)";     //不可能是多行注释第一行的正则表达式
		String regComBegin = "(\\s*)(\\{|\\})?(\\s*)(/\\*)[\\s\\S]*";     //可能是多行注释第一行的正则表达式
		//String regComEnd=".*(\\*/)";    //多行注释结束
		
    	//说明：MayStartCommons为true，则说明当前行出现了/*
    	try 
		{ // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  
    		File filename = new File(fileName); // 要读取该路径的文件  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line ;  
            line = br.readLine(); 
            while (line != null) 
            {  
            	
            	//_______________________打印该行行号和内容
            	//System.out.print(lines);
            	//System.out.print("	");
            	System.out.print(line);
            	
        		//__________________判断本行类型：代码行、空行、注释行_____________________
        		
        		if(comFlag<=0)  //如果目前不在多行注释当中
        		{
        			if(line.matches(regCom))  //判断是不是注释行
            		{
            			
            			comLines++;
            			System.out.print("	");
                    	System.out.print("comment");
                    	
            			//下面判断是不是多行注释的开始
            			//统计“/*”和“*/”的个数相应的改变comFlag的值
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
        		else  //如果当前正在多行注释当中
        		{
        			//判断是否含有该多行注释结束的“*/”
    				//System.out.println("\tcomment");

        			//统计“/*”和“*/”的个数相应的改变comFlag的值
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
        			
        			if(comFlag<=0) //如果出现了多行注释结束的 “*/”
        			{
        				String sub=line.substring(line.lastIndexOf("*/")+2);
        				
        				if(sub.matches(regEmp)) //如果*/后面的子串，符合空行特征，则改整行为注释行
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
        			else //不是多行注释的结束，则一定为注释行
        			{
        				comLines++;
						System.out.print("	");
                    	System.out.print("comment");
                    	System.out.println(comFlag);
        			}
        		}
            	//-----------------------------------------------------------------
                line = br.readLine(); // 读取下一行数据  

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
		
		//
		inputFile="";
		
		for(int i=0;i<args.length;i++)
		{
			//System.out.println(args[i]);
			//判断参数情况
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
		//根据参数情况，生成输出结果字符串
		String outputStr="";
		ArrayList<String> fileNames =new ArrayList<String>();//要统计的文件名们
		
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
			fn=fileNames.get(i);    //对于每一个要统计的文件
			//System.out.println(fn);
			String fileShortName=fn.substring(fn.lastIndexOf("\\")+1, fn.length());
			
			 //基本信息
			if(needC||needW||needL)    
			{
				//统计基本信息
				if(needE)
					getBasicInfoWithSL(fn);
				else getBasicInfo(fn);
				
				//将基本信息写结果字符串
				
				if(needC)
				{
					//file.c, 字符数: 50
					outputStr+=fileShortName;
					outputStr+=", 字符数: ";
					outputStr+=chars;
					outputStr+="\r\n";
				}
				if(needW)
				{
					//file1.c, 单词数: 30
					outputStr+=fileShortName;
					outputStr+=", 单词数: ";
					outputStr+=words;
					outputStr+="\r\n";
				}
				if(needL)
				{
					//file.c, 行数: 10
					outputStr+=fileShortName;
					outputStr+=", 行数: ";
					outputStr+=lines;
					outputStr+="\r\n";
				}
			}
		
			
			if(needA)
			{
				getComInfo(fn);//统计复杂信息
				//file1.c, 代码行/空行/注释行: 5/2/3
				outputStr+=fileShortName;
				outputStr+=", 代码行/空行/注释行: ";
				outputStr+=codeLines;
				outputStr+="/";
				outputStr+=empLines;
				outputStr+="/";
				outputStr+=comLines;
				outputStr+="\r\n";
			}
			
		}
		
		System.out.println(outputStr);
		//写入文件
		if(!needO)
		{
			outputFile="result.txt";
		}
		try 
		{
			
	        File writename = new File(outputFile); // 如果没有则要建立一个新的output。txt文件  
	        writename.createNewFile(); // 创建新文件  
	        BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
	        out.write(outputStr); // \r\n即为换行  
	        out.flush(); // 把缓存区内容压入文件  
	        out.close(); // 最后记得关闭文件  
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