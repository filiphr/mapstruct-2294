A temporary repo to illustrate https://github.com/mapstruct/mapstruct/issues/2294

The reason for the problem is actually due to a bug in the Java compiler. 
There are different errors under different java compiler versions:

### Java 15

Steps to reproduce:

1. Make sure that you are using Java 15 for the compilation
1. export MAVEN_OPTS="--enable-preview" && ./mvnw clean verify -Djava.version=16

The result will be a compilation error coming from the [`TestProcessor`](processor-module/src/main/java/processor/TestProcessor.java)
because `TypeElement#getRecordComponents` does not return any record components for records coming from another jar (not in the compilation round).

The error message will be: "Record element source.SourceInAnotherPackage has no record components"

### Java 16.ea.28-open

Steps to reproduce:

1. Make sure that you are using Java 16.ea.28-open for the compilation
1. export MAVEN_OPTS="--enable-preview" && ./mvnw clean verify -Djava.version=16

The result will be a compilation error coming from the [`TestProcessor`](processor-module/src/main/java/processor/TestProcessor.java)
because `RecordComponentElement#getAccessor` returns `null` for records coming from another jar (not in the compilation round).

The error message will be: "Record component data from record element source.SourceInAnotherPackage has no accessor"
