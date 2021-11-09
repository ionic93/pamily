package com.ds.pamily.controller;

import com.ds.pamily.dto.UploadResultDTO;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController //view에 관여하지 않고(리소스를 요청하지 않음) 데이터만 넘겨줌
@Log4j2
public class UploadImageController {
    @Value("${com.ds.upload.path}") //application.properties의 변수
    private String uploadPath;
    @PostMapping("/uploadAjax")
    //ResponseEntity = 내가 넘겨줄 데이터(여기서는 List<UploadResultDTO>) + HTTP에 대한 설정
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){
        List<UploadResultDTO> resultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles){
            //이미지가 아니면 못올림(MultipartFile로 검사)
            if(uploadFile.getContentType().startsWith("image") == false){
                log.warn("파일의 타입이 이미지가 아닙니다.");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            //실제 파일 이름, IE나 Edge는 전체 경로가 들어오므로
            String originalName = uploadFile.getOriginalFilename();
            // 마지막 '\' 바로 뒷부분(ex: C:\classData\workspace\like.jpg => like.jpg)
            String fileName = originalName.substring(originalName.lastIndexOf("\\")+1);
            log.info("fileName: "+fileName);

            // 날짜 폴더 생성
            String folderPath = makeFolder();
            // UUID
            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 "_"를 이용해서 구분
            String saveName = uploadPath + File.separator + folderPath + File.separator + uuid
                    + "_" + fileName;

            Path savePath = Paths.get(saveName);

            try {
                uploadFile.transferTo(savePath);

                //썸네일 생성
                String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator +
                        "s_" + uuid + "_" + fileName;
                //썸네일 파일 이름은 중간에 s_로 시작하도록
                File thumbnailFile = new File(thumbnailSaveName);
                //썸네일 생성
                Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile,100,100);

                resultDTOList.add(new UploadResultDTO(fileName,uuid,folderPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//end for
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFiles(String fileName, String size){
        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName,"UTF-8");
            log.info("fileName: "+ srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);

            log.info("LYJ: "+file.getName()+"/"+file.getParent());
            if (size !=null && size.equals("1")){
                file = new File(file.getParent(), file.getName().substring(2));
            }
            log.info("Realfile: "+file);
            log.info("size!: "+size);

            HttpHeaders headers = new HttpHeaders();

            //MIME타입 처리
            headers.add("Content-Type", Files.probeContentType(file.toPath()));
            //파일 데이터 처리
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/removeFile") //업로드 파일 삭제
    public ResponseEntity<Boolean> removeFile(String fileName){
        String srcFileName = null;
        boolean result = false;
        try {
            //원본 이미지 삭제
            srcFileName = URLDecoder.decode(fileName,"UTF-8");
            File file = new File(uploadPath + File.separator + srcFileName);
            result = file.delete(); //삭제시 true를 돌려줌

            //썸네일 이미지 삭제
            File thumbnail = new File(file.getParent(), "s_"+file.getName());
            result = thumbnail.delete(); //삭제시 true를 돌려줌

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String makeFolder() {
        //str = 업로드 날짜의 문자열
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        //folderPath = 파일 구분자
        String folderPath = str.replace("\\", File.separator);

        //make folder------
        File uploadPathFolder = new File(uploadPath, folderPath);
        if (uploadPathFolder.exists() == false){
            //디렉토리 생성
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }
}
