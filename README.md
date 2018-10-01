# jhex [![Build Status](https://travis-ci.org/comodal/jhex.svg?branch=master)](https://travis-ci.org/comodal/jhex) [ ![Download](https://api.bintray.com/packages/comodal/libraries/jhex/images/download.svg) ](https://bintray.com/comodal/libraries/jhex/_latestVersion) [![license](https://img.shields.io/badge/license-Apache%202-blue.svg)](LICENSE) [![codecov](https://codecov.io/gh/comodal/jhex/branch/master/graph/badge.svg)](https://codecov.io/gh/comodal/jhex)

[JHex](systems.comodal.jhex/src/main/java/systems/comodal/jhex/JHex.java#L7) provides static utility methods for encoding to and decoding from hexadecimal encoded data.

```java
String hexString = "4265207375726520746F206472696E6B20796F7572204F76616C74696E65";
byte[] decoded = JHex.decode(hexString);
System.out.println(new String(decoded));
String reEncoded = JHex.encode(decoded);
// reEncoded.equals(hexString) == true ... promise.
```

### Benchmarks

###### Environment

* Intel® Xeon(R) CPU E5-2687W v3 @ 3.10GHz × 20 / 128GB Memory / Ubuntu 16.04
* JDK 9-ea+140 / vm options: -server -Xmx16G
* JMH 1.15, 1 thread, 5 warm-up & 10 measurement iterations.
* Comparison Dependencies:
  * com.google.guava:guava:20.0-rc1
  * commons-codec:commons-codec:1.10
  * javax.xml.bind:jaxb-api:2.2.12

Each benchmark method encodes or decodes an element from an array of 8,388,608 randomly generated elements.  The array is shuffled between each JMH iteration.

Actual result numbers can be found under [./benchmark](benchmark)

#### [Decoding](systems.comodal.jhex/src/jmh/java/systems/comodal/jhex/DecodeBenchmark.java#L79)

>./gradlew jmh -PbenchmarkRegex=Decode

* JHEX_TO_CHAR_ARRAY: Uses `char[] String#toCharArray()`
* JHEX_CHAR_ITERATOR: Uses `PrimitiveIterator.OfInt String#chars().iterator()`.  Corresponds to `JHex.decodePrimIter()`.
* JHEX_CHAR_AT: Uses `char String#chartAt(int)`.  Corresponds to `JHex.decode()`.
* \*_CHECKED: Validates encoding and length to match functionality of other libraries. Corresponds to `JHex.*Checked()` methods.

##### 8-byte elements
![decode-8-byte-elements](https://rawgit.com/comodal/jhex/master/benchmark/decode-8-byte-elements.svg)
##### 32-byte elements
![decode-32-byte-elements](https://rawgit.com/comodal/jhex/master/benchmark/decode-32-byte-elements.svg)
##### 128-byte elements
![decode-128-byte-elements](https://rawgit.com/comodal/jhex/master/benchmark/decode-128-byte-elements.svg)
##### 512-byte elements
![decode-512-byte-elements](https://rawgit.com/comodal/jhex/master/benchmark/decode-512-byte-elements.svg)

#### [Encoding](systems.comodal.jhex/src/jmh/java/systems/comodal/jhex/EncodeBenchmark.java#L66)

>./gradlew jmh -PbenchmarkRegex=Encode

* JHEX_BYTE_STR_CTOR: Uses the Java String byte[] constructor, instead of char[].
* JHEX_UPPER: Encodes to upper case, all other benchmarks use lower case.
* JHEX_REVERSE: Allows the user to java.systems.comodal.encode by traversing the given data in reverse.
* JHEX: Very similar to Apache Commons Codec implementation.  Uses the Java String char[] constructor.

##### 8-byte elements
![java.systems.comodal.encode-8-byte-elements](https://cdn.rawgit.com/comodal/jhex/master/benchmark/java.systems.comodal.encode-8-byte-elements.svg)
##### 32-byte elements
![java.systems.comodal.encode-32-byte-elements](https://cdn.rawgit.com/comodal/jhex/master/benchmark/java.systems.comodal.encode-32-byte-elements.svg)
##### 128-byte elements
![java.systems.comodal.encode-128-byte-elements](https://cdn.rawgit.com/comodal/jhex/master/benchmark/java.systems.comodal.encode-128-byte-elements.svg)
##### 512-byte elements
![java.systems.comodal.encode-512-byte-elements](https://cdn.rawgit.com/comodal/jhex/master/benchmark/java.systems.comodal.encode-512-byte-elements.svg)
