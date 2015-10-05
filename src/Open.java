import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Open {

	public static void main(String[] args) throws IOException{
		
		String hostName;
		int portNumber;
		int MAX_LEN = 255;
		byte cmdString[] = new byte[MAX_LEN];
		
		System.out.print("csdict> ");
		// recognizes the input from the command line
		int len = System.in.read(cmdString);
		// convert the byte to string
		String inputString = new String(cmdString);
//		System.out.println(cmdString);
//		System.out.println(inputString);
		// split the input into a string array
		String[] inputParts = inputString.split("\\s+");
		String part1 = inputParts[0];
		String part2 = inputParts[1];
		String part3 = inputParts[2];
//		System.out.println(part1);
//		System.out.println(part2);
//		System.out.println(part3);
		
		// java recognizes the first command "open"
		if(part1.equals("open") || part1.equals("OPEN")){
			hostName = part2;
			if(part3 == null){
				portNumber = 2628;
			}
			portNumber = Integer.parseInt(part3);
			int test = portNumber + 2;
//			System.out.println(portNumber);
//			System.out.println(test);
			
			try (
				    Socket echoSocket = new Socket(hostName, portNumber);
				    PrintWriter out =
				        new PrintWriter(echoSocket.getOutputStream(), true);
				    BufferedReader in =
				        new BufferedReader(
				            new InputStreamReader(echoSocket.getInputStream()));
				    BufferedReader stdIn =
				        new BufferedReader(
				            new InputStreamReader(System.in))
				){
					String userInput;
					while ((userInput = stdIn.readLine()) != null) {
					    out.println(userInput);
					    System.out.println("echo: " + in.readLine());
					    System.out.println(in);
					}}catch(IOException e){
						
		}
	}
	}
}


