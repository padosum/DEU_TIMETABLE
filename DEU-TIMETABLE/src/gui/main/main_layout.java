package gui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class main_layout {

	protected static void displayGUI(final String title, final JComponent
			component) {
		
		// 창의 제목과 함께 창 생성
        final JFrame frame = new JFrame(title);
        if (component instanceof AddMenu) {
            AddMenu ms = (AddMenu) component;
            ms.top(frame);
            ms.middle(frame);
            ms.middle2(frame);
            ms.bottom(frame);
            ms.timeTable(frame);
        }
        
        
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
