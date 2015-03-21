package gui.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class main_layout {

	protected static void displayGUI(final String title, final JComponent
			component) {
		
		// â�� ����� �Բ� â ����
        final JFrame frame = new JFrame(title);
        if (component instanceof AddMenu) {
            AddMenu ms = (AddMenu) component;
            ms.menu(frame);
        }
        
        // ���ø����̼� ���� ��ư ����
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // BorderLayout�� ����ؼ� ������Ʈ�� �߾����� ��ġ
        frame.getContentPane().add(component, BorderLayout.CENTER);
        frame.setMinimumSize(component.getMinimumSize());
        
        // ���̾ƿ��� ����� â ũ��(������ ũ��)
        frame.pack();

        // ����� �߾� ��
        Dimension scrnSize = Toolkit.getDefaultToolkit().getScreenSize();
        int scrnWidth = frame.getSize().width;
        int scrnHeight = frame.getSize().height;
        int x = (scrnSize.width - scrnWidth) / 2;
        int y = (scrnSize.height - scrnHeight) / 2;
    
        // â �̵�
        frame.setLocation(x, y);
        
        // display 
        frame.setVisible(true);
	}

	public static void launch(final String title, final JComponent component) {
		
		// �̺�Ʈ ����Ī ������(EDT)�� �����
		// ������ GUUI �۾��� ��⿭�� �ִ´�.
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                displayGUI(title, component);
            } 
        }); // invokeLater()
	} // launch()
} // main_layout
