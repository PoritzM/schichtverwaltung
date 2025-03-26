package org.schichtverwaltung.zUtils;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Map;

//Zum Lesen von infos aus der Config-Datei
public class YmalReader {

    public static Object getYamlValue(String variableName) {
        String filePath = null;
        try {
            filePath = Paths.get("..", "SchichtverwaltungsBackendConfig.ymal").toString();

            InputStream inputStream = new FileInputStream(filePath);
            Yaml yaml = new Yaml();
            Map<String, Object> yamlData = yaml.load(inputStream);

            if (yamlData != null && yamlData.containsKey(variableName)) {
                return yamlData.get(variableName);
            } else {
                throw new RuntimeException("YMAL VALUE NOT FOUND!");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("YMAL FILE NOT FOUND!");
        }
    }
}