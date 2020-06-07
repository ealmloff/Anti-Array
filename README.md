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

== links ==

https://esolangs.org/wiki/Anti-Array#tokens //this contains information on syntax
