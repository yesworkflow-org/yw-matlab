package org.yesworkflow.matlab;

import java.io.File;
import java.io.IOException;

import org.yesworkflow.YesWorkflowTestCase;
import org.yesworkflow.data.UriTemplate;
import org.yesworkflow.recon.ResourceFinder;
import org.yesworkflow.recon.ResourceFinder.ResourceRole;

public class TestMatlabResourceFinder extends YesWorkflowTestCase {

    private static String TEST_RESOURCE_DIR = "src/test/resources/org/yesworkflow/matlab/tests/TestMatlabResourceFinder/";
    private String MATLAB_RUN_DIR;
    
    private ResourceFinder finder;
    
    @Override
    public void setUp() throws IOException {
        finder = new MatlabResourceFinder();
        MATLAB_RUN_DIR = new File(".").getCanonicalPath().toString() + "/src/main/resources/examples/C3_C4_mapping/";
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

    private String pathsWithBaseYamlString = 
            "inputFiles:"                       + EOL +
            "- run/inputs/text/infile1.txt"     + EOL +
            "- run/inputs/text/infile2.txt"     + EOL +
            "- run/inputs/data/infile3.dat"     + EOL +
            "- run/inputs/data/infile4.dat"     + EOL +
            "- /data/tmcphill/infile5.png"      + EOL +
            "- /data/tmcphill/infile6.png"      + EOL +
            "- /data/tmcphill/infile7.png"      + EOL +
            ""                                  + EOL +
            "outputFiles:"                      + EOL +
            "- run/outputs/text/outfile1.txt"   + EOL +
            "- run/outputs/text/outfile2.txt"   + EOL +
            "- run/outputs/images/outfile3.png" + EOL +
            "- /tmp/tmcphill/3513jj.tmp"        + EOL +
            "- /tmp/tmcphill/456lfm.tmp"        + EOL +
            "- /tmp/tmcphill/2235ss.tmp"        + EOL;
    
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
    
    public void testYamlString_PathsWithBase_AllInputs_EmptyBase() throws Exception {
        finder.configure("yamlstring", pathsWithBaseYamlString);
        assertEquals("[run/inputs/text/infile1.txt, run/inputs/text/infile2.txt, run/inputs/data/infile3.dat, " + 
                     "run/inputs/data/infile4.dat, /data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]", 
            finder.findResources("", new UriTemplate("{path}"), ResourceRole.INPUT).toString());
    }

    public void testYamlString_PathsWithBase_AllInputs_RelativeBase() throws Exception {
        finder.configure("yamlstring", pathsWithBaseYamlString);
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt, inputs/data/infile3.dat, inputs/data/infile4.dat, " +
                     "/data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]",
            finder.findResources("run/", new UriTemplate("{path}"), ResourceRole.INPUT).toString());
    }

    public void testYamlString_PathsWithBase_AllInputs_AbsoluteBase() throws Exception {
        finder.configure("yamlstring", pathsWithBaseYamlString);
        assertEquals("[run/inputs/text/infile1.txt, run/inputs/text/infile2.txt, run/inputs/data/infile3.dat, " + 
                     "run/inputs/data/infile4.dat, tmcphill/infile5.png, tmcphill/infile6.png, tmcphill/infile7.png]",
            finder.findResources("/data/", new UriTemplate("{path}"), ResourceRole.INPUT).toString());
    }

    public void testYamlString_PathsWithBase_DataInput_AbsoluteBase() throws Exception {
        finder.configure("yamlstring", pathsWithBaseYamlString);
        assertEquals("[/data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]",
            finder.findResources("", new UriTemplate("/data/{path}"), ResourceRole.INPUT).toString());
    }
    
