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
<section class='tools'>
    <ul class="menu align-center">
        <li><a title="Facebook" href="https://www.facebook.com/NetBeans"><i class="fa fa-md fa-facebook"></i></a></li>
        <li><a title="Twitter" href="https://twitter.com/netbeans"><i class="fa fa-md fa-twitter"></i></a></li>
        <li><a title="Github" href="https://github.com/apache/netbeans"><i class="fa fa-md fa-github"></i></a></li>
        <li><a title="YouTube" href="https://www.youtube.com/user/netbeansvideos"><i class="fa fa-md fa-youtube"></i></a></li>
        <li><a title="Slack" href="https://tinyurl.com/netbeans-slack-signup/"><i class="fa fa-md fa-slack"></i></a></li>
        <li><a title="Issues" href="https://github.com/apache/netbeans/issues"><i class="fa fa-mf fa-bug"></i></a></li>
    </ul>
    <ul class="menu align-center">
        <%
            /* 
                jbake's "content.file' has this structure: "/home/user/directory/of/the/clone/netbeans-website/netbeans.apache.org/build/generated-bake/content/plugins/index.asciidoc"
                we're interested in the part after "generated-bake"
            */
            String file="/";
            if (content != null) {
                String content_file = content.get("file");
                int i = content_file.lastIndexOf("build/generated-bake");
                // Adds support for Windows builds
                i = i == -1 ? content_file.lastIndexOf("build\\generated-bake") : i;
                file = content_file.substring(i + "build/generated-bake".length());
            }
        %>
        <li><a href="https://github.com/apache/netbeans-website/blob/master/netbeans.apache.org/src${file}" title="See this page in github"><i class="fa fa-md fa-edit"></i> See this page in GitHub.</a></li>
    </ul>
</section>
