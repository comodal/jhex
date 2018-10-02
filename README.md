# jhex [![Build Status](https://travis-ci.org/comodal/jhex.svg?branch=master)](https://travis-ci.org/comodal/jhex) [ ![Download](https://api.bintray.com/packages/comodal/libraries/jhex/images/download.svg) ](https://bintray.com/comodal/libraries/jhex/_latestVersion) [![license](https://img.shields.io/badge/license-Apache%202-blue.svg)](LICENSE) [![codecov](https://codecov.io/gh/comodal/jhex/branch/master/graph/badge.svg)](https://codecov.io/gh/comodal/jhex)

[JHex](systems.comodal.jhex/src/main/java/systems/comodal/jhex/JHex.java#L1) provides static utility methods for encoding to and decoding from hexadecimal encoded data.

```java
String hexString = "4265207375726520746F206472696E6B20796F7572204F76616C74696E65";
byte[] decoded = JHex.decode(hexString);
System.out.println(new String(decoded));
String reEncoded = JHex.encode(decoded);
// reEncoded.equals(hexString) == true ... promise.
```