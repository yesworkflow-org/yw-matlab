package org.yesworkflow.matlab;

import org.yesworkflow.YesWorkflowTestCase;
import org.yesworkflow.config.YWConfiguration;
import org.yesworkflow.data.UriTemplate;
import org.yesworkflow.recon.ResourceFinder;
import org.yesworkflow.recon.ResourceFinder.ResourceRole;

public class TestMatlabResourceFinder extends YesWorkflowTestCase {

    private static String TEST_RESOURCE_DIR = "src/test/resources/org/yesworkflow/matlab/tests/TestMatlabResourceFinder/";
    private static String EMPTY_BASE_DIR = "";
    
    private YWConfiguration config;
    private ResourceFinder finder;
    
    @Override
    public void setUp() throws Exception {
        config = new YWConfiguration();
        finder = new MatlabResourceFinder().configure("matchonce", "true");
    }
    
    private String fileNameOnlyYamlString = 
            "inputs:"           + EOL +
            "- infile1.txt"     + EOL +
            "- infile2.txt"     + EOL +
            "- infile3.dat"     + EOL +
            "- infile4.dat"     + EOL +
            ""                  + EOL +
            "outputs:"          + EOL +
            "- outfile1.txt"    + EOL +
            "- outfile2.txt"    + EOL +
            "- outfile3.png"    + EOL;

    private String relativePathsYamlString = 
            "inputs:"                       + EOL +
            "- inputs/text/infile1.txt"     + EOL +
            "- inputs/text/infile2.txt"     + EOL +
            "- inputs/data/infile3.dat"     + EOL +
            "- inputs/data/infile4.dat"     + EOL +
            ""                              + EOL +
            "outputs:"                      + EOL +
            "- outputs/text/outfile1.txt"   + EOL +
            "- outputs/text/outfile2.txt"   + EOL +
            "- outputs/images/outfile3.png" + EOL;

    private String pathsWithBaseYamlString = 
            "inputs:"                           + EOL +
            "- run/inputs/text/infile1.txt"     + EOL +
            "- run/inputs/text/infile2.txt"     + EOL +
            "- run/inputs/data/infile3.dat"     + EOL +
            "- run/inputs/data/infile4.dat"     + EOL +
            "- /data/tmcphill/infile5.png"      + EOL +
            "- /data/tmcphill/infile6.png"      + EOL +
            "- /data/tmcphill/infile7.png"      + EOL +
            ""                                  + EOL +
            "outputs:"                          + EOL +
            "- run/outputs/text/outfile1.txt"   + EOL +
            "- run/outputs/text/outfile2.txt"   + EOL +
            "- run/outputs/images/outfile3.png" + EOL +
            "- /tmp/tmcphill/3513jj.tmp"        + EOL +
            "- /tmp/tmcphill/456lfm.tmp"        + EOL +
            "- /tmp/tmcphill/2235ss.tmp"        + EOL;
    
