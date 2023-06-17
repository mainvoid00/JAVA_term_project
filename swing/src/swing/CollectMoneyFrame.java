/*
 *  관리자 모드 화폐 현황 및 수금 JFrame class
 */

package swing;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

class CollectMoneyFrame extends JFrame  {
	private int Coin[] = {10,50,100,500,1000};  
	final int MAXSIZE=15;
	public CollectMoneyFrame(AtomicInteger[] stack_top, AtomicInteger[][] stack_money, String CoinNames[]
			,AtomicInteger machine_totalmoney){
		
		
		setTitle("수금");
	    setSize(500, 500);
	    setVisible(true);
	    setResizable(false);
	    GridLayout grid = new GridLayout(6, 4);
        setBackground(Color.pink);
        setLayout(grid);
        StackClass sc = new StackClass();
        JLabel CoinNa[] = new JLabel[5];       // 화폐 이름 JLabel
        JLabel Coincount[] = new JLabel[5];    // 화폐 갯수 JLabel
        JButton AddMoney[] = new JButton[5];   // 화폐 추가 button
        JButton SubMoney[] = new JButton[5];   // 화폐 빼기 button
        JLabel TotalMoney = new JLabel("총액: "+Integer.toString(machine_totalmoney.get())); // 현재 기계 안에화폐 총액 JLabel
        JButton exit = new JButton("수금 및 EXIT");    // 종료 button
        AtomicInteger collectmoney = new AtomicInteger(0);    // 수금액 count 변수 
        JLabel collectLa = new JLabel("수금액: "+collectmoney.get()+" won"); // 수금액 JLable
        exit.addActionListener(new ActionListener() {        // 종료 버튼 액션 method
        	public void actionPerformed(ActionEvent e) {
        		FileIo fileio = new FileIo();
        		fileio.CoinWrite(stack_top);
        		JOptionPane.showMessageDialog(null, Integer.toString(collectmoney.get())+" won을 수금 및 저장했습니다.");
        		dispose();
        	}
        });
        for(int i=0;i<5;i++) {     // JLabel , button 초기화
        	int index=i;
        	CoinNa[i] = new JLabel(CoinNames[i]);
        	Coincount[i]= new JLabel(Integer.toString(stack_top[i].get()+1)+"개");
        	AddMoney[i] = new JButton("+");
        	SubMoney[i] = new JButton("-");
        	AddMoney[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	if (sc.is_full(stack_top[index])==-1) {     // 화폐 추가 갯수 예외처리 
                		JOptionPane.showMessageDialog(null, "최대치입니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
                	}
                	else {
                	sc.push(stack_money[index], stack_top[index], Coin[index], machine_totalmoney);
                	Coincount[index].setText(Integer.toString(stack_top[index].get()+1)+"개");
                	TotalMoney.setText("총액: "+Integer.toString(machine_totalmoney.get()));
                	collectLa.setText("수금액: "+collectmoney.addAndGet(-Coin[index])+" won");
                	}
                }
            });
        	SubMoney[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	if(stack_top[index].get()<=4) {           // coin의 갯수가 5개 초과 일때만 뺄 수 있게 예외처리 
                		JOptionPane.showMessageDialog(null, "최소 5개 이상 있어야 합니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
                	}
                	else {      // 스택에서 pop하고 총액과 수금액 count 
                	sc.pop(stack_money[index], stack_top[index],machine_totalmoney);
                	Coincount[index].setText(Integer.toString(stack_top[index].get()+1)+"개");
                	TotalMoney.setText("총액: "+Integer.toString(machine_totalmoney.get()));
                	collectLa.setText("수금액: "+collectmoney.addAndGet(Coin[index])+" won");
                	}
                }
            });
        	this.add(CoinNa[i]);
        	this.add(Coincount[i]);
        	this.add(AddMoney[i]);
        	this.add(SubMoney[i]);
        }
        
        this.add(TotalMoney);
        this.add(collectLa);
        this.add(exit);
	}
	
}
