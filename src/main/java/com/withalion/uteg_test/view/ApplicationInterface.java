package com.withalion.uteg_test.view;

import com.withalion.uteg_test.controller.JsonParserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.logging.Logger;

@Service
public class ApplicationInterface implements CommandLineRunner {
    private final Scanner scanner;
    private final JsonParserController jsonParserController;
    private final Logger logger = Logger.getLogger(ApplicationInterface.class.getName());

    @Autowired
    public ApplicationInterface(JsonParserController jsonParserController) {
        this.scanner = new Scanner(System.in);
        this.jsonParserController = jsonParserController;
    }

    @Override
    public void run(String... args) {
        logger.info("Hello World!");
        while (true) {
            try {
                System.out.print("Please enter your command: ");
                var command = scanner.nextLine();
                switch (command) {
                    case "print":
                    case "findMax":
                        jsonParserController.executeCommand(command);
                        break;
                    default: throw new RuntimeException("Unknown command");
                }
            } catch (Exception exception){
                logger.warning("Parsing of command failed with: " + exception.getMessage());
            }
        }
    }
}
