package gui.main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class main_layout {

	protected static void displayGUI(final String title, final JComponent
			component) {
		
		// 창의 제목과 함께 창 생성
        final JFrame frame = new JFrame(title);
        Container con = frame.getContentPane();     
        if (component instanceof AddMenu) {
            AddMenu ms = (AddMenu) component;
            ms.top(frame);
            ms.middle(frame);
            ms.bottom(frame);
        }
        
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
        
        
        // 애플리케이션 종료 버튼 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // BorderLayout을 사용해서 컴포넌트를 중앙으로 배치
        frame.getContentPane().add(component, BorderLayout.WEST);
        frame.setMinimumSize(component.getMinimumSize());
        
        // 레이아웃에 기반한 창 크기(적절한 크기)
        frame.pack();

        // 모니터 중앙 값
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        int scrnWidth = frame.getSize().width;
        int scrnHeight = frame.getSize().height;
        int x = (scrnSize.width - scrnWidth) / 2;
        int y = (scrnSize.height - scrnHeight) / 2;
    
        // 창 이동
        frame.setLocation(x, y);
        
        // display 
        frame.setVisible(true);
	}

	public static void launch(final String title, final JComponent component) {
		
		// 이벤트 디스패칭 스레드(EDT)를 사용해
		// 실행할 GUUI 작업을 대기열에 넣는다.
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                displayGUI(title, component);
            } 
        }); // invokeLater()
	} // launch()
} // main_layout
