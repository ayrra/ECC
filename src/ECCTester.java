import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;


public class ECCTester {

	public static void main(String[] args) throws IOException {
		
		if (args.length != 2) // Test for correct # of arguments
			throw new IllegalArgumentException("Parameter: <filename> <testfiletocompareto>");
		openFile(args[0], args[1]);	//this function does all the work
	}

	public static void openFile(String fileName, String testFile) throws IOException {
		File f = new File(fileName);
		FileReader fr = new FileReader(new File(testFile));
		BufferedReader br = new BufferedReader(fr);
		
		FileInputStream in = new FileInputStream(fileName);
		byte[] buf = new byte[16];	//create temporary buffer for 16 bits
		byte[] bu1 = new byte[8];	//these buffers hold the index's values, for example we have ABCDEFGHIJKLMNOP each represents a bit
		byte[] bu2 = new byte[8];	// bu1 will hold AAAAAAAA while bu2 will hold BBBBBBBB etc...
		byte[] bu3 = new byte[8];
		byte[] bu4 = new byte[8];
		byte[] bu5 = new byte[8];
		byte[] bu6 = new byte[8];
		byte[] bu7 = new byte[8];
		byte[] bu8 = new byte[8];
		byte[] bu9 = new byte[8];
		byte[] bu10 = new byte[8];
		byte[] bu11 = new byte[8];
		byte[] bu12 = new byte[8];
		byte[] bu13 = new byte[8];
		byte[] bu14 = new byte[8];
		byte[] bu15 = new byte[8];
		byte[] bu16 = new byte[8];
		String s1 = "";	//Strings for outputting
		String s2 = "";
		String s3 = "";
		String s4 = "";
		String s5 = "";
		String s6 = "";
		String s7 = "";
		String s8 = "";
		String s9 = "";
		String s10 = "";
		String s11 = "";
		String s12 = "";
		String s13 = "";
		String s14 = "";
		String s15 = "";
		String s16 = "";
		int[] values = new int[2];
	
		for (int xy = 0; xy < (f.length()/129); xy++) {
			Arrays.fill(buf,(byte) 0);		//we clear the arrays each time we iterate to prevent possible errors just incase
			Arrays.fill(bu1, (byte) 0);
			Arrays.fill(bu2, (byte) 0);
			Arrays.fill(bu3, (byte) 0);
			Arrays.fill(bu4, (byte) 0);
			Arrays.fill(bu5, (byte) 0);
			Arrays.fill(bu6, (byte) 0);
			Arrays.fill(bu7, (byte) 0);
			Arrays.fill(bu8, (byte) 0);
			Arrays.fill(bu9, (byte) 0);
			Arrays.fill(bu10, (byte) 0);
			Arrays.fill(bu11, (byte) 0);
			Arrays.fill(bu12, (byte) 0);
			Arrays.fill(bu13, (byte) 0);
			Arrays.fill(bu14, (byte) 0);
			Arrays.fill(bu15, (byte) 0);
			Arrays.fill(bu16, (byte) 0);
			
		for (int i = 0; i < 8; i++) {
			in.read(buf, 0, 16);		//read 16 bytes from file
			bu1[i] = buf[0];			//8 times we will store the 16 bit values to their corresponding array
			bu2[i] = buf[1];
			bu3[i] = buf[2];
			bu4[i] = buf[3];
			bu5[i] = buf[4];
			bu6[i] = buf[5];
			bu7[i] = buf[6];
			bu8[i] = buf[7];
			bu9[i] = buf[8];
			bu10[i] = buf[9];
			bu11[i] = buf[10];
			bu12[i] = buf[11];
			bu13[i] = buf[12];
			bu14[i] = buf[13];
			bu15[i] = buf[14];
			bu16[i] = buf[15];	
			
			s1 = majority(bu1);		//We perform majority vote on each of the 16 bits, each array holds 8 bits, we do majority vote of 5/8 to write 1, 0 or U.
			s2 = majority(bu2);
			s3 = majority(bu3);
			s4 = majority(bu4);
			s5 = majority(bu5);
			s6 = majority(bu6);
			s7 = majority(bu7);
			s8 = majority(bu8);
			s9 = majority(bu9);
			s10 = majority(bu10);
			s11 = majority(bu11);
			s12 = majority(bu12);
			s13 = majority(bu13);
			s14 = majority(bu14);
			s15 = majority(bu15);
			s16 = majority(bu16);
		}	
		String s = s1 + s2 + s3 + s4 + s5 + s6 + s7 + s8 + s9 + s10 + s11 + s12 + s13 + s14 + s15 + s16; //we combine the 16 strings into our 16 bit string
		System.out.println(s);
		values = compareTo(s, br, values[0], values[1]);	
		in.read();	//we use this to offset the newline byte every 16*8 bytes
		}
		float percent = (values[0] * 100.0f)/ (values[0] + values[1]);
		System.out.println("We got " + values[0] + "/" + (values[0] + values[1] + " correct"));
		System.out.print(percent + "%");
		in.close();
}
		//use this function to see how many of the created strings match to the true values provided in 100% sure file
		public static int[] compareTo(String s, BufferedReader fr, int g, int b) throws IOException {
			int[] temp = new int[2];
			if(fr.readLine().equals(s)) 
				g++;
			else 	b++;			
			temp[0] = g;
			temp[1] = b;
			return temp;
		}
	
		//This function will return majority value if there are at least 5/8 otherwise it returns a U
		public static String majority(byte[] members){
			Arrays.sort(members);
			int value = members[0];
			int count = 1;
			int tempCount = 1;
			
			for (int i=1; i<members.length;i++) {
				int previous = members[i - 1];
				int current = members[i];
				
				if (current == previous) {
					tempCount++;
				}
				if (current != previous || i == members.length - 1) {
					if (tempCount > count) {
						value = previous;
						count = tempCount;
					}
				}
			}
			if(count < 5) 
				return "U";
			if(value == 49)
				return "1";
			if (value == 48)
				return "0";
			return ""; //it should never do this
				
		}
}
