package cn.cestc.os.desktop.service.impl;


import cn.cestc.os.desktop.mapper.MemberMapper;
import cn.cestc.os.desktop.mapper.PwallpaperMapper;
import cn.cestc.os.desktop.model.MemberModel;
import cn.cestc.os.desktop.model.PwallpaperModel;
import cn.cestc.os.desktop.pojo.PwallpaperUploadVO;
import cn.cestc.os.desktop.service.PwallpaperService;
import cn.cestc.os.desktop.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@Transactional(rollbackFor = Throwable.class)
public class PwallpaperServiceImpl implements PwallpaperService
{

    @Autowired
    private PwallpaperMapper pwallpaperMapper;
    @Autowired
    private MemberMapper memberMapper;

    public Integer insert(PwallpaperModel pwallpaperModel)
    {
        return pwallpaperMapper.insert(pwallpaperModel);
    }

    public Integer delete(PwallpaperModel pwallpaperModel)
    {
        return pwallpaperMapper.delete(pwallpaperModel);
    }

    public Integer updateById(PwallpaperModel pwallpaperModel)
    {
        return pwallpaperMapper.updateById(pwallpaperModel);
    }

    public List<PwallpaperModel> selectByCondition(
            PwallpaperModel pwallpaperModel)
    {
        return pwallpaperMapper.selectByCondition(pwallpaperModel);
    }

    @Override
    public PwallpaperUploadVO uploadPwallpaper(HttpServletRequest request)
    {
        PwallpaperUploadVO pwallpaperUploadVO = new PwallpaperUploadVO();
        //得到用户信息
        String userName = ServletUtils.getUserName(request);
        MemberModel memberModel = new MemberModel();
        memberModel.setUsername(userName);
        memberModel = memberMapper.selectByCondition(memberModel).get(0);

        PwallpaperModel pwallpaperModeltemp = new PwallpaperModel();
        pwallpaperModeltemp.setMemberId(memberModel.getTbid());
        List<PwallpaperModel> list = pwallpaperMapper.selectByCondition(pwallpaperModeltemp);
        if (list.size() < 6)
        {
            //上传
            String basepath = request.getSession().getServletContext().getRealPath("/");


            String separator = System.getProperties().get("file.separator").toString();
            log.debug("当前系统分隔符 {}", separator);

            String[] split = basepath.split(separator);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < split.length - 1; i++)
            {
                sb.append(split[i]).append(separator);
            }
            basepath = sb.toString();

            // String filepath = "WEB-INF/classes/static/uploads/member/" + memberModel.getTbid() + "/wallpaper/" + DateUtils.getDateByFormae("yyyyMMdd") + "/";
            String filepath = "img/member/" + memberModel.getTbid() + "/" + DateUtils.getDateByFormae("yyyyMMdd") + "/";

            String filename = new Date().getTime() + "" + new Random().nextInt(9);

            String fileLocalPath = basepath + "/" + filepath;

            log.debug("basepath :{}", basepath);
            log.debug("fileLocalPath :{}", fileLocalPath);
            String flietype = (String) FileOperationUtils.fileuploadnew(request, fileLocalPath, filename).get("flietype");

            //生成缩略图
            //原图地址
            String thumbfilePath = filepath + filename + "." + flietype;
            //缩略图地址
            String thumboutFilPath = ParamDataHandlerUtils.getFileInfo(basepath + "/" + thumbfilePath, "simg");
            String tbid = "";
            try
            {
                //得到原始图片宽高
                FileInputStream fis = new FileInputStream(basepath + "/" + thumbfilePath);
                BufferedImage bufferedImg = ImageIO.read(fis);
                int imgWidth = bufferedImg.getWidth();
                int imgHeight = bufferedImg.getHeight();
                //生成缩略图
                ImageScaleUtils.resizeFix(new File(basepath + "/" + thumbfilePath), new File(thumboutFilPath), 150, 105);
                // String path = thumbfilePath.replaceAll("WEB-INF/classes/", "");
                PwallpaperModel pwallpaperModel = new PwallpaperModel();
                pwallpaperModel.setWidth(imgWidth);
                pwallpaperModel.setHeight(imgHeight);
                pwallpaperModel.setUrl(thumbfilePath);
                pwallpaperModel.setMemberId(memberModel.getTbid());
                pwallpaperMapper.insert(pwallpaperModel);
                tbid = pwallpaperMapper.selectByCondition(pwallpaperModel).get(0).getTbid() + "";
            } catch (Exception e)
            {
                throw new RuntimeException(e.getMessage());
            }
            pwallpaperUploadVO.setTbid(tbid);
            pwallpaperUploadVO.setSurl(ParamDataHandlerUtils.getFileInfo(thumbfilePath, "simg"));
            pwallpaperUploadVO.setUrl(thumbfilePath);
            pwallpaperUploadVO.setFileType(flietype);
            pwallpaperUploadVO.setOriginal("");
            pwallpaperUploadVO.setState("success");
        } else
        {
            pwallpaperUploadVO.setState("您已经上传满6张壁纸，可以删除之后再进行上传");
        }
        return pwallpaperUploadVO;
    }

}