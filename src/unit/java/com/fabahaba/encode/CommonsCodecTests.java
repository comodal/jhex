package com.fabahaba.encode;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CommonsCodecTests {

  @Test(expected = IllegalStateException.class)
  public void testDecodeCheckedHexCharArrayOddCharacters1() {
    JHex.decodeChecked(new char[]{'A'});
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeCheckedHexStringOddCharacters1() {
    JHex.decodeChecked("A");
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeCheckedHexCharArrayOddCharacters3() {
    JHex.decodeChecked(new char[]{'A', 'B', 'C'});
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeCheckedHexCharArrayOddCharacters5() {
    JHex.decodeChecked(new char[]{'A', 'B', 'C', 'D', 'E'});
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeBadCharacterPos0() {
    JHex.decodeChecked("q0");
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeBadCharacterPos1() {
    JHex.decodeChecked("0q");
  }

  @Test
  public void testDecodeByteArrayEmpty() {
    assertArrayEquals(new byte[0], JHex.decodeChecked(new char[0]));
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeByteArrayOddCharacters() {
    JHex.decodeChecked(new char[]{65});
  }

  @Test
  public void testDecodeByteBufferEmpty() {
    assertArrayEquals(new byte[0], JHex.decodeChecked(ByteBuffer.allocate(0)));
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeByteBufferOddCharacters() {
    final ByteBuffer buffer = ByteBuffer.allocate(1);
    buffer.put((byte) 65);
    JHex.decodeChecked(buffer);
  }

  @Test
  public void testDecodeHexStringEmpty() {
    assertArrayEquals(new byte[0], JHex.decodeChecked(""));
  }

  @Test(expected = IllegalStateException.class)
  public void testDecodeHexStringOddCharacters() {
    JHex.decodeChecked("6");
  }

  @Test
  public void testEncodeByteArrayEmpty() {
    assertArrayEquals(new char[0], JHex.encodeChars(new byte[0]));
  }

  @Test
  public void testEncodeByteBufferEmpty() {
    assertArrayEquals(new byte[0], JHex.encodeBytes(ByteBuffer.allocate(0)));
  }

  @Test
  public void testEncodeCharsByteBufferEmpty() {
    assertArrayEquals(new char[0], JHex.encodeChars(ByteBuffer.allocate(0)));
  }

  @Test
  public void testEncodeDecodeHexCharArrayRandom() {
    final Random random = ThreadLocalRandom.current();

    for (int i = 5;i > 0;i--) {
      final byte[] data = new byte[random.nextInt(10000) + 1];
      random.nextBytes(data);

      // static API
      final char[] encodedChars = JHex.encodeChars(data);
      byte[] decodedBytes = JHex.decode(encodedChars);
      assertArrayEquals(data, decodedBytes);

      // instance API with array parameter
      final byte[] encodedStringBytes = JHex.encodeBytes(data);
      decodedBytes = JHex.decode(encodedStringBytes);
      assertArrayEquals(data, decodedBytes);

      // instance API with char[] (Object) parameter
      String dataString = new String(encodedChars);
      char[] encodedStringChars = JHex.encodeChars(dataString.getBytes(StandardCharsets.UTF_8));
      decodedBytes = JHex.decode(encodedStringChars);
      assertArrayEquals(dataString.getBytes(StandardCharsets.UTF_8), decodedBytes);

      // instance API with String (Object) parameter
      dataString = new String(encodedChars);
      encodedStringChars = JHex.encodeChars(dataString.getBytes(StandardCharsets.UTF_8));
      decodedBytes = JHex.decode(new String(encodedStringChars));
      assertArrayEquals(dataString.getBytes(StandardCharsets.UTF_8), decodedBytes);
    }
  }

  @Test
  public void testEncodeHexByteArrayEmpty() {
    assertArrayEquals(new char[0], JHex.encodeChars(new byte[0]));
    assertArrayEquals(new byte[0], JHex.encodeBytes(new byte[0]));
  }

  @Test
  public void testEncodeHexByteArrayHelloWorldLowerCaseHex() {
    final byte[] data = "Hello World".getBytes(StandardCharsets.UTF_8);
    final String expected = "48656c6c6f20576f726c64";
    char[] actual;
    actual = JHex.encodeChars(data);
    assertEquals(expected, new String(actual));
    actual = JHex.encodeUpperChars(data);
    assertEquals(expected.toUpperCase(Locale.ENGLISH), new String(actual));
  }

  @Test
  public void testEncodeHexByteBufferHelloWorldLowerCaseHex() {
    final ByteBuffer buffer = ByteBuffer.wrap("Hello World".getBytes(StandardCharsets.UTF_8));
    final String expected = "48656c6c6f20576f726c64";
    char[] actual;
    actual = JHex.encodeChars(buffer);
    assertEquals(expected, new String(actual));
    buffer.flip();
    actual = JHex.encodeUpperChars(buffer);
    assertEquals(expected.toUpperCase(Locale.ENGLISH), new String(actual));
  }

  @Test
  public void testEncodeHex_ByteBufferOfZeroes() {
    final char[] chars = JHex.encodeChars(ByteBuffer.allocate(36));
    assertEquals("000000000000000000000000000000000000000000000000000000000000000000000000",
        new String(chars));
  }

  @Test
  public void testEncodeHexByteString_ByteBufferOfZeroes() {
    final String chars = JHex.encode(ByteBuffer.allocate(36));
    assertEquals("000000000000000000000000000000000000000000000000000000000000000000000000",
        chars);
  }

  @Test
  public void testEncodeHexByteArrayZeroes() {
    final char[] chars = JHex.encodeChars(new byte[36]);
    assertEquals("000000000000000000000000000000000000000000000000000000000000000000000000",
        new String(chars));
  }

  @Test
  public void testEncodeHexByteString_ByteArrayOfZeroes() {
    final String chars = JHex.encode(new byte[36]);
    assertEquals("000000000000000000000000000000000000000000000000000000000000000000000000",
        chars);
  }

  @Test
  public void testEncodeHexByteString_ByteArrayBoolean_ToLowerCase() {
    assertEquals("0a", JHex.encode(new byte[]{10}));
  }

  @Test
  public void testEncodeHexByteString_ByteArrayBoolean_ToUpperCase() {
    assertEquals("0A", JHex.encodeUpper(new byte[]{10}));
  }

  @Test
  public void testEncodeHexByteString_ByteBufferBoolean_ToLowerCase() {
    assertEquals("0a", JHex.encode(ByteBuffer.wrap(new byte[]{10})));
  }

  @Test
  public void testEncodeHexByteString_ByteBufferBoolean_ToUpperCase() {
    assertEquals("0A", JHex.encodeUpper(ByteBuffer.wrap(new byte[]{10})));
  }
}
