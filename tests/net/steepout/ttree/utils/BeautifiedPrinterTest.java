package net.steepout.ttree.utils;

import org.junit.jupiter.api.Test;

class BeautifiedPrinterTest {

    @Test
    void printBytes() {
        BeautifiedPrinter.printBytes(("/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/" +
                "Contents/Home/bin/java -Didea.test.cyclic.buffer.size=1048576 \"" +
                "-javaagent:/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar=" +
                "52514:/Applications/IntelliJ IDEA CE.app/Contents/bin\" -Dfile.encoding=UTF-8 " +
                "-classpath \"/Applications/IntelliJ IDEA CE.app/Contents/lib/idea_rt.jar:/Applications/" +
                "IntelliJ IDEA CE.app/Contents/plugins/junit/lib/junit-rt.jar:/Applications/IntelliJ IDEA CE." +
                "app/Contents/plugins/junit/lib/junit5-rt.jar:/Users/phosphorus15/.m2/repository/org/junit" +
                "/platform/junit-platform-launcher/1.0.0/junit-platform-launcher-1.0.0.jar:/Users/phosphorus15" +
                "/.m2/repository/org/apiguardian/apiguardian-api/1.0.0/apiguardian-api-1.0.0.jar:/Users/phosphorus15/" +
                ".m2/repository/org/junit/platform/junit-platform-engine/1.0.0/junit-platform-engine-1.0.0.jar:" +
                "/Users/phosphorus15/.m2/repository/org/junit/platform/junit-platform-commons/1.0.0" +
                "/junit-platform-commons-1.0.0.jar:/Users/phosphorus15/.m2/repository/org/opentest4j/opentest4j" +
                "/1.0.0/opentest4j-1.0.0.jar:/Users/phosphorus15/.m2/repository/org/junit/jupiter/junit-jupiter-engine" +
                "/5.0.0/junit-jupiter-engine-5.0.0.jar:/Users/phosphorus15/.m2/repository/org/junit/jupiter" +
                "/junit-jupiter-api/5.0.0/junit-jupiter-api-5.0.0.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/" +
                "Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk" +
                "/Contents/Home/jre/lib/deploy.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents" +
                "/Home/jre/lib/ext/cldrdata.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home" +
                "/jre/lib/ext/dnsns.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home/jre/lib" +
                "/ext/jaccess.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home/jre/lib/ex" +
                "t/jfxrt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home/jre/lib/ext/locale" +
                "data.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home/jre/lib/ext/nashorn." +
                "jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home/jre/lib/ext/sunec.jar:/" +
                "Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home/jre/lib/ext/sunjce_provider.j" +
                "ar:/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home/jre/lib/ext/sunpkcs11.jar:" +
                "/Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home/jre/lib/ext/zipfs.jar:/" +
                "Library/Java/JavaVirtualMachines/jdk1.8.0_171.jdk/Contents/Home/jre/lib/javaws.jar:/Libra" +
                "-junit5 net.steepout.ttree.parser.l_arbre.ArbreProcessorTest,serialize\n").getBytes());
    }
}