package cn.cestc.os.desktop.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Description:文件操作相关
 *
 * @author bo.xu
 * 2015年8月6日 下午1:46:43
 */

@Slf4j
public class FileOperationUtils
{


    /**
     * 上传
     */
    public static Map fileuploadnew(HttpServletRequest req, String filedirPath, String filename)
    {

        // 在解析请求之前先判断请求类型是否为文件上传类型
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        Map<String, Object> map = new HashMap<String, Object>();
        String fileType = "";
        Long size = 0L;
        String fileName = "";
        try
        {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1)
        {
            throw new RuntimeException(e1.getMessage());
        }

        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
        Iterator<String> iter = multiRequest.getFileNames();
        MultipartFile multipartFile = multiRequest.getFile(iter.next());

        String originalFilename = multipartFile.getOriginalFilename();

        log.info("originalFilename: {}", originalFilename);


        // 信息为文件格式
        fileName = multipartFile.getOriginalFilename();
        size = multipartFile.getSize();

        int index = fileName.lastIndexOf("\\");
        fileName = fileName.substring(index + 1);
        if (StringUtils.isNotBlank(fileName))
        {
            int i = fileName.lastIndexOf(".");
            if (i > 0)
            {
                fileType = fileName.substring(i + 1);
                fileName = fileName.substring(0, i);
            }
        }
        // 写到指定的路径中
        File filedir = new File(filedirPath);
        // 如果指定的路径没有就创建
        if (!filedir.exists())
        {
            filedir.setWritable(true, false);
            filedir.mkdirs();
        }
        File file = new File(filedirPath, filename + "." + fileType);
        //替换掉原来的文件
        if (file.exists())
        {
            file.setWritable(true, false);
            file.delete();
        }

        try
        {
            multipartFile.transferTo(file);

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        map.put("size", size);
        map.put("flietype", fileType);
        map.put("filename", fileName);
        return map;
    }


    /**
     * 上传
     */
    public static Map fileupload(HttpServletRequest req, String filedirPath, String filename)
    {
        log.info("文件路径: {}", filedirPath);
        // 在解析请求之前先判断请求类型是否为文件上传类型
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        Map<String, Object> map = new HashMap<String, Object>();
        String fileType = "";
        Long size = 0L;
        String fileName = "";
        try
        {
            req.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e1)
        {
            throw new RuntimeException(e1.getMessage());
        }
        // 文件上传处理工厂
        FileItemFactory factory = new DiskFileItemFactory();
        // 创建文件上传处理器
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("utf-8");

        // 开始解析请求信息
        List items = null;
        try
        {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
            Iterator<String> iter = multiRequest.getFileNames();
            MultipartFile multipartFile = multiRequest.getFile(iter.next());
            String originalFilename = multipartFile.getOriginalFilename();

            log.info("originalFilename: {}", originalFilename);

            items = upload.parseRequest(req);

        } catch (FileUploadException e)
        {
            throw new RuntimeException(e.getMessage());
        }
        // 对所有请求信息进行判断
        Iterator iter = items.iterator();
        while (iter.hasNext())
        {
            FileItem item = (FileItem) iter.next();
            // 信息为普通的格式
            if (item.isFormField())
            {

            } else
            {
                // 信息为文件格式
                fileName = item.getName();
                size = item.getSize();
                int index = fileName.lastIndexOf("\\");
                fileName = fileName.substring(index + 1);
                if (StringUtils.isNotBlank(fileName))
                {
                    int i = fileName.lastIndexOf(".");
                    if (i > 0)
                    {
                        fileType = fileName.substring(i + 1);
                        fileName = fileName.substring(0, i);
                    }
                }
                // 写到指定的路径中
                File filedir = new File(filedirPath);
                // 如果指定的路径没有就创建
                if (!filedir.exists())
                {
                    filedir.mkdirs();
                }
                File file = new File(filedirPath, filename + "." + fileType);
                //替换掉原来的文件
                if (file.exists())
                {
                    file.delete();
                }
                try
                {
                    item.write(file);
                } catch (Exception e)
                {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }
        map.put("size", size);
        map.put("flietype", fileType);
        map.put("filename", fileName);
        return map;
    }

    /**
     * 下载
     *
     * @throws Exception
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, String path, String fileName) throws Exception
    {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try
        {
            long fileLength = new File(path).length();
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename="
                    + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(path));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length)))
            {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }


    /**
     * 通过配置文件获取上传文件类型信息
     */
    public static Map<String, String> getUploadFileTypesMessageByProperties()
    {
        Map<String, String> map = new HashMap<String, String>();
        String uploadFileType = "rar:static/img/ui/file_zip.png,zip:static/img/ui/file_zip.png,7z:static/img/ui/file_zip.png,jpeg:static/img/ui/file_image.png,jpg:static/img/ui/file_image.png,gif:static/img/ui/file_image.png,bmp:static/img/ui/file_image.png,png:static/img/ui/file_image.png,doc:static/img/ui/file_word.png,docx:static/img/ui/file_word.png,xls:static/img/ui/file_excel.png,xlsx:static/img/ui/file_excel.png,ppt:static/img/ui/file_ppt.png,pptx:static/img/ui/file_ppt.png,pdf:static/img/ui/file_pdf.png,wma:static/img/ui/file_music.png,mp3:static/img/ui/file_music.png,txt:static/img/ui/file_txt.png";
        for (String filetypemessage : uploadFileType.split(","))
        {
            String[] filetypestr = filetypemessage.split(":");
            map.put(filetypestr[0], filetypestr[1]);
        }
        return map;
    }

    /**
     * 通过配置文件获取上传文件类型信息
     */
    public static String getUploadFileTypesByProperties(String splitstr)
    {
        String result = "";
        String uploadFileType = "rar:static/img/ui/file_zip.png,zip:static/img/ui/file_zip.png,7z:static/img/ui/file_zip.png,jpeg:static/img/ui/file_image.png,jpg:static/img/ui/file_image.png,gif:static/img/ui/file_image.png,bmp:static/img/ui/file_image.png,png:static/img/ui/file_image.png,doc:static/img/ui/file_word.png,docx:static/img/ui/file_word.png,xls:static/img/ui/file_excel.png,xlsx:static/img/ui/file_excel.png,ppt:static/img/ui/file_ppt.png,pptx:static/img/ui/file_ppt.png,pdf:static/img/ui/file_pdf.png,wma:static/img/ui/file_music.png,mp3:static/img/ui/file_music.png,txt:static/img/ui/file_txt.png";
        for (String filetypemessage : uploadFileType.split(","))
        {
            String[] filetypestr = filetypemessage.split(":");
            result += filetypestr[0] + splitstr;
        }
        return result.substring(0, result.length() - 1);
    }

}


	