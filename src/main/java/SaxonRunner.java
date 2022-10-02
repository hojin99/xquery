import net.sf.saxon.s9api.*;
import java.io.*;
import java.nio.file.Files;

public class SaxonRunner {

    private Processor processor;
//    private String encoding = "MS949";
    private final String encoding = "UTF-8";

    public SaxonRunner() {
        setUpProcessor();
    }

    private void setUpProcessor() {
        processor = new Processor(false);
    }

    // 주의할점
    // xquery 내에서의 경로는 앱과 관련없이 xquery 파일의 위치 기준이다 doc()함수 쓸때 주의
    public void runApp(String[] params, String xqueryFile, String resultFile) throws Exception {
        XQueryCompiler compiler = processor.newXQueryCompiler();
        compiler.setUpdatingEnabled(false);
        compiler.setEncoding("UTF-8");
//        compiler.setBaseURI(new File(System.getProperty("user.dir")).toURI());
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(new File(resultFile).toPath()), encoding));
        Serializer serializer = processor.newSerializer(bw);
        serializer.setOutputProperty(Serializer.Property.METHOD, "xml");
        serializer.setOutputProperty(Serializer.Property.ENCODING, encoding);
        serializer.setOutputProperty(Serializer.Property.INDENT, "no");
        serializer.setOutputProperty(Serializer.Property.OMIT_XML_DECLARATION, "yes");

//        compiler.declareNamespace("xsd", "http://www.w3.org/2001/XMLSchema");
        XQueryExecutable executable = compiler.compile(new File(xqueryFile));
        XQueryEvaluator evaluator = executable.load();
        int i = 0;
        for (String param : params) {
            evaluator.setExternalVariable(new QName("param" + (++i)), new XdmAtomicValue(param));
        }

        evaluator.run(serializer);
    }

}