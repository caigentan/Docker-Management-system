package com.caigen.graduationproject.controller;

import com.caigen.graduationproject.comhandle.ComHandle;
import com.caigen.graduationproject.dao.*;
import com.caigen.graduationproject.impls.DockerInit;
import com.caigen.graduationproject.terminal.TerminalSSH2;
import com.github.dockerjava.api.command.PullImageCmd;
import com.github.dockerjava.api.exception.DockerClientException;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.core.InvocationBuilder;
import com.github.dockerjava.core.command.PullImageResultCallback;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Yasin on 2020-03-16 20:40
 */

@RestController
public class ResponseBodyController extends ComHandle implements DockerInit {
    public static final Logger LOG = LoggerFactory.getLogger(ResponseBodyController.class);
    @Autowired
    ReadLogDao readLogDao;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    BaseSqlDao baseSqlDao;
    @Autowired
    SearchImages searchImages;
    @Autowired
    SuggestDao suggestDao;
    @Autowired
    JdbcTemplate jdbcTemplate;
    //ajax处理提交刷新

    @PostMapping("/successEdit")
    public String editimageInfo(HttpServletRequest request, HttpSession session){

        String imagename = request.getParameter("imagename");
        String editinfo = request.getParameter("editinfo");
        baseSqlDao.updateImageInfo(imagename,editinfo);
        LOG.info("edit {} imageinfo success",imagename);
        return editinfo;
    }

