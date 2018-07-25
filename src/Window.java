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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

public class Window extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JFrame jframe = new JFrame();
	
	private JTabbedPane jtabbedpane = new JTabbedPane();
	
	private JPanel jpanelConsulta = new JPanel();
	private JPanel jpanelInput = new JPanel();
	private JPanel jpanelOutputButton = new JPanel();
	private JPanel jpanelOutputLabel = new JPanel();
	
	private JPanel jpanelSubir = new JPanel();
	
	private JScrollPane jscrollpane = new JScrollPane(jpanelOutputButton, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private JScrollPane jscrollpane2 = new JScrollPane(jpanelOutputLabel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	private JTextPane jtextpane = new JTextPane();
	
	private JTextField jtextfieldSearch = new JTextField(40);
	private JTextField jtextfieldMail = new JTextField(30);
	private JTextField jtextfieldPost = new JTextField(40);
	private JButton jbuttonSearch = new JButton("Enviar");
	private JButton jbuttonPost = new JButton("Subir pregunta");
	
	private Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
	private Cursor textCursor = new Cursor(Cursor.TEXT_CURSOR);
	
	Post post = new Post();
	
	Window()
	{		
		jpanelConsulta.setLayout(new BorderLayout());
		
		jbuttonSearch.setCursor(handCursor);
		jbuttonPost.setCursor(handCursor);
		jtextfieldSearch.setCursor(textCursor);
		jtextfieldPost.setCursor(textCursor);
		jtextpane.setCursor(textCursor);
		
		jtextpane.setContentType("text/html");
		jtextpane.setEditable(false);
		
		jbuttonSearch.addActionListener(this);
		jbuttonPost.addActionListener(this);
		
		jpanelOutputButton.setBorder(LineBorder.createBlackLineBorder());
		jpanelOutputLabel.setBorder(LineBorder.createBlackLineBorder());
		jpanelOutputButton.setLayout(new BoxLayout(jpanelOutputButton, BoxLayout.Y_AXIS));

		jpanelInput.add(jtextfieldSearch);
		jpanelInput.add(jbuttonSearch);
		
		jpanelConsulta.add(jscrollpane, BorderLayout.NORTH);
		jpanelConsulta.add(jscrollpane2, BorderLayout.CENTER);
		jpanelConsulta.add(jpanelInput, BorderLayout.SOUTH);
		
		jpanelSubir.add(jtextfieldMail);
		jpanelSubir.add(jtextfieldPost);
		jpanelSubir.add(jbuttonPost);
		
		jtabbedpane.addTab("Buscar", jpanelConsulta);
		jtabbedpane.addTab("Subir", jpanelSubir);
		
		jframe.add(jtabbedpane);
	}
	
	public void showGUI()
	{
		jframe.setTitle("HTTP Request DEMO");
		jframe.setSize(768, 960);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void searchQuestion() throws Exception
	{			
		jpanelOutputButton.removeAll();
		
		String pregunta = jtextfieldSearch.getText();
		Map<String, String> respuesta = post.searchQuestion(pregunta);
		jpanelOutputButton.add(new JLabel("<html><h1>" + pregunta + "</h1><html>"));
		
		for(String key : respuesta.keySet())
		{
			System.out.println(key);
			jpanelOutputButton.add(new JButton(new AbstractAction(key)
			{
				private static final long serialVersionUID = 1L;
				public void actionPerformed(ActionEvent e)
				{	
					System.out.println(respuesta.get(key));
					jtextpane.setText("<html><h1>" + key + "</h1><div>" + respuesta.get(key)+ "</div><br></html>");
					jpanelOutputLabel.add(jtextpane);
				}
			}));
		}
		jtextfieldSearch.setText("");
		jtextfieldSearch.requestFocus();
		
		jframe.revalidate();
		jframe.repaint();
	}
	
	public void postQuestion()
	{
		String mail = jtextfieldMail.getText();
		String pregunta = jtextfieldPost.getText();
		jtextfieldPost.setText("");
		
		try
		{
			post.postQuestion(pregunta, mail);
			jpanelSubir.add(new JLabel("Se subió tu pregunta: '" + pregunta + "'"));
			
			jframe.revalidate();
			jframe.repaint();
		}
		catch(Exception ex) { ex.printStackTrace(); }
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == jbuttonSearch && !(jtextfieldSearch.getText().isEmpty()))
		{
			try { searchQuestion(); }
			catch (Exception ex) { ex.printStackTrace(); }
		}
		
		if(e.getSource() == jbuttonPost && !(jtextfieldPost.getText().isEmpty()))
		{
			try { postQuestion(); }
			catch (Exception ex) { ex.printStackTrace(); }
		}
		else { JOptionPane.showMessageDialog(jframe, "Ingresa una pregunta valida", "ERROR", JOptionPane.ERROR_MESSAGE); }
	}
}