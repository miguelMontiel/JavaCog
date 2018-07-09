import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	
	Post post;
	
	Window()
	{
		post = new Post();
		jframe = new JFrame();
		jpanel = new JPanel();
		grouplayout = new GroupLayout(jpanel);
		
		jlabel1 = new JLabel("Pregunta a enviar a Cog: ");
		jlabel2 = new JLabel("Pregunta: ");
		jtextfield = new JTextField(30);
		jbutton = new JButton("Enviar"); 
		
		handCursor = new Cursor(Cursor.HAND_CURSOR);
		textCursor = new Cursor(Cursor.TEXT_CURSOR);
		
		jbutton.setCursor(handCursor);
		jlabel1.setCursor(textCursor);
		jtextfield.setCursor(textCursor);
		
		jbutton.addActionListener(this);
		
		add(jpanel);
		
		jlabel2.setVisible(false);
		
		grouplayout.setHorizontalGroup(grouplayout.createSequentialGroup()
				.addComponent(jlabel1)
				.addComponent(jtextfield)
				.addComponent(jbutton)
				.addComponent(jlabel2));
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == jbutton && !jtextfield.getText().isEmpty())
		{
			jlabel2.setVisible(false);
			String pregunta = jtextfield.getText();
			jtextfield.setText("");
			
			try 
			{
				post.sendPost(pregunta);
				jlabel2.setText("Pregunta: '" + pregunta + "' enviada!");
				jlabel2.setVisible(true);
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(jframe, "Ingresa una pregunta valida", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}