/*
to add:
	1) else statement
	2) line which error occurred on
changed:
	1) nested statements work now
	2) userInput added
	3) now allows multi-token strings as input to asCode
	4) opening quotes = "{
	5) closing quotes = }"
	6) added \n for newline
	7) now allows multi-line strings as input to asCode
*/

import java.util.*;
import java.lang.*;
import java.util.Scanner;
import java.util.Arrays;

public class Anti_array{// Driver class
	static Scanner scan = new Scanner(System.in);
	static ArrayList<String> variableNames = new ArrayList<String>();
	static ArrayList<Object> variableValues = new ArrayList<Object>();
  public static void main (String[] args){//Driver function
  	while(true){
	  	nextLine();
	  }
  }
  static public Object getInput(){//gets user input (not code just int, bool, or String )
  	System.out.print(">>");
  	String st = "";
	  st = scan.nextLine();
	  Token t = new Token(st);
	  if(t.type.equals("bool")) return (boolean)t.value;
	  if(t.type.equals("int")) return (int)t.value;
	  if(t.type.equals("string")) return (String)t.value;
	  return new Token(false);
  }
  static ArrayList<Token> splitCode(String st){
  	ArrayList<Character> currentWord = new ArrayList<Character>();
	  ArrayList<String> wordsList = new ArrayList<String>();
	  int quotesDepth = 0;
	  for(int i = 0; i < st.length(); i++){//keeps track of when its in quotes
	  	char x = st.charAt(i);
		  if(x == '}'){
	  		if(st.charAt(Math.min(i+1, st.length()-1)) == '\"'){
		  		quotesDepth--;
		  	}
		  }
		  if(st.charAt(Math.max(i-1, 0)) != '}'){
		  	if(x == '\"'){
		  		if(st.charAt(i+1) == '{'){
			  		quotesDepth++;
			  	}
			  	else{
			  		throw new Error("invalid quotes");
			  	}
			  }
		  }
	  	if(quotesDepth == 0){//if a space appears outside of quotes end the current String
	  		if(x == ' '){
		  		if(currentWord.size() != 0){
		  			char[] tempChars = new char[currentWord.size()];
		 				for(int z = 0; z < currentWord.size(); z++) tempChars[z] = currentWord.get(z).charValue();
		  			wordsList.add(new String(tempChars));
		  			currentWord = new ArrayList<Character>();
		  		}
	  		}
	  		else currentWord.add(x);//add the current character when not in quotes and the current character is not a space
	  	}
	  	else currentWord.add(x);//add the current character when in quotes
	  }

	  char[] tempChars = new char[currentWord.size()];//end the current word
		for(int z = 0; z < currentWord.size(); z++) tempChars[z] = currentWord.get(z).charValue();
		wordsList.add(new String(tempChars));
		currentWord = new ArrayList<Character>();

	  String[] words = wordsList.toArray(new String[wordsList.size()]);
	  Token[] tokens = new Token[words.length];
	  for(int i = 0; i < words.length; i++){//turn each word into a token
	  	//System.out.print(words[i] + ", ");
  	  if(words[i].length() != 0) tokens[i] = new Token(words[i]);
  	}
  	ArrayList<Token> tokensOut =  new ArrayList<Token>(Arrays.asList(tokens));
  	return tokensOut;
  }
  static ArrayList<Token> getNextLine(){//gets one line of code from the user and turns it into an array of tokens
  	String st = "";
	  st = scan.nextLine();
  	//System.out.println();
  	ArrayList<Token> nully = new ArrayList<Token>();
  	nully.add(new Token(false));
  	return st.length() > 0 ? splitCode(st) : nully;
  }
  static ArrayList<Token> nextLine(){//get, and run a line of code from the user
  	ArrayList<Token> tokens = getNextLine();
  	if(checkForStatements(tokens)) return new ArrayList<Token>();
  	return run(tokens);
  }
  static boolean checkForStatements(ArrayList<Token> tokens){
  	return checkForStatements(tokens, true);
  }
  static boolean checkForStatements(ArrayList<Token> tokens, boolean run){
  	return checkForStatements(tokens, run, null);
  }
  static boolean checkForStatements(ArrayList<Token> tokens, boolean run, ArrayList<ArrayList<Token>> afterStatement){//checks for multi-line statements (loops, and if statements etc.) and runs them if run is true
  	boolean modified = false;
  	ArrayList<Token> tokensCopy = new ArrayList<Token>();
	  if(tokens.size() > 0){//if it is not an empty array
	  	if(tokens.get(0).type.equals("if")){//if this is the start of a if statement
	  		ArrayList<Token> tempTokens = new ArrayList<Token>();
	  		for(int i = 1; i < tokens.size(); i++) tempTokens.add(tokens.get(i));
	  		if(run){
	  			IfStatement i = IfStatement.generateNewUserDefinedIfStatement(tempTokens, afterStatement);//create if statement
	  			i.run();//run if statement
	  		}
	  		modified = true;
	  	}
	  	if(tokens.get(0).type.equals("loop")){//if this is the start of a loop
	  		ArrayList<Token> tempTokens = new ArrayList<Token>();
  			for(int i = 1; i < tokens.size(); i++) tempTokens.add(tokens.get(i));
	  		if(run){
	  			Loop x = Loop.generateNewUserDefinedLoop(tempTokens, afterStatement);//create loop
	  			x.run();//run loop
	  		}
	  		modified = true;
	  	}
	  }
	  return modified;
  }
  static String[] splitNewLine(String st){
  	int quotesDepth = 0;
  	ArrayList<Character> currentWord = new ArrayList<Character>();
  	String[] wordsList = new String[0];
	  for(int i = 0; i < st.length(); i++){//keeps track of when its in quotes
	  	char x = st.charAt(i);
		  if(x == '}'){
	  		if(st.charAt(Math.min(i+1, st.length()-1)) == '\"'){
		  		quotesDepth--;
		  	}
		  }
		  if(st.charAt(Math.max(i-1, 0)) != '}'){
		  	if(x == '\"'){
		  		if(st.charAt(i+1) == '{'){
			  		quotesDepth++;
			  	}
			  	else{
			  		throw new Error("invalid quotes");
			  	}
			  }
		  }
	  	if(quotesDepth == 0){//if a \n appears outside of quotes end the current String
	  		if(x == '\\'){
	  			if(st.charAt(Math.min(i+1, st.length()-1)) == 'n'){
			  		if(currentWord.size() != 0){
			  			char[] tempChars = new char[currentWord.size()];
			 				for(int z = 0; z < currentWord.size(); z++) tempChars[z] = currentWord.get(z).charValue();
			 				String[] tempStrings = new String[wordsList.length+1];
			 				for(int z = 0; z < wordsList.length; z++) tempStrings[z] = wordsList[z];
			 				tempStrings[tempStrings.length-1] = new String(tempChars);
			  			wordsList = tempStrings;
			  			currentWord = new ArrayList<Character>();
			  		}
			  	}
	  		}
	  		else currentWord.add(x);//add the current character when not in quotes and the current character is not a newline
	  	}
	  	else currentWord.add(x);//add the current character when in quotes
	  }
	  if(currentWord.size() != 0){
			char[] tempChars = new char[currentWord.size()];
				for(int z = 0; z < currentWord.size(); z++) tempChars[z] = currentWord.get(z).charValue();
				String[] tempStrings = new String[wordsList.length+1];
				for(int z = 0; z < wordsList.length; z++) tempStrings[z] = wordsList[z];
				tempStrings[tempStrings.length-1] = new String(tempChars);
			wordsList = tempStrings;
			currentWord = new ArrayList<Character>();
		}
	  return wordsList;
  }
  static ArrayList<Token> run(ArrayList<Token> tokens){//on ( call run(everything inside ()s)
  	Token[] pastTokens;
  	ArrayList<Token> tokensOut = new ArrayList<Token>();
  	forever: do{//loop until one token remains
  		pastTokens = new Token[4];//store token buffer
  		tokensOut.clear();
	  	loop: for(int j = 0; j < tokens.size(); j++){//loop over every token
	  		Token currentToken = tokens.get(j);

	  		if(currentToken.type.equals("userInput")){//replaces userInput with input from the user
	  			Object o = getInput();
	  			for(int z = 0; z < j; z++) tokensOut.add(tokens.get(z));//add the numbers before the userInput
	  			if(o instanceof String) tokensOut.add(new Token("\"{"+(String)o+"}\""));//add the userInput
	  			else{
	  				if(o instanceof Boolean) tokensOut.add(new Token((Boolean)o));
	  				else{
	  					if(o instanceof Integer) tokensOut.add(new Token((Integer)o));
	  					else{
	  						throw new Error(o + "is of unknown type");
	  					}
	  				}
	  			}
  				for(int z = j+1; z < tokens.size(); z++) tokensOut.add(tokens.get(z));
	  			tokens = (ArrayList<Token>)(tokensOut.clone());
	  			break loop;
	  		}

	  		if(currentToken.type.equals("parentheses") && currentToken.value.equals("(")){//this bit of code uses recession to evaluate functions in parentheses
	  			int closingParenthesesLocation = -1;
	  			int requiredClosingParentheses = 0;
	  			loop2 : for(int z = j+1; z < tokens.size(); z++){
	  				if(tokens.get(z).type.equals("parentheses") && tokens.get(z).value.equals("(")){
	  					requiredClosingParentheses++;
	  				}
	  				if(tokens.get(z).type.equals("parentheses") && tokens.get(z).value.equals(")")){
		  				if(requiredClosingParentheses == 0){
		  					closingParenthesesLocation = z;
		  					break loop2;
		  				}
	  					requiredClosingParentheses--;
	  				}
	  			}
	  			if(closingParenthesesLocation == -1) throw new Error("no closing parentheses");
	  			ArrayList<Token> tokensToRun = new ArrayList<Token>();
	  			for(int z = j+1; z < closingParenthesesLocation; z++) tokensToRun.add(tokens.get(z));
	  			ArrayList<Token> results = run(tokensToRun);
	  			for(int z = 0; z < j; z++) tokensOut.add(tokens.get(z));//add the numbers before the parentheses
					for(Token t : results) tokensOut.add(t);//add the numbers in the parentheses
	  			for(int z = closingParenthesesLocation+1; z < tokens.size(); z++) tokensOut.add(tokens.get(z));//add the numbers after the parentheses
	  			tokens = (ArrayList<Token>)(tokensOut.clone());
	  			break loop;
	  		}

	  		for(int i = pastTokens.length-1; i >= 0; i--){//add to token buffer
	  			if((i+1) < pastTokens.length) pastTokens[i+1] = pastTokens[i];
	  		}

	  		pastTokens[0] = currentToken;
	  		if(j >= 1){
	  			if((pastTokens[1].type.equals("asCode") && (pastTokens[0].type.equals("variable") || pastTokens[0].type.equals("string")))){//interpret String after asCode as normal code
		  			var tempString = pastTokens[0].type.equals("string") ? ((String)(pastTokens[0].value)) : ((String)(((Variable)pastTokens[0].value).get()));
		  			//System.out.println(tempString);
						String[] tempStringArray = splitNewLine(tempString);
						if(tempStringArray.length == 1){//if there is no newline then 
							ArrayList<Token> tempTokens = splitCode(tempStringArray[0]);
			  			for(int i = 0; i < tempTokens.size(); i++) tokensOut.add(tempTokens.get(i));//adds each token to the token list
			  			for(int z = j+1; z < tokens.size(); z++) tokensOut.add(tokens.get(z));//adds all tokens after to the token list
			  			tokens = (ArrayList<Token>)(tokensOut.clone());
			  			break loop;
						}
						ArrayList<ArrayList<Token>> tempTokens = new ArrayList<ArrayList<Token>>();
						for(int s = 0; s < tempStringArray.length; s++){
			  			tempTokens.add(splitCode(tempStringArray[s]));
			  			//System.out.println(tempStringArray[s]);
			  		}
		  			ArrayList<ArrayList<Token>> z;
						for(int i = 0; i < tempTokens.size(); i++){//runs the code within the string
							if(tempTokens.size() != 0){
								z = new ArrayList<ArrayList<Token>>();
								for(int i2 = i; i2 < tempTokens.size(); i2++) z.add(tempTokens.get(i2));
								ArrayList<ArrayList<Token>> tempz = new ArrayList<ArrayList<Token>>();
								for(int indexx = 0; indexx < z.size(); indexx++){
									ArrayList<Token> tempzpart1 = new ArrayList<Token>();
									for(int indexy = 0; indexy < z.get(indexx).size(); indexy++) tempzpart1.add(z.get(indexx).get(indexy));
									tempz.add(tempzpart1);
								}
								if(Anti_array.checkForStatements(tempTokens.get(i), true, tempz)){//if this line is a statement then run it
									int requiredClosingBrackets = 1;
									while(true){//skip till the statement ends
										if(i < tempTokens.size()){
											i++;
											z.remove(z.size()-1);//I would think that z.remove(0) works; not this, but it didn't
										}
										else throw new Error("missing }");
										if(Anti_array.checkForStatements(tempTokens.get(i), false)) requiredClosingBrackets++;
										if(tempTokens.get(i).get(0).value.equals("}")){
											requiredClosingBrackets--;
											if(requiredClosingBrackets == 0) break;
										}
									}
								}
								else{
									Anti_array.run(tempTokens.get(i));//if this line is not a statement then run it normally
								}
							}
						}
		  			break forever;
		  		}
	  		}
		  	if(j >= 2){//replace valid operations with results
	  			if((pastTokens[2].type.equals("string") || pastTokens[2].type.equals("int") || pastTokens[2].type.equals("variable")) && pastTokens[1].type.equals("operator") && (pastTokens[0].type.equals("int") || pastTokens[0].type.equals("variable") || pastTokens[0].type.equals("string"))){//for math operations
	  				var tempObj = (((Operator)pastTokens[1].value).operate(pastTokens[2].value, pastTokens[0].value));
		  			tokensOut.add(new Token(tempObj instanceof String ? ("\"{" + (String)tempObj + "}\"") : Integer.toString(((int)tempObj))));
		  			for(int z = j+1; z < tokens.size(); z++) tokensOut.add(tokens.get(z));
		  			tokens = (ArrayList<Token>)(tokensOut.clone());
		  			break loop;
					}
					if(pastTokens[2].type.equals("variable") && pastTokens[1].type.equals("setter") && (pastTokens[0].type.equals("int") || pastTokens[0].type.equals("variable") || pastTokens[0].type.equals("bool") || pastTokens[0].type.equals("string"))){//for variable setters
		  			tokensOut.add(new Token(((Setter)pastTokens[1].value).set(pastTokens[2].value, pastTokens[0].value)));
		  			for(int z = j+1; z < tokens.size(); z++) tokensOut.add(tokens.get(z));
		  			tokens = (ArrayList<Token>)(tokensOut.clone());
		  			break loop;
					}
					if((pastTokens[2].type.equals("string") || pastTokens[2].type.equals("int") || pastTokens[2].type.equals("variable") || pastTokens[2].type.equals("bool")) && pastTokens[1].type.equals("conditional") && (pastTokens[0].type.equals("string") || pastTokens[0].type.equals("variable") || pastTokens[0].type.equals("int") || pastTokens[0].type.equals("bool"))){//for comparing
		  			tokensOut.add(new Token(((Conditional)pastTokens[1].value).check(pastTokens[2].value, pastTokens[0].value)));
		  			for(int z = j+1; z < tokens.size(); z++) tokensOut.add(tokens.get(z));
		  			tokens = (ArrayList<Token>)(tokensOut.clone());
		  			break loop;
					}
				}
				if(tokens.size() == 2 && j == 1){//preform ++ or -- if there are only two tokens
					if(pastTokens[1].type.equals("variable") && pastTokens[0].type.equals("setter")){
		  			tokensOut.add(new Token(((Setter)pastTokens[0].value).set(pastTokens[1].value)));
		  			for(int z = j+1; z < tokens.size(); z++) tokensOut.add(tokens.get(z));
		  			tokens = (ArrayList<Token>)(tokensOut.clone());
		  			break loop;
					}
				}
	  	}
	  }while(tokensOut.size() > 1);
	  if(tokensOut.size() == 0) tokensOut = tokens;
	  if(tokensOut.size() == 0) tokensOut.add(new Token(false));//make sure the list isn't empty
	  //System.out.println("results:");
	  if(tokensOut.size() > 0) if(tokensOut.get(0).type.equals("print")) for(int i = 1; i < tokensOut.size(); i++){//if the first token is print then print each remaining token
	  	String toPrint = "null";
	  	if(tokens.get(i).value instanceof Variable){
	  		toPrint = (((Variable)tokensOut.get(i).value).get()).toString();
	  	}
	  	else{
		  	toPrint = (tokensOut.get(i).value).toString();
			}
			//System.out.println(toPrint);
			String[] toPrintArray = splitNewLine(toPrint);
			for(int s = 0; s < toPrintArray.length; s++) System.out.println(toPrintArray[s]);
	  }
	  //for(int i = 1; i < tokensOut.size(); i++) System.out.println(tokens.get(i).value instanceof Variable ? (((Variable)tokensOut.get(i).value).get()) : tokensOut.get(i).value);
	  return tokensOut;
  }
}
class Token{//this class is the primary data structure for the program; each Token has a type and a value. the value is often a member of another class.
	String type = "undefined";
	Object value = "undefined";
	static final Operator[] operators = new Operator[]{new Operator("+"), new Operator("-"), new Operator("*"), new Operator("/"), new Operator("%")};
	static final Conditional[] conditionals = new Conditional[]{new Conditional("<"), new Conditional(">"), new Conditional("=="), new Conditional("!=")};
	static final Setter[] setters = new Setter[]{new Setter("+="), new Setter("-="), new Setter("*="), new Setter("/="), new Setter("%="), new Setter("="), new Setter("--"), new Setter("++")};
	public Token(String in){//parses a word into the correct type and value
		try{//if int
  		int num = Integer.parseInt(in);
  		type = "int";
  		value = num;
  	}
  	catch (NumberFormatException e){//if not int
  		for(Operator o : operators){
  			if(in.equals(o.type)){//if Operator
  				type = "operator";
  				value = new Operator(in);
  			}
  		}
  		for(Setter s : setters){
  			if(in.equals(s.type)){//if setters
  				type = "setter";
  				value = new Setter(in);
  			}
  		}
  		for(Conditional c : conditionals){
  			if(in.equals(c.type)){//if conditionals
  				type = "conditional";
  				value = new Conditional(in);
  			}
  		}
  		if(in.equals("(") || in.equals(")")){
  			type = "parentheses";
  			value = in;
  		}
  		if(in.equals("if")){
  			type = "if";
  			value = in;
  		}
  		if(in.equals("userInput")){
  			type = "userInput";
  			value = in;
  		}
  		if(in.equals("|")){
  			type = "separator";
  			value = in;
  		}
  		if(in.equals("print")){
  			type = "print";
  			value = in;
  		}
  		if(in.equals("{") || in.equals("}")){
  			type = "bracket";
  			value = in;
  		}
  		if(in.equals("loop")){
  			type = "loop";
  			value = in;
  		}
  		if(in.equals("asCode")){
  			type = "asCode";
  			value = in;
  		}
  		if(in.equals("true") || in.equals("false")){
  			type = "bool";
  			value = in.equals("true");
  		}
  		try{
	  		if(in.charAt(0) == '\"'){
	  			if(in.charAt(1) == '{' && in.charAt(in.length()-2) == '}' && in.charAt(in.length()-1) == '\"'){//if String
		  			type = "string";
		  			String out = new String(in);
		  			StringBuilder sb = new StringBuilder(out);
		  			sb.deleteCharAt(0);
		  			sb.deleteCharAt(0);
		  			sb.deleteCharAt(sb.length()-1);
		  			sb.deleteCharAt(sb.length()-1);
		  			out = sb.toString();
		  			value = out;
		  		}
	  		}
	  	}
	  	catch(StringIndexOutOfBoundsException ex){
	  		throw new Error(in + "is invalid");
	  	}
  		if(type.equals("undefined")){//if the type still isn't set then set it to "variable"
  			type = "variable";
  			value = new Variable(in);
  		}
  	}
	}
	public Token(int x){//if we know the type skip the longer parsing process
		type = "int";
		value = x;
	}
	public Token(boolean x){//if we know the type skip the longer parsing process
		type = "bool";
		value = x;
	}
	public Token(){}//blank constructor
}
class Variable{//this class stores variables. each contain a variable name and value. the value can be of any type and the type can be changed at any time.
	String name = "undefined";
	Object value = "unset";
	public Variable(String namein){
		name = namein;
		if(findPosition() == -1){//if this variable hasn't been referenced before, then create add it to the global variable list
			Anti_array.variableNames.add(name);
			Anti_array.variableValues.add(value);
		}
		else{//otherwise, find the value
			value = Anti_array.variableValues.get(findPosition());
		}
	}
	private int findPosition(){//finds where in the global variable list this variable name is found. if it is not found return -1
		int position = -1;
		for(int i = 0; i < Anti_array.variableNames.size(); i++){
			if(Anti_array.variableNames.get(i).equals(name)){
				position = i;
				break;
			}
		}
		return position;
	}
	public void set(Object valuein){//set the variable globally
		int position = findPosition();
		if(position == -1) throw new Error("variable doesn't exist?");
		Anti_array.variableValues.set(position, valuein);
		value = valuein;
	}
	public Object get(){//set the variable globally
		int position = findPosition();
		if(position == -1) throw new Error("variable doesn't exist?");
		return Anti_array.variableValues.get(position);
	}
}
class Conditional{
	String type;
	public Conditional(String typein){
		type = typein;
	}
	public boolean check(Object objX, Object objY){//returns boolean result
		Object x = 0;
		Object y = 0;
		if(objX instanceof Variable){//if x Variable
			try{
				x = ((Variable)objX).get();
			}
			catch(NullPointerException e){
				throw new Error("variable " + ((Variable)objX).name + " is undefined");
			}
		}
		else{
			if(objX instanceof Integer){//if x int
				x = objX;
			}
			else{
				if(objX instanceof String){//if x String
					x = objX;
				}
				else{
					if(objX instanceof Boolean){//if x boolean
						x = objX;
					}
					else{

					}
				}
			}
		}
		if(objY instanceof Variable){//if y Variable
			try{
				y = ((Variable)objY).get();
			}
			catch(NullPointerException e){
				throw new Error("variable " + ((Variable)objY).name + " is undefined");
			}
		}
		else{
			if(objY instanceof Integer){//if y int
				y = objY;
			}
			else{
				if(objY instanceof String){//if y String
					y = objY;
				}
				else{
					if(objY instanceof Boolean){//if y boolean
						y = objY;
					}
				}
			}
		}
		//System.out.println(objX + ", " + y);\
		if(type.equals("==")){
			return x.equals(y);
		}
		if(type.equals("!=")){
			return (x.equals(y) != true);
		}
		try{
			if(type.equals("<")){
				return (int)x < (int)y;
			}
			if(type.equals(">")){
				return (int)x > (int)y;
			}
		}
		catch(ClassCastException e){}
		System.out.println("value of x: " + x + " value of y: " + y);
	  throw new Error("operation: " + objX.toString() + " " + type + " " + objY.toString() + " is undefined");//if the program got to here then the operation must not have been checked for
	}
}
class Setter{
	String type;
	public Setter(String typein){
		type = typein;
	}
	public int set(Object objX, Object objY){//sets the variable
		Token x = new Token(false);
		Token y = new Token(false);
		if(objX instanceof Variable){//if x Variable
			try{
				x = new Token(((Variable)objX).name);
			}
			catch(NullPointerException e){
				throw new Error("variable " + ((Variable)objX).name + " is undefined");
			}
		}
		if(objY instanceof Variable){//if y Variable then cast to the right type
			try{
				if((((Variable)objY).get()) instanceof Integer){//if the variable contains a int
					y = new Token((int)(((Variable)objY).get()));
				}
				else{
					if((((Variable)objY).get()) instanceof String){//if the variable contains a String
						y = new Token("\"{" + (String)(((Variable)objY).get()) + "}\"");
					}
					else{
						if((((Variable)objY).get()) instanceof Boolean){//if the variable contains a boolean
							y = new Token((Boolean)(((Variable)objY).get()));
						}
						else{
							
						}
					}
				}
			}
			catch(NullPointerException e){
				throw new Error("variable " + ((Variable)objY).name + " is undefined");
			}
		}
		else{
			if(objY instanceof Integer){//if y int
				y = new Token((int)objY);
			}
			else{
				if(objY instanceof String){//if y String
					y = new Token("\"{" + (String)objY + "}\"");
				}
				else{
					if(objY instanceof Boolean){//if y boolean
						y = new Token((boolean)objY);
					}
					else{//otherwise try just giving it the token
						y = (Token)objY;
					}
				}
			}
		}
		try{//integer operations only
			if(type.equals("-=")){
				((Variable)x.value).set(((int)(((Variable)x.value).get()))-(int)(y).value);
				return (int)((Variable)(x).value).get();//return the new variable
			}
			if(type.equals("*=")){
				((Variable)x.value).set(((int)(((Variable)x.value).get()))*(int)(y).value);
				return (int)((Variable)(x).value).get();//return the new variable
			}
			if(type.equals("/=")){
				((Variable)x.value).set(((int)(((Variable)x.value).get()))/(int)(y).value);
				return (int)((Variable)(x).value).get();//return the new variable
			}
			if(type.equals("%=")){
				((Variable)x.value).set(((int)(((Variable)x.value).get()))%(int)(y).value);
				return (int)((Variable)(x).value).get();//return the new variable
			}
		}
		catch(NullPointerException e){
			throw new Error("variable " + ((Variable)objX).name + " is null");
		}
		if(type.equals("+=")){//either int or String
			if(((Variable)x.value).get() instanceof String){
				((Variable)x.value).set((String)(((Variable)x.value).get())+((y.value instanceof String) ? (String)y.value : (int)y.value));
			}
			else{
				((Variable)x.value).set(((int)(((Variable)x.value).get()))+(int)(y).value);
				return (int)((Variable)(x).value).get();//return the new variable
			}
			return -1;//default to -1
		}
		if(type.equals("=")){//all types
			if(y.value instanceof Variable) ((Variable)(x).value).set(((Token)(((Variable)y.value).get())).value);
			else ((Variable)(x).value).set(y.value);
			try{
				return (int)((Variable)x.value).get();//return the new variable
			}
			catch(Exception ex){
				return -1;//default to -1
			}
		}
  	throw new Error("operation: " + objX.toString() + " " + type + " " + objY.toString() + " is undefined");//if the program got to here then the operation must not have been checked defined
	}
	public int set(Object objX){//like above, but for no ++, and --
		if(objX instanceof Variable){//if x variable
			Variable x = (Variable)objX;
			try{
				if(type.equals("++")){
					x.set((int)x.get()+1);
					return (int)x.get();
				}
				if(type.equals("--")){
					x.set((int)x.get()-1);
					return (int)x.get();
				}
			}
			catch(NullPointerException e){
				throw new Error("variable " + x.name + " is null or not int");
			}
		}
		throw new Error("operation: " + objX.toString() + " " + type + " is undefined");//if the program got to here then the operation must not have been checked defined
	}
}
class Operator{//this class preforms Math operations
	String type;
	public Operator(String typein){
		type = typein;
	}
	public Object operate(Object objX, Object objY){
		Object x = 0;
		Object y = 0;
		if(objX instanceof Variable){//if x variable
			if(((Variable)objX).get() instanceof String){
				x = (String)(((Variable)objX).get());
			}
			else{
				x = (int)((Variable)objX).get();
			}
		}
		if(objY instanceof Variable){//if y variable
			if(((Variable)objY).get() instanceof String){
				y = (String)((Variable)objY).get();
			}
			else{
				y = (int)((Variable)objY).get();
			}
		}
		if(objX instanceof String){//if x String
			x = (String)objX;
		}
		if(objY instanceof String){//if y String
			y = (String)objY;
		}
		if(objX instanceof Integer){//if x int
  		x = (int)objX;
		}
		if(objY instanceof Integer){//if y int
	  	y = (int)objY;
	  }
		try{//do the operations
			if(type.equals("+")){//either add strings or integers
				if(x instanceof String){
					if(y instanceof String){
						return ((String)x + (String)y);
					}
					if(y instanceof Integer){
						return ((String)x + (int)y);
					}
				}
				else{
					if(y instanceof String){
						return ((int)x + (String)y);
					}
					if(y instanceof Integer){
						return ((int)x + (int)y);
					}
				}
				return 0;
			}
			if(type.equals("-")){
				return (int)x-(int)y;
			}
			if(type.equals("*")){
				return (int)x*(int)y;
			}
			if(type.equals("/")){
				return (int)x/(int)y;
			}
			if(type.equals("%")){
				return (int)x%(int)y;
			}
		}
		catch(Exception ex){
			System.out.println(ex);
		}
		throw new Error("operation: " + objX.toString() + " " + type + " " + objY.toString() + " is undefined");//if the program got to here then the operation must not have been checked defined
	}
}
class Loop{
	ArrayList<Token> assignment;//preformed once at the beginning
	ArrayList<Token> condition;//preformed every time and must interpret to one token
	ArrayList<Token> change;//preformed ever time at the end
	ArrayList<ArrayList<Token>> toRun;//the code inside the brackets
	public Loop(){

	}
	public Loop(ArrayList<Token> assignmentin, ArrayList<Token> conditionin, ArrayList<Token> changein, ArrayList<ArrayList<Token>> toRunin){
		assignment = assignmentin;
		condition = conditionin;
		change = changein;
		toRun = toRunin;
	}
	public static Loop generateNewUserDefinedLoop(ArrayList<Token> tokens){
		return generateNewUserDefinedLoop(tokens, null);
	}
	public static Loop generateNewUserDefinedLoop(ArrayList<Token> tokens, ArrayList<ArrayList<Token>> afterStatement){//gets code for loop from user or from afterStatement variable
		/*if(afterStatement != null){
			for(int w = 0; w < afterStatement.size(); w++){
				for(int y = 0; y < afterStatement.get(w).size(); y++){
					System.out.print(afterStatement.get(w).get(y).value + " ");
				}
				System.out.println();
			}
		}
		else System.out.println("null");*/
		int requiredClosingBrackets = 0;
		int tokenIndex = 0;
		ArrayList<Token> tempTokens;
		tempTokens = new ArrayList<Token>();//gets the assignment from tokens variable
		do{
			tempTokens.add(tokens.get(tokenIndex));
			tokenIndex++;
		}while(!tempTokens.get(tempTokens.size()-1).value.equals("|"));
		tempTokens.remove(tempTokens.size()-1);
		//System.out.print("assignment: ");
		ArrayList<Token> assignmentOut = tempTokens;
		tempTokens = new ArrayList<Token>();//gets the condition from tokens variable
		do{
			tempTokens.add(tokens.get(tokenIndex));
			tokenIndex++;
		}while(!tempTokens.get(tempTokens.size()-1).value.equals("|"));
		tempTokens.remove(tempTokens.size()-1);
		//System.out.print("condition: ");
		ArrayList<Token> conditionOut = tempTokens;
		tempTokens = new ArrayList<Token>();//gets the change from tokens variable
		do{
			tempTokens.add(tokens.get(tokenIndex));
			tokenIndex++;
		}while(!tempTokens.get(tempTokens.size()-1).value.equals("|"));
		tempTokens.remove(tempTokens.size()-1);
		//System.out.print("change: ");
		//System.out.println("{");
		ArrayList<Token> changeOut = tempTokens;

		ArrayList<Token> tokensCopy;
		ArrayList<ArrayList<Token>> toRunOut = new ArrayList<ArrayList<Token>>();
		//Anti_array.checkForStatements((ArrayList<Token>)tokensCopy.clone(), false);
		int count = 1;
		while(true){//loop until this loop is closed by afterStatement or if that is null, then the user
			if(afterStatement == null) tokensCopy = Anti_array.getNextLine();
			else{
  			try{
					tokensCopy = afterStatement.get(count);
				}
				catch(IndexOutOfBoundsException e){
					throw new Error("missing }");
				}
			}
  		if(Anti_array.checkForStatements((ArrayList<Token>)tokensCopy.clone(), false)){//if there is a statement inside then it needs more brackets
  			requiredClosingBrackets++;
  			//System.out.println("statement detected");
  		}
			toRunOut.add(tokensCopy);
			Token currentToken = tokensCopy.get(0);
			if(currentToken.value.equals("}")){
				//System.out.println(requiredClosingBrackets);
				if(requiredClosingBrackets == 0){
					break;
				}
				requiredClosingBrackets--;
			}
			count++;
		}
		return new Loop(assignmentOut, conditionOut, changeOut, toRunOut);
	}
	private boolean checkCondition(){//runs the condition and returns the result
		var x = Anti_array.run(condition);
		if(x.size() != 1){//if not one token return debug info
			x = new ArrayList<Token>();
			x.add(new Token("print"));
			for(int i = 0; i < condition.size(); i++){
				x.add(condition.get(i));
				System.out.println(condition.get(i).value);
			}
			Anti_array.run(x);
			throw new Error("condition does not compile to one token");
		}
		return (x.get(0).value instanceof Boolean) ? (boolean)x.get(0).value : (boolean)((Variable)x.get(0).value).get();
	}
	public void run(){//runs the loop
		Anti_array.run(assignment);//run the assignment
		boolean continueRuning = checkCondition();//runs the condition
		while(continueRuning){
			ArrayList<ArrayList<Token>> z;
			for(int i = 0; i < toRun.size(); i++){//runs the code within the loop
				if(toRun.size() != 0){
					z = new ArrayList<ArrayList<Token>>();
					for(int i2 = i; i2 < toRun.size(); i2++) z.add(toRun.get(i2));
					//for(int w = 0; w < toRun.get(i).size(); w++) System.out.print(toRun.get(i).get(w).value + ", ");
					//System.out.println();
					ArrayList<ArrayList<Token>> tempz = new ArrayList<ArrayList<Token>>();
					for(int indexx = 0; indexx < z.size(); indexx++){
						ArrayList<Token> tempzpart1 = new ArrayList<Token>();
						for(int indexy = 0; indexy < z.get(indexx).size(); indexy++) tempzpart1.add(z.get(indexx).get(indexy));
						tempz.add(tempzpart1);
					}
					if(Anti_array.checkForStatements(toRun.get(i), true, tempz)){//if this line is a statement then run it
						int requiredClosingBrackets = 1;
						while(true){//skip till the statement ends
							if(i < toRun.size()){
								i++;
								z.remove(z.size()-1);//I would think that z.remove(0) works; not this, but it didn't
							}
							else throw new Error("missing }");
							if(Anti_array.checkForStatements(toRun.get(i), false)) requiredClosingBrackets++;
							if(toRun.get(i).get(0).value.equals("}")){
								requiredClosingBrackets--;
								if(requiredClosingBrackets == 0) break;
							}
						}
					}
					else{
						Anti_array.run(toRun.get(i));//if this line is not a statement then run it normally
					}
				}
			}
			Anti_array.run(change);//runs the change
			continueRuning = checkCondition();//runs the condition
		}
	}
}
class IfStatement{
	ArrayList<Token> condition;//the condition determines if the code inside will run
	ArrayList<ArrayList<Token>> toRun;//the code inside the brackets
	public IfStatement(ArrayList<Token> conditionIn, ArrayList<ArrayList<Token>> toRunIn){
		condition = conditionIn;
		toRun = toRunIn;
	}
	public static IfStatement generateNewUserDefinedIfStatement(ArrayList<Token> tokens){
		return generateNewUserDefinedIfStatement(tokens, null);
	}
	public static IfStatement generateNewUserDefinedIfStatement(ArrayList<Token> tokens, ArrayList<ArrayList<Token>> afterStatement){//gets code for if statement from user or from afterStatement variable
		//System.out.print("condition: ");
		ArrayList<ArrayList<Token>> toRunOut = new ArrayList<ArrayList<Token>>();
		ArrayList<Token> conditionOut = tokens;
		//System.out.println("{");
		ArrayList<Token> tokensCopy;
		int requiredClosingBrackets = 0;
		int count = 1;
		while(true){//loop until this loop is closed by afterStatement or if that is null, then the user
			if(afterStatement == null) tokensCopy = Anti_array.getNextLine();
  		else{
  			try{
					tokensCopy = afterStatement.get(count);
				}
				catch(IndexOutOfBoundsException e){
					throw new Error("missing }");
				}
			}
  		if(Anti_array.checkForStatements((ArrayList<Token>)tokensCopy.clone(), false)){//if there is a statement inside then it needs more brackets
  			requiredClosingBrackets++;
  			//System.out.println("statement detected");
  		}
			toRunOut.add(tokensCopy);
			Token currentToken = tokensCopy.get(0);
			if(currentToken.value.equals("}")){
				if(requiredClosingBrackets == 0){
					break;
				}
				requiredClosingBrackets--;
			}
			count++;
		}
		return new IfStatement(conditionOut, toRunOut);
	}
	public void run(){//runs the if statement
		boolean continueRuning;
		var x = new ArrayList<Token>();
		/*x.add(new Token("print"));
		for(int i = 0; i < condition.size(); i++){
			x.add(condition.get(i));
			//System.out.println(condition.get(i).value);
		}*/
		x = Anti_array.run(condition);//run the condition
		if(x.size() != 1){//if not one token return debug info
			x = new ArrayList<Token>();
			x.add(new Token("print"));
			for(int i = 0; i < condition.size(); i++){
				x.add(condition.get(i));
				System.out.println(condition.get(i).value);
			}
			Anti_array.run(condition);
			throw new Error("condition does not compile to one token");
		}
		continueRuning = (x.get(0).value instanceof Boolean) ? (boolean)x.get(0).value : (boolean)((Variable)x.get(0).value).get();
		if(continueRuning){
			ArrayList<ArrayList<Token>> z;
			for(int i = 0; i < toRun.size(); i++){
				z = new ArrayList<ArrayList<Token>>();
				for(int i2 = i; i2 < toRun.size(); i2++) z.add(toRun.get(i2));
				//for(int w = 0; w < toRun.get(i).size(); w++) System.out.print(toRun.get(i).get(w).value + ", ");
				//System.out.println();
				ArrayList<ArrayList<Token>> tempz = new ArrayList<ArrayList<Token>>();
				for(int indexx = 0; indexx < z.size(); indexx++){
					ArrayList<Token> tempzpart1 = new ArrayList<Token>();
					for(int indexy = 0; indexy < z.get(indexx).size(); indexy++) tempzpart1.add(z.get(indexx).get(indexy));
					tempz.add(tempzpart1);
				}
				if(Anti_array.checkForStatements(toRun.get(i), true, tempz)){//if this line is a statement then run it
					int requiredClosingBrackets = 1;
					while(true){//skip till the statement ends
						if(i < toRun.size()){
							i++;
							z.remove(z.size()-1);//I would think that z.remove(0) works; not this, but it didn't
						}
						else throw new Error("missing }");
						if(Anti_array.checkForStatements(toRun.get(i), false)) requiredClosingBrackets++;
						if(toRun.get(i).get(0).value.equals("}")){
							requiredClosingBrackets--;
							if(requiredClosingBrackets == 0) break;
						}
					}
				}
				else{
					Anti_array.run(toRun.get(i));//if this line is not a statement then run it normally
				}
			}
		}
	}
}
