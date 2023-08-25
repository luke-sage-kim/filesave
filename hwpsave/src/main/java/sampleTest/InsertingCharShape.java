package sampleTest;

import java.io.File;
import java.io.UnsupportedEncodingException;

import kr.dogfoot.hwplib.object.HWPFile;
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
import kr.dogfoot.hwplib.writer.HWPWriter;

public class InsertingCharShape {
	 public static void main(String[] args) throws Exception {
	        String filename = "C:\\Temp\\sample11.hwpx";

	        HWPFile hwpFile = HWPReader.fromFile(filename);
	        if (hwpFile != null) {
	        	InsertingCharShape tmcs = new InsertingCharShape();
	            tmcs.test(hwpFile);//@@@@@

	            String writePath ="C:\\Temp\\sample12.hwpx";
	            HWPWriter.toFile(hwpFile, writePath);
	        }
	    }

	    private HWPFile hwpFile;
	    private int charShapeIndexForNormal;
	    private int charShapeIndexForBold;
	    private int faceNameIndexForBatang;

	    private InsertingCharShape() {
	        hwpFile = null;
	    }

	    private void test(HWPFile hwpFile) {
	        this.hwpFile = hwpFile;

	        faceNameIndexForBatang = createFaceNameForBatang();//바탕 폰트를 위한 FaceName 객체를 생성하고, 다양한 문자 부분에 해당 폰트를 적용하도록 설정
	        charShapeIndexForNormal = createCharShape(false);//글자 모양(CharShape) 객체를 생성하고, 해당 글자 모양을 다양한 속성으로 설정합니다. 볼드 속성에 따라 다른 글자 모양을 생성
	        charShapeIndexForBold = createCharShape(true);

	        createTestParagraph();
	    }

	    // 바탕 폰트를 위한 FaceName 객체를 생성한다.(create FaceName Object for 'Batang' font)
	    // '한글' 프로그램에서는 폰트를 적용할 문자를 6개의 부분으로 나눈다.(In 'Hangul' programs, the characters
	    // to be applied to the font are divided into six parts.)
	    private int createFaceNameForBatang() {
	        FaceName fn;

	        // 한글 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for hangul part.)
	        fn = hwpFile.getDocInfo().addNewHangulFaceName();
	        setFaceNameForBatang(fn);

	        // 영어 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for english part.)
	        fn = hwpFile.getDocInfo().addNewEnglishFaceName();
	        setFaceNameForBatang(fn);

	        // 한자 부분을 위한 FaceName 객체를 생성한다. (create FaceName Object for hanja(Chinese)
	        // part.)
	        fn = hwpFile.getDocInfo().addNewHanjaFaceName();
	        setFaceNameForBatang(fn);

	        // 일본어 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for japanse part.)
	        fn = hwpFile.getDocInfo().addNewJapaneseFaceName();
	        setFaceNameForBatang(fn);

	        // 기타 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for etc part.)
	        fn = hwpFile.getDocInfo().addNewEtcFaceName();
	        setFaceNameForBatang(fn);

	        // 기호 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for symbol part.)
	        fn = hwpFile.getDocInfo().addNewSymbolFaceName();
	        setFaceNameForBatang(fn);

	        // 사용자 정의 문자 부분을 위한 FaceName 객체를 생성한다.(create FaceName Object for user part.)
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

	    private int createCharShape(boolean bold) {    //글자 모양을 설정하는 부분을 담당
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

	    private int ptToBaseSize(int pt) {
	        return pt * 100;
	    }//포인트(pt) 크기를 HWP 파일에서 사용되는 기준 크기 단위로 변환해주는 역할

	    private void createTestParagraph() {
	        Paragraph p = hwpFile.getBodyText().getSectionList().get(0).addNewParagraph();
	        setParaHeader(p);
	        setParaText(p, "This is a Paragraph. Bold on. Bold off.");
	        setParaCharShape(p);
	        setParaLineSeg(p);
	    }

	    private void setParaHeader(Paragraph p) {
	        ParaHeader ph = p.getHeader();
	        ph.setLastInList(true);
	        // 문단 모양을 이미 만들어진 문단 모양으로 사용함
	        ph.setParaShapeId(1);
	        // 이미 만들어진 스타일으로 사용함
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

	    private void setParaText(Paragraph p, String text2) {
	        p.createText();
	        ParaText pt = p.getText();
	        try {
	            pt.addString(text2);
	        } catch (UnsupportedEncodingException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }

	    private void setParaCharShape(Paragraph p) {//단락 내에서 텍스트의 글자 모양(CharShape)을 설정하는 역할을 합니다. 특정 위치에 대한 글자 모양을 설정하여 텍스트의 스타일 변화를 구현하는데 사용
	        int paragraphStartPos = 0;
	        int boldStartPos = 20;
	        int boldEndPos = 28;

	        p.createCharShape();

	        ParaCharShape pcs = p.getCharShape();
	        pcs.addParaCharShape(paragraphStartPos, charShapeIndexForNormal);
	        pcs.addParaCharShape(boldStartPos, charShapeIndexForBold);
	        pcs.addParaCharShape(boldEndPos, charShapeIndexForNormal);
	    }

	    private void setParaLineSeg(Paragraph p) {
	        p.createLineSeg();

	        ParaLineSeg pls = p.getLineSeg();
	        LineSegItem lsi = pls.addNewLineSegItem();

	        lsi.setTextStartPosition(0);
	        lsi.setLineVerticalPosition(0);
	        lsi.setLineHeight(ptToLineHeight(11.0));
	        lsi.setTextPartHeight(ptToLineHeight(11.0));
	        lsi.setDistanceBaseLineToLineVerticalPosition(ptToLineHeight(11.0 * 0.85));
	        lsi.setLineSpace(ptToLineHeight(4.0));
	        lsi.setStartPositionFromColumn(0);
	        lsi.setSegmentWidth((int) mmToHwp(50.0));
	        lsi.getTag().setFirstSegmentAtLine(true);
	        lsi.getTag().setLastSegmentAtLine(true);
	    }

	    private int ptToLineHeight(double pt) {
	        return (int) (pt * 100.0f);
	    }

	    private long mmToHwp(double mm) {
	        return (long) (mm * 72000.0f / 254.0f + 0.5f);
	    }
	}