    public void testYamlString_FileNameOnly_AllInputs() throws Exception {
        config.set("recon.matlab.yamlstring", fileNameOnlyYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[infile1.txt, infile2.txt, infile3.dat, infile4.dat]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{filename}"), ResourceRole.INPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }

    public void testYamlString_FileNameOnly_InputsDistingishedByExtension() throws Exception {
        config.set("recon.matlab.yamlstring", fileNameOnlyYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[infile1.txt, infile2.txt]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{name}.txt"), ResourceRole.INPUT).toString());
        assertEquals("[infile3.dat, infile4.dat]", 
                finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{name}.dat"), ResourceRole.INPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }

    public void testYamlString_FileNameOnly_InputsDistingishedByExtension_WithVariable() throws Exception {
        config.set("recon.matlab.yamlstring", fileNameOnlyYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[infile1.txt, infile2.txt]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("infile{num}.txt"), ResourceRole.INPUT).toString());
        assertEquals("[infile3.dat, infile4.dat]", 
                finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("infile{num}.dat"), ResourceRole.INPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }
    
    public void testYamlString_FileNameOnly_AllOutputs() throws Exception {
        config.set("recon.matlab.yamlstring", fileNameOnlyYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[outfile1.txt, outfile2.txt, outfile3.png]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{filename}"), ResourceRole.OUTPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.OUTPUT).toString());
    }
    
    public void testYamlString_FileNameOnly_OutputsDistingishedByExtension() throws Exception {
        config.set("recon.matlab.yamlstring", fileNameOnlyYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[outfile1.txt, outfile2.txt]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{name}.txt"), ResourceRole.OUTPUT).toString());
        assertEquals("[outfile3.png]", 
                finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{name}.png"), ResourceRole.OUTPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.OUTPUT).toString());
    }

    public void testYamlString_FileNameOnly_OutputsDistingishedByExtension_WithVariable() throws Exception {
        config.set("recon.matlab.yamlstring", fileNameOnlyYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[outfile1.txt, outfile2.txt]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("outfile{num}.txt"), ResourceRole.OUTPUT).toString());
        assertEquals("[outfile3.png]", 
                finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("outfile{num}.png"), ResourceRole.OUTPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.OUTPUT).toString());
    }
    
    public void testYamlString_FileNameOnly_AllResources() throws Exception {
        config.set("recon.matlab.yamlstring", fileNameOnlyYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[infile1.txt, infile2.txt, infile3.dat, infile4.dat, outfile1.txt, outfile2.txt, outfile3.png]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{filename}"), ResourceRole.INPUT_OR_OUTPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT_OR_OUTPUT).toString());
    }

    public void testYamlString_FileNameOnly_AllResources_DistingishedByExtension() throws Exception {
        config.set("recon.matlab.yamlstring", fileNameOnlyYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[infile1.txt, infile2.txt, outfile1.txt, outfile2.txt]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{name}.txt"), ResourceRole.INPUT_OR_OUTPUT).toString());
        assertEquals("[infile3.dat, infile4.dat]", 
                finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{name}.dat"), ResourceRole.INPUT_OR_OUTPUT).toString());
        assertEquals("[outfile3.png]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT_OR_OUTPUT).toString());
    }
    
    public void testYamlString_FileNameOnly_Inputs_NoMatch() throws Exception {
        config.set("recon.matlab.yamlstring", fileNameOnlyYamlString); 
        finder.configure(config.getSection("recon"));
        assertEquals("[]", finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("infile{num}.jpg"), ResourceRole.INPUT).toString());
        assertEquals("[infile1.txt, infile2.txt, infile3.dat, infile4.dat]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }

    public void testYamlString_FileNameOnly_Outputs_NoMatch() throws Exception {
        config.set("recon.matlab.yamlstring", fileNameOnlyYamlString); 
        finder.configure(config.getSection("recon"));
        assertEquals("[]", finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("outfile{num}.jpg"), ResourceRole.OUTPUT).toString());
        assertEquals("[outfile1.txt, outfile2.txt, outfile3.png]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.OUTPUT).toString());
    }

    public void testYamlString_FileNameOnly_AllResources_NoMatch() throws Exception {
        config.set("recon.matlab.yamlstring", fileNameOnlyYamlString); 
        finder.configure(config.getSection("recon"));
        assertEquals("[]", finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{prefix}file{num}.jpg"), ResourceRole.INPUT_OR_OUTPUT).toString());
        assertEquals("[infile1.txt, infile2.txt, infile3.dat, infile4.dat, outfile1.txt, outfile2.txt, outfile3.png]", 
                finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT_OR_OUTPUT).toString());
    }

    public void testYamlString_RelativePaths_AllInputs() throws Exception {
        config.set("recon.matlab.yamlstring", relativePathsYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt, inputs/data/infile3.dat, inputs/data/infile4.dat]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{filename}"), ResourceRole.INPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }

    public void testYamlString_RelativePaths_InputsDistingishedByExtension() throws Exception {
        config.set("recon.matlab.yamlstring", relativePathsYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt]",
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("inputs/text/{name}.txt"), ResourceRole.INPUT).toString());
        assertEquals("[inputs/data/infile3.dat, inputs/data/infile4.dat]",
                finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("inputs/data/{name}.dat"), ResourceRole.INPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }

    public void testYamlString_RelativePaths_OutputsDistingishedByExtension() throws Exception {
        config.set("recon.matlab.yamlstring", relativePathsYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[outputs/text/outfile1.txt, outputs/text/outfile2.txt]",
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("outputs/text/{name}.txt"), ResourceRole.OUTPUT).toString());
        assertEquals("[outputs/images/outfile3.png]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.OUTPUT).toString());
    }
    
    public void testYamlString_RelativePaths_AllResources_DistingishedByExtension() throws Exception {
        config.set("recon.matlab.yamlstring", relativePathsYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt, outputs/text/outfile1.txt, outputs/text/outfile2.txt]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{types}/text/{name}.txt"), ResourceRole.INPUT_OR_OUTPUT).toString());
        assertEquals("[inputs/data/infile3.dat, inputs/data/infile4.dat, outputs/images/outfile3.png]", 
            finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT_OR_OUTPUT).toString());
    }

    public void testYamlString_RelativePaths_AllResources_NoMatch() throws Exception {
        config.set("recon.matlab.yamlstring", relativePathsYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[]", finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{types}/docs/{name}.txt"), ResourceRole.INPUT_OR_OUTPUT).toString());
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt, inputs/data/infile3.dat, inputs/data/infile4.dat, " +
                     "outputs/text/outfile1.txt, outputs/text/outfile2.txt, outputs/images/outfile3.png]", 
                finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT_OR_OUTPUT).toString());
    }
    
    public void testYamlString_PathsWithBase_AllInputs_EmptyBase() throws Exception {
        config.set("recon.matlab.yamlstring", pathsWithBaseYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[run/inputs/text/infile1.txt, run/inputs/text/infile2.txt, run/inputs/data/infile3.dat, " + 
                     "run/inputs/data/infile4.dat, /data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{path}"), ResourceRole.INPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }

    public void testYamlString_PathsWithBase_AllInputs_RelativeBase() throws Exception {
        config.set("recon.matlab.yamlstring",  pathsWithBaseYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt, inputs/data/infile3.dat, inputs/data/infile4.dat, " +
                     "/data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]",
            finder.findMatchingResources("run/", new UriTemplate("{path}"), ResourceRole.INPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }

    public void testYamlString_PathsWithBase_AllInputs_AbsoluteBase() throws Exception {
        config.set("recon.matlab.yamlstring",  pathsWithBaseYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[run/inputs/text/infile1.txt, run/inputs/text/infile2.txt, run/inputs/data/infile3.dat, " + 
                     "run/inputs/data/infile4.dat, tmcphill/infile5.png, tmcphill/infile6.png, tmcphill/infile7.png]",
            finder.findMatchingResources("/data/", new UriTemplate("{path}"), ResourceRole.INPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }

    public void testYamlString_PathsWithBase_DataInput_AbsoluteBase() throws Exception {
        config.set("recon.matlab.yamlstring", pathsWithBaseYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[/data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]",
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("/data/{path}"), ResourceRole.INPUT).toString());
        assertEquals("[run/inputs/text/infile1.txt, run/inputs/text/infile2.txt, run/inputs/data/infile3.dat, run/inputs/data/infile4.dat]", 
            finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }
    
    public void testYamlString_PathsWithBase_AllRelativeTextInputs() throws Exception {
        config.set("recon.matlab.yamlstring", pathsWithBaseYamlString);
        finder.configure(config.getSection("recon"));
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt]", 
            finder.findMatchingResources("run/", new UriTemplate("inputs/text/{name}.txt"), ResourceRole.INPUT).toString());
        assertEquals("[run/inputs/data/infile3.dat, run/inputs/data/infile4.dat, /data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]", 
                finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }
        
    public void testYamlFile_PathsWithBase_AllInputs_EmptyBase() throws Exception {
        config.set("recon.matlab.exportfile", TEST_RESOURCE_DIR + "ioresources.yaml");
        finder.configure(config.getSection("recon"));
        assertEquals("[run/inputs/text/infile1.txt, run/inputs/text/infile2.txt, run/inputs/data/infile3.dat, " + 
                     "run/inputs/data/infile4.dat, /data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{path}"), ResourceRole.INPUT).toString());
    }

    public void testYamlFile_PathsWithBase_AllInputs_RelativeBase() throws Exception {
        config.set("recon.matlab.exportfile", TEST_RESOURCE_DIR + "ioresources.yaml");
        finder.configure(config.getSection("recon"));
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt, inputs/data/infile3.dat, inputs/data/infile4.dat, " +
                     "/data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]",
            finder.findMatchingResources("run/", new UriTemplate("{path}"), ResourceRole.INPUT).toString());
    }

    public void testYamlFile_PathsWithBase_AllInputs_AbsoluteBase() throws Exception {
        config.set("recon.matlab.exportfile", TEST_RESOURCE_DIR + "ioresources.yaml");
        finder.configure(config.getSection("recon"));
        assertEquals("[run/inputs/text/infile1.txt, run/inputs/text/infile2.txt, run/inputs/data/infile3.dat, " + 
                     "run/inputs/data/infile4.dat, tmcphill/infile5.png, tmcphill/infile6.png, tmcphill/infile7.png]",
            finder.findMatchingResources("/data/", new UriTemplate("{path}"), ResourceRole.INPUT).toString());
    }

    public void testYamlFile_PathsWithBase_DataInput_AbsoluteBase() throws Exception {
        config.set("recon.matlab.exportfile", TEST_RESOURCE_DIR + "ioresources.yaml");
        finder.configure(config.getSection("recon"));
        assertEquals("[/data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]",
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("/data/{path}"), ResourceRole.INPUT).toString());
    }
    
    public void testYamlFile_PathsWithBase_AllRelativeTextInputs() throws Exception {
        config.set("recon.matlab.exportfile", TEST_RESOURCE_DIR + "ioresources.yaml");
        finder.configure(config.getSection("recon"));
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt]", 
            finder.findMatchingResources("run/", new UriTemplate("inputs/text/{name}.txt"), ResourceRole.INPUT).toString());
    }
        
    public void testRunResourceFile_AllInputs() throws Exception {
        config.set("recon.matlab.exportfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
        finder.configure(config.getSection("recon"));
        assertEquals(
               "[inputs/land_cover/SYNMAP_NA_QD.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.1.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.2.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.3.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.4.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.5.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.6.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.7.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.8.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.9.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.10.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.11.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.12.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.1.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.2.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.3.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.4.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.5.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.6.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.7.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.8.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.9.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.10.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.11.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.12.nc]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("{path}"), ResourceRole.INPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }

    public void testRunResourceFile_AirTempInputs() throws Exception {
        config.set("recon.matlab.exportfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
        finder.configure(config.getSection("recon"));
        assertEquals(
               "[inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.1.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.2.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.3.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.4.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.5.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.6.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.7.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.8.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.9.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.10.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.11.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.12.nc]",
            finder.findMatchingResources(
                    EMPTY_BASE_DIR, 
                    new UriTemplate("inputs/narr_air.2m_monthly/air.2m_monthly_{start_year}_{end_year}_mean.{month}.nc"), 
                    ResourceRole.INPUT)
                    .toString());
        assertEquals(
                "[inputs/land_cover/SYNMAP_NA_QD.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.1.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.2.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.3.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.4.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.5.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.6.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.7.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.8.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.9.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.10.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.11.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.12.nc]",
                finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }
    
    public void testRunResourceFile_PrecipInputs() throws Exception {
        config.set("recon.matlab.exportfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
        finder.configure(config.getSection("recon"));
        assertEquals(
               "[inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.1.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.2.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.3.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.4.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.5.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.6.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.7.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.8.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.9.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.10.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.11.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.12.nc]", 
            finder.findMatchingResources(
                    EMPTY_BASE_DIR, 
                    new UriTemplate("inputs/narr_apcp_rescaled_monthly/apcp_monthly_{start_year}_{end_year}_mean.{month}.nc"), 
                    ResourceRole.INPUT)
                    .toString());
        assertEquals(
                "[inputs/land_cover/SYNMAP_NA_QD.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.1.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.2.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.3.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.4.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.5.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.6.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.7.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.8.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.9.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.10.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.11.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.12.nc]",
                finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }
    
    public void testRunResourceFile_LandCoverInput() throws Exception {
        config.set("recon.matlab.exportfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
        finder.configure(config.getSection("recon"));
        assertEquals(
               "[inputs/land_cover/SYNMAP_NA_QD.nc]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("inputs/land_cover/SYNMAP_NA_QD.nc"), ResourceRole.INPUT).toString());
        assertEquals(
                "[inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.1.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.2.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.3.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.4.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.5.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.6.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.7.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.8.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.9.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.10.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.11.nc, " +
                "inputs/narr_air.2m_monthly/air.2m_monthly_2000_2010_mean.12.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.1.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.2.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.3.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.4.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.5.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.6.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.7.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.8.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.9.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.10.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.11.nc, " +
                "inputs/narr_apcp_rescaled_monthly/apcp_monthly_2000_2010_mean.12.nc]",
                finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());    
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.INPUT).toString());
    }
    
    public void testRunResourceFile_C3FractionOutput() throws Exception {
        config.set("recon.matlab.exportfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
        finder.configure(config.getSection("recon"));
        assertEquals(
               "[outputs/SYNMAP_PRESENTVEG_C3Grass_RelaFrac_NA_v2.0.nc]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("outputs/SYNMAP_PRESENTVEG_C3Grass_RelaFrac_NA_v2.0.nc"), ResourceRole.OUTPUT).toString());
        assertEquals(
                "[outputs/SYNMAP_PRESENTVEG_C4Grass_RelaFrac_NA_v2.0.nc, " +
                 "outputs/SYNMAP_PRESENTVEG_Grass_Fraction_NA_v2.0.nc]",
                 finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.OUTPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.OUTPUT).toString());
    }

    public void testRunResourceFile_C4FractionOutput() throws Exception {
        config.set("recon.matlab.exportfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
        finder.configure(config.getSection("recon"));
        assertEquals(
               "[outputs/SYNMAP_PRESENTVEG_C4Grass_RelaFrac_NA_v2.0.nc]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("outputs/SYNMAP_PRESENTVEG_C4Grass_RelaFrac_NA_v2.0.nc"), ResourceRole.OUTPUT).toString());
        assertEquals(
               "[outputs/SYNMAP_PRESENTVEG_C3Grass_RelaFrac_NA_v2.0.nc, " +
                "outputs/SYNMAP_PRESENTVEG_Grass_Fraction_NA_v2.0.nc]",
                finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.OUTPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.OUTPUT).toString());
    }

    public void testRunResourceFile_GrassFractionOutput() throws Exception {
        config.set("recon.matlab.exportfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
        finder.configure(config.getSection("recon"));
        assertEquals(
               "[outputs/SYNMAP_PRESENTVEG_Grass_Fraction_NA_v2.0.nc]", 
            finder.findMatchingResources(EMPTY_BASE_DIR, new UriTemplate("outputs/SYNMAP_PRESENTVEG_Grass_Fraction_NA_v2.0.nc"), ResourceRole.OUTPUT).toString());
        assertEquals(
                "[outputs/SYNMAP_PRESENTVEG_C3Grass_RelaFrac_NA_v2.0.nc, " + 
                 "outputs/SYNMAP_PRESENTVEG_C4Grass_RelaFrac_NA_v2.0.nc]",
                 finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.OUTPUT).toString());
        assertEquals("[]", finder.findUnmatchedResources(EMPTY_BASE_DIR, ResourceRole.OUTPUT).toString());
    }
}
