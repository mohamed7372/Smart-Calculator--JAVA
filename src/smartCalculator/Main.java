package smartCalculator;

import java.util.ArrayDeque;
import java.util.Deque;
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
				System.out.println("The program calculates the sum, munis, mult, div of numbers");
			else if (input.matches("/.*"))
				System.out.println("Unknown command");
			else if (input.matches(".*=.*")) 
				declarationVar(input.trim());
			else if (input.trim().isEmpty())
				System.out.print("");
			else if(input.trim().split(" ").length == 1 && !input.matches(".*([+]|-|[*]|/|[)]|[(]).*")) 
				System.out.println(recupereValue(input.trim()));
			else if (!input.trim().equals("")) {
				String res = postFixe(input);
				System.out.println(res.equals("Invalid expression") ? "Invalid expression" : (answer(res).equals("Unknown variable")?
						"Unknown variable" : answer(res)));
			}
			input = sc.nextLine();
		}
		System.out.println("Bye!");
	}
	
	// recupere val of var
	static String recupereValue(String str) {
		if(str.matches("[a-zA-Z]+") && variables.get(str) != null) 
			return  String.valueOf(variables.get(str));
		else if (str.matches("\\d+"))
			return str;
		return "Unknown variable";
	}
	// declaration variables
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
				else if (arr[2].matches("[-]{0,1}\\d+"))
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
	// convert infixe to postfixe
	static String postFixe(String s) {
		if(correctExpression(s).equals("Invalid expression"))
			return "Invalid expression";
		s = correctExpression(s);
		Deque<String> operater = new ArrayDeque<String>();
		String str[] = s.split(" ");
		String res = "";//str[0] + " ";
		for (int i = 0; i < str.length; i++) {
			// + - * /
			if(str[i].matches("([+]|-|[*]|/|[)]|[(])")) {
				// meme priorite
				if(operater.isEmpty()) 
					operater.offerFirst(str[i]);
				else {
					// add operande
					if(priorite(operater.peekFirst(), str[i]) >= 1) 
						operater.offerFirst(str[i]);
					else if(priorite(operater.peekFirst(), str[i]) <= 0) {
						if(!operater.peekFirst().matches("([(]|[)])")) {
							do {
								res += operater.pollFirst() + " ";
								if(operater.isEmpty() || operater.peekFirst().matches("([(]|[)])"))
									break;
							}while(priorite(operater.peekFirst(), str[i]) <= 0);
							operater.offerFirst(str[i]);
						}
					}
					if(str[i].equals(")")) {
						operater.pollFirst();
						while(!operater.peekFirst().equals("(") && !operater.isEmpty()) 
							res += operater.pollFirst() + " ";
						if(operater.peekFirst().equals("("))
							operater.pollFirst();
					}
				}
			}
			// variables
			else if(str[i].matches("[a-zA-Z0-9]+")) 
				res += str[i] + " ";
		}
		while(!operater.isEmpty()) {
			res += operater.pollFirst() + " ";
		}
		return res.trim();
	}
	static int priorite(String deque, String str) {
		if(deque.matches("([*]|/)") && str.matches("([+]|-)"))
			return -1;
		else if(deque.matches("([+]|-)") && str.matches("([*]|/)")) 
			return 1;
		else if(str.matches("([(]|[)])"))
			return 2;
		else if(deque.matches("([(]|[)])") && str.matches("([*]|/|[+]|-)")) 
			return 3;
		return 0;
	}
	// convert postfixe to answer
	static String answer(String s) {
		String str[] = s.trim().split(" ");
		Deque<Integer> nbr = new ArrayDeque<Integer>();
		int a,b;
		for (int i = 0; i < str.length; i++) {
			if(str[i].matches("\\w+")) {
				if(recupereValue(str[i]).equals("Unknown variable"))
					return "Unknown variable";
				nbr.offerFirst(Integer.valueOf(recupereValue(str[i])));
			}
			else {
				a = nbr.pollFirst();
				b = nbr.pollFirst();
				switch (str[i]) {
				case "+":
					nbr.offerFirst(a + b);
					break;
				case "-": 
					nbr.offerFirst(b - a);
					break;
				case "*": 
					nbr.offerFirst(a * b);
					break;
				case "/": 
					nbr.offerFirst(b / a);
					break;	
				default:
					System.out.println("Error operation");
				}
			}
		}
		return "" + nbr.peekFirst();
	}
	// convert vers correct arth
	static String correctExpression(String str) {
		if(checkErr(str).equals("Invalid expression"))
			return checkErr(str);
		str = checkErr(str);
		// make space
		for (int i = 0; i < str.length(); i++) {
			if(str.substring(i,i+1).matches("([+]|-|[*]|/|[)]|[(])")) {
				str = str.substring(0,i) + " " + str.charAt(i) + " " + str.substring(++i);
				i++;
			}
		}
		// delete all space
		Pattern ptRmSpace = Pattern.compile("\\s+");
		Matcher mt = ptRmSpace.matcher(str);
		str = mt.replaceAll(" ");
		// check right expr
		ptRmSpace = Pattern.compile("(\\w+\\s\\w+|([+]|-|[*]|/)\\s([+]|-|[*]|/)|\\w+\\s[(]|[)]\\s\\w+|[*]{2,}|/{2,})");
		mt = ptRmSpace.matcher(str);
		if(mt.find())
			return "Invalid expression";
		return str;
	}
	// checkin all errors
	static String checkErr(String str) {
		// remove plus
		Pattern pt = Pattern.compile("[+]+");
		Matcher mt = pt.matcher(str);
		str = mt.replaceAll("+");
		// remove minus
		int nbr = 0;
		// remove munis
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == '-')
				nbr++;
			else {
				if(nbr >= 2 && nbr % 2 == 0)
					str = str.substring(0,i - nbr) + "+ " + str.substring(i + 1);
				else if (nbr >= 2 && nbr % 2 == 1)
					str = str.substring(0,i - nbr) + "- " + str.substring(i + 1);
				nbr = 0;
			}
		}
		// for brackets
		int bracketsR = 0, bracketsL = 0;
		for (int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == ')')
				bracketsR++;
			if(str.charAt(i) == '(')
				bracketsL++;
		}
		if(bracketsL != bracketsR)
			return "Invalid expression";
		return str;
	}
}
