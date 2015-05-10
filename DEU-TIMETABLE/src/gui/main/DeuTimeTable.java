package gui.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager; 	
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import gui.main.AddMenu;
import gui.main.main_layout;
import data.DefineString;

public class DeuTimeTable extends JPanel implements AddMenu, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Label lbl1, lbl2, lbl3;
	static int row,col=0;
	static int bottomRow =0;
	Object object, scheObject = "";
	JTable table, table_add, timetable;
	private String bottomData[][];
	private String[] colName = {DefineString.Parser.SORT, DefineString.Parser.LECTURE_NUM, DefineString.Parser.LECTURE_NAME, DefineString.Parser.CREDIT, 
			DefineString.Parser.TIME, DefineString.Parser.GRADE, DefineString.Parser.PROFESSOR, DefineString.Parser.LECTURE_ROOM};
	DefaultTableModel dtm = new DefaultTableModel(colName, bottomRow);
	private String data[][];
	   static Object [][]time = {
           {"09:00 ~ 09:50", "", "", "", "", "", "", ""},
           {"10:00 ~ 10:50", "", "", "", "", "", "", ""},
           {"11:00 ~ 11:50", "", "", "", "", "", "", ""},
           {"12:00 ~ 12:50", "", "", "", "", "", "", ""},
           {"13:00 ~ 13:50", "", "", "", "", "", "", ""},
           {"14:00 ~ 14:50", "", "", "", "", "", "", ""},
           {"15:00 ~ 15:50", "", "", "", "", "", "", ""},
           {"16:00 ~ 16:50", "", "", "", "", "", "", ""},
           {"17:00 ~ 17:50", "", "", "", "", "", "", ""},
           {"18:00 ~ 18:50", "", "", "", "", "", "", ""},
           {"19:00 ~ 19:50", "", "", "", "", "", "", ""}
           };
	
	
	public void top(final JFrame frame) {
		// 메뉴바 객체 생성
		JMenuBar menuBar = new JMenuBar();
		
		// 메뉴 추가
		JMenu menu = new JMenu(DefineString.Menu.FILE);
		JMenu help = new JMenu(DefineString.Menu.HELP);
		
		
		// 서브 메뉴 생성
		JMenuItem show = new JMenuItem(DefineString.Menu.SHOW_TIME_TABLE);
		JMenuItem save = new JMenuItem(DefineString.Menu.SAVE_TIME_TABLE);
		JMenuItem load = new JMenuItem(DefineString.Menu.LOAD_TIME_TABLE);
		JMenuItem load_lecture = new JMenuItem(DefineString.Menu.LOAD_HAND_BOOK);
	    JMenuItem curriculum = new JMenuItem(DefineString.Menu.LOAD_CURRICULUM);
		
		JMenuItem about = new JMenuItem(DefineString.AboutThis.TITLE);

		// 서브 메뉴 추가
		menu.add(show);
		menu.add(save);
		menu.add(load);
		menu.addSeparator();	// 분리선 추가
		menu.add(load_lecture);
	    menu.add(curriculum);
		help.add(about);
		
		// 메뉴바에 메뉴 추가
		menuBar.add(menu);
		menuBar.add(help);
		
		// 프레임에 메뉴바 추가
		frame.setJMenuBar(menuBar);
		
	      //메뉴 기능 추가
	      load_lecture.addActionListener(new ActionListener() {
	        
	        public void actionPerformed(ActionEvent arg0) {
	            try                                      
	             { 
	               //강의편람 열기
	                 Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "201502_sugang_handbook.hwp");

	             } catch (Exception e)                   
	               { 
	                   System.out.println(DefineString.NO_FILE);  
	               } 
	         } 
	                   
	     });
	      
	      curriculum.addActionListener(new ActionListener() {
	      
	      @Override
	      public void actionPerformed(ActionEvent arg0) {
	         // TODO Auto-generated method stub
	          try                                      
	                { 
	                  //강의편람 열기
	                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "curriculum.pdf");

	                } catch (Exception e)                   
	                  { 
	                      System.out.println(DefineString.NO_FILE);  
	                  } 
	            } 
	   });
		
		//jcombobox 객체 추가
		JComboBox<String> jc = new JComboBox<String>();
		add(jc);
		jc.addItem(DefineString.Parser.SORT);
		jc.addItem(DefineString.Parser.LECTURE_NAME);
		jc.addItem(DefineString.Parser.CREDIT);
		jc.addItem(DefineString.Parser.TIME);
		jc.addItem(DefineString.Parser.GRADE);
		jc.addItem(DefineString.Parser.PROFESSOR);
		jc.addItem(DefineString.Parser.LECTURE_ROOM);
		
		//jtextfield 객체 추가
		JTextField jt = new JTextField(10);
		add(jt);
		
		//jbutton 객체 추가
		JButton search = new JButton(DefineString.SEARCH);
		add(search);
	} 

	public void middle(final JFrame frame) {
		FileInputStream fis = null;
		int rowindex=0;
		int columnindex=0;
		int rowindex1=0;
		 data = new String[100][100];
		 JFileChooser jfc = new JFileChooser();
		 jfc.setMultiSelectionEnabled(true);
	     jfc.showOpenDialog(null);
	     File selectedFiles[] = jfc.getSelectedFiles(); // 파일 선택
	     
	     for(int i=0; i<jfc.getSelectedFiles().length; i++)
	     {
	    	 
	      try {

	         fis = new FileInputStream(jfc.getSelectedFiles()[i].toString()); // 파일이름 불러오기
	        
	      } catch (FileNotFoundException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	       XSSFWorkbook workbook = null;
	      try {
	         workbook = new XSSFWorkbook(fis);
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      
	       //시트 수 (첫번째에만 존재하므로 0을 준다)
	       //만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
	       XSSFSheet sheet=workbook.getSheetAt(0);
	       //행의 수
	       int rows=sheet.getPhysicalNumberOfRows();
	       for(rowindex=0; rowindex<rows; rowindex++)
	       {
	          //행을읽는다
	          XSSFRow row=sheet.getRow(rowindex);
	          
	          if(row != null){
	             //셀의 수   
	             int cells=row.getPhysicalNumberOfCells();
	             for(columnindex=0;columnindex<=cells;columnindex++){
	                //셀값을 읽는다
	                XSSFCell cell=row.getCell(columnindex);
	                String value="";
	                //셀이 빈값일경우를 위한 널체크
	                if(cell==null){
	                   continue;
	                }
	                else{
	                   //타입별로 내용 읽기
	                   switch (cell.getCellType()){
	                   case XSSFCell.CELL_TYPE_FORMULA:
	                      value=cell.getCellFormula();
	                      break;
	                   case XSSFCell.CELL_TYPE_NUMERIC:
	                      value=cell.getNumericCellValue()+"";
	                      break;
	                   case XSSFCell.CELL_TYPE_STRING:
	                	   value=cell.getStringCellValue()+"";
	                      break;
	                   case XSSFCell.CELL_TYPE_BLANK:
	                      value=cell.getBooleanCellValue()+"";
	                      break;
	                   case XSSFCell.CELL_TYPE_ERROR:
	                      value=cell.getErrorCellValue()+"";
	                      break;
	                   }
	                }
	                
	                data[rowindex1][columnindex] = value;
	             }
	          }

	          String kkk = jfc.getSelectedFiles()[i].toString();
	          if(kkk.contains(DefineString.Parser.LEARNING_BASE)== true)
	          {
		          String value = new String();
		           value = data[rowindex1][2];
		         data[rowindex1][2] = data[rowindex1][3];
		         data[rowindex1][3] = value;
	          }
	        	  rowindex1++;
	       }	       
	     }
	     
		// 테이블의 열 이름이 들어갈 내용을 일차원 배열에 넣는다.
		
		String data2[][] = new String[rowindex1][columnindex];
		
		for(int i = 0; i < rowindex1 ; i++)
		{
			for (int j = 0 ; j < columnindex ; j++)
			{

					String value = new String();
					value = data[i][j];
					data2[i][j] = value;
			}
			
		}
		// JTable생성자를 이용하여 테이블을 만든다.
		table = new JTable(data2, colName);	

		// JScrollPane에 테이블을 붙이고, 프레임에 붙인다.
		add(new JScrollPane(table));

		}
	
	public void middle2(JFrame frame) // 버튼 이벤트
	{
		
		JButton add = new JButton(DefineString.ADD);
		add(add);
		
		JButton remove = new JButton(DefineString.REMOVE);
		add(remove);
		
		add.addActionListener(this);
		remove.addActionListener(this);
		

	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		
		String button = e.toString();
		row = table.getSelectedRow();
		col = table.getSelectedColumn();
		

		//System.out.println(bottomData2.length);
		if (button.contains(DefineString.ADD))
		{
			
			
			for (int i = 0 ; i < table_add.getColumnCount() ; i++)
			{
				if(bottomRow < 0)
					bottomRow =0;

				object = table.getValueAt(row, i);
				bottomData[bottomRow][i] = (String) object;
				
			}
			dtm.addRow(bottomData[bottomRow]);
			bottomRow++;
			Schedule();

			// JTable생성자를 이용하여 테
		}
		

		else
		{
			try {
		            dtm.removeRow(table_add.getSelectedRow());
		        }
		            
		    catch (Exception e2) {
		        JOptionPane.showMessageDialog(this, DefineString.WARNING);

		        
		         
		 }
			bottomRow--;
			
		}

		
		table_add.updateUI();
		timetable.updateUI();
		
	}
	
	
	
	public void bottom(final JFrame frame) {
		
		bottomData = new String[20][8];
		

		// 테이블의 열 이름이 들어갈 내용을 일차원 배열에 넣는다.
		String[] colName = {DefineString.Parser.SORT, DefineString.Parser.LECTURE_NUM, DefineString.Parser.LECTURE_NAME, DefineString.Parser.CREDIT, 
				DefineString.Parser.TIME, DefineString.Parser.GRADE, DefineString.Parser.PROFESSOR, DefineString.Parser.LECTURE_ROOM};
		
		// JTable생성자를 이용하여 테이블을 만든다.
		table_add = new JTable(dtm);	
		// JScrollPane에 테이블을 붙이고, 프레
		JScrollPane scroll = new JScrollPane(table_add);
		scroll.setPreferredSize(new Dimension(450, 100)); // scroll 크기
		// table.setPreferredSize(new Dimension(450, 500));
		add(scroll);
		
		
	
	}
	
    // 열 너비 지정 메소드 (퍼센테이지로 지정)
	public static void setJTableColumnsWidth(JTable table, int tablePreferredWidth,
	        double... percentages) {
	    double total = 0;
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        total += percentages[i];
	    }
	 
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        TableColumn column = table.getColumnModel().getColumn(i);
	        column.setPreferredWidth((int)
	                (tablePreferredWidth * (percentages[i] / total)));
	    }
	}
	
	public void timeTable(final JFrame frame) {
	      String []weekday = {DefineString.Week.TIME, DefineString.Week.MON, DefineString.Week.TUE, DefineString.Week.WEN, DefineString.Week.THU, DefineString.Week.FRI};
	      timetable= new JTable(time, weekday);
	      
	      // 행 세로 길이
	      timetable.setRowHeight(45);
	      // 총 길이 500에 퍼센테이지로 지정
	      setJTableColumnsWidth(timetable, 500, 20, 16, 16, 16, 16, 16);
	      
	      JScrollPane timeweek = new JScrollPane(timetable);
	      Color a = new Color(255, 255, 255);
	      timeweek.setBackground(a);
	      timeweek.setPreferredSize(new Dimension(500, 200));//153 거의 맞음
	      add(timeweek);
	      
	   }
	
	
	// 폰트 설정 method
	public static void setUIFont(FontUIResource f) {
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }
	
	private void Schedule()
	{


			try {
				object = table.getValueAt(row, 7);
				scheObject = table.getValueAt(row, 2);
				String trans = (String)object;
				StringBuffer trans1 = new StringBuffer(trans);
				trans1.reverse();
				String retrans = trans1.toString();
				int day = 0;
				String print = "";
				String backprint = "";
				int i1 = trans.indexOf('[');
				int i2 = trans.indexOf(']');
				int j1 = retrans.indexOf(']');
				int j2 = retrans.indexOf('[');
				print = trans.substring(i1, i2+1);
				backprint = retrans.substring(j1, j2+1);
				
				int day1[] = {1,2,3,4,5,6,7,8,9,10,11};
				
				
				if(trans.contains(DefineString.Week.MON))
				{
					for(int i=0; i<12 ; i++)
					{
						String number = Integer.toString(day1[i]);
						
						if(print.contains(number) )
						{
								time[i][1] = (String) scheObject;
							
						}

					}
					
					for(int i3=0; i3<12 ; i3++)
					{
						String number2 = Integer.toString(day1[i3]);
						
						if(backprint.contains(number2) )
						{
								time[i3][1] = (String) scheObject;
							
						}

				}
			}
				
				if(trans.contains(DefineString.Week.TUE))
				{
					for(int i=0; i<12 ; i++)
					{
						String number = Integer.toString(day1[i]);		
						if(print.contains(number) )
						{
								time[i][2] = (String) scheObject;
							
						}
							
					}
					
					for(int i3=0; i3<12 ; i3++)
					{
						String number2 = Integer.toString(day1[i3]);
						if(backprint.contains(number2) )
						{
								time[i3][2] = (String) scheObject;
							
						}
					}
				}
				
					
				
				if(trans.contains(DefineString.Week.WEN))
				{
					for(int i=0; i<12 ; i++)
					{
						String number = Integer.toString(day1[i]);
						
						if(print.contains(number) )
						{
								time[i][3] = (String) scheObject;
							
						}
							
					}
					
					for(int i3=0; i3<12 ; i3++)
					{
						String number2 = Integer.toString(day1[i3]);
						if(backprint.contains(number2) )
						{
								time[i3][3] = (String) scheObject;
							
						}
					}
				}

				if(trans.contains(DefineString.Week.THU))
				{
					for(int i=0; i<12 ; i++)
					{
						String number = Integer.toString(day1[i]);
						if(print.contains(number) )
						{
								time[i][4] = (String) scheObject;
							
						}
							
					}
					
					for(int i3=0; i3<12 ; i3++)
					{
						String number2 = Integer.toString(day1[i3]);
						if(backprint.contains(number2) )
						{
								time[i3][4] = (String) scheObject;
							
						}
					}
				}
				
				
				if(trans.contains(DefineString.Week.FRI))
				{
					for(int i=0; i<12 ; i++)
					{
						String number = Integer.toString(day1[i]);
						if(print.contains(number) )
						{
								time[i][5] = (String) scheObject;
							
						}
							
					}
					
					for(int i3=0; i3<12 ; i3++)
					{
						String number2 = Integer.toString(day1[i3]);
						if(backprint.contains(number2) )
						{
								time[i3][5] = (String) scheObject;
							
						}
					}
				}
			}

			catch (Exception e) {
				// TODO: handle exception
			}
	}
	
	public static void main(String[] args) {
		final JPanel c = new DeuTimeTable();
		c.setPreferredSize(new Dimension(520,850)); // 윈도우 창 크기
		
		// 폰트 설정
		 setUIFont(new FontUIResource(new Font("나눔고딕", 0, 13)));
		// EDT를 사용해 실행할 GUI 작업을 넣는다.
		// title과 component
		main_layout.launch(DefineString.TITLE, c);
		
		return;
		
	} 

}







