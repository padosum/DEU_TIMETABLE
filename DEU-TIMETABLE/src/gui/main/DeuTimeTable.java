package gui.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

import gui.main.AddMenu;
import gui.main.main_layout;

public class DeuTimeTable extends JPanel implements AddMenu{
	
	@Override
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
		menu.addSeparator();	// 분리선 추가
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
		
		JTabbedPane tabPane = new JTabbedPane(JTabbedPane.LEFT);
		
	} 
	
	
	public void middle(final JFrame frame) {
		// 테이블의 각 셀에 들어갈 내용을 이차원 배열에 넣는다.
		Object [][]data = {
				{"전공핵심", "123456-00", "데이터베이스", "3", "/3", "3", "최치즈", "자대319"},
				{"교양", "123456-01", "누가내치즈를..", "3", "/2", "3", "헴과허", "자대419"}
		};
		
		// 테이블의 열 이름이 들어갈 내용을 일차원 배열에 넣는다.
		String[] colName = {"구분", "강좌번호", "교과목명", "학점", "시간", "수강대상(학년)", "담당교수", "강의시간 및 강의실"};
		
		// JTable생성자를 이용하여 테이블을 만든다.
		JTable table = new JTable(data, colName);
		
		// JScrollPane에 테이블을 붙이고, 프레임에 붙인다.
		JScrollPane scroll = new JScrollPane(table);
		add(scroll);
		scroll.getViewport().setBackground(Color.WHITE);	// 배경흰색
		
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
		JScrollPane scroll_bottom = new JScrollPane(table_add);
		scroll_bottom.getViewport().setBackground(Color.WHITE);	// 배경흰색
		
		scroll_bottom.setPreferredSize(new Dimension(450, 100)); // scroll 크기
		// table.setPreferredSize(new Dimension(450, 500));
		add(scroll_bottom);
	
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
	

	public static void main(String[] args) {
		final JPanel c = new DeuTimeTable();
		c.setPreferredSize(new Dimension(500, 600)); // 윈도우 창 크기
		
		
		// 폰트 설정
		 setUIFont(new FontUIResource(new Font("나눔고딕", 0, 13)));
		
		// EDT를 사용해 실행할 GUI 작업을 넣는다.
		// title과 component
		main_layout.launch("동의대 시간표 프로그램", c);
	} 

} 
