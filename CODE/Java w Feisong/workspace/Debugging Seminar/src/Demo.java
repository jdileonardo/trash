import java.util.Random;
import java.util.Scanner;


public class Demo {
	// TODO Auto-generated constructor stub
public static void main (String[] args){

	Random rnd = new Random();
	Scanner scanner = new Scanner(System.in);
	for(int i = 0; i < 100; i++){
		System.out.println("My random number is : " + rnd.nextInt());
	}
	String s = scanner.nextLine();
}

}
