# Test our mini java examples
for d in SamplePrograms/CodeGenTests/*.java; do
  f=${d%.*}
  f=${f##*/}
  echo "TEST ${f}"
  java -cp build/classes:lib/java-cup-11b.jar MiniJava ${d} > CodeGenOutput.s
  gcc -o CodeGenOutput CodeGenOutput.s src/runtime/boot.c
  ./CodeGenOutput > CodeGenOutput.expected
  cmp --silent CodeGenOutput.expected test/resources/MiniJavaLanguageCodeGen/$f.expected || echo "${f} difference"
done

# Test Sample Programs
for d in SamplePrograms/SampleMiniJavaPrograms/*.java; do
  f=${d%.*}
  f=${f##*/}
  echo "TEST ${f}"
  java -cp build/classes:lib/java-cup-11b.jar MiniJava ${d} > CodeGenOutput.s
  gcc -o CodeGenOutput CodeGenOutput.s src/runtime/boot.c
  ./CodeGenOutput > CodeGenOutput.expected
  cmp --silent CodeGenOutput.expected test/resources/MiniJavaLanguageCodeGen/$f.expected || echo "${f} difference"
done