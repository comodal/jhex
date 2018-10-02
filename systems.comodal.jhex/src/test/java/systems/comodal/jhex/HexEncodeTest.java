package systems.comodal.jhex;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Locale;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

final class HexEncodeTest {

  static final String TEST_HEX = "4a5e1e4baab89f3a32518a88c31bc87f618f76673e2cc77ab2127b7afdeda33b";
  private static final byte[] TEST_HEX_BYTES = TEST_HEX.getBytes(US_ASCII);
  private static final char[] TEST_HEX_CHARS = TEST_HEX.toCharArray();

  private static final String TEST_UPPER_HEX = TEST_HEX.toUpperCase(Locale.ENGLISH);
  private static final byte[] TEST_UPPER_HEX_BYTES = TEST_UPPER_HEX.getBytes(US_ASCII);
  private static final char[] TEST_UPPER_HEX_CHARS = TEST_UPPER_HEX.toCharArray();

  static byte[] copyReverse(final byte[] data) {
    return copyReverse(data, 0, data.length);
  }

  static byte[] copyReverse(final byte[] data, int offset, final int len) {
    offset += len;
    final byte[] bytes = new byte[len];
    for (int i = 0; i < len; ) {
      bytes[i++] = data[--offset];
    }
    return bytes;
  }

  static void testPrivateCtor(final Class<?> clas) {
    try {
      final var constructor = clas.getDeclaredConstructor();
      assertTrue(Modifier.isPrivate(constructor.getModifiers()));
      constructor.setAccessible(true);
      constructor.newInstance();
    } catch (NoSuchMethodException | IllegalAccessException
        | InvocationTargetException | InstantiationException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void decodeEncodeLower() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeCharSequenceEncodeLower() {
    final byte[] lower = JHex.decodeToCharArray(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeBytesEncodeLower() {
    final byte[] lower = JHex.decode(TEST_HEX_BYTES);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeByteBufferEncodeLower() {
    final byte[] lower = JHex.decode(ByteBuffer.wrap(TEST_HEX_BYTES));
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeCheckedEncodeLower() {
    final byte[] lower = JHex.decodeChecked(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeCheckedCharSequenceEncodeLower() {
    final byte[] lower = JHex.decodeCheckedToCharArray(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeCheckedBytesEncodeLower() {
    final byte[] lower = JHex.decodeChecked(TEST_HEX_BYTES);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeCheckedByteBufferEncodeLower() {
    final byte[] lower = JHex
        .decodeChecked(ByteBuffer.wrap(TEST_HEX_BYTES));
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeOffsetEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decode(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeOffsetCharSequenceEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodeToCharArray(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeOffsetCheckedEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodeChecked(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeOffsetCheckedCharSequenceEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodeCheckedToCharArray(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeOffsetCheckedBytesEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodeChecked(TEST_HEX_BYTES, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeOffsetCheckedByteBufferEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodeChecked(ByteBuffer.wrap(TEST_HEX_BYTES), lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodeEncodeLowerOffset() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower, 0, lower.length));
  }

  @Test
  void decodeEncodeUpperOffset() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_UPPER_HEX, JHex.encodeUpper(lower, 0, lower.length));
  }

  @Test
  void decodeEncodeLowerOffsetChars() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_HEX_CHARS, JHex.encodeChars(lower, 0, lower.length));

    final char[] out = new char[TEST_HEX_CHARS.length << 1];
    JHex.encodeChars(lower, 0, lower.length, out, TEST_HEX_CHARS.length);
    assertTrue(Arrays.equals(TEST_HEX_CHARS, 0, TEST_HEX_CHARS.length,
        out, TEST_HEX_CHARS.length, out.length));
  }

  @Test
  void decodeEncodeOffsetUpperChars() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_UPPER_HEX_CHARS, JHex.encodeUpperChars(lower, 0, lower.length));

    final char[] out = new char[TEST_UPPER_HEX_CHARS.length << 1];
    JHex.encodeUpperChars(lower, 0, lower.length, out, TEST_UPPER_HEX_CHARS.length);
    assertTrue(Arrays.equals(TEST_UPPER_HEX_CHARS, 0, TEST_UPPER_HEX_CHARS.length,
        out, TEST_UPPER_HEX_CHARS.length, out.length));
  }

  @Test
  void decodeEncodeLowerOffsetBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_HEX_BYTES, JHex.encodeBytes(lower, 0, lower.length));

    final byte[] out = new byte[TEST_HEX_BYTES.length << 1];
    JHex.encodeBytes(lower, 0, lower.length, out, TEST_HEX_BYTES.length);
    assertTrue(Arrays.equals(TEST_HEX_BYTES, 0, TEST_HEX_BYTES.length,
        out, TEST_HEX_BYTES.length, out.length));
  }

  @Test
  void decodeEncodeOffsetUpperBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_UPPER_HEX_BYTES, JHex.encodeUpperBytes(lower, 0, lower.length));

    final byte[] out = new byte[TEST_UPPER_HEX_BYTES.length << 1];
    JHex.encodeUpperBytes(lower, 0, lower.length, out, TEST_UPPER_HEX_BYTES.length);
    assertTrue(Arrays.equals(TEST_UPPER_HEX_BYTES, 0, TEST_UPPER_HEX_BYTES.length,
        out, TEST_UPPER_HEX_BYTES.length, out.length));
  }

  @Test
  void decodeEncodeLowerByteBufferFixedLength() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(ByteBuffer.wrap(lower), lower.length));
  }

  @Test
  void decodeEncodeUpperByteBufferFixedLength() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_UPPER_HEX, JHex.encodeUpper(ByteBuffer.wrap(lower), lower.length));
  }

  @Test
  void decodeEncodeCharsLowerByteBufferFixedLength() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_HEX_CHARS, JHex.encodeChars(ByteBuffer.wrap(lower), lower.length));

