# XQuery Processor [UCSD cse232b Project ]
## Milestone 1
1. description  
    Read a Xpath, retrieve appropriate nodes from DOM
2. How to run  
    java -jar XPathProcessor.jar [your XPATH expression]
3. example  
java -jar XPathProcessor.jar "doc(\"j_caesar.xml\")//ACT[(./TITLE)==(./TITLE)]/*/SPEECH/../TITLE"