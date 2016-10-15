# jhex [![Build Status](https://travis-ci.org/jamespedwards42/jhex.svg?branch=master)](https://travis-ci.org/jamespedwards42/jhex) [ ![Download](https://api.bintray.com/packages/jamespedwards42/libs/jhex/images/download.svg) ](https://bintray.com/jamespedwards42/libs/jhex/_latestVersion) [![license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/jhex/jedipus/master/LICENSE) [![codecov](https://codecov.io/gh/jamespedwards42/jhex/branch/master/graph/badge.svg)](https://codecov.io/gh/jamespedwards42/jhex)

[JHex](src/main/java/com/fabahaba/encode/JHex.java#L7) provides static utility methods for encoding to and decoding from hexadecimal encoded data.

```java
String hexString = "596f752772652077656c636f6d652e";
byte[] decoded = JHex.decode(hexString);
System.out.println(new String(decoded));
String reEncoded = JHex.encode(decoded);
// reEncoded.equals(hexString) == true ... promise.
```

###Benchmarks
######Environment
* Intel® Xeon(R) CPU E5-2687W v3 @ 3.10GHz × 20 / 128GB Memory / Ubuntu 16.04
* VM: JDK 9-ea+140 / options: -server -Xmx16G
* JMH 1.15, 1 thread, 5 warm-up & 10 measurement iterations.
* Comparison Dependencies:
  * com.google.guava:guava:20.0-rc1
  * commons-codec:commons-codec:1.10
  * javax.xml.bind:jaxb-api:2.2.12
  
Each benchmark method encodes or decodes an element from an array of 8388608 randomly generated elements.  The array is shuffled between each JMH iteration.

####[Decoding](src/jmh/java/com/fabahaba/encode/DecodeBenchmark.java#L79)

* JHEX_CHAR_ITERATOR: Uses the Java 9 primitive int iterator, String#chars().  Does not validate encoding.
* JHEX_TO_CHAR_ARRAY: Uses String#toCharArray().  Does not validate encoding.
* JHEX_CHAR_ITERATOR_CHECKED: Uses the Java 9 primitive int iterator, String#chars().  Validates encoding.
* JHEX_TO_CHAR_ARRAY_CHECKED: Uses String#toCharArray().  Validates encoding.

##### 8 byte elements
![decode-8-byte-elements](https://cdn.rawgit.com/jamespedwards42/jhex/master/benchmark/decode-8-byte-elements.svg)
##### 32 byte elements
![decode-32-byte-elements](https://cdn.rawgit.com/jamespedwards42/jhex/master/benchmark/decode-32-byte-elements.svg)
##### 128 byte elements
![decode-128-byte-elements](https://cdn.rawgit.com/jamespedwards42/jhex/master/benchmark/decode-128-byte-elements.svg)
##### 512 byte elements
![decode-512-byte-elements](https://cdn.rawgit.com/jamespedwards42/jhex/master/benchmark/decode-512-byte-elements.svg)
 
####[Encoding](src/jmh/java/com/fabahaba/encode/EncodeBenchmark.java#L66)

* JHEX_BYTE_STR_CTOR: Uses the Java String byte array constructor.
* JHEX_UPPER: Encodes to upper case, all other benchmarks use lower case.
* JHEX_REVERSE: Allows the user to encode by traversing the given data in reverse.
* JHEX: Very similar to Apache Commons Codec implementation.  Uses the Java String char array constructor.

##### 8 byte elements 
![encode-8-byte-elements](https://cdn.rawgit.com/jamespedwards42/jhex/master/benchmark/encode-8-byte-elements.svg)
##### 32 byte elements
![encode-32-byte-elements](https://cdn.rawgit.com/jamespedwards42/jhex/master/benchmark/encode-32-byte-elements.svg)
##### 128 byte elements
![encode-128-byte-elements](https://cdn.rawgit.com/jamespedwards42/jhex/master/benchmark/encode-128-byte-elements.svg)
##### 512 byte elements
![encode-512-byte-elements](https://cdn.rawgit.com/jamespedwards42/jhex/master/benchmark/encode-512-byte-elements.svg)
