# Test our mini java examples
for d in SamplePrograms/SemanticsTests/*.java; do
  f=${d%.*}
  f=${f##*/}
  echo "TEST ${f}"
  java -cp build/classes:lib/java-cup-11b.jar MiniJava -T ${d} > SemanticsOutput.txt
  cmp --silent SemanticsOutput.txt test/resources/MiniJavaLanguageSemantics/$f.expected || echo "${f} difference"
done

# Test Sample Programs
for d in SamplePrograms/SampleMiniJavaPrograms/*.java; do
  f=${d%.*}
  f=${f##*/}
  echo "TEST ${f}"
  java -cp build/classes:lib/java-cup-11b.jar MiniJava -T ${d} > SemanticsOutput.txt
  cmp --silent SemanticsOutput.txt test/resources/MiniJavaLanguageSemantics/$f.expected || echo "${f} difference"
done
