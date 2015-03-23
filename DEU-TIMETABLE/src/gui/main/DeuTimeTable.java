package gui.main;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
		jc.addItem("수강대상대학(원)");
		jc.addItem("수강대상학과");
		jc.addItem("학점");
		jc.addItem("교과구분");
		jc.addItem("학년");
		
		//jtextfield 객체 추가
		JTextField jt = new JTextField(10);
		add(jt);
		
		//jbutton 객체 추가
		JButton search = new JButton("검색");
		add(search);
	} 
	
	
	public void middle(final JFrame frame) {
		// 테이블의 각 셀에 들어갈 내용을 이차원 배열에 넣는다.
		Object [][]data = {
				{"최연정", "111-2222", "assdf@naver.com", "1", "2"},
				{"최연정", "111-2222", "assdf@naver.com", "1", "2"},
				{"최연정", "111-2222", "assdf@naver.com", "1", "2"},
		};
		
		// 테이블의 열 이름이 들어갈 내용을 일차원 배열에 넣는다.
		String[] colName = {"수강대상대학(원)", "수강대상학과", "학점", "교과구분", "학년"};
		
		// JTable생성자를 이용하여 테이블을 만든다.
		JTable table = new JTable(data, colName);
		
		// JScrollPane에 테이블을 붙이고, 프레임에 붙인다.
		add(new JScrollPane(table));
		
		JButton add = new JButton("추가");
		add(add);
		
		JButton remove = new JButton("제거");
		add(remove);
	}
	

	public static void main(String[] args) {
		final JPanel c = new DeuTimeTable();
		c.setPreferredSize(new Dimension(433, 312));
		
		// EDT를 사용해 실행할 GUI 작업을 넣는다.
		// title과 component
		main_layout.launch("동의대 시간표 프로그램", c);
	} 

} 
