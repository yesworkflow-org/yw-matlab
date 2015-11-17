YesWorkflow-MATLAB
==================

Overview
--------

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





