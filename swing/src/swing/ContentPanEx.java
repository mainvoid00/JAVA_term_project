/*
 * vendingmachine 의 main Frame
*/
package swing;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ContentPanEx extends JFrame {
	static final int MAX_STACK = 50;
	JLabel statusLa;               // 이벤트 상태를 출력해주는 label
	String[] drinkNames;           // 음료 이름 
	AtomicInteger[] at_pd_count;   // 음료 재고 갯수
	
	AtomicInteger money; // 넣어진 돈
	AtomicInteger Atprice[] ;   // 음료 가격  
	AtomicInteger[][] stack_money ;// 기계안에 있는 돈 스택 
	AtomicInteger[] stack_top ;// 돈 스택 
	AtomicInteger charge_1000_count;// 1000원을 넣은 갯수 count
	AtomicInteger machine_totalmoney; // 기계 안에 있는 총 돈
	ImageIcon imageIcon[];
	JLabel money_status;
	public ContentPanEx () {
		Container contentPane = getContentPane();
		ImageIcon icon = new ImageIcon("src/image/vending_machine.png"); // 그림 출처: 20 김창환
	       
        //배경 Panel 생성후 컨텐츠페인으로 지정      
        JPanel background = new JPanel() {
            public void paintComponent(Graphics g) {
               
                g.drawImage(icon.getImage(), 0, 0, null);
                
                setOpaque(false); //그림을 표시하게 설정,투명하게 조절
                super.paintComponent(g);
            }
        };
        // vendingmachine background 생성 method
        
		
		
		
		
        
		contentPane.setLayout(null);//레이아웃을 내맘대로 설정가능하게 해줌.
		setTitle("Vending Machine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // x버튼을 누르면 전원이 꺼짐
		statusLa = new JLabel("default",JLabel.CENTER);  // 이벤트 상태를 출력해주는 label
		setResizable(false);
		setLocationRelativeTo(null);//창이 가운데 나오게
		FileIo fileio = new FileIo();
		final int Coin[] = {10,50,100,500,1000};
		drinkNames = new String[5];    // 음료 이름
		at_pd_count = new AtomicInteger[5]; // 음료의 재고 
		 money = new AtomicInteger(0); // 넣어진 돈
		Atprice = new AtomicInteger[5];   // 음료 가격  
		stack_money = new AtomicInteger[5][MAX_STACK+1];// 기계안에 있는 돈 스택 
		stack_top = new AtomicInteger[5];// 돈 스택 
		charge_1000_count= new AtomicInteger(0);// 1000원을 넣은 갯수 count
		machine_totalmoney = new AtomicInteger(0); //기계안에 있는 총 돈 
		for(int i=0;i<5;i++) {
			stack_top[i] = new AtomicInteger(-1);     // 돈의 stack top을 -1 초기화 
		}


		for (int i = 0; i < 5; i++) {
		    for (int j = 0; j < MAX_STACK+1; j++) {
		        stack_money[i][j] = new AtomicInteger();
		    }
		}
		// 돈의 스택 초기화 
		
		
		fileio.DrinkRead(drinkNames, at_pd_count, Atprice); 
		fileio.CoinRead(stack_top, stack_money, Coin, machine_totalmoney);
        // 음료 이름과, 음료 갯수, 음료 가격을 drink.txt에서 읽어와 저
		// 돈의 현황을 coin.txt에서 읽어와 저장 
		
		money_status = new JLabel(String.valueOf(money.get()),JLabel.CENTER);
		// 돈의 투입 금액을 출력해주는 label
		
		imageIcon = new ImageIcon[5];
		imageIcon[0] = new ImageIcon("src/image/water.jpg");
	    imageIcon[1] = new ImageIcon("src/image/coffee.jpg");
		imageIcon[2] = new ImageIcon("src/image/energy_drink.jpeg");
		imageIcon[3] = new ImageIcon("src/image/high_level_coffee.jpg");
		imageIcon[4]= new ImageIcon("src/image/cider.jpeg");
		// 음료 사진을 ImageIcon으로 생성 
		
		ProductPanel propanel[] = new ProductPanel[5]; // 상품 class 객체 배열로 생성 (상품이 5개 이므로)
		
		
		for (int i = 0; i < 5; i++) {
            propanel[i] = new ProductPanel(drinkNames[i], imageIcon[i], Atprice[i], at_pd_count[i],statusLa,
            		money_status, money,stack_money,stack_top,machine_totalmoney, Coin
            		,charge_1000_count);
            
            contentPane.add(propanel[i]);
            
            propanel[i].setBounds(i*100,20,80,300);
            
        }    //productPanel을 초기화 하여 contentPane Frame 에 add함 
		
		CoinPanel coinPanel = new CoinPanel(money, statusLa, money_status, stack_money,
				stack_top, machine_totalmoney,charge_1000_count, drinkNames, at_pd_count, Atprice,
				propanel); // coinpanel 객체 생성 

        
        contentPane.add(coinPanel);  // coinpanel을 현재 프레임에 add
		statusLa.setOpaque(true);
        statusLa.setBounds(0,580,410,78);
        statusLa.setBackground(Color.black);
        statusLa.setForeground (Color.red);
        statusLa.setFont(statusLa.getFont().deriveFont(13.0f));
        contentPane.add(statusLa);
      // status label option 을 설정 및 contentpane에 add
        money_status.setOpaque(true);
        money_status.setBackground(Color.black);
        money_status.setForeground (Color.red);
        money_status.setBounds(280,335,60,25);
		contentPane.add(money_status);
		// money status label option 설정 및 contentpane에 add 
		
	
		
		setSize(480, 720);
		setVisible(true);
		// Jframe 사이즈 
		
		
		
		
		contentPane.add(background);     // background 이미지 add
        background.setBounds(0,0,480,720);    // background size 조절 
		
	} 
	
	
}

