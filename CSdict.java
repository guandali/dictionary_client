
import java.lang.System;
import java.io.IOException;

//
// This is an implementation of a simplified version of a command 
// line ftp client. The program takes no arguments.
//


public class CSdict
{
    static final int MAX_LEN = 255;
    static final int PERMITTED_ARGUMENT_COUNT = 1;
    static Boolean debugOn = false;
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
		if (len <= 0) 
		    break;
		// Start processing the command here.
		
		// command for open
		if(cmdString.equals("open")){
			
		}
		// command for dict
		else if(cmdString.equals("dict")){
			
		}
		// command for set
		else if(cmdString.equals("set")){
			
		}
		// command for define
		else if(cmdString.equals("define")){
			
		}
		// command for match
		else if(cmdString.equals("match")){
			
		}
		// command for prefixmatch
		else if(cmdString.equals("prefixmatch")){
			
		}
		// command for close
		else if(cmdString.equals("close")){
			
		}
		// command for quit
		else if(cmdString.equals("quit")){
			
		}
		else{
		System.out.println("900 Invalid command.");
	    }
	    }
	} catch (IOException exception) {
	    System.err.println("998 Input error while reading commands, terminating.");
	}
    }
}
