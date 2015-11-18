YesWorkflow-MATLAB
==================

The __yw-matlab__ module contains [YesWorkflow](https://github.com/yesworkflow-org/yw-prototypes) (YW) modules and command line tools for interacting with the [DataONE MATLAB RunManager](https://github.com/DataONEorg/matlab-dataone/b). These tools make the RunManager's records of script runs and data provenance available to YW so that this retrospective provenance information can be queried in terms of, and in combination with, the prospective provenance revealed by YW annotations in the MATLAB scripts.

This repository also contains example MATLAB scripts marked up with YW annotations, the products of running YW on the scripts, and example prospective and retrospective queries of the provenance records jointly created by YW and the DataONE RunManager for runs of each script.

#### Layout of repository

Directory | Description
----------|------------
[src/main/resources/scripts](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/scripts) | MATLAB scripts for working with the RunManager and exporting provenance records for importing into YesWorkflow.
[src/main/resources/examples](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples) | Example scripts and associated data files, provenance records, and results of prospective and retrospective provenance queries for runs of each script.


Examples
--------

#### C3_C4_mapping

The example script and data files currently used to exercise the tools in this repository are based on those used in the DataONE [MATLAB Walk-through](https://github.com/DataONEorg/matlab-dataone/blob/master/docs/walk-through.rst).  Files for this example are located at [src/main/resources/examples/C3_C4_mapping](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping). Layout of directories and files within this example directory:

Directory or file | Description
------------------|------------
[C3_C4_map_present_NA.m](https://github.com/yesworkflow-org/yw-matlab/blob/master/src/main/resources/examples/C3_C4_mapping/C3_C4_map_present_NA.m) | The MATLAB script marked up with YW annotations.
[inputs/](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/inputs) | Script input data files.
[outputs/](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/outputs) | Outputs from one run of the script.
[yw/](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw) | YesWorkflow configuration and output files.
[yw/xsb/extractfacts.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/extractfacts.P), [yw/xsb/modelfacts.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/modelfacts.P), [yw/xsb/reconfacts.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/reconfacts.P)  | Prolog fact files created by YesWorkflow and containing the prospective and retrospective provenance captured by YW.
[yw/xsb/rules.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/rules.P) | Prolog rules for querying the YW-written facts.
[yw/xsb/extract_queries.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/extract_queries.P), [yw/xsb/model_queries.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/model_queries.P), [yw/xsb/recon_queries.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/recon_queries.P)| Prolog queries employing the rules defined in rules.P.
[yw/xsb/run_queries.sh](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/run_queries.sh) | Bash script for running all of the Prolog queries.
[yw/xsb/run_queries.txt](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/run_queries.txt) | The output produced by running all of the Prolog queries using run_queries.sh.

##### YesWorkflow combined view of script

![Combined YW view of C3_C4_map_present_NA.m](https://raw.githubusercontent.com/yesworkflow-org/yw-matlab/master/src/main/resources/examples/C3_C4_mapping/yw/combined.png)

### YW provenance queries 

The following is a summary of the queries posed by running [yw/xsb/run_queries.sh](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/run_queries.sh) and the corresponding results.

#### Queries about the script and the YW annotations extracted from it

    | Query | Result
----|-------|--------
EQ1 | What source files were YW annotations extracted from?   | `../C3_C4_map_present_NA.m`
EQ2 | What are the names of all program blocks in the script? | `generate_netcdf_file_for_C3_fraction`, `fetch_monthly_mean_precipitation_data`, `C3_C4_map_present_NA`, `examine_pixels_for_grass`, `generate_netcdf_file_for_C4_fraction`, `fetch_SYNMAP_land_cover_map_variable`, `generate_netcdf_file_for_Grass_fraction`, `fetch_monthly_mean_air_temperature_data`, `initialize_Grass_Matrix`
EQ3 | What out ports are qualified with URIs? | `Grass_fraction_data`, `C4_fraction_data`, `C3_fraction_data`

#### Queries about the workflow model of the script (prospective provenance)

   | Query | Result
----|-------|--------
MQ1 | Where is the definition of block `fetch_monthly_mean_precipitation_data`? | SourceFile=`../C3_C4_map_present_NA.m`, StartLine=`59`, EndLine=`69`
MQ2 | What is the name of the top-level workflow? | `C3_C4_map_present_NA`
MQ3 | What are the names of the program blocks comprising the workflow? |<ul><li>generate_netcdf_file_for_Grass_fraction <li>generate_netcdf_file_for_C4_fraction <li>generate_netcdf_file_for_C3_fraction <li>examine_pixels_for_grass <li>initialize_Grass_Matrix <li>fetch_monthly_mean_precipitation_data <li>fetch_monthly_mean_air_temperature_data <li>fetch_SYNMAP_land_cover_map_variable)</ul>
MQ4 | What are the names of the program blocks in the workflow that produce workflow outputs? | <ul><li>generate_netcdf_file_for_Grass_fraction <li>generate_netcdf_file_for_C4_fraction <li>generate_netcdf_file_for_C3_fraction</ul>
MQ5 | What are the inputs to the script? | <ul><li>mean_precip <li>mean_airtemp <li>SYNMAP_land_cover_map_data</ul>
MQ6 | What data is output by program block `examine_pixels_for_grass`? | <ul><li>C4_Data <li>C3_Data</ul>
MQ7 | What program blocks provide input directly to `generate_netcdf_file_for_Grass_fraction`? | <ul><li>initialize_Grass_Matrix <li>fetch_SYNMAP_land_cover_map_variable</ul>
MQ8 | What programs have input ports that receive data `lon_variable`? | <ul><li>generate_netcdf_file_for_Grass_fraction <li>generate_netcdf_file_for_C4_fraction <li>generate_netcdf_file_for_C3_fraction</ul>
MQ9 | How many ports read data `lat_variable`? | 3
MQ10 | How many data are read by more than port in workflow `C3_C4_map_present_NA`? | 4
MQ11 | What program blocks are immediately downstream of `examine_pixels_for_grass`? | <ul><li>generate_netcdf_file_for_C4_fraction <li>generate_netcdf_file_for_C3_fraction</ul>
MQ12 | What program blocks are immediately upstream of `generate_netcdf_file_for_Grass_fraction`? | <ul><li>initialize_Grass_Matrix <li>fetch_SYNMAP_land_cover_map_variable</ul>
MQ13 | What program blocks are upstream of `generate_netcdf_file_for_C3_fraction`? | <ul> <li>examine_pixels_for_grass <li>fetch_monthly_mean_precipitation_data <li>fetch_monthly_mean_air_temperature_data <li>fetch_SYNMAP_land_cover_map_variable</ul>
MQ14 | What program blocks are anywhere downstream of `fetch_monthly_mean_precipitation_data`? | <ul><li>generate_netcdf_file_for_C4_fraction <li>generate_netcdf_file_for_C3_fraction <li>examine_pixels_for_grass</ul>
MQ15 | What data is immediately downstream of `Tair_Matrix`? | <ul><li>C4_Data <li>C3_Data</ul>
MQ16 | What data is immediately upstream of `Grass_fraction_data`? | <ul><li>Grass_variable <li>lat_bnds_variable <li>lon_bnds_variable <li>lat_variable <li>lon_variable </ul>
MQ17 | What data is downstream of `Rain_Matrix`? | <ul><li>C4_Data <li>C3_Data <li>C4_fraction_data <li>C3_fraction_data</ul>
MQ18 | What data is upstream of `SYNMAP_land_cover_map_variable`? |
MQ19 | What URI variables are associated with reads of data `mean_airtemp`? | <ul><li>month <li>end_year <li>start_year</ul>
MQ20 | What URI variables do data read into `mean_precip` and `mean_airtemp` have in common? | <ul><li>start_year <li>end_year <li>month </ul>

#### Queries about a run of the script (retrospective provenance)
 | Query | Result
-|-------|--------
RQ1| What input files were used to compose the precipitation array `Rain_Matrix`? | <ul><li>inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.6.nc <li>inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.10.nc <li>inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.3.nc <li>inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.7.nc <li>inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.11.nc <li>inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.4.nc <li>inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.8.nc <li>inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.12.nc <li>inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.5.nc <li>inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.1.nc <li>inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.9.nc <li>inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.2.nc</ul>
RQ2 | How many input files were used to compose the air temperature array `Tair_Matrix`? | 12
RQ3 | What input files provided the data used to derive the workflow output `Grass_fraction_data`? | inputs/land_cover/SYNMAP_NA_QD.nc
RQ4 | What is the range of years over which the data in the `mean_precip` input files were collected? | StartYear=2000, EndYear=2010
RQ5 | What months of the year do the mean_airtemp input files correspond to? | <ul><li>7 <li>2<li>3<li>5<li>8<li>10<li>4<li>9<li>11<li>1<li>6<li>12</ul>










