/**
 * 
 */
package code.test;

/**
 * Find possible values for 
 * a, b, c, d where 
 * a OP1 b = w, c OP4 d = x, 
 * a OP2 c = y, b OP3 d = z, 
 * if w, x, y, z are given. 
 * OP1, OP2, OP3 and OP4 can be either "+" (Addition) or "-" (Subtraction).
 * This puzzle is commonly known as "a+b=8 Box Puzzle"
 * 
 * @author Pratik
 *
 */
public class ab_8_maths_box_puzzle_2 {
	
	//To Find
	private static float a = 0.0f, b = 0.0f, c = 0.0f, d = 0.0f;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Given
		float w = 8.0f, x = 6.0f, y = 13.0f, z = 8.0f;
		String operation1 = "-", operation2 = "+", operation3 = "+", operation4 = "-";
		
		calculateSolution(w, x, y, z, operation1, operation2, operation3, operation4);
	}

	private static void calculateSolution(float w, float x, float y, float z, 
			String operation1, String operation2, String operation3, String operation4) {
		boolean canSolve = false;
		
		//Calculate required equations
		equation_format e1;
		equation_format e2;
		equation_format e3;
		equation_format e4;
		equation_format e1_sum_e2;
		equation_format e3_sum_e4;
		equation_format e1_sum_e2__sum__e3_sum_e4;
		equation_format e1_sum_e2__diff__e3_sum_e4;
		
		e1 = new equation_format(1, getByOperation(1, operation1), 0, 0, w);
		e2 = new equation_format(0, 0, 1, getByOperation(1, operation4), x);
		e3 = new equation_format(1, 0, getByOperation(1, operation1), 0, y);
		e4 = new equation_format(0, 1, 0, getByOperation(1, operation1), z);
		
		e1_sum_e2 = addEquations(e1, e2);
		e3_sum_e4 = addEquations(e3, e4);
		
		e1_sum_e2__sum__e3_sum_e4 = addEquations(e1_sum_e2, e3_sum_e4);
		e1_sum_e2__diff__e3_sum_e4 = subEquations(e1_sum_e2, e3_sum_e4);
		
		//Determine if equations can be solved
		if(canSolveEquation(e1_sum_e2__sum__e3_sum_e4)) {
			solveEquation(e1_sum_e2__sum__e3_sum_e4);
			canSolve = true;
		} else if(canSolveEquation(e1_sum_e2__diff__e3_sum_e4)) {
			solveEquation(e1_sum_e2__diff__e3_sum_e4);
			canSolve = true;
		}
		
		//Solve the equations
		if(canSolve) {
			int iterationCount = 0;			
			while( (a == 0 || b == 0 || c == 0 || d == 0) && (iterationCount <= 3) ) {
				if(a != 0) {
					if(b == 0) {	b = getByOperation(returnOperationResult("-", w, a), operation1);	}
					if(c == 0) {	c = getByOperation(returnOperationResult("-", y, a), operation2);	}
				}
				if(b != 0) {
					if(a == 0) {	a = returnReverseOperationResult(operation1, w, b);					}
					if(d == 0) {	d = getByOperation(returnOperationResult("-", z, b), operation3);	}
				}
				if(c != 0) {
					if(d == 0) {	d = getByOperation(returnOperationResult("-", x, c), operation4);	}
					if(a == 0) {	a = returnReverseOperationResult(operation2, y, c);					}
				}
				if(d != 0) {
					if(c == 0) {	c = returnReverseOperationResult(operation4, x, d);					}
					if(b == 0) {	b = returnReverseOperationResult(operation3, z, d);					}
				}
			}
		}
		
		printSolution(w, x, y, z, operation1, operation2, operation3, operation4, canSolve);
		
	}
	
	private static equation_format addEquations(equation_format equation1, equation_format equation2) {
		return new equation_format(	equation1.coefficient_a + equation2.coefficient_a, 
									equation1.coefficient_b + equation2.coefficient_b, 
									equation1.coefficient_c + equation2.coefficient_c, 
									equation1.coefficient_d + equation2.coefficient_d, 
									equation1.coefficient_answer + equation2.coefficient_answer);
	}
	
	private static equation_format subEquations(equation_format equation1, equation_format equation2) {
		return new equation_format(	equation1.coefficient_a - equation2.coefficient_a, 
									equation1.coefficient_b - equation2.coefficient_b, 
									equation1.coefficient_c - equation2.coefficient_c, 
									equation1.coefficient_d - equation2.coefficient_d, 
									equation1.coefficient_answer - equation2.coefficient_answer);
	}
	
	private static boolean canSolveEquation(equation_format equation) {
		int count_zero_values = 0;
		if(equation.coefficient_a == 0)		count_zero_values++;
		if(equation.coefficient_b == 0)		count_zero_values++;
		if(equation.coefficient_c == 0)		count_zero_values++;
		if(equation.coefficient_d == 0)		count_zero_values++;
		
		if(count_zero_values == 3)	return true;
		return false;
	}
	
	private static void solveEquation(equation_format equation) {
		if(equation.coefficient_a != 0)			a = equation.coefficient_answer / equation.coefficient_a;
		else if(equation.coefficient_b != 0)	b = equation.coefficient_answer / equation.coefficient_b;
		else if(equation.coefficient_c != 0)	c = equation.coefficient_answer / equation.coefficient_c;
		else if(equation.coefficient_d != 0)	d = equation.coefficient_answer / equation.coefficient_d;
	}
	
	private static float returnOperationResult(String operation, float operand1, float operand2) {
		switch (operation) {
			case "+":	return add(operand1, operand2);
			case "-":	return sub(operand1, operand2);
			default:	return -999.0f;
		}
	}
	
	private static float returnReverseOperationResult(String operation, float operand1, float operand2) {
		switch (operation) {
			case "+":	return sub(operand1, operand2);
			case "-":	return add(operand1, operand2);
			default:	return -999.0f;
		}
	}
	
	private static float add(float operand1, float operand2) {	return operand1 + operand2;	}
	
	private static float sub(float operand1, float operand2) {	return operand1 - operand2;	}
	
	private static int getByOperation(int operand, String operation) {
		switch (operation) {
			case "-":	return -operand;
			default:	return operand;
		}
	}
	
	private static float getByOperation(float operand, String operation) {
		switch (operation) {
			case "-":	return -operand;
			default:	return operand;
		}
	}
	
	private static void printSolution(float w, float x, float y, float z, String operation1, String operation2, String operation3, String operation4, boolean isSolution) {
		if(isSolution) {
			System.out.printf("%.1f\t%s\t%.1f\t=\t%.1f\n", a, operation1, b, w);
			System.out.printf(" %s\t\t %s\n", operation2, operation3);
			System.out.printf("%.1f\t%s\t%.1f\t=\t%.1f\n", c, operation4, d, x);
			System.out.println(" =\t\t =");
			System.out.printf("%.1f\t\t%.1f", y, z);
		} else {
			System.out.printf("[ ]\t%s\t[ ]\t=\t%.1f\n", operation1, w);
			System.out.printf(" %s\t\t %s\n", operation2, operation3);
			System.out.printf("[ ]\t%s\t[ ]\t=\t%.1f\n", operation4, x);
			System.out.println(" =\t\t =");
			System.out.printf("%.1f\t\t%.1f", y, z);
			System.out.println();
			System.out.println();
			System.out.println("The problem has infinite solutions !!!");
		}
	}

}

class equation_format {
	int coefficient_a = 0;
	int coefficient_b = 0;
	int coefficient_c = 0;
	int coefficient_d = 0;
	float coefficient_answer = 0;
	
	public equation_format() {}
	
	public equation_format(int coefficient_a, int coefficient_b, int coefficient_c, int coefficient_d, float coefficient_answer) {
		super();
		this.coefficient_a = coefficient_a;
		this.coefficient_b = coefficient_b;
		this.coefficient_c = coefficient_c;
		this.coefficient_d = coefficient_d;
		this.coefficient_answer = coefficient_answer;
	}
}
