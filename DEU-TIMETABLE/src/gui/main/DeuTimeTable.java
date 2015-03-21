package gui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

//메인 GUI

public class DeuTimeTable extends JComponent {
	
	/*	
		@param title 창의 제목, 장과 레시피 표시
		@param canvas 그리기 영역
		
	*/
	
	protected static void displayGUI(final String title, final JComponent
			component) {
		
		// 제목이 있는 창 생성
		final JFrame frame = new JFrame(title);
		
		// 종료 버튼 설정
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// BorderLayout을 이용, component를 중앙에 배치
		frame.getContentPane().add(component, BorderLayout.CENTER);
		
		// 레이아웃에 기반한 창 크기
		frame.pack();
		
		// 화면에 표시
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		final DeuTimeTable c = new DeuTimeTable();
		c.setPreferredSize(new Dimension(290, 277));
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				displayGUI("DeuTimeTable", c);
				
			}
		});

	}

}
