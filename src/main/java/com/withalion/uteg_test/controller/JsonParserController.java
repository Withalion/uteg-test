package com.withalion.uteg_test.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class JsonParserController {
    private final JsonNode products;
    private ArrayList<String> maxPath;

    public JsonParserController() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        products = objectMapper.readTree(new File("src/main/resources/products.json"));
    }

    /**
     * Entry function which executes commands typed by user
     * @param command user command (print / findMax)
     */
    public int executeCommand(String command) {
        if (command.equals("print")){
            try {
                processNode(products, 0);
                return 0;
            } catch (Exception e) {
                return 1;
            }

        } else if (command.equals("findMax")){
            try {
                findMaximum(products, new ArrayList<>());
                System.out.println(createPrintablePath());
                // here should be some cleanup function, however as we are loading the file only once per app start we
                // could use this to our advantage and not traverse the JSON everytime
                //maxPath = null;
                return 0;
            } catch (Exception e) {
                return 1;
            }

        }
        return 1;
    }

    /**
     * Recursive function to find the key with the biggest value
     * @param node root node or current node
     * @param currentPath path in json to current node
     */
    private void findMaximum(JsonNode node, ArrayList<String> currentPath) {
        // node is a leaf
        if (node.isValueNode()){
            try {
                // if there is already a maximum check if we have a new
                var currentMax = Integer.parseInt(maxPath.getLast());
                if (currentMax < node.asInt()) {
                    maxPath = new ArrayList<>(currentPath);
                    maxPath.add(node.asText());
                }
            // exception means there wasn't leaf yet, so we found new maximum
            } catch (Exception exception) {
                maxPath = new ArrayList<>(currentPath);
                maxPath.add(node.asText());
            }
        // node is inner node
        } else if (node.isObject()){
            var fields = node.fields();
            while (fields.hasNext()) {
                var jsonField = fields.next();
                currentPath.add(jsonField.getKey());
                findMaximum(jsonField.getValue(), currentPath);
                currentPath.removeLast();
            }
        }
    }

    /**
     * Recursive function which goes through all nodes in JSON an initiates the printout
     * @param node current node which is examined or root node when called first time
     * @param depth current depth of node (needed for indentation), 0 at root
     */
    private void processNode(JsonNode node, int depth) {
        if (node.isObject()){
            var fields = node.fields();
            while (fields.hasNext()) {
                var jsonField = fields.next();
                System.out.println(createPrintableOutput(jsonField.getKey(), depth));
                processNode(jsonField.getValue(), depth + 1);
            }
        }
    }

    /**
     * Function creates the visual format specified in test1.md and prints to the output.
     * The function was changed to public for sake of testing
     * @param text the name of the key in the map
     * @param offset the depth of given key in JSON
     */
    public String createPrintableOutput(String text, int offset) {
        if (offset == 0){
            return text;
        } else {
            return ".".repeat(Math.max(0, (offset * 2))) + " " + text;
        }
    }

    /**
     * Function prints chain path to the maximum value in JSON.
     * The function was changed to public for sake of testing
     */
    public String createPrintablePath(){
        //we are doing this deep copy for the sake of reusability and testing
        var tempList = new ArrayList<>(maxPath);
        tempList.set(maxPath.size() - 2, maxPath.get(maxPath.size() - 2) + ": "+ maxPath.getLast());
        tempList.removeLast();
        return String.join(" -> ", tempList);
    }
}
