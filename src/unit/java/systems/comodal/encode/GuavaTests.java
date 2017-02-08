package systems.comodal.encode;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import systems.comodal.encode.JHex;

public class GuavaTests {

  private static void testEncodesWithOffset(final String decoded, final int offset, final int len,
      final String encoded) {
    assertEquals(encoded.toLowerCase(Locale.ENGLISH),
        JHex.encode(decoded.getBytes(UTF_8), offset, len));
    assertEquals(encoded.toUpperCase(Locale.ENGLISH),
        JHex.encodeUpper(decoded.getBytes(UTF_8), offset, len));
  }

  private static void testEncodingWithCasing(final String decoded, final String encoded) {
    testEncoding(JHex::encode, JHex::decode, decoded, encoded.toLowerCase(Locale.ENGLISH));
    testEncoding(JHex::encodeUpper, JHex::decode, decoded, encoded.toUpperCase(Locale.ENGLISH));
  }

  private static void testEncoding(final Function<byte[], String> encoder,
      final Function<String, byte[]> decoder, final String decoded, final String encoded) {
    testEncodes(encoder, decoded, encoded);
    testDecodes(decoder, encoded, decoded);
  }

  private static void testEncodes(final Function<byte[], String> encoder, final String decoded,
      final String encoded) {
    assertEquals(encoded, encoder.apply(decoded.getBytes(UTF_8)));
  }

  private static void testDecodes(final Function<String, byte[]> decoder, final String encoded,
      final String decoded) {
    assertArrayEquals(decoded.getBytes(UTF_8), decoder.apply(encoded));
  }

  @Test
  public void testBase16() {
    testEncodingWithCasing("", "");
    testEncodingWithCasing("f", "66");
    testEncodingWithCasing("fo", "666F");
    testEncodingWithCasing("foo", "666F6F");
    testEncodingWithCasing("foob", "666F6F62");
    testEncodingWithCasing("fooba", "666F6F6261");
    testEncodingWithCasing("foobar", "666F6F626172");
  }

  @Test
  public void testBase16Offset() {
    testEncodesWithOffset("foobar", 0, 6, "666F6F626172");
    testEncodesWithOffset("foobar", 1, 5, "6F6F626172");
    testEncodesWithOffset("foobar", 2, 3, "6F6261");
    testEncodesWithOffset("foobar", 3, 1, "62");
    testEncodesWithOffset("foobar", 4, 0, "");
  }

  @Test
  public void testBase16InvalidDecodingNewlines() {
    assertEquals("Invalid character '\n' for hex encoding at position 0.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked("\n\n")).getMessage());
  }

  @Test
  public void testBase16InvalidDecodingG() {
    assertEquals("Invalid character 'G' for hex encoding at position 2.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked("EFGH")).getMessage());
  }

  @Test
  public void testBase16ValidDecodingOddLength1() {
    assertEquals("Invalid hex encoding length of 1.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked("A")).getMessage());
  }

  @Test
  public void testBase16ValidDecodingOddLength3() {
    assertEquals("Invalid hex encoding length of 3.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked("ABC")).getMessage());
  }

  @Test
  public void testBase16InvalidDecodingOddLength1() {
    assertEquals("Invalid hex encoding length of 1.",
        assertThrows(IllegalArgumentException.class,
            () -> JHex.decodeChecked("?")).getMessage());
  }
}
