/*
 * 관리자 모드 Frame class
 */

package swing;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

class MyFrame extends JFrame {
	static final char special_chars[] = {'!', '@', '#', '$', '%', '^', '&', '*', '+', '-'};//특수문자 
    static final char numbers[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}; // 비밀번호 숫자
    public MyFrame(AtomicInteger[] stack_top, AtomicInteger[][] stack_money, String CoinNames[],
    		AtomicInteger machine_totalmoney, String [] drinkNames, AtomicInteger[] at_pd_count,
			 AtomicInteger Atprice[]) {
         

        setTitle("관리자 모드");
        setSize(500, 500);
        setVisible(true);
        setResizable(false);
        GridLayout grid = new GridLayout(3, 2);
        setBackground(Color.pink);
        setLayout(grid);
        JButton ManagerBu[] = new JButton[6];
        ManagerBu[0] = new JButton("음료 현황 및 변경");
        ManagerBu[1] = new JButton("화폐현황 및 수금");
        ManagerBu[2] = new JButton("NULL");
        ManagerBu[3] = new JButton("관리자 비밀번호 변경");
        ManagerBu[4] = new JButton("일별/월별 매출");
        ManagerBu[5] = new JButton("종료");
        
        
        for (int i = 0; i < 6; i++) {
            add(ManagerBu[i]);
        }
        
        
        ManagerBu[0].addActionListener(new ActionListener() {    
        	public void actionPerformed(ActionEvent e) {
        		new DrinkPanel(drinkNames, at_pd_count,Atprice);
        	}
        });
        
        
        ManagerBu[1].addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new CollectMoneyFrame(stack_top, stack_money, CoinNames, machine_totalmoney);
        	}
        });
        
        ManagerBu[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               change_passwd_method();
            }
        });
        
        
        
        ManagerBu[4].addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		new SalesFrame();
        	}
        });
        
        ManagerBu[5].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
         // 비밀번호에 특수문자 포함 여부 검사 
    private boolean hasSpecialChar(String password, char[] special_chars) {
        for (char special_char : special_chars) {
            if (password.contains(String.valueOf(special_char))) {
                return true;
            }
        }
        return false;
    }
    //   비밀번호 숫자 포함 여부 검사 
    private boolean hasNumber(String password, char[] numbers) {
        for (char number : numbers) {
            if (password.contains(String.valueOf(number))) {
                return true;
            }
        }
        return false;
    }
    
    // 비밀번호 변경 method
    private void change_passwd_method()
    {    // 새 비밀번호 입력 
    	String change_passwd = JOptionPane.showInputDialog(null,
                "변경할 비밀번호를 입력하세요.(8자 이상, 숫자 하나 이상 또는 특수문자 하나 이상 포함)");
        if (change_passwd != null) {
        	// 새 비밀번호 확인 받기
            String change_passwd_confirm = JOptionPane.showInputDialog(null,
                    "변경할 비밀번호를 다시 입력하세요.");
         // 비밀번호 변경 조건 확인
            if (change_passwd.equals(change_passwd_confirm) && change_passwd.length() >= 8 &&
                    (hasNumber(change_passwd, numbers) || hasSpecialChar(change_passwd, special_chars))) {
                try {
                	// 새 비밀번호 저장
                    OutputStream output = new FileOutputStream("src/DB/passwd.txt");
                    output.write(change_passwd.getBytes());
                    output.close();
                    // 변경 완료 메시지 표시
                    JOptionPane.showMessageDialog(null, "비밀번호가 성공적으로 변경되었습니다.");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
            	// 예외처리 
                JOptionPane.showMessageDialog(null, "비밀번호가 조건을 만족하지 않습니다. 다시 입력해주세요.");
            }
        }
    }
    
    
}
