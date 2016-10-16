package com.fabahaba.encode;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.Locale;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HexEncodeTest {

  static final String TEST_HEX =
      "4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b";

  @Test
  public void decodeEncodeLower() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeCharSequenceEncodeLower() {
    final byte[] lower = JHex.decodeToCharArray(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeBytesEncodeLower() {
    final byte[] lower = JHex.decode(TEST_HEX.getBytes(US_ASCII));
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeByteBufferEncodeLower() {
    final byte[] lower = JHex.decode(ByteBuffer.wrap(TEST_HEX.getBytes(US_ASCII)));
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeCheckedEncodeLower() {
    final byte[] lower = JHex.decodeChecked(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeCheckedCharSequenceEncodeLower() {
    final byte[] lower = JHex.decodeCheckedToCharArray( TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeCheckedBytesEncodeLower() {
    final byte[] lower = JHex.decodeChecked(TEST_HEX.getBytes(US_ASCII));
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeCheckedByteBufferEncodeLower() {
    final byte[] lower = JHex
        .decodeChecked(ByteBuffer.wrap(TEST_HEX.getBytes(US_ASCII)));
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeOffsetEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decode(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeOffsetCharSequenceEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodeToCharArray( TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeOffsetCheckedEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodeChecked(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeOffsetCheckedCharSequenceEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodeCheckedToCharArray(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeOffsetCheckedBytesEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodeChecked(TEST_HEX.getBytes(US_ASCII), lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeOffsetCheckedByteBufferEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodeChecked(ByteBuffer.wrap(TEST_HEX.getBytes(US_ASCII)), lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeEncodeLowerOffset() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower, 0, lower.length));
  }

  @Test
  public void decodeEncodeUpperOffset() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_HEX.toUpperCase(Locale.ENGLISH),
        JHex.encodeUpper(lower, 0, lower.length));
  }

  @Test
  public void decodeEncodeLowerOffsetChars() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_HEX.toCharArray(), JHex.encodeChars(lower, 0, lower.length));
  }

  @Test
  public void decodeEncodeOffsetUpperChars() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_HEX.toUpperCase(Locale.ENGLISH).toCharArray(),
        JHex.encodeUpperChars(lower, 0, lower.length));
  }

  @Test
  public void decodeEncodeLowerOffsetBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_HEX.getBytes(US_ASCII), JHex.encodeBytes(lower, 0, lower.length));
  }

  @Test
  public void decodeEncodeOffsetUpperBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_HEX.toUpperCase(Locale.ENGLISH).getBytes(US_ASCII),
        JHex.encodeUpperBytes(lower, 0, lower.length));
  }

  @Test
  public void decodeEncodeLowerByteBufferFixedLength() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(ByteBuffer.wrap(lower), lower.length));
  }

  @Test
  public void decodeEncodeUpperByteBufferFixedLength() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_HEX.toUpperCase(Locale.ENGLISH),
        JHex.encodeUpper(ByteBuffer.wrap(lower), lower.length));
  }

  @Test
  public void decodeEncodeCharsLowerByteBufferFixedLength() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_HEX.toCharArray(),
        JHex.encodeChars(ByteBuffer.wrap(lower), lower.length));
  }

  @Test
  public void decodeEncodeUpperCharsByteBufferFixedLength() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_HEX.toUpperCase(Locale.ENGLISH).toCharArray(),
        JHex.encodeUpperChars(ByteBuffer.wrap(lower), lower.length));
  }

  @Test
  public void decodeEncodeBytesLowerByteBufferFixedLength() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_HEX.getBytes(US_ASCII),
        JHex.encodeBytes(ByteBuffer.wrap(lower), lower.length));
  }

  @Test
  public void decodeEncodeUpperBytesByteBufferFixedLength() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_HEX.toUpperCase(Locale.ENGLISH).getBytes(US_ASCII),
        JHex.encodeUpperBytes(ByteBuffer.wrap(lower), lower.length));
  }

  @Test
  public void encodeReverse() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertEquals(TEST_HEX, JHex.encodeReverse(reverse, 31, 32));
  }

  @Test
  public void encodeReverseChars() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_HEX.toCharArray(), JHex.encodeReverseChars(reverse, 31, 32));
  }

  @Test
  public void encodeReverseBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_HEX.getBytes(US_ASCII), JHex.encodeReverseBytes(reverse, 31, 32));
  }

  @Test
  public void encodeUpperReverseBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_HEX.toUpperCase(Locale.ENGLISH).getBytes(US_ASCII),
        JHex.encodeUpperReverseBytes(reverse, 31, 32));
  }

  @Test
  public void encodeUpperReverse() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertEquals(TEST_HEX.toUpperCase(Locale.ENGLISH),
        JHex.encodeUpperReverse(reverse, 31, 32));
  }

  @Test
  public void encodeUpperReverseChars() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_HEX.toUpperCase(Locale.ENGLISH).toCharArray(),
        JHex.encodeUpperReverseChars(reverse, 31, 32));
  }

  @Test
  public void encodeByteBufferReverse() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertEquals(TEST_HEX,
        JHex.encodeReverse(ByteBuffer.wrap(reverse), 31, 32));
  }

  @Test
  public void encodeByteBufferUpperReverse() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertEquals(TEST_HEX.toUpperCase(Locale.ENGLISH),
        JHex.encodeUpperReverse(ByteBuffer.wrap(reverse), 31, 32));
  }

  @Test
  public void encodeByteBufferReverseChars() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_HEX.toCharArray(),
        JHex.encodeReverseChars(ByteBuffer.wrap(reverse), 31, 32));
  }

  @Test
  public void encodeByteBufferUpperReverseChars() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_HEX.toUpperCase(Locale.ENGLISH).toCharArray(),
        JHex.encodeUpperReverseChars(ByteBuffer.wrap(reverse), 31, 32));
  }

  @Test
  public void encodeByteBufferReverseBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_HEX.getBytes(US_ASCII),
        JHex.encodeReverseBytes(ByteBuffer.wrap(reverse), 31, 32));
  }

  @Test
  public void encodeUpperByteBufferReverseBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_HEX.toUpperCase(Locale.ENGLISH).getBytes(US_ASCII),
        JHex.encodeUpperReverseBytes(ByteBuffer.wrap(reverse), 31, 32));
  }

  @Test
  public void decodeEncodeUpper() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final String upperHex = TEST_HEX.toUpperCase(Locale.ENGLISH);
    assertEquals(upperHex, JHex.encodeUpper(lower));
    final byte[] upper = JHex.decode(upperHex);
    assertEquals(TEST_HEX, JHex.encode(upper));
    assertEquals(upperHex, JHex.encodeUpper(upper));
  }

  @Test
  public void decodeEncodeUpperBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] upperHex = TEST_HEX.toUpperCase(Locale.ENGLISH).getBytes(US_ASCII);
    assertArrayEquals(upperHex, JHex.encodeUpperBytes(lower));
    final byte[] upper = JHex.decode(upperHex);
    assertArrayEquals(upperHex, JHex.encodeUpperBytes(upper));
  }

  @Test
  public void decodeEncodeUpperByteBuffer() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] upperHex = TEST_HEX.toUpperCase(Locale.ENGLISH).getBytes(US_ASCII);
    assertArrayEquals(upperHex, JHex.encodeUpperBytes(ByteBuffer.wrap(lower)));
    final byte[] upper = JHex.decode(upperHex);
    assertArrayEquals(upperHex, JHex.encodeUpperBytes(ByteBuffer.wrap(upper)));
  }

  @Test
  public void decodePrimIterOffsetEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodePrimIter(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodePrimIterEncodeLower() {
    final byte[] lower = JHex.decodePrimIter(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodePrimIterEncodeReverse() {
    final byte[] lower = JHex.decodePrimIter(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertEquals(TEST_HEX, JHex.encodeReverse(reverse, 31, 32));
  }

  @Test
  public void decodePrimIterEncodeUpper() {
    final byte[] lower = JHex.decodePrimIter(TEST_HEX);
    final String upperHex = TEST_HEX.toUpperCase(Locale.ENGLISH);
    assertEquals(upperHex, JHex.encodeUpper(lower));
    final byte[] upper = JHex.decodePrimIter(upperHex);
    assertEquals(TEST_HEX, JHex.encode(upper));
    assertEquals(upperHex, JHex.encodeUpper(upper));
  }

  @Test
  public void decodePrimIterCheckedOffsetEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodePrimIterChecked(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void encodeEmpty() {
    assertArrayEquals(new byte[0], JHex.encodeBytes(new byte[0], 0, 0));
    assertArrayEquals(new char[0], JHex.encodeReverseChars(new byte[0], 0, 0));
    assertArrayEquals(new char[0], JHex.encodeUpperReverseChars(new byte[0], 0, 0));
    assertArrayEquals(new char[0], JHex.encodeReverseChars(ByteBuffer.wrap(new byte[0]), 0, 0));
    assertArrayEquals(new char[0],
        JHex.encodeUpperReverseChars(ByteBuffer.wrap(new byte[0]), 0, 0));
    assertArrayEquals(new byte[0], JHex.encodeReverseBytes(new byte[0], 0, 0));
    assertArrayEquals(new byte[0], JHex.encodeUpperReverseBytes(new byte[0], 0, 0));
    assertArrayEquals(new byte[0], JHex.encodeReverseBytes(ByteBuffer.wrap(new byte[0]), 0, 0));
    assertArrayEquals(new byte[0],
        JHex.encodeUpperReverseBytes(ByteBuffer.wrap(new byte[0]), 0, 0));
  }

  @Test
  public void decodeEmpty() {
    final CharSequence emptyCharSequence = "";
    assertArrayEquals(new byte[0], JHex.decode(emptyCharSequence));
    assertArrayEquals(new byte[0], JHex.decode(new byte[0]));
    assertArrayEquals(new byte[0], JHex.decode(new char[0]));
    assertArrayEquals(new byte[0], JHex.decode(ByteBuffer.wrap(new byte[0])));
    assertArrayEquals(new byte[0], JHex.decodeChecked(emptyCharSequence));
    assertArrayEquals(new byte[0], JHex.decodeChecked(new char[0]));
    assertArrayEquals(new byte[0], JHex.decodeChecked(new byte[0]));
    assertArrayEquals(new byte[0], JHex.decodeChecked(ByteBuffer.wrap(new byte[0])));
    assertArrayEquals(new byte[0], JHex.decodePrimIterChecked(""));
    assertArrayEquals(new byte[0], JHex.decodePrimIter(""));

    final byte[] empty = new byte[4];
    JHex.decodeChecked(new byte[0], empty, 0);
    assertArrayEquals(new byte[4], empty);
    JHex.decodeChecked(emptyCharSequence, empty, 0);
    assertArrayEquals(new byte[4], empty);
    JHex.decodeChecked(new char[0], empty, 0);
    assertArrayEquals(new byte[4], empty);
    JHex.decodeChecked(ByteBuffer.wrap(new byte[0]), empty, 0);
    assertArrayEquals(new byte[4], empty);
    JHex.decodePrimIterChecked("", empty, 0);
    assertArrayEquals(new byte[4], empty);
  }

  @Test
  public void decodeNull() {
    assertThrows(NullPointerException.class, () -> JHex.decodeChecked((CharSequence) null));
    assertThrows(NullPointerException.class, () -> JHex.decodeChecked((String) null));
    assertThrows(NullPointerException.class, () -> JHex.decodeChecked((char[]) null));
    assertThrows(NullPointerException.class, () -> JHex.decodeChecked((byte[]) null));
    assertThrows(NullPointerException.class, () -> JHex.decodeChecked((ByteBuffer) null));

    assertThrows(NullPointerException.class,
        () -> JHex.decodeChecked((CharSequence) null, new byte[0], 0));
    assertThrows(NullPointerException.class,
        () -> JHex.decodeChecked((String) null, new byte[0], 0));
    assertThrows(NullPointerException.class,
        () -> JHex.decodeChecked((char[]) null, new byte[0], 0));
    assertThrows(NullPointerException.class,
        () -> JHex.decodeChecked((byte[]) null, new byte[0], 0));
    assertThrows(NullPointerException.class,
        () -> JHex.decodeChecked((ByteBuffer) null, new byte[0], 0));
  }

  @Test
  public void decodeCheckedOddLength() {
    decodeCheckedInvalidEncodings("1", "Invalid hex encoding length of 1.");
  }

  @Test
  public void decodeCheckedBadCharEven() {
    decodeCheckedInvalidEncodings("12!3",
        "Invalid character '!' for hex encoding at position 2.");
    decodeCheckedInvalidEncodings("12q3",
        "Invalid character 'q' for hex encoding at position 2.");
  }

  @Test
  public void decodeCheckedBadCharOdd() {
    decodeCheckedInvalidEncodings("123!",
        "Invalid character '!' for hex encoding at position 3.");
    decodeCheckedInvalidEncodings("123q",
        "Invalid character 'q' for hex encoding at position 3.");
  }

  private void decodeCheckedInvalidEncodings(final String invalid, final String expectedMsg) {
    assertEquals(expectedMsg, assertThrows(IllegalArgumentException.class,
        () -> JHex.decodeCheckedToCharArray(invalid)).getMessage());
    assertEquals(expectedMsg, assertThrows(IllegalArgumentException.class,
        () -> JHex.decodeCheckedToCharArray(invalid, new byte[2], 0)).getMessage());

    assertEquals(expectedMsg, assertThrows(IllegalArgumentException.class,
        () -> JHex.decodeChecked(invalid.toCharArray())).getMessage());
    assertEquals(expectedMsg, assertThrows(IllegalArgumentException.class,
        () -> JHex.decodeChecked(invalid.toCharArray(), new byte[2], 0)).getMessage());

    assertEquals(expectedMsg, assertThrows(IllegalArgumentException.class,
        () -> JHex.decodeChecked(invalid.getBytes(UTF_8))).getMessage());
    assertEquals(expectedMsg, assertThrows(IllegalArgumentException.class,
        () -> JHex.decodeChecked(invalid.getBytes(UTF_8), new byte[2], 0)).getMessage());

    assertEquals(expectedMsg, assertThrows(IllegalArgumentException.class,
        () -> JHex.decodeChecked(ByteBuffer.wrap(invalid.getBytes(UTF_8)))).getMessage());
    assertEquals(expectedMsg, assertThrows(IllegalArgumentException.class,
        () -> JHex.decodeChecked(ByteBuffer.wrap(invalid.getBytes(UTF_8)), new byte[2], 0))
        .getMessage());

    assertEquals(expectedMsg, assertThrows(IllegalArgumentException.class,
        () -> JHex.decodePrimIterChecked(invalid)).getMessage());
    assertEquals(expectedMsg, assertThrows(IllegalArgumentException.class,
        () -> JHex.decodePrimIterChecked(invalid, new byte[2], 0)).getMessage());
  }

  @Test
  public void decodePrimIterCheckedEncodeLower() {
    final byte[] lower = JHex.decodePrimIterChecked(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  static byte[] copyReverse(final byte[] data) {
    return copyReverse(data, 0, data.length);
  }

  static byte[] copyReverse(final byte[] data, int offset, final int len) {
    offset += len;
    final byte[] bytes = new byte[len];
    for (int i = 0;i < len;) {
      bytes[i++] = data[--offset];
    }
    return bytes;
  }

  @Test
  public void testLengthUtil() {
    assertTrue(JHex.isLengthValid("42"));
    assertTrue(JHex.isLengthValid(""));
    assertTrue(JHex.isLengthValid("!!"));

    assertFalse(JHex.isLengthValid("421"));
    assertFalse(JHex.isLengthValid(null));
  }

  @Test
  public void testIsValidUtil() {
    assertTrue(JHex.isValid("42"));
    assertTrue(JHex.isValid("4242"));
    assertTrue(JHex.isValid(""));

    assertFalse(JHex.isValid("!!"));
    assertFalse(JHex.isValid("4!"));
    assertFalse(JHex.isValid("!2"));
    assertFalse(JHex.isValid("421"));
    assertFalse(JHex.isValid("42!"));

    assertFalse(JHex.isValid("qq"));
    assertFalse(JHex.isValid("4q"));
    assertFalse(JHex.isValid("q2"));
    assertFalse(JHex.isValid("42q"));
    assertFalse(JHex.isValid(null));
  }

  @Test
  public void testConstructorIsPrivate() {
    testPrivateCtor(JHex.class);
  }

  static void testPrivateCtor(final Class<?> clas) {
    try {
      Constructor<?> constructor = clas.getDeclaredConstructor();
      assertTrue(Modifier.isPrivate(constructor.getModifiers()));
      constructor.setAccessible(true);
      constructor.newInstance();
    } catch (NoSuchMethodException | IllegalAccessException
        | InvocationTargetException | InstantiationException e) {
      throw new RuntimeException(e);
    }
  }
}
