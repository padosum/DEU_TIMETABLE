package gui.main;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;    
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.CORBA.DATA_CONVERSION;

import gui.main.AddMenu;
import gui.main.main_layout;

public class DeuTimeTable extends JPanel implements AddMenu{

   public static void main(String[] args) {
      final JPanel c = new DeuTimeTable();
      c.setPreferredSize(new Dimension(500, 600)); // 윈도우 창 크기
      
      // 폰트 설정
       setUIFont(new FontUIResource(new Font("나눔고딕", 0, 13)));
      
      // EDT를 사용해 실행할 GUI 작업을 넣는다.
      // title과 component
      main_layout.launch("동의대 시간표 프로그램", c);
      
   } 
   
   
   public void top(final JFrame frame) {
      // 메뉴바 객체 생성
      JMenuBar menuBar = new JMenuBar();
      
      // 메뉴 추가
      JMenu menu = new JMenu("파일(F)");
      JMenu help = new JMenu("도움말(H)");
      
      
      // 서브 메뉴 생성
      JMenuItem show = new JMenuItem("시간표 보기");
      JMenuItem save = new JMenuItem("시간표 저장");
      JMenuItem load = new JMenuItem("시간표 불러오기");
      JMenuItem load_lecture = new JMenuItem("강의편람 불러오기");
      
      JMenuItem about = new JMenuItem("이 프로그램에 대해서...");

      // 서브 메뉴 추가
      menu.add(show);
      menu.add(save);
      menu.add(load);
      menu.addSeparator();   // 분리선 추가
      menu.add(load_lecture);
      help.add(about);
      
      // 메뉴바에 메뉴 추가
      menuBar.add(menu);
      menuBar.add(help);
      
      // 프레임에 메뉴바 추가
      frame.setJMenuBar(menuBar);
      
      //jcombobox 객체 추가
      JComboBox jc = new JComboBox();
      add(jc);
      jc.addItem("구분");
      jc.addItem("강좌번호");
      jc.addItem("교과목명");
      jc.addItem("학점");
      jc.addItem("시간");
      jc.addItem("수강대상(학년)");
      jc.addItem("담당교수");
      jc.addItem("강의실");
      
      //jtextfield 객체 추가
      JTextField jt = new JTextField(10);
      add(jt);
      
      //jbutton 객체 추가
      JButton search = new JButton("검색");
      add(search);
   } 
   
   public void middle(final JFrame frame) {
      FileInputStream fis = null;
      int rowindex=0;
      int columnindex=0;
      int rowindex1=0;
      int rows1=0;
      
       JFileChooser jfc = new JFileChooser();
       jfc.setMultiSelectionEnabled(true);
        jfc.showOpenDialog(null);
        String data[][] = new String[100][100];
        File selectedFiles[] = jfc.getSelectedFiles();
        for(int i=0; i<4 ; i++)
        {
           
         try {
            fis = new FileInputStream(jfc.getSelectedFiles()[i].toString());
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
          rows1 = rows1 + rows;
          for(rowindex=0; rowindex<rows; rowindex++){
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
                      
                      // 테이블의 각 셀에 들어갈 내용을 이차원 배열에 넣는다
                      data[rowindex1][columnindex] = value;
                      
                      
                   }
                   
                }
                
             }
             rowindex1++;
          }
          
          
        }

        
      
      // 테이블의 열 이름이 들어갈 내용을 일차원 배열에 넣는다.
      String[] colName = {"구분", "강좌번호", "교과목명", "학점", "시간", "수강대상(학년)", "담당교수", "강의시간 및 강의실"};
      
      // JTable생성자를 이용하여 테이블을 만든다.
      JTable table = new JTable(data, colName);
      
      // JScrollPane에 테이블을 붙이고, 프레임에 붙인다.
      add(new JScrollPane(table));
      
      JButton add = new JButton("추가");
      add(add);
      
      JButton remove = new JButton("제거");
      add(remove);
      }
      
   
   public void bottom(final JFrame frame) {
      
      Object [][]data = {   
      };
      
      // 테이블의 열 이름이 들어갈 내용을 일차원 배열에 넣는다.
      String[] colName = {"구분", "강좌번호", "교과목명", "학점", "시간", "수강대상(학년)", "담당교수", "강의시간 및 강의실"};
      
      // JTable생성자를 이용하여 테이블을 만든다.
      JTable table_add = new JTable(data, colName);
      
      // JScrollPane에 테이블을 붙이고, 프레임에 붙인다.
      JScrollPane scroll = new JScrollPane(table_add);
      scroll.setPreferredSize(new Dimension(450, 100)); // scroll 크기
      // table.setPreferredSize(new Dimension(450, 500));
      add(scroll);
   
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

}
   



