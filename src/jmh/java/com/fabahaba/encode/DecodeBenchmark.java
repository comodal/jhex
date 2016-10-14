package com.fabahaba.encode;

import com.google.common.io.BaseEncoding;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.IntStream;

import javax.xml.bind.DatatypeConverter;

@State(Scope.Benchmark)
@Threads(1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public class DecodeBenchmark {

  private static final int NUM_ELEMENTS = 1 << 18;
  private static final int MASK = NUM_ELEMENTS - 1;
  private static final int ELEMENT_LENGTH = 256;

  @Param({
             "JHEX_CHAR_ITERATOR",
             "JHEX_CODE_POINT_ITERATOR",
             "JHEX_TO_CHAR_ARRAY",
             "JHEX_TO_CHAR_ARRAY_CHECKED",
             "COMMONS_CODEC",
             "GUAVA",
             "JMX_DATATYPE_CONVERTER",
         })
  private DecodeFactory decodeType;
  private Function<String, byte[]> decodeFunction;
  private final byte[][] data = new byte[NUM_ELEMENTS][ELEMENT_LENGTH];
  private final String[] hexStrings = new String[NUM_ELEMENTS];

  @Setup(Level.Iteration)
  public void setup() {
    if (decodeFunction == null) {
      decodeFunction = decodeType.createDecodeFunction();
    }
    IntStream.range(0, NUM_ELEMENTS).parallel()
        .forEach(i -> {
          final byte[] element = data[i];
          ThreadLocalRandom.current().nextBytes(element);
          hexStrings[i] = JHex.encode(element);
        });
  }

  @Benchmark
  public byte[] run(final EncodeBenchmark.ThreadState threadState) {
    return decodeFunction.apply(hexStrings[threadState.index++ & MASK]);
  }

  public enum DecodeFactory {

    JHEX_TO_CHAR_ARRAY {
      @Override
      public Function<String, byte[]> createDecodeFunction() {
        return JHex::decode;
      }
    },
    JHEX_TO_CHAR_ARRAY_CHECKED {
      @Override
      public Function<String, byte[]> createDecodeFunction() {
        return JHex::decodeChecked;
      }
    },
    JHEX_CHAR_ITERATOR {
      @Override
      public Function<String, byte[]> createDecodeFunction() {
        return JHexAlt::decodeCharIter;
      }
    },
    JHEX_CODE_POINT_ITERATOR {
      @Override
      public Function<String, byte[]> createDecodeFunction() {
        return JHexAlt::decodeCodePointIter;
      }
    },
    COMMONS_CODEC {
      @Override
      public Function<String, byte[]> createDecodeFunction() {
        return hex -> {
          try {
            return Hex.decodeHex(hex.toCharArray());
          } catch (DecoderException e) {
            throw new RuntimeException(e);
          }
        };
      }
    },
    GUAVA {
      @Override
      public Function<String, byte[]> createDecodeFunction() {
        final BaseEncoding coder = BaseEncoding.base16().lowerCase();
        return coder::decode;
      }
    },
    JMX_DATATYPE_CONVERTER {
      @Override
      public Function<String, byte[]> createDecodeFunction() {
        return DatatypeConverter::parseHexBinary;
      }
    };

    public abstract Function<String, byte[]> createDecodeFunction();
  }
}
