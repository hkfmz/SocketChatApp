

import java.net.*;
import java.util.Date;
import java.util.Vector;
import java.io.*;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseAdapter;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.table.DefaultTableModel;





import java.awt.*;
import java.awt.event.*;
//import java.awt.event.MouseAdapter;
public class InterfaceClient extends JFrame{
	
	private JPanel jpPrincipale;
/*
 * =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
 */
	private JPanel jp1;
	private JTextArea jtAffichage;
	private JScrollPane jtaScrollPane;
	private JTextArea jtaSaisie;
	private JScrollPane jtaSScrollPane;
	private JButton jbEnvoier;
/*
 * =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
 */
	private JPanel jp2;
	private JPanel jp3;
	private JTextField jtLogin;
	private JButton jbConnecter;
	private JList jlClientConnecter;
	private DefaultListModel listModel;
	private JScrollPane listScrollPane;
	private JTable jtabClientConnecter;
/*
* =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
*/
	private JSplitPane js;
	JLabel jlEntete;
/*
* =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
*/
	private Client client;

/*
 * =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
 * Definition de la constructeur
 */	
	public InterfaceClient(){
		initialisation();
		Dimension d=new Dimension();
		this.setContentPane(jpPrincipale);
		d=this.getPreferredSize();
		this.setSize(d);
		this.setTitle("Client");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setJMenuBar(createMenuBar());
		this.setVisible(true);
	}
/*
* =*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
* initialisation de l'interface
*/	
	public void initialisation(){
		
		
		jpPrincipale=new JPanel();
		
		jp1=new JPanel();
		jtAffichage=new JTextArea(10,27);
		jtaScrollPane=new JScrollPane(jtAffichage);
		//jtAffichage.setEditable(false);
		jtAffichage.setForeground(Color.blue);
		
		
		jtaSaisie=new JTextArea(5,5);
		jtaSScrollPane =new JScrollPane(jtaSaisie);
		jtaSaisie.setForeground(Color.BLACK);
		
		
		jbEnvoier=new JButton("envoyer");
		
		
		
		
		jp2=new JPanel();
		jbConnecter=new JButton("Se connecter");
		jtLogin=new JTextField("Entrer votre login");
		jtLogin.selectAll();
		jtLogin.setSelectionStart(0);
		jtaSaisie.setBackground(jtLogin.getSelectionColor());
		
		remplissageTable(null);
		listScrollPane = new JScrollPane(jtabClientConnecter);
        
		remplissageJP1();
        
		remplissageJP2();
		
		
		
		
		js =new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jp2,jp1);
		js.setOneTouchExpandable(true);
		//js.setBackground(Color.orange);
		//js.setBottomComponent(jp2);
		js.setRequestFocusEnabled(false);
		remplissageJPPrincipale();
		
		jp1.setVisible(false);
		//jbConnecter.setEnabled(false);
		
		jtLogin.addMouseListener(new TraitementSourie());
		jtLogin.addKeyListener(new TraitementClavier());
		jbConnecter.addActionListener(new TraitemmentButtonAction());
		jbEnvoier.addActionListener(new TraitemmentButtonAction());
		
		
		
		client=new Client();
		client.setPort(9999);
		client.setInetAddress("localhost");
		
		
		TimeThread t=new TimeThread();
		t.start();
	}
	public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("?");
        menu.setMnemonic(KeyEvent.VK_A);
        //menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Bienvenue",KeyEvent.VK_T);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
        menuItem.addActionListener(new TraitemmentButtonAction());
        menu.add(menuItem);
        
        return menuBar;
    }
	
	public void remplissageJP1(){
		GroupLayout layout = new GroupLayout(jp1);
		jp1.setLayout(layout);
		//jp1.setBackground(Color.orange);
		jp1.setOpaque(true);
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup(LEADING)
	        		.addComponent(jtaScrollPane)
	        		.addComponent(jtaSScrollPane)
	        		.addComponent(jbEnvoier))
        		);
        //layout.linkSize(SwingConstants.HORIZONTAL,jtaSScrollPane, jtaScrollPane,jbEnvoier );
        layout.setVerticalGroup(layout.createSequentialGroup()
        		.addComponent(jtaScrollPane)
	        	.addComponent(jtaSScrollPane)
	            .addComponent(jbEnvoier)
                );		
	}//Fin Remplissage jp1
	
	public void remplissageJP2(){
		jp3=new JPanel();
		GroupLayout layout = new GroupLayout(jp3);
		jp3.setLayout(layout);
		//jp3.setBackground(Color.orange);
		jp3.setOpaque(true);
		layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup(LEADING)
        			//.addComponent(jtLogin)
	        		.addComponent(jbConnecter)
	        		.addComponent(listScrollPane))
        		);
        
        //layout.linkSize(SwingConstants.HORIZONTAL,jtLogin, listScrollPane,jbConnecter );
        
        layout.setVerticalGroup(layout.createSequentialGroup()
        		//.addComponent(jtLogin)
        		.addComponent(jbConnecter)
	            .addComponent(listScrollPane)
                );
        jp2.setLayout(new BorderLayout());
        jp2.add(jtLogin,BorderLayout.NORTH);
        jp2.add(jp3,BorderLayout.CENTER);
	}//Fin Remplissage jp2
	
	public void remplissageJPPrincipale(){
		jlEntete=new JLabel();
		ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/entete.png"));
		jlEntete.setPreferredSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
		jlEntete.setIcon(image);
		jlEntete.setOpaque(true);
		jlEntete.setBackground(Color.WHITE);
		jlEntete.setForeground(Color.blue);
		jpPrincipale.setLayout(new BorderLayout());
		jpPrincipale.add(jlEntete,BorderLayout.NORTH);
		jpPrincipale.add(js,BorderLayout.CENTER);
	}//Fin Remplissage jp2
	
	
	
	public void remplissageTable(String [][]tab){
		
		System.out.println("remplissage table  h2");
		String [][] tDonnee=tab;
		String [] tNom={"Login de Client","Adresse IP"};
		jtabClientConnecter=new JTable(new MonTabModel(tNom,tDonnee));
		jtabClientConnecter.getSelectionModel().setSelectionInterval(0, 0);
		jtabClientConnecter.setPreferredScrollableViewportSize(new Dimension(100, 100));
		jtabClientConnecter.setFillsViewportHeight(true);
		jtabClientConnecter.setSelectionBackground(Color.cyan);
		jtabClientConnecter.setBackground(jtLogin.getSelectionColor());
				
	}
	
	public static void main(String []args){
		
		
		InterfaceClient mi=new InterfaceClient();
		
	}//Fin main
	
	
	class MonTabModel extends DefaultTableModel{
		String []columnNames;
		Object[][]data;
		public MonTabModel(Object []n,Object[][]d){
			super(d,n);
			columnNames=(String[])n;
			data=d;
		}
	}//Fin class MonTabModel
	
	public class TraitementSourie extends MouseAdapter {

		public void mouseClicked(MouseEvent e) {
			if(e.getSource().equals(jtLogin)){
				jtLogin.selectAll();
			}
		}

	}
	public class TraitementClavier extends KeyAdapter {
		ImageIcon image;
		public void keyPressed(KeyEvent e) {
			String s="localhost";
			
			if(e.getSource().equals(jtLogin)){
				if(e.getKeyChar()=='\n'){
					int len=jtLogin.getText().length();
					if(len!=0){
						s=jtLogin.getText().replace(" ", "");
						if(s.length()!=len){
							image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/erroricon.png"));
							JOptionPane.showMessageDialog(null,"le login ("+jtLogin.getText()+") contient des espaces ","Attention",JOptionPane.YES_NO_OPTION,image);
						}
						else
						{
							image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/edit_f2.png"));
							String adrServeur=(String) JOptionPane.showInputDialog(null,"Entrer @IP du serveur SVP: ","@IP Serveur",1,image,null,"localhost");
							
							client.setInetAddress(adrServeur);
							client.setPort(9999);
							client.setLogin(jtLogin.getText());
							client.etablissementDeConnexion();
						}
					}
				}
			}
		}

	}//Fin class TraitementClavier
	
	
	class TraitemmentButtonAction implements ActionListener{
	
		public void actionPerformed(ActionEvent e){
			if(e.getSource().equals(jbConnecter)){
				
				if (e.getActionCommand().indexOf("Se conn")!=-1){
					ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/edit_f2.png"));
					String adrServeur=(String) JOptionPane.showInputDialog(null,"Entrer @IP du serveur SVP: ","@IP Serveur",1,image,null,"localhost");
					
					client.setInetAddress(adrServeur);
					client.setPort(9999);
					client.setLogin(jtLogin.getText());
					
					client.etablissementDeConnexion();
					jtabClientConnecter.getSelectionModel().setSelectionInterval(0, 0);
					System.out.println("1");
				}
				else{
					System.out.println("2");
					
					
					client.deconnecter();
					//ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/informationicon.png"));
					//JOptionPane.showMessageDialog(null,"Vous etes deconnecter de la serveur","Deconnexion...",JOptionPane.YES_OPTION,image);
				}
			}
			else{
				if(e.getSource().equals(jbEnvoier)){
					
					int pos=jtabClientConnecter.getSelectedRow();
					
					if(pos==0){
						client.setAdrIPDestination("tout");
						client.setLoginDestination("tout");
					}
					else
					{
						client.setAdrIPDestination(String.valueOf(pos));
						client.setLoginDestination(String.valueOf(pos));
					}
					if(pos>=0){
						client.setMsgE1(jtaSaisie.getText());
						client.setTypeMsgE("s");
						client.envoieMessage();
					}
					else{
						ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/optionpane.warningicon.png"));
						JOptionPane.showMessageDialog(null,"Il faut choisir un client","Attention...",JOptionPane.YES_OPTION,image);
					}
				}
			}
			if(e.getActionCommand().equalsIgnoreCase("bienvenue")){
				new Bienvenue();
			}
				
		}
		
	}//Fin class TraitementClavier
	public class Bienvenue extends JDialog  {
		private JEditorPane htmlPane;
		private URL helpURL;
		public Bienvenue(){
			htmlPane = new JEditorPane();
	        htmlPane.setEditable(true);
	        htmlPane.setOpaque(true);
	        htmlPane.setForeground(Color.BLUE);
	        JScrollPane htmlView = new JScrollPane(htmlPane);
	        try{
	        	helpURL = Bienvenue.class.getResource("description.html");
	        	htmlPane.setPage(helpURL);
	        }catch(IOException e){System.out.println("erreeur");}
	        this.add(htmlView);
	        Dimension d=new Dimension();
	        d=this.getPreferredSize();
	        this.setSize(650,500);
	        this.setTitle("Bienvenue");
	        this.setVisible(true);
		}
	}

	
	class Client implements Runnable{

	// Déclaration des attributs de la classe 
	//
	   	private int port ;
	    private InetAddress inetAddress ;    
	    private Thread _t ;
		private Socket s ;
	    private String login ;
		
		private String msgRecue;
		private String typeMsgRecue;
		
		private String typeMsgE;
		private String msgE1;
		private String loginDestination="serveur";
		private String ipDestination="localhost";

		private Vector vClient=new Vector();
		//private JTextArea jtaAffichage;
	// Déclaration des méthodes

	    	/** Constructeur de la classe Client */
	    	public Client() {
	    		
	    	}
	    
		
		/** Méthode déclenchée après appel de '_t.start()', son rôle consiste à lire et afficher les messages envoyés par le serveur*/
	 	//Il s'agit principalement des messages envoyés par les autres utilisateurs (clients) */

		public void run(){
			try{
				while(true){
					//ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/error.png"));
					//JOptionPane.showMessageDialog(null,"debut reception1 "+jtLogin.getText(),"Attention",JOptionPane.YES_NO_OPTION,image);
					// 1. Récupérer le flux d'entrée de la socket s (méthode getInputStream() de la classe Socket)
					InputStream is = s.getInputStream ();
	      	  		ObjectInputStream ois = new ObjectInputStream (is);

					// 2. Lire le type de message envoyé par le serveur (méthode bloquante, par exemple : readObject())
					Object obj = ois.readObject();                
	         		

					// 3. Convertir l'objet ('Object') reçu en une chaine de caractères ('String')
					String msg = (String) obj;
					typeMsgRecue=msg;
					//image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/error.png"));
					//JOptionPane.showMessageDialog(null,"debut reception2 "+jtLogin.getText(),"Attention",JOptionPane.YES_NO_OPTION,image);
					
					// 4. Lire le  message envoyé par le serveur (méthode bloquante, par exemple : readObject())
					obj = ois.readObject();
					//JOptionPane.showMessageDialog(null,"fin reception "+typeMsgRecue+" "+jtLogin.getText(),"Attention",JOptionPane.YES_NO_OPTION,image);
					//acceptation de connexion (TypeMsgRecue=0)
	         		if(typeMsgRecue.equalsIgnoreCase("ac")){
	         			msgRecue = "connexion etablie a "+(((Date) obj)).toString().substring(11,20);
	         			System.out.println("allo------ "+msgRecue);
	         			//JOptionPane.showMessageDialog(null,"msgRecue "+msgRecue,"Attention",JOptionPane.YES_NO_OPTION,image);
	         			jtAffichage.setText(jtAffichage.getText()+"\n"+msgRecue);
	         			
	         		}
	         		else{
	         			if(typeMsgRecue.charAt(0)=='i'){
	         				//JOptionPane.showMessageDialog(null," Avant","Attention",JOptionPane.YES_NO_OPTION,image);
		         			String[][]donnee = (String[][]) obj;
		         			//JOptionPane.showMessageDialog(null," Apree","Attention",JOptionPane.YES_NO_OPTION,image);
		         			Object[] objects=new Object[20];
		         			int nb=Integer.parseInt(typeMsgRecue.substring(1));
		         			if(jtabClientConnecter.getRowCount()<nb){
			         			for(int i=jtabClientConnecter.getRowCount();i<nb;i++){
			         				//image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/error.png"));
									//JOptionPane.showMessageDialog(null,"i= "+i+" typeMsgRecue= "+typeMsgRecue,"Attention",JOptionPane.YES_NO_OPTION,image);
			         				DefaultTableModel tab = (DefaultTableModel) jtabClientConnecter.getModel();
									objects[0] = (Object)donnee[i][0];
									objects[1] = donnee[i][1];
									tab.addRow(objects);
									
			         			}//fin for
		         			}//fin if
			         			
		         		}
	         			else{
	         				if (typeMsgRecue.charAt(0)=='a'){
			         			String[][]donnee = (String[][]) obj;
			         			Object[] objects=new Object[20];
			         			DefaultTableModel tab = (DefaultTableModel) jtabClientConnecter.getModel();
								objects[0] = (Object)donnee[0][0];
								objects[1] = donnee[0][1];
								tab.addRow(objects);
	         				}
	         				else{
	         					if (typeMsgRecue.charAt(0)=='s'){
				         			String[][]donnee = (String[][]) obj;
				         			Object[] objects=new Object[20];
				         			int nb=Integer.parseInt(typeMsgRecue.substring(1));
				         			DefaultTableModel tab = (DefaultTableModel) jtabClientConnecter.getModel();
									objects[0] = (Object)donnee[0][0];
									objects[1] = donnee[0][1];
									//tab.addRow(objects);
									tab.removeRow(nb);
		         				}
	         					else{
			         				String s=(String)obj;
			         				jtAffichage.setText(jtAffichage.getText()+"\n"+s);
			         			}//fin if
	         				}
	         				
	         			}
	         			
	         			jtabClientConnecter.setSelectionMode(0);
	         			
	         		}

				}
			}
			catch(Exception e){
				ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/informationicon.png"));
				JOptionPane.showMessageDialog(null,"Vous etes deconnecter de la serveur","Deconnexion...",JOptionPane.YES_OPTION,image);
			
			}
		}
	 
	
		public void etablissementDeConnexion(){
			try{

				// 1. Créer la socket
				//jtAffichage.setText(jtAffichage.getText()+"\nConnexion au serveur...");
		        s = new Socket(getInetAddress(),getPort());
		        
		        _t = new Thread(this);
				_t.start();
				
		        typeMsgE="dc";//demande de connexion
				loginDestination="serveur";
				ipDestination="localhost";
				msgE1="demande de connexion";
				//ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/error.png"));
				//JOptionPane.showMessageDialog(null,"hello etablissement connexion","Erreur",JOptionPane.YES_NO_OPTION,image);
				
				envoieMessage();
				jbConnecter.setText("Se deconnecter");
				jp1.setVisible(true);
				js.resetToPreferredSizes();
				jtabClientConnecter.getSelectionModel().setSelectionInterval(0, 0);
				jtabClientConnecter.setSelectionMode(0);
				
			}catch(IOException e){
				ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/erroricon.png"));
				JOptionPane.showMessageDialog(null,"Erreur de connexion avec le serveur","Connexion...",JOptionPane.YES_NO_OPTION,image);
				jbConnecter.setText("Se connecter");
	        	System.out.println("Erreur de serilisation "+e);
			}
		}
		public void envoieMessage(){
			try{

		        OutputStream os;
				ObjectOutputStream oos ;
		        os = s.getOutputStream();
				oos = new ObjectOutputStream(os);
				
				//ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/error.png"));
				//JOptionPane.showMessageDialog(null,"debut envoieMessage typeMsgE = "+typeMsgE,"Erreur",JOptionPane.YES_NO_OPTION,image);
	        	
				oos.writeObject(typeMsgE);
				oos.writeObject(loginDestination);
				oos.writeObject(ipDestination);
				oos.writeObject(login);
				oos.writeObject(msgE1);
				//image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/error.png"));
				//JOptionPane.showMessageDialog(null,"fin envoieMessage typeMsgE = "+typeMsgE,"Erreur",JOptionPane.YES_NO_OPTION,image);
	        	
				jtAffichage.setText(jtAffichage.getText()+"\n"+login+">> "+msgE1);
				if(msgE1.equalsIgnoreCase("bye")){
					deconnecter();				
				}
				jtaSaisie.setText("");	
				
			}catch(IOException e){
				ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/error.png"));
				JOptionPane.showMessageDialog(null,"Erreur d'emission de message","Erreur",JOptionPane.YES_NO_OPTION,image);
	        	System.out.println("Erreur de serilisation "+e);
			}
		}
		public void deconnecter(){
			try{
				typeMsgE="dd";//demande de deconnexion
				loginDestination="serveur";
				ipDestination="localhost";
				msgE1="demande de connexion";
				envoieMessage();
				
				//jbConnecter.setEnabled(true);
				//vider la table
				jbConnecter.setText("Se connecter");
				jtAffichage.setText(jtAffichage.getText()+"\ndeconnexion...");
				int taille=jtabClientConnecter.getRowCount();
				//ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/error.png"));
				//JOptionPane.showMessageDialog(null,"nb client dans la table = "+taille,"Erreur",JOptionPane.YES_NO_OPTION,image);
				DefaultTableModel tab = (DefaultTableModel) jtabClientConnecter.getModel();
				for(int i=taille-1;i>=0;i--){
					tab.removeRow(i);
				}
				s.close();
			}catch(IOException e){
				ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/error.png"));
				JOptionPane.showMessageDialog(null,"Erreur de deconnexion...","Erreur",JOptionPane.YES_NO_OPTION,image);
				jbConnecter.setText("Se connecter");
	        	System.out.println("Erreur de serilisation "+e);
			}
			
		}
	      /** Méthode permettant d'introduire le numéro de port surveillé par le serveur */          
	   	public void setPort(int _port){
	   		this.port=_port;
	    	}

	   	/** Méthode permettant de retourner la message recue */          
		public String getMsgRecue(){
			return this.msgRecue;
		}
		/** Méthode permettant de mmodifier la message recue */          
		public void setMsgRecue(String s){
			this.msgRecue=s;
		}
		/** Méthode permettant de retourner la message recue */          
		public String getTypeMsgRecue(){
			return this.typeMsgRecue;
		}
		/** Méthode permettant de mmodifier la message recue */          
		public void setTypeMsgRecue(String s){
			this.typeMsgRecue=s;
		}
		/** Méthode permettant de retourner la message recue */          
		public String getTypeMsgE(){
			return this.typeMsgE;
		}
		/** Méthode permettant de mmodifier la message recue */          
		public void setTypeMsgE(String s){
			this.typeMsgE=s;
		}
		/** Méthode permettant de retourner la message envo */          
		public String getMsgE1(){
			return this.msgE1;
		}
		/** Méthode permettant de mmodifier la message  */          
		public void setMsgE1(String s){
			this.msgE1=s;
		}
		
		/** Méthode permettant de retourner le login du destinataire */          
		public String getLoginDestination(){
			return this.loginDestination;
		}
		/** Méthode permettant de mmodifier le login du destinataire */          
		public void setLoginDestination(String s){
			this.loginDestination=s;
		}/** Méthode permettant de retourner l @IP du destinataire */          
		public String getAdrIPDestination(){
			return this.ipDestination;
		}
		/** Méthode permettant de mmodifier l @IP du destinataire */          
		public void setAdrIPDestination(String s){
			this.ipDestination=s;
		}
		/** Méthode permettant de retourner le numéro de port du serveur */          
	    	public int getPort(){
			return this.port;
	    	}

	  	/** Méthode permettant d'introduire le nom ou l'IP du serveur */          
	    	public void setInetAddress (String  _machineName){
	    		try{
	    			inetAddress=InetAddress.getByName(_machineName);
	    		}
	    		catch(Exception e){System.out.println();}
	    		
	    	}


		/** Méthode permettant de retourner l'adresse IP du serveur */ 
	   	public InetAddress getInetAddress(){
	        return this.inetAddress;
	    	}

		/** Méthode permettant d'introduire le login de l'utilisateur */ 
		public void getLogin(){
			System.out.print("donner votre login SVP:");
			//try{
				//this.login=Lire.S();
			//}catch(IOException e){System.out.println(e.getMessage());}
			
		}
		/** Méthode permettant d'introduire le login du client login<>" " */          
		public void setLogin (String  l){
			this.login=l;
		}

	}//Fin Client
	
	class TimeThread extends Thread{
		public void run(){
			Color c= jtLogin.getSelectionColor();
			while(true){
				try{
					jlEntete.setText(new Date().toString());
					if(jtLogin.getText().equalsIgnoreCase("entrer votre login")){
						if(jtLogin.getSelectionColor().equals(c)){
							jtLogin.selectAll();
							jtLogin.setSelectionColor(Color.red);
						}
						else{
							jtLogin.setSelectionColor(c);
							jtLogin.selectAll();
						}
					}
					else{
						if(jtLogin.getText().indexOf(" ")>=0){
							if(jtLogin.getSelectionColor().equals(c)){
								jtLogin.select(jtLogin.getText().indexOf(" "),jtLogin.getText().length());
								jtLogin.setSelectionColor(Color.red);
							}
							else{
								jtLogin.select(jtLogin.getText().indexOf(" "),jtLogin.getText().length());
								jtLogin.setSelectionColor(c);
								
							}
						}
					}
					sleep(1000);
				}catch(Exception e){}
			}
			
		}
	}
}
