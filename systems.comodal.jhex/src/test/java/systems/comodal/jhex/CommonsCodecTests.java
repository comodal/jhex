package systems.comodal.jhex;

import org.junit.jupiter.api.Test;
import systems.comodal.jhex.JHex;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

final class CommonsCodecTests {

  @Test
  void testDecodeCheckedHexCharArrayOddCharacters1() {
    assertEquals("Invalid hex encoding length of 1.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked(new char[]{'A'})).getMessage());
  }

  @Test
  void testDecodeCheckedHexStringOddCharacters1() {
    assertEquals("Invalid hex encoding length of 1.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked("A")).getMessage());
  }

  @Test
  void testDecodeCheckedHexCharArrayOddCharacters3() {
    assertEquals("Invalid hex encoding length of 3.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked(new char[]{'A', 'B', 'C'})).getMessage());
  }

  @Test
  void testDecodeCheckedHexCharArrayOddCharacters5() {
    assertEquals("Invalid hex encoding length of 5.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked(new char[]{'A', 'B', 'C', 'D', 'E'})).getMessage());
  }

  @Test
  void testDecodeBadCharacterPos0() {
    assertEquals("Invalid character 'q' for hex encoding at position 0.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked("q0")).getMessage());
  }

  @Test
  void testDecodeBadCharacterPos1() {
    assertEquals("Invalid character 'q' for hex encoding at position 1.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked("0q")).getMessage());
  }

  @Test
  void testDecodeByteArrayEmpty() {
    assertArrayEquals(new byte[0], JHex.decodeChecked(new char[0]));
  }

  @Test
  void testDecodeByteArrayOddCharacters() {
    assertEquals("Invalid hex encoding length of 1.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked(new char[]{65})).getMessage());
  }

  @Test
  void testDecodeByteBufferEmpty() {
    assertArrayEquals(new byte[0], JHex.decodeChecked(ByteBuffer.allocate(0)));
  }

  @Test
  void testDecodeByteBufferOddCharacters() {
    final ByteBuffer buffer = ByteBuffer.allocate(1);
    buffer.put((byte) 65);
    assertEquals("Invalid hex encoding length of 1.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked(buffer)).getMessage());
  }

  @Test
  void testDecodeHexStringEmpty() {
    assertArrayEquals(new byte[0], JHex.decodeChecked(""));
  }

  @Test
  void testDecodeHexStringOddCharacters() {
    assertEquals("Invalid hex encoding length of 1.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked("6")).getMessage());
  }

  @Test
  void testEncodeByteArrayEmpty() {
    assertArrayEquals(new char[0], JHex.encodeChars(new byte[0]));
  }

  @Test
  void testEncodeByteBufferEmpty() {
    assertArrayEquals(new byte[0], JHex.encodeBytes(ByteBuffer.allocate(0)));
  }

  @Test
  void testEncodeCharsByteBufferEmpty() {
    assertArrayEquals(new char[0], JHex.encodeChars(ByteBuffer.allocate(0)));
  }

  @Test
  void testEncodeDecodeHexCharArrayRandom() {
    final Random random = ThreadLocalRandom.current();

    for (int i = 5; i > 0; i--) {
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
  void testEncodeHexByteArrayEmpty() {
    assertArrayEquals(new char[0], JHex.encodeChars(new byte[0]));
    assertArrayEquals(new byte[0], JHex.encodeBytes(new byte[0]));
  }

  @Test
  void testEncodeHexByteArrayHelloWorldLowerCaseHex() {
    final byte[] data = "Hello World".getBytes(StandardCharsets.UTF_8);
    final String expected = "48656c6c6f20576f726c64";
    char[] actual;
    actual = JHex.encodeChars(data);
    assertEquals(expected, new String(actual));
    actual = JHex.encodeUpperChars(data);
    assertEquals(expected.toUpperCase(Locale.ENGLISH), new String(actual));
  }

  @Test
  void testEncodeHexByteBufferHelloWorldLowerCaseHex() {
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
  void testEncodeHex_ByteBufferOfZeroes() {
    final char[] chars = JHex.encodeChars(ByteBuffer.allocate(36));
    assertEquals("000000000000000000000000000000000000000000000000000000000000000000000000",
        new String(chars));
  }

  @Test
  void testEncodeHexByteString_ByteBufferOfZeroes() {
    final String chars = JHex.encode(ByteBuffer.allocate(36));
    assertEquals("000000000000000000000000000000000000000000000000000000000000000000000000",
        chars);
  }

  @Test
  void testEncodeHexByteArrayZeroes() {
    final char[] chars = JHex.encodeChars(new byte[36]);
    assertEquals("000000000000000000000000000000000000000000000000000000000000000000000000",
        new String(chars));
  }

  @Test
  void testEncodeHexByteString_ByteArrayOfZeroes() {
    final String chars = JHex.encode(new byte[36]);
    assertEquals("000000000000000000000000000000000000000000000000000000000000000000000000",
        chars);
  }

  @Test
  void testEncodeHexByteString_ByteArrayBoolean_ToLowerCase() {
    assertEquals("0a", JHex.encode(new byte[]{10}));
  }

  @Test
  void testEncodeHexByteString_ByteArrayBoolean_ToUpperCase() {
    assertEquals("0A", JHex.encodeUpper(new byte[]{10}));
  }

  @Test
  void testEncodeHexByteString_ByteBufferBoolean_ToLowerCase() {
    assertEquals("0a", JHex.encode(ByteBuffer.wrap(new byte[]{10})));
  }

  @Test
  void testEncodeHexByteString_ByteBufferBoolean_ToUpperCase() {
    assertEquals("0A", JHex.encodeUpper(ByteBuffer.wrap(new byte[]{10})));
  }
}
