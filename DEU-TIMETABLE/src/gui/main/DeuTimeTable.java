package gui.main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JComponent;
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
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JLabel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import data.DefineString;

public class DeuTimeTable extends JFrame implements ActionListener {
	int rowindex1=0;
	private JPanel contentPane;
	private JTable table, table_add, timetable;
	private String data[][];
	private String data2[][];
	private DefaultTableModel model;
	private JComboBox comboBox;
	private JTextField searchField;
	JTextField title_tf, num_tf, sort_tf, credit_tf, pro_tf, time_tf,  grade_tf, room_tf;
	private static final long serialVersionUID = 1L;
	Label lbl1, lbl2, lbl3;
	static int row,col=0;
	static int bottomRow =0;
	Object object, scheObject = "";
	private String bottomData[][];
	private String[] colName = {DefineString.Parser.SORT, DefineString.Parser.LECTURE_NUM, DefineString.Parser.LECTURE_NAME, DefineString.Parser.CREDIT, 
			DefineString.Parser.TIME, DefineString.Parser.GRADE, DefineString.Parser.PROFESSOR, DefineString.Parser.LECTURE_ROOM};
	DefaultTableModel dtm = new DefaultTableModel(colName, bottomRow);
	
	private String search_data[][];
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
	   
		// 색상지정
	    Color blue = new Color(0x0B67CD);
	   
	   
	   
	/**
	 * Launch the application.
	 */
	
	
	
