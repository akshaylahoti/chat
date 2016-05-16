import java.io.*;
import java.net.*;
import java.util.Scanner;
public class Client {
	public static void chat() throws Exception {
		Socket sock = new Socket("10.100.104.228", 3000); // client tries to connect to '110.100.104.228' (ip) at port 3000 
		BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in)); // reading from keyboard (keyRead object)
		OutputStream ostream = sock.getOutputStream(); // outputstream of the client (to send message to the server) 
		PrintWriter pwrite = new PrintWriter(ostream, true); // using printwriter to write to the stream
		InputStream istream = sock.getInputStream(); // inputstream of the client (to recieve message from the server)
		BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream)); // buffering the input stream
		System.out.println("Start the chitchat, type and press Enter key");
		String receiveMessage, sendMessage;               
		while(true) { // loop forever
			sendMessage = keyRead.readLine();  // client types in first
			pwrite.println(sendMessage);       // send the message to the server
			pwrite.flush();                    // flush the outputstream
			if(sendMessage.equals("Bye."))	   // on sending 'bye', break
				System.exit(0);
			if((receiveMessage = receiveRead.readLine()) != null) { // recieve message from server
				System.out.println("Server : " + receiveMessage); // displaying at the terminal
        		}  
			if(receiveMessage.equals("FILE"))
				break;
			if(receiveMessage.equals("Bye."))	// on recieving 'bye', break
				System.exit(0);  
		}
		sock.close();           
	} 

	public static void filetransfer() throws IOException {
		int filesize = 6022386;	// filesize temporary hardcoded
		long start = System.currentTimeMillis();	//Current time noted
		int bytesRead;	
		int current = 0;
		String s;
		Socket sock = new Socket("10.100.104.228",13267);	//Ip of the server and port at which it is operating
		System.out.println("Connecting...");	
		// receive file
		Scanner in = new Scanner(System.in);
		System.out.println("Enter name of file...");
		s = in.nextLine();	//Get name of file from user
		byte [] mybytearray  = new byte [filesize];	//Make a byte array
		InputStream is = sock.getInputStream();		//Make an inputstream
		FileOutputStream fos = new FileOutputStream(s);	//Make a FileOutputStream
		BufferedOutputStream bos = new BufferedOutputStream(fos);	//Buffer the outputstream
		bytesRead = is.read(mybytearray,0,mybytearray.length);	//Read from the inputstream upto mybytearray.length bytes 
		current = bytesRead;					//And store the number of bytes read into bytesRead
		do {
			bytesRead = is.read(mybytearray, current, (mybytearray.length-current));		//Repeat till
			if(bytesRead >= 0)									//No more bytes are
				current += bytesRead;								//left to be read
		} while(bytesRead > -1);
		bos.write(mybytearray, 0 , current);	//Write the entire mybytearray into the buffered outputstream
		bos.flush();	//Flush the outputstream
		long end = System.currentTimeMillis();	//Note the time
		System.out.println("Thankyou.");	
		bos.close();	//Close the outputstream
		sock.close();	//Close the socket
	}                
	
	public static void main(String[] args) throws Exception {
		while(true) {		
			chat();
			filetransfer();
		}   
	}
}                        
