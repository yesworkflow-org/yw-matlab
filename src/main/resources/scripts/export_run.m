

function export_run()

    % load the yw.properties file
    prop_file_path = [pwd '/yw.properties'];
    fileReader = java.io.FileReader(java.lang.String(prop_file_path));
    properties = java.util.Properties();
    properties.load(fileReader);
    fileReader.close();

    % use properties or default values to configure export
    run_base_dir = get_property_or_default(properties, 'recon.rundir', '.');
    provdir = get_property_or_default(properties, 'matlab.provdir', '.');
    runid = get_property_or_default(properties, 'matlab.runid', '1');
    export_file = get_property_or_default(properties, 'matlab.exportfile', 'run.yaml');

    % load the execution workspace for the run
    executionWorkspaceFile = [provdir '/runs/' runid '/' runid '.mat'];
    load(executionWorkspaceFile);
    
    % write base-relative file paths of input and output files for run
    fileID = fopen(export_file,'w');
    run_base_path = char(java.io.File(run_base_dir).getCanonicalPath());
    write_file_paths(fileID, 'inputs', run_base_path, executionObj, executionObj.execution_input_ids);
    write_file_paths(fileID, 'outputs', run_base_path, executionObj, executionObj.execution_output_ids);
    fclose(fileID);
end

function [value] = get_property_or_default(properties, propertyName, defaultValue)
    if (properties.containsKey(propertyName))
        value = properties.get(propertyName);
    else 
        value = defaultValue;
    end
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
        base_relative_path = full_path(base_dir_length+2:end);
    else
        base_relative_path = full_path;
    end
end