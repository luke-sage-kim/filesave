package hwpsave.hwpsave;


import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;

import kr.dogfoot.hwplib.tool.blankfilemaker.BlankFileMaker;
import kr.dogfoot.hwplib.writer.HWPWriter;




/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		try {
			
			 HWPFile hwpFile = BlankFileMaker.make();

	            Section s = hwpFile.getBodyText().getSectionList().get(0);
	            Paragraph firstParagraph = s.getParagraph(0);
	            ParaText paraText = firstParagraph.getText();
	            paraText.addString("gd");
	            paraText.addString("\n 다음줄"); // 줄바꿈 추가
	

					
	
			HWPWriter.toFile(hwpFile, "c:/temp/sample7.hwpx" );
			
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
}
