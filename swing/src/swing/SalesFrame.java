/*
 *  일별 월별 매출관리 Jframe class
 */

package swing;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class SalesFrame extends JFrame {
	private DefaultTableModel model; 
	private JTable table;
	//Jtabel 로 구현 
	public SalesFrame()
	{
		String colName[] = {"DATE","TIME","DRINK","PRICE"};   // table에 목록 
		String[] year = new String[(LocalDate.now()).getYear() - 2000 + 2]; // 2000년 부터 현재 년도 
		String month[] = {"월","01","02","03","04","05","06","07","08","09","10","11","12"};
		String day[] = new String[32];
		day[0] = "일";
		day[1] = "01";
		day[2] = "02";
		day[3] = "03";
		day[4] = "04";
		day[5] = "05";
		day[6] = "06";
		day[7] = "07";
		day[8] = "08";
		day[9] = "09";

		year[0]="연";
		int index=1;
		for(int i=10;i<=31;i++) {
			day[i]= String.valueOf(i);
		}
		for(int i=2000; i<=(LocalDate.now()).getYear(); i++) {
			year[index]= String.valueOf(i);
			index++;
		}
		setTitle("일/월 매출");
        setSize(800, 500);
        setVisible(true);
        setResizable(false);
        setLayout(new FlowLayout());
       
        
        
        
        
        model = new DefaultTableModel(colName, 0);
        table = new JTable(model);
        
        add(new JScrollPane(table),BorderLayout.CENTER);
        JComboBox yearCombo= new JComboBox(year);         // 연도 선택 combobox
        JComboBox monthCombo = new JComboBox(month);       // 월 선택 combobox
        JComboBox dayCombo = new JComboBox(day);           // 일 선택 combobox
        JButton selectBu = new JButton("조회");             // datafile 선택 버
        JButton exitBu = new JButton("exit");           
        JLabel monthLa = new JLabel("월매출");              // 일매출 label
        JLabel dayLa = new JLabel("일매출");                 // 월매출 label
        selectBu.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String selectyear = yearCombo.getSelectedItem().toString();
        		String selectmonth =monthCombo.getSelectedItem().toString();
        		String selectday = dayCombo.getSelectedItem().toString();
        		String selectdate = selectyear+"-"+selectmonth+"-"+selectday;
        		model.setRowCount(0);
        		SalesRead(selectyear+"-"+selectmonth); // YYYY-MM.txt 읽어오는 method
        		int columnIndex = 3; // 4번째 열의 인덱스
        		int month_total = 0;
        		int day_total=0;                        // 읽어온 data를 jtable 에 구현 
        		for (int row = 0; row < model.getRowCount(); row++) {
        		   String value = (String) model.getValueAt(row, columnIndex);
        		   month_total+= Integer.parseInt(value);
        		}
        		
        		for(int row= 0; row < model.getRowCount(); row++) {
        			String valuedate = (String) model.getValueAt(row, 0);
        			if(selectdate.equals(valuedate)==true) {
        				String value = (String) model.getValueAt(row, columnIndex);
        				day_total+=Integer.parseInt(value);
        			}
        		}
        		// 월매출 set
        		monthLa.setText(selectmonth+"월: "+Integer.toString(month_total)+"원");
        		// 일매출 set
        		dayLa.setText(selectday+"일: "+Integer.toString(day_total)+"원");
        	}
        	
        });
        
        exitBu.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        
        this.add(yearCombo,BorderLayout.EAST);
        this.add(monthCombo,BorderLayout.EAST);
        this.add(dayCombo,BorderLayout.EAST);
        this.add(selectBu,BorderLayout.EAST);
        this.add(exitBu,BorderLayout.SOUTH);
        this.add(monthLa,BorderLayout.NORTH);
        this.add(dayLa,BorderLayout.NORTH);
	}
	
	
	// 매출 datafile 읽어오는 method
	void SalesRead(String date){
		
		String filename= "src/DB/"+date+".txt";   // datafile name
		//System.out.println(filename);
		try {
			 
			 BufferedReader reader = new BufferedReader(new FileReader(filename));
			 String strtmp;
			 while ((strtmp = reader.readLine()) != null) {
	                String[] parts = strtmp.split("\t");
	                model.addRow(parts);
	                
	            }
			
			reader.close();
			
		} catch(FileNotFoundException e){
			
		    JOptionPane.showMessageDialog(null, "FILE DATA OPEN ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
		    
		} catch (IOException e) {
		    
		    JOptionPane.showMessageDialog(null, "FILE READ ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
		    
		}
	}
}
