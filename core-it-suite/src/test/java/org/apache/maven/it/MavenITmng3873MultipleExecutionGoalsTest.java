package org.apache.maven.it;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;
import java.util.List;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-3873">MNG-3873</a>.
 * 
 * @author Benjamin Bentmann
 * @version $Id$
 */
public class MavenITmng3873MultipleExecutionGoalsTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng3873MultipleExecutionGoalsTest()
    {
    }

    /**
     * Test that all goals from a plugin execution are actually executed and not only one when no <pluginManagement>
     * is involved.
     */
    public void testitWithoutPluginMngt()
        throws Exception
    {
        testitMNG3873( "test-1" );
    }

    /**
     * Test that all goals from a plugin execution are actually executed and not only one when <pluginManagement>
     * is involved.
     */
    public void testitWithPluginMngt()
        throws Exception
    {
        testitMNG3873( "test-2" );
    }

    private void testitMNG3873( String project )
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3873" );

        Verifier verifier = new Verifier( new File( testDir, project ).getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        /*
         * NOTE: We deliberately don't check the exact order here, that's another issue.
         */
        List lines = verifier.loadLines( "target/it.log", "UTF-8" );
        assertTrue( lines.toString(), lines.contains( "test" ) );
        assertTrue( lines.toString(), lines.contains( "----" ) );
    }

}
