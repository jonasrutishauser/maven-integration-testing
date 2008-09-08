package jar;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Properties;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * @goal test
 */
public class MyMojo
    extends AbstractMojo
{
    /**
     * @parameter
     * @required
     */
    private String check;
    
    /**
     * @parameter default-value="${project.properties}"
     * @readonly
     */
    private Properties properties;

    public void execute()
        throws MojoExecutionException
    {
        String sysProp = System.getProperty( "java.version" );
        String pomProp = properties.getProperty( "java.version" );
        
        boolean fail = false;
        
        if ( check.equals( sysProp ) )
        {
            getLog().error( "Check value is the same as the system property; interpolation failed! (value: " + check + ")" );
            fail = true;
        }
        
        if ( !check.equals( pomProp ) )
        {
            getLog().error( "Check value is NOT the same as the POM property; interpolation failed! (value: " + check + ")" );
            fail = true;
        }
        
        if ( fail )
        {
            throw new MojoExecutionException( "Failed to verify interpolation with POM override of a system property. See console output for more information." );
        }
    }
}
