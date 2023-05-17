package swing;
import java.awt.*;
import javax.swing.*;

class CoinPanel extends JPanel {
	public CoinPanel(){
		setVisible(true);
		setSize(500,1000);
		String[] CoinNames = {"10won", "50won", "100won", "500won", "1000won"};

		JButton ten_won = new JButton(CoinNames[0]);
		JButton fifty_won = new JButton(CoinNames[1]);
		JButton hund_won = new JButton(CoinNames[2]);
		JButton five_hund_won = new JButton(CoinNames[3]);
		JButton thous_won = new JButton(CoinNames[4]);
		
		ten_won.setBounds(20,20,20,20);
		fifty_won.setBounds(20,20,20,20);
		hund_won.setBounds(20,20,20,20);
		five_hund_won.setBounds(20,20,20,20);
		thous_won.setBounds(20,20,20,20);
		
		
		this.add(ten_won);
		this.add(fifty_won);
		this.add(hund_won);
		this.add(five_hund_won);
		this.add(thous_won);
		
	}
}
