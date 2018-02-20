import groovy.json.JsonBuilder
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import java.util.List
import java.util.Properties
import org.yaml.snakeyaml.Yaml

class Utils {
    
    static final globals = new ConcurrentHashMap()

    private static def Map _addFlattenedProperties(Map m,
            String separator = '.',
            String key = '',
            Map receiver=[:],
            boolean top=true) {
        
        m.each {
            k, v ->
            if(v instanceof Map) {
                _addFlattenedProperties(v, separator, "${key?:''}${key?separator:''}${k}", receiver, false)
            } else {
                receiver["${key?:''}${key?separator:''}${k}"] = v
            }
        }
        //if we're the top caller
        if(top) {
            receiver.putAll(m)
        }
        return receiver
    }

    private static def Map addFlattendProperties(Map m) {
        _addFlattenedProperties(m)
    }

    /**
     * <p>Load the actual globals.yml file into the globals static map
     * to allow it to be used by any and all jobs; beyond this point
     * it is expected to be read-only and not edited. This will also
     * map all structured keys into Java Property like concatenations
     * such that if a yaml file contains a structure which would
     * produce a map hierarchy like <code>object.another.somethingElse</code>
     * the loaded map will contain an actual key "object.another.somethingElse"
     * with the same value associated to it. This will only happen for final
     * scalar values and not lists.</p>
     *
     * @param globalsFile the file to load
     */
    static def loadGlobals(File globalsFile) {
        if(globalsFile.exists()) {
            def map = loadYaml(globalsFile)
            map = addFlattendProperties(map)
            globals.putAll(map)
        }
    }

    static def loadYaml(File ymlFile) {
        def yaml = new Yaml()
        return yaml.load(ymlFile.text)
    }
    
    static def createMergeData(String yml, String file) {
        return new MergeData(yml: yml, file: file)
    }

    static def parseJBakeTags(def tags) {
        if(tags instanceof String) {
            return tags
        } else if (tags instanceof List) {
            return tags.join(",")
        } else {
            throw new IllegalStateException("The metadata tags must either be a string or a list.")
        }
    }
    
    static def writeCommonJBakeProperties(def data, def map, def out) {
        boolean foundType = false,
        foundStatus = false
        
        map.each { k, v ->
            switch(k) {
            case "type":
                if(v) {
                    foundType = true;
                }
                break;
            case "status":
                if(v) {
                    foundStatus = true;
                }
                break;
            default:
                break;
            }
            switch(k) {
            case "type":
            case "status":
            case "title":
            case "date":
                out << "${k}=${v}\n"
                break;
            case "tags":
                out << "${k}=${parseJBakeTags(v)}\n"
                break;
            default:
                //the rest we don't care about right now
                //we'll add that separately
                break;
            }
        }
        if(!foundType) {
            throw new IllegalStateException("The metadata for ${data.file} at ${data.yml} must include the type.")
        }
        if(!foundStatus) {
            throw new IllegalStateException("The metadata for ${data.file} at ${data.yml} must include the status.")
        }
    }
    
    /**
     * Merges a content file and a YAML file to match the expected values and format for JBake
     * @param data a specific yaml and file combination to merge together
     */
    static def mergeContentAndMetadata(MergeData data) {
        def contentFile = new File(data.file)
        def ymlFile = new File(data.yml)
        def yml = loadYaml(ymlFile)
        def jb = new JsonBuilder(yml)
        //store content for a moment
        def cdata = contentFile.text
        contentFile.withWriter "UTF-8", { out ->
            writeCommonJBakeProperties(data, yml, out)
            out << "metadata=${jb.toString()}\n"
            out << "~~~~~~\n"
            out << cdata
        }
    }
}
