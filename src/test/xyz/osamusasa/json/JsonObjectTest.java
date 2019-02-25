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
        String nul = "{}";
        String jsonStr = "{\"str\":\"ok\"}";
        String jsonInt = "{\"int\":0}";
        String jsonObj = "{\"obj\":{\"str\":\"ok\"}}";
        String jsonArr = "{\"array\":[\"str\",\"int\",\"etc\"]}";
        String jsonCst = "{\"const\":false}";
        String jsonAll = "{\"str\":\"ok\",\"int\":0,\"obj\":{\"str\":\"ok\"},\"array\":[\"str\",\"int\",\"etc\"],\"const\":true}";

        assertAll(
                "create",
                ()->assertEquals(
                        new JsonTokener(nul).getObject().print(),
                        JsonObject.create(new JsonTokener(nul)).print(),
                        "nul"
                ),
                ()->assertEquals(
                        jsonStr,
                        JsonObject.create(new JsonTokener(jsonStr)).print(),
                        "str"
                ),
                ()->assertEquals(
                        jsonInt,
                        JsonObject.create(new JsonTokener(jsonInt)).print(),
                        "int"
                ),
                ()->assertEquals(
                        jsonObj,
                        JsonObject.create(new JsonTokener(jsonObj)).print(),
                        "obj"
                ),
                ()->assertEquals(
                        jsonArr,
                        JsonObject.create(new JsonTokener(jsonArr)).print(),
                        "arr"
                ),
                ()->assertEquals(
                        jsonCst,
                        JsonObject.create(new JsonTokener(jsonCst)).print(),
                        "const"
                ),
                ()->assertEquals(
                        jsonAll,
                        JsonObject.create(new JsonTokener(jsonAll)).print(),
                        "all"
                )
        );








/*
        String json = "{\"str\":\"ok\",\"int\":0,\"obj\":{\"str\":\"ok\"},\"array\":[\"str\",\"int\",\"etc\"],\"const\":true}";
        Assert.assertEquals(
                json,
                JsonObject.create(new JsonTokener(json)).print()
        );*/
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