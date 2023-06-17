/*
 * 파일 입출력 method 를 구현한 class
 */

package swing;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JOptionPane;

class FileIo {
	private static final int EXIT_ON_CLOSE = 0;
	void CoinWrite(AtomicInteger[] stack_top)
	{
		try {
			 
            // 1. 파일 객체 생성
            File file = new File("src/DB/coin.txt");
 
           
            FileWriter fw = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fw);
 
            for(int i=0; i<5;i++) {
            	writer.write(Integer.toString(stack_top[i].get()+1)+"\t");
            }
            
 
            
            writer.close();
 
        } catch(FileNotFoundException e){
			System.out.println("File open error");
		    JOptionPane.showMessageDialog(null, "FILE DATA OPEN ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
		    System.exit(EXIT_ON_CLOSE);
		} catch (IOException e) {
		    
		    JOptionPane.showMessageDialog(null, "FILE READ ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
		    System.exit(EXIT_ON_CLOSE);
		}
	}
	
	
	
	
	
	void CoinRead(AtomicInteger[] stack_top, AtomicInteger[][] stack_money,
			int Coin[] , AtomicInteger machine_totalmoney)
	{
		try {
			 BufferedReader reader = new BufferedReader(new FileReader("src/DB/coin.txt"));
			 String strtmp = null;
			strtmp = reader.readLine();
			String[] parts = strtmp.split("\t");
			for(int i=0;i<5;i++) {
				int count = Integer.parseInt(parts[i].trim());
				for(int j=0;j<count;j++) {
					StackClass stackclass = new StackClass();
					stackclass.push(stack_money[i], stack_top[i], Coin[i], machine_totalmoney);
				}
				
			}
			reader.close();
		} catch(FileNotFoundException e){
			System.out.println("File open error");
		    JOptionPane.showMessageDialog(null, "FILE DATA OPEN ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
		    System.exit(EXIT_ON_CLOSE);
		} catch (IOException e) {
		    
		    JOptionPane.showMessageDialog(null, "FILE READ ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
		    System.exit(EXIT_ON_CLOSE);
		}
	}
	
	
	
	
	
	
	void DrinkRead(String [] drinkNames, AtomicInteger[] at_pd_count,
			 AtomicInteger Atprice[])
	{
		try {
		    BufferedReader reader = new BufferedReader(new FileReader("src/DB/drink.txt"));
		    String strtmp = null;
		    int index = 0;
		    while ((strtmp = reader.readLine()) != null && index < drinkNames.length) {
		        String[] parts = strtmp.split("\t");
		        if (parts.length == 3) {
		            drinkNames[index] = parts[0].trim();
		            Atprice[index] = new AtomicInteger(Integer.parseInt(parts[1].trim()));	       
		            at_pd_count[index]= new AtomicInteger(Integer.parseInt(parts[2].trim()));
		            index++;
		            
		        }
		    }
		    
		    reader.close();
		} catch (FileNotFoundException e) {
		    System.out.println("File open error");
		    JOptionPane.showMessageDialog(null, "FILE DATA OPEN ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
		    System.exit(EXIT_ON_CLOSE);
		} catch (IOException e) {
		    System.out.println("File read error");
		    JOptionPane.showMessageDialog(null, "FILE READ ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
		    System.exit(EXIT_ON_CLOSE);
		}

	}
	
	
	void DrinkWrite(String []drinkNames, AtomicInteger[] price, AtomicInteger[] at_pd_count)
	{
		 try {
			 
	            // 1. 파일 객체 생성
	            File file = new File("src/DB/drink.txt");
	
	 
	            // 3. Buffer를 사용해서 File에 write할 수 있는 BufferedWriter 생성
	            FileWriter fw = new FileWriter(file);
	            BufferedWriter writer = new BufferedWriter(fw);
	 
	            // 4. 파일에 쓰기
	           
	            for(int i=0;i<5;i++) {
	            	writer.write(drinkNames[i]+"\t"+price[i].get()+"\t"+at_pd_count[i].get());
	            	writer.newLine();
	            }
	            writer.close();
	 
	        } catch(FileNotFoundException e){
				System.out.println("File open error");
			    JOptionPane.showMessageDialog(null, "FILE DATA OPEN ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
			    System.exit(EXIT_ON_CLOSE);
			} catch (IOException e) {
			    
			    JOptionPane.showMessageDialog(null, "FILE WRITE ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
			    System.exit(EXIT_ON_CLOSE);
			}
	}
	// 매출 기록 write method
	void SalesWrite(String drinkname, int price)
	{
		LocalDate nowdate = LocalDate.now();
		LocalTime nowtime = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String formatedNow = nowtime.format(formatter);
		DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("yyyy-MM");
		String filename ="src/DB/"+nowdate.format(Dateformatter)+".txt"; // filename 현재시간 설정
		 
		try {         // file에 YYYY-MM-DD	HH:mm:ss	drinkname	price를  write
			 File file = new File(filename);      // 파일이 존재하지 않으면 새 파일을 만들음 
			 if(!file.exists()) file.createNewFile();
			 FileWriter fw = new FileWriter(file,true);
	         BufferedWriter writer = new BufferedWriter(fw);
	         writer.write(nowdate+"\t"+formatedNow+"\t"+drinkname+"\t"+price);
	         writer.newLine();
	         writer.close();
	         
		}  catch (IOException e) {
		    
		    JOptionPane.showMessageDialog(null, "FILE WRITE ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
		    System.exit(EXIT_ON_CLOSE);
		}

		 
	}
	
	
	
}
