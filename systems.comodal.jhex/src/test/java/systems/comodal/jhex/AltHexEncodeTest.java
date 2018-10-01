package systems.comodal.jhex;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class AltHexEncodeTest {

  @Test
  void decodeAltEncodeLower() {
    final byte[] lower = JHex.decode(HexEncodeTest.TEST_HEX);
    assertEquals(HexEncodeTest.TEST_HEX, JHexAlt.encode(lower));
  }

  @Test
  void decodeAltEncodeUpperOffset() {
    final byte[] lower = JHex.decode(HexEncodeTest.TEST_HEX);
    assertEquals(HexEncodeTest.TEST_HEX.toUpperCase(Locale.ENGLISH),
        JHexAlt.encodeUpper(lower, 0, lower.length));
  }

  @Test
  void decodeAltEncodeLowerOffset() {
    final byte[] lower = JHex.decode(HexEncodeTest.TEST_HEX);
    assertEquals(HexEncodeTest.TEST_HEX, JHexAlt.encode(lower, 0, lower.length));
  }

  @Test
  void altEncodeReverse() {
    final byte[] lower = JHex.decode(HexEncodeTest.TEST_HEX);
    final byte[] reverse = HexEncodeTest.copyReverse(lower);
    assertEquals(HexEncodeTest.TEST_HEX, JHexAlt.encodeReverse(reverse, 31, 32));
  }

  @Test
  void altEncodeUpperReverseOffset() {
    final byte[] lower = JHex.decode(HexEncodeTest.TEST_HEX);
    final byte[] reverse = HexEncodeTest.copyReverse(lower);
    assertEquals(HexEncodeTest.TEST_HEX.toUpperCase(Locale.ENGLISH),
        JHexAlt.encodeUpperReverse(reverse, 31, 32));
  }

  @Test
  void decodeAltEncodeUpper() {
    final byte[] lower = JHex.decode(HexEncodeTest.TEST_HEX);
    final String upperHex = HexEncodeTest.TEST_HEX.toUpperCase(Locale.ENGLISH);
    assertEquals(upperHex, JHexAlt.encodeUpper(lower));
    final byte[] upper = JHex.decode(upperHex);
    assertEquals(HexEncodeTest.TEST_HEX, JHexAlt.encode(upper));
    assertEquals(upperHex, JHexAlt.encodeUpper(upper));
  }

  @Test
  void testConstructorIsPrivate() {
    HexEncodeTest.testPrivateCtor(JHexAlt.class);
  }
}
