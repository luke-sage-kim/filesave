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
import kr.dogfoot.hwplib.object.docinfo.CharShape;
import kr.dogfoot.hwplib.object.docinfo.FaceName;
import kr.dogfoot.hwplib.object.docinfo.charshape.BorderType2;
import kr.dogfoot.hwplib.object.docinfo.charshape.EmphasisSort;
import kr.dogfoot.hwplib.object.docinfo.charshape.OutterLineSort;
import kr.dogfoot.hwplib.object.docinfo.charshape.ShadowSort;
import kr.dogfoot.hwplib.object.docinfo.charshape.UnderLineSort;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractMethod;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractOption;
import kr.dogfoot.hwplib.tool.textextractor.TextExtractor;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class Answer7 {
	

	
public static void main(String[] args) {
		
		try {
			String[] sentence =	readFile("sample.hwp"); //샘플 불러오는 메소드
      						        		
			HWPFile hwpFile = BlankFileMaker.make();

			Section s = hwpFile.getBodyText().getSectionList().get(0);
			
			 Answer7 answer7 = new Answer7();
			 answer7.initializeCharShapes(hwpFile);
		 		 
			 
			for (int i = 2; i < sentence.length; i++) {
			    Paragraph paragraph = s.addNewParagraph();
			    setParaHeader(paragraph);
			    setParaText(paragraph, sentence[i]);			    
			    setParaCharShape(paragraph,i);
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


private HWPFile hwpFile;
private static int charShapeIndexForNormal;
private static int charShapeIndexForBlack;
private static int charShapeIndexForBold;
private static int charShapeIndexForBlackBold;
private static int charShapeIndexForBoldYellow;
private static int charShapeIndexForYellow;
private int faceNameIndexForBatang;

private Answer7() {
    hwpFile = null;
}








//글씨체 초괴화하는 친구들
private void initializeCharShapes(HWPFile hwpFile) {
	 this.hwpFile = hwpFile;
    faceNameIndexForBatang = createFaceNameForBatang();
    charShapeIndexForNormal = createCharShapeBlue(false);
    charShapeIndexForBlack = createCharShapeBlack(false);
    charShapeIndexForBold =  createCharShapeBlue(true);
    charShapeIndexForBlackBold =  createCharShapeBlack(true);
    charShapeIndexForBoldYellow =  createCharShapeBlackYellow(true);
    charShapeIndexForYellow =  createCharShapeBlackYellow(false);
    
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

	
	private static void setParaCharShape(Paragraph p,int i) {
		 int paragraphStartPos = 0;
		p.createCharShape();

		ParaCharShape pcs = p.getCharShape();
		// 셀의 글자 모양을 이미 만들어진 글자 모양으로 사용함
		
		if( i == 13) {
			pcs.addParaCharShape(paragraphStartPos, charShapeIndexForBold);
		}
		
		else if( i == 14) {
			pcs.addParaCharShape(paragraphStartPos, charShapeIndexForNormal);
		}
		else if((i == 2 || i == 3 || i == 4 ||  i == 10)) {
			pcs.addParaCharShape(paragraphStartPos, charShapeIndexForBlackBold);
		}
		else if(( i == 8)) {
			pcs.addParaCharShape(paragraphStartPos, charShapeIndexForBoldYellow);
		}
		
		else if((i== 9)) {
			pcs.addParaCharShape(paragraphStartPos, charShapeIndexForYellow);
		}
		
		else {
			
			pcs.addParaCharShape(paragraphStartPos, charShapeIndexForBlack);
		}
		
		
	  //  pcs.addParaCharShape(0, 1); // 새로 추가한 글자 모양을 적용

	}

	 
	  private int createFaceNameForBatang() {
	        FaceName fn;

	        fn = hwpFile.getDocInfo().addNewHangulFaceName();
	        setFaceNameForBatang(fn);
	     
	        fn = hwpFile.getDocInfo().addNewEnglishFaceName();
	        setFaceNameForBatang(fn);
	       
	        fn = hwpFile.getDocInfo().addNewHanjaFaceName();
	        setFaceNameForBatang(fn);

	        fn = hwpFile.getDocInfo().addNewJapaneseFaceName();
	        setFaceNameForBatang(fn);

	        fn = hwpFile.getDocInfo().addNewEtcFaceName();
	        setFaceNameForBatang(fn);

	        fn = hwpFile.getDocInfo().addNewSymbolFaceName();
	        setFaceNameForBatang(fn);

	        fn = hwpFile.getDocInfo().addNewUserFaceName();
	        setFaceNameForBatang(fn);

	        return hwpFile.getDocInfo().getHangulFaceNameList().size() - 1;
	    }

	    private void setFaceNameForBatang(FaceName fn) {
	        String fontName = "굴림체";
	        fn.getProperty().setHasBaseFont(false);//기본 글꼴이 없음을 설정
	        fn.getProperty().setHasFontInfo(false);//글꼴 정보가 없음을 설정
	        fn.getProperty().setHasSubstituteFont(false);//대체 글꼴이 없음을 설정
	        fn.setName(fontName);
	    }
	 
	    
	    private int ptToBaseSize(int pt) {
	        return pt * 100;
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
	
	
	
	
	
	
	
	
	 private int createCharShapeBlue(boolean bold) {    //글자 모양을 설정하는 부분을 담당
	        CharShape cs = hwpFile.getDocInfo().addNewCharShape();
	        // 바탕 폰트를 위한 FaceName 객체를 링크한다. (link FaceName Object for 'Batang' font.)
	        cs.getFaceNameIds().setForAll(faceNameIndexForBatang);//글자 모양에 해당 폰트의 FaceName 정보를 설정

	        cs.getRatios().setForAll((short) 100);		//글자의 장평 비율을 설정
	        cs.getCharSpaces().setForAll((byte) 0);		// 글자 간의 자간을 설정
	        cs.getRelativeSizes().setForAll((short) 100);//글자의 상대 크기를 설정
	        cs.getCharOffsets().setForAll((byte) 0);	//글자 위치를 설정
	        cs.setBaseSize(ptToBaseSize(12));   //글씨크기

	        cs.getProperty().setItalic(false);							//이탤릭체
	        cs.getProperty().setBold(bold);					//글자를 볼드체로 설정
	        cs.getProperty().setUnderLineSort(UnderLineSort.None);	//밑줄 종류를 설정
	        cs.getProperty().setUnderLineShape(BorderType2.Solid);	//밑줄의 모양을 설정
	        cs.getProperty().setOutterLineSort(OutterLineSort.None);//외곽선 종류를 설정
	        cs.getProperty().setShadowSort(ShadowSort.None);		//그림자 종류를 설정
	        cs.getProperty().setEmboss(false);						//음각 효과를 설정
	        cs.getProperty().setEngrave(false);						//양각 효과를 설정
	        cs.getProperty().setSuperScript(false);					//윗 첨자 설정을 설정
	        cs.getProperty().setSubScript(false);					//아랫 첨자 설정을 설정
	        cs.getProperty().setStrikeLine(false);					//취소선 설정을 설정
	        cs.getProperty().setEmphasisSort(EmphasisSort.None);	//강조 종류를 설정
	        cs.getProperty().setUsingSpaceAppropriateForFont(false);//글꼴에 적합한 공백 사용 여부를 설정
	        cs.getProperty().setStrikeLineShape(BorderType2.Solid);//취소선 모양을 설정
	        cs.getProperty().setKerning(false);						//커닝 설정을 설정

	        cs.setShadowGap1((byte) 0);
	        cs.setShadowGap2((byte) 0);
	        cs.getCharColor().setValue(0xFF0000); // 글자의 색상을 설정
	        cs.getUnderLineColor().setValue(0x00000000);//밑줄의 색상을 설정-> 현재 파랑
	        cs.getShadeColor().setValue(-1);			//음영 색상을 설정
	        cs.getShadowColor().setValue(0x00b2b2b2);// 그림자 색상을 설정
	        cs.setBorderFillId(0);

	        return hwpFile.getDocInfo().getCharShapeList().size() - 1;
	    }

	    
	    
	    
	    private int createCharShapeBlack(boolean bold) {    //글자 모양을 설정하는 부분을 담당
	        CharShape cs = hwpFile.getDocInfo().addNewCharShape();
	        // 바탕 폰트를 위한 FaceName 객체를 링크한다. (link FaceName Object for 'Batang' font.)
	        cs.getFaceNameIds().setForAll(faceNameIndexForBatang);//글자 모양에 해당 폰트의 FaceName 정보를 설정

	        cs.getRatios().setForAll((short) 100);		//글자의 장평 비율을 설정
	        cs.getCharSpaces().setForAll((byte) 0);		// 글자 간의 자간을 설정
	        cs.getRelativeSizes().setForAll((short) 100);//글자의 상대 크기를 설정
	        cs.getCharOffsets().setForAll((byte) 0);	//글자 위치를 설정
	        cs.setBaseSize(ptToBaseSize(12));   //글씨크기

	        cs.getProperty().setItalic(false);							//이탤릭체
	        cs.getProperty().setBold(bold);					//글자를 볼드체로 설정
	        cs.getProperty().setUnderLineSort(UnderLineSort.None);	//밑줄 종류를 설정
	        cs.getProperty().setUnderLineShape(BorderType2.Solid);	
	        cs.getProperty().setOutterLineSort(OutterLineSort.None);
	        cs.getProperty().setShadowSort(ShadowSort.None);		
	        cs.getProperty().setEmboss(false);						
	        cs.getProperty().setEngrave(false);						
	        cs.getProperty().setSuperScript(false);					
	        cs.getProperty().setSubScript(false);					
	        cs.getProperty().setStrikeLine(false);					
	        cs.getProperty().setEmphasisSort(EmphasisSort.None);	
	        cs.getProperty().setUsingSpaceAppropriateForFont(false);
	        cs.getProperty().setStrikeLineShape(BorderType2.Solid);
	        cs.getProperty().setKerning(false);						

	        cs.setShadowGap1((byte) 0);
	        cs.setShadowGap2((byte) 0);
	        cs.getCharColor().setValue(0x000000); 
	        cs.getUnderLineColor().setValue(0x00000000);
	        cs.getShadeColor().setValue(-1);			
	        cs.getShadowColor().setValue(0x00b2b2b2);
	        cs.setBorderFillId(0);

	        return hwpFile.getDocInfo().getCharShapeList().size() - 1;
	    }
	    private int createCharShapeBlackYellow(boolean bold) {    //글자 모양을 설정하는 부분을 담당
	    	CharShape cs = hwpFile.getDocInfo().addNewCharShape();
	    	// 바탕 폰트를 위한 FaceName 객체를 링크한다. (link FaceName Object for 'Batang' font.)
	    	cs.getFaceNameIds().setForAll(faceNameIndexForBatang);//글자 모양에 해당 폰트의 FaceName 정보를 설정
	    	
	    	cs.getRatios().setForAll((short) 100);		//글자의 장평 비율을 설정
	    	cs.getCharSpaces().setForAll((byte) 0);		// 글자 간의 자간을 설정
	    	cs.getRelativeSizes().setForAll((short) 100);//글자의 상대 크기를 설정
	    	cs.getCharOffsets().setForAll((byte) 0);	//글자 위치를 설정
	    	cs.setBaseSize(ptToBaseSize(12));   //글씨크기
	    	
	    	cs.getProperty().setItalic(false);							//이탤릭체
	    	cs.getProperty().setBold(bold);					//글자를 볼드체로 설정
	    	cs.getProperty().setUnderLineSort(UnderLineSort.None);	//밑줄 종류를 설정
	    	cs.getProperty().setUnderLineShape(BorderType2.Solid);	
	    	cs.getProperty().setOutterLineSort(OutterLineSort.None);
	    	cs.getProperty().setShadowSort(ShadowSort.None);		
	    	cs.getProperty().setEmboss(false);						
	    	cs.getProperty().setEngrave(false);						
	    	cs.getProperty().setSuperScript(false);					
	    	cs.getProperty().setSubScript(false);					
	    	cs.getProperty().setStrikeLine(false);					
	    	cs.getProperty().setEmphasisSort(EmphasisSort.None);	
	    	cs.getProperty().setUsingSpaceAppropriateForFont(false);
	    	cs.getProperty().setStrikeLineShape(BorderType2.Solid);
	    	cs.getProperty().setKerning(false);						
	    	
	    	cs.setShadowGap1((byte) 0);
	    	cs.setShadowGap2((byte) 0);
	    	cs.getCharColor().setValue(0x000000); 
	    	cs.getUnderLineColor().setValue(0x00000000);
	    	cs.getShadeColor().setValue(0x00FFFF);			
	    	cs.getShadowColor().setValue(0x00b2b2b2);
	    	cs.setBorderFillId(0);
	    	
	    	return hwpFile.getDocInfo().getCharShapeList().size() - 1;
	    }
	    
	    
	    
	    
	
}
