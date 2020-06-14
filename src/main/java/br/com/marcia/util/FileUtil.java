package br.com.marcia.util;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileUtil {

    /**
     * Converte um arquivo MultipartFile em HttpEntity<MultiValueMap<String, Object>> para ser enviado em um form-data.
     *
     * @param file
     * @param nome: o nome que será submetido no form
     * @return
     * @throws IOException
     */
    public static HttpEntity<MultiValueMap<String, Object>> converterMultipartFile(MultipartFile file, String nome) throws IOException {

        // header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // body
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name(nome)
                .filename(file.getName())
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>(file.getBytes(), fileMap);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);

        return new HttpEntity<>(body, headers);
    }
}
