# Thompson's Algorithm

An implementation of McNaughton-Yamada-Thompson's algorithm
in Java to create an AFN from a Regular Expression.

## Getting Started

Fork the project or download it. Then just add the project
to your prefered IDE or compile it from Console.

### Prerequisites

Java SE8.

### Installing

Load the project to an IDE or copy the contents to a folder.
Then compile the project from console or with IDE. 
Main class is:

```
src\Run\Runnable.java
```

## Running the program

Enter a regular expression in console. Valid operators are:

```
'|', '*', '+', '?', '^', '.'
```

And for the symbols you may use any other Ascii or Unicode symbol.
Note: you MUST use the epsilon symbol to represent an empty
word:

```
ε
```

Example of a valid regular expression may be:

```
0?(1|ε)?0*
```

Note that you can use abbreviations and yuxtaposed concatenation, ie. no
need for placing '.' in your expression.
## Deployment

After running the program, a text file with the contents of the
AFN will be exported as:

```
root_directory\AFN.txt
```

## Authors

* **Gabriel Brolo** 

## License

This project is licensed under the MIT License

## Acknowledgments

* Big shoutout to Guillaume Ménard who's code helped me finish the infix to postfix converter:
https://gist.github.com/gmenard/6161825
* To Alfred Aho, for making such explicit algorithms in his book 'Compilators'.

