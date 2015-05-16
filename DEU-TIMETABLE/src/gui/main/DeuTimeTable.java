package gui.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JLabel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import data.DefineString;

public class DeuTimeTable extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table, table_add, timetable;

	private JComboBox comboBox;
	private JTextField searchField;
	
	private static final long serialVersionUID = 1L;
	Label lbl1, lbl2, lbl3;
	static int row,col=0;
	static int bottomRow =0;
	Object object, scheObject = "";
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
	  
	   
	   
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeuTimeTable frame = new DeuTimeTable();
					frame.setVisible(true);
			        
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the frame.
	 */
	public DeuTimeTable() {
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 750);
		setTitle(DefineString.TITLE);
		
		setLocationRelativeTo(null); // 프레임을모니터화면중앙에배치
		
		// 메뉴바 생성, 프레임에 추가
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
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
		
		// 메뉴 기능 추가
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
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		// top 패널 
		JPanel top = new JPanel();
		panel.add(top);
		
		// jcomboBox 객체 추가
		comboBox = new JComboBox(new Object[]{DefineString.Parser.SORT, DefineString.Parser.LECTURE_NAME, DefineString.Parser.CREDIT, 
	    		DefineString.Parser.TIME, DefineString.Parser.GRADE, DefineString.Parser.PROFESSOR, DefineString.Parser.LECTURE_ROOM});
		top.add(comboBox);
		
		// jtextField 객체 추가
		searchField = new JTextField();
		top.add(searchField);
		searchField.setColumns(20);
		
		// 검색 버튼 추가
		JButton button = new JButton("검색");
		top.add(button);
		
		// middle 패널
		JPanel middle = new JPanel();
		panel.add(middle);
		middle.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 5));
		
		
		
		FileInputStream fis = null;
		int rowindex=0;
		int columnindex=0;
		int rowindex1=0;
		 data = new String[100][100];
		 JFileChooser jfc = new JFileChooser();
		 jfc.setMultiSelectionEnabled(true);
	     jfc.showOpenDialog(null);
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
		//table = new JTable(data2, colName);	
        
		
	    DefaultTableModel model = new DefaultTableModel(data2, colName);
	    table = new JTable(model);
	    
		JScrollPane scrollPane = new JScrollPane();
		middle.add(scrollPane);
	    scrollPane.setViewportView(table);
		
	    
	    // 추가 삭제 버튼 
		JButton add = new JButton(DefineString.ADD);
		middle.add(add);
		
		JButton remove = new JButton(DefineString.REMOVE);
		middle.add(remove);
		
		add.addActionListener(this);
		remove.addActionListener(this);
		
		
		JLabel label = new JLabel("");
		middle.add(label);
		
		JPanel bottom = new JPanel();
		panel.add(bottom);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		bottom.add(scrollPane_1);
		scrollPane_1.setPreferredSize(new Dimension(450, 100));
		
		bottomData = new String[20][8];
		
		table_add = new JTable(dtm);
		scrollPane_1.setViewportView(table_add);
		
		JLabel label_1 = new JLabel("");
		panel.add(label_1);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane timeweek = new JScrollPane();
		panel_1.add(timeweek);
		
		String []weekday = {DefineString.Week.TIME, DefineString.Week.MON, DefineString.Week.TUE, DefineString.Week.WEN, DefineString.Week.THU, DefineString.Week.FRI};
		timetable = new JTable(time, weekday);
	    // 행 세로 길이
	    timetable.setRowHeight(50);
	    // 총 길이 500에 퍼센테이지로 지정
	    setJTableColumnsWidth(timetable, 500, 20, 16, 16, 16, 16, 16);
	    
        // 중앙정렬
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
              dtcr.setHorizontalAlignment(SwingConstants.CENTER);
              TableColumnModel tcm = timetable.getColumnModel();
              for(int i = 0 ; i< tcm.getColumnCount() ; i++)
              {
               tcm.getColumn(i).setCellRenderer(dtcr);
              }
	    
	    Color a = new Color(255, 255, 255);
		timeweek.setViewportView(timetable);
	    timeweek.setBackground(a);
	    timeweek.setPreferredSize(new Dimension(500, 200));
	    
	    
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
	
	private void Schedule()
	{
        object = table.getValueAt(row, 7);
        scheObject = table.getValueAt(row, 2);
        String trans = (String)object;
        StringBuffer trans1 = new StringBuffer(trans);
        trans1.reverse();
        String retrans = trans1.toString();
        String print = "";
        String backprint = "";
        int i1 = trans.indexOf('[');
        int i2 = trans.indexOf(']');
        int j1 = retrans.indexOf(']');
        int j2 = retrans.indexOf('[');
        print = trans.substring(i1, i2+1);
        backprint = retrans.substring(j1, j2+1);
        StringBuffer trans2 = new StringBuffer(backprint);
        trans2.reverse();
        String retrans2 = trans2.toString();
				
		int day1[] = {1,2,3,4,5,6,7,8,9,10,11};
				
				
		//월요일
	    if(trans.contains(DefineString.Week.MON))
	    {
	    	for(int i=0; i<11 ; i++)
	        {
	            if(i == 0 || i ==1)
	            {
	               if (trans.contains("10") || trans.contains("11"))
	                    continue;
	             }
	            String number = Integer.toString(day1[i]);
	              if(print.contains(number))
	                  time[i][1] = (String) scheObject;
	               }
	            }
	            
	            
	            //화요일
	            else if(trans.contains(DefineString.Week.TUE))
	            {
	               for(int i=0; i<11 ; i++)
	               {
	                  if(i == 0 || i ==1)
	                  {
	                     if (trans.contains("10") || trans.contains("11"))
	                           continue;
	                  }
	                  String number = Integer.toString(day1[i]);      
	                  if(print.contains(number) )
	                     time[i][2] = (String) scheObject;
	               }
	            }
	            
	            //수요일
	            else if(trans.contains(DefineString.Week.WEN))
	            {
	               for(int i=0; i<11 ; i++)
	               {
	                  if(i == 0 || i ==1)
	                  {
	                     if (trans.contains("10") || trans.contains("11"))
	                           continue;
	                  }
	                  String number = Integer.toString(day1[i]);
	                  if(print.contains(number) )
	                     time[i][3] = (String) scheObject;
	               }
	            }
	            
	            //목요일
	            else if(trans.contains(DefineString.Week.THU))
	            {
	               for(int i=0; i<11 ; i++)
	               {
	                  if(i == 0 || i ==1)
	                  {
	                     if (trans.contains("10") || trans.contains("11"))
	                           continue;
	                  }
	                  String number = Integer.toString(day1[i]);
	                  if(print.contains(number))
	                     time[i][4] = (String) scheObject;
	               }
	            }
	      
	            //금요일
	            else
	            {
	               for(int i=0; i<11 ; i++)
	               {
	                  if(i == 0 || i ==1)
	                  {
	                     if (trans.contains("10") || trans.contains("11"))
	                           continue;
	                  }
	                  String number = Integer.toString(day1[i]);
	                  if(print.contains(number) )
	                     time[i][5] = (String) scheObject;
	               }
	            }
	            
	            
	            
	            
	            //월요일
	            if(retrans2.contains(DefineString.Week.MON))
	            {
	               for(int i=0; i<11 ; i++)
	               {
	                  if(i == 0 || i ==1)
	                  {
	                     if (trans.contains("10") || trans.contains("11"))
	                           continue;
	                  }
	                  String number = Integer.toString(day1[i]);
	                  if(retrans2.contains(number))
	                     time[i][1] = (String) scheObject;
	               }
	            }

	            //화요일
	            else if(retrans2.contains(DefineString.Week.TUE))
	            {
	               for(int i=0; i<11 ; i++)
	               {
	                  if(i == 0 || i ==1)
	                  {
	                     if (trans.contains("10") || trans.contains("11"))
	                           continue;
	                  }
	                  String number = Integer.toString(day1[i]);      
	                  if(retrans2.contains(number) )
	                     time[i][2] = (String) scheObject;
	               }
	            }
	            
	            //수요일
	            else if(retrans2.contains(DefineString.Week.WEN))
	            {
	               for(int i=0; i<11 ; i++)
	               {
	                  if(i == 0 || i ==1)
	                  {
	                     if (trans.contains("10") || trans.contains("11"))
	                           continue;
	                  }
	                  String number = Integer.toString(day1[i]);
	                  if(retrans2.contains(number) )
	                     time[i][3] = (String) scheObject;
	               }
	            }
	            
	            //목요일
	            else if(retrans2.contains(DefineString.Week.THU))
	            {
	               for(int i=0; i<11 ; i++)
	               {
	                  if(i == 0 || i ==1)
	                  {
	                     if (trans.contains("10") || trans.contains("11"))
	                           continue;
	                  }
	                  String number = Integer.toString(day1[i]);
	                  if(retrans2.contains(number))
	                     time[i][4] = (String) scheObject;
	               }
	            }
	            
	            //금요일
	            else
	            {
	               for(int i=0; i<11 ; i++)
	               {
	                  if(i == 0 || i ==1)
	                  {
	                     if (trans.contains("10") || trans.contains("11"))
	                           continue;
	                  }
	                  String number = Integer.toString(day1[i]);
	                  if(retrans2.contains(number) )
	                     time[i][5] = (String) scheObject;
	               }
	            }
	            
	            

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String button = e.toString();
		row = table.getSelectedRow();
		col = table.getSelectedColumn();
		
		
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

	
	

}
