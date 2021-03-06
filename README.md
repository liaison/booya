Booya
==================
*  @date:    Nov 04, 2014


* This is a Java project that is intended to parse the boolean expression 
 into a binary tree. 

* First of all, the problem seems to be a typical parsing problem, which
one might resort to any kind of parser-generating framework such as ANTLR.
But I figured that it seems to be too heavy to employ any framework. It 
feels like killing a mosquito with a machine gun. I expect to have certain
lightweight tokenizer that can do the lexing job, since the grammar is 
actually not complicated. 

* Eventually, I did find a nice Tokenizer class that implements the tokenization
with the help of string regex. Then I was happy to reuse their code in 
this project. 

* The second part of the problem is the parser, given the lexer above. As 
one might figure, the problem is similar with the evaluation of the 
arithmetic expression, which we typically employ the stack data structure
to do so. So the overall solution here did use the stack to transform each
operator and each operand into a node in a binary tree. A trick part is
the handling of parentheses, which in my implementation, I divided the 
expression into sub-expression on-the-fly when the parser comes across 
parentheses and then the parser recursively refine/evaluation the expression
until there is only node left which is the root node of the entire expression.



