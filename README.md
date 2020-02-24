# XQuery Processor [UCSD cse232b Project ]
## Milestone 1
1. Description  
    Read a Xpath, retrieve appropriate nodes from DOM
2. How to run  
    java -jar XPathProcessor.jar [your XPATH expression]
3. Example  
java -jar XPathProcessor.jar "doc(\"j_caesar.xml\")//ACT[(./TITLE)==(./TITLE)]/*/SPEECH/../TITLE"

## Milestone 2
1. Description  
    Read a file that contains a XQuery, then retrieve appropriate nodes from DOM
2. How to run  
    java -jar XQueryProcessor.jar [your file path]
3. Example   
    java -jar XQueryProcessor.jar ./testFiles/test1.txt
