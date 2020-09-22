package smartCalculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public static final Scanner sc = new Scanner(System.in);
	public static Map<String,Integer> variables;
	
	public static void main(String[] args) {
		variables = new HashMap<String, Integer>();
		String input = sc.nextLine();
		
		while(!input.equals("/exit")) {
			if (input.equals("/help"))
				System.out.println("The program calculates the sum of numbers");
			else if (input.matches("/.*"))
				System.out.println("Unknown command");
			else if (input.matches(".*=.*")) 
				declarationVar(input.trim());
			else if (!input.trim().equals("")) {
				Pattern ptRmSpace = Pattern.compile("\\s+");
				Matcher mt = ptRmSpace.matcher(input);
				arth(mt.replaceAll(" ").split(" "));
			}
			input = sc.nextLine();
		}
		System.out.println("Bye!");
	}
	
	static void arth(String ...arr) {
		Pattern pt = Pattern.compile("[-+]{0,1}\\w+");
		Matcher mt = pt.matcher(arr[0]);
		boolean err = false, err2 = false;
		int s = 0;
		if(mt.matches()) {
			if(recupereValue(arr[0]) == "Unknown variable")
				err2 = true;
			else
				s = Integer.parseInt(recupereValue(arr[0]));
			for (int i = 1; i < arr.length; i++) {
				switch (arr[i].charAt(0)) {
				case '-':
					if(arr[i].length() % 2 == 1) {
						if(recupereValue(arr[0]) == "Unknown variable")
							err2 = true;
						else
							s -= Integer.parseInt(recupereValue(arr[++i]));
						break;
					}
				case '+':
					if(recupereValue(arr[0]) == "Unknown variable")
						err2 = true;
					else
						s += Integer.parseInt(recupereValue(arr[++i]));
					break;
				default:
					err = true;
				}
			}
		}
		else
			err = true;
		
		if(err)
			System.out.println("Invalid expression");
		else if (err2)
			System.out.println("Unknown variable");
		else
			System.out.println(s);
	}
	static String recupereValue(String str) {
		if(str.matches("[a-zA-Z]+") && variables.get(str) != null) 
			return  String.valueOf(variables.get(str));
		else if (str.matches("\\d+"))
			return str;
		return "Unknown variable";
	}
	
	static void declarationVar(String dec) {
		Pattern pt = Pattern.compile("\\s+");
		Matcher mt = pt.matcher(dec);
		dec = mt.replaceAll("");
		//make space between equal
		pt = Pattern.compile("=");
		mt = pt.matcher(dec);
		dec = mt.replaceAll(" = ");
		//divise string to parties
		String arr[] = dec.split(" ");
		if(arr.length == 3) {
			if(arr[0].matches("[a-zA-Z]+") && arr[1].equals("=") && arr.length == 3) {
				if(arr[2].matches("[a-zA-Z]+") && variables.get(arr[2]) != null)
					variables.put(arr[0], variables.get(arr[2]));
				else if (arr[2].matches("\\d+"))
					variables.put(arr[0], Integer.valueOf(arr[2]));
				else
					System.out.println("Invalid assignment");
			}
			else if (arr[0].matches("(.*\\d.*|[^a-zA-Z]+)"))
				System.out.println("Invalid identifier");
		}
		else
			System.out.println("Invalid assignment");
	}
}
