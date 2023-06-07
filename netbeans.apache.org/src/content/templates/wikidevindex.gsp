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

    page_noaside.gsp: A main area with no aside.

*/%>
<!doctype html>
<html class="no-js" lang="en" dir="ltr">
  <%include "templatesparts/head.gsp"%>
  <body>
    <%include "templatesparts/menu.gsp"%>
    <%include "templatesparts/news.gsp"%>
    <div class='grid-container main-content'>
      <h1 class="sect1">${content.title}</h1>
      <%include "templatesparts/wiki-devindexbread.gsp"%>
      <%
def sectionMap=['_actions_how_to_add_things_to_files_folders_menus_toolbars_and_more':'Actions: How to add things to Files, Folders, Menus, Toolbars and more',
                        'lifecyclesection':'Application Lifecycle and Hooks'       
]
// toc section first
out.println('<div id="toc" class="toc">');
out.println('<div id="toctitle"></div>');
out.println('<ul class="sectlevel1">');
out.println('<li>'+content.title+'</a>');
out.println('<ul class="sectlevel2">');      
for (asection in sectionMap) {
    out.println('<li><a href="#'+asection.key+'">'+asection.value+'</a></li>');
}
out.println('</ul></li></ul></div>');
// content by aggregation 
for (asection in sectionMap) {
    out.println('<div class="sect2">');
    out.println('<h3 id="'+asection.key+'">'+asection.value+'</h3>');
    TreeMap sectionsubitm = [:]
    for ( atag in tags ) {
        if ( atag.name=="devfaq") {
            for ( mydoc in atag.tagged_documents ) {
                if ( mydoc.position!=null && mydoc.wikidevsection!=null && asection.key==mydoc.wikidevsection ) {
                    sectionsubitm.put(mydoc.position,mydoc);
                }
            }
        }
    }
    sectionsubitm.sort();
    out.println('<div class="ulist">');
    out.println('<ul>');
    for (adoc in sectionsubitm) {
        out.println('<li><p>');
        out.println('<a href="'+content.rootpath+adoc.value.uri+'">'+adoc.value.title+'</a>');
        if (adoc.value.tags.contains('needsreview')) {
            out.println("[needs review]")
        }
        out.println('</p></li>');
    }
    out.println('</ul>');
    out.println('</div>');
    out.println('</div>');
}

      %>
      <hr>
       Manually edited
      <hr>
      ${content.body}
      <%include "templatesparts/tools.gsp"%>
    </div>
    <%include "templatesparts/footer.gsp"%>
    <%include "templatesparts/scripts.gsp"%>
  </body>
</html>
