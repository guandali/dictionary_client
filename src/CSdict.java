
import java.lang.System;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.nio.charset.StandardCharsets;



//import testP.dict_client;

//
// This is an implementation of a simplified version of a command 
// line ftp client. The program takes no arguments.
//


public class CSdict
{
    static final int MAX_LEN = 255;
    static final int PERMITTED_ARGUMENT_COUNT = 1;
    static Boolean debugOn = false;
     static Socket dict_client;
     static BufferedReader in_reader;
     static PrintWriter out_writer;
     static String unspecified_err_msg = "999 Processing error.";
     static boolean quit_or_open_state = false;
     static String dict_setting_ = "* ";
    static int DEFAULT_PORT_NUMBER = 2628;
    
   public static void main(String [] args)

    {
	byte cmdString[] = new byte[MAX_LEN];
	
	
	if (args.length == PERMITTED_ARGUMENT_COUNT) {
	    debugOn = args[0].equals("-d");
	    if (debugOn) {
		System.out.println("Debugging output enabled");
	    } else {
		System.out.println("997 Invalid command line option - Only -d is allowed");
		return;
            } 
	} else if (args.length > PERMITTED_ARGUMENT_COUNT) {
	    System.out.println("996 Too many command line options - Only -d is allowed");
	    return;
	}
		
	try {
	    for (int len = 1; len > 0;) {
		System.out.print("csdict> ");
		len = System.in.read(cmdString);
//		if (len <= 0) 
//		    break;
//		// Start processing the command here.
//		System.out.println("900 Invalid command.");
		if (len <= 0) 
		    break;
		// Start processing the command here.
		System.out.println("Before cmdString is:"+cmdString);
		System.out.println("Before len is:"+len);
		actionPerformed(cmdString);// check cmdString 
		System.out.println("reach here 1 ");
		//System.out.println("900 Invalid command.");
	    }
	} catch (IOException exception) {
	    System.err.println("998 Input error while reading commands, terminating.");
	}
    }

	private static void actionPerformed(byte[] cmdString) {
		// TODO Auto-generated method stub
		String str = new String(cmdString, StandardCharsets.UTF_8);// Convert bytes array to string
		System.out.println("After:"+str);
		String[] split = str.split("\\s+");// split string into string array by identifying spaces
		int length_split = split.length;
		if(split[0].matches("open") || split[0].matches("OPEN")){
			System.out.println("match open");
			// check whether there is already a connection 
			System.out.println("call Build connection");
			buildConnection(split);
			return;
			
		}
		else if (split[0].matches("dict") || split[0].matches("DICT")){
			System.out.println("call showAllDict");
			showAllDict();
			return;
			
		}else if (split[0].matches("define") || split[0].matches("DEFINE")){
			if (!quit_or_open_state){
			defineWord(split);
			return;
			}
			System.out.println("903 Supplied command not expected at this time");
			
			
		}else if (split[0].matches("close") || split[0].matches("CLOSE")){
			closeDict();
			System.out.println("Open new connection or quit");
			return;
			
			
		}else if (split[0].matches("prefixmatch") || split[0].matches("PREFIXMATCH")){
			if (!quit_or_open_state){
			prefixmatchWord(split);
			return;
			}
			System.out.println("903 Supplied command not expected at this time");
			
			
		}else if (split[0].matches("match") || split[0].matches("MATCH")){
			if (!quit_or_open_state){
			matchWord(split);
			return;
			}
			System.out.println("903 Supplied command not expected at this time");
			
			
		}
		else if (split[0].matches("set") || split[0].matches("SET")){
			if (!quit_or_open_state){
			setDict(split);
			return;
			}
			System.out.println("903 Supplied command not expected at this time");
			
			
		}else if (split[0].matches("quit") || split[0].matches("QUIT")){
			
			quitDict();
			return;
			
			//System.out.println("903 Supplied command not expected at this time");
			
			
		}
		else{
			System.out.println("900 Invalid command");
			
		}
		
		
		
		
		
	}
	private static void quitDict() {
		// TODO Auto-generated method stub
		try {
			dict_client.close();
			in_reader.close();
			out_writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("no client connection");
		} catch (NullPointerException e){
			
		}
		quit_or_open_state = true; // change state only allow quit or open 
		DEFAULT_PORT_NUMBER = 2638;// close connection and reset Default value 
		System.out.println("Quitting the program");
		System.exit(0);
		
	}

