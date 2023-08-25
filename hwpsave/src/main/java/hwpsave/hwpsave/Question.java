package hwpsave.hwpsave;

import java.io.UnsupportedEncodingException;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.ParaCharShape;
import kr.dogfoot.hwplib.object.bodytext.paragraph.header.ParaHeader;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.LineSegItem;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.ParaLineSeg;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractOption;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class Question {
public static void main(String[] args) {
		
		try {
			String[] sentence =	readFile("sample.hwp");
	for (int i = 0; i < sentence.length; i++) {
		System.out.println(i+"번째 문장은?" + sentence[i]);		
	}        		
	
			        		

			HWPFile hwpFile = BlankFileMaker.make();

			Section s = hwpFile.getBodyText().getSectionList().get(0);
	
		
			for (int i = 0; i < sentence.length; i++) {
			    Paragraph paragraph = s.addNewParagraph();
			    setParaHeader(paragraph);
			    setParaText(paragraph, sentence[i]);
			    

			    setParaCharShape(paragraph);
			    setParaLineSeg(paragraph);
			    
			    
			}

			HWPWriter.toFile(hwpFile, "c:/temp/sample11.hwpx");

		} catch (Exception e) {
			if (e.getMessage() != null) {
				System.out.println("오류발생");
				System.out.println(e.getMessage());
				e.printStackTrace();
			} else {
				System.out.println("오류발생");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		}
		
		
		
	}
	
    private static String[] readFile(String filename)
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
       
          
        
        return splitSentence;
    }
	  
	
	 private static String fullPath(String filename) {
	        return "C:\\Temp\\"+ filename;
	    }
		
	
	private static void setParaHeader(Paragraph p) {
		ParaHeader ph = p.getHeader();
		ph.setLastInList(true);
		ph.setParaShapeId(1);
		ph.setStyleId((short) 1);
		ph.getDivideSort().setDivideSection(false);
		ph.getDivideSort().setDivideMultiColumn(false);
		ph.getDivideSort().setDividePage(false);
		ph.getDivideSort().setDivideColumn(false);
		ph.setCharShapeCount(1);
		ph.setRangeTagCount(0);
		ph.setLineAlignCount(1);
		ph.setInstanceID(0);
		ph.setIsMergedByTrack(0);
	}

	private static void setParaText(Paragraph p, String text2) {//텍스트를 추가하는 역할
		p.createText();
		ParaText pt = p.getText();
		try {
			pt.addString(text2);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
	}

	private static void setParaCharShape(Paragraph p) {
		p.createCharShape();

		ParaCharShape pcs = p.getCharShape();
		
		pcs.addParaCharShape(0, 1);
	}

	private static void setParaLineSeg(Paragraph p) {
		p.createLineSeg();

		ParaLineSeg pls = p.getLineSeg();
		LineSegItem lsi = pls.addNewLineSegItem();

		lsi.setTextStartPosition(0);
		lsi.setLineVerticalPosition(0);
		lsi.setLineHeight(ptToLineHeight(10.0));
		lsi.setTextPartHeight(ptToLineHeight(10.0));
		lsi.setDistanceBaseLineToLineVerticalPosition(ptToLineHeight(10.0 * 0.85));
		lsi.setLineSpace(ptToLineHeight(3.0));
		lsi.setStartPositionFromColumn(0);
		lsi.setSegmentWidth((int) mmToHwp(50.0));
		lsi.getTag().setFirstSegmentAtLine(true);
		lsi.getTag().setLastSegmentAtLine(true);
	}

	private static int ptToLineHeight(double pt) {
		return (int) (pt * 100.0f);
	}

	private static long mmToHwp(double mm) {
		return (long) (mm * 72000.0f / 254.0f + 0.5f);
	}
	
}
