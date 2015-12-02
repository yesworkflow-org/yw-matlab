

function export_run(run_prov_dir, script_exec_dir)

    EOL = '\n';

    % use the current directory as the run provenance directory if no argument passed
    if nargin < 1 || strcmp(run_prov_dir, '.')
        run_prov_dir = pwd;
    end

    if nargin < 2 || strcmp(script_exec_dir, '.')
        script_exec_dir = pwd;
    end
    [one,script_exec_dir_length] = size(script_exec_dir);

    disp(script_exec_dir_length)

    % load the execution object
    [run_parent_directory,run_id] = fileparts(run_prov_dir);
    executionWorkspaceFile = [run_parent_directory '/' run_id '/' run_id '.mat'];
    load(executionWorkspaceFile);

    fileID = fopen('run.yaml','w');
    
    fprintf(fileID, [EOL 'inputs:' EOL]);
    for input_id=executionObj.execution_input_ids
        input_object = executionObj.execution_objects(input_id{1});
        input_file_path = input_object.get('full_file_path');
        if strncmpi(input_file_path, script_exec_dir, script_exec_dir_length)
            r = script_exec_dir_length;
        else
            r = 1;
        end
        fprintf(fileID, ['  - ' input_file_path(r+1:end) EOL]);
    end
    
    fprintf(fileID, [EOL 'outputs:' EOL]);
    for output_id=executionObj.execution_output_ids
        output_object = executionObj.execution_objects(output_id{1});
        output_file_path = output_object.get('full_file_path');
        if strncmpi(output_file_path, script_exec_dir, script_exec_dir_length)
            r = script_exec_dir_length;
        else
            r = 1;
        end
        fprintf(fileID, ['  - ' output_file_path(r+1:end) EOL]);
    end

    fclose(fileID);
    
end