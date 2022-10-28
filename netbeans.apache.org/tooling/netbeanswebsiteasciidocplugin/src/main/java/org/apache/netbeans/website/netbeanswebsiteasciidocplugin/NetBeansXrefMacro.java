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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.asciidoctor.ast.ContentNode;
import org.asciidoctor.extension.InlineMacroProcessor;

public class NetBeansXrefMacro extends InlineMacroProcessor {
// very strict https only :p

    @Override
    public Object process(ContentNode parent, String target, Map<String, Object> attributes) {

        Map<String, String> globalAttributes = (Map) parent.getDocument().getAttributes();
        String filename = NetBeansWebSiteExtension.githubize(parent.getDocument().getAttributes());
        String filenamesane = (String) parent.getDocument().getAttributes().get("docfile");
        Path rootfile = Paths.get((String) parent.getDocument().getAttributes().get("docdir"));
        String auditFolder = globalAttributes.get("fileauditfolder");
        Path wrongxreftarget = Paths.get(auditFolder + "/wrongXREF");
        // 404 special as can be cast from relative place not only root
        Path file404 = Paths.get(auditFolder + "/404");
        if (filename.contains("404.adoc")) {
            NetBeansWebSiteExtension.writeAndAppend(file404, filename, target);
        } else {
            if (!target.contains("adoc")) {
                throw new IllegalStateException("CANNOT XREF:" + target + " in " + filename);
            } else {
                String[] split = target.split("adoc");
                Path resolve = Paths.get(filenamesane).getParent().resolve(split[0] + "adoc");
                if (filenamesane.contains("remotedev")) {
                    System.err.println("--------------------------------------"+resolve.toString());
                //throw new IllegalStateException("");
                }
                if (!Files.exists(resolve)) {
                    NetBeansWebSiteExtension.writeAndAppend(wrongxreftarget, filename, rootfile.relativize(resolve).toString());
                    //throw new IllegalStateException("XREF cannot find file" + resolve.toString());
                }
            }
        }
// return non modified
        Map<String, Object> options = new HashMap<>();
        options.put("type", ":xref");
        options.put("target", target);
        return createPhraseNode(parent, "anchor", target, attributes, options);
    }

}
