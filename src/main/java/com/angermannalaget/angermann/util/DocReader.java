package com.angermannalaget.angermann.util;

import com.angermannalaget.angermann.controller.SongUploadController;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class DocReader {
    private static final Logger logger = LoggerFactory.getLogger(SongUploadController.class);
    public DocReader (){

    }

    public List<String> parseDocX(MultipartFile input){
        String docText = "";
        try {
            File file = new File(input.getOriginalFilename());
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(input.getBytes());
            fos.close();

            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument docx = new XWPFDocument(fis);
            XWPFWordExtractor result = new XWPFWordExtractor(docx);
            docText = result.getText();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return splitStringToArray(docText);
    }

    public List<String> parseTxt(MultipartFile filePath) {
        List<String> outPut = new ArrayList<>();
        try {
            InputStream x = filePath.getInputStream();
            outPut = new BufferedReader(
                    new InputStreamReader(x, StandardCharsets.UTF_8)) // Svensk encoder. Huzzah!!
                    .lines()
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return outPut;
    }

    public List<String> splitStringToArray(String input){
        List<String> retVal = new ArrayList<>();
        for (String line : input.split("\n|\r")){
            if (!line.trim().isEmpty())
                retVal.add(line);
        }
        return retVal;
    }

    public List<String> splitTextField(String input){
        List<String> retVal = new ArrayList<>();
        for(String line : input.split("\n")){
            retVal.add(line);
        }
        return retVal;
    }
}
