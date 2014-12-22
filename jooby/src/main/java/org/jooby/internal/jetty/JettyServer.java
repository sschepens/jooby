/**
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
package org.jooby.internal.jetty;

import javax.inject.Inject;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;

import com.google.inject.Injector;

public class JettyServer implements org.jooby.internal.Server {

  private Server server;

  @Inject
  public JettyServer(final Injector injector) throws Exception {
    this.server = JettyServerBuilder.build(injector);
  }

  @Override
  public void start() throws Exception {
    server.start();
    server.join();
  }

  @Override
  public void stop() throws Exception {
    server.stop();
  }

  @Override
  public void restart(final Injector injector) throws Exception {
    Handler handler = server.getHandler();
    handler.stop();
    handler.destroy();
    
    server.setHandler(JettyServerBuilder.buildHandler(injector));
  }
}
