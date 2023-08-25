package hwpsave.hwpsave;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractOption;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class Answer4 {
	public static void main(String[] args) throws Exception {
        test("sample.hwp");

    }

    private static void test(String filename)
            throws Exception {
        HWPFile hwpFile = HWPReader.fromFile(fullPath(filename));
        System.out.println(filename + "  읽기 성공 !!");
        System.out.println();

        TextExtractOption option = new TextExtractOption();
        option.setMethod(TextExtractMethod.InsertControlTextBetweenParagraphText);
        option.setWithControlChar(false);
        option.setAppendEndingLF(true);

        String hwpText = TextExtractor.extract(hwpFile, option);
      
      
        
        
        
        HWPFile hwpFileMake = BlankFileMaker.make();
        Section s = hwpFileMake.getBodyText().getSectionList().get(0);
        Paragraph firstParagraph = s.addNewParagraph();
        
        
        String[] splitSentence = hwpText.split("\\n");
       
        for (String sentence : splitSentence) {
        	System.out.println(sentence);
        	
        	// firstParagraph.getText().addString(sentence);
        	
        }
        
        //파일만들기

		//HWPWriter.toFile(hwpFile, "c:/temp/sample9.hwpx" );
        
        
    }

    private static String fullPath(String filename) {
        return "C:\\Temp\\"+ filename;
    }


}
