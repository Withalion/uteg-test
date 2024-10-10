package com.withalion.uteg_test;

import com.withalion.uteg_test.controller.JsonParserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
public class UtegTestUnitTests {
    @Autowired
    private JsonParserController jsonParserController;

    @Test
    void checkApplicationParsesPrintCommand() {
        assertThat(jsonParserController.executeCommand("print")).isEqualTo(0);
    }

    @Test
    void checkApplicationParsesFindMaxCommand() {
        assertThat(jsonParserController.executeCommand("findMax")).isEqualTo(0);
    }

    @Test
    void checkJsonPrints1() {
        assertThat(jsonParserController.createPrintableOutput("frog", 0)).isEqualTo("frog");
    }

    @Test
    void checkJsonPrints2() {
        assertThat(jsonParserController.createPrintableOutput("onion", 2)).isEqualTo(".... onion");
    }

    @Test
    void checkMaximumValuePrintsCorrectly() {
        jsonParserController.executeCommand("findMax");
        assertThat(jsonParserController.createPrintablePath()).isEqualTo("vegetables -> carrot -> red: 21");
    }
}
