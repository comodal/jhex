package com.fabahaba.encode;

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
}
