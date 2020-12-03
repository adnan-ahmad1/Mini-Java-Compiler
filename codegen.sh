# Test our mini java examples
for d in SamplePrograms/CodeGenTests/*.java; do
  f=${d%.*}
  f=${f##*/}
  echo "TEST ${f}"
  java -cp build/classes:lib/java-cup-11b.jar MiniJava ${d} > test.s
  gcc -o testOutput test.s src/runtime/boot.c
  ./testOutput > testOutput.expected
  cmp --silent testOutput.expected test/resources/MiniJavaLanguageCodeGen/$f.expected || echo "${f} difference"
done

# Test Sample Programs
for d in SamplePrograms/SampleMiniJavaPrograms/*.java; do
  f=${d%.*}
  f=${f##*/}
  echo "TEST ${f}"
  java -cp build/classes:lib/java-cup-11b.jar MiniJava ${d} > test.s
  gcc -o testOutput test.s src/runtime/boot.c
  ./testOutput > testOutput.expected
  cmp --silent testOutput.expected test/resources/MiniJavaLanguageCodeGen/$f.expected || echo "${f} difference"
done