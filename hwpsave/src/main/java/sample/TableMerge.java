package sample;


import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.control.Control;
import kr.dogfoot.hwplib.object.bodytext.control.ControlTable;
import kr.dogfoot.hwplib.object.bodytext.control.ControlType;
import kr.dogfoot.hwplib.reader.HWPReader;
import kr.dogfoot.hwplib.tool.TableCellMerger;
import kr.dogfoot.hwplib.writer.HWPWriter;

public class TableMerge {
	 public static void main(String[] args) throws Exception {
	        String filename = "C:\\Temp\\"+ "sample8.hwpx";

	        HWPFile hwpFile = HWPReader.fromFile(filename);
	        if (hwpFile != null) {
	            Control control = hwpFile.getBodyText().getSectionList().get(0).getParagraph(0).getControlList().get(2);
	            // HWP 파일의 첫 번째 섹션의 첫 번째 문단에서 세 번째 컨트롤을 가져옵니다. 이 컨트롤은 표를 나타내는 것으로 가정합니다. (실제 파일 구조에 따라 인덱스가 달라질 수 있습니다.)
	            
	            if (control.getType() == ControlType.Table) {			//가져온 컨트롤이 표인지 확인
	            	//메소드시작
	            	System.out.println("병합을시작합니다");
	            	
	                ControlTable table = (ControlTable) control;
	             boolean s=   TableCellMerger.mergeCell(table, 0, 0, 1, 0);
	            	System.out.println(s);
	            	if (s == true) {						
	            		System.out.println("병합 완료");
					}else {
						System.out.println("병합 실패");						
					}
	            	
	            	
	            }else {
	            	System.out.println("표가없어영");
	            }

	            String writePath = "C:\\Temp\\"+ "sample9.hwpx";
	            HWPWriter.toFile(hwpFile, writePath);
	        }
	    }
	}