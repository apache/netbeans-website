<%/*

    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

*/%>
<%/*

    The Apache NetBeans site must comply with the 
    "Apache Website Navigation Links Policy" https://www.apache.org/foundation/marks/pmcs#navigation

    This seems to be a good place to comply with the policy.

*/%>
    <div class='grid-container incubator-area' style='margin-top: 64px'>
      <div class='grid-x grid-padding-x'>
        <div class='large-auto cell text-center'>
          <a href="https://www.apache.org/">
            <img style="width: 320px" title="Apache Software Foundation" src="/images/asf_logo_wide.svg" />
          </a>
        </div>
        <div class='large-auto cell text-center'>
          <a href="https://www.apache.org/events/current-event.html">
            <img style="width:234px; height: 60px;" title="Apache Software Foundation current event" src="https://www.apache.org/events/current-event-234x60.png"/>
          </a>
        </div>
      </div>
    </div>
    <footer>
      <div class="grid-container">
        <div class="grid-x grid-padding-x">
          <div class="large-auto cell">
                    <% /* See https://www.apache.org/foundation/marks/pmcs#navigation */ %>
            <h1><a href="/about/index.html">About</a></h1>
            <ul>
              <li><a href="https://netbeans.apache.org/community/who.html">Who's Who</a></li>
              <li><a href="https://www.apache.org/foundation/thanks.html">Thanks</a></li>
              <li><a href="https://www.apache.org/foundation/sponsorship.html">Sponsorship</a></li>
              <li><a href="https://www.apache.org/security/">Security</a></li>
            </ul>
          </div>
          <div class="large-auto cell">
            <h1><a href="/community/index.html">Community</a></h1>
            <ul>
              <li><a href="/community/mailing-lists.html">Mailing lists</a></li>
              <li><a href="/community/committer.html">Becoming a committer</a></li>
              <li><a href="/community/events.html">NetBeans Events</a></li>
              <li><a href="https://www.apache.org/events/current-event.html">Apache Events</a></li>
            </ul>
          </div>
          <div class="large-auto cell">
            <h1><a href="/participate/index.html">Participate</a></h1>
            <ul>
              <li><a href="/participate/submit-pr.html">Submitting Pull Requests</a></li>
              <li><a href="/participate/report-issue.html">Reporting Issues</a></li>
              <li><a href="/participate/index.html#documentation">Improving the documentation</a></li>
            </ul>
          </div>
          <div class="large-auto cell">
            <h1><a href="/help/index.html">Get Help</a></h1>
            <ul>
              <li><a href="/help/index.html#documentation">Documentation</a></li>
              <li><a href="/wiki/index.html">Wiki</a></li>
              <li><a href="/help/index.html#support">Community Support</a></li>
              <li><a href="/help/commercial-support.html">Commercial Support</a></li>
            </ul>
          </div>
          <div class="large-auto cell">
            <h1><a href="/download/index.html">Download</a></h1>
            <ul>
              <li><a href="/download/index.html">Releases</a></li>                    
              <li><a href="https://plugins.netbeans.apache.org/">Plugins</a></li>
              <li><a href="/download/index.html#source">Building from source</a></li>
              <li><a href="/download/index.html#previous">Previous releases</a></li>
            </ul>
          </div>
        </div>
      </div>
    </footer>
    <div class='footer-disclaimer'>
      <div class="footer-disclaimer-content">
        <p>Copyright &copy; 2017-2022 <a href="https://www.apache.org">The Apache Software Foundation</a>.</p>
        <p>Licensed under the Apache <a href="https://www.apache.org/licenses/">license</a>, version 2.0</p>
        <div style='max-width: 40em; margin: 0 auto'>
          <p>Apache, Apache NetBeans, NetBeans, the Apache feather logo and the Apache NetBeans logo are trademarks of <a href="https://www.apache.org">The Apache Software Foundation</a>.</p>
          <p>Oracle and Java are registered trademarks of Oracle and/or its affiliates.</p>
          <p>The Apache NetBeans website conforms to the <a href="https://privacy.apache.org/policies/privacy-policy-public.html">Apache Software Foundation Privacy Policy</a></p>
        </div>
            <%
                /* This is used for debugging the website rendering, it displays the 'content' variable. Just set the "debug" metadata to a not null value (in asciidoc documents add ":debug: yes") to render this area. */
                 if (content.debug != null) {
                     out.println("<pre style='background: #777; color: #eee;'>");
                     for (String key: content.keySet()) {
                         if (! key.equals("body")) {
                             out.println(key + ":" + content.get(key));
                         }
                     }
                     out.println("</pre>");
                 }
             %>
      </div>
    </div>

