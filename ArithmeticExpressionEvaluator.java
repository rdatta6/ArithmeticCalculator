package com.company;
import java.util.*;


public class ArithmeticExpressionEvaluator {
    String Exp = new String(); //initilaize postfix expression
    ArrayList<String> Components = new ArrayList<>();//store postfix operators

    public static boolean LowerPrecedence(char op1, char op2)//check precedence of one operator over current
    {
        if(op1 == op2){
            return false;
        }
        switch (op1)
        {
            case '+':
            case '-':
                return !(op2 == '+' || op2 == '-');

            case '*':
            case '/':
                return (op2 == '^' || op2 == '(');

            case '^':
                return op2 == '(';

            case '(':
                return true;

            default:
                return false;
        }
    }

    public boolean isOperator(String x){//check if operator
        if (!x.equals("-") && !x.equals("+") && !x.equals("*") && !x.equals("/") && !x.equals("^")){
            return false;
            }
        else{
            return true;
        }
    }

    public String InfixtoPostfix(){
        System.out.println("Enter your expression, with spaces between all characters that aren't part of the same number, and a space at the end");
        System.out.println("Make sure all negative numbers x are represented as ( 0 - x ):");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.print("Your infix input was: ");
        System.out.println(input);
        Stack<String> oper = new Stack<>();//stack for operators

        String[] InfixArray = input.split(" ");//split expression into components

        for(int i = 0; i < InfixArray.length; i++){
            if (isOperator(InfixArray[i])) {//if we encounter an operator
                while(!oper.empty() && (!oper.peek().equals("(")) && LowerPrecedence(InfixArray[i].charAt(0),oper.peek().charAt(0))){
                Exp = Exp + oper.pop() + " ";//if the oper Stack has content and we do not encounter a parenthesis,
                    // we add the previous operator the Postfix expression if we encounter a lower precedence operator
                }
                oper.push(InfixArray[i]);//push the operator on to the stack
        }
            else if (InfixArray[i].equals("(")){
                oper.push(InfixArray[i]);
            }
            else if (InfixArray[i].equals(")")){//if we encounter a closing parenthesis (which is highest priority, we immediately
                //add to the postfix expression
                while(!oper.empty() && (!oper.peek().equals("("))){
                    Exp = Exp + oper.pop() + " ";
                }
               oper.pop();
            }
            else {//we have encountered a number, and we add to the postfix expression
                Exp = Exp + InfixArray[i] + " ";
            }
        }
        while(!oper.empty()){
            Exp = Exp + oper.pop() + " ";
        }
        String[] Postfixarray = Exp.split(" ");
        for (int i = 0; i < Postfixarray.length; i++){
            Components.add(Postfixarray[i]);//initialize the postfix ArrayList
        }
        System.out.println("The postfix output is: ");
        System.out.println(Exp);
        return Exp;
    }

    public void EvaluatePostfix(){
        Stack<Double> vals = new Stack<Double>();
        for (int i = 0; i < Components.size(); i++){//different cases based on operator
            if (Components.get(i).equals("+")){
                double v = vals.pop();
                v = v + vals.pop();
                vals.push(v);
            }
            else if (Components.get(i).equals("-")){
                double v = vals.pop();
                v = vals.pop() - v;
                vals.push(v);
            }
            else if (Components.get(i).equals("*")){
                double v = vals.pop();
                v = v * vals.pop();
                vals.push(v);
            }
            else if (Components.get(i).equals("/")){
                double v = vals.pop();
                v = vals.pop()/v;
                vals.push(v);
            }
            else if (Components.get(i).equals("^")){
                double v = vals.pop();
                v = Math.pow(vals.pop(),v);
                vals.push(v);
            }
            else {vals.push(Double.parseDouble(Components.get(i)));}//push if encountering operand
        }
        System.out.println("The evaluated expression is: ");
        System.out.print(vals.pop());
    }

    public static void main(String[] args) {
        ArithmeticExpressionEvaluator Simulator = new ArithmeticExpressionEvaluator();
        Simulator.InfixtoPostfix();
        Simulator.EvaluatePostfix();
    }
}
// OUTPUT:/Library/Java/JavaVirtualMachines/jdk-10.0.1.jdk/Contents/Home/bin/java "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=58459:/Applications/IntelliJ IDEA CE.app/Contents/bin" -Dfile.encoding=UTF-8 -classpath /Users/rohandatta/IdeaProjects/Question2/out/production/Question2 com.company.ArithmeticExpressionEvaluator
//Enter your expression, with spaces between all characters that aren't part of the same number, and a space at the end
//Make sure all negative numbers x are represented as ( 0 - x ):
//( 10 + 2 ) * 3
//Your infix input was: ( 10 + 2 ) * 3
//The postfix output is:
//10 2 + 3 *
//The evaluated expression is:
//36.0
//Process finished with exit code 0

