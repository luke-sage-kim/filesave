package hwpsave.hwpsave;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.writer.HWPWriter;
//클론해서 바로나오게하는 것
public class Answer1 {
	 public static void main(String[] args) throws Exception {
	        test("sample.hwp");

	    }

	    private static void test(String filename) throws Exception {
	        HWPFile hwpFile = HWPReader.fromFile(fullPath(filename));
	        if (hwpFile != null) {
	            HWPFile clonedHWPFile = hwpFile.clone(false);
	            String filename2 = "answer-" + filename;
	            HWPWriter.toFile(clonedHWPFile, fullPath(filename2));
	            System.out.println(filename + " ok !!!");
	        }
	    }

	    private static String fullPath(String filename) {
	        return "C:\\Temp\\"+ filename;
	    }

}
