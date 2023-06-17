/*
 * 음료 이름, 음료 갯수, 음료 가격, 구매 버튼을 구현한 JPanel class
*/
package swing;
import java.awt.Color;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
class ProductPanel extends JPanel {
	 private LinkedList<Integer> list;     // 음료 재고 linkedlist
	 private StackClass stackClass;       // stack push pop class
	 private JButton buyButton;          // 구매 버튼 
	ProductPanel(String drinkName, ImageIcon imageIcon, AtomicInteger price, AtomicInteger at_pd_count,JLabel statusLa, JLabel money_status,
			AtomicInteger money, AtomicInteger stack_money[][], AtomicInteger top[], AtomicInteger machine_totalmoney, int Coin[]
					,AtomicInteger charge_1000_count){
		list = new LinkedList<Integer>();        // 음료 재고를 linkedlist로 구현 
		for(int i=0;i<at_pd_count.get();i++) { // 음료 재고 갯수만큼 list.add
			list.add(1);
		}
		
		this.setBackground(new Color(255,0,0,0));
		this.setLayout(null);
		
		JLabel imageLabel = new JLabel(imageIcon);  //  음료 사진 
        buyButton = new JButton();                // 음료 구매 버튼 
        buyButton.setOpaque(true);
        JLabel drinkLabel = new JLabel(drinkName,JLabel.CENTER);
        JLabel price_Label = new JLabel(Integer.toString(price.get())+"원",JLabel.CENTER);
        
        Timer timer = new Timer(100, new ActionListener() {    // 0.1초 단위로 button refresh()
            public void actionPerformed(ActionEvent e) {      
                button_refresh(money, price);
            }
        });
        
        int imageX = 10; // 이미지의 가로 위치
        int imageY = 10; // 이미지의 세로 위치
        int imageWidth = 80; // 이미지의 폭
        int imageHeight = 200; // 이미지의 높이
        imageLabel.setBounds(imageX, imageY, imageWidth, imageHeight);

        int buttonX = 10; // 버튼의 가로 위치
        int buttonY = 270; // 버튼의 세로 위치
        int buttonWidth = 80; // 버튼의 폭
        int buttonHeight = 30; // 버튼의 높이
        buyButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);

        int drinkLabelX = 10; // 음료 이름의 가로 위치
        int drinkLabelY = 180; // 음료 이름의 세로 위치
        int drinkLabelWidth = 80; // 음료 이름의 폭
        int drinkLabelHeight = 20; // 음료 이름의 높이
        drinkLabel.setBounds(drinkLabelX, drinkLabelY, drinkLabelWidth, drinkLabelHeight);

        int priceLabelX = 10; // 가격의 가로 위치
        int priceLabelY = 200; // 가격의 세로 위치
        int priceLabelWidth = 80; // 가격의 폭
        int priceLabelHeight = 20; // 가격의 높이
        price_Label.setBounds(priceLabelX, priceLabelY, priceLabelWidth, priceLabelHeight);

        
        
		add(imageLabel);     
        add(drinkLabel);
        add(price_Label);
        add(buyButton);
       
        stackClass = new StackClass();
        
        
        
        timer.start();
        
        buyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {   // 물품을 구매하고 잔돈을 반환할 수 없을 때 확인 method
				if( stackClass.is_return(stack_money, top, machine_totalmoney, Coin, money, price)==false) {
					statusLa.setText("잔돈이 충분하지 않아 구매할 수 없습니다");
				}
				
				else {                // 잔돈을 반환할 수 있으면 
				if(list.size()>0) {             
					if(money.get()>=price.get()) {   // 상품 재고가 있고 투입 금액이 상품 가격보다 많으면 구매 
						
						list.remove();               // 재고 하나 삭제 
						at_pd_count.set(list.size());       // 재고 count set
						statusLa.setText(drinkName + "이(가) 구매되었습니다.");  // 물품 구매 label set
						money.addAndGet(-(price.get()));                   // 투입 금액 변
						money_status.setText(String.valueOf(money.get())); // 투입 금액 label set
						new FileIo().SalesWrite(drinkName,price.get());   // 월별 매출  데이터 파일에 write
						if(money.get()==0) {                              // 투입금액이 0원이면 1000원count 초기
							charge_1000_count.set(0);
							
						}
						
						
					}
					else {
						statusLa.setText("not enough money");
					}
				}
				else {
					statusLa.setText(drinkName + "이(가) 품절되었습니다.");
					
				}
			}
			}
		});
        
        
        
	}
	
	void button_refresh(AtomicInteger money, AtomicInteger price  ) // 구매버튼 stauts refresh 메소드 
	{
		if(list.size()==0) {
			buyButton.setText("sold out");
			buyButton.setBackground(Color.RED);
		}
		else if(price.get()<=money.get()) {
			buyButton.setText("Buy");
			buyButton.setBackground(Color.GREEN);
		}
		else {
			
			buyButton.setBackground(Color.black);
			buyButton.setText("Default");
		}
	}
}


