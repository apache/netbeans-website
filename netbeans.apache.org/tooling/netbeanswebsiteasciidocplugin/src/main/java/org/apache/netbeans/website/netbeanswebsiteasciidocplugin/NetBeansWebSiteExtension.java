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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.extension.JavaExtensionRegistry;
import org.asciidoctor.jruby.extension.spi.ExtensionRegistry;

public class NetBeansWebSiteExtension implements ExtensionRegistry {

    @Override
    public void register(Asciidoctor asciidoctor) {
        JavaExtensionRegistry javaExtensionRegistry = asciidoctor.javaExtensionRegistry();
        javaExtensionRegistry.treeprocessor(NetBeansWebSiteTreeprocessor.class);
        javaExtensionRegistry.inlineMacro("link", NetBeansLinkMacro.class);
        javaExtensionRegistry.inlineMacro("xref", NetBeansXrefMacro.class);
    }

    static String githubize(Map<String, Object> attributes) {

        Path file = Paths.get((String) attributes.get("docfile"));
        Path root = Paths.get((String) attributes.get("docdir"));
        return "https://github.com/apache/netbeans-website/netbeans.apache.org/src/content/" + root.relativize(file).toString();
        // throw new IllegalStateException();
    }

    public static void writeAndAppend(Path newFilePath, String adocsource, String issue) {
        if (Files.notExists(newFilePath)) {
            try {
                Files.createDirectories(newFilePath.getParent());
                Files.createFile(newFilePath);
            } catch (IOException ex) {
                Logger.getLogger(NetBeansWebSiteTreeprocessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            List<String> allline = Files.readAllLines(newFilePath);
            TreeMap<String, String> tm = new TreeMap<>();
            tm.put(issue, adocsource);
            for (String aline : allline) {
                String[] split = aline.split(SEPARATOR);
                if (tm.containsKey(split[0])) {
                    tm.put(split[0], tm.get(split[0]) + "," + split[1]);
                } else {
                    tm.put(split[0], split[1]);
                }
            }
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : tm.entrySet()) {
                sb.append(entry.getKey()).append(SEPARATOR).append(entry.getValue()).append("\n");
            }
            Files.write(newFilePath, sb.toString().getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(NetBeansWebSiteTreeprocessor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private static final String SEPARATOR = "::::";

}
