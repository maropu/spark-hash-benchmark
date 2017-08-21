/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.maropu;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import org.apache.spark.unsafe.hash.Murmur3_x86_32;


@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1, jvmArgsAppend = {"-XX:-UseSuperWord"})
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public class HashWithNoSuperWord {
  public static final int SIZE = 1024;

  @State(Scope.Thread)
  public static class Context {
    public final int[] values = new int[SIZE];
    public final int[] results = new int[SIZE];

    @Setup
    public void setup() {
      Random random = new Random();
      for (int i = 0; i < SIZE; i++) {
        values[i] = random.nextInt(Integer.MAX_VALUE / 32);
      }
    }
  }

  @Benchmark
  @CompilerControl(CompilerControl.Mode.DONT_INLINE) // This makes looking at assembly easier
  public int[] test(Context context) {
    for (int i = 0; i < SIZE; i++) {
      context.results[i] = Murmur3_x86_32.hashInt(context.values[i], 0);
    }
    return context.results;
  }
}
