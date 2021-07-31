////
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
////
= Apache NetBeans / netbeans.apache.org
:toc:

The http://netbeans.apache.org website.

== Introduction. Objectives.

The NetBeans project has a huge website at https://www.netbeans.org. The site
has grown over the years, and is indeed quite big. Now that
link:https://github.com/apache/netbeans[NetBeans] is being donated by
Oracle to the link:http://www.apache.org[Apache Software Foundation], a new static web site must be
created, trying to keep as much information as possible
from the "old" website.

This is ongoing effort and is being performed in these two steps:

1. Wade Chandler has made a great effort retrieving as much information as
   possible from the old website. This is actually kept in https://github.com/apache/netbeans-website-cleanup.
   He has also set up the a sophisticated, yet simple to use, gradle build system.
2. Once this content is approved by Oracle it will be gradually added here.
3. And once all content is donated, http://netbeans.apache.org will become http://netbeans.org

== About this repository

As stated above, the objective of this repository is to construct the website at http://netbeans.apache.org
that will gradually receive content from http://netbeans.org (as stored in https://github.com/apache/netbeans-website-cleanup).

The tasks to do are described in a specific [NetBeans Wiki Page](https://cwiki.apache.org/confluence/display/NETBEANS/New+NetBeans+Web+Site+Planning).

== Tooling

This repository is build built using the following technologies:

. Gradle
. http://jbake.org/[JBake] see also http://jbake.org/docs/2.5.1/[JBake documentation]
. SaSS
. Apache Groovy
. SnakeYaml
. Apache Tomcat
. ... and others.

NOTE: You *do not need* to install any of these. The tools will be automatically installed by gradle.

The project uses Gradle, Groovy, and JBake to create a template based statically compiled web site.
The technologies used compliment each other. JBake performs the final steps of preparing the site.
Gradle and Groovy are used to preprocess files and execute JBake. Gradle provides features which JBake
currently does not, and thus uses JBake as part of a larger build system which is completely configurable
and programmable. Other features can be added as needed.

Tomcat is used to provide a local web server to use while working with the site sources locally,
and is not intended to be used as the production server though Tomcat could certainly be used
to serve the sources. It is expected the sources will be served by Apache httpd at Apache.

To properly use the build and tools, one should become familiar with this README, but also the build
sources, and have an understanding of how they function. The goal is certainly for this README to suffice,
but it is entirely possible something has been missed. Please update this README accordingly when/if
something is missing.

== The Source and Build Layout and Important Concepts

The project source layout is roughly:

----
build.gradle
settings.gradle
globals.yml
buildSrc
-- src
---- main
------ groovy
---- test
------ groovy
-- build.gradle
-- gradle
---- wrapper
-- deps.gradle
-- utils.gradle
src
-- content
---- templates
gradlew
gradlew.bat
----

Much of the above is standard Gradle build stuff. The rest is explained below:

[options="header"]
|===
|Name|Description


|globals.yml|A YAML file which is used to replace/supplement the notion of jbake.properties plus allows customization
|buildSrc|A convention in Gradle which is a subproject with sources which will be built before tasks are configured and ran
|gradle|A folder for Gradle specifics such as include files and the wrapper
|gradle/wrapper|The gradle wrapper files which allow it to run with nothing but a JDK and JAVA_HOME
|gradle/deps.gradle|A gradle script which defines variables and common dependencies used by the build
|gradle/utils.gradle|A gradle script which defines and maps various utilities the build uses
|src/content|The main content files top level sources directory
|src/content/templates|A folder for JBakes templates; this build uses Groovy templates
|===

=== globals.yml

JBake configuration can go here, but in some cases, the configuration is better
defined in the `jbake` configuration inside `build.gradle` as it can be calculated
by the Gradle build.

Global data or configuration can then be defined here. This data can be in complex
form, such as a YAML tree/map hierarchy, or it may be flat attributes. This
data is used both for JBake configuration, those common to JBake, and thus
accessible by the JBake templates, and is also accessible in pure object form
by the "content templates" (more on these later)

=== buildSrc

Gradle will build `buildSrc` as a project before configuring and executing the
rest of the build. Classes in this project can be used to do some very specific
things in the build. They can provide utilities or they can setup and run
an Apache Tomcat server (which they do).

=== src/content/templates

The JBake templates go here. This project specifically uses Groovy templates. These
should not be confused with "content templates" which is a concept to be described
later. 

These templates are executed by JBake at specific times as described in the
JBake documentation, and usually based on the `type` attribute from content metadata.

An example is `type: page` in content metadata which maps to the JBake template
`src/content/templates/page.gsp` 

The same is true for `type: post`, as it has a `post.gsp` file. Posts are
useful for a "blog like" area of the site where
news items may be kept etc.

For more information about the templates, and how to build one, see the
link:src/content/templates[templates] directory README.

=== src/content (\*\*/\*.html.gsp and \*\*/\*.md.gsp and YAML) or Content Templates

This build treats files ending in `.html.gsp` and `.md.gsp` specially. These files
will be preprocessed before JBake accesses them, and turned into `.html` and `.md`
files respectively. It is a build error to have such a file without a counterpart
file ending in `.html.gsp.yml` or `.md.gsp.yml` respectively.

The YAML file will be merged into the final file as the JBake metadata. The common
JBake metadata attributes defined in the documentation will be added to the top
of the files such as `title=Some Title` if in the YAML file exists the value
`title: Some Title`. The same is true for `type` such that `type: page` becomes
`type=page`. The common attributes along with the entirety of the YAML file will
be rolled up into a JSON attribute (as defined in the JBake documentation), and
will be named `metadata`.

The `tags` attribute is treated specially in the YAML file processing. It can be
a comma separated string value, or, it may be a YAML list of strings. This allows
for better layout of the metadata.

Custom metadata, such as `summary: Some longer page summary` will not be added
as top level attributes in the generated and merged content metadata, but will
instead be available in the JSON object `metadata` in the content. This is
accessible in JBake templates as `content.metadata`. Thus, to access `summary`,
one would write `content.metadata.summary`, and the YAML would look like:

----
type: page
status: published
summary: Some longer page summary
----

and the metadata prepended to the content file will look like (properties/attributes and JSON):

----
type=page
status=published
metadata={"type":"page","status":"published","summary":"Some longer page summary"}
~~~~~~
----

JBake can not currently separate the metadata from the content, and this setup
allows the metadata and the content to reside in separate files, and each file
to remain more pure or clean for its purpose.

Thus, if a file
exists `some-html.html.gsp` and a YAML file exists `some-html.html.gsp.yml`, the
`.yml` file will be merged with the resulting `.html` file, and JBake will
understand the result, and will use it when it "bakes" the site. The file
`some-html.html.gsp` will also be renamed to `some-html.html` during
preprocessing.

It is expected that content templates in this build will not have metadata
added directly to them, or the result will be undefined or may cause
errors in JBake.

The difference between "content templates" and "static content"
as defined below is the JBake information from the `global.yml`
configuration file can be accessed, and used to iterate over
logic, or to reference specific folders or information quite
similarly as JBake templates. The files may also have any logic
added to them which may be placed into a Groovy Server Page or
Simple Groovy Template. Thus is supports `<% %>` and `${ }`
notation. This does mean that JavaScript inline in these files
must be escaped accordingly as it will collide with Groovy syntax
at times, and thus is a good reason to keep JavaScript separated
into files, and not be inlined.

=== src/content (\*\*/\*.html and \*\*/\*.md and YAML) or Static Content

This build treats static content files ending in `.html` and `.md` similar
to the "Content Templates" except it does not process the content files as
Apache Groovy templates. It does however merge the `.html` or `.md` files
with a similarly named file ending in `.html.yml` and `.md.yml` in the
exact same way as this data is added to the "Content Templates". See
above for how those are transformed and merged. 

Thus, if a file
exists `some-html.html` and a YAML file exists `some-html.html.yml`, the
`.yml` file will be merged with the `.html` file, and JBake will understand
the result, and will use it when it "bakes" the site.

It is expected that
content files in this build will not have metadata added directly to them,
or the result will be undefined or may cause errors in JBake.

=== src/content (\*\*/\*.asciidoc)

This build also handles http://asciidoc.org[asciidoc] content. This content
is transformed to `.html` using http://asciidoctor.org/docs/asciidoctorj/[AsciidoctorJ].

All asciidoc files must define some JBake specific metadata like so (see http://jbake.org/docs/2.5.1/#metadata_header for details):

----
= Document title here
Jonathan Bullock
2013-10-17
:jbake-type: page
:jbake-tags: documentation, manual
:jbake-status: published
----

asciidoc documents may also define asciidoc metadata, such as `:toc:` or others. This metadata will be available
to jbake templates inside the `content` map. So, for instance, if the asciidoc contains this metadata:

----
= Document title here
Jonathan Bullock
2013-10-17
:jbake-type: page
:jbake-tags: documentation, manual
:jbake-status: published
:foo: bar
----

then the value of `foo` (i.e., `bar`) will be accessible in the template GSP file as `${content.foo}`.

=== Build Layout

During build, certain structures are setup in the `build` directory
of the main project. This directory should **never** be added to the
git repository, and is included in `.gitignore` file. The build
directories folders are described below. See `build.gradle` for
the technical details as to what files go where. It is extremely
important for the site "bake" or generation.

----
build
--bake
--bake-cache
--generated-bake
----assets
----content
----templates
--tomcat

----

[options="header"]
|===
|Name|Description

|bake|This is the finished product after JBake is run with the bake task
|bake-cache|This is where JBake caches its files to speed up the build; it is cleaned with the clean task
|generated-bake|This directories directories are generated from preprocessing tasks along with content; JBake uses this
|generated-bake/assets|Assets which are copied directly to bake from JBake into this structure such as scss->css
|generated-bake/content|Content files with prepended metadata go here; only those files should go here
|generated-bake/templates|JBake templates are copied here by preprocess tasks
|tomcat|This is the home directory of the tomcat process when run
|===

Specifics on the above may be added here if it is deemed more necessary.

=== Things To Keep In Mind

The various preprocess tasks move files into different areas
of the "Build Layout" from the "Source Layout". Those tasks
are responsible for copying or processing various discrete
file types. If something placed into the `src/content` structure
is not showing up where or as expected, `build.gradle` is
the place to look. Some items could be affected by variables
defined in `deps.gradle`, `utils.gradle`, or sources under
`buildSrc` as well, so keep this in mind.

== How To Bake The Site

There are multiple preprocess tasks in the build which setup the JBake data and structures. These structures
and data are setup in a way JBake understands and is configured in the build.

Run the following gradle build to preprocess content plus run JBake, and bake the web site:

=== Linux and Mac

`./gradlew preprocessContent bake`

or, simply

`./gradlew build`

=== Windows

`gradlew preprocessContent bake`

== Using The Local Tomcat Web Server

Once the server is running, the site can be baked or built multiple times, and the site can be
continually viewed in the browser.

=== Run Tomcat

From a separate terminal, change to the project directory, and run:

`./gradlew run`

This aspect of the build needs to be modified to allow the Tomcat
server to fork, and continue to exist independent of the Gradle
build. At the moment it consumes the shell/command line. This also
hogs a Gradle daemon.

=== Stop Tomcat

To stop the local Tomcat server, from a seperate terminal run:

`./gradlew stop`

or in the same one as `run` press `CONTROL-C`; hopefully this will
change in the future.

== Gradle Tasks of Note

=== Build tasks

. clean - deletes the build directory
. compileContentSass - compiles the projects SaSS files to the build directory.
. preprocessContent - runs all preprocess tasks to prepare for a bake
. preprocessContentAssets - pre-processes all .css, .js (not in the minimize list), images, etc from the content directory into the generated assets directory
. preprocessContentStatics - pre-processes all .html, .md, and possibly other files which have metadata/front matter in a side car file of the same name and extension with an extra .yml extension after that
. preprocessContentTemplates - pre-processes the *.gsp and *.gsp.yml files under content for baking as Groovy templates; edit global.yml to add data used in content other than YAML front matter
. preprocessTemplates - pre-processes the templates for JBake to use for baking

=== Documentation tasks

1. bake - bakes a jbake project; the final generator

=== Run tasks

1. run - runs the Tomcat server
2. stop - stops the Tomcat server


== Important directories

link:src/content[content]:: Main content
    - Different entries in asciidoc format.

link:src/content/assets[src/content/assets]:: CSS, images and Javascript files
    - link:src/content/assets:/css/[foundation *] : A copy of link:https://foundation.zurb.com/sites/download.html/[Foundation 6 for Sites] by Zurb. MIT License (see link:LICENSE-foundation[LICENSE-foundation]).
    - link:src/content/assets:/css/font-awesome.min.css[font-awesome.min.css] The link:https://github.com/FortAwesome/Font-Awesome[Font Awesome CSS]. MIT License (see https://github.com/FortAwesome/Font-Awesome#license).
    - link:src/content/assets:/fonts/[assets/fonts] The link:https://github.com/FortAwesome/Font-Awesome[Font Awesome Fonts]. Released under the link:[http://scripts.sil.org/OFL] SIL OFL 1.1 license.
    - link:src/content/assets:/images/[assets/images] Different images, such as the Apache NetBeans Logo and some background images.
    - link:src/content/assets:/css/netbeans.scss[netbeans.scss] : A simple SCSS file with some Foundation 6 modifications. 

link:src/content/templates[src/content/templates]:: Templates
    - link:src/content/templates/page.gsp[src/content/templates/page.gsp] A prototype page that includes the asciidoc's table of contents in an aside. May also include a 'hero' area and others.
    - link:src/content/templates/menu.gsp[src/content/templates/menu.gsp] A prototype menu bar (Foundation's top bar)
    - link:src/content/templates/head.gsp[src/content/templates/head.gsp] A prototype `<head>` tag that incorporates some of asciidoc's metadata (`keywords`, `description`, title).
    - link:src/content/templates/footer.gsp[src/content/templates/footer.gsp] A prototype `<footer>` tag with some ASF's required links.

build/bake:: The generated website.
    - This is a generated directory. 

