package systems.comodal.jhex;

final class JHexAlt {

  private JHexAlt() {
  }

  static String encode(final byte[] data) {
    return new String(JHex.encodeBytes(data, JHex.LOWER_BYTES));
  }

  static String encodeUpper(final byte[] data) {
    return new String(JHex.encodeBytes(data, JHex.UPPER_BYTES));
  }

  static String encode(final byte[] data, int offset, final int len) {
    return new String(JHex.encodeBytes(data, offset, len, JHex.LOWER_BYTES));
  }

  static String encodeUpper(final byte[] data, int offset, final int len) {
    return new String(JHex.encodeBytes(data, offset, len, JHex.UPPER_BYTES));
  }

  static String encodeReverse(final byte[] data, int offset, final int len) {
    return new String(JHex.encodeReverseBytes(data, offset, len, JHex.LOWER_BYTES));
  }

  static String encodeUpperReverse(final byte[] data, int offset, final int len) {
    return new String(JHex.encodeReverseBytes(data, offset, len, JHex.UPPER_BYTES));
  }
}
