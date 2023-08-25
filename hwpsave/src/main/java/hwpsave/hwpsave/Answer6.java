package hwpsave.hwpsave;

import java.io.UnsupportedEncodingException;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.CharPositionShapeIdPair;
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.ParaCharShape;
import kr.dogfoot.hwplib.object.bodytext.paragraph.header.ParaHeader;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.LineSegItem;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.ParaLineSeg;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.object.docinfo.CharShape;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractOption;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class Answer6 {
public static void main(String[] args) {
		
		try {
			String[] sentence =	readFile("sample.hwp"); //샘플 불러오는 메소드
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

			//HWPWriter.toFile(hwpFile, "c:/temp/sample11.hwpx");

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

	private int faceNameIndexForGulim;
	
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
		ParaText pt = p.getText();//해당 문단에 텍스트를 추가할 수 있는 준비를 합니다.
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
		
	    pcs.addParaCharShape(0, 1); // 새로 추가한 글자 모양을 적용

	}
	

	
	

	private static void setParaLineSeg(Paragraph p) {
		p.createLineSeg();// 문단 내에서 줄 레이아웃을 생성

		ParaLineSeg pls = p.getLineSeg();// 생성한 줄 레이아웃 객체를 가져옵
		LineSegItem lsi = pls.addNewLineSegItem();// 새로운 줄 레이아웃 항목을 추가

		lsi.setTextStartPosition(0);// 텍스트의 시작 위치를 설정
		lsi.setLineVerticalPosition(0);// 줄의 수직 위치를 설정
		lsi.setLineHeight(ptToLineHeight(10.0));// 줄의 높이를 설정
		lsi.setTextPartHeight(ptToLineHeight(10.0)); // 텍스트 영역의 높이를 설정
		lsi.setDistanceBaseLineToLineVerticalPosition(ptToLineHeight(10.0 * 0.85));// 기준선과 줄의 수직 위치 간의 거리를 설정
		lsi.setLineSpace(ptToLineHeight(3.0));// 줄 간격을 설정
		lsi.setStartPositionFromColumn(0);		// 열의 시작 위치를 설정
		lsi.setSegmentWidth((int) mmToHwp(50.0)); // 세그먼트의 너비를 설정
		lsi.getTag().setFirstSegmentAtLine(true);// 첫 번째 세그먼트가 줄의 처음에 있는지 설정
		lsi.getTag().setLastSegmentAtLine(true); // 마지막 세그먼트가 줄의 끝에 있는지 설정
	}

	private static int ptToLineHeight(double pt) {
		return (int) (pt * 100.0f);// // 포인트를 한글 문서 단위로 변환하여 반환 
	}

	private static long mmToHwp(double mm) {
		return (long) (mm * 72000.0f / 254.0f + 0.5f);// 밀리미터를 한글 문서 단위로 변환하여 반환
	}
	
	
	
	
}
