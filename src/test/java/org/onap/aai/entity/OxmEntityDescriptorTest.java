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
package org.onap.aai.entity;

import org.junit.Assert;
import org.junit.Test;
import org.onap.aai.util.CrossEntityReference;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OxmEntityDescriptorTest {

    @Test
    public void testOxmEntityDescriptor(){
        OxmEntityDescriptor descriptor = new OxmEntityDescriptor();
        descriptor.setEntityName("entity-1");
        Assert.assertEquals(descriptor.getEntityName(), "entity-1");

        descriptor.setPrimaryKeyAttributeName(new ArrayList<String>());
        Assert.assertTrue(descriptor.getPrimaryKeyAttributeName().size()==0);

        Assert.assertFalse(descriptor.hasSearchableAttributes());

        List<String> searchableAttr = new ArrayList<String>();
        searchableAttr.add("search");
        descriptor.setSearchableAttributes(searchableAttr);
        Assert.assertTrue(descriptor.getSearchableAttributes().size()==1);

        Assert.assertTrue(descriptor.hasSearchableAttributes());

        CrossEntityReference ref = new CrossEntityReference();
        descriptor.setCrossEntityReference(ref);
        Assert.assertEquals(descriptor.getCrossEntityReference(), ref);

        descriptor.setSuggestableEntity(true);
        Assert.assertTrue(descriptor.isSuggestableEntity());

        Assert.assertNotNull(descriptor.toString());
    }
}
