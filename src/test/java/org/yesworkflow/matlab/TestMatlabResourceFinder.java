package org.yesworkflow.matlab;

import org.yesworkflow.YesWorkflowTestCase;
import org.yesworkflow.data.UriTemplate;
import org.yesworkflow.recon.ResourceFinder;
import org.yesworkflow.recon.ResourceFinder.ResourceRole;

public class TestMatlabResourceFinder extends YesWorkflowTestCase {

    private ResourceFinder finder;
    
    @Override
    public void setUp() {
        finder = new MatlabResourceFinder();
    }
    
    private String fileNameOnlyYamlString = 
            "inputFiles:"       + EOL +
            "- infile1.txt"     + EOL +
            "- infile2.txt"     + EOL +
            "- infile3.dat"     + EOL +
            "- infile4.dat"     + EOL +
            ""                  + EOL +
            "outputFiles:"      + EOL +
            "- outfile1.txt"    + EOL +
            "- outfile2.txt"    + EOL +
            "- outfile3.png"    + EOL;

    private String relativePathsYamlString = 
            "inputFiles:"       + EOL +
            "- inputs/text/infile1.txt"     + EOL +
            "- inputs/text/infile2.txt"     + EOL +
            "- inputs/data/infile3.dat"     + EOL +
            "- inputs/data/infile4.dat"     + EOL +
            ""                              + EOL +
            "outputFiles:"                  + EOL +
            "- outputs/text/outfile1.txt"   + EOL +
            "- outputs/text/outfile2.txt"   + EOL +
            "- outputs/images/outfile3.png" + EOL;
    
    private String relativePathsWithBaseYamlString = 
            "inputFiles:"                       + EOL +
            "- run/inputs/text/infile1.txt"     + EOL +
            "- run/inputs/text/infile2.txt"     + EOL +
            "- run/inputs/data/infile3.dat"     + EOL +
            "- run/inputs/data/infile4.dat"     + EOL +
            ""                                  + EOL +
            "outputFiles:"                      + EOL +
            "- run/outputs/text/outfile1.txt"   + EOL +
            "- run/outputs/text/outfile2.txt"   + EOL +
            "- run/outputs/images/outfile3.png" + EOL;

    
    
    public void testYamlString_FileNameOnly_AllInputs() throws Exception {
        finder.configure("yamlstring", fileNameOnlyYamlString);
        assertEquals("[infile1.txt, infile2.txt, infile3.dat, infile4.dat]", 
            finder.findResources("", new UriTemplate("{filename}"), ResourceRole.INPUT).toString());
    }

    public void testYamlString_FileNameOnly_InputsDistingishedByExtension() throws Exception {
        finder.configure("yamlstring", fileNameOnlyYamlString);
        assertEquals("[infile1.txt, infile2.txt]", 
            finder.findResources("", new UriTemplate("{name}.txt"), ResourceRole.INPUT).toString());
        assertEquals("[infile3.dat, infile4.dat]", 
                finder.findResources("", new UriTemplate("{name}.dat"), ResourceRole.INPUT).toString());
    }

    public void testYamlString_FileNameOnly_InputsDistingishedByExtension_WithVariable() throws Exception {
        finder.configure("yamlstring", fileNameOnlyYamlString);
        assertEquals("[infile1.txt, infile2.txt]", 
            finder.findResources("", new UriTemplate("infile{num}.txt"), ResourceRole.INPUT).toString());
        assertEquals("[infile3.dat, infile4.dat]", 
                finder.findResources("", new UriTemplate("infile{num}.dat"), ResourceRole.INPUT).toString());
    }
    
    public void testYamlString_FileNameOnly_AllOutputs() throws Exception {
        finder.configure("yamlstring", fileNameOnlyYamlString);
        assertEquals("[outfile1.txt, outfile2.txt, outfile3.png]", 
            finder.findResources("", new UriTemplate("{filename}"), ResourceRole.OUTPUT).toString());
    }
    
    public void testYamlString_FileNameOnly_OutputsDistingishedByExtension() throws Exception {
        finder.configure("yamlstring", fileNameOnlyYamlString);
        assertEquals("[outfile1.txt, outfile2.txt]", 
            finder.findResources("", new UriTemplate("{name}.txt"), ResourceRole.OUTPUT).toString());
        assertEquals("[outfile3.png]", 
                finder.findResources("", new UriTemplate("{name}.png"), ResourceRole.OUTPUT).toString());
    }

