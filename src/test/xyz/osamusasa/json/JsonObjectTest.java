package xyz.osamusasa.json;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonObjectTest {

    @Test
    void hasNext() {
    }

    @Test
    void next() {
    }

    @Test
    void iterator() {
    }

    @Test
    void add() {
    }

    @Test
    void get() {
    }

    @Test
    void create() {
        Assert.assertEquals(
                new JsonTokener("{}").getObject().print(),
                JsonObject.create().print());
    }

    @Test
    void create1() {
        Assert.assertEquals(
                new JsonTokener("{}").getObject().print(),
                JsonObject.create(new JsonTokener("{}")).print()
        );
        String jsonStr = "{\"str\":\"ok\"}";
        Assert.assertEquals(
                jsonStr,
                JsonObject.create(new JsonTokener(jsonStr)).print()
        );

        String json = "{\"str\":\"ok\",\"int\":0,\"obj\":{\"str\":\"ok\"},\"array\":[\"str\",\"int\",\"etc\"],\"const\":true}";
        Assert.assertEquals(
                json,
                JsonObject.create(new JsonTokener(json)).print()
        );
    }

    @Test
    void stream() {
    }

    @Test
    void size() {
        JsonObject o1 = new JsonTokener("{}").getObject();
        JsonObject o2 = new JsonTokener("{\"str\":\"ok\"}").getObject();
        JsonObject o3 = new JsonTokener("{\"str\":\"ok\",\"int\":0,\"obj\":{\"str\":\"ok\"},\"array\":[\"str\",\"int\",\"etc\"],\"const\":true}").getObject();
        Assert.assertEquals(0, o1.size());
        Assert.assertEquals(1, o2.size());
        Assert.assertEquals(5, o3.size());
    }
}