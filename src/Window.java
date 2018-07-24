import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

public class Window extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JFrame jframe = new JFrame();
	private JPanel jpanelInput = new JPanel();
	private JPanel jpanelOutput = new JPanel();
	private JScrollPane jscrollpane = new JScrollPane(jpanelOutput, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private BorderLayout borderlayout = new BorderLayout();

	private JLabel jlabel = new JLabel();
	private JLabel jlabel2 = new JLabel();
	
	private JTextField jtextfield = new JTextField(30);
	private JButton jbutton = new JButton("Enviar");
	
	private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
	private Cursor textCursor = new Cursor(Cursor.TEXT_CURSOR);
	
	Post post = new Post();
	
	Window()
	{		
		jframe.setLayout(borderlayout);
		
		jbutton.setCursor(handCursor);
		jlabel.setCursor(textCursor);
		jtextfield.setCursor(textCursor);
		
		jlabel.setVisible(false);
		
		jbutton.addActionListener(this);
		
		jpanelOutput.setBorder(LineBorder.createBlackLineBorder());
		jpanelOutput.setLayout(new BoxLayout(jpanelOutput, BoxLayout.Y_AXIS));
		
		jpanelInput.add(jtextfield);
		jpanelInput.add(jbutton);
		jpanelInput.add(jlabel);
		
		jframe.add(jscrollpane);
		jframe.add(jpanelInput, BorderLayout.SOUTH);
	}
	
	public void showGUI()
	{
		jframe.setTitle("HTTP Request DEMO");
		jframe.setSize(720, 960);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void sendQuestion() throws Exception
	{			
		String pregunta = jtextfield.getText();
		Map<String, String> respuesta = post.sendPost(pregunta);
		for(String key : respuesta.keySet())
		{
			System.out.println(key);
			jpanelOutput.add(new JButton(new AbstractAction(key)
			{
				private static final long serialVersionUID = 1L;
				public void actionPerformed(ActionEvent e)
				{	
					System.out.println(respuesta.get(key));
					jlabel2.setText("<html><h1>" + key + "</h1><div width = 500>" + respuesta.get(key)+ "</div><br></html>");
					jpanelOutput.add(jlabel2);
					
					jframe.revalidate();
					jframe.repaint();
				}
			}));
		}		
		jtextfield.setText("");
		jtextfield.requestFocus();
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == jbutton && !(jtextfield.getText().isEmpty()))
		{
			jlabel.setVisible(true);
			try 
			{ 
				sendQuestion(); 
			} 
			catch (Exception ex) { ex.printStackTrace(); }
		}
		else { JOptionPane.showMessageDialog(jframe, "Ingresa una pregunta valida", "ERROR", JOptionPane.ERROR_MESSAGE); }
	}
}