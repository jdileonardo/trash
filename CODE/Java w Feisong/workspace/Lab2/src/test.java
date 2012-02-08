import java.util.Scanner;

public class test {

	public static void main(String args[]) {
		main.Comparison(args[0].toUpperCase(),args[1].toUpperCase());
		
		int quit = 0;
		while(quit == 0)
		{
		Scanner keyboard = new Scanner(System.in);
		String seq1;
		String seq2;
		System.out.println("\n-------");
		System.out.println("Input 1 Sequence or type QUIT");
		seq1 = keyboard.next();
		if(seq1.toUpperCase().equals("QUIT"))
		{
			quit = 1;
			break;
		}
		System.out.println("Type Next Sequence");
		seq2 = keyboard.next();
		main.Comparison(seq1.toUpperCase(),seq2.toUpperCase());
		}
	  }
	}

