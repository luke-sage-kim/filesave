//package hwpsave.hwpsave;
//
//
//
//
//import kr.dogfoot.hwpxlib.object.HWPXFile;
//import kr.dogfoot.hwpxlib.reader.HWPXReader;
//import kr.dogfoot.hwpxlib.writer.HWPXWriter;
//
//public class Cloning_HWPX {
//	 public static void main(String[] args) throws Exception {
//	        test("4.hwp");
//
//	    }
//
//	    private static void test(String filename) throws Exception {
//	        HWPXFile hwpxFile = HWPXReader.fromFilepath(fullPath(filename));
//	        if (hwpxFile != null) {
//	        	HWPXFile clonedHWPXFile = (HWPXFile) hwpxFile.clone(false);
//	            String filename2 = "result-clone-" + filename;
//	            HWPXWriter.toFilepath(clonedHWPXFile, fullPath(filename2));
//	            System.out.println(filename + " ok !!!");
//	        }
//	    }
//
//	    private static String fullPath(String filename) {
//	        return "C:\\Temp\\"+ filename;
//	    }
//}
