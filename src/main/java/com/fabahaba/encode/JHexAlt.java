package com.fabahaba.encode;

import java.util.PrimitiveIterator;

import static com.fabahaba.encode.JHex.DIGITS;
import static com.fabahaba.encode.JHex.LOWER_BYTES;
import static com.fabahaba.encode.JHex.UPPER_BYTES;

final class JHexAlt {

  private JHexAlt() {}

  static String encode(final byte[] data) {
    return new String(JHex.encode(data, LOWER_BYTES));
  }

  static String encodeUpper(final byte[] data) {
    return new String(JHex.encode(data, UPPER_BYTES));
  }

  static String encode(final byte[] data, int offset, final int len) {
    return new String(JHex.encode(data, offset, len, LOWER_BYTES));
  }

  static String encodeUpper(final byte[] data, int offset, final int len) {
    return new String(JHex.encode(data, offset, len, UPPER_BYTES));
  }

  static String encodeReverse(final byte[] data, int offset, final int len) {
    return new String(JHex.encodeReverse(data, offset, len, LOWER_BYTES));
  }

  static String encodeUpperReverse(final byte[] data, int offset, final int len) {
    return new String(JHex.encodeReverse(data, offset, len, UPPER_BYTES));
  }

  static byte[] decodeCharIter(final String hex) {
    final int len = hex.length();
    if (len == 0) {
      return new byte[0];
    }
    final byte[] data = new byte[len >> 1];
    final PrimitiveIterator.OfInt chars = hex.chars().iterator();
    int index = 0;
    do {
      data[index++] = (byte) (DIGITS[chars.nextInt()] << 4 | DIGITS[chars.nextInt()]);
    } while (index < data.length);
    return data;
  }

  static void decodeCharIter(final String hex, final byte[] data, int offset) {
    final int len = hex.length();
    if (len == 0) {
      return;
    }
    final PrimitiveIterator.OfInt chars = hex.chars().iterator();
    final int max = offset + (len >> 1);
    do {
      data[offset++] = (byte) (DIGITS[chars.nextInt()] << 4 | DIGITS[chars.nextInt()]);
    } while (offset < max);
  }

  static byte[] decodeCodePointIter(final String hex) {
    final int len = hex.length();
    if (len == 0) {
      return new byte[0];
    }
    final byte[] data = new byte[len >> 1];
    final PrimitiveIterator.OfInt chars = hex.codePoints().iterator();
    int index = 0;
    do {
      data[index++] = (byte) (DIGITS[chars.nextInt()] << 4 | DIGITS[chars.nextInt()]);
    } while (index < data.length);
    return data;
  }

  static void decodeCodePointIter(final String hex, final byte[] data, int offset) {
    final int len = hex.length();
    if (len == 0) {
      return;
    }
    final PrimitiveIterator.OfInt chars = hex.codePoints().iterator();
    final int max = offset + (len >> 1);
    do {
      data[offset++] = (byte) (DIGITS[chars.nextInt()] << 4 | DIGITS[chars.nextInt()]);
    } while (offset < max);
  }
}
