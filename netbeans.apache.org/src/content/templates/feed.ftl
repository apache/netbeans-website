<#--
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
-->
<?xml version="1.0" encoding="UTF-8"?>
<feed xmlns="http://www.w3.org/2005/Atom">
  <title type="html">Apache NetBeans</title>
  <subtitle type="html">Quickly and easily develop web, mobile and desktop applications with Java, JavaScript, HTML5, PHP, C/C++ and more. </subtitle>
  <id>https://netbeans.apache.org/blogs/atom</id>
  <link rel="self" type="application/atom+xml" href="https://netbeans.apache.org/blogs/atom" />
  <link rel="alternate" type="text/html" href="https://netbeans.apache.org/blogs/atom" />
  <updated>${published_date?datetime?string.xs}</updated>
  <#list published_posts as post>
  <#--
   All published post, adoc file of :jbake-type: post will be on entry, need authors, date
  -->
  <entry>
    <id>${post.noExtensionUri!post.uri}</id>
    <title type="html"><#escape x as x?xml>${post.title}</#escape></title>
    <author><name>${post.author}</name></author>
    <link rel="alternate" type="text/html" href="${config.site_host}/${post.noExtensionUri!post.uri}"/>
    <published>${post.date?datetime?string.xs}</published>
    <updated>${post.date?datetime?string.xs}</updated>
    <content type="html">
	<#escape x as x?xml>	
	${post.body}
	</#escape>
    </content>
  </entry>
  </#list>
</feed>