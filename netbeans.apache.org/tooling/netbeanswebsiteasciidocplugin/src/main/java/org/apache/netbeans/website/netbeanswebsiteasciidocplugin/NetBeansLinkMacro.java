package org.apache.netbeans.website.netbeanswebsiteasciidocplugin;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;

public class NetBeansLinkMacro extends InlineMacroProcessor {
// very strict https only :p

    List<String> dummyrules = new ArrayList<>(List.of(
            "https://archive.apache.org/dist/",
            "https://dist.apache.org/",
            "https://downloads.apache.org/",
            "https://www.apache.org/",
            "https://cwiki.apache.org/",
            "https://www.youtube.com/",
            "https://github.com/apache/netbeans",
            "https://github.com/",
            "https://snapcraft.io/netbeans",
            "https://lists.apache.org/",
            "https://issues.apache.org/jira",
            "https://codelerity.com",
            "https://ci-builds.apache.org/",
            "https://ci-builds.apache.org/",
            "https://www.apache.org",
            "mailto:commits@netbeans.apache.org",
            "mailto:dev@netbeans.apache.org",
            "https://www.facebook.com/NetBeans",
            "http://wiki.apidesign.org",
            "https://www.w3.org",
            "https://wiki.php.net/",
            "https://whimsy.apache.org/",
            "https://wiki.apache.org/",
            "https://dukescript.com",
            "https://dzone.com/articles/",
            "https://docs.oracle.com/",
            //
            "https://leanpub.com/nbp4beginners",
            "https://link.springer.com/book/",
            "https://www.amazon.com/",
            "https://www.packtpub.com/", // seems not referencing the good book
            //
            "https://bz.apache.org/netbeans/",
            "https://en.wikipedia.org/",
            "https://openjdk.java.net/jeps/",
            "https://search.maven.org/",
            "https://openjfx.io/",
            "https://ant.apache.org",
            "https://archiva.apache.org",
            "https://maven.apache.org",
            "https://freemaker.apache.org",
            //
            "http://tdamir.blogspot.com/",
            "http://blogs.kiyut.com/tonny",
            "https://vieiro.net/",
            //
            "http://www.netbeans.org/ns",
            "http://www.netbeans.org/dtds"
    ));

    @Override
    public Object process(ContentNode parent, String target, Map<String, Object> attributes) {
        String content = target;
        String filename = NetBeansWebSiteExtension.githubize(parent.getDocument().getAttributes()) + "," + attributes;
        Map<String, String> globalAttributes = (Map) parent.getDocument().getAttributes();
        String auditFolder = globalAttributes.get("fileauditfolder");
        Path allowedURL = Paths.get(auditFolder + "/accepted.txt");
        Path rejectedURL = Paths.get(auditFolder + "/url.txt");
        Path apidocURL = Paths.get(auditFolder + "/apidocurl.txt");
        Path apioverURL = Paths.get(auditFolder + "/apiurl.txt");
        Path wikiMigration = Paths.get(auditFolder + "/wikimigration.txt");
        Path newFilePathmacro = Paths.get(auditFolder + "/macrocurl.txt");
        Path linkmustbexref = Paths.get(auditFolder + "/linkthatmustbexref.txt");
        boolean apidoccheck = target.startsWith("http://bits.netbeans.org/") || target.startsWith("https://bits.netbeans.org/");
        if (apidoccheck) {
            if (content.contains("overview-summary.html")) {
                NetBeansWebSiteExtension.writeAndAppend(apioverURL, filename, content);
            } else {
                // this should be empty in a while once we use macro PR #537
                NetBeansWebSiteExtension.writeAndAppend(apidocURL, filename, content);
            }
        } else {
            // if it's start with macro list on special file
            if (target.startsWith("{")) {
                NetBeansWebSiteExtension.writeAndAppend(newFilePathmacro, filename, content);
                // if start with protocol  we scan for rules
            } else if (target.startsWith("http") || target.startsWith("mailto")) {
                boolean accepted = false;
                // very basic rules
                for (String dummyrule : dummyrules) {
                    if (target.startsWith(dummyrule)) {
                        accepted = true;
                    }
                }
                if ((!target.startsWith("mailto") && !target.startsWith("https://lists.apache.org/")) && target.contains("netbeans.apache.org")) {
                    NetBeansWebSiteExtension.writeAndAppend(linkmustbexref, filename, content);
                } else {
                    if (accepted) {
                        NetBeansWebSiteExtension.writeAndAppend(allowedURL, filename, content);
                    } else {

//
                        if (target.startsWith("http://wiki.netbeans.org") && filename.contains(target.replace("http://wiki.netbeans.org", ""))) {
                            NetBeansWebSiteExtension.writeAndAppend(wikiMigration, filename, content);
                        } else {
                            NetBeansWebSiteExtension.writeAndAppend(rejectedURL, filename, content);
                        }
                    }
                }
            } else {
                boolean accepted = target.matches("[0-9a-zA-Z\\./].*");
// internal to the site jbake should take care starting with Dev... starting with / or . 
                if (accepted) {
                    if (!filename.contains("404.adoc")) {
                        NetBeansWebSiteExtension.writeAndAppend(linkmustbexref, filename, content);
                    }
                } else {
                    NetBeansWebSiteExtension.writeAndAppend(rejectedURL, filename, content);
                }
            }
        }
// return non modified
        Map<String, Object> options = new HashMap<>();
        options.put("type", ":link");
        options.put("target", target);
        return createPhraseNode(parent, "anchor", target, attributes, options);
    }

}
