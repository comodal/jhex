package com.fabahaba.encode;

import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class ThreadState {

  int index = 0;
}
