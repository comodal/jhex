package systems.comodal.encode;

import static systems.comodal.encode.HexEncodeTest.copyReverse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AltHexEncodeTest {

  @Test
  public void decodeAltEncodeLower() {
    final byte[] lower = JHex.decode(HexEncodeTest.TEST_HEX);
    Assertions.assertEquals(HexEncodeTest.TEST_HEX, JHexAlt.encode(lower));
  }

  @Test
  public void decodeAltEncodeUpperOffset() {
    final byte[] lower = JHex.decode(HexEncodeTest.TEST_HEX);
    Assertions.assertEquals(HexEncodeTest.TEST_HEX.toUpperCase(Locale.ENGLISH),
        JHexAlt.encodeUpper(lower, 0, lower.length));
  }

  @Test
  public void decodeAltEncodeLowerOffset() {
    final byte[] lower = JHex.decode(HexEncodeTest.TEST_HEX);
    Assertions.assertEquals(HexEncodeTest.TEST_HEX, JHexAlt.encode(lower, 0, lower.length));
  }

  @Test
  public void altEncodeReverse() {
    final byte[] lower = JHex.decode(HexEncodeTest.TEST_HEX);
    final byte[] reverse = HexEncodeTest.copyReverse(lower);
    Assertions.assertEquals(HexEncodeTest.TEST_HEX, JHexAlt.encodeReverse(reverse, 31, 32));
  }

  @Test
  public void altEncodeUpperReverseOffset() {
    final byte[] lower = JHex.decode(HexEncodeTest.TEST_HEX);
    final byte[] reverse = HexEncodeTest.copyReverse(lower);
    Assertions.assertEquals(HexEncodeTest.TEST_HEX.toUpperCase(Locale.ENGLISH),
        JHexAlt.encodeUpperReverse(reverse, 31, 32));
  }

  @Test
  public void decodeAltEncodeUpper() {
    final byte[] lower = JHex.decode(HexEncodeTest.TEST_HEX);
    final String upperHex = HexEncodeTest.TEST_HEX.toUpperCase(Locale.ENGLISH);
    assertEquals(upperHex, JHexAlt.encodeUpper(lower));
    final byte[] upper = JHex.decode(upperHex);
    Assertions.assertEquals(HexEncodeTest.TEST_HEX, JHexAlt.encode(upper));
    assertEquals(upperHex, JHexAlt.encodeUpper(upper));
  }

  @Test
  public void testConstructorIsPrivate() {
    HexEncodeTest.testPrivateCtor(JHexAlt.class);
  }
}
