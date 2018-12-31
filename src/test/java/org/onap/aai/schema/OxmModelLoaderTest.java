/**
 * ﻿============LICENSE_START=======================================================
 * org.onap.aai
 * ================================================================================
 * Copyright © 2017-2018 AT&T Intellectual Property. All rights reserved.
 * Copyright © 2017-2018 Amdocs
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */
package org.onap.aai.schema;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipse.persistence.dynamic.DynamicType;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.jaxb.dynamic.DynamicJAXBContext;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.aai.nodes.NodeIngestor;
import org.onap.aai.setup.SchemaLocationsBean;
import org.onap.aai.setup.SchemaVersions;
import org.onap.aai.util.EntityOxmReferenceHelper;
import org.onap.aai.util.ExternalOxmModelProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/spring-beans/data-router-oxm.xml")
public class OxmModelLoaderTest {

    @Autowired
    private SchemaVersions schemaVersions;
    @Autowired
    private SchemaLocationsBean schemaLocationsBean;
    @Autowired
    private NodeIngestor nodeIngestor;
  
    @Test
    public void testLoadingMultipleOxmFiles() {
      
        ArrayList<ExternalOxmModelProcessor> externalOxmModelProcessors = new ArrayList<ExternalOxmModelProcessor>();
        externalOxmModelProcessors.add(EntityOxmReferenceHelper.getInstance());
        OxmModelLoader.registerExternalOxmModelProcessors(externalOxmModelProcessors);
        OxmModelLoader.setNodeIngestor(nodeIngestor);
        OxmModelLoader.loadModels(schemaVersions, schemaLocationsBean);

        DynamicJAXBContext jaxbContext = OxmModelLoader.getContextForVersion("v13", schemaVersions, schemaLocationsBean);

        DynamicType pserver = jaxbContext.getDynamicType("Pserver");
        DynamicType genericVnf = jaxbContext.getDynamicType("GenericVnf");

        assertNotNull(pserver);
        assertNotNull(genericVnf);

        DatabaseMapping mapping = pserver.getDescriptor().getMappings().firstElement();
        if (mapping.isAbstractDirectMapping()) {
            DatabaseField f = mapping.getField();
            String keyName = f.getName().substring(0, f.getName().indexOf("/"));
            assertTrue(keyName.equals("hostname"));
        }

        mapping = genericVnf.getDescriptor().getMappings().firstElement();
        if (mapping.isAbstractDirectMapping()) {
            DatabaseField f = mapping.getField();
            String keyName = f.getName().substring(0, f.getName().indexOf("/"));
            assertTrue(keyName.equals("vnf-id"));
        }
      
    }
    
}
