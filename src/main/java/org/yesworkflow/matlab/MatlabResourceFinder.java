package org.yesworkflow.matlab;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.yaml.snakeyaml.Yaml;
import org.yesworkflow.data.UriTemplate;
import org.yesworkflow.recon.ResourceFinder;

public class MatlabResourceFinder implements ResourceFinder {
    
    private static String INPUTS_LIST_NAME = "inputs";
    private static String OUTPUTS_LIST_NAME = "outputs";
            
    private Collection<String> matchingUris;
    private Map<String,Object> runRecords;
    private List<String> inputFiles = null;
    private List<String> outputFiles = null;
    private boolean findResourceOnce = false;
    
    @Override
    public MatlabResourceFinder configure(Map<String, Object> config) throws Exception {
        if (config != null) {
            for (Map.Entry<String, Object> entry : config.entrySet()) {
                configure(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public MatlabResourceFinder configure(String key, Object value) throws Exception {
        if (key.equalsIgnoreCase("yamlstring")) {
            parseYamlString((String)value);
        } else if (key.equalsIgnoreCase("exportfile")) {
           parseYamlFile((String)value);
        } else if (key.equalsIgnoreCase("matlab")) {
            configure((Map<String,Object>)value);
        } else if (key.equalsIgnoreCase("matchonce")) {
            findResourceOnce = ((String)value).equalsIgnoreCase("true");
        }

        return this;
    }
    
    @Override
    public Collection<String> findMatchingResources(String baseUri, UriTemplate uriTemplate, ResourceRole role) {
        matchingUris = new LinkedHashSet<String>();
        Pattern pattern = uriTemplate.getRegexpPattern();
        if (role == ResourceRole.INPUT || role == ResourceRole.INPUT_OR_OUTPUT) {
            addMatches(baseUri, pattern, inputFiles, matchingUris);
        }
        if (role == ResourceRole.OUTPUT || role == ResourceRole.INPUT_OR_OUTPUT) {
            addMatches(baseUri, pattern, outputFiles, matchingUris);
        }
        return matchingUris;
    }

    private void addMatches(String baseUri, Pattern pattern, List<String> resourceUris, Collection<String> matchingUris) {
        if (resourceUris == null) return;
        List<String> usedUris = new LinkedList<String>();
        for (String uri : resourceUris) {
            String unbasedUri = (uri.startsWith(baseUri)) ? uri.substring(baseUri.length()) : uri;
            if (pattern.matcher(unbasedUri).matches()) {
                matchingUris.add(unbasedUri);
                usedUris.add(uri);
            }
        }
        
        if (findResourceOnce) {
            resourceUris.removeAll(usedUris);
        }
    }

    public Collection<String> findUnmatchedResources(String baseUri, ResourceRole role) {
        Collection<String> unmatchedUris = new LinkedHashSet<String>();
        if (role == ResourceRole.INPUT || role == ResourceRole.INPUT_OR_OUTPUT) {
            addNonMatches(baseUri, inputFiles, unmatchedUris);
        }
        if (role == ResourceRole.OUTPUT || role == ResourceRole.INPUT_OR_OUTPUT) {
            addNonMatches(baseUri, outputFiles, unmatchedUris);
        }
        return unmatchedUris;
    }
    
    private void addNonMatches(String baseUri, List<String> resourceUris, Collection<String> nonMatchingUris) {
        if (resourceUris == null) return;
        for (String uri : resourceUris) {
            String unbasedUri = (uri.startsWith(baseUri)) ? uri.substring(baseUri.length()) : uri;
            nonMatchingUris.add(unbasedUri);
        }
        if (findResourceOnce) {
            resourceUris.clear();
        }
    }
    
    @SuppressWarnings("unchecked")
    private void parseYamlString(String yamlString) {
        runRecords = (Map<String,Object>) (new Yaml()).load(yamlString);
        extractRunRecords();
    }
    
    @SuppressWarnings("unchecked")
    private void parseYamlFile(String path) throws FileNotFoundException {
        InputStream stream = new FileInputStream(path);
        runRecords = (Map<String,Object>) (new Yaml()).load(stream);
        extractRunRecords();
    }
    
    @SuppressWarnings("unchecked")
    private void extractRunRecords() {
        inputFiles = (List<String>) runRecords.get(INPUTS_LIST_NAME);
        outputFiles = (List<String>) runRecords.get(OUTPUTS_LIST_NAME);
    }
    
}