	/**
	 * Create the frame.
	 */
	public DeuTimeTable() {
		setResizable(false);
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
		JMenuItem save = new JMenuItem(DefineString.Menu.SAVE_TIME_TABLE);
		JMenuItem save_img = new JMenuItem(DefineString.Menu.SAVE_TIME_IMG);
		JMenuItem load = new JMenuItem(DefineString.Menu.LOAD_TIME_TABLE);
		JMenuItem load_lecture = new JMenuItem(DefineString.Menu.LOAD_HAND_BOOK);
	    JMenuItem curriculum = new JMenuItem(DefineString.Menu.LOAD_CURRICULUM);
		JMenuItem about = new JMenuItem(DefineString.AboutThis.TITLE);
		JMenuItem eval = new JMenuItem(DefineString.Menu.EVALUATION);

		// 서브 메뉴 추가
		menu.add(save);
		menu.add(load);
		menu.add(save_img);
		menu.addSeparator();	// 분리선 추가
		menu.add(load_lecture);
		menu.add(curriculum);
		menu.add(eval);
		help.add(about);
				
		// 메뉴바에 메뉴 추가
		menuBar.add(menu);
		menuBar.add(help);
		
		// 메뉴 기능 추가
	      // 메뉴 기능 추가
        //시간표 저장
        save.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent arg0) {
           // TODO Auto-generated method stub
           
           XSSFWorkbook wb = new XSSFWorkbook(); // Excel 2007 이상, 대용량 Excel 처리에 적합하며 '쓰기전용'임
           
            /* (2) 새로운 Sheet 생성 */
            Sheet sheet = wb.createSheet("First sheet");
            if( sheet == null ){
              /*test*/System.out.println("Sheet1 is Null!");
              return;
            }
          
            /* (3) 새로운 Sheet에 Row를 만든 후 해당 Row의 각 Cell에 값을 입력
                즉, Sheet -> Row -> Cell 순서로 접근하여 값을 입력한다. */
             //Row 번호는 0,1,2... 순서, 따라서 createRow(1)는 2번째 Row 생성
            for (int i = 0 ; i < timetable.getRowCount() ; i++)
              {
               Row row1 = sheet.createRow(i);
                 for(int j = 0 ; j < timetable.getColumnCount(); j++)
                 {
               Cell cell = row1.createCell(j); //Cell 번호도 0,1,2... 순서임
               cell.setCellValue((String)timetable.getValueAt(i, j)); //각 Cell에 0, 1, 2, 3, 4 입력
            }
              }
            try {
                FileOutputStream fileOut = new FileOutputStream("yoyo.xlsx");
                wb.write(fileOut);
                fileOut.close();
                wb.close(); //HSSFWorkbook. XSSFWorkbook 사용시 사용
                //wb.dispose(); //SXSSFWorkbook 사용시 사용
             } catch (FileNotFoundException e) {
                e.printStackTrace();
             } catch (IOException e) {
                e.printStackTrace();
             }




        }
     });
        
        //시간표 불러오기
        load.addActionListener(new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent arg0) {
           /*
           FileInputStream fis = null;
           try {
              fis = new FileInputStream("yoyo.xlsx");
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
           int rowindex=0;
           int columnindex=0;
           //시트 수 (첫번째에만 존재하므로 0을 준다)
           //만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
           XSSFSheet sheet=workbook.getSheetAt(0);
           //행의 수
           int rows=sheet.getPhysicalNumberOfRows();
           for(rowindex=1;rowindex<rows;rowindex++){
               //행을읽는다
               XSSFRow row=sheet.getRow(rowindex);
               if(row !=null){
                   //셀의 수
                   int cells=row.getPhysicalNumberOfCells();
                   for(columnindex=0;columnindex<=cells;columnindex++){
                       //셀값을 읽는다
                       XSSFCell cell=row.getCell(columnindex);
                       String value="";
                       //셀이 빈값일경우를 위한 널체크
                       if(cell==null){
                           continue;
                       }else{
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
                       String data2[][] = new String[rowindex][columnindex];
                       
                       for(int i = 0; i < rowindex1 ; i++)
                       {
                          for (int j = 0 ; j < columnindex ; j++)
                          {

                                String value = new String();
                                value = data[i][j];
                                data2[i][j] = value;
                          }
                          
                       }
                   }
               }
           }*/
        }
     });
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
		        	  //교육과정 열기
		                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + "curriculum.pdf");

		                } catch (Exception e)                   
		                  { 
		                      System.out.println(DefineString.NO_FILE);  
		                  } 
		            } 
		   });
		      

	     
	      eval.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.main.Eval.main();
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
		final JButton button = new JButton("검색");
		top.add(button);
		
		// middle 패널
		JPanel middle = new JPanel();
		panel.add(middle);
		//middle.setLayout(new FlowLayout(FlowLayout.CENTER, 3, 5));
		middle.setLayout(new BoxLayout(middle, BoxLayout.X_AXIS));
		
		JPanel panel_6 = new JPanel();
		middle.add(panel_6);
		panel_6.setLayout(new GridLayout(1, 2));
		
		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		
		
		//검색버튼 
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				search();
			}
			
			
		});
		
		
		FileInputStream fis = null;
		int rowindex=0;
		int columnindex=0;
		
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
		
		data2 = new String[rowindex1][columnindex];
		
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
        
		
		model = new DefaultTableModel(data2, colName);
	    table = new JTable(model){
			public boolean isCellEditable(int row, int column) {
		        if (column >= 0) {
		            return false;
		        } else {
		            return true;
		        }
		    }
			
		};
		
		// table 색상 지정
		table.getTableHeader().setBackground(blue); // 배경색
		table.getTableHeader().setForeground(Color.white); // 글자색
		
		// 열 숨기기
		table.getColumn(DefineString.Parser.CREDIT).setPreferredWidth(0);
		table.getColumn(DefineString.Parser.CREDIT).setMinWidth(0);
		table.getColumn(DefineString.Parser.CREDIT).setWidth(0);
		table.getColumn(DefineString.Parser.CREDIT).setMaxWidth(0);
		
		table.getColumn(DefineString.Parser.TIME).setPreferredWidth(0);
		table.getColumn(DefineString.Parser.TIME).setMinWidth(0);
		table.getColumn(DefineString.Parser.TIME).setWidth(0);
		table.getColumn(DefineString.Parser.TIME).setMaxWidth(0);
		
		table.getColumn(DefineString.Parser.GRADE).setPreferredWidth(0);
		table.getColumn(DefineString.Parser.GRADE).setMinWidth(0);
		table.getColumn(DefineString.Parser.GRADE).setWidth(0);
		table.getColumn(DefineString.Parser.GRADE).setMaxWidth(0);
		
		table.getColumn(DefineString.Parser.PROFESSOR).setPreferredWidth(0);
		table.getColumn(DefineString.Parser.PROFESSOR).setMinWidth(0);
		table.getColumn(DefineString.Parser.PROFESSOR).setWidth(0);
		table.getColumn(DefineString.Parser.PROFESSOR).setMaxWidth(0);
		
		table.getColumn(DefineString.Parser.LECTURE_ROOM).setPreferredWidth(0);
		table.getColumn(DefineString.Parser.LECTURE_ROOM).setMinWidth(0);
		table.getColumn(DefineString.Parser.LECTURE_ROOM).setWidth(0);
		table.getColumn(DefineString.Parser.LECTURE_ROOM).setMaxWidth(0);
		
		table.getColumn(DefineString.Parser.LECTURE_NUM).setPreferredWidth(0);
		table.getColumn(DefineString.Parser.LECTURE_NUM).setMinWidth(0);
		table.getColumn(DefineString.Parser.LECTURE_NUM).setWidth(0);
		table.getColumn(DefineString.Parser.LECTURE_NUM).setMaxWidth(0);
		
		JScrollPane scrollPane = new JScrollPane();
		// 배경 색
		scrollPane.getViewport().setBackground(Color.WHITE);
		
		//middle.add(scrollPane);
	    scrollPane.setViewportView(table);
	    panel_7.add(scrollPane);
		
	    	    
	    JPanel panel_4 = new JPanel();
	    panel_6.add(panel_4);
	    //panel_4.setLayout(new FlowLayout( FlowLayout.LEFT, 5, 5));
	    panel_4.setLayout(new GridLayout(8,2,20,10));
	    
	    // 과목 정보
	    
	    JPanel empno_p, name_p, pos_p, dept_p, pro_p, time_p, grade_p, room_p;
        empno_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        name_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pos_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dept_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pro_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        time_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        grade_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        room_p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        empno_p.add(new JLabel("교과목명: "));
        empno_p.add(title_tf = new JTextField(15));
        
        UIManager.put("TextField.disabledBackground", Color.WHITE);

        title_tf.setDisabledTextColor(Color.WHITE);
	    title_tf.setEditable(false);
        
        name_p.add(new JLabel("강좌번호:"));
        name_p.add(num_tf = new JTextField(7));
        
        pos_p.add(new JLabel("학점:"));
        pos_p.add(credit_tf = new JTextField(2));
        
        dept_p.add(new JLabel("이수구분:"));
        dept_p.add(sort_tf = new JTextField(3));
        
        pro_p.add(new JLabel("교수명:"));
        pro_p.add(pro_tf = new JTextField(15));
        
        time_p.add(new JLabel("시간:"));
        time_p.add(time_tf = new JTextField(4));
        
        grade_p.add(new JLabel("권장학년:"));
        grade_p.add(grade_tf = new JTextField(10));
        
        room_p.add(new JLabel("강의실 및 강의시간:"));
        room_p.add(room_tf = new JTextField(15));
        
       
        panel_4.add(empno_p);
        panel_4.add(name_p);
        panel_4.add(pos_p);
        panel_4.add(dept_p);
        panel_4.add(pro_p);
        panel_4.add(time_p);
        panel_4.add(grade_p);
        panel_4.add(room_p);

		
		JLabel label = new JLabel("");
		middle.add(label);
		
		JPanel bottom = new JPanel();
		panel.add(bottom);
		
		JPanel panel_5 = new JPanel();
		bottom.add(panel_5);
		FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		//panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
	    // 추가 삭제 버튼 
		JButton add = new JButton(DefineString.ADD);
		panel_5.add(add);
		
		JButton remove = new JButton(DefineString.REMOVE);
		panel_5.add(remove);
		
		add.addActionListener(this);
		remove.addActionListener(this);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		bottom.add(scrollPane_1);
		scrollPane_1.setPreferredSize(new Dimension(450, 100));
		
		bottomData = new String[20][8];
		
		table_add = new JTable(dtm){
			public boolean isCellEditable(int row, int column) {
		        if (column >= 0) {
		            return false;
		        } else {
		            return true;
		        }
		    }
			
		};
		scrollPane_1.setViewportView(table_add);
		
		table_add.getTableHeader().setBackground(blue); // 배경색
		table_add.getTableHeader().setForeground(Color.white); // 글자색
		
		// 배경 색
		scrollPane_1.getViewport().setBackground(Color.WHITE);
		
		JLabel label_1 = new JLabel("");
		panel.add(label_1);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JScrollPane timeweek = new JScrollPane();
		panel_1.add(timeweek);
		
		String []weekday = {DefineString.Week.TIME, DefineString.Week.MON, DefineString.Week.TUE, DefineString.Week.WEN, DefineString.Week.THU, DefineString.Week.FRI};
		timetable = new JTable(time, weekday){
			public boolean isCellEditable(int row, int column) {
		        if (column >= 0) {
		            return false;
		        } else {
		            return true;
		        }
		    }
			
		};
		
		
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
	    
	    
		timeweek.setViewportView(timetable);
	    timeweek.setPreferredSize(new Dimension(timetable.WIDTH, timetable.HEIGHT));
	    
	    // 색상 지정
		timetable.getTableHeader().setBackground(blue); // 배경색
		timetable.getTableHeader().setForeground(Color.white); // 글자색
	    
		table.getTableHeader().setReorderingAllowed(false); // 셀 고정
		table_add.getTableHeader().setReorderingAllowed(false); // 셀 고정
		timetable.getTableHeader().setReorderingAllowed(false); // 셀 고정
		
		
		// 정보 출력 이벤트
		table.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				
				if(table.getSelectedRow() != -1 && table.getSelectedColumn() != -1 ) {
					
				
					String selSort = table.getValueAt(table.getSelectedRow(), 0).toString();
					sort_tf.setText(selSort);
					String selNum = table.getValueAt(table.getSelectedRow(), 1).toString();
					num_tf.setText(selNum);
					String selTitle = table.getValueAt(table.getSelectedRow(), 2).toString();
					title_tf.setText(selTitle);
					String selCredit = table.getValueAt(table.getSelectedRow(), 3).toString();
					credit_tf.setText(selCredit);
					String selTime = table.getValueAt(table.getSelectedRow(), 4).toString();
					time_tf.setText(selTime);
					String selGrade = table.getValueAt(table.getSelectedRow(), 5).toString();
					grade_tf.setText(selGrade);
					String selPro = table.getValueAt(table.getSelectedRow(), 6).toString();
					pro_tf.setText(selPro);
					String selRoom = table.getValueAt(table.getSelectedRow(), 7).toString();
					room_tf.setText(selRoom);
				
				}
				
			}
		
		});
		
		// 이미지 저장
	      save_img.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				saveToImage(timetable, timetable.getTableHeader());
			}
	    	  
	      });
	}
	
	// 이미지 저장 함수
	 private static void saveToImage(JTable table, JTableHeader header)
	    {
	        int w = Math.max(table.getWidth(), header.getWidth());
	        int h = table.getHeight() + header.getHeight();
	        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2 = bi.createGraphics();
	        header.paint(g2);
	        g2.translate(0, header.getHeight());
	        table.paint(g2);
	        g2.dispose();
	        try
	        {
	            ImageIO.write(bi, "png", new File("tableImage.png"));
	        }
	        catch(IOException ioe)
	        {
	            System.out.println("write: " + ioe.getMessage());
	        }
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

		            	for (int i = 1 ; i < timetable.getRowCount() ; i++)
		            	{
		            		for(int j = 1 ; j < timetable.getColumnCount(); j++)
		            		{
		            			if(time[i][j] == table.getValueAt(row, 2))
			            			time[i][j] = "";
		            		}

		            	}
		        }
		            
		    catch (Exception e2) {
		        JOptionPane.showMessageDialog(this, DefineString.WARNING);

		 }
			bottomRow--;
			
		}

		
		table_add.updateUI();
		timetable.updateUI();
		
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
	            
	            //중앙정렬
	            DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
				dtcr.setHorizontalAlignment(SwingConstants.CENTER);
				TableColumnModel tcm = timetable.getColumnModel();
				for(int i = 0 ; i< tcm.getColumnCount() ; i++)
				{
				 tcm.getColumn(i).setCellRenderer(dtcr);
				}
	            
	            

	}
	private void search()
	{
		String value = new String();
		String content = searchField.getText();
		int content_length = content.length();
		
		if(content_length == 0)
		{
			model.setRowCount(0);
			for(int i=0 ; i<rowindex1; i++)
			{
					model.addRow(data[i]);
			}

		}

		
		//System.out.println(searchField.getText());
		else if(comboBox.getSelectedItem() == DefineString.Parser.SORT )//구분
		{
			model.setRowCount(0);
			for(int i=0 ; i<rowindex1; i++)
			{
					
				if(data[i][0].contains(content))
				{
					model.addRow(data[i]);
				}
				
			}
			
		}
		
		else if(comboBox.getSelectedItem() == DefineString.Parser.LECTURE_NAME )//교과목명
		{
			model.setRowCount(0);
			for(int i=0 ; i<rowindex1; i++)
			{
					
				if(data[i][2].contains(content))
				{
					model.addRow(data[i]);
				}
				
			}
		}
		
		else if(comboBox.getSelectedItem() == DefineString.Parser.CREDIT)//학점
		{
			model.setRowCount(0);
			for(int i=0 ; i<rowindex1; i++)
			{
					
				if(data[i][3].contains(content))
				{
					model.addRow(data[i]);
				}
				
			}
		}
		
		else if(comboBox.getSelectedItem() == DefineString.Parser.GRADE)//학년
		{
			model.setRowCount(0);
			for(int i=0 ; i<rowindex1; i++)
			{
					
				if(data[i][4].contains(content))
				{
					model.addRow(data[i]);
				}
				
			}
		}
		
		else if(comboBox.getSelectedItem() == DefineString.Parser.TIME)//시간
		{
			model.setRowCount(0);
			for(int i=0 ; i<rowindex1; i++)
			{
					
				if(data[i][5].contains(content))
				{
					model.addRow(data[i]);
				}
				
			}
		}
		
		else if(comboBox.getSelectedItem() == DefineString.Parser.PROFESSOR)//담당교수
		{
			model.setRowCount(0);
			for(int i=0 ; i<rowindex1; i++)
			{
					
				if(data[i][6].contains(content))
				{
					model.addRow(data[i]);
				}
				
			}
		}
		
		else//강의실 및 강의 시간
		{
			model.setRowCount(0);
			for(int i=0 ; i<rowindex1; i++)
			{
					
				if(data[i][7].contains(content))
				{
					model.addRow(data[i]);
				}
				
			}
		}
		
		
	}
	
	
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


	
	

}
