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
<!DOCTYPE html>
<html class="no-js" lang="en" dir="ltr">
    <%include "head.gsp"%>
    <body>
        <%include "menu.gsp"%>
        <%include "news.gsp"%>
        <div class='grid-container main-content tutorial'>
            <h1 class="sect0">${content.title}</h1>
            <% if (content.reviewed == null) { %>
            <div class="sectionbody">
              <div class="admonitionblock note">
                <table>
                  <tbody><tr>
                  <td class="icon"><i class="fa icon-note" title="Note"></i></td>
                  <td class="content">This tutorial needs a review. You can <a href="/kb/docs/contributing.html">help us review it.</a></td>
                  </tr></tbody>
                </table>
              </div>
            </div>
            <% } %>
            ${content.body}
            <%include "tools.gsp"%>
        </div>
        <%include "footer.gsp"%>

        <script src="/js/vendor/jquery-3.2.1.min.js"></script>
        <script src="/js/vendor/what-input.js"></script>
        <script src="/js/vendor/jquery.colorbox-min.js"></script>
        <script src="/js/vendor/foundation.min.js"></script>
        <script src="/js/netbeans.js"></script>
        <script>
            <% // NOTE: Plain jquery stuff needs to be quoted in gsp pages, so we quote with ${' ... '} %>
            ${'$(function(){ $(document).foundation(); });'}
        </script>
        <% if (content.syntax) { %>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.13.1/highlight.min.js"></script>
        <script>
        ${' $(document).ready(function() { $("pre code").each(function(i, block) { hljs.highlightBlock(block); }); }); '}
        </script>
        <% } %>
    </body>
</html>
