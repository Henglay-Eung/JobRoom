package com.ksga.springboot.employeeservice.rest.restcontroller;

import com.ksga.springboot.employeeservice.configuration.BaseApi;
import com.ksga.springboot.employeeservice.rest.message.BaseApiResponse;
import com.ksga.springboot.employeeservice.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = BaseApi.BASE_API_URL)
public class FilesController {

    @Value(value = "${file.upload.server.path}")
    private String serverPath;

    @Value("${file.base.url}")
    private String imageUrl;

    @Autowired
    FilesStorageService storageService;


    //TODO: Upload files
    @RequestMapping(value = "/files/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> uploadFile(@RequestParam("files") MultipartFile[] files) {

        Map<String, Object> res = new HashMap<>();
//        StringBuilder images = new StringBuilder();
        ArrayList<String> arrayList = new ArrayList<>();

        int i=0;

        try {
            for(MultipartFile file : files)
            {
                i++;
                String fileName = storageService.save(file);

                if(i==1){
                    res.put("message","File have been saved successfully");
                    res.put("status","Ok");
                }
                arrayList.add(imageUrl+fileName);
//                images.append(imageUrl).append(fileName).append(",");
            }
//            res.put("data",images);
            String[] array = arrayList.toArray(new String[arrayList.size()]);
            res.put("data",array);
            res.put("time",new Timestamp(System.currentTimeMillis()));

            return ResponseEntity.status(HttpStatus.OK).body(res);

        } catch (Exception e) {

            res.put("message","Could not upload the file:");
            res.put("status",false);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(res);
        }
    }

    //TODO: Get Files
    @GetMapping("/files")
    public ResponseEntity<BaseApiResponse<ArrayList<String>>> getAllFiles(HttpServletResponse response) {

        try{
            BaseApiResponse<ArrayList<String>> baseApiResponse = new BaseApiResponse<>();

            Set<String> fileNames = storageService.listFilesUsingJavaIO(serverPath);

            ArrayList<String> nameWithAddress= new ArrayList<>();

            for(String string : fileNames){
                nameWithAddress.add(imageUrl+string);
            }

            baseApiResponse.setData(nameWithAddress);
            baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
            baseApiResponse.setStatus(HttpStatus.OK);
            baseApiResponse.setMessage("Files");


            return ResponseEntity.ok(baseApiResponse);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //TODO: Get File By Name
    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<BaseApiResponse<String>> getFileByName(@PathVariable String filename) {

        BaseApiResponse<String> baseApiResponse  = new BaseApiResponse<>();

        try{

            Set<String> fileNames = storageService.listFilesUsingJavaIO(serverPath);
            String nameWithAddress= "";

            for(String string : fileNames){

                if(string.equals(filename))
                    nameWithAddress = imageUrl+string;
            }

            baseApiResponse.setData(nameWithAddress);
            baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
            baseApiResponse.setStatus(HttpStatus.OK);
            baseApiResponse.setMessage("You have selected the images");

            return ResponseEntity.ok(baseApiResponse);

        }catch (Exception e){

            baseApiResponse.setData("");
            baseApiResponse.setTimestamp(new Timestamp(System.currentTimeMillis()));
            baseApiResponse.setStatus(HttpStatus.NOT_FOUND);
            baseApiResponse.setMessage("No image");

            return null;
        }
    }

}
