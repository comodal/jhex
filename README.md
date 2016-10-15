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

Each benchmark method encodes or decodes an element from an array of 8388608 randomly generated elements.  The array is shuffled between each JMH iteration.

####[Decoding](src/jmh/java/com/fabahaba/encode/DecodeBenchmark.java#L79)

##### 8 byte elements
![decode-8-byte-elements](benchmark/decode-8-byte-elements.svg)
##### 32 byte elements
![decode-32-byte-elements](benchmark/decode-32-byte-elements.svg)
##### 128 byte elements
![decode-128-byte-elements](benchmark/decode-128-byte-elements.svg)
##### 512 byte elements
![decode-512-byte-elements](benchmark/decode-512-byte-elements.svg)
 
####[Encoding](src/jmh/java/com/fabahaba/encode/EncodeBenchmark.java#L66)

##### 8 byte elements 
![encode-8-byte-elements](benchmark/encode-8-byte-elements.svg)
##### 32 byte elements
![encode-32-byte-elements](benchmark/encode-32-byte-elements.svg)
##### 128 byte elements
![encode-128-byte-elements](benchmark/encode-128-byte-elements.svg)
##### 512 byte elements
![encode-512-byte-elements](benchmark/encode-512-byte-elements.svg)