    public void testYamlString_PathsWithBase_AllRelativeTextInputs() throws Exception {
        finder.configure("yamlstring", pathsWithBaseYamlString);
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt]", 
            finder.findResources("run/", new UriTemplate("inputs/text/{name}.txt"), ResourceRole.INPUT).toString());
    }
    
    
    public void testYamlFile_PathsWithBase_AllInputs_EmptyBase() throws Exception {
        finder.configure("yamlfile", TEST_RESOURCE_DIR + "ioresources.yaml");
        assertEquals("[run/inputs/text/infile1.txt, run/inputs/text/infile2.txt, run/inputs/data/infile3.dat, " + 
                     "run/inputs/data/infile4.dat, /data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]", 
            finder.findResources("", new UriTemplate("{path}"), ResourceRole.INPUT).toString());
    }

    public void testYamlFile_PathsWithBase_AllInputs_RelativeBase() throws Exception {
        finder.configure("yamlfile", TEST_RESOURCE_DIR + "ioresources.yaml");
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt, inputs/data/infile3.dat, inputs/data/infile4.dat, " +
                     "/data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]",
            finder.findResources("run/", new UriTemplate("{path}"), ResourceRole.INPUT).toString());
    }

    public void testYamlFile_PathsWithBase_AllInputs_AbsoluteBase() throws Exception {
        finder.configure("yamlfile", TEST_RESOURCE_DIR + "ioresources.yaml");
        assertEquals("[run/inputs/text/infile1.txt, run/inputs/text/infile2.txt, run/inputs/data/infile3.dat, " + 
                     "run/inputs/data/infile4.dat, tmcphill/infile5.png, tmcphill/infile6.png, tmcphill/infile7.png]",
            finder.findResources("/data/", new UriTemplate("{path}"), ResourceRole.INPUT).toString());
    }

    public void testYamlFile_PathsWithBase_DataInput_AbsoluteBase() throws Exception {
        finder.configure("yamlfile", TEST_RESOURCE_DIR + "ioresources.yaml");
        assertEquals("[/data/tmcphill/infile5.png, /data/tmcphill/infile6.png, /data/tmcphill/infile7.png]",
            finder.findResources("", new UriTemplate("/data/{path}"), ResourceRole.INPUT).toString());
    }
    
    public void testYamlFile_PathsWithBase_AllRelativeTextInputs() throws Exception {
        finder.configure("yamlfile", TEST_RESOURCE_DIR + "ioresources.yaml");
        assertEquals("[inputs/text/infile1.txt, inputs/text/infile2.txt]", 
            finder.findResources("run/", new UriTemplate("inputs/text/{name}.txt"), ResourceRole.INPUT).toString());
    }
        
    public void testRunResourceFile_AllInputs() throws Exception {
        finder.configure("yamlfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
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
            finder.findResources(MATLAB_RUN_DIR, new UriTemplate("{path}"), ResourceRole.INPUT).toString());
    }

    public void testRunResourceFile_AirTempInputs() throws Exception {
        finder.configure("yamlfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
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
            finder.findResources(
                    MATLAB_RUN_DIR, 
                    new UriTemplate("inputs/narr_air.2m_monthly/air.2m_monthly_{start_year}_{end_year}_mean.{month}.nc"), 
                    ResourceRole.INPUT)
                    .toString());
    }
    
    public void testRunResourceFile_PrecipInputs() throws Exception {
        finder.configure("yamlfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
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
            finder.findResources(
                    MATLAB_RUN_DIR, 
                    new UriTemplate("inputs/narr_apcp_rescaled_monthly/apcp_monthly_{start_year}_{end_year}_mean.{month}.nc"), 
                    ResourceRole.INPUT)
                    .toString());
    }
    
    public void testRunResourceFile_LandCoverInput() throws Exception {
        finder.configure("yamlfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
        assertEquals(
               "[inputs/land_cover/SYNMAP_NA_QD.nc]", 
            finder.findResources(MATLAB_RUN_DIR, new UriTemplate("inputs/land_cover/SYNMAP_NA_QD.nc"), ResourceRole.INPUT).toString());
    }
    
    public void testRunResourceFile_C3FractionOutput() throws Exception {
        finder.configure("yamlfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
        assertEquals(
               "[outputs/SYNMAP_PRESENTVEG_C3Grass_RelaFrac_NA_v2.0.nc]", 
            finder.findResources(MATLAB_RUN_DIR, new UriTemplate("outputs/SYNMAP_PRESENTVEG_C3Grass_RelaFrac_NA_v2.0.nc"), ResourceRole.OUTPUT).toString());
    }

    public void testRunResourceFile_C4FractionOutput() throws Exception {
        finder.configure("yamlfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
        assertEquals(
               "[outputs/SYNMAP_PRESENTVEG_C4Grass_RelaFrac_NA_v2.0.nc]", 
            finder.findResources(MATLAB_RUN_DIR, new UriTemplate("outputs/SYNMAP_PRESENTVEG_C4Grass_RelaFrac_NA_v2.0.nc"), ResourceRole.OUTPUT).toString());
    }

    public void testRunResourceFile_GrassFractionOutput() throws Exception {
        finder.configure("yamlfile", TEST_RESOURCE_DIR + "matlabRunResources.yaml");
        assertEquals(
               "[outputs/SYNMAP_PRESENTVEG_Grass_Fraction_NA_v2.0.nc]", 
            finder.findResources(MATLAB_RUN_DIR, new UriTemplate("outputs/SYNMAP_PRESENTVEG_Grass_Fraction_NA_v2.0.nc"), ResourceRole.OUTPUT).toString());
    }
}
