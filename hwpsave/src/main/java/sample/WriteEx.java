package sample;
import java.io.UnsupportedEncodingException;

import kr.dogfoot.hwplib.object.HWPFile;
import kr.dogfoot.hwplib.writer.HWPWriter;
import kr.dogfoot.hwplib.object.bodytext.Section;
import kr.dogfoot.hwplib.object.bodytext.paragraph.Paragraph;
import kr.dogfoot.hwplib.object.bodytext.paragraph.text.ParaText;

public class WriteEx {
    public static void main(String[] args) {
        String sentence = "안녕하세요, 한글 파일 생성 예제입니다.";

        HWPFile hwpFile = new HWPFile();

        // 한글 파일 생성 작업 수행
		addTextToHWPFile(hwpFile, sentence);

		String outputPath = "C:\\Temp\\output.hwp";
		saveHWPFile(hwpFile, outputPath);

		System.out.println("한글 파일이 생성되었습니다: " + outputPath);
    }

    private static void addTextToHWPFile(HWPFile hwpFile, String text) {
        if (hwpFile.getBodyText() != null) {
            Section section = hwpFile.getBodyText().addNewSection();
            if (section != null) {
                Paragraph paragraph = section.addNewParagraph();
                if (paragraph != null) {
                    ParaText paraText = paragraph.getText();
                    if (paraText != null) {
                        try {
                            paraText.addString(text);
                        } catch (Exception e) {
                            System.out.println("오류발생");
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("ParaText 객체가 null입니다.");
                    }
                } else {
                    System.out.println("Paragraph 객체가 null입니다.");
                }
            } else {
                System.out.println("Section 객체가 null입니다.");
            }
        } else {
            System.out.println("BodyText 객체가 null입니다.");
        }
    }

    private static void saveHWPFile(HWPFile hwpFile, String outputPath) {
        try {
            HWPWriter.toFile(hwpFile, outputPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
