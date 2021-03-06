/**
 * Copyright 2017-2019 The OpenTracing Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package io.opentracing.contrib.spring.integration.messaging;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class JmsTextTextExtractAdapterTest {

  @Test
  public void shouldGetIterator() {
    Map<String, String> headers = new HashMap<>(2);
    headers.put("h1", "v1");
    headers.put("h2", "v2");
    Message<String> message = MessageBuilder.withPayload("test").copyHeaders(headers).build();
    MessageTextMap<String> map = new MessageTextMap<>(message);
    JmsTextMapExtractAdapter adapter = new JmsTextMapExtractAdapter(map);
    assertThat(adapter.iterator()).containsAll(headers.entrySet());
  }

  @Test
  public void shouldDecodeDash() {
    Map<String, String> headers = new HashMap<>(1);
    headers.put("uber_$dash$_trace_$dash$_id", "v1");
    Map<String, String> chanedHeaders = new HashMap<>(1);
    headers.put("uber-trace-id", "v1");
    Message<String> message = MessageBuilder.withPayload("test").copyHeaders(headers).build();
    MessageTextMap<String> map = new MessageTextMap<>(message);
    JmsTextMapExtractAdapter adapter = new JmsTextMapExtractAdapter(map);
    assertThat(adapter.iterator()).containsAll(chanedHeaders.entrySet());
  }

}
