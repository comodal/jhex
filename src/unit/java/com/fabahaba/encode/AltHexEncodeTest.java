package com.fabahaba.encode;

import org.junit.Test;

import java.util.Locale;

import static com.fabahaba.encode.HexEncodeTest.TEST_HEX;
import static com.fabahaba.encode.HexEncodeTest.copyReverse;
import static org.junit.Assert.assertEquals;

public class AltHexEncodeTest {

  @Test
  public void decodeAltEncodeLower() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_HEX, JHexAlt.encode(lower));
  }

  @Test
  public void decodeAltEncodeLowerOffset() {
    final byte[] lower = JHex.decode(TEST_HEX);
    assertEquals(TEST_HEX, JHexAlt.encode(lower, 0, lower.length));
  }

  @Test
  public void altEncodeReverse() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final byte[] reverse = copyReverse(lower);
    assertEquals(TEST_HEX, JHexAlt.encodeReverse(reverse, 31, 32));
  }

  @Test
  public void decodeAltEncodeUpper() {
    final byte[] lower = JHex.decode(TEST_HEX);
    final String upperHex = TEST_HEX.toUpperCase(Locale.ENGLISH);
    assertEquals(upperHex, JHexAlt.encodeUpper(lower));
    final byte[] upper = JHex.decode(upperHex);
    assertEquals(TEST_HEX, JHexAlt.encode(upper));
    assertEquals(upperHex, JHexAlt.encodeUpper(upper));
  }
}
