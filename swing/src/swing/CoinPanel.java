/*
 * 지폐 투입 버튼과, 관리자 모드, 반환 버튼을 구현한 jpanel class
*/
package swing;
import java.awt.Color;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
class CoinPanel extends JPanel {
	 static final int MAX_STACK = 50 ;

	
	 CoinPanel(AtomicInteger money, JLabel status, JLabel money_status, AtomicInteger stack_money[][], AtomicInteger stack_top[]
			 , AtomicInteger machine_totalmoney,AtomicInteger charge_1000_count, String [] drinkNames, AtomicInteger[] at_pd_count,
			 AtomicInteger Atprice[], ProductPanel[] propanel){
		 StackClass stackClass = new StackClass();
		 
     	
		String[] CoinNames = {"10원", "50원", "100원", "500원", "1000원"}; //화폐 이
		final int[] Coin= {10,50,100,500,1000};
		JButton[] charge_won = new JButton[5]; // 10, 50, 100, 500, 1000원 버튼 레퍼런스 
		int buttonWidth = 100; // 버튼의 가로 크기
	    int buttonHeight = 40; // 버튼의 세로 크기
	    int gap = 10; // 버튼 사이의 간격
	    this.setVisible(true);
	    this.setLayout(null);
	    this.setSize(480,720);
	    this.setBackground(new Color(255,0,0,0));
		// 버튼 초기 생성 
		for(int i=0;i<5;i++) {
			final int index = i;      // void actionPerforme에서 i를 사용하기위해 index 변수 선언 
			charge_won[i] = new JButton(CoinNames[i]);    // 지폐 투입 버튼 
		
			charge_won[i].addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	if(index==4) {      
	            		charge_1000_count.getAndAdd(1);    // 1000원을 입력 받으면 1000원 count
		            	
	            	}
	            		
	            	if (stackClass.is_full(stack_top[index])==-1) {          
	            		status.setText(CoinNames[index]+"더이상 투입할 수 없습니다");
	            		//화폐 스택이 가득 차 있으면 staus label 출력
	            	}
	            	else if (money.get() + Coin[index] <= 5000) { // 투입 금액이 5000원 이하이고
	            		if (index==4 && charge_1000_count.get()>3) {
	            			status.setText("1000원을 투입할 수 없습니다.");
	            			charge_1000_count.getAndAdd(-1);
	            			//만약 1000원권 투입을 3개 초과 넣었을 시 1000원 투입불
	            		}
	            		else {
	                	money.addAndGet(Coin[index]);
	                	// 총투입금액 변수 money에 돈을 투입 
	                    money_status.setText(String.valueOf(money.get()));
	                    // money vaulue 값 money label에 출력 
	                    stackClass.push(stack_money[index], stack_top[index], Coin[index], machine_totalmoney);
	                    // money stack에 coin push
	                    for(int j=0;j<5;j++) {
	                    	propanel[j].button_refresh(money,Atprice[j]);
	                    	// 구매 버튼의 색깔을 바꾸는 refresh method 실행 
	                    }
	                     
	                }
	            }
	            	
	            	
	                else {
	                    status.setText("최대 금액을 초과합니다."); // 금액이 5000원 초과이면 status label 출력 
	                }
	            }
	        });
			charge_won[i].setBounds(i*75,525,70,50);
			this.add(charge_won[i]);
		}
		
		JButton return_coin = new JButton("반환");     // 반환 버
		// 반환 조건은 1000원 부터 10원까지 최대한 큰 화폐로 반
		return_coin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	int count=4;
            	int return_count[]= {0,0,0,0,0};  // 동전 갯수 반환 카운트 변
            	charge_1000_count.set(0);       // 반환 할 시 1000원 count 0으로 초기화 
            	while(money.get()!=0) {         // 투입 금액이 0 일때까지
            		// 투입금액이 coin[count]보다 크고  스택이 비어있지 않을 때 까지
            		while(money.get()>=Coin[count] && stack_top[count].get()!=-1) {  
            			money.addAndGet(-(stackClass.pop(stack_money[count], stack_top[count], machine_totalmoney)));
            			return_count[count]++;   // moneystack에서 pop 한만큼 money에서 sub
            		} 
            		count--;                   
            		if(count==-1) break;
            	}
            	for(int j=0;j<5;j++) {
                	propanel[j].button_refresh(money,Atprice[j]);  // 구매 버튼 refresh method 실행 
            	}
            	money_status.setText(Integer.toString(money.get())); // money status set
            	String tmp = null;
            	for(int i=0;i<5;i++) {          // 반환 화폐 갯수를을 status label에 set
            		if(return_count[i]!=0) {
            			if (tmp == null) {
            	            tmp = CoinNames[i] + ":" + Integer.toString(return_count[i]) + "개 ";
            	        } else {
            	            String tmp2 = CoinNames[i] + ":" + Integer.toString(return_count[i]) + "개 ";
            	            System.out.println(tmp2);
            	            tmp += tmp2;
            	        }
            		}
            	}
            	if(tmp!= null) status.setText(tmp + "반환 되었습니다. ");
            	
            	
            	else {
            		status.setText("반환할 돈이 없습니다.");
            	}
            	
            	
            }
        });
		
		
		
		
		JButton manager_bu = new JButton("관리자 모드");      // 관리자 모드 버
		JButton exit_bu = new JButton("Program Exit");       // 자판기프로그램 종료 버튼 
		
		return_coin.setBounds(355,343,50,50);
		manager_bu.setBounds(20,348,229,172);
		exit_bu.setBounds(380,525,100,50);
		this.add(return_coin);
		this.add(manager_bu);
		this.add(exit_bu);
		
		
		
		exit_bu.addActionListener(new ActionListener() {   // 버튼을 누를 시 coin.txt drink.txt
			public void actionPerformed(ActionEvent e) {   // 저장 후 프로그램 종료 
				FileIo fileio = new FileIo();
				fileio.CoinWrite(stack_top);        
				fileio.DrinkWrite(drinkNames, Atprice, at_pd_count);
				System.exit(0);
			}
		});
		manager_bu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				managerbutton_method(stack_top, stack_money, CoinNames, machine_totalmoney, drinkNames, at_pd_count, Atprice);
			}
		});
		
	}
	 
	 
	 
	 // 비밀번호 입력 메소드 passwd.txt에서 비밀번호를 가져와 입력받은 비밀번호와 대조 후 MYframe클래스(관리자모드) 불러오기 
	 private void managerbutton_method(AtomicInteger stack_top[], AtomicInteger stack_money[][], String[] CoinNames, AtomicInteger machine_totalmoney,
			 String [] drinkNames, AtomicInteger[] at_pd_count, AtomicInteger Atprice[]){
		 String passwd_input = JOptionPane.showInputDialog(null, "비밀번호를 입력하세요.");
			try {
			    BufferedReader reader = new BufferedReader(new FileReader("src/DB/passwd.txt"));
			    String strtmp = null;
			    strtmp = reader.readLine();
			    reader.close();
			    if(passwd_input != null && passwd_input.equals(strtmp)) {
			    	new MyFrame(stack_top, stack_money, CoinNames, machine_totalmoney, drinkNames, at_pd_count, Atprice);
			    }
			    else {
			    	JOptionPane.showMessageDialog(null, "PASSWORD IS WRONG!");
			    }
			} catch (FileNotFoundException e1) {
			    JOptionPane.showMessageDialog(null, "FILE DATA OPEN ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
			    
			} catch (IOException e1) {
			    
			    JOptionPane.showMessageDialog(null, "FILE READ ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
	 }
}