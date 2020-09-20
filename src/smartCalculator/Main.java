package smartCalculator;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static final Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		String input = sc.nextLine();
		
		while(!input.equals("/exit")) {
			if (input.equals("/help"))
				System.out.println("The program calculates the sum of numbers");
			else if (!input.trim().equals("")) {
				Pattern ptRmSpace = Pattern.compile("\\s+");
				Matcher mt = ptRmSpace.matcher(input);
				String[] arr = mt.replaceAll(" ").split(" ");
				System.out.println(arth(arr));
			}
			input = sc.nextLine();
		}
		System.out.println("Bye!");
	}
	
	static int arth(String ...arr) {
		int s = Integer.parseInt(arr[0]);
		for (int i = 1; i < arr.length; i++) {
			if (arr[i].charAt(0) == '-' && arr[i].length() % 2 == 1) 
				s -= Integer.parseInt(arr[++i]);
			else 
				s += Integer.parseInt(arr[++i]);
		}
		return s;
	}
}
