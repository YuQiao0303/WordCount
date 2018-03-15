package test;
import java.io.File;  
import java.io.InputStreamReader;  
import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.FileInputStream;  
import java.io.FileWriter; 
import java.io.IOException;


public class test 
{
	public static void main(String[] args)
	{
		System.out.println("Hello World!");
		
		//print arguments
		for (int i = 0; i < args.length; i++) 
		{ 
			System.out.print("arg "); 
			System.out.print(i); 
			System.out.print(" = "); 
			System.out.println(args[i]); 
		} 
		//write file
		try 
		{
			 /* 写入文件 */  
            File writename = new File("output.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
            writename.createNewFile(); // 创建新文件  
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
            out.write("我会写入文件啦\r\n"); // \r\n即为换行  
            out.flush(); // 把缓存区内容压入文件  
            out.close(); // 最后记得关闭文件  
			System.out.println("written"); 
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		//read file
		try 
		{ // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  

            /* 读入文件 */  
            String pathname = "my.c"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径  
            //String pathname = "output.txt";
            File filename = new File(pathname); // 要读取以上路径的input。txt文件  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line = "afasdf";  
            line = br.readLine();  
            System.out.println(line);
            while (line != null) 
            {  
                line = br.readLine(); // 一次读入一行数据  
            } 
            br.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
}
