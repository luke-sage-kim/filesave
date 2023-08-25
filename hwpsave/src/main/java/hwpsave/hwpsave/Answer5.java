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

public class Answer5 {
	
	public static void main(String[] args) {
		
		try {
			String[] sentence =	readFile("sample.hwp");
			System.out.println(sentence[0]);	        		
			System.out.println(sentence[1]);	        		
			System.out.println(sentence[2]);	        		

			HWPFile hwpFile = BlankFileMaker.make();

			Section s = hwpFile.getBodyText().getSectionList().get(0);

			// 첫 번째 문장
			Paragraph firstParagraph = s.getParagraph(0);
			ParaText paraText = firstParagraph.getText();
			paraText.addString(sentence[0]);
			
			// 두 번째 문장
			Paragraph secondParagraph = s.addNewParagraph();
			setParaHeader(secondParagraph);
			setParaText(secondParagraph, sentence[1]);
			setParaCharShape(secondParagraph);
			setParaLineSeg(secondParagraph);
			
			//세번째문장
			Paragraph thirdParagraph = s.addNewParagraph();
			setParaHeader(thirdParagraph);
			setParaText(thirdParagraph,(sentence[2]));
			setParaCharShape(thirdParagraph);
			setParaLineSeg(thirdParagraph);
			
			for (int i = 0; i < sentence.length; i++) {
				
				
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
		ph.setLastInList(true);//헤더의 LastInList 속성을 true로 설정합니다. 이는 해당 문단이 목록의 마지막 항목인지 여부를 나타냅
		// 셀의 문단 모양을 이미 만들어진 문단 모양으로 사용함
		ph.setParaShapeId(1);//문단의 모양을 나타내는 ID를 1로 설정
		// 셀의 스타일을이미 만들어진 스타일으로 사용함
		ph.setStyleId((short) 1);// 문단의 스타일을 나타내는 ID를 1로 설정
		ph.getDivideSort().setDivideSection(false);// 분할 설정을 다루는 DivideSort 객체를 통해 섹션 분할 여부를 false로 설정
		ph.getDivideSort().setDivideMultiColumn(false);//여러 열에 걸친 분할 여부를 false로 설정
		ph.getDivideSort().setDividePage(false);//페이지에 걸친 분할 여부를 false로 설정합니다
		ph.getDivideSort().setDivideColumn(false);//열에 걸친 분할 여부를 false로 설정
		ph.setCharShapeCount(1);//문자 모양의 개수를 1로 설정합니다.
		ph.setRangeTagCount(0);//범위 태그의 개수를 0으로 설정합니다
		ph.setLineAlignCount(1);//줄 정렬의 개수를 1로 설정합니다.
		ph.setInstanceID(0);//인스턴스 ID를 0으로 설정합니다.
		ph.setIsMergedByTrack(0);//트랙에 의해 병합 여부를 0으로 설정합니다
	}

	private static void setParaText(Paragraph p, String text2) {
		p.createText();
		ParaText pt = p.getText();
		try {
			pt.addString(text2);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void setParaCharShape(Paragraph p) {
		p.createCharShape();

		ParaCharShape pcs = p.getCharShape();
		// 셀의 글자 모양을 이미 만들어진 글자 모양으로 사용함
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
