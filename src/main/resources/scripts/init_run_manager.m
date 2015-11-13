% Creates a run manager corresponding to the provided configuration
% directory or the current directory if no argument is provided.
function [run_mgr] = init_run_manager(run_manager_config_dir)

    % use the current directory as the config directory if no argument passed
    if nargin == 0
        run_manager_config_dir = pwd;
    end

    % create a configuration corresponding to the given directory
    import org.dataone.client.configure.Configuration;
    run_manager_config = Configuration('configuration_directory', run_manager_config_dir);
    
    % create a run manager associated with the configuration
    import org.dataone.client.run.RunManager;
    run_mgr = RunManager.getInstance(run_manager_config);

end
