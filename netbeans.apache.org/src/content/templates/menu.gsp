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
<% /* https://foundation.zurb.com/sites/docs/top-bar.html */ %>
<div class="title-bar" data-responsive-toggle="responsive-menu" data-hide-for="medium">
    <button type="button" data-toggle="responsive-menu"><i style='font-size: 32px; color: #fff; padding: 8px' class='fa fa-bars'></i></button>
    <div class="title-bar-title">Apache NetBeans</div>
</div>
<div class="top-bar" id="responsive-menu">
    <div class='top-bar-left'>
        <a class='title' href="/"><img src='/images/apache-netbeans.svg' style='padding: 8px; height: 48px;'></img> Apache NetBeans</a>
    </div>
    <div class="top-bar-right">
        <ul class="vertical medium-horizontal menu" data-responsive-menu="drilldown medium-dropdown">
            <li> <a href="/community/index.html">Community</a> </li>
            <li> <a href="/participate/index.html">Participate</a> </li>
            <li> <a href="https://blogs.apache.org/netbeans/">Blog</a></li>
            <li> <a href="/help/index.html">Get Help</a> </li>
            <li> <a href="https://plugins.netbeans.apache.org/">Plugins</a> </li>
            <li> <a href="/download/index.html">Download</a> </li>
        </ul>
    </div>
</div>

