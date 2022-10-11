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
import java.nio.file.StandardOpenOption;
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

    public static void writeAndAppend(Path newFilePath, String content) {
        if (Files.notExists(newFilePath)) {
            try {
                Files.createDirectories(newFilePath.getParent());
                Files.createFile(newFilePath);
            } catch (IOException ex) {
                Logger.getLogger(NetBeansWebSiteTreeprocessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Files.write(newFilePath, content.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ex) {
            Logger.getLogger(NetBeansWebSiteTreeprocessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
