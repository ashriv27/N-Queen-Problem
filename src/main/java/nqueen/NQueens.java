/**
 *
 * @author Aviral
 */
package nqueen;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class NQueens {

	public static String minimumOne(ArrayList<Integer> arr) {
		String s = "";
		for (int i = 0; i < arr.size(); i++) {
			s += " " + arr.get(i).toString();
		}
		s += " 0\n";
		return s;
	}

	public static String maximumOne(ArrayList<Integer> arr) {
		String s = "";
		for (int i = 0; i < arr.size(); i++) {
			for (int j = arr.indexOf(arr.get(i)) + 1; j < arr.size(); j++) {
				s += " -" + arr.get(i).toString() + " -" + arr.get(j).toString() + " 0\n";
			}
		}

		return s;
	}

	public static String onlyOne(ArrayList<Integer> arr) {
		String s = "";
		s += minimumOne(arr);
		s += maximumOne(arr);
		return s;
	}

	public static int queenPosition(int row, int column, int N) {
		int positionValue = row * N + column + 1;
		return positionValue;
	}

	// appending comments in string
	public static String appendData(String s, int N, int boardSize, int count) {
		StringBuilder str = new StringBuilder();
		str.append("c " + N + "-Queen CNF Solution \n");
		str.append("c number of positions in board = " + boardSize + "\n");
		str.append("p cnf " + boardSize + " " + count + "\n\n");
		str.append(s);
		return str.toString();
	}

	public static String positionOnConditionBasis(String str, int N) {
		// Constraint 1: queen in row
		for (int i = 0; i < N; i++) {
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for (int j = 0; j < N; j++) {
				int position = queenPosition(i, j, N);
				arr.add(position);
			}
			str += onlyOne(arr);
		}

		// Constraint 2: queen in column
		for (int j = 0; j < N; j++) {
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for (int i = 0; i < N; i++) {
				int pos = queenPosition(i, j, N);
				arr.add(pos);
			}
			str += onlyOne(arr);
		}

		// Constraint 3: queen in left negative diagonal
		for (int i = N - 1; i > -1; i--) {
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for (int j = 0; j < N - i; j++) {
				arr.add(queenPosition(i + j, j, N));
			}
			str += maximumOne(arr);
		}

		// Constraint 4: queen in top of negative diagonal
		for (int j = 1; j < N; j++) {
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for (int i = 0; i < N - j; i++) {
				arr.add(queenPosition(i, j + i, N));
			}
			str += maximumOne(arr);
		}

		// Constraint 5: queen in right positive diagonal
		for (int i = N - 1; i > -1; i--) {
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for (int j = 0; j < N - i; j++) {
				arr.add(queenPosition(i + j, N - 1 - j, N));
			}
			str += maximumOne(arr);
		}

		// Constraint 6: queen in top of positive diagonal
		for (int j = N - 2; j > -1; j--) {
			ArrayList<Integer> arr = new ArrayList<Integer>();
			for (int i = 0; i < j + 1; i++) {
				arr.add(queenPosition(i, j - i, N));
			}
			str += maximumOne(arr);
		}
		return str;
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter value of N: ");
		int N = sc.nextInt();
		sc.close();
		if (N > 1) {

			int boardSize = N * N;
			String str = "";
			str = positionOnConditionBasis(str, N);
			int count = str.length() - str.replace("\n", "").length();
			try {
				String fileName = "input.cnf";
				FileWriter fw = new FileWriter(fileName);
				String s = appendData(str, N, boardSize, count);
				fw.write(s);
				fw.close();
				System.out.println(s);
				System.out.println("CNF file is created");
			} catch (Exception e) {
				System.out.println("error");
				e.printStackTrace();
			}

		} else {
			System.err.println("Atleast 1 Queen is required");
			return;
		}
	}

}
