package gui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

//���� GUI

public class DeuTimeTable extends JComponent {
	
	/*	
		@param title â�� ����, ��� ������ ǥ��
		@param canvas �׸��� ����
		
	*/
	
	protected static void displayGUI(final String title, final JComponent
			component) {
		
		// ������ �ִ� â ����
		final JFrame frame = new JFrame(title);
		
		// ���� ��ư ����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// BorderLayout�� �̿�, component�� �߾ӿ� ��ġ
		frame.getContentPane().add(component, BorderLayout.CENTER);
		
		// ���̾ƿ��� ����� â ũ��
		frame.pack();
		
		// ȭ�鿡 ǥ��
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
