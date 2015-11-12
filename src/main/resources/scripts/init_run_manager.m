import org.dataone.client.configure.Configuration;
import org.dataone.client.run.RunManager;

% prompt for the configuration directory
run_manager_config_dir = input('Configuration directory: ', 's');

% create a run manager associated with the given configuration directory
run_manager_config = Configuration('configuration_directory', run_manager_config_dir);
run_mgr = RunManager.getInstance(run_manager_config);
clear run_manager_config_dir run_manager_config


