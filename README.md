This is benchmark code for hash functions in Apache Spark using JMH.

## How to run benchmar code

You run a script below:

    $ ./bin/run-hash-benchmark

If you would like to print compiled assembly together, you need to define `ENABLE_PRINT_ASSEMBLY` like:

    $ ENABLE_PRINT_ASSEMBLY=1 ./bin/run-hash-benchmark

## How to compile hsdis on your platform

    $ ./bin/compile-hsdis

## Performance results

    # VM version: JDK 1.8.0_141, VM 25.141-b16
    # CPU: Intel(R) Xeon(R) CPU E5-2666 v3 @ 2.90GHz
    Benchmark                                   Mode  Cnt     Score   Error  Units
    HashWithNoSuperWord.Murmur3_x86_32_hashInt  avgt   10  3426.037 ± 0.460  ns/op
    HashWithSuperWord.Murmur3_x86_32_hashInt    avgt   10  3426.425 ± 0.591  ns/op

    # VM version: JDK 9, VM 9+181
    # CPU: Intel(R) Xeon(R) CPU E5-2666 v3 @ 2.90GHz
    Benchmark                                   Mode  Cnt     Score   Error  Units
    HashWithNoSuperWord.Murmur3_x86_32_hashInt  avgt   10  3427.109 ± 1.059  ns/op
    HashWithSuperWord.Murmur3_x86_32_hashInt    avgt   10   999.454 ± 0.924  ns/op

