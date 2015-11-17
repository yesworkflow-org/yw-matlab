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

printall('mq1(SourceFile,StartLine,EndLine) - Where is the definition of block C3_C4_map_present_NA.fetch_monthly_mean_precipitation_data?', mq1(_,_,_)).
printall('mq2(WorkflowName) - What is the name of the top-level workflow?', mq2(_)).
printall('mq4(ProgramName) -  What are the names of the programs comprising the top-level workflow?', mq4(_)).
printall('mq5(DataName) - What are the inputs to the top-level workflow?', mq5(_)).
printall('mq6(DataName) - What data is output by program block C3_C4_map_present_NA.examine_pixels_for_grass?', mq6(_)).

printall('mq7(ProgramName) - What program blocks provide input directly to C3_C4_map_present_NA.generate_netcdf_file_for_Grass_fraction?', mq7(_)).
printall('mq8(ProgramName) - What programs have input ports that receive data C3_C4_map_present_NA[lon_variable]?', mq8(_)).
printall('mq9(PortCount) - How many ports read data C3_C4_map_present_NA[lat_variable]?', mq9(_)).
printall('mq10(DataCount) - How many data are read by more than port in workflow C3_C4_map_present_NA?', mq10(_)).
printall('mq11(ProgramName) - What program blocks are immediately downstream of examine_pixels_for_grass?', mq11(_)).
printall('mq12(UpstreamProgramName) - What program blocks are immediately upstream of generate_netcdf_file_for_Grass_fraction?', mq12(_)).
printall('mq13(UpstreamProgramName) - What program blocks are upstream of generate_netcdf_file_for_C3_fraction?', mq13(_)).
printall('mq14(DownstreamProgramName) - What program blocks are anywhere downstream of fetch_monthly_mean_precipitation_data?', mq14(_)).
printall('mq15(DownstreamDataName) - What data is immediately downstream of Tair_Matrix?', mq15(_)).
printall('mq16(UpstreamDataName) - What data is immediately upstream of Grass_fraction_data?', mq16(_)).
printall('mq17(DownstreamDataName) - What data is downstream of Rain_Matrix?', mq17(_)).
printall('mq18(UpstreamDataName) - What data is upstream of SYNMAP_land_cover_map_variable?', mq18(_)).
printall('mq19(UriVariableName) - What URI variables are associated with reads of data C3_C4_map_present_NA[mean_airtemp]?', mq19(_)).
printall('mq20(UpStreamDataName) - What URI variables do data read into mean_precip and mean_airtemp have in common?', mq20(_)).


END_XSB_STDIN


