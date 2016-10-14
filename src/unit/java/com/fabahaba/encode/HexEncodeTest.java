package com.fabahaba.encode;

import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class HexEncodeTest {

  static final String TEST_HEX =
      "b3bd615f25765d5b5c43058f2a7a44916131b506ce9952d59ad2a5601899f8f9";

  @Test
  public void decodeEncodeLower() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeCheckedEncodeLower() {
    final byte[] lower = JHex.decodeChecked(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeOffsetEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decode(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeOffsetCheckedEncodeLower() {
    final byte[] lower = new byte[TEST_HEX.length() >> 1];
    JHex.decodeChecked(TEST_HEX, lower, 0);
    assertEquals(TEST_HEX, JHex.encode(lower));
  }

  @Test
  public void decodeEncodeLowerOffset() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_HEX, JHex.encode(lower, 0, lower.length));
  }

  @Test
  public void encodeReverse() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertEquals(TEST_HEX, JHex.encodeReverse(reverse, 31, 32));
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
}
