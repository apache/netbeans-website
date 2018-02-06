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
"Apache Website Navigation Links Policy"
available at https://www.apache.org/foundation/marks/pmcs#navigation

This seems to be a good place to comply with the policy.

*/%>
<footer>
    <div class="grid-container">
        <div class="grid-x grid-padding-x">
            <div class="large-auto cell">
                <h1>Platform</h1>
            </div>
            <div class="large-auto cell">
                <h1>IDE @ netbeans.org</h1>
                <ul>
                    <li><a href="//netbeans.org/features/ide/index.html" title="Base IDE">Base IDE</a></li>
                </ul>
            </div>
            <div class="large-auto cell">
                <h1><a href="/community/index.html" title="Apache NetBeans Community">Community</a></h1>
                <ul>
                    <li><a href="/community/mailing_lists.html" title="Mailing Lists">Mailing lists</a></li>
                    <li><a href="/community/contributing.html" title="Contributing">Contributing</a></li>
                    <li><a href="/community/team.html" title="Who is Who">Who is Who</a></li>
                    <li><a href="/community/support.html" title="Issues and Support">Issues and Support</a></li>
                </ul>
            </div>
        </div>
        <div class="grid-x">
            <div class='cell' style='text-align: center'>
            </div>
        </div>
    </div>
</footer>
<div class='footer-disclaimer'>
    <div class="footer-disclaimer-content">
        <p>Copyright &copy; 2017-2018 the <a href="//www.apache.org">The Apache Software Foundation</a>.</p>
        <p>Licensed under the <a href="//www.apache.org/licenses/">Apache Software License, version 2.0.</a></p>
        <p>Apache NetBeans is an effort undergoing incubation at The Apache Software Foundation</a> (ASF).</p>
        <p>Incubation is required of all newly accepted projects until a further review indicates that the infrastructure, communications, and decision making process have stabilized in a manner
        consistent with other successful ASF projects.</p>
        <p>While incubation status is not necessarily a reflection of the completeness or stability of the code, it does indicate that the project has yet to be fully endorsed by the Apache Software Foundation.</p>
<%
   debug=false;
   if (debug) {
       out.println("<pre>");
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
