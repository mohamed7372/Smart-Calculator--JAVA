package smartCalculator;

import java.util.Scanner;

public class Main {

	public static final Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		String input = sc.nextLine();
		while(!input.equals("/exit")) {
			if (!input.trim().equals("")) {
				String inNbr[] = input.split(" ");
				int sum = inNbr.length == 1 ? Integer.parseInt(inNbr[0]) : (Integer.parseInt(inNbr[0]) + Integer.parseInt(inNbr[1]));
				System.out.println(sum);
			}
			input = sc.nextLine();
		}
		System.out.println("Bye!");
	}
}
