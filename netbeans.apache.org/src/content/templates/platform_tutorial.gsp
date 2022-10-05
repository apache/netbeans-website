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

    tutorial.gsp: A main area with a right aside for TOCs and tutorial sections.

*/%>
<!doctype html>
<html class="no-js" lang="en" dir="ltr">
  <%include "templatesparts/head.gsp"%>
  <body>
    <%include "templatesparts/menu.gsp"%>
    <%include "templatesparts/news.gsp"%>
    <div class='grid-container main-content tutorial'>
      <h1 class="sect0">${content.title}</h1>
            <% if (content.reviewed == null) { 
                /* 
                    jbake's 'content.file' has this structure: "/home/user/directory/of/the/clone/netbeans-website/netbeans.apache.org/build/generated-bake/content/plugins/index.asciidoc"
                    we're interested in the part after "generated-bake"
                */
                String tutorial_file="/";
                String tutorial_content_file = content.get("file");
                int idx = tutorial_content_file.lastIndexOf("build/generated-bake");
                // Adds support for Windows builds
                idx = idx == -1 ? tutorial_content_file.lastIndexOf("build\\generated-bake") : idx;
                tutorial_file = idx == -1 ? "???" + tutorial_content_file : tutorial_content_file.substring(idx + "build/generated-bake".length());
            %>
            <div class="sectionbody">
              <div class="admonitionblock note">
                <table>
                  <tbody><tr>
                  <td class="icon"><i class="fa icon-note" title="Note"></i></td>
                  <td class="content">This tutorial needs a review. 
                     You can <a href="https://github.com/apache/netbeans-website/blob/master/netbeans.apache.org/src${tutorial_file}" title="Edit this tutorial in github">edit it in GitHub </a>
                     following these <a href="/kb/docs/contributing.html">contribution guidelines.</a></td>
                  </tr></tbody>
                </table>
              </div>
            </div>
            <% } else { %>
            <div class="sectionbody">
              <div class="paragraph">
                <p class='reviewed'><i class="fa fa-check-circle"></i> Last reviewed on <%= content.reviewed %></p>
              </div>
            </div>
            <% } %>
        ${content.body}
        <%include "templatesparts/tools.gsp"%>
    </div>
    <%include "templatesparts/footer.gsp"%>
    <%include "templatesparts/scripts.gsp"%>
  </body>
</html>
