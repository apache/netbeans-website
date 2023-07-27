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
      ${content.body}
      <%
// order by wiki section on former wiki
def sectionMap=['_getting_started':'Getting Started',
                '_tutorials_and_important_starting_points':'Tutorials and important starting points',
                '_getting_support_where_to_find_examples':'Getting support, where to find examples',
                '_application_lifecycle_and_hooks':'Application Lifecycle and Hooks',
                '_development_issues_module_basics_and_classpath_issues_and_information_about_rcpplatform_application_configuration':'Development issues, module basics and classpath issues, and information about RCP/Platform application configuration',
                '_mavenized_builds':'Mavenized Builds',
                '_configuration_how_modules_install_things':'Configuration: How Modules Install Things',
                '_when_there_are_multiple_ways_to_do_something':'When There Are Multiple Ways To Do Something...',
                '_actions_how_to_add_things_to_files_folders_menus_toolbars_and_more':'Actions: How to add things to Files, Folders, Menus, Toolbars and more',
                '_key_bindings':'Key Bindings',
                '_lookup':'Lookup',
                '_files_and_data_objects':'Files and Data Objects',
                '_converting_between_common_data_types_and_finding_things':'Converting between common data types and finding things',
                '_editor_and_edited_files':'Editor and Edited Files',
                '_file_management_within_the_ideapplication':'File Management (within the IDE/Application)',
                '_module_system':'Module System',
                '_nodes_and_explorer':'Nodes and Explorer',
                '_tasks_and_progressbar':'Tasks and Progressbar',
                '_command_line_parsing':'Command Line Parsing',
                '_threading':'Threading',
                '_creating_a_custom_programming_language':'Creating a Custom Programming Language',
                '_settings':'Settings',
                '_window_system':'Window System',
                '_dialogs_api':'Dialogs API',
                '_xml_multiview_api':'XML Multiview API',
                '_project_handling':'Project Handling',
                '_project_types':'Project Types',
                '_versioning':'Versioning',
                '_printing':'Printing',
                '_html_browser':'HTML Browser',
                '_wizards_and_templates':'Wizards and Templates',
                '_properties_and_propertysheet':'Properties and PropertySheet',
                '_output_window':'Output Window',
                '_using_enterprise_resources_from_netbeans_module':'Using Enterprise Resources from NetBeans module',
                '_running_and_writing_tests':'Running and Writing tests',
                '_branding_your_application':'Branding your application',
                '_authentication_and_authorization_in_platform_apps':'Authentication and Authorization in Platform Apps',
                '_logging_and_error_handling':'Logging and Error Handling',
                '_javahelp':'JavaHelp',
                '_look_and_design':'Look and Design',
                '_deploying_changes_through_autoupdate_and_using_autoupdate_api':'Deploying Changes through AutoUpdate and using Autoupdate API',
                '_deployment_using_installers_nbi':'Deployment using installers / NBI',
                '_programmatic_access_to_java_sources':'Programmatic access to Java Sources',
                '_when_things_go_wrong_troubleshooting':'When things go wrong: Troubleshooting',
                '_licensing_issues':'Licensing Issues',
                '_using_sounds':'Using Sounds'   
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
                // wikidevsection and position may have space separated value length must match, by index mapping
                if ( mydoc.position!=null && mydoc.wikidevsection!=null) { 
                          String[] sections = mydoc.wikidevsection.split(" ");
                          String[] positions = mydoc.position.split(" ");
                          int i = 0;
                          for (String section in sections) {
                              if ( asection.key==section ) {
                                  sectionsubitm.put(positions[i].toInteger(),mydoc);
                              }
                              i++;
                          }
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
      <%include "templatesparts/tools.gsp"%>
    </div>
    <%include "templatesparts/footer.gsp"%>
    <%include "templatesparts/scripts.gsp"%>
  </body>
</html>
