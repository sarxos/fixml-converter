
The goal of this project is to create two way converter - from FIX -> FIXML and from FIXML -> FIX. On the FIX side I depends on "QuickFIX/J":http://www.quickfixj.org solution. On the FIXML side I try to parse schema files and construct proper XML with "XStream":http://xstream.codehaus.org converters from Codehause.

## How It Works

Currently you can easily create new FIX message and convert it to FIXML. I'm still working to implement conversion in opposite direction, i.e. FIXML unmarshalling. 

## Work In Progress

I have no knownledge and time to test all possible messages, so any *help* is greatly *appreciated*. You can find working (more or less) examples in src/examples/java there are also some unit tests in src/test/java directory.

## TODO

* Whole unmarshalling stuff
