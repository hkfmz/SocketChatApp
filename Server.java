

/*
 **** Chat multiclient - M1I 2008 *****
 *	Server.java
 *
 */


// Packages à inclure
import java.net.*;
import java.awt.Toolkit;
import java.io.*;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Server  implements Runnable,Serializable{
// Déclaration des attributs de la classe
//
    private int listenPort ;
	private InetAddress inetAddress ;
    private ServerSocket ss;
 	private Thread _t;
	Socket _s ;
	private String[] logins = new String[20];
 	Vector clients = new Vector();
	int nbClients = 0;
	
	int typeMsgE;
	
	String typeMsgRecue;
	private String loginDestination;
	private String ipDestination;
	private String login;
	private String msgRecue;
	
	
// Déclaration des méthodes

    	/** Constructeur de la classe Client */
   	public Server() {

    	}

  	/** Méthode permettant d'introduire le numéro de port du serveur */
    	private void setListenPort(int _listenPort){
    		this.listenPort=_listenPort;
   	}

	/** Méthode permettant de retourner le numéro de port du serveur */
 	public int getPort(){
    		return listenPort ;
	}

    	/** Méthode permettant de lancer le serveur*/
	private void launch(){
		logins[0]="Tout";
		clients.add("Tout");
		nbClients++;
		// 1. Créer la socket du serveur : ServerSocket(int _port)
		try{
			ss=new ServerSocket(listenPort);
		}catch(IOException e){System.out.println(e.getMessage());}

      	System.out.println("Serveur en execution...");

        	try{
			while(true){

				// 2. Appeler la méthode 'accept()' du ServerSocket créée en 1.
				_s=ss.accept();
				// 3. Ajouter la nouvelle connexion dans un vecteur (ou tableau) contenant toutes les connexions
				clients.add(clients.size(),_s);
                nbClients++;

                // 4. Appeler la méthode _t.start() permettant de lancer run() (run() : permet de traiter les connexions des clients)
				_t = new Thread(this);
				_t.start();
			}
        	}catch(Exception e){
            	System.out.println("Erreur socket ...."+e);
            	System.exit(0);
		}
 	}

	public void initialisationTablelient(Socket socket){
		System.out.println("debut for initialisationTablelient "+logins[clients.indexOf(socket)]+ "clients.indexOf(socket) = "+clients.indexOf(socket));
			String [][]donnee=new String[20][2];
			donnee[0][0]=logins[0];
			donnee[0][1]="Tout";
			int j=1;
			int num=clients.indexOf(_s);
			for(int i=1;i<clients.size();i++){
 				try{
 					Socket soc;
 					if(!(soc=(Socket)clients.get(i)).equals(socket)){
 						
	 					donnee[j][0]=logins[i];
	 					
	 					String adr=soc.getInetAddress().toString();
	 					System.out.println("hello "+adr);
	 					donnee[j][1]=soc.getLocalAddress().toString();
	 					//System.out.println("ok2  "+nbClients);
	 					j++;
	 				
	 						
 					}//fin if
 					
 					
 				}catch(NullPointerException e){
 					ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("src/images/error.png"));
					JOptionPane.showMessageDialog(null,"Socket num ("+i+") est null ","Attention",JOptionPane.YES_NO_OPTION,image);
 				}
 				
 			}
			try{
				OutputStream os = socket.getOutputStream();
		        ObjectOutputStream oos = new ObjectOutputStream(os);
		        //msg1 (type initialisation du table client(liste des clients connecter))
		        Object obj = "i"+(Object)String.valueOf(nbClients-1);
		        System.out.println("nbclient-1 = "+(nbClients-1));
		        oos.writeObject(obj);
		        //msg2 (information)
		        obj = (Object)donnee;
		        oos.writeObject(obj);
		        System.out.println("fin for initialisationTablelient "+logins[clients.indexOf(socket)]+" j= "+j);
	           }
				catch(Exception e){
	        	    System.out.println("Erreur initialisationTableClient "+e);
	        	    
	            }
	}
	
	public void ajouterClient(Socket socket,int num){
		try{
			String [][]donnee=new String[1][2];
			donnee[0][0]=logins[num];
			donnee[0][1]=socket.getLocalAddress().toString();
			int j=1;
			num=clients.indexOf(socket);
			System.out.println("debut  ajouter "+logins[clients.indexOf(socket)]);
			for(int i=1;i<clients.size();i++){
 				
 					Socket soc;
 					if(!(soc=(Socket)clients.get(i)).equals(socket)){
 						try{
 						OutputStream os = soc.getOutputStream();
 				        ObjectOutputStream oos = new ObjectOutputStream(os);
 				        //msg1 (type ajout du nouveau client connecter a la table Client des autre client connecter))
 				        Object obj = "a1";
 				        System.out.println("i=  "+i);
 				        oos.writeObject(obj);
 				        //msg2 (information)
 				        obj = (Object)donnee;
 				        oos.writeObject(obj);
 						}catch(SocketException e){
 							System.out.println("Suppression1 du client **ajouter** i= "+i+" "+logins[i]);
 							
 							clients.remove(i);
 							if(i!=nbClients-1){
 								for(int x=i;x<nbClients-1;x++){
 									logins[x]=logins[x+1];
 								}//fin for
 							}
 							nbClients--;
 							supprimerClient(soc,i);
 							i--;
 							
 						}
 					}//fin if
 					else{
 						System.out.println("egale");
 					}
 				
 			}
			System.out.println("fin ajouter "+logins[clients.indexOf(socket)]);
	      }catch(Exception e){System.out.println("Erreur h1 "+e);}
	      
	}// fin ajouterClient
	
	public void supprimerClient(Socket socket,int num){
		try{
			String [][]donnee=new String[1][2];
			donnee[0][0]=logins[num];
			donnee[0][1]=socket.getLocalAddress().toString();
			int j=1;
			System.out.println("debut supprimerClient"); 
			for(int i=1;i<clients.size();i++){
 				
 					Socket soc;
 					if(!(soc=(Socket)clients.get(i)).equals(socket)){
 						Object obj;
 						OutputStream os = soc.getOutputStream();
 				        ObjectOutputStream oos = new ObjectOutputStream(os);
 				        //msg1 (type ajout du nouveau client connecter a la table Client des autre client connecter))
 				        if(i>num){
 				        	obj = "s"+String.valueOf(num);
 				        }
 				        else{
 				        	obj = "s"+String.valueOf(num-1);
 				        	
 				        }
 				        oos.writeObject(obj);
 				        //msg2 (information)
 				        obj = (Object)donnee;
 				        oos.writeObject(obj);	
 					}//fin if
 				
 			}
			
	      }catch(Exception e){System.out.println("Erreur h1 "+e);}
	      System.out.println("Fin supprimerClient"); 
	}// fin ajouterClient
	public Vector getListeClients(){
		return clients;
	}
	public void run(){
		try{
			// 1. Retirer la dernière socket du vecteur
			Socket sck=(Socket)clients.lastElement();
			int n=nbClients;
            while(true){
            	System.out.println("Debut reception");
            	// 2. Récupérer le flux d'entrée de la socket (méthode getInputStream() de la classe Socket)
    			InputStream is = sck.getInputStream ();
    			ObjectInputStream  ois = new ObjectInputStream(is);
    			Object obj;
    			//msg1
    			obj = ois.readObject();
                typeMsgRecue = (String) obj;

                //msg2
                obj = ois.readObject();
                loginDestination = (String) obj;

                //msg3
                obj = ois.readObject();
                ipDestination = (String) obj;

                //msg4
                obj = ois.readObject();
                login = (String) obj;
                logins[n-1]=login;
                
                //msg5
                obj = ois.readObject();
                msgRecue = (String) obj;
                System.out.println("Fin reception du serveur");
                System.out.println("typeMsgRecue "+typeMsgRecue+" "+ipDestination);
                if(typeMsgRecue.equalsIgnoreCase("dc")){//demande de connexion
                	OutputStream os = _s.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(os);
                    //msg1 (type msg)
                    obj = (Object)"ac";//acceptation de la connexion
                    oos.writeObject(obj);
                    //msg2 (information)
                    obj = (Object)new Date();
                    oos.writeObject(obj);
                  
                    //initialisation de tt les table client
                    //for(int i=1;i<nbClients;i++){
                    	//initialisationTablelient((Socket)clients.get(i));
                    //}
                    initialisationTablelient(sck);
                    ajouterClient (sck,clients.indexOf(sck));
                    
                    
                }
                else{
                	if(typeMsgRecue.equalsIgnoreCase("dd")){//demande de deconnexion
                		
                		    System.out.println("debut deconnexion du client **dd** i= "+clients.indexOf(sck)+" "+logins[clients.indexOf(sck)]);
							supprimerClient(sck,clients.indexOf(sck));
							
							if(clients.indexOf(sck)!=clients.size()-1){
								for(int x=clients.indexOf(sck);x<clients.size()-1;x++){
									logins[x]=logins[x+1];
								}//fin for
							}
							nbClients--;
							clients.remove(clients.indexOf(sck));
							System.out.println("fin deconnexion du client **dd** ");
                	}
                	else{
	                	if(ipDestination.equalsIgnoreCase("tout")){
	                		
	                		 for (int i=1;i<nbClients;i++){
	                             //if(!((Socket)clients.get(i)).equals(_s)){
	                			 if(i!=clients.indexOf(sck)){
	                            	 System.out.println("hello      tout login["+i+"]= "+logins[i]);
	                            	 Socket sck1=(Socket)clients.get(i);
	                                 OutputStream os = sck1.getOutputStream();
	                                 ObjectOutputStream oos = new ObjectOutputStream(os);
	                                 System.out.println("debut ecreture tout");
	                                 //msg1
	                                 obj = (Object)"msg";
	                                 oos.writeObject(obj);
	                                 
	                                 //msg2
	                                 obj = (Object)(login+">> "+msgRecue);
	                                 oos.writeObject(obj);
	                                 System.out.println("Fin ecreture tout");
	                             }
	                		 }
	                	}
	                	else{
	                		try{
	                			    int posClientDestination=Integer.parseInt(ipDestination);
	                			    int posClientSource=clients.indexOf(sck);
	                			    Socket sck1;
	                			    System.out.println("leclient "+logins[clients.indexOf(sck)]+" clients.indexOf(sck)= "+(clients.indexOf(sck)));
	                			    if(clients.indexOf(sck)>posClientDestination){
	                			    	sck1=(Socket)clients.get(posClientDestination);
	                			    	System.out.println("envoie d'une msg priver pour leclient "+logins[posClientDestination]+" posClientDestination= "+posClientDestination);
	                			    }
	                			    else{
	                			    	sck1=(Socket)clients.get(posClientDestination+1);
	                			    	System.out.println("envoie d'une msg priver pour leclient "+logins[posClientDestination+1]+" posClientDestination+1= "+(posClientDestination+1));
	                			    }
	                			    
	                				OutputStream os = sck1.getOutputStream();
	                                ObjectOutputStream oos = new ObjectOutputStream(os);
	                                //msg1
	                                obj = (Object)"0";
	                                oos.writeObject(obj);
	                                //msg2
	                                obj = (Object)(login+">> "+msgRecue);
	                                oos.writeObject(obj);
	                                 
	                			
	                			
	                		}catch(NullPointerException e){System.out.println("Socket null");}
	                	}
                	}
                }
               
            }


     		}catch(Exception e){}
	}



//////////////////////////////////////////////////////////////////////////////////////////////
// Méthode principale : 'main'
//
// Le programme prendra 1 parametre : le numero de port à surveiller
//
//////////////////////////////////////////////////////////////////////////////////////////////


	public static void main(String arg[]){
		Server server = new Server();
		//int port = Integer.parseInt (arg[0]);
        int port=9999;

          server.setListenPort(port);
        	server.launch();
    	}
}