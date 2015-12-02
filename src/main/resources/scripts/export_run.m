

function export_run(run_prov_dir, run_base_dir)

    % use the current directory as the run provenance directory if no
    % argument provided
    if nargin < 1 || strcmp(run_prov_dir, '.')
        run_prov_dir = pwd;
    end

    % use the current directory as the run base directory if second
    % argument not provided
    if nargin < 2 || strcmp(run_base_dir, '.')
        run_base_dir = pwd;
    end

    % load the execution object
    [run_parent_directory,run_id] = fileparts(run_prov_dir);
    executionWorkspaceFile = [run_parent_directory '/' run_id '/' run_id '.mat'];
    load(executionWorkspaceFile);

    % write base-relative file paths of input and output files for run
    fileID = fopen('run.yaml','w');
    write_file_paths(fileID, 'inputs', run_base_dir, executionObj, executionObj.execution_input_ids);
    write_file_paths(fileID, 'outputs', run_base_dir, executionObj, executionObj.execution_output_ids);
    fclose(fileID);
    
end

function write_file_paths(fileID, section_name, base_dir, executionObj, file_object_ids)

    fprintf(fileID, ['\n' section_name ':' '\n']);
    
    for object_id=file_object_ids
        execution_object = executionObj.execution_objects(object_id{1});
        full_file_path = execution_object.get('full_file_path');
        base_relative_path = get_base_relative_path(base_dir, full_file_path);
        fprintf(fileID, ['  - ' base_relative_path '\n']);
    end

end
    
function [base_relative_path] = get_base_relative_path(base_dir, full_path)

    [one,base_dir_length] = size(base_dir);
    
    if strncmpi(full_path, base_dir, base_dir_length)
        base_relative_path = full_path(base_dir_length+1:end);
    else
        base_relative_path = full_path;
    end
    
end