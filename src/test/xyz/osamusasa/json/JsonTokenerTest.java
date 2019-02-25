package xyz.osamusasa.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonTokenerTest {

    @Test
    void next() {
        JsonTokener hello = new JsonTokener("hello");
        assertEquals('h', hello.next());
        assertEquals('e', hello.next());
        assertEquals('l', hello.next());
        assertEquals('l', hello.next());
        assertEquals('o', hello.next());
        assertEquals(0, hello.next());
        assertEquals(0, hello.next());
    }

    @Test
    void nextString() {
        JsonTokener tokener = new JsonTokener("hello,my,name,is,osamu");
        assertEquals("hello",tokener.nextString(','));
        assertEquals("my",tokener.nextString(','));
        assertEquals("name",tokener.nextString(','));
        assertEquals("is",tokener.nextString(','));
        assertEquals("osamu",tokener.nextString(','));
    }

    @Test
    void check() {
    }

    @Test
    void isObjectBegin() {
    }

    @Test
    void isObjectSpliter() {
    }

    @Test
    void isObjectPairSpliter() {
    }

    @Test
    void isObjectEnd() {
    }

    @Test
    void isStringBegin() {
    }

    @Test
    void isStringEnd() {
    }

    @Test
    void isArrayBegin() {
    }

    @Test
    void isArraySpliter() {
    }

    @Test
    void isArrayEnd() {
    }

    @Test
    void isNumberBegin() {
    }

    @Test
    void isNumberEnd() {
    }

    @Test
    void isTrue() {
    }

    @Test
    void isFalse() {
    }

    @Test
    void isNull() {
    }

    @Test
    void whichBegin() {
    }
}