YesWorkflow-MATLAB
==================

The __yw-matlab__ module will contain [YesWorkflow](https://github.com/yesworkflow-org/yw-prototypes) (YW) modules and command line tools for interacting with the [DataONE MATLAB RunManager](https://github.com/DataONEorg/matlab-dataone). These tools will make the RunManager's records of script runs and data provenance available to YW so that this retrospective provenance information can be queried in terms of, and in combination with, the prospective provenance revealed by YW annotations in the MATLAB scripts.

This repository also contains example MATLAB scripts marked up with YW annotations, the products of running YW on the scripts, and example prospective and retrospective queries of the provenance records jointly created by YW and the DataONE RunManager for runs of each script.

#### Layout of repository

Directory | Description
----------|------------
[src/main/resources/scripts](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/scripts) | MATLAB scripts for working with the RunManager and exporting provenance records for importing into YesWorkflow.
[src/main/resources/examples](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples) | Example scripts and associated data files, provenance records, and results of prospective and retrospective provenance queries for runs of each script.


Examples
--------

### C3_C4_mapping

The example script and data files currently used to exercise the tools in this repository are based on those used in the  [DataONE MATLAB Toolbox Walk-through](https://github.com/DataONEorg/matlab-dataone/blob/master/docs/walk-through.rst).  

Files for this example are located at [src/main/resources/examples/C3_C4_mapping](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping). Layout of directories and files within this example directory:

Directory or file | Description
------------------|------------
[C3_C4_map_present_NA.m](https://github.com/yesworkflow-org/yw-matlab/blob/master/src/main/resources/examples/C3_C4_mapping/C3_C4_map_present_NA.m) | The MATLAB script marked up with YW annotations.
[inputs/](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/inputs) | Script input data files.
[outputs/](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/outputs) | Outputs from one run of the script.
[yw/](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw) | YesWorkflow configuration and output files.
[yw/xsb/extractfacts.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/extractfacts.P), [yw/xsb/modelfacts.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/modelfacts.P), [yw/xsb/reconfacts.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/reconfacts.P)  | Fact files created by YesWorkflow and containing the prospective and retrospective provenance captured by YW.
[yw/xsb/rules.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/rules.P) | Logic rules for querying the YW-written facts.
[yw/xsb/extract_queries.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/extract_queries.P), [yw/xsb/model_queries.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/model_queries.P), [yw/xsb/recon_queries.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/recon_queries.P)| Queries employing the rules defined in rules.P.
[yw/xsb/run_queries.sh](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/run_queries.sh) | Bash script for running all of the queries.
[yw/xsb/run_queries.txt](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/run_queries.txt) | The output produced by running all of the queries using run_queries.sh.

#### YesWorkflow combined view of script

![Combined YW view of C3_C4_map_present_NA.m](https://raw.githubusercontent.com/yesworkflow-org/yw-matlab/master/src/main/resources/examples/C3_C4_mapping/yw/combined.png)

#### YW provenance queries 

The following is a summary of the queries posed by running [yw/xsb/run_queries.sh](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/run_queries.sh) and the corresponding results.

#### Queries about the script and the YW annotations extracted from it
See [extract_queries.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/extract_queries.P) for definitions of the queries.

    | Query | Result
----|-------|--------
EQ1 | What source files were YW annotations extracted from?   | `../C3_C4_map_present_NA.m`
EQ2 | What are the names of all program blocks in the script? | `generate_netcdf_file_for_C3_fraction`, `fetch_monthly_mean_precipitation_data`, `C3_C4_map_present_NA`, `examine_pixels_for_grass`, `generate_netcdf_file_for_C4_fraction`, `fetch_SYNMAP_land_cover_map_variable`, `generate_netcdf_file_for_Grass_fraction`, `fetch_monthly_mean_air_temperature_data`, `initialize_Grass_Matrix`
EQ3 | What out ports are qualified with URIs? | `Grass_fraction_data`, `C4_fraction_data`, `C3_fraction_data`

#### Queries about the workflow model of the script (prospective provenance)
See [model_queries.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/model_queries.P) for definitions of the queries.

    | Query | Result
----|-------|--------
MQ1 | Where is the definition of program block `fetch_monthly_mean_precipitation_data`? | SourceFile=`../C3_C4_map_present_NA.m`, StartLine=`59`, EndLine=`69`
MQ2 | What is the name of the top-level workflow? | `C3_C4_map_present_NA`
MQ3 | What are the names of the program blocks comprising the workflow? | `generate_netcdf_file_for_Grass_fraction`, `generate_netcdf_file_for_C4_fraction`, `generate_netcdf_file_for_C3_fraction`, `examine_pixels_for_grass`, `initialize_Grass_Matrix`, `fetch_monthly_mean_precipitation_data`, `fetch_monthly_mean_air_temperature_data`, `fetch_SYNMAP_land_cover_map_variable`
MQ4 | What are the names of the program blocks in the workflow that produce workflow outputs? | `generate_netcdf_file_for_Grass_fraction`, `generate_netcdf_file_for_C4_fraction`, `generate_netcdf_file_for_C3_fraction`
MQ5 | What are the inputs to the script? | `mean_precip`, `mean_airtemp`, `SYNMAP_land_cover_map_data`
MQ6 | What data is output by program block `examine_pixels_for_grass`? | `C4_Data`, `C3_Data`
MQ7 | What program blocks provide input directly to `generate_netcdf_file_for_Grass_fraction`? | `initialize_Grass_Matrix`, `fetch_SYNMAP_land_cover_map_variable`
MQ8 | What programs have input ports that receive data `lon_variable`? | `generate_netcdf_file_for_Grass_fraction`, `generate_netcdf_file_for_C4_fraction`, `generate_netcdf_file_for_C3_fraction`
MQ9 | How many ports read data `lat_variable`? | `3`
MQ10 | How many data are read by more than port in workflow `C3_C4_map_present_NA`? | `4`
MQ11 | What program blocks are immediately downstream of `examine_pixels_for_grass`? | `generate_netcdf_file_for_C4_fraction`, `generate_netcdf_file_for_C3_fraction`
MQ12 | What program blocks are immediately upstream of `generate_netcdf_file_for_Grass_fraction`? | `initialize_Grass_Matrix`, `fetch_SYNMAP_land_cover_map_variable`
MQ13 | What program blocks are upstream of `generate_netcdf_file_for_C3_fraction`? | `examine_pixels_for_grass`, `fetch_monthly_mean_precipitation_data`, `fetch_monthly_mean_air_temperature_data`, `fetch_SYNMAP_land_cover_map_variable`
MQ14 | What program blocks are anywhere downstream of `fetch_monthly_mean_precipitation_data`? | `generate_netcdf_file_for_C4_fraction`, `generate_netcdf_file_for_C3_fraction`, `examine_pixels_for_grass`
MQ15 | What data is immediately downstream of `Tair_Matrix`? | `C4_Data`, `C3_Data`
MQ16 | What data is immediately upstream of `Grass_fraction_data`? | `Grass_variable`, `lat_bnds_variable`, `lon_bnds_variable`, `lat_variable`, `lon_variable`
MQ17 | What data is downstream of `Rain_Matrix`? | `C4_Data`, `C3_Data`, `C4_fraction_data`, `C3_fraction_data`
MQ18 | What data is upstream of `SYNMAP_land_cover_map_variable`? |
MQ19 | What URI variables are associated with reads of data `mean_airtemp`? | `month`, `end_year`, `start_year`
MQ20 | What URI variables do data read into `mean_precip` and `mean_airtemp` have in common? | `start_year`, `end_year`, `month`

#### Queries about a run of the script (retrospective provenance)
See [recon_queries.P](https://github.com/yesworkflow-org/yw-matlab/tree/master/src/main/resources/examples/C3_C4_mapping/yw/xsb/recon_queries.P) for definitions of the queries.

    | Query | Result
----|-------|--------
RQ1| What input files were used to compose the precipitation array `Rain_Matrix`? | `inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.6.nc`,  `inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.10.nc`,  `inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.3.nc`,  `inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.7.nc`,  `inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.11.nc`,  `inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.4.nc`,  `inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.8.nc`,  `inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.12.nc`,  `inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.5.nc`,  `inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.1.nc`,  `inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.9.nc`,  `inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.2.nc`
RQ2 | How many input files were used to compose the air temperature array `Tair_Matrix`? | `12`
RQ3 | What input files provided the data used to derive the workflow output `Grass_fraction_data`? | `inputs/land_cover/SYNMAP_NA_QD.nc`
RQ4 | What is the range of years over which the data in the `mean_precip` input files were collected? | StartYear=`2000`, EndYear=`2010`
RQ5 | What months of the year do the mean_airtemp input files correspond to? | `7`, `2`, `3`,`5`,`8`,`10`,`4`,`9`,`11`,`1`,`6`,`12`</ul>
