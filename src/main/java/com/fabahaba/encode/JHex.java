package com.fabahaba.encode;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.PrimitiveIterator;

public final class JHex {

  private JHex() {}

  private static final int INVALID = -1;

  private static final char[] LOWER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      'a', 'b', 'c', 'd', 'e', 'f'};

  private static final char[] UPPER = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      'A', 'B', 'C', 'D', 'E', 'F'};

  static final byte[] LOWER_BYTES = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      'a', 'b', 'c', 'd', 'e', 'f'};

  static final byte[] UPPER_BYTES = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
      'A', 'B', 'C', 'D', 'E', 'F'};

  static final int[] DIGITS = new int[103];

  static {
    Arrays.fill(DIGITS, INVALID);
    for (final char c : LOWER) {
      DIGITS[c] = Character.digit(c, 16);
    }
    for (int i = 10;i < UPPER.length;++i) {
      final char c = UPPER[i];
      DIGITS[c] = Character.digit(c, 16);
    }
  }

  private static RuntimeException createIllegalLengthException(final int len) {
    return new IllegalArgumentException(String.format("Invalid hex encoding length of %d.", len));
  }

  private static RuntimeException createIllegalCharException(final byte chr, final int pos) {
    return createIllegalCharException((char) chr, pos);
  }

  private static RuntimeException createIllegalCharException(final char chr, final int pos) {
    return new IllegalArgumentException(String
        .format("Invalid character '%c' for hex encoding at position %d.", chr, pos));
  }

  public static String encode(final byte[] data) {
    return new String(encode(data, LOWER));
  }

  public static String encodeUpper(final byte[] data) {
    return new String(encode(data, UPPER));
  }

  public static char[] encodeChars(final byte[] data) {
    return encode(data, LOWER);
  }

  public static char[] encodeUpperChars(final byte[] data) {
    return encode(data, UPPER);
  }

  private static char[] encode(final byte[] data, final char[] alpha) {
    final int len = data.length;
    final char[] hex = new char[len << 1];
    for (int i = 0, h = 0, d;i < len;) {
      d = data[i++] & 0xff;
      hex[h++] = alpha[d >>> 4];
      hex[h++] = alpha[d & 0xf];
    }
    return hex;
  }

  public static byte[] encodeBytes(final byte[] data) {
    return encodeBytes(data, LOWER_BYTES);
  }

  public static byte[] encodeUpperBytes(final byte[] data) {
    return encodeBytes(data, UPPER_BYTES);
  }

  static byte[] encodeBytes(final byte[] data, final byte[] alpha) {
    final int len = data.length;
    final byte[] hex = new byte[len << 1];
    for (int i = 0, h = 0, d;i < len;) {
      d = data[i++] & 0xff;
      hex[h++] = alpha[d >>> 4];
      hex[h++] = alpha[d & 0xf];
    }
    return hex;
  }

  public static String encode(final ByteBuffer data) {
    return new String(encode(data, LOWER));
  }

  public static String encodeUpper(final ByteBuffer data) {
    return new String(encode(data, UPPER));
  }

  public static char[] encodeChars(final ByteBuffer data) {
    return encode(data, LOWER);
  }

  public static char[] encodeUpperChars(final ByteBuffer data) {
    return encode(data, UPPER);
  }

  private static char[] encode(final ByteBuffer data, final char[] alpha) {
    final int len = data.limit();
    final char[] hex = new char[len << 1];
    for (int h = 0, d;h < hex.length;) {
      d = data.get() & 0xff;
      hex[h++] = alpha[d >>> 4];
      hex[h++] = alpha[d & 0xf];
    }
    return hex;
  }

  public static byte[] encodeBytes(final ByteBuffer data) {
    return encodeBytes(data, LOWER_BYTES);
  }

  public static byte[] encodeUpperBytes(final ByteBuffer data) {
    return encodeBytes(data, UPPER_BYTES);
  }

  static byte[] encodeBytes(final ByteBuffer data, final byte[] alpha) {
    final int len = data.limit();
    final byte[] hex = new byte[len << 1];
    for (int h = 0, d;h < hex.length;) {
      d = data.get() & 0xff;
      hex[h++] = alpha[d >>> 4];
      hex[h++] = alpha[d & 0xf];
    }
    return hex;
  }

  public static String encode(final byte[] data, int offset, final int len) {
    return new String(encode(data, offset, len, LOWER));
  }

  public static String encodeUpper(final byte[] data, int offset, final int len) {
    return new String(encode(data, offset, len, UPPER));
  }

  public static char[] encodeChars(final byte[] data, int offset, final int len) {
    return encode(data, offset, len, LOWER);
  }

  public static char[] encodeUpperChars(final byte[] data, int offset, final int len) {
    return encode(data, offset, len, UPPER);
  }

  private static char[] encode(final byte[] data, int offset, final int len, final char[] alpha) {
    if (len == 0) {
      return new char[0];
    }
    final char[] hex = new char[len << 1];
    for (int i = 0, d;;++offset) {
      d = data[offset] & 0xff;
      hex[i++] = alpha[d >>> 4];
      hex[i++] = alpha[d & 0xf];
      if (i == hex.length) {
        return hex;
      }
    }
  }

  public static String encode(final ByteBuffer data, final int len) {
    return new String(encode(data, len, LOWER));
  }

  public static String encodeUpper(final ByteBuffer data, final int len) {
    return new String(encode(data, len, UPPER));
  }

  public static char[] encodeChars(final ByteBuffer data, final int len) {
    return encode(data, len, LOWER);
  }

  public static char[] encodeUpperChars(final ByteBuffer data, final int len) {
    return encode(data, len, UPPER);
  }

  private static char[] encode(final ByteBuffer data, final int len, final char[] alpha) {
    final char[] hex = new char[len << 1];
    for (int i = 0, d;i < hex.length;) {
      d = data.get() & 0xff;
      hex[i++] = alpha[d >>> 4];
      hex[i++] = alpha[d & 0xf];
    }
    return hex;
  }

  public static byte[] encodeBytes(final ByteBuffer data, final int len) {
    return encodeBytes(data, len, LOWER_BYTES);
  }

  public static byte[] encodeUpperBytes(final ByteBuffer data, final int len) {
    return encodeBytes(data, len, UPPER_BYTES);
  }

  private static byte[] encodeBytes(final ByteBuffer data, final int len, final byte[] alpha) {
    final byte[] hex = new byte[len << 1];
    for (int i = 0, d;i < hex.length;) {
      d = data.get() & 0xff;
      hex[i++] = alpha[d >>> 4];
      hex[i++] = alpha[d & 0xf];
    }
    return hex;
  }

  public static byte[] encodeBytes(final byte[] data, int offset, final int len) {
    return encodeBytes(data, offset, len, LOWER_BYTES);
  }

  public static byte[] encodeUpperBytes(final byte[] data, int offset, final int len) {
    return encodeBytes(data, offset, len, UPPER_BYTES);
  }

  static byte[] encodeBytes(final byte[] data, int offset, final int len, final byte[] alpha) {
    if (len == 0) {
      return new byte[0];
    }
    final byte[] hex = new byte[len << 1];
    for (int i = 0, d;;++offset) {
      d = data[offset] & 0xff;
      hex[i++] = alpha[d >>> 4];
      hex[i++] = alpha[d & 0xf];
      if (i == hex.length) {
        return hex;
      }
    }
  }

  public static String encodeReverse(final byte[] data, int offset, final int len) {
    return new String(encodeReverse(data, offset, len, LOWER));
  }

  public static String encodeUpperReverse(final byte[] data, int offset, final int len) {
    return new String(encodeReverse(data, offset, len, UPPER));
  }

  public static char[] encodeReverseChars(final byte[] data, int offset, final int len) {
    return encodeReverse(data, offset, len, LOWER);
  }

  public static char[] encodeUpperReverseChars(final byte[] data, int offset, final int len) {
    return encodeReverse(data, offset, len, UPPER);
  }

  private static char[] encodeReverse(final byte[] data, int offset, final int len,
      final char[] alpha) {
    if (len == 0) {
      return new char[0];
    }
    final char[] hex = new char[len << 1];
    for (int i = 0, d;;--offset) {
      d = data[offset] & 0xff;
      hex[i++] = alpha[d >>> 4];
      hex[i++] = alpha[d & 0xf];
      if (i == hex.length) {
        return hex;
      }
    }
  }

  public static String encodeReverse(final ByteBuffer data, int offset, final int len) {
    return new String(encodeReverse(data, offset, len, LOWER));
  }

  public static String encodeUpperReverse(final ByteBuffer data, int offset, final int len) {
    return new String(encodeReverse(data, offset, len, UPPER));
  }

  public static char[] encodeReverseChars(final ByteBuffer data, int offset, final int len) {
    return encodeReverse(data, offset, len, LOWER);
  }

  public static char[] encodeUpperReverseChars(final ByteBuffer data, int offset, final int len) {
    return encodeReverse(data, offset, len, UPPER);
  }

  private static char[] encodeReverse(final ByteBuffer data, int offset, final int len,
      final char[] alpha) {
    if (len == 0) {
      return new char[0];
    }
    final char[] hex = new char[len << 1];
    for (int i = 0, d;;--offset) {
      d = data.get(offset) & 0xff;
      hex[i++] = alpha[d >>> 4];
      hex[i++] = alpha[d & 0xf];
      if (i == hex.length) {
        return hex;
      }
    }
  }

  public static byte[] encodeReverseBytes(final byte[] data, int offset, final int len) {
    return encodeReverseBytes(data, offset, len, LOWER_BYTES);
  }

  public static byte[] encodeUpperReverseBytes(final byte[] data, int offset, final int len) {
    return encodeReverseBytes(data, offset, len, UPPER_BYTES);
  }

  static byte[] encodeReverseBytes(final byte[] data, int offset, final int len,
      final byte[] alpha) {
    if (len == 0) {
      return new byte[0];
    }
    final byte[] hex = new byte[len << 1];
    for (int i = 0, d;;--offset) {
      d = data[offset] & 0xff;
      hex[i++] = alpha[d >>> 4];
      hex[i++] = alpha[d & 0xf];
      if (i == hex.length) {
        return hex;
      }
    }
  }

  public static byte[] encodeReverseBytes(final ByteBuffer data, int offset, final int len) {
    return encodeReverseBytes(data, offset, len, LOWER_BYTES);
  }

  public static byte[] encodeUpperReverseBytes(final ByteBuffer data, int offset, final int len) {
    return encodeReverseBytes(data, offset, len, UPPER_BYTES);
  }

  static byte[] encodeReverseBytes(final ByteBuffer data, int offset, final int len,
      final byte[] alpha) {
    if (len == 0) {
      return new byte[0];
    }
    final byte[] hex = new byte[len << 1];
    for (int i = 0, d;;--offset) {
      d = data.get(offset) & 0xff;
      hex[i++] = alpha[d >>> 4];
      hex[i++] = alpha[d & 0xf];
      if (i == hex.length) {
        return hex;
      }
    }
  }

  public static boolean isValid(final String hex) {
    if (hex == null) {
      return false;
    }
    final int len = hex.length();
    if ((len & 1) != 0) {
      return false;
    }
    if (len == 0) {
      return true;
    }
    final char[] chars = hex.toCharArray();
    int index = 0;
    do {
      char chr = chars[index++];
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        return false;
      }
      chr = chars[index++];
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        return false;
      }
    } while (index < len);
    return true;
  }

  public static boolean isLengthValid(final String hex) {
    return hex != null && (hex.length() & 1) == 0;
  }

  public static byte[] decode(final CharSequence chars) {
    final byte[] data = new byte[chars.length() >> 1];
    if (data.length == 0) {
      return data;
    }
    for (int i = 0, c = 0;;++c) {
      data[i++] = (byte) (DIGITS[chars.charAt(c)] << 4 | DIGITS[chars.charAt(++c)]);
      if (i == data.length) {
        return data;
      }
    }
  }

  public static byte[] decode(final String hex) {
    return decode(hex.toCharArray());
  }

  public static byte[] decode(final char[] chars) {
    final byte[] data = new byte[chars.length >> 1];
    if (data.length == 0) {
      return data;
    }
    for (int i = 0, c = 0;;++c) {
      data[i++] = (byte) (DIGITS[chars[c]] << 4 | DIGITS[chars[++c]]);
      if (i == data.length) {
        return data;
      }
    }
  }

  public static byte[] decode(final byte[] chars) {
    final byte[] data = new byte[chars.length >> 1];
    if (data.length == 0) {
      return data;
    }
    for (int i = 0, c = 0;;++c) {
      data[i++] = (byte) (DIGITS[chars[c]] << 4 | DIGITS[chars[++c]]);
      if (i == data.length) {
        return data;
      }
    }
  }

  public static byte[] decode(final ByteBuffer chars) {
    final byte[] data = new byte[chars.limit() >> 1];
    if (data.length == 0) {
      return data;
    }
    int index = 0;
    do {
      data[index++] = (byte) (DIGITS[chars.get()] << 4 | DIGITS[chars.get()]);
    } while (index < data.length);
    return data;
  }

  public static byte[] decodeChecked(final CharSequence chars) {
    final int len = chars.length();
    if (len == 0) {
      return new byte[0];
    }
    if ((len & 1) != 0) {
      throw createIllegalLengthException(len);
    }
    final byte[] data = new byte[len >> 1];
    for (int i = 0, c = 0;;++c) {
      char chr = chars.charAt(c);
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      int bite = DIGITS[chr] << 4;
      chr = chars.charAt(++c);
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      data[i++] = (byte) (bite | DIGITS[chr]);
      if (i == data.length) {
        return data;
      }
    }
  }

  public static byte[] decodeChecked(final String hex) {
    return decodeChecked(hex.toCharArray());
  }

  public static byte[] decodeChecked(final char[] chars) {
    if (chars.length == 0) {
      return new byte[0];
    }
    if ((chars.length & 1) != 0) {
      throw createIllegalLengthException(chars.length);
    }
    final byte[] data = new byte[chars.length >> 1];
    for (int i = 0, c = 0;;++c) {
      char chr = chars[c];
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      int bite = DIGITS[chr] << 4;
      chr = chars[++c];
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      data[i++] = (byte) (bite | DIGITS[chr]);
      if (i == data.length) {
        return data;
      }
    }
  }

  public static byte[] decodeChecked(final byte[] chars) {
    if (chars.length == 0) {
      return new byte[0];
    }
    if ((chars.length & 1) != 0) {
      throw createIllegalLengthException(chars.length);
    }
    final byte[] data = new byte[chars.length >> 1];
    for (int i = 0, c = 0;;++c) {
      byte chr = chars[c];
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      int bite = DIGITS[chr] << 4;
      chr = chars[++c];
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      data[i++] = (byte) (bite | DIGITS[chr]);
      if (i == data.length) {
        return data;
      }
    }
  }

  public static byte[] decodeChecked(final ByteBuffer chars) {
    final int len = chars.limit();
    if (len == 0) {
      return new byte[0];
    }
    if ((len & 1) != 0) {
      throw createIllegalLengthException(len);
    }
    final byte[] data = new byte[len >> 1];
    for (int i = 0;;) {
      byte chr = chars.get();
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, i * 2);
      }
      int bite = DIGITS[chr] << 4;
      chr = chars.get();
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, (i * 2) + 1);
      }
      data[i++] = (byte) (bite | DIGITS[chr]);
      if (i == data.length) {
        return data;
      }
    }
  }

  public static void decode(final CharSequence chars, final byte[] out, int offset) {
    for (int c = 0, len = chars.length();c < len;) {
      out[offset++] = (byte) (DIGITS[chars.charAt(c++)] << 4 | DIGITS[chars.charAt(c++)]);
    }
  }

  public static void decode(final String hex, final byte[] out, int offset) {
    decode(hex.toCharArray(), out, offset);
  }

  public static void decode(final char[] chars, final byte[] out, int offset) {
    for (int c = 0;c < chars.length;) {
      out[offset++] = (byte) (DIGITS[chars[c++]] << 4 | DIGITS[chars[c++]]);
    }
  }

  public static void decodeChecked(final CharSequence chars, final byte[] out, int offset) {
    final int len = chars.length();
    if (len == 0) {
      return;
    }
    if ((len & 1) != 0) {
      throw createIllegalLengthException(len);
    }
    for (int c = 0;;++offset) {
      char chr = chars.charAt(c);
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      int bite = DIGITS[chr] << 4;
      chr = chars.charAt(++c);
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      out[offset] = (byte) (bite | DIGITS[chr]);
      if (++c == len) {
        return;
      }
    }
  }

  public static void decodeChecked(final String hex, final byte[] out, int offset) {
    decodeChecked(hex.toCharArray(), out, offset);
  }

  public static void decodeChecked(final char[] chars, final byte[] out, int offset) {
    if (chars.length == 0) {
      return;
    }
    if ((chars.length & 1) != 0) {
      throw createIllegalLengthException(chars.length);
    }
    for (int c = 0;;++offset) {
      char chr = chars[c];
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      int bite = DIGITS[chr] << 4;
      chr = chars[++c];
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      out[offset] = (byte) (bite | DIGITS[chr]);
      if (++c == chars.length) {
        return;
      }
    }
  }

  public static void decodeChecked(final byte[] chars, final byte[] out, int offset) {
    if (chars.length == 0) {
      return;
    }
    if ((chars.length & 1) != 0) {
      throw createIllegalLengthException(chars.length);
    }
    for (int c = 0;;++offset) {
      byte chr = chars[c];
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      int bite = DIGITS[chr] << 4;
      chr = chars[++c];
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      out[offset] = (byte) (bite | DIGITS[chr]);
      if (++c == chars.length) {
        return;
      }
    }
  }

  public static void decodeChecked(final ByteBuffer buffer, final byte[] out, int offset) {
    final int len = buffer.limit();
    if (len == 0) {
      return;
    }
    if ((len & 1) != 0) {
      throw createIllegalLengthException(len);
    }
    for (int c = 0;;++offset) {
      byte chr = buffer.get();
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, c);
      }
      int bite = DIGITS[chr] << 4;
      chr = buffer.get();
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException(chr, ++c);
      }
      out[offset] = (byte) (bite | DIGITS[chr]);
      c += 2;
      if (c == len) {
        return;
      }
    }
  }

  public static byte[] decodePrimIter(final CharSequence hex) {
    final byte[] data = new byte[hex.length() >> 1];
    if (data.length == 0) {
      return data;
    }
    final PrimitiveIterator.OfInt chars = hex.chars().iterator();
    int index = 0;
    do {
      data[index++] = (byte) (DIGITS[chars.nextInt()] << 4 | DIGITS[chars.nextInt()]);
    } while (index < data.length);
    return data;
  }

  public static byte[] decodePrimIterChecked(final CharSequence hex) {
    final int len = hex.length();
    if (len == 0) {
      return new byte[0];
    }
    if ((len & 1) != 0) {
      throw createIllegalLengthException(len);
    }
    final byte[] data = new byte[len >> 1];
    final PrimitiveIterator.OfInt chars = hex.chars().iterator();
    for (int index = 0;index < data.length;) {
      int chr = chars.nextInt();
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException((char) chr, index * 2);
      }
      int bite = DIGITS[chr] << 4;
      chr = chars.nextInt();
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException((char) chr, (index * 2) + 1);
      }
      data[index++] = (byte) (bite | DIGITS[chr]);
    }
    return data;
  }

  public static void decodePrimIter(final CharSequence hex, final byte[] out, int offset) {
    final PrimitiveIterator.OfInt chars = hex.chars().iterator();
    final int max = offset + (hex.length() >> 1);
    while (offset < max) {
      out[offset++] = (byte) (DIGITS[chars.nextInt()] << 4 | DIGITS[chars.nextInt()]);
    }
  }

  public static void decodePrimIterChecked(final CharSequence hex, final byte[] out, int offset) {
    final int len = hex.length();
    if (len == 0) {
      return;
    }
    if ((len & 1) != 0) {
      throw createIllegalLengthException(len);
    }
    final PrimitiveIterator.OfInt chars = hex.chars().iterator();
    for (int index = 0;chars.hasNext();index += 2) {
      int chr = chars.nextInt();
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException((char) chr, index);
      }
      int bite = DIGITS[chr] << 4;
      chr = chars.nextInt();
      if (chr >= DIGITS.length || DIGITS[chr] == INVALID) {
        throw createIllegalCharException((char) chr, index + 1);
      }
      out[offset++] = (byte) (bite | DIGITS[chr]);
    }
  }
}
