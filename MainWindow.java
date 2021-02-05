
package prodcount;

import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class MainWindow implements ActionListener, KeyListener, MouseListener{
    
    //VARIABLES
    JFrame window = new JFrame();
    JPanel panelB = new JPanel();
    JPanel logPan = new JPanel();
   
    JLabel countLabel = new JLabel();
    JLabel instructLabel = new JLabel();
    JLabel timeLabel = new JLabel();
    JLabel productivity = new JLabel();
    JLabel lossLabel = new JLabel();
    JTextArea logText = new JTextArea(0,1);
    JScrollPane logScroll = new JScrollPane(logText);
    
    
    JLabel aviso = new JLabel();
    
    int elapsedTime = 0;
    int count = 0;
    int sec = 0;
    int min = 0;
    int hor = 0;
    static double prod = 0;
    int los = 0;
    int minCom = 0;
    int control = 0;
    double prodSec = 0;
    int global = 0;
    
    String logFirstMessage = " APERTE ESC PARA REGISTRAR!";
    String agora = "";
    
 
    boolean started = false;
    
    String countS = String.format("%01d", count);
    String secS = String.format("%02d", sec);
    String minS = String.format("%02d", min);
    String horS = String.format("%02d", hor);
    String prodS = String.format("%.1f", prod);
    String losS = String.format("%01d", los);
    final static String newline = "\n";
  
	Timer timer = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		elapsedTime=elapsedTime+1000;
        hor = (elapsedTime/3600000);
        min = (elapsedTime/60000) % 60;
        minCom = (elapsedTime)/60000;
        sec = (elapsedTime)/1000 % 60;
        secS = String.format("%02d", sec);
        minS = String.format("%02d", min);
        horS = String.format("%02d", hor);

        timeLabel.setText(horS+":"+minS+":"+secS);
        timeLabel.setForeground(Color.cyan);
        timeLabel.setBackground(Color.BLACK);
		}
    	    	
    });
	
    
   //CONTRUCTOR 
    public MainWindow(){
       
       countLabel.setText(countS);
       countLabel.setForeground(Color.white);
       countLabel.setFont(new Font("Arial", Font.BOLD, 300));
       countLabel.setBounds(510, 30, 700, 300);
       
       instructLabel.setText("UN: ");
       instructLabel.setFont(new Font("Arial", Font.BOLD, 100));
       instructLabel.setBounds(330, 30, 300, 300);
       instructLabel.setForeground(Color.LIGHT_GRAY);
              
       timeLabel.setText(horS+":"+minS+":"+secS);
       timeLabel.setBounds(30, 30, 233, 100);
       timeLabel.setOpaque(true);
       timeLabel.setBorder(BorderFactory.createBevelBorder(1));
       timeLabel.setFont(new Font("Roboto Light", Font.PLAIN, 40));
       timeLabel.setHorizontalAlignment(JTextField.CENTER);
       timeLabel.setForeground(Color.cyan);
       timeLabel.setBackground(Color.BLACK);
       
       productivity.setText("un/min: "+prodS);
       productivity.setBounds(30, 160, 233, 100);
       productivity.setOpaque(true);
       productivity.setBorder(BorderFactory.createBevelBorder(1));
       productivity.setFont(new Font("Roboto Light", Font.PLAIN, 25));
       productivity.setHorizontalAlignment(JTextField.CENTER);
       productivity.setForeground(Color.cyan);
       productivity.setBackground(Color.BLACK);
       
       aviso.setBounds(10, 390, 300, 300);
       aviso.setFont(new Font("Tahoma", Font.PLAIN, 15));
       aviso.setText("<HTML>"
       		+ "<BR>ENTER : Pausar Timer <BR>"
       		+ "<BR>DELETE : Subtrair Contador<BR>"
       		+ "<BR>ESC : Registrar Trabalho"
       		+ "<BR>"
       		+ "</HTML>");
       aviso.setVerticalAlignment(JTextField.NORTH);
       aviso.setForeground(Color.WHITE);
     
       lossLabel.setText("Perdido: "+losS);
       lossLabel.setBounds(10, 326, 300, 50);
       lossLabel.setFont(new Font("Courier New", Font.BOLD,25));
       lossLabel.setBorder(BorderFactory.createBevelBorder(1));
       lossLabel.setHorizontalAlignment(JTextField.CENTER);
       lossLabel.setForeground(Color.RED);
       lossLabel.setBackground(Color.BLACK);
       lossLabel.setOpaque(true);
        
       panelB.setBackground(Color.lightGray);
       panelB.add(timeLabel);
       panelB.add(productivity);
       panelB.setBounds(10, 10, 300, 300);
       panelB.setLayout(null);
       panelB.setBorder(BorderFactory.createBevelBorder(1));
       
       logText.setEditable(false);
       logText.setFocusable(false);
       logText.setFont(new Font("Roboto Light",Font.PLAIN, 16));
       logText.append(logFirstMessage+"\n");
       logText.setCursor(null);
       logText.addMouseListener(this);
       logText.setLineWrap(true);
       logText.setBackground(Color.BLACK);
       logText.setBorder(null);
       logText.setForeground(Color.CYAN);
       //logText.addKeyListener(this);
       //logText.setVerticalAlignment(JTextField.TOP);
       //logScroll.setLayout();
       Dimension logD = new Dimension(850,300);
       logScroll.setPreferredSize(logD);
       logScroll.setBackground(Color.BLACK);
       logScroll.setOpaque(true);
       
       
       logPan.setLayout(new BorderLayout());
       logPan.setBackground(Color.LIGHT_GRAY);
       logPan.setBounds(330, 326, 850, 300);
       logPan.setBorder(BorderFactory.createBevelBorder(1));
       logPan.add(logScroll);
       
       
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       //window.setSize(1280, 800);
       window.setExtendedState(JFrame.MAXIMIZED_BOTH);
       window.setUndecorated(true);
       window.setLayout(null);             
       window.add(panelB);
       window.add(countLabel);
       window.add(instructLabel);
       window.add(aviso);
       window.add(lossLabel);
       window.add(logPan);  
       window.addMouseListener(this);
       window.addKeyListener(this);
       window.getContentPane().setBackground(Color.DARK_GRAY);
       window.setTitle("Produção");
      
       
       
       window.setVisible(true);
      // System.out.println(minCom, min);
   }  
    
    void prodSum() {
    	count = count + 1;
    	
    	String countS = String.format("%01d", count);
        countLabel.setText(countS);
        countLabel.setFont(new Font("Arial", Font.BOLD, 300));
        countLabel.setBounds(510, 30, 700, 300);
        
    }
    
    void start() {
    	timer.start();
    	
    }
    
    void pause() {
    	  	
    	timer.stop();
    	timeLabelPause("Pa","usa","do");
    }
    
    void reset() {
    	
    	//global = count;
    	count = 0;
    	String countS = String.format("%01d", count);
    	prodCounter(countS);
    	
    	prod = 0;
        control = 0;
        minCom = 0;
    	productivityUpdate(prod);
    	
    	timer.stop();
    	elapsedTime = 0;
    	timeUpdate("00", "00", "00");
    	
    	los = 0;
    	losS = "0";
    	lossUpdate(losS);
    	
    	//System.out.println(global);
       	
    }
    
   void subtract() {
    	
    	count = count -1;
    	String countS = String.format("%01d", count);
    	prodCounter(countS);
    	los = los + 1;
    	String losS = String.format("%01d", los);
    	lossUpdate(losS);
    	
    	
    	
    }
    
    public String productivityUpdate(double prod) {
    	
    	String prodS = String.format("%.1f", prod);
    	productivity.setText("un/min: "+prodS);
        productivity.setBounds(30, 160, 233, 100);
        productivity.setOpaque(true);
        productivity.setBorder(BorderFactory.createBevelBorder(1));
        productivity.setFont(new Font("Roboto Light", Font.PLAIN, 25));
        productivity.setHorizontalAlignment(JTextField.CENTER);
        productivity.setForeground(Color.cyan);
        productivity.setBackground(Color.BLACK);
        
        return prodS;
    	
    }
    
    
    void prodCounter(String countS) {
    	
    	countLabel.setText(countS);
        countLabel.setFont(new Font("Arial", Font.BOLD, 300));
        countLabel.setBounds(510, 30, 700, 300);
    	
    }
    
    void lossUpdate(String losS) {
    	
    	lossLabel.setText("Perdido: "+losS);
        lossLabel.setBounds(10, 326, 300, 50);
        lossLabel.setFont(new Font("Courier New", Font.BOLD,25));
        lossLabel.setBorder(BorderFactory.createBevelBorder(1));
        lossLabel.setHorizontalAlignment(JTextField.CENTER);
        lossLabel.setForeground(Color.RED);
        //lossLabel.setBackground(Color.BLACK);
    }
    
    void timeUpdate(String horS,String minS, String secS) {
    	
    	timeLabel.setText(horS+":"+minS+":"+secS);
        timeLabel.setBounds(30, 30, 233, 100);
        timeLabel.setOpaque(true);
        timeLabel.setBorder(BorderFactory.createBevelBorder(1));
        timeLabel.setFont(new Font("Roboto Light", Font.PLAIN, 40));
        timeLabel.setHorizontalAlignment(JTextField.CENTER);
        timeLabel.setForeground(Color.CYAN);
        timeLabel.setBackground(Color.BLACK);
    }
    
    
    double calculon() {
    	if (minCom > 0 & minCom  > control) {
    		
        	control = minCom;
        	double d = count;
        	double t = minCom;
        	prod = (d-1)/t;
        	productivityUpdate(prod);
        	
        	System.out.println("Contador: "+ count +" Minutos: "+ minCom +" controle: " +  control);
        	System.out.println(prod);
        	
        }
		return prod;
    	
    }
    
    void timeLabelPause(String horS, String minS, String secS) {
    	
        timeLabel.setText(horS+minS+secS);
        timeLabel.setBounds(30, 30, 233, 100);
        timeLabel.setOpaque(true);
        timeLabel.setBorder(BorderFactory.createBevelBorder(1));
        timeLabel.setForeground(Color.RED);
        timeLabel.setFont(new Font("Roboto Light", Font.PLAIN, 40));
        timeLabel.setHorizontalAlignment(JTextField.CENTER);
    	
    }
    
    
        
   
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		prodSum();
		calculon();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(e.getKeyCode());
		
		
		if (e.getKeyCode() == 10) {
			pause();
		}
		
		if (e.getKeyCode() == 127) {
			subtract();
		}
		
		if(e.getKeyCode() == 27) {
			global = count + global;
                        String prodSS = String.format("%.1f", prod);
                        String reseted = " | PRODUZIDO  : "+ count + "  |  TOTAL : " + global + 
                                " |  TEMPO : " + horS + ":" + minS + ":" + secS + "  |  UN/MIN : " + 
                                prodSS + "  |  PERDAS = "+ los +
                                " \n";
                        currentTime();
                        fileWriter(agora + reseted);
                        logText.append(reseted);
                        
                        reset();
		}
		
	}	

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
        
        public void fileWriter(String log){
        try{
            FileWriter myWriter = new FileWriter("LOG DE TRABALHO.txt", true);
            myWriter.write(log);
            myWriter.close();
            System.out.println("escrito com sucesso");
            
        }
        catch (IOException e){
            System.out.println("ocorreu um erro na hora de gravar");
            e.printStackTrace();
            
        }
    }
            
        public String currentTime(){
            DateTimeFormatter ddt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime now = LocalDateTime.now();
            agora = ddt.format(now);
            
            return agora;
        }
    
}
