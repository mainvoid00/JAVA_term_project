/*
 *  money stack 관련 push, pop method class method 구현class
 */

package swing;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;
 class StackClass {
	 static final int MAX_STACK = 50 ;
	
	int is_full(AtomicInteger top) {
		if(top.get()>=MAX_STACK-1) {
			return -1;
			
		}
		else return 1;
	}
	int is_empty(AtomicInteger top) {
		if(top.get()==-1) {
			return -1;
		}
		else {
			return 1;
		}
	}
	void push(AtomicInteger[] stack_money, AtomicInteger top, 
			int data, AtomicInteger machine_totalmoney) {
	    if (is_full(top) == 1) {
	        top.getAndAdd(1);
	        stack_money[top.get()].set(data);
	        machine_totalmoney.addAndGet(data);
	    }
	    
	}

	int pop(AtomicInteger stack_money[], AtomicInteger top, AtomicInteger machine_totalmoney) {
		if(is_empty(top)==1) {
			top.getAndAdd(-1);
			machine_totalmoney.addAndGet(-(stack_money[top.get()+1].get()));
			return stack_money[top.get()+1].get();
			
		}
		else return 0;
	}
	
	
	
	// 물품을 구매할 때 잔돈을 반환해줄 수 있는지 확인해주는 method
	boolean is_return(AtomicInteger[][] stack_money, AtomicInteger[] top, AtomicInteger machine_totalmoney, int[] Coin, AtomicInteger money, AtomicInteger price) {
	    int tmp_money = money.get()-price.get();
	    int index = -1;

	    
	    for (int i = Coin.length - 1; i >= 0; i--) {
	        if (Coin[i] <= tmp_money) {
	            index = i;
	            break;
	        }
	    }

	    
	   
	    for (int i = index; i >= 0; i--) {
	        int popcount = 0;
	        while (top[i].get() != -1) {
	            tmp_money -= pop(stack_money[i], top[i], machine_totalmoney);
	            popcount++;
	        }
	        for (int j = 0; j < popcount; j++) {
	            push(stack_money[i], top[i], Coin[i], machine_totalmoney);
	        }
	    }

	    return tmp_money <= 0;
	}

}
