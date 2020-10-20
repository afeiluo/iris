package com.afeiluo.jdk_serialization;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.Assert;

/**
 * Created by qiaolinfei on 2020/8/12.
 */
public class JacksonUnmarshalling {
    public static void main(String[] args) throws Exception {
        String json = "{ \"name\":\"Pear yPhone 72\",\"category\":\"cellphone\", \"details\":[{\"displayAspectRatio\":\"97:3\",\"audioConnector\":\"none\"},{\"displayAspectRatio\":\"97:3\",\"audioConnector\":\"no\"}]}";

        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(json, Product.class);

        Assert.isTrue(product.getName().equals("Pear yPhone 72"), "失败");
        Assert.isTrue(product.getDetails()[0].get("audioConnector").asText().equals("none"), "失败");
    }
}

class Product {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public JsonNode[] getDetails() {
        return details;
    }

    public void setDetails(JsonNode[] details) {
        this.details = details;
    }


    String name;
    String category;
    JsonNode[] details;
}