    @RequestMapping("/searchImage")
    public String searchImage(
            @RequestParam(value = "q",defaultValue = "hello-world")
            @NotNull(message = "搜索关键字不可以为空") String searchName){
        String text = null;
        try {
            Document doc = Jsoup.connect("https://index.docker.io/v1/search?q="+searchName)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)")
                    .get();
            Element link = doc.select("body").first();
            text = link.text();
            LOG.info("search image:",searchName);
        } catch (Exception e) {
            LOG.error("search {} failed",searchName);
            text = null;
        }
        return text;
    }

    @RequestMapping(value = {"/getLog"},method = RequestMethod.GET)
    public String getLog(){
        return readLogDao.getLog();
    }

    /**
     * @Description:启动容器
     * @From:containerList.html by ajax,post
     */

    /**
     * @Description:下载容器
     * @From：imageList.html by ajax,post
     */

    @PostMapping("/pullimage")
    public String pullImage(
            @RequestParam(value = "imagename") String imagename){
        PullImageCmd pullImageCmd = dockerClient.pullImageCmd(imagename);
        try {
            pullImageCmd.withTag("latest").exec(new PullImageResultCallback() {
                @Override
                public void onNext(PullResponseItem item) {
                    super.onNext(item);
                }
            }).awaitCompletion();
            LOG.info("Pullimage {} success",imagename);
        } catch (InterruptedException va1) {
            LOG.error("Pull image {} failed,Reason:InterruptedException",imagename);
            va1.printStackTrace();
            return "error";
        } catch (NotFoundException va2) {
            LOG.error("Pull image {} failed,Reason:NotFoundException",imagename);
            va2.printStackTrace();
            return "not found";
        }
        return "success";
    }

    /**
     * @Description:启动镜像，放入容器队列，
     * 前端 ajax 传入要启动的镜像列表：checkimage
     */

    @PostMapping({"/createNewContainerByImage"})
    public String createNewContainerByImage(
            @RequestParam(value = "createJson") String createJson
    ) {
        LOG.info("try createNewContaineByImage....");
        String cmd = "docker run -d --name ";
        JSONArray createParamJSON = JSONArray.fromObject(createJson);
        JSONObject jsonOne;
        Map<String, Object> map = null;
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        jsonOne = createParamJSON.getJSONObject(0);
        String imageid = (String)jsonOne.get("imageid");
        cmd += (String) jsonOne.get("containername");
        if (jsonOne.get("auto") != null) {
            cmd = cmd + " -P";
        } else {
            if(!"".equals(jsonOne.get("public-port"))) {
                cmd = cmd + " -p " + jsonOne.get("public-port") + ":" + jsonOne.get("docker-port");
            }
        }
        if (jsonOne.get("proset") != null) {
            String memory = (String) jsonOne.getJSONObject("proset").get("memory");
            String cpu = (String) jsonOne.getJSONObject("proset").get("cpu");
            String networkmode = (String) jsonOne.getJSONObject("proset").getJSONObject("networkset").get("mode");
            String hostfile = (String) jsonOne.getJSONObject("proset").get("hostfile");
            String containerfile = (String) jsonOne.getJSONObject("proset").get("containerfile");
            String addon = (String) jsonOne.getJSONObject("proset").get("addon");
            cmd = !"".equals(memory) ? (cmd + " -m=" + memory+"M") : cmd;
            cmd = !"".equals(cpu) ? (cmd + " --cpus=" + cpu) : cmd;
            if ("container".equals(networkmode)) {
                cmd += " –net=container:" + jsonOne.getJSONObject("proset").getJSONObject("networkset").get("bindcontainer");
            }
            if (!"".equals(hostfile) && !"".equals(containerfile)) {
                cmd += " -v " + hostfile + ":" + containerfile;
            }

            if (!"".equals(addon)) {
                cmd += " -c " + addon;
            }
        }
        cmd =cmd + " "+imageid;
        System.out.println(cmd);
        TerminalSSH2 terminalSSH2 = new TerminalSSH2();
        terminalSSH2.createContainerBackId(cmd);
        LOG.info("createNewContaineByImage Success,With CMD:{}",cmd);
        return "success";
    }

    /**
     * 动态获取CPU占用，返回前端交付ajax刷新
     */

    /*@GetMapping("/cpustatus")
    public Long getCpuStatus(@RequestParam("id")String containerId){
        InvocationBuilder.AsyncResultCallback<Statistics> callback = new InvocationBuilder.AsyncResultCallback<>();
        System.out.println(containerId);
        dockerClient.statsCmd(containerId).exec(callback);
        Statistics stats = null;
        long cs = 0L;
        try {
            stats = callback.awaitResult();
            callback.close();
            cs = stats.getCpuStats().getCpuUsage().getTotalUsage()/(1024*1024*1024);
        } catch (RuntimeException | IOException e) {
            // TODO
            e.printStackTrace();
            return 0L;
        }
        return cs;
    }*/

    @GetMapping({"/StatusWithChart"})
    public List<Object> getMemoryStatus(@RequestParam("id") String containerId){
        InvocationBuilder.AsyncResultCallback<Statistics> callback = new InvocationBuilder.AsyncResultCallback<>();
        dockerClient.statsCmd(containerId).exec(callback);
        List<Object>  cmList = new ArrayList<>();
        Statistics stats = null;
        double ms = 0d;
        long cs = 0L;
        try {
            stats = callback.awaitResult();
            callback.close();
            ms = stats.getMemoryStats().getUsage()*1.0/(1024.0*1024.0);
            cs = stats.getCpuStats().getCpuUsage().getTotalUsage()/(1024*1024*1024);
            cmList.add(cs);
            cmList.add(ms);
        } catch (RuntimeException | IOException e) {
            LOG.info("StatusWithChart Failed,Reason:RuntimeException | IOException");
            return cmList;
        }
        System.out.println(ms);
        return cmList;
    }

    @RequestMapping({"/buildImageByDockerfile"})
    public String buildImageByDockerfile(
            @RequestParam("dockerfile") String content,
            @Nullable @RequestParam("tag") String tag
    ){
        System.out.println(content);
        UploadDockerFileDao udfd = new UploadDockerFileDao(content);
        BuildImageByDockerfileDao bibdf = new BuildImageByDockerfileDao();
        LOG.info("Congratulation,buildImageByDockerfile Success!");
        return bibdf.BuildImageByDockerfileDao(tag);
    }

    @RequestMapping(value = {"/commitSuggest"},method = RequestMethod.POST)
    public String commitSuggest(
            @RequestParam("title")String title,
            @RequestParam("context")String content
    ){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        String sql = BaseSqlDao.COMMIT_SUGGEST;
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Object[] params = new Object[]{title,content,userName,date};
        int i = suggestDao.Commit(sql,params);
        if(i>0){
            return "success";
        }else {
            return "error";
        }
    }

    @RequestMapping(value = {"/delMessage"},method = RequestMethod.POST)
    public String delMessage(
            @RequestParam("id") String id
    ){
        System.out.println(1);
        String sql = BaseSqlDao.DEL_MESSAGE_BY_ID;
        Object[] params = new Object[]{Integer.parseInt(id)};
        int i = suggestDao.Delete(sql,params);
        if(i>0){
            return "success";
        }else{
            return "error";
        }
    }

    @RequestMapping(value = {"/downComImage"},method = RequestMethod.POST)
    public String downComImage(
         @RequestParam("imageName")String imageName
    ){
        PullImageCmd pullImageCmd = dockerClient.pullImageCmd(imageName);
        try {
            pullImageCmd.withTag("latest").exec(new PullImageResultCallback() {
                @Override
                public void onNext(PullResponseItem item) {
                    super.onNext(item);
                }
            }).awaitCompletion();
            LOG.info("Pullimage {} success",imageName);
        } catch (InterruptedException va1) {
            LOG.error("Pull image {} failed,Reason:InterruptedException",imageName);
            va1.printStackTrace();
            return "error";
        } catch (NotFoundException va2) {
            LOG.error("Pull image {} failed,Reason:NotFoundException",imageName);
            va2.printStackTrace();
            return "not found";
        } catch (DockerClientException va3){
            LOG.error("docker client timeout!");
            return "docker client timeout";
        }

        return "success";
    }

    @RequestMapping(value = {"/startC"},method = RequestMethod.POST)
    public String startC(
            @RequestParam("id")String id
    ){
        System.out.println(id);
        dockerClient.startContainerCmd(id).exec();
        return "success";
    }

    @RequestMapping(value = {"/removeI"},method = RequestMethod.POST)
    public String removeI(
            @RequestParam(value = "imageId")String id
    ){
        RemoveDao rd = new RemoveDao();
        rd.removeImages(id);
        System.out.println(id);
        return "success";
    }

    @RequestMapping(value = {"/removeC"},method = RequestMethod.POST)
    public String removeC(
            @RequestParam("containerId")String id
    ){
        RemoveDao rd = new RemoveDao();
        rd.removeContainers(id);

        return "success";
    }

    @RequestMapping(value = {"/stopC"},method = RequestMethod.POST)
    public String stopC(
            @RequestParam(value = "containerId")String id
    ){
        DockerInit.dockerClient.stopContainerCmd(id).exec();

        return "success";
    }

    @RequestMapping(value = {"/noticeSuccess"},method = RequestMethod.POST)
    public String updateNotice(
            @RequestParam(value = "id")String id,
            @RequestParam(value = "content")String content,
            @RequestParam(value = "date")String date
    ){
        baseSqlDao.updateNotice(id,content,date);
        return content;
    }

    @RequestMapping(value = {"/banUser"},method = RequestMethod.POST)
    public String banUser(
            @RequestParam(value = "userId")String userId){
        String sql = "update system_user set Enabled=0 where user_id=?";
        Object[] params = new Object[]{userId};
        jdbcTemplate.update(sql,params);
        return "success";
    }

    @RequestMapping(value = {"/recoverUser"},method = RequestMethod.POST)
    public String recoverUser(
            @RequestParam(value = "userId")String userId
    ){
        String sql = "update system_user set Enabled=1 where user_id=?";
        Object[] params = new Object[]{userId};
        jdbcTemplate.update(sql,params);
        return "success";
    }

    @RequestMapping(value = {"/removeUser"},method = RequestMethod.POST)
    public String rmoveUser(
            @RequestParam(value = "userId")String userId
    ){
        String sql1 = "delete from system_user where USER_ID=?";
        String sql2 = "delete from user_role where USER_ID=?";
        String sql3 = "delete from user_info where USER_ID=?";
        Object[] params = new Object[]{userId};
        try {
            jdbcTemplate.update(sql3,params);
            jdbcTemplate.update(sql1,params);
            jdbcTemplate.update(sql2,params);

        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(1211);
        return "success";
    }

    @RequestMapping(value = {"/containerDayNum"},method = RequestMethod.GET)
    public List<Object> containerDayNum(){
        final String sql = "select count from container_count";
        List<Object> containerStatus = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for(Map<String,Object> map : maps){
            containerStatus.add(map.get("count"));
        }
        System.out.println(containerStatus);
        return containerStatus;
    }
}
