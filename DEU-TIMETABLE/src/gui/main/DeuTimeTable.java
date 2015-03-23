package gui.main;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import gui.main.AddMenu;
import gui.main.main_layout;

public class DeuTimeTable extends JPanel implements AddMenu{
	
	@Override
	public void menu(final JFrame frame) {
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
		
		// test 
		
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
		
	} 


	public static void main(String[] args) {
		final JPanel c = new DeuTimeTable();
		c.setPreferredSize(new Dimension(433, 312));
		
		// EDT를 사용해 실행할 GUI 작업을 넣는다.
		// title과 component
		main_layout.launch("동의대 시간표 프로그램", c);
	} 

} 
