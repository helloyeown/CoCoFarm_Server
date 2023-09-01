package com.project.apiserver.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


import net.coobird.thumbnailator.Thumbnailator;

@Component
public class FileUploader {
    
    // custom Exception
    public static class UploadException extends RuntimeException{
        public UploadException(String msg){
            super(msg);
        }
    }

    @Value("${com.project.upload.path}")
    private String path;

    // 파일 삭제 (디비에는 남겨둠)
    public void removeFiles(List<String> fileNames){

        if(fileNames == null || fileNames.size() == 0) return;

        // 예외 처리할 땐 람다식 사용 X
        for (String fname : fileNames) {
            // 원본, 썸네일
            File original = new File(path, fname);
            File thumb = new File(path, "s_" + fname);

            if(thumb.exists()) thumb.delete();
            original.delete();
        }
    }
    
    public void removeProfile(String profileName){
        if(profileName == null || profileName.trim().length()==0) return;

        File orginal = new File (path, profileName);
        orginal.delete();
    }


    // 파일 업로드
    public List<String> uploadFiles(List<MultipartFile> files, boolean makeThumb) {

        if(files == null || files.size() == 0) {
            return new ArrayList<>();
        }

        List<String> uploadFileNames = new ArrayList<>();

        // 파일 하나씩 save
        for (MultipartFile mFile : files) {
            // 파일 이름
            String originalFileName = mFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            String saveFileName = uuid + "_" + originalFileName;
            File saveFile = new File(path, saveFileName);

            // auto close
            try (InputStream in = mFile.getInputStream(); OutputStream out = new FileOutputStream(saveFile)){
                // 실제 파일 생성
                FileCopyUtils.copy(in, out);

                // boolean 값에 따라 썸네일 생성
                if(makeThumb){
                    File thumbOutFile = new File(path, "s_" + saveFileName);
                    Thumbnailator.createThumbnail(saveFile, thumbOutFile, 200, 200);
                }

                // 파일 이름들 리스트로 담음
                uploadFileNames.add(saveFileName);

            } catch (Exception e) {
                throw new UploadException("Upload Fail " + e.getMessage());
            }
        }

        return uploadFileNames;

    }
    public String uploadProfile(MultipartFile profile){
        if( profile==null ||profile.getSize()==0) return "";

        String orginal = profile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + "_" + orginal;
        File saveFile = new File(path,saveFileName);

        try(InputStream in = profile.getInputStream(); OutputStream out = new FileOutputStream(saveFile)){
            FileCopyUtils.copy(in,out);

        }catch (Exception e){
            throw new UploadException("Upload Fail " + e.getMessage());
        }

        return saveFileName;
    }

}
