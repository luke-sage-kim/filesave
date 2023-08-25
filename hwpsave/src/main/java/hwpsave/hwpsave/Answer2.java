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

import java.io.Console;
import java.io.File;


/**
 * 파일에 전체 텍스트를 추출하는 샘플 프로그램
 */
public class Answer2 {
    public static void main(String[] args) throws Exception {
        test("sample2.hwp");

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
      //  System.out.println(hwpText);
        System.out.println("========================================================");
        
        String[] splitSentence = hwpText.split("\\n");
       
        for (String line : splitSentence) {
        	System.out.println(line);
        	
        }
        
        //파일만들기
//		HWPFile hwpFileMake = BlankFileMaker.make( );
//
//		Section s = hwpFileMake.getBodyText( ).getSectionList( ).get( 0 );
//	
//		
//		Paragraph firstParagraph = s.getParagraph( 0 );
//
//		firstParagraph.getText( ).addString( hwpText );
//		
//		
//		
//		
//		HWPWriter.toFile(hwpFile, "c:/temp/sample8.hwpx" );
        
        
    }

    private static String fullPath(String filename) {
        return "C:\\Temp\\"+ filename;
    }


}
