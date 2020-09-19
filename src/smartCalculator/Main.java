package smartCalculator;

import java.util.Scanner;

public class Main {

	public static final Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		String input = sc.nextLine();
		while(!input.equals("/exit")) {
			if (input.equals("/help"))
				System.out.println("The program calculates the sum of numbers");
			else if (!input.trim().equals("")) {
				String inNbr[] = input.split(" ");
				System.out.println(sum(arrStrToInt(inNbr)));
			}
			input = sc.nextLine();
		}
		System.out.println("Bye!");
	}
	
	static int sum(int ...n) {
		int s = 0;
		for (int i : n) {
			s += i;
		}
		return s;
	}
	static int[] arrStrToInt(String[] arr) {
		int[] newArr = new int[arr.length];
		for (int i = 0; i < newArr.length; i++) 
			newArr[i] = Integer.parseInt(arr[i]);
		return newArr;
	}
}
