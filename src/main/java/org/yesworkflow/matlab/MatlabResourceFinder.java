package org.yesworkflow.matlab;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.Yaml;
import org.yesworkflow.data.UriTemplate;
import org.yesworkflow.recon.ResourceFinder;

public class MatlabResourceFinder implements ResourceFinder {
    
    private Path basePath;
    private Collection<String> matchingURIs;
    private Map<String,Object> runRecords;
    private List<String> inputFiles = null;
    private List<String> outputFiles = null;

    @Override
    public MatlabResourceFinder configure(Map<String, Object> config) throws Exception {
        return this;
    }

    @Override
    public MatlabResourceFinder configure(String key, Object value) throws Exception {
        if (key.equalsIgnoreCase("yamlstring")) {
            String yamlString = (String)value;
            parseYamlString(yamlString);
        }
        
        return this;
    }
    
    @Override
    public Collection<String> findResources(String baseUri, UriTemplate uriTemplate, ResourceRole role) {
        this.basePath = Paths.get(baseUri);
        matchingURIs = new LinkedHashSet<String>();
        Pattern pattern = uriTemplate.getRegexpPattern();
        
        if (role == ResourceRole.INPUT || role == ResourceRole.INPUT_OR_OUTPUT) {
            addMatches(baseUri, pattern, inputFiles, matchingURIs);
        }

        if (role == ResourceRole.OUTPUT || role == ResourceRole.INPUT_OR_OUTPUT) {
            addMatches(baseUri, pattern, outputFiles, matchingURIs);
        }
        
        return matchingURIs;
    }

    private void addMatches(String baseUri, Pattern pattern, List<String> resourceURIs,  Collection<String> matchingURIs) {
        if (resourceURIs == null) return;
        for (String uri : resourceURIs) {
            if (!uri.startsWith(baseUri)) continue;
            uri = uri.substring(baseUri.length());
            if (pattern.matcher(uri).matches()) {
                matchingURIs.add(uri);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private void parseYamlString(String yamlString) {
        runRecords = (Map<String,Object>) (new Yaml()).load(yamlString);;
        inputFiles = (List<String>) runRecords.get("inputFiles");
        outputFiles = (List<String>) runRecords.get("outputFiles");
   }
}