    public void testYamlString_FileNameOnly_OutputsDistingishedByExtension_WithVariable() throws Exception {
        finder.configure("yamlstring", fileNameOnlyYamlString);
        assertEquals("[outfile1.txt, outfile2.txt]", 
            finder.findResources("", new UriTemplate("outfile{num}.txt"), ResourceRole.OUTPUT).toString());
        assertEquals("[outfile3.png]", 
                finder.findResources("", new UriTemplate("outfile{num}.png"), ResourceRole.OUTPUT).toString());
    }
    
    public void testYamlString_FileNameOnly_AllResources() throws Exception {
        finder.configure("yamlstring", fileNameOnlyYamlString);
        assertEquals("[infile1.txt, infile2.txt, infile3.dat, infile4.dat, outfile1.txt, outfile2.txt, outfile3.png]", 
            finder.findResources("", new UriTemplate("{filename}"), ResourceRole.INPUT_OR_OUTPUT).toString());
    }

    public void testYamlString_FileNameOnly_AllResources_DistingishedByExtension() throws Exception {
        finder.configure("yamlstring", fileNameOnlyYamlString);
        assertEquals("[infile1.txt, infile2.txt, outfile1.txt, outfile2.txt]", 
            finder.findResources("", new UriTemplate("{name}.txt"), ResourceRole.INPUT_OR_OUTPUT).toString());
        assertEquals("[infile3.dat, infile4.dat]", 
                finder.findResources("", new UriTemplate("{name}.dat"), ResourceRole.INPUT_OR_OUTPUT).toString());
    }
    
    public void testYamlString_FileNameOnly_Inputs_NoMatch() throws Exception {
        finder.configure("yamlstring", fileNameOnlyYamlString);
        assertEquals("[]", finder.findResources("", new UriTemplate("infile{num}.jpg"), ResourceRole.INPUT).toString());
    }

    public void testYamlString_FileNameOnly_Outputs_NoMatch() throws Exception {
        finder.configure("yamlstring", fileNameOnlyYamlString);
        assertEquals("[]", finder.findResources("", new UriTemplate("outfile{num}.jpg"), ResourceRole.OUTPUT).toString());
    }

    public void testYamlString_FileNameOnly_AllResources_NoMatch() throws Exception {
        finder.configure("yamlstring", fileNameOnlyYamlString);
        assertEquals("[]", finder.findResources("", new UriTemplate("{prefix}file{num}.jpg"), ResourceRole.INPUT_OR_OUTPUT).toString());
    }

    public void testYamlString_RelativePaths_AllInputs() throws Exception {
        finder.configure("yamlstring", relativePathsYamlString);
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt, inputs/data/infile3.dat, inputs/data/infile4.dat]", 
            finder.findResources("", new UriTemplate("{filename}"), ResourceRole.INPUT).toString());
    }

    public void testYamlString_RelativePaths_InputsDistingishedByExtension() throws Exception {
        finder.configure("yamlstring", relativePathsYamlString);
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt]",
            finder.findResources("", new UriTemplate("inputs/text/{name}.txt"), ResourceRole.INPUT).toString());
        assertEquals("[inputs/data/infile3.dat, inputs/data/infile4.dat]",
                finder.findResources("", new UriTemplate("inputs/data/{name}.dat"), ResourceRole.INPUT).toString());
    }

    public void testYamlString_RelativePaths_OutputsDistingishedByExtension() throws Exception {
        finder.configure("yamlstring", relativePathsYamlString);
        assertEquals("[outputs/text/outfile1.txt, outputs/text/outfile2.txt]",
            finder.findResources("", new UriTemplate("outputs/text/{name}.txt"), ResourceRole.OUTPUT).toString());
        assertEquals("[outputs/images/outfile3.png]",
                finder.findResources("", new UriTemplate("outputs/images/{name}.png"), ResourceRole.OUTPUT).toString());
    }
    
    public void testYamlString_RelativePaths_AllResources_DistingishedByExtension() throws Exception {
        finder.configure("yamlstring", relativePathsYamlString);
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt, outputs/text/outfile1.txt, outputs/text/outfile2.txt]", 
            finder.findResources("", new UriTemplate("{types}/text/{name}.txt"), ResourceRole.INPUT_OR_OUTPUT).toString());
    }

    public void testYamlString_RelativePaths_AllResources_NoMatch() throws Exception {
        finder.configure("yamlstring", relativePathsYamlString);
        assertEquals("[]", finder.findResources("", new UriTemplate("{types}/docs/{name}.txt"), ResourceRole.INPUT_OR_OUTPUT).toString());
    }
    
    public void testYamlString_RelativePathsWithBase_AllInputs() throws Exception {
        finder.configure("yamlstring", relativePathsWithBaseYamlString);
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt]", 
            finder.findResources("run/", new UriTemplate("inputs/text/{name}.txt"), ResourceRole.INPUT).toString());
    }
}
