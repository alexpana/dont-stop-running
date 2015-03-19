package org.vertexarmy.dsr.atlas_viewer;

import com.beust.jcommander.internal.Lists;
import com.beust.jcommander.internal.Maps;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

/**
 * created by Alex
 * on 3/14/2015.
 */
public class AtlasLoader {
    private final List<String> inputLines;
    private int currentLine = 0;
    private File atlasFile;

    private AtlasLoader(File atlasFile) {
        this.atlasFile = atlasFile;
        inputLines = readAllLines(atlasFile);
    }

    private List<String> readAllLines(File file) {
        try {
            List<String> fileContent = Files.readAllLines(file.toPath(), Charset.defaultCharset());
            return ImmutableList.copyOf(Iterables.filter(fileContent, new Predicate<String>() {
                @Override
                public boolean apply(String input) {
                    return !input.isEmpty();
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
            return Lists.newArrayList();
        }
    }

    private TextureAtlas doLoad() {
        String textureName = new File(atlasFile.getParent(), readLine()).toString();
        Map<String, String> atlasParameters = readParameters();
        Map<String, TextureAtlas.Region> regionMap = readRegions();

        TextureAtlas atlas = new TextureAtlas(textureName);
        for (Map.Entry<String, TextureAtlas.Region> region : regionMap.entrySet()) {
            atlas.addRegion(region.getKey(), region.getValue());
        }
        return atlas;
    }

    private Map<String, String> readParameters() {
        Map<String, String> result = Maps.newHashMap();

        String currentLine = readLine();
        while (currentLine != null && currentLine.contains(":")) {
            String[] keyValuePair = currentLine.split(":");
            String key = keyValuePair[0].trim();
            String value = keyValuePair[1].trim();
            result.put(key, value);
            currentLine = readLine();
        }
        if (hasMoreLines()) {
            undoReadLine();
        }

        return result;
    }

    private Map<String, TextureAtlas.Region> readRegions() {
        Map<String, TextureAtlas.Region> regionMap = Maps.newHashMap();

        while (hasMoreLines()) {
            String regionName = readLine();
            Map<String, String> regionParameters = readParameters();

            String[] position = regionParameters.get("xy").split(", ");
            String[] size = regionParameters.get("size").split(", ");

            regionMap.put(regionName, new TextureAtlas.Region(
                    Integer.valueOf(position[0]),
                    Integer.valueOf(position[1]),
                    Integer.valueOf(size[0]),
                    Integer.valueOf(size[1])));
        }
        return regionMap;
    }

    private boolean hasMoreLines() {
        return currentLine < inputLines.size();
    }

    private String readLine() {
        if (hasMoreLines()) {
            return inputLines.get(currentLine++);
        } else {
            return null;
        }
    }

    private void undoReadLine() {
        currentLine -= 1;
    }

    public static TextureAtlas load(File atlasFile) {
        AtlasLoader loader = new AtlasLoader(atlasFile);
        return loader.doLoad();
    }

}
