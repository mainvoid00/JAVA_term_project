package swing;
import javax.swing.*;
import java.awt.*;

public class ContentPanEx extends JFrame {
	public ContentPanEx () {
		setTitle("Vending Machine");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		String[] drinkNames = { "물", "커피", "이온음료", "고급커피", "탄산음료" };
        CoinPanel coinPanel = new CoinPanel();

		Container contentPane = getContentPane();
		GridLayout grid = new GridLayout(8,2);
		contentPane.setBackground(Color.pink);
		contentPane.setLayout(grid);
		
		ImageIcon imageIcon0 = new ImageIcon("src/image/water.jpg");
		ImageIcon imageIcon1 = new ImageIcon("src/image/coffee.jpg");
		ImageIcon imageIcon2 = new ImageIcon("src/image/energy_drink.jpeg");
		ImageIcon imageIcon3 = new ImageIcon("src/image/high_level_coffee.jpg");
		ImageIcon imageIcon4 = new ImageIcon("src/image/cider.jpeg");
		
        JLabel imageLabel0 = new JLabel(imageIcon0);
        JLabel imageLabel1 = new JLabel(imageIcon1);
        JLabel imageLabel2 = new JLabel(imageIcon2);
        JLabel imageLabel3 = new JLabel(imageIcon3);
        JLabel imageLabel4 = new JLabel(imageIcon4);
        JLabel[] drinkna = new JLabel[5];
        for(int i=0;i<5;i++) {
        	drinkna[i]= new JLabel(drinkNames[i]);
        }
    
		
		
		contentPane.add(imageLabel0);
		contentPane.add(imageLabel1);
		contentPane.add(drinkna[0]);
		contentPane.add(drinkna[1]);
		contentPane.add(new JButton("구매"));
		contentPane.add(new JButton("구매"));
		
		contentPane.add(imageLabel2);
		contentPane.add(imageLabel3);
		contentPane.add(drinkna[2]);
		contentPane.add(drinkna[3]);
		contentPane.add(new JButton("구매"));
		contentPane.add(new JButton("구매"));
		
		contentPane.add(imageLabel4);
		contentPane.add(drinkna[4]);
		
		contentPane.add(coinPanel);
		contentPane.add(new JButton("구매"));
		contentPane.add(new JButton("반환"));
		
		
        
		
		
	
		
		setSize(500, 1500);
		setVisible(true);
	}
}
