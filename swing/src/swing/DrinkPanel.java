/*
 * 상품 재고 및 음료 현황, 음료 이름, 음료 가격을 관리하는 JFrame class
*/
package swing;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

class DrinkPanel extends JFrame {
	static final int MAX_SIZE = 15;
	static final int MIN_SIZE = 3;
	public DrinkPanel(String [] drinkNames, AtomicInteger[] at_pd_count,
			 AtomicInteger Atprice[]) {
		setTitle("음료 현황 및 변경");
        setSize(800, 500);
        setVisible(true);
        GridLayout grid = new GridLayout(6, 7);
        setLayout(grid);
        setResizable(false);
        FileIo fileio = new FileIo();
        JLabel[] DrinkNaLa = new JLabel[5];         // 음료 이름 Label
        JLabel[] pdcountLa = new JLabel[5];        //  음료 재고 Label
        JLabel[] priceLa = new JLabel[5];          // 가격 Label
        JButton[] sum_product = new JButton[5];     // 재고 추가 버튼 
        JButton[] sub_product = new JButton[5];       // 재고 반출 버튼 
        JButton[] change_drink_na = new JButton[5];    // 음료 이름 변경 버튼 
        JButton[] change_drink_price = new JButton[5];    // 음료 가격 변경 버튼 
        JButton exit = new JButton("save and exit");      // 종료 버튼 
        for(int i=0;i<5;i++) {
        	int index=i;
        	DrinkNaLa[i] = new JLabel((drinkNames[i]),JLabel.CENTER);
        	pdcountLa[i]= new JLabel((Integer.toString(at_pd_count[i].get())+"개"),JLabel.CENTER);
        	priceLa[i] = new JLabel((Integer.toString(Atprice[i].get())+"won"),JLabel.CENTER);
        	sum_product[i]= new JButton("+");
        	sub_product[i]= new JButton("-");
        	change_drink_na[i] = new JButton("상품 이름 변경");
        	change_drink_price[i] = new JButton("상품 가격 변경");
        	
        	
        	
        	sum_product[i].addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			if(at_pd_count[index].get() == MAX_SIZE) {    // max size 예외처리 
        				JOptionPane.showMessageDialog(null, "재고를 더 넣을 수 없습니다.");
        			}
        			else {
        				at_pd_count[index].addAndGet(1);          // at_pd_count[i] ++
            			pdcountLa[index].setText((Integer.toString(at_pd_count[index].get())+"개"));
        			}
        		}
        	});
        	
        	
        	sub_product[i].addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {       
        			if(at_pd_count[index].get()<=MIN_SIZE) {            // minsize 예외처리 
        				JOptionPane.showMessageDialog(null, "재고는 최소 3개 이상이어야만 됩니다.");
        			}
        			else {
        				at_pd_count[index].addAndGet(-1);
        				pdcountLa[index].setText((Integer.toString(at_pd_count[index].get())+"개"));
        			}
        		}
        	});
        	
        	change_drink_na[i].addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			String tmp = JOptionPane.showInputDialog(null,"음료 이름을 입력하세요.");
        			drinkNames[index] = tmp;
        			DrinkNaLa[index].setText(drinkNames[index]);
        		}
        	});
        	
        	
        	change_drink_price[i].addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			String tmp = JOptionPane.showInputDialog(null,"음료 가격을 입력하세요.(숫자만)");
        			try {
        	            Atprice[index].set(Integer.parseInt(tmp));
        	            priceLa[index].setText(Integer.toString(Atprice[index].get())+"won");
        	            }
        			catch(NumberFormatException e1){
        				JOptionPane.showMessageDialog(null, "숫자만 입력해 주세요");
        	            }
        	        }
        	     
        	});
        }
        
        exit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		fileio.DrinkWrite(drinkNames, Atprice, at_pd_count);
        		JOptionPane.showMessageDialog(null, "FILE SAVE");
        		new ContentPanEx().invalidate();
        		dispose();
        	}
        });
        
        
        
        this.add(new JLabel(("이름"),JLabel.CENTER));
        this.add(new JLabel(("재고"),JLabel.CENTER));
        this.add(new JLabel(("가격"),JLabel.CENTER));
        this.add(new JLabel(("재고 투입"),JLabel.CENTER));
        this.add(new JLabel(("재고 반출"),JLabel.CENTER));
        this.add(new JLabel((""),JLabel.CENTER));
        this.add(exit);
        
        for(int i=0;i<5;i++) {
        	this.add(DrinkNaLa[i]);
        	this.add(pdcountLa[i]);
        	this.add(priceLa[i]);
        	this.add(sum_product[i]);
        	this.add(sub_product[i]);
        	this.add(change_drink_na[i]);
        	this.add(change_drink_price[i]);
        	
        }
        
        
	}
}
