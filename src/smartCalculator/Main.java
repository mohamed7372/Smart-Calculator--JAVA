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
			else if (input.matches("/.*"))
				System.out.println("Unknown command");
			else if (!input.trim().equals("")) {
				Pattern ptRmSpace = Pattern.compile("\\s+");
				Matcher mt = ptRmSpace.matcher(input);
				String[] arr = mt.replaceAll(" ").split(" ");
				arth(arr);
			}
			input = sc.nextLine();
		}
		System.out.println("Bye!");
	}
	
	static void arth(String ...arr) {
		Pattern pt = Pattern.compile("[-+]{0,1}\\d+");
		Matcher mt = pt.matcher(arr[0]);
		boolean err = false;
		int s = 0;
		if(mt.matches()) {
			s = Integer.parseInt(arr[0]);
			for (int i = 1; i < arr.length; i++) {
				switch (arr[i].charAt(0)) {
				case '-':
					if(arr[i].length() % 2 == 1) {
						s -= Integer.parseInt(arr[++i]);
						break;
					}
				case '+':
					s += Integer.parseInt(arr[++i]);
					break;
				default:
					err = true;
				}
			}
		}
		else
			err = true;
		
		System.out.println(err ? "Invalid expression" : s);
	}
}