    final char[] out = new char[TEST_HEX_CHARS.length << 1];
    JHex.encodeChars(ByteBuffer.wrap(lower), lower.length, out, TEST_HEX_CHARS.length);
    assertTrue(Arrays.equals(TEST_HEX_CHARS, 0, TEST_HEX_CHARS.length,
        out, TEST_HEX_CHARS.length, out.length));
  }

  @Test
  void decodeEncodeUpperCharsByteBufferFixedLength() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_UPPER_HEX_CHARS, JHex.encodeUpperChars(ByteBuffer.wrap(lower), lower.length));

    final char[] out = new char[TEST_UPPER_HEX_CHARS.length << 1];
    JHex.encodeUpperChars(ByteBuffer.wrap(lower), lower.length, out, TEST_UPPER_HEX_CHARS.length);
    assertTrue(Arrays.equals(TEST_UPPER_HEX_CHARS, 0, TEST_UPPER_HEX_CHARS.length,
        out, TEST_UPPER_HEX_CHARS.length, out.length));
  }

  @Test
  void decodeEncodeBytesLowerByteBufferFixedLength() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_HEX_BYTES, JHex.encodeBytes(ByteBuffer.wrap(lower), lower.length));

    final byte[] out = new byte[TEST_HEX_BYTES.length << 1];
    JHex.encodeBytes(ByteBuffer.wrap(lower), lower.length, out, TEST_HEX_BYTES.length);
    assertTrue(Arrays.equals(TEST_HEX_BYTES, 0, TEST_HEX_BYTES.length,
        out, TEST_HEX_BYTES.length, out.length));
  }

  @Test
  void decodeEncodeUpperBytesByteBufferFixedLength() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertArrayEquals(TEST_UPPER_HEX_BYTES, JHex.encodeUpperBytes(ByteBuffer.wrap(lower), lower.length));

    final byte[] out = new byte[TEST_UPPER_HEX_BYTES.length << 1];
    JHex.encodeUpperBytes(ByteBuffer.wrap(lower), lower.length, out, TEST_UPPER_HEX_BYTES.length);
    assertTrue(Arrays.equals(TEST_UPPER_HEX_BYTES, 0, TEST_UPPER_HEX_BYTES.length,
        out, TEST_UPPER_HEX_BYTES.length, out.length));
  }

  @Test
  void encodeReverse() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertEquals(TEST_HEX, JHex.encodeReverse(reverse, 31, 32));
  }

  @Test
  void encodeReverseChars() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_HEX_CHARS, JHex.encodeReverseChars(reverse, 31, 32));

    final char[] out = new char[TEST_HEX_CHARS.length << 1];
    JHex.encodeReverseChars(reverse, 31, 32, out, TEST_HEX_CHARS.length);
    assertTrue(Arrays.equals(TEST_HEX_CHARS, 0, TEST_HEX_CHARS.length,
        out, TEST_HEX_CHARS.length, out.length));
  }

  @Test
  void encodeReverseBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_HEX_BYTES, JHex.encodeReverseBytes(reverse, 31, 32));

    final byte[] out = new byte[TEST_HEX_BYTES.length << 1];
    JHex.encodeReverseBytes(reverse, 31, 32, out, TEST_HEX_BYTES.length);
    assertTrue(Arrays.equals(TEST_HEX_BYTES, 0, TEST_HEX_BYTES.length,
        out, TEST_HEX_BYTES.length, out.length));
  }

  @Test
  void encodeUpperReverseBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_UPPER_HEX_BYTES, JHex.encodeUpperReverseBytes(reverse, 31, 32));

    final byte[] out = new byte[TEST_UPPER_HEX_BYTES.length << 1];
    JHex.encodeUpperReverseBytes(reverse, 31, 32, out, TEST_UPPER_HEX_BYTES.length);
    assertTrue(Arrays.equals(TEST_UPPER_HEX_BYTES, 0, TEST_UPPER_HEX_BYTES.length,
        out, TEST_UPPER_HEX_BYTES.length, out.length));
  }

  @Test
  void encodeUpperReverse() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertEquals(TEST_UPPER_HEX, JHex.encodeUpperReverse(reverse, 31, 32));
  }

  @Test
  void encodeUpperReverseChars() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_UPPER_HEX_CHARS, JHex.encodeUpperReverseChars(reverse, 31, 32));

    final char[] out = new char[TEST_UPPER_HEX_CHARS.length << 1];
    JHex.encodeUpperReverseChars(reverse, 31, 32, out, TEST_UPPER_HEX_CHARS.length);
    assertTrue(Arrays.equals(TEST_UPPER_HEX_CHARS, 0, TEST_UPPER_HEX_CHARS.length,
        out, TEST_UPPER_HEX_CHARS.length, out.length));
  }

  @Test
  void encodeByteBufferReverse() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertEquals(TEST_HEX,
        JHex.encodeReverse(ByteBuffer.wrap(reverse), 31, 32));
  }

  @Test
  void encodeByteBufferUpperReverse() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertEquals(TEST_UPPER_HEX,
        JHex.encodeUpperReverse(ByteBuffer.wrap(reverse), 31, 32));
  }

  @Test
  void encodeByteBufferReverseChars() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_HEX_CHARS,
        JHex.encodeReverseChars(ByteBuffer.wrap(reverse), 31, 32));
  }

  @Test
  void encodeByteBufferUpperReverseChars() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_UPPER_HEX_CHARS,
        JHex.encodeUpperReverseChars(ByteBuffer.wrap(reverse), 31, 32));
  }

  @Test
  void encodeByteBufferReverseBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_HEX_BYTES,
        JHex.encodeReverseBytes(ByteBuffer.wrap(reverse), 31, 32));
  }

  @Test
  void encodeUpperByteBufferReverseBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertArrayEquals(TEST_UPPER_HEX_BYTES,
        JHex.encodeUpperReverseBytes(ByteBuffer.wrap(reverse), 31, 32));
  }

  @Test
  void decodeEncodeUpper() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final String upperHex = TEST_UPPER_HEX;
    assertEquals(upperHex, JHex.encodeUpper(lower));
    final byte[] upper = JHex.decode(upperHex);
    assertEquals(TEST_HEX, JHex.encode(upper));
    assertEquals(upperHex, JHex.encodeUpper(upper));
  }

  @Test
  void decodeEncodeUpperBytes() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] upperHex = TEST_UPPER_HEX_BYTES;
    assertArrayEquals(upperHex, JHex.encodeUpperBytes(lower));
    final byte[] upper = JHex.decode(upperHex);
    assertArrayEquals(upperHex, JHex.encodeUpperBytes(upper));
  }

  @Test
  void decodeEncodeUpperByteBuffer() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] upperHex = TEST_UPPER_HEX_BYTES;
    assertArrayEquals(upperHex, JHex.encodeUpperBytes(ByteBuffer.wrap(lower)));
    final byte[] upper = JHex.decode(upperHex);
    assertArrayEquals(upperHex, JHex.encodeUpperBytes(ByteBuffer.wrap(upper)));
  }

  @Test
  void decodePrimIterOffsetEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodePrimIter(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodePrimIterEncodeLower() {
    final byte[] lower = JHex.decodePrimIter(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void decodePrimIterEncodeReverse() {
    final byte[] lower = JHex.decodePrimIter(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertEquals(TEST_HEX, JHex.encodeReverse(reverse, 31, 32));
  }

  @Test
  void decodePrimIterEncodeUpper() {
    final byte[] lower = JHex.decodePrimIter(TEST_HEX);
    final String upperHex = TEST_UPPER_HEX;
    assertEquals(upperHex, JHex.encodeUpper(lower));
    final byte[] upper = JHex.decodePrimIter(upperHex);
    assertEquals(TEST_HEX, JHex.encode(upper));
    assertEquals(upperHex, JHex.encodeUpper(upper));
  }

  @Test
  void decodePrimIterCheckedOffsetEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodePrimIterChecked(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void encodeEmpty() {
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
  void decodeEmpty() {
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
  void decodeNull() {
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
  void decodeCheckedOddLength() {
    decodeCheckedInvalidEncodings("1", "Invalid hex encoding length of 1.");
  }

  @Test
  void decodeCheckedBadCharEven() {
    decodeCheckedInvalidEncodings("12!3",
        "Invalid character '!' for hex encoding at position 2.");
    decodeCheckedInvalidEncodings("12q3",
        "Invalid character 'q' for hex encoding at position 2.");
  }

  @Test
  void decodeCheckedBadCharOdd() {
    decodeCheckedInvalidEncodings("123!",
        "Invalid character '!' for hex encoding at position 3.");
    decodeCheckedInvalidEncodings("123q",
        "Invalid character 'q' for hex encoding at position 3.");
  }

  private void decodeCheckedInvalidEncodings(final String invalid, final String expectedMsg) {
    assertEquals(expectedMsg, assertThrows(IllegalArgumentException.class,
        () -> JHex.decodeChecked(invalid)).getMessage());
    assertEquals(expectedMsg, assertThrows(IllegalArgumentException.class,
        () -> JHex.decodeChecked(invalid, new byte[2], 0)).getMessage());

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
  void decodePrimIterCheckedEncodeLower() {
    final byte[] lower = JHex.decodePrimIterChecked(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  void testLengthUtil() {
    assertTrue(JHex.isLengthValid("42"));
    assertTrue(JHex.isLengthValid(""));
    assertTrue(JHex.isLengthValid("!!"));

    assertFalse(JHex.isLengthValid("421"));
    assertFalse(JHex.isLengthValid(null));
  }

  @Test
  void testIsValidUtil() {
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
  void testConstructorIsPrivate() {
    testPrivateCtor(JHex.class);
  }
}
