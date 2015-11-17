#!/usr/bin/env bash -l
#
# ./run_queries.sh &> run_queries.txt

xsb << END_XSB_STDIN

[rules].
[extractfacts].
[modelfacts].
[reconfacts].
[extract_queries].
[model_queries].
[recon_queries].

printall('eq1(SourceFile) - What source files SF were YW annotations extracted from?', eq1(_)).
printall('eq2(ProgramName) - What are the names N of all program blocks?', eq2(_)).
printall('eq3(PortName) - What out ports are qualified with URIs?', eq3(_)).


END_XSB_STDIN


