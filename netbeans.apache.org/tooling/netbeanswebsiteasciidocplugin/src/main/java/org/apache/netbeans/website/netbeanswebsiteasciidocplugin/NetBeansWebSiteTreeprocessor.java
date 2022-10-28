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
import java.util.List;
import java.util.Map;
import org.asciidoctor.ast.Block;
import org.asciidoctor.ast.Document;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.Treeprocessor;

public class NetBeansWebSiteTreeprocessor extends Treeprocessor {

    private Map<String, String> attributes;

    public NetBeansWebSiteTreeprocessor(Map<String, Object> config) {
        super(config);
    }

    @Override
    public Document process(Document dcmnt) {
        attributes = (Map) dcmnt.getOptions().get("attributes");
        processBlock((StructuralNode) dcmnt);
        return dcmnt;
    }

    private void processBlock(StructuralNode block) {
        List<StructuralNode> blocks = block.getBlocks();
        for (StructuralNode currentBlock : blocks) {
            if (currentBlock instanceof Block) {
                for (String line : ((Block) currentBlock).getLines()) {
                    // detect plain href
                    String context = currentBlock.getContext();
                    if (line.contains("href=\"")) {
                        //case in listing

                        if ("listing".equals(context)) {
                            // is ok to get href in listing
                        } else if ("pass".equals(context)) {
                            // is ok in passthrough block
                        } else {
                            appendErrorReport("NOK" + context + " " + line);
                            log(new org.asciidoctor.log.LogRecord(
                                    org.asciidoctor.log.Severity.ERROR,
                                    currentBlock.getSourceLocation(),
                                    attributes.get("docfile") + ">>>>" + line));
                        }
                    } else if (line.contains(" htt")) {
                        if ("listing".equals(context)) {
                            // is ok to get href in listing
                        } else {
                            appendErrorReport("NOK" + context + " " + line);
                            log(new org.asciidoctor.log.LogRecord(
                                    org.asciidoctor.log.Severity.ERROR,
                                    currentBlock.getSourceLocation(),
                                    attributes.get("docfile") + ">>>>" + line));
                        }
                    }
                }

            }
            processBlock(currentBlock);
        }

    }

    private void appendErrorReport(String line) {
        String asciidocfile = attributes.get("docfile");
        String fileName = attributes.get("fileauditfolder");
        String content = ">>>" + asciidocfile + "<<< " + line + "";
        Path newFilePath = Paths.get(fileName + "/plainhref");
        NetBeansWebSiteExtension.writeAndAppend(newFilePath, asciidocfile, content);
    }

}
