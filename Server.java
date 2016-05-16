import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Server{
public static void Chat() throws Exception {
        ServerSocket sersock = new ServerSocket(3000); // server listening at port 3000
        System.out.println("Server  ready for chatting");
        Socket sock = sersock.accept( ); // seeing if there is any client trying to communicate at this port                                        
        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in)); // recieving from keyboard
        OutputStream ostream = sock.getOutputStream(); // outputstream of the server(print data to client)
        PrintWriter pwrite = new PrintWriter(ostream, true); // data to be written to the client ( receiveRead  object)
        InputStream istream = sock.getInputStream(); // stream to listen for message from the client
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream)); // buffered input stream for server
        String receiveMessage, sendMessage;               
        while(true) { // loop forever
                if((receiveMessage = receiveRead.readLine()) != null) { // because client types in first, the server has to listen first
                    System.out.println("Client : " + receiveMessage); // print message recieved from client onto the screen         
                }         
            
                if(receiveMessage.equals("Bye."))
                    System.exit(0);// on recieving 'Bye', break

                sendMessage = keyRead.readLine(); // message from the keyboard has to be given to the client
                pwrite.println(sendMessage); // write message onto the outputstream             
                pwrite.flush(); // flush the output stream
                if(sendMessage.equals("FILE")) {
                    sock.close();// on sending 'Bye', break;
                    break;
                }   
                
                if(sendMessage.equals("Bye."))
                    System.exit(0);
        } 
        sersock.close();
  
}                                             

public static void File() throws IOException {
	ServerSocket servsock = new ServerSocket(13267);	//Make a serversocket operating at port 13267. 
	System.out.println("Waiting...");	
	Socket sock = servsock.accept();			//If a client tries to communicate, make a socket.
	System.out.println("Accepted connection : " + sock);
	String s;
	System.out.println("Enter the file to send...");
	Scanner in = new Scanner(System.in);			//Take the filename from the user.
	s = in.nextLine();
	File myFile = new File (s);				//Make a file object pointing to the filename recieved.
	byte [] mybytearray  = new byte [(int)myFile.length()];	//Make a mybytearray of size the size of the file.
	FileInputStream fis = new FileInputStream(myFile);	//Make a fileinputstream
	BufferedInputStream bis = new BufferedInputStream(fis);	//Make a buffered inputstream
	bis.read(mybytearray,0,mybytearray.length);		//Read from the file into mybytearray all the contents of the file
	OutputStream os = sock.getOutputStream();		//Make an outputstream
	System.out.println("Sending...");			
	os.write(mybytearray,0,mybytearray.length);		//Send the mybytearray through the outputstream
	os.flush();						//Flush the outputstream
	sock.close();						//Close the socket
	servsock.close();					//Close the serversocket
}
    
public static void main(String args[]) throws Exception {
  
    while(true) {
        Chat();
        File();

    }
}

}
