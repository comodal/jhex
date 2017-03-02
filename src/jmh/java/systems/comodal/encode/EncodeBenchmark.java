package systems.comodal.encode;

import com.google.common.io.BaseEncoding;
import io.ipfs.multibase.Base16;
import io.ipfs.multibase.Multibase;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import javax.xml.bind.DatatypeConverter;
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

@State(Scope.Benchmark)
@Threads(1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public class EncodeBenchmark {

  private static final int NUM_ELEMENTS = 1 << 21;
  private static final int MASK = NUM_ELEMENTS - 1;
  private static final int ELEMENT_LENGTH = 32;
  private final byte[][] data = new byte[NUM_ELEMENTS][ELEMENT_LENGTH];
  @Param({
      "JHEX_BYTE_STR_CTOR",
      "JHEX_UPPER",
      "JHEX_REVERSE",
      "JHEX",
      "COMMONS_CODEC",
      "GUAVA",
      "JMX_DATATYPE_CONVERTER",
      "BC",
      "MULTIBASE"
  })
  private EncodeFactory encodeType;
  private Function<byte[], String> encodeFunction;

  @Setup(Level.Iteration)
  public void setup(final ThreadState threadState) {
    threadState.index = 0;
    if (encodeFunction == null) {
      encodeFunction = encodeType.createEncodeFunction();
      final ThreadLocalRandom random = ThreadLocalRandom.current();
      for (int i = 0; i < NUM_ELEMENTS; i++) {
        random.nextBytes(data[i]);
      }
    } else {
      DecodeBenchmark.shuffleArray(data);
    }
  }

  @Benchmark
  public String run(final ThreadState threadState) {
    return encodeFunction.apply(data[threadState.index++ & MASK]);
  }

  public enum EncodeFactory {

    JHEX {
      @Override
      public Function<byte[], String> createEncodeFunction() {
        return JHex::encode;
      }
    },
    JHEX_UPPER {
      @Override
      public Function<byte[], String> createEncodeFunction() {
        return JHex::encodeUpper;
      }
    },
    JHEX_REVERSE {
      @Override
      public Function<byte[], String> createEncodeFunction() {
        return data -> JHex.encodeReverse(data, data.length - 1, data.length);
      }
    },
    JHEX_BYTE_STR_CTOR {
      @Override
      public Function<byte[], String> createEncodeFunction() {
        return JHexAlt::encode;
      }
    },
    COMMONS_CODEC {
      @Override
      public Function<byte[], String> createEncodeFunction() {
        return Hex::encodeHexString;
      }
    },
    GUAVA {
      @Override
      public Function<byte[], String> createEncodeFunction() {
        final BaseEncoding coder = BaseEncoding.base16().lowerCase();
        return coder::encode;
      }
    },
    JMX_DATATYPE_CONVERTER {
      @Override
      public Function<byte[], String> createEncodeFunction() {
        return DatatypeConverter::printHexBinary;
      }
    },
    BC {
      @Override
      public Function<byte[], String> createEncodeFunction() {
        return org.bouncycastle.util.encoders.Hex::toHexString;
      }
    },
    MULTIBASE {
      @Override
      public Function<byte[], String> createEncodeFunction() {
        return Base16::encode;
      }
    };

    public abstract Function<byte[], String> createEncodeFunction();
  }
}
