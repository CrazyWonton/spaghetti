GS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
		program1.java \
		program2.java \
		program3.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
