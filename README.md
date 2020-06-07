# Anti-Array
a esolang without arrays or stacks

== the idea ==

Anti-array is a programming language without arrays or stacks.

Instead of either, it allows you to interpret a string as code. This allows you to programmatically create variables.

example:
  variableName = "var"
  loop variableNumber = 0 | variableNumber < 100 | variableNumber ++ |//loop from 1-100 and assign a variable with the name var + the variable number to the variable number
    ( asCode ( variableName + variableNumber ) ) = variableNumber
  }
  loop variableNumber = 0 | variableNumber < 100 | variableNumber ++ |// print each previously created variable
    print ( asCode ( variableName + variableNumber ) )
  }

== syntax ==

Each line is made up of tokens. Each token must have a space between adjacent tokens.

=== tokens ===

operators:
* assignment: +=, -=, *=, /=, %=, =, --, ++
* comparison: <, >, ==, !=
* math: +, -, *, /, %
commands:
* print
* asCode
  * interpret the next string (or string variable) as code, not a string
* if
	* if boolean
        * requires } to close the statement
* loop (statement that runs when initialized) | (condition) | (statement that runs at the end of every loop) |
	* parentheses are not necessary
        * requires } to close the statement
numbers:
* any sequence of pure numbers will be interpreted as an int
* there are no floating-point numbers
strings:
* anything within quotes
boolean:
* false or true
other:
* parentheses
  * performs operations inside the parentheses first
* premature line breaks
	* use | to act as a line break
variables:
* anything that is not one of the above
* Does not require a set type
* internal types are boolean, string, and int
comments:
* there are currently no comments

== examples ==

* factorial:

	total = 1
	factorialNumber = 9
	loop x = 1 | x < factorialNumber | x ++ |
		total *= x
	}
	print total

* create variables with 1-100 in them then print them

	baseVariableName = "var"
	loop x = 1 | x < 101 | x ++ |
		( asCode ( baseVariableName + x ) ) = x
	}
	loop x = 1 | x < 101 | x ++ |
		print ( asCode ( baseVariableName + x ) )
	}      
