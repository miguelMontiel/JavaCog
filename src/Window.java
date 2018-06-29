import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JFrame jframe;
	private JPanel jpanel;
	private GroupLayout grouplayout;
	
	private JLabel jlabel1;
	private JLabel jlabel2;
	private JTextField jtextfield;
	private JButton jbutton;
	
	private Cursor handCursor;
	private Cursor textCursor;
	
	Window()
	{
		jframe = new JFrame();
		jpanel = new JPanel();
		grouplayout = new GroupLayout(jpanel);
		
		jlabel1 = new JLabel("Pregunta a enviar a Cog: ");
		jlabel2 = new JLabel("Pregunta:  enviada!");
		jtextfield = new JTextField(30);
		jbutton = new JButton("Enviar"); 
		
		handCursor = new Cursor(Cursor.HAND_CURSOR);
		textCursor = new Cursor(Cursor.TEXT_CURSOR);
		
		jbutton.setCursor(handCursor);
		jlabel1.setCursor(textCursor);
		jtextfield.setCursor(textCursor);
		
		add(jpanel);
		
		grouplayout.setHorizontalGroup(grouplayout.createSequentialGroup()
				.addComponent(jlabel1)
				.addComponent(jtextfield)
				.addComponent(jbutton)
				.addComponent(jlabel2));
		
		jlabel2.setVisible(false);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == jbutton)
		{
			System.out.println(jtextfield.getText());
			jtextfield.setText("");
			jlabel2.setVisible(true);
		}
	}
}