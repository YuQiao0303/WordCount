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
			 /* д���ļ� */  
            File writename = new File("output.txt"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�  
            writename.createNewFile(); // �������ļ�  
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
            out.write("�һ�д���ļ���\r\n"); // \r\n��Ϊ����  
            out.flush(); // �ѻ���������ѹ���ļ�  
            out.close(); // ���ǵùر��ļ�  
			System.out.println("written"); 
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		//read file
		try 
		{ // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw  

            /* �����ļ� */  
            String pathname = "my.c"; // ����·�������·�������ԣ������Ǿ���·����д���ļ�ʱ��ʾ���·��  
            //String pathname = "output.txt";
            File filename = new File(pathname); // Ҫ��ȡ����·����input��txt�ļ�  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename)); // ����һ������������reader  
            BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
            String line = "afasdf";  
            line = br.readLine();  
            System.out.println(line);
            while (line != null) 
            {  
                line = br.readLine(); // һ�ζ���һ������  
            } 
            br.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
}
