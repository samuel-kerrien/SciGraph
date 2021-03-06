/**
 * Copyright (C) 2014 The SciGraph authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.sdsc.scigraph.services.jersey.dynamic;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;

import com.google.inject.assistedinject.Assisted;

import edu.sdsc.scigraph.services.jersey.CustomMediaTypes;
import edu.sdsc.scigraph.services.swagger.beans.resource.Apis;

public class DynamicCypherResource extends ResourceConfig {

  private static final Logger logger = Logger.getLogger(DynamicCypherResourceFactory.class.getName());

  final Resource.Builder resourceBuilder = Resource.builder();

  @Inject
  DynamicCypherResource(CypherInflectorFactory factory, @Assisted Apis config) {
    logger.info("Building dynamic resoure at " + config.getPath());
    resourceBuilder.path(config.getPath());
    ResourceMethod.Builder methodBuilder = resourceBuilder.addMethod("GET");
    methodBuilder.produces(
        MediaType.APPLICATION_JSON_TYPE, CustomMediaTypes.APPLICATION_JSONP_TYPE, CustomMediaTypes.APPLICATION_GRAPHSON_TYPE,
        MediaType.APPLICATION_XML_TYPE, CustomMediaTypes.APPLICATION_GRAPHML_TYPE, CustomMediaTypes.APPLICATION_XGMML_TYPE,
        CustomMediaTypes.TEXT_GML_TYPE, CustomMediaTypes.TEXT_CSV_TYPE, CustomMediaTypes.TEXT_TSV_TYPE,
        CustomMediaTypes.IMAGE_JPEG_TYPE, CustomMediaTypes.IMAGE_PNG_TYPE)
        .handledBy(factory.create(config));
  }

  public Resource.Builder getBuilder() {
    return resourceBuilder;
  }

}
