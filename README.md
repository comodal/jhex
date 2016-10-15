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
* VM: JDK 9-ea+140 / options: -server -Xmx4G

####Decoding

> JMH 1.15, 1 thread, 10 warm-up & 20 measurement iterations.

####Encoding

> JMH 1.15, 1 thread, 10 warm-up & 20 measurement iterations.
