/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.servicecomb.governance.service;

import java.util.Map;

import org.apache.servicecomb.governance.marker.GovHttpRequest;
import org.apache.servicecomb.governance.marker.RequestProcessor;
import org.apache.servicecomb.governance.marker.TrafficMarker;
import org.apache.servicecomb.governance.properties.MatchProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MatchersServiceImpl implements MatchersService {
  @Autowired
  private RequestProcessor requestProcessor;

  @Autowired
  private MatchProperties matchProperties;

  @Override
  public boolean checkMatch(GovHttpRequest govHttpRequest, String key) {
    Map<String, TrafficMarker> parsedEntity = matchProperties.getParsedEntity();

    String[] subKeys = key.split("\\.");
    if (subKeys.length != 2) {
      return false;
    }

    TrafficMarker trafficMarker = parsedEntity.get(subKeys[0]);

    if (trafficMarker == null) {
      return false;
    }

    return trafficMarker.checkMatch(govHttpRequest, requestProcessor, subKeys[1]);
  }
}
