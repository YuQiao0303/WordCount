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
    
    //_____________________getInfo()返回指定文件inputFile的数据____________________
    public static void getInfo()
    {
    	if(inputFile==null)
    	{
    		System.out.println("Error: inputFile==null !!");
    		return;
    	}
    	//_____________________变量声明____________________
    	chars=0;
    	words=0;
    	lines=0;
    	codeLines=0;
    	empLines=0;
    	comLines=0;
    	
    	//统计单词用的变量
    	boolean partition=true;
    	char charNow;
    	
    	//判断类型用的变量
    	//boolean isComLine=false;    //当前行是注释行
    	//boolean isCodLine=false;    //当前行是代码行
    	//boolean isEmpLine=false;    //当前行是空行
    	//boolean mayStartCom=false;  //当前行可能是多行注释的第一行（含"/*"）
    	//boolean noStartCom=false;   //当前行绝对不是多行注释的第一行（虽然含"/*"，但本行后面有"*/"）
    	//boolean endCom=false;
    	boolean commentingNow=false;//之前出现过“/*”且尚未出现“*/”
    	
    	String regCom = "(\\s*)(\\{|\\})?(\\s*)(//|/\\*)(.*)";     //注释行的正则表达式
		String regEmp ="(\\s*)(\\{|\\})?(\\s*)";            //空行的正则表达式
		String regComSingle = "(\\s*)(\\{|\\})?(\\s*)(/\\*).*(\\*/)(\\s*)";     //不可能是多行注释第一行的正则表达式
		String regComBegin = "(\\s*)(\\{|\\})?(\\s*)(/\\*).*";     //可能是多行注释第一行的正则表达式
		String regComEnd=".*(\\*/)";    //多行注释结束
		
    	//说明：如果noStartComm为false，且MayStartCommons为true，则说明当前行是多行注释的第一行
    	
    	
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
            	//——————————————————————统计行数和字符数__________
            	chars+=line.length();
            	lines++;
            	//_______________________打印该行行号和内容
            	System.out.print(lines);
            	System.out.print("	");
            	System.out.print(line);
            	
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
            	
        		//System.out.println(words);
        		//__________________判断本行类型：代码行、空行、注释行_____________________
        		
        		if(commentingNow==false)  //如果目前不在多行注释当中
        		{
        			if(line.matches(regCom))  //判断是不是注释行
            		{
            			//isComLine=true;
            			comLines++;
            			
            			//下面判断是不是多行注释的开始
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
        		else  //如果当前正在多行注释当中,当前行必为注释行
        		{
        			comLines++;
    				System.out.println("\tcomment");
        			if(line.matches(regComEnd))   //如果匹配多行注释结束表达式，则将endCom=1,com=0
        			{
        				commentingNow=false;
        				
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
		//下面测试正则表达式
//		String line="	}/* sf*/	";
//		String regCom = "(\\s*)(\\{|\\})?(\\s*)(//|/\\*)(.*)";     //注释行的正则表达式
//		String regCod ="(\\s*)(\\{|\\})?(\\s*)";            //空行的正则表达式
//		String regComSingle = "(\\s*)(\\{|\\})?(\\s*)(/\\*).*(\\*/)(\\s*)";     //不可能是多行注释开始
//		String regComBegin = "(\\s*)(\\{|\\})?(\\s*)(/\\*).*";     //多行注释开始
//		
//		System.out.println(line);
//		System.out.println(line.matches(regCom)?"是注释行":"");   
//		System.out.println(line.matches(regCod)?"是空行":""); 
//		System.out.println(line.matches(regComBegin)?"多行注释开始":""); 
//		System.out.println(line.matches(regComSingle)?"不可能是多行注释开始":""); 
		//---------------------------------------------------------------------------
	}

}
