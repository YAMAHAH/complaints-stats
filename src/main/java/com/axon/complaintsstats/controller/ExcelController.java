package com.axon.complaintsstats.controller;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.axon.complaintsstats.listener.ExcelListener;
import com.axon.complaintsstats.model.excel.ExcelData;
import com.axon.complaintsstats.model.excel.LoanInfo;
import com.axon.complaintsstats.util.ExcelUtil;
import com.axon.complaintsstats.util.FtpFileUtil;
import com.axon.complaintsstats.util.StreamConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/excel")
public class ExcelController {


    @Autowired
    private StreamConvertUtil streamConvertUtil;

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void excel(HttpServletResponse response , HttpServletRequest request) throws Exception {
        ExcelData data = new ExcelData();
        data.setName("hello");
        List<String> titles = new ArrayList();
        titles.add("№");
        titles.add("Order number");
        data.setTitles(titles);

        List<List<Object>> rows = new ArrayList();
        List<Object> row = new ArrayList();
        row.add(1);
        row.add("LU574343255");
        rows.add(row);

        List<Object> row2 = new ArrayList();
        row2.add(2);
        row2.add("LU574343254");
        rows.add(row2);

        data.setRows(rows);

//        String basePath = request.getServletContext().getRealPath("/data");
//        System.out.println(basePath);
//        //生成本地
//        File directory = new File(basePath);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
       // File f = new File(basePath + "/test.xlsx");
       // FileOutputStream out = new FileOutputStream(f);
        OutputStream out = new ByteArrayOutputStream();
        ExcelUtil.exportExcel(data, out);
        out.close();

        InputStream xlsStream = streamConvertUtil.parse(out);

        SimpleDateFormat fdate = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = fdate.format(new Date()) + ".xlsx";
        if (FtpFileUtil.uploadFile(fileName, xlsStream)) {
            ExcelUtil.exportExcel(response, fileName, data);
        }
    }
    //ftp处理文件上传
    @RequestMapping(value="/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file,
                     HttpServletRequest request) throws IOException {
        String fileName = file.getOriginalFilename();
        InputStream inputStream=file.getInputStream();
        String filePath=null;

        Boolean flag=FtpFileUtil.uploadFile(fileName,inputStream);
        if(flag==true){
            System.out.println("ftp上传成功！");
            filePath=fileName;
        }
        return  filePath;  //该路径图片名称，前端框架可用ngnix指定的路径+filePath,即可访问到ngnix图片服务器中的图片
    }
    @Value("${web.upload-path}")
    private String webUploadPath;

    @RequestMapping(value="/uploadExcelFile", method = RequestMethod.POST)
    @ResponseBody
    public List<Object> readExcel(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        System.out.println(file.getContentType());
        InputStream inputStream = null;
        try {
           // file.transferTo(new File("d:/data/" + "a" + fileName));
            inputStream = file.getInputStream();
            //SaveFileFromInputStream(inputStream,"d:/data",fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // 解析每行结果在listener中处理
            AnalysisEventListener listener = new ExcelListener();
            ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            excelReader.read(new Sheet(1, 1, LoanInfo.class));
            return ((ExcelListener) listener).getDatas();
        }
        catch (Exception e){
            return null;
        }
        finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value="/makeExcelFile", method = RequestMethod.POST)
    public void writeExcel(HttpServletResponse response, @RequestBody List<LoanInfo> data) throws IOException {
        String fileName = ExcelUtil.genDateFileName();
        response.setHeader("content-Type", "application/vnd.ms-excel");//application/octet-stream
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        OutputStream out = response.getOutputStream(); //new FileOutputStream(webUploadPath + fileName);
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            Sheet sheet1 = new Sheet(1, 1, LoanInfo.class);
            writer.write(data, sheet1);
            writer.finish();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void SaveFileFromInputStream(InputStream stream,String path,String filename) throws IOException
    {
        FileOutputStream fs=new FileOutputStream( path + "/"+ filename);
        byte[] buffer =new byte[1024*1024];
        int bytesum = 0;
        int byteread = 0;
        while ((byteread=stream.read(buffer))!=-1)
        {
            bytesum+=byteread;
            fs.write(buffer,0,byteread);
            fs.flush();
        }
        fs.close();
        stream.close();
    }

    @RequestMapping(value = "/batchUpload", method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(HttpServletRequest request) {
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        String name=params.getParameter("name");
        System.out.println("name:"+name);
        String id=params.getParameter("id");
        System.out.println("id:"+id);
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(webUploadPath + file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => "
                            + e.getMessage();
                }
            } else {
                return "You failed to upload " + i
                        + " because the file was empty.";
            }
        }
        return "upload successful";
    }
}
