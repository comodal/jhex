package systems.comodal.jhex;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class ThreadState {

  int index = 0;
}
