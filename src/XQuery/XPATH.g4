grammar XPATH;

//absolute path
ap: doc '/' rp                  # apSingle
  | doc '//' rp                 # apDouble
  ;
doc: 'doc("' filename '")';

//relative path

rp : tagName                    # rpTag
   | '*'                        # rpChildren
   | '.'                        # rpSelf
   | '..'                       # rpParent
   | 'text()'                   # rpText
   | '@' attName                # rpAttri
   | '(' rp ')'                 # rpParentheses
   | rp '/' rp                  # rpSingleSlash
   | rp '//' rp                 # rpDoubleSlash
   | rp '[' f ']'               # rpCondition
   | rp ',' rp                  # rpComma
   ;

//path filter
f : rp                          # filterRp
  | rp EQUALS rp                # filterEq
  | rp IS rp                    # filterIs
  | '(' f ')'                   # filterParentheses
  | f 'and' f                   # filterAND
  | f 'or' f                    # filterOR
  | 'not' f                     # filterNOT
  ;

tagName:  ID;
attName:  ID;

EQUALS: '=' | 'eq';
IS: '==' | 'is';
ID: [a-zA-Z0-9_-]+ ;

filename: FILENAME;
FILENAME: [a-zA-Z0-9._]+;

WHITESPACE:[ \t\n\r]+ -> skip;
