package com.ctrip.framework.cs;

import com.ctrip.framework.cs.analyzer.Analyzer;
import com.ctrip.framework.cs.analyzer.PomDependency;
import com.ctrip.framework.cs.analyzer.PomDependencyHandler;
import com.ctrip.framework.cs.analyzer.PomInfo;
import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jiang.j on 2016/5/19.
 */
public class AnalyzerTest {

    @Test
    public void testSome() throws IOException, NoSuchFieldException, URISyntaxException, NoSuchAlgorithmException {


    }

    @Test
    public void testAnalyzePom() {

        try {
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            PomDependencyHandler handler = new PomDependencyHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(this.getClass().getClassLoader().getResourceAsStream("testpom.xml")));
            assertEquals(4, handler.getDependencies().size());
            PomInfo pom = handler.getPomInfo();
            boolean hasVI = false;
            for (PomDependency d : handler.getDependencies()) {
                assertNotNull(d.artifactId);
                assertNotNull(d.groupId);
                assertNotNull(d.version);
                if ("framework-validateinternals".equals(d.artifactId)) {
                    hasVI = true;
                    assertEquals(d.version, "0.9");
                }
            }
            assertTrue(hasVI);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetAllJarPom() {

        try {
            List<PomInfo> poms = Analyzer.getAllJarPomInfo();
            assertTrue(poms.size() > 0);
            PomInfo pomInfo = poms.get(0);
            assertNotNull(pomInfo.artifactId);
            System.out.println(pomInfo.artifactId);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
