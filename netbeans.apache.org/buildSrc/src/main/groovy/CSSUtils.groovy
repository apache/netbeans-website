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

import io.bit3.jsass.CompilationException;
import io.bit3.jsass.Compiler;
import io.bit3.jsass.Options;
import io.bit3.jsass.importer.Importer;
import io.bit3.jsass.importer.Import;
import io.bit3.jsass.Output;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter
import java.util.Arrays

class CSSUtils {

    static def compileSCSS(File netbeansSCSS) {

        File scssDirectory = netbeansSCSS.getParentFile()

        File cssFile = new File(scssDirectory, "netbeans.css");
        File mapFile = new File(scssDirectory, "netbeans.css.map");

        Compiler compiler = new Compiler()
        Options options = new Options()

        options.setSourceComments(false)
        options.setSourceMapContents(false)
        options.setSourceMapEmbed(false)
        options.setIsIndentedSyntaxSrc(false)
        options.setOmitSourceMapUrl(false)
        options.setOutputStyle(io.bit3.jsass.OutputStyle.COMPRESSED)
        options.setSourceMapFile(mapFile.toURI())
        options.setPrecision(3)
        options.setIncludePaths(Arrays.asList(netbeansSCSS))

        Output cssOutput = compiler.compileFile(
            netbeansSCSS.toURI(), 
            cssFile.toURI(),
            options)

        PrintWriter pw = new PrintWriter(new FileWriter(cssFile))
        pw.print(cssOutput.getCss())
        pw.close()

        PrintWriter sm = new PrintWriter(new FileWriter(mapFile))
        sm.print(cssOutput.getSourceMap())
        sm.close()

    }

}


