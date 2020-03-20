# XQuery Processor [UCSD cse232b Project ]
## Milestone 1
1. Description  
    Read a Xpath, retrieve appropriate nodes from DOM
2. How to run  
    java -jar XPathProcessorM1.jar XPath
3. Example  
java -jar XPathProcessorM1.jar "doc(\"j_caesar.xml\")//ACT[(./TITLE)==(./TITLE)]/*/SPEECH/../TITLE"

## Milestone 2
1. Description  
    Read a file that contains a XQuery, then retrieve appropriate nodes from DOM
2. How to run  
    java -jar XQueryProcessorM2.jar XQuertfilePath
3. Example   
    java -jar XQueryProcessorM2.jar ./testFiles/milestone2/test1.txt

## Milestone 3  
1. Description  
    Implemented a XQueryProcessor with Optimizer to detect join and rewriter the XQuery.
2. How to run  
    java -jar XQueryProcessorM3.jar XQuertfilePath
3. Example   
    java -jar XQueryProcessorM3.jar ./testFiles/milestone3/test1.txt    

## Milestone 4  
1. Description  
    Add join plan options that "-B" represent bushy join and "-L" represent left deep join.
2. How to run  
    java -jar XQueryProcessorM4.jar XQuertFilePath -B/-L  
3. Example  
    java -jar XQueryProcessorM4.jar ./testFiles/milestone3/test10.txt -L  
    java -jar XQueryProcessorM4.jar ./testFiles/milestone3/test10.txt -B