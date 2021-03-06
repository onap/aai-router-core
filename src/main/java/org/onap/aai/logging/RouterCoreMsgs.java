/**
 * ============LICENSE_START=======================================================
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
package org.onap.aai.logging;

import com.att.eelf.i18n.EELFResourceManager;
import org.onap.aai.cl.eelf.LogMessageEnum;

public enum RouterCoreMsgs implements LogMessageEnum {

  /**
   * Processed event {0}. Result: {1}.
   * 
   * Arguments: {0} = event topic {1} = result
   */
  PROCESS_EVENT,
  /**
   * Arguments: {0} = Processing exception
   */
  EVENT_PROCESSING_EXCEPTION,

  /**
   * Successfully loaded schema: {0}
   *
   * <p>Arguments:
   * {0} = oxm filename
   */
  LOADED_OXM_FILE,

  /** Failed to load the schemaIngest.properties file */
  SCHEMA_INGEST_LOAD_ERROR,

  /**
   * Arguments: {0} = Creation exception
   */
  EVENT_CONSUMER_CREATION_EXCEPTION;


  /**
   * Static initializer to ensure the resource bundles for this class are loaded...
   */
  static {
    EELFResourceManager.loadMessageBundle("logging/RouterCoreMsgs");
  }
}
