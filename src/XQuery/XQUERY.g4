grammar XQUERY;
import XPATH;

xq : Var                                                # xqVar
   | StringConstant                                     # xqStringConstant
   | ap                                                 # xqAp
   | '(' xq ')'                                         # xqParentheses
   | xq ',' xq                                          # xqComma
   | xq '/' rp                                          # xqSingleSlash
   | xq '//' rp                                         # xqDoubleSlash
   | '<' tagName '>' '{' xq '}' '</' tagName '>'        # xqTag
   | forClause letClause? whereClause? returnClause     # xqFLWR
   | letClause xq                                       # xqLet
   | joinClause                                         # xqJoin
   ;

forClause : 'for' Var 'in' xq (',' Var 'in' xq)*;
letClause : 'let' Var ':=' xq (',' Var ':=' xq)*;
whereClause : 'where' cond;
returnClause : 'return' xq;
joinClause : 'join' '(' xq ',' xq ',' joinKeys ',' joinKeys')';
joinKeys: '[' (ID ',')* ID ']';

cond : xq EQUALS xq                                                 # condEq
     | xq IS xq                                                     # condIs
     | 'empty (' xq ')'                                             # condEmpty
     | 'some' Var 'in' xq (',' Var 'in' xq)* 'satisfies' cond       # condSatisfy
     | '(' cond ')'                                                 # condComma
     | cond 'and' cond                                              # condAND
     | cond 'or' cond                                               # condOR
     | 'not' cond                                                   # condNOT
     ;

Var: '$' ID;
StringConstant : '"'[a-zA-Z0-9,.!?; '"-]+'"';