	//prefix: match all prefix (word)
	//MATCH database strategy word
	private static void prefixmatchWord(String[] split) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
    	String theWord = split[1];
    	String match_ = "match ";
    	String match_database_ = match_.concat(dict_setting_);
    	String match_database_prefix_ = match_database_.concat("prefix ");
    	String prefix_cmd = match_database_prefix_.concat(theWord);
    	System.out.println(prefix_cmd);
    	out_writer.println(prefix_cmd);
    	out_writer.flush();
    	try {
    		while((in_reader.readLine()!=null)){
    		String input_ = in_reader.readLine();
    		System.out.println("MARK1");
			System.out.println(input_);
			System.out.println("MARK2");
			if(in_reader.ready()){
				System.out.println("*****************");
				return;
			}else if (input_.contains("550")){
				//System.out.println("550 invalid database, use SHOW DB for list");
			}
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return;
		
	}

	private static void setDict(String[] split) {
		// TODO Auto-generated method stub
		if(!quit_or_open_state){
		if(split!=null){
		dict_setting_ = split[1]+" ";
		System.out.println("dictionary has been set to "+split[1]);
		return;
		   }else{
			   System.out.println("902 Invalid argument ");
			   return;
			   
		   }
		}else{
			System.out.println("903 Supplied command not expected at this time");
			return;
			
		}
		
		
	}

	// use for cmd "match WORD" means "match all exact WORD"
    private static void matchWord(String[] split) {
		// TODO Auto-generated method stub
    	String theWord = split[1];
    	String match_ = "match ";
    	String match_database_ = match_.concat(dict_setting_);
    	String match_database_exact_ = match_database_.concat("exact ");
    	String match_cmd = match_database_exact_.concat(theWord);
    	System.out.println(match_cmd);
    	out_writer.println(match_cmd);
    	out_writer.flush();
    	try {
    		while((in_reader.readLine()!=null)){
    		String input_ = in_reader.readLine();
    		System.out.println("MARK1");
			System.out.println(input_);
			System.out.println("MARK2");
			if(in_reader.ready()){
				System.out.println("250 received");
				return;
			}else if (input_.contains("550")){
				//System.out.println("550 invalid database, use SHOW DB for list");
			}
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return;
		
	}

	private static void closeDict() {
		// TODO Auto-generated method stub
		try {
			dict_client.close();
			in_reader.close();
			out_writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("no client connection");
		} catch (NullPointerException e){
			
		}
		quit_or_open_state = true; // change state only allow quit or open 
		DEFAULT_PORT_NUMBER = 2638;// close connection and reset Default value 
	}

	private static void defineWord(String[] split) {
		// TODO Auto-generated method stub
    	String theWord = " "+split[1];
    	String define_ = "define ";
    	String define_database = define_.concat(dict_setting_);
    	String def_cmd = define_database.concat(theWord);
    	System.out.println(def_cmd);
    	out_writer.println(def_cmd);
    	out_writer.flush();
    	try {
    		while((in_reader.readLine()!=null)){
    		String input_ = in_reader.readLine();
    		System.out.println("MARK1");
			System.out.println(input_);
			System.out.println("MARK2");
			if(input_.contains("250")){
				System.out.println("250 received");
				return;
			}else if (input_.contains("550")){
				//System.out.println("550 invalid database, use SHOW DB for list");
			}
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return;
		
	}

	// "dict" retrive and print the list of all the dictionaries the server supports 
	// basically "show db" 
	public static void showAllDict()  {
        String output_ = "show databases ";
    	System.out.println(output_);
    	out_writer.println(output_);
    	out_writer.flush();
    	try {
    		while((in_reader.readLine()!=null)){
    		String input_ = in_reader.readLine();
    		System.out.println("MARK1");
			System.out.println(input_);
			System.out.println("MARK2");
			if(input_.contains("250")){
				System.out.println("250 received");
				return;
			}else if (input_.contains("550")){
				//System.out.println("550 invalid database, use SHOW DB for list");
			}
    		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return;
		
		
	}

	public static void buildConnection(String[] split)  {
		
		int port_num = 0;
		String server_name = split[1];
		if(split.length == 4){
			port_num =  Integer.parseInt(split[2]);
			}
		else if(split.length == 3){
			port_num = DEFAULT_PORT_NUMBER;
		}
		else{
			System.out.println("901 Incorrect number of grguments");// split length should be 3 or 4 for open command
			return;
		}
		
			try {
				dict_client = new Socket(server_name, port_num);
				in_reader = new BufferedReader(new InputStreamReader(dict_client.getInputStream()));
				out_writer = new PrintWriter(dict_client.getOutputStream(),true);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("902 Invalid argument"); // incorrect host name 
				System.out.println("Unknown Host");
			} catch (IOException e1){
				System.out.println("IOException?");
			}
		  if(in_reader != null){
			  try {
				System.out.println(in_reader.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Cannot Read");
			}
		  }
	
		
		
		
	}
}
