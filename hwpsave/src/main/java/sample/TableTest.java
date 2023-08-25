package sample;

import java.io.File;
import java.io.UnsupportedEncodingException;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.control.ControlTable;
import kr.dogfoot.hwplib.object.bodytext.control.ControlType;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.CtrlHeaderGso;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.HeightCriterion;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.HorzRelTo;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.ObjectNumberSort;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.RelativeArrange;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.TextFlowMethod;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.TextHorzArrange;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.VertRelTo;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.gso.WidthCriterion;
import kr.dogfoot.hwplib.object.bodytext.control.ctrlheader.sectiondefine.TextDirection;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.LineChange;
import kr.dogfoot.hwplib.object.bodytext.control.gso.textbox.TextVerticalAlignment;
import kr.dogfoot.hwplib.object.bodytext.control.table.Cell;
import kr.dogfoot.hwplib.object.bodytext.control.table.DivideAtPageBoundary;
import kr.dogfoot.hwplib.object.bodytext.control.table.ListHeaderForCell;
import kr.dogfoot.hwplib.object.bodytext.control.table.Row;
import kr.dogfoot.hwplib.object.bodytext.control.table.Table;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.charshape.ParaCharShape;
import kr.dogfoot.hwplib.object.bodytext.paragraph.header.ParaHeader;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.LineSegItem;
import kr.dogfoot.hwplib.object.bodytext.paragraph.lineseg.ParaLineSeg;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;
import kr.dogfoot.hwplib.object.docinfo.BorderFill;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BackSlashDiagonalShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderThickness;
import kr.dogfoot.hwplib.object.docinfo.borderfill.BorderType;
import kr.dogfoot.hwplib.object.docinfo.borderfill.SlashDiagonalShape;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternFill;
import kr.dogfoot.hwplib.object.docinfo.borderfill.fillinfo.PatternType;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class TableTest {

    public static void main(String[] args) throws Exception {
        String filename = "C:\\Temp\\"+ "sample7.hwpx";

        HWPFile hwpFile = HWPReader.fromFile(filename);
        if (hwpFile != null) {
        	TableTest tmt = new TableTest();
            tmt.makeTable(hwpFile);
            String writePath = "C:\\Temp\\"+ "sample8.hwpx";
            HWPWriter.toFile(hwpFile, writePath);
        }

    }

    private HWPFile hwpFile;
    private ControlTable table;
    private Row row;
    private Cell cell;
    private int borderFillIDForCell;

    private int zOrder = 0;

    private void makeTable(HWPFile hwpFile2) {
        hwpFile = hwpFile2;
        createTableControlAtFirstParagraph();	//문단의 첫 번째 위치에 표 컨트롤을 생성
        setCtrlHeaderRecord();					//표 컨트롤의 속성을 설정
        setTableRecordFor2By2Cells();			// 표의 기본 속성을 설정하고 셀의 크기 및 경계선 채우기 정보를 설정합니다
        add2By2Cell();							//메서드를 호출하여 행을 추가
    }
    
    
    //메서드: 문단의 첫 번째 위치에 표 컨트롤을 생성
    private void createTableControlAtFirstParagraph() {
        Section firstSection = hwpFile.getBodyText().getSectionList().get(0);
        Paragraph firstParagraph = firstSection.getParagraph(0);

        // 문단에서 표 컨트롤의 위치를 표현하기 위한 확장 문자를 넣는다.
        firstParagraph.getText().addExtendCharForTable();

        // 문단에 표 컨트롤 추가한다.
        table = (ControlTable) firstParagraph.addNewControl(ControlType.Table);
    }
    //메서드: 표 컨트롤의 속성을 설정합니다. 위치, 크기, 정렬 등의 정보를 설정합니다.
    private void setCtrlHeaderRecord() {
        CtrlHeaderGso ctrlHeader = table.getHeader();
        ctrlHeader.getProperty().setLikeWord(false);//MS Word와 같은 형태로 표시하지 않음.
        ctrlHeader.getProperty().setApplyLineSpace(false);//줄 간격을 적용하지 않음.
        ctrlHeader.getProperty().setVertRelTo(VertRelTo.Para);//수직 위치의 기준을 문단으로 설정.
        ctrlHeader.getProperty().setVertRelativeArrange(RelativeArrange.TopOrLeft);//수직 정렬 위치를 위쪽 또는 왼쪽으로 설정.
        ctrlHeader.getProperty().setHorzRelTo(HorzRelTo.Para);//수평 위치의 기준을 문단으로 설정.
        ctrlHeader.getProperty().setHorzRelativeArrange(RelativeArrange.TopOrLeft);//수평 정렬 위치를 위쪽 또는 왼쪽으로 설정.
        ctrlHeader.getProperty().setVertRelToParaLimit(false);//수직 위치가 문단 제한을 넘어서는 것을 허용하지 않음.
        ctrlHeader.getProperty().setAllowOverlap(false);//겹쳐지는 것을 허용하지 않음.
        ctrlHeader.getProperty().setWidthCriterion(WidthCriterion.Absolute);//너비 기준을 절대 값으로 설정.
        ctrlHeader.getProperty().setHeightCriterion(HeightCriterion.Absolute);//높이 기준을 절대 값으로 설정.
        ctrlHeader.getProperty().setProtectSize(false);//크기 보호를 해제
        ctrlHeader.getProperty().setTextFlowMethod(TextFlowMethod.FitWithText);//텍스트와 함께 표 맞춤 설정.
        ctrlHeader.getProperty().setTextHorzArrange(TextHorzArrange.BothSides);//양쪽으로 텍스트 정렬.
        ctrlHeader.getProperty().setObjectNumberSort(ObjectNumberSort.Table);// 객체 번호 정렬을 테이블로 설정.
        ctrlHeader.setxOffset(mmToHwp(20.0));// X 축 위치 오프셋 설정.
        ctrlHeader.setyOffset(mmToHwp(20.0));//: Y 축 위치 오프셋 설정.
        ctrlHeader.setWidth(mmToHwp(100.0));//너비 설정.
        ctrlHeader.setHeight(mmToHwp(60.0));//높이 설정.
        ctrlHeader.setzOrder(zOrder++);// Z 순서 설정.
        ctrlHeader.setOutterMarginLeft(0);//왼쪽 외부 여백 설정.
        ctrlHeader.setOutterMarginRight(0);//오른쪽 외부 여백 설정
        ctrlHeader.setOutterMarginTop(0);//위쪽 외부 여백 설정
        ctrlHeader.setOutterMarginBottom(0);//아래쪽 외부 여백 설정
    }

    private long mmToHwp(double mm) {
        return (long) (mm * 72000.0f / 254.0f + 0.5f);
        //밀리미터 단위 값을 HWP 파일 내부에서 사용하는 단위로 변환하는 역할을 합니다.
        //HWP 파일 내에서 크기와 위치는 72000 분의 1 포인트 단위로 표현되며, 1 인치는 25.4 밀리미터입니다. 
        //따라서 밀리미터 값을 포인트 단위로 변환한 후 72000로 나누어 계산하게 됩니다. 소수점 반올림을 위해 0.5를 더해줍니다.
    }
    //메서드: 표의 기본 속성을 설정하고 셀의 크기 및 경계선 채우기 정보를 설정합니다.
    private void setTableRecordFor2By2Cells() {
        Table tableRecord = table.getTable();
        tableRecord.getProperty().setDivideAtPageBoundary(DivideAtPageBoundary.DivideByCell);//표를 페이지 경계에서 나누도록 설정.
        tableRecord.getProperty().setAutoRepeatTitleRow(false);//표의 타이틀 행을 자동 반복하지 않도록 설정.
        
        tableRecord.setRowCount(2);//표의 행 수를 2로 설정.																	@@@@@@@@@@@@@@@@@
        tableRecord.setColumnCount(3);//표의 열 수를 2로 설정.
        
        tableRecord.setCellSpacing(0);//셀 간격을 0으로 설정.
        tableRecord.setLeftInnerMargin(0);//왼쪽 내부 여백을 0으로 설정.
        tableRecord.setRightInnerMargin(0);//오른쪽 내부 여백을 0으로 설정.
        tableRecord.setTopInnerMargin(0);//위쪽 내부 여백을 0으로 설정.
        tableRecord.setBottomInnerMargin(0);//아래쪽 내부 여백을 0으로 설정.
        tableRecord.setBorderFillId(getBorderFillIDForTableOutterLine());//표의 외곽 경계선 채우기 정보 설정.
        tableRecord.getCellCountOfRowList().add(2);// 첫 번째 행에 3개의 셀이 있다고 설정.
        tableRecord.getCellCountOfRowList().add(2);//두 번째 행에도 3개의 셀이 있다고 설정.
    }

    private int getBorderFillIDForTableOutterLine() {
        BorderFill bf = hwpFile.getDocInfo().addNewBorderFill();
        bf.getProperty().set3DEffect(false);// 3D 효과를 사용하지 않음.
        bf.getProperty().setShadowEffect(false);//그림자 효과를 사용하지 않음.
        bf.getProperty().setSlashDiagonalShape(SlashDiagonalShape.None);//슬래시 대각선 모양 없음.
        bf.getProperty().setBackSlashDiagonalShape(BackSlashDiagonalShape.None);//백슬래시 대각선 모양 없음.
        bf.getLeftBorder().setType(BorderType.None);// 왼쪽 테두리 없음.
        bf.getLeftBorder().setThickness(BorderThickness.MM0_5);//왼쪽 테두리 두께를 0.5mm로 설정.
        bf.getLeftBorder().getColor().setValue(0x0);//왼쪽 테두리 색상을 검정으로 설정.
        bf.getRightBorder().setType(BorderType.None);//위쪽 테두리 없음.
        bf.getRightBorder().setThickness(BorderThickness.MM0_5);//위쪽 테두리 두께를 0.5mm로 설정.
        bf.getRightBorder().getColor().setValue(0x0);//위쪽 테두리 색상을 검정으로 설정.
        bf.getTopBorder().setType(BorderType.None);// 오른쪽 테두리 없음.
        bf.getTopBorder().setThickness(BorderThickness.MM0_5);//오른쪽 테두리 두께를 0.5mm로 설정.
        bf.getTopBorder().getColor().setValue(0x0);//오른쪽 테두리 색상을 검정으로 설정.
        bf.getBottomBorder().setType(BorderType.None);//아래쪽 테두리 없음
        bf.getBottomBorder().setThickness(BorderThickness.MM0_5);//아래쪽 테두리 두께를 0.5mm로 설정
        bf.getBottomBorder().getColor().setValue(0x0);//아래쪽 테두리 색상을 검정으로 설정.
        bf.getDiagonalBorder().setType(BorderType.None);//대각선 테두리 없음.
        bf.getDiagonalBorder().setThickness(BorderThickness.MM0_5);//대각선 테두리 두께를 0.5mm로 설정.
        bf.getDiagonalBorder().getColor().setValue(0x0);//대각선 테두리 색상을 검정으로 설정

        bf.getFillInfo().getType().setPatternFill(true);//패턴 채우기 정보를 사용하도록 설정.
        bf.getFillInfo().createPatternFill();//패턴 채우기 정보를 생성.
        PatternFill pf = bf.getFillInfo().getPatternFill();//생성된 패턴 채우기 정보 객체를 얻어옴.
        pf.setPatternType(PatternType.None);//패턴 없음
        pf.getBackColor().setValue(-1);//배경 색상을 기본 값으로 설정.
        pf.getPatternColor().setValue(0);//패턴 색상을 기본 값으로 설정.

        return hwpFile.getDocInfo().getBorderFillList().size();//로 생성된 BorderFill 객체의 ID 값을 반환
    }

    private void add2By2Cell() {						//행추가하는곳
        borderFillIDForCell = getBorderFillIDForCell();

        addFirstRow();//메서드를 호출하여 첫 번째 행을 추가합니다.
        addSecondRow();//메서드를 호출하여 두 번째 행을 추가합니다.
        addThirdRow();
        
    }

    private int getBorderFillIDForCell() {//BorderFill 객체를 생성하고 설정하여 문서의 BorderFillList에 추가한 후, 생성된 BorderFill 객체의 ID 값을 반환하는 역할
        BorderFill bf = hwpFile.getDocInfo().addNewBorderFill();
        bf.getProperty().set3DEffect(false);									//3D 효과를 비활성화
        bf.getProperty().setShadowEffect(false);								// 그림자 효과를 비활성화
        bf.getProperty().setSlashDiagonalShape(SlashDiagonalShape.None);		//슬래시 대각선 모양을 없음으로 설정
        bf.getProperty().setBackSlashDiagonalShape(BackSlashDiagonalShape.None);//백슬래시 대각선 모양을 없음으로 설정
       
        bf.getLeftBorder().setType(BorderType.Solid);							//왼쪽 테두리의 유형을 솔리드로 설정
        bf.getLeftBorder().setThickness(BorderThickness.MM0_5);					//왼쪽 테두리의 두께를 0.5mm로 설정
        bf.getLeftBorder().getColor().setValue(0x0);							//왼쪽 테두리의 색상을 검정색으로 설정
       
        bf.getRightBorder().setType(BorderType.Solid);
        bf.getRightBorder().setThickness(BorderThickness.MM0_5);
        bf.getRightBorder().getColor().setValue(0x0);
       
        bf.getTopBorder().setType(BorderType.Solid);
        bf.getTopBorder().setThickness(BorderThickness.MM0_5);
        bf.getTopBorder().getColor().setValue(0x0);
       
        bf.getBottomBorder().setType(BorderType.Solid);
        bf.getBottomBorder().setThickness(BorderThickness.MM0_5);
        bf.getBottomBorder().getColor().setValue(0x0);
      
        bf.getDiagonalBorder().setType(BorderType.None);
        bf.getDiagonalBorder().setThickness(BorderThickness.MM0_5);
        bf.getDiagonalBorder().getColor().setValue(0x0);

        bf.getFillInfo().getType().setPatternFill(true);				// 채우기 정보에 패턴 채우기를 사용하도록 설정
        bf.getFillInfo().createPatternFill();							//패턴 채우기 정보를 생성
        PatternFill pf = bf.getFillInfo().getPatternFill();				//생성된 패턴 채우기 정보 객체를 가져옵니다
        pf.setPatternType(PatternType.None);							//패턴 타입을 없음으로 설정
        pf.getBackColor().setValue(-1);									//: 배경 색상을 기본 값으로 설정
        pf.getPatternColor().setValue(0);								// 패턴 색상을 기본 값으로 설정

        return hwpFile.getDocInfo().getBorderFillList().size();
    }

    private void addFirstRow() {			//1행!
        row = table.addNewRow();	
        addLeft0Cell();			//첫번째줄이니까 왼쪽 맨위추가
        addLeft1Cell();
        addLeft2Cell();
    }

    private void addLeft0Cell() {
        cell = row.addNewCell();
        setListHeaderForCell(0, 0);
        setParagraphForCell("0,0좌표");
    }

    private void setListHeaderForCell(int colIndex, int rowIndex) {
        ListHeaderForCell lh = cell.getListHeader();
        lh.setParaCount(1);
        lh.getProperty().setTextDirection(TextDirection.Horizontal);
        lh.getProperty().setLineChange(LineChange.Normal);
        lh.getProperty().setTextVerticalAlignment(TextVerticalAlignment.Center);
        lh.getProperty().setProtectCell(false);
        lh.getProperty().setEditableAtFormMode(false);
        lh.setColIndex(colIndex);
        lh.setRowIndex(rowIndex);
        lh.setColSpan(1);
        lh.setRowSpan(1);
        lh.setWidth(mmToHwp(50.0));
        lh.setHeight(mmToHwp(30.0));
        lh.setLeftMargin(0);
        lh.setRightMargin(0);
        lh.setTopMargin(0);
        lh.setBottomMargin(0);
        lh.setBorderFillId(borderFillIDForCell);
        lh.setTextWidth(mmToHwp(50.0));
        lh.setFieldName("");
    }

    private void setParagraphForCell(String text) {
        Paragraph p = cell.getParagraphList().addNewParagraph();
        setParaHeader(p);
        setParaText(p, text);
        setParaCharShape(p);
        setParaLineSeg(p);
    }

    private void setParaHeader(Paragraph p) {		//setParagraphForCell(1)
        ParaHeader ph = p.getHeader();
        ph.setLastInList(true);
        // 셀의 문단 모양을 이미 만들어진 문단 모양으로 사용함
        ph.setParaShapeId(1);
        // 셀의 스타일을이미 만들어진 스타일으로 사용함
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

    private void setParaText(Paragraph p, String text2) {	//setParagraphForCell(2)
        p.createText();
        ParaText pt = p.getText();
        try {
            pt.addString(text2);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void setParaCharShape(Paragraph p) {		//setParagraphForCell(3)
        p.createCharShape();

        ParaCharShape pcs = p.getCharShape();
        // 셀의 글자 모양을 이미 만들어진 글자 모양으로 사용함
        pcs.addParaCharShape(0, 1);
    }


    private void setParaLineSeg(Paragraph p) {			////setParagraphForCell(4)
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

    private int ptToLineHeight(double pt) {
        return (int) (pt * 100.0f);
    }

    
    private void addLeft1Cell() {
        cell = row.addNewCell();
        setListHeaderForCell(1, 0);
        setParagraphForCell("왼쪽에서 두번째 1,0");
    }
    
    private void addLeft2Cell() {
    	cell = row.addNewCell();
    	setListHeaderForCell(2, 0);
    	setParagraphForCell("왼쪽에서 세번째 2,0");
    }
    
    
    
    

    private void addSecondRow() {		//2행!
        row = table.addNewRow();
        add2Left1Cell();
        add2Left2Cell();
        add2Left3Cell();
    }

    private void add2Left1Cell() {
        cell = row.addNewCell();
        setListHeaderForCell(0, 1);
        setParagraphForCell("2행 1열 셀");
    }

    private void add2Left2Cell() {
        cell = row.addNewCell();
        setListHeaderForCell(1, 1);
        setParagraphForCell("2행 2열 셀");
    }
    private void add2Left3Cell() {
    	cell = row.addNewCell();
    	setListHeaderForCell(2, 1);
    	setParagraphForCell("2행 3열셀");
    }
    
    
    
    private void addThirdRow() {		//3행!
    	row = table.addNewRow();
    	add3Left1Cell();
    	add3Left2Cell();
    	add3Left3Cell();
    }
    
    private void add3Left1Cell() {
    	cell = row.addNewCell();
    	setListHeaderForCell(0, 2);
    	setParagraphForCell("3행 1열 셀");
    }
    
    private void add3Left2Cell() {
    	cell = row.addNewCell();
    	setListHeaderForCell(1, 2);
    	setParagraphForCell("3행 2열 셀");
    }
    private void add3Left3Cell() {
    	cell = row.addNewCell();
    	setListHeaderForCell(2, 2);
    	setParagraphForCell("3행 3열셀");
    }
    
    
    
    
    
    
    
}
