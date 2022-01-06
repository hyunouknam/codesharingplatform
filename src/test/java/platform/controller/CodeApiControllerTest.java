package platform.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CodeApiController.class)
class CodeApiControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void postCode() {
    }

    @Test
    void getApiCode() throws Exception {
        String id = "38003b03-6f6c-43b0-9fbd-fe2a51da35a2";
        RequestBuilder request = MockMvcRequestBuilders.get("/api/code/{id}");
        MvcResult result = mvc.perform(request).andReturn();
        assertEquals("public static void main(String[] args) {\n    SpringApplication.run(222CodeSharingPlatform.class, args);\n}", result.getResponse().getContentAsString());
    }

    @Test
    void getLatestApiCode() {
    }
}