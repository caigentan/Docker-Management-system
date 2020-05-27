package com.caigen.graduationproject.controller;

import com.caigen.graduationproject.dao.*;
import com.caigen.graduationproject.model.*;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.plugin2.main.client.MozillaServiceDelegate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Yasin
 * @version 1.0
 * @date 2020-02-17 20:53
 * @description 地址直接访问页面跳转
 */

@Controller
public class RouteController {
    public static final Logger LOG = LoggerFactory.getLogger(RouteController.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserInfoDao userInfoDao;
    @Autowired
    GetImagesDao getImagesDao;
    @Autowired
    GetExitedContainersDao getExitedContainersDao;
    @Autowired
    GetAllContainersDao getAllContainersDao;
    @Autowired
    BaseSqlDao baseSqlDao;
    @Autowired
    AddNewUser addNewUser;
    @Autowired
    private WriteBoardDao writeBoardDao;
    @Autowired
    CommonImagesDao commonImagesDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping({"/","/login","/index"})
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/main")
    public String toMain(Model model, HttpSession session){
        GetNumInfo getNumInfo = new GetNumInfo();
        List<ImageBean> imageInfo = getImagesDao.getImageImfo();
        List<ContainerBean> exitedContainerInfo = getExitedContainersDao.getExitedContainers();

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserInfoBean userinfo = userInfoDao.getUserinfo(name);

        session.setAttribute("img",userinfo.getImg());
        System.out.println(name);
        model.addAttribute("dockerNumInfo", getNumInfo.getAllWithMap());
        model.addAttribute("imageInfo",imageInfo);
        model.addAttribute("exitedContainerInfo",exitedContainerInfo);
        //重定向次数过多
        LOG.info("login success! Username:{}",SecurityContextHolder.getContext().getAuthentication().getName());
        return "drashboard";
    }

    //镜像详情
    @RequestMapping("/imginfo/{imagename}")
    public String imageInfo(@PathVariable("imagename") String imagename,Model model){
        Map<String,Object> imageinfo = baseSqlDao.getImageinfo(imagename);
        String describe = "";
        String iname = "";
        try{
            iname = (String)imageinfo.get("imagename");
            describe = (String)imageinfo.get("imageinfo");
            LOG.info("Inspect imageinfo with imageName={}",imagename);
        }catch (NullPointerException e){
            LOG.error("Inspect imageinfo with imageName={} failed,Reason:NullPointerException expect 1 actual 0",imagename);
            System.out.println(describe);
        }
        model.addAttribute("describle",describe);
        model.addAttribute("iname",iname);

        LOG.info("Success,to imageinfo.heml");
        return "imginfo";
    }

    @RequestMapping("/imagelist")
    public String imageList(){
        LOG.info("See imageList!");
        return "imagelist";
    }

    @RequestMapping(value = "/containerlist",method = RequestMethod.GET)
    public String continerList(){
        LOG.info("See containerList!");
        return "containerlist";
    }

    @RequestMapping({"/startcontainer/{id}"})
    public boolean startContainer(
            @PathVariable("id")String id,
            @RequestParam(value="cl")
            @NotNull(message = "获取容器id或容器名失败，请重试") String identif
    ){
        LOG.info("Start Container with ID:{},Success",id);
        return true;
    }

    /**
     * @description 根据Dockerfile来build一个镜像
     */
    @RequestMapping(value = {"/buildimage"},method = RequestMethod.GET)
    public String buildImage(){
        LOG.info("to buildimage page");
        return "buildimage";
    }

    @RequestMapping("/createC")
    public String createC(@RequestParam(value = "id",defaultValue = "") String id,Model model){
        System.out.println(id);
        model.addAttribute("imageid",id);
        LOG.info("Create new container with imageid {}",id);
        return "newContainer";

    }

    /**
     * 根据容器id获得某个容器的详细配置信息
     * @return
     */
    @RequestMapping(value = {"/container/{id}/inspect"},method = RequestMethod.GET)
    public String containerInfo(@PathVariable(value = "id") String id,Model model){
        InspectContainerDao icd = new InspectContainerDao();
        String jsoninfo = icd.inspectContainer(id);
        model.addAttribute("jsoninfo",jsoninfo);
        LOG.info("get inspectContainer info with container ID:{}",id);
        return "containerinfo";
    }
    /**
     * @description 容器状态信息
     */
    @RequestMapping(value = {"/container/{id}"},method = RequestMethod.GET)
    public String containerSatus(@PathVariable(value = "id")String containerId,Model model){
        Map<String,Map<String,Object>> stusAndDetails = new GetContainerStatusById().getContainerStatus(containerId);
        Map<String,Object> status = stusAndDetails.get("status");
        Map<String,Object> details = stusAndDetails.get("details");
        model.addAttribute("status",status);
        model.addAttribute("details",details);
        LOG.info("Inspect Container Info with container ID:{}",containerId);
        return "containerStatus";
    }

    /**
     * @description 异步刷新表单数据，使用thymeleaf而不是ajax
     *
     */
    @RequestMapping({"/refreshIList"})
    public String reImageList(Model model){
        List<ImageBean> imagelist = getImagesDao.getImageImfo();
        model.addAttribute("imagelist",imagelist);
        //System.out.println(imagelist.get(0).getImageName());
        LOG.info("Refresh Image table data with Thymeleaf");
        return "imagelist::ilist";
    }

    @RequestMapping({"/getAllContainerList"})
    public String getAllContainerList(Model model){
        List<ContainerBean> allContainersInfo = getAllContainersDao.getAllContainers();
        model.addAttribute("allContainers",allContainersInfo);
        LOG.info("Refresh Container table data with Thymeleaf");
        return "containerlist::aclist";
    }

    @RequestMapping({"/userinfo/{name}"})
    public String  getUserInfo(@PathVariable("name") String userName,Model model){

        UserInfoBean userinfo = userInfoDao.getUserinfo(userName);
        model.addAttribute("userInfo",userinfo);
        LOG.info("{} Inspect his Info",userName);
        return "userinfo";
    }

    @RequestMapping(value = {"/saveUserinfo"},method = RequestMethod.POST)
    public String saceUserInfo(HttpServletRequest request){

        String userName = request.getParameter("userName");
        String img = request.getParameter("img");
        String profile = request.getParameter("profile");
        String email = request.getParameter("email");
        System.out.println(userName);
        userInfoDao.setUserinfo(userName,img,profile,email);
        LOG.info("{} modify his profile success",userName);
        return "redirect:/main";
    }

    @RequestMapping(value = {"/logInfo"})
    public String logInfo(){

        return "log";
    }

    @RequestMapping({"/addNewUser"})
    public String addUser(){

        return "addNewUser";
    }
    @RequestMapping(value = {"/submitAdd"},method = RequestMethod.POST)
    public String submitAdd (
            @RequestParam("userName") String userName,
            @RequestParam("confirmPassword") String password,
            @RequestParam("role") String authority
    ){

        String tm = Long.toString(System.currentTimeMillis());
        String id = tm.substring(5,tm.length()-1);
        password = new BCryptPasswordEncoder().encode(password);
        Object[] params = new Object[]{id,userName,password,1};
        addNewUser.addNewUser(BaseSqlDao.NEWUSER_SQL,params);

        String img = "01.png";
        String role = "用户";
        if("1".equals(authority)){
            role = "管理员";
        }
        Object[] params1 = new Object[]{id,img,role};
        addNewUser.addUserInfo(BaseSqlDao.NEWUSER_INFO,params1);

        Object[] params2 = new Object[]{id,authority};
        addNewUser.userRole(BaseSqlDao.USER_ROLE,params2);
        return "redirect:/main";
    }

    @RequestMapping(value = {"/writeBoard"})
    public String feedBack( Model model){
        List<WriteBoardBean> writeBoardBeanList =writeBoardDao.findAllMessages();
        model.addAttribute("messages",writeBoardBeanList);
        return "writeboard";
    }

    @RequestMapping(value = {"/suggest"},method = RequestMethod.GET)
    public String suggest(){
        return "suggest";
    }

    @RequestMapping(value = {"/commonImages"},method = RequestMethod.GET)
    public String commonImages(Model model){
        List<CommonImagesBean> commonImagesBeanList = commonImagesDao.findAllCommonImages();
        model.addAttribute("images",commonImagesBeanList);
        return "commonImages";
    }

    @RequestMapping(value = "/containerLog/{id}",method = RequestMethod.GET)
    public String containerLog(@PathVariable("id")String id,Model model){
        List<String> logList = new ContainerLogDao().getContaienrLog(id);
        model.addAttribute("logList",logList);
        return "containerLog";
    }

    @RequestMapping(value = {"/notice"},method = RequestMethod.GET)
    public String Notice(Model model){
        Map<String,Object> notice = baseSqlDao.getNotice();
        model.addAttribute("notice",notice);
        return "notice";
    }

    @RequestMapping(value = {"/userlist"},method = RequestMethod.GET)
    public String userList(Model model){
        List<UserBean> users = userDao.findAllMessages();
        model.addAttribute("users",users);
        return "userList";
    }

    @RequestMapping(value = {"systemStatus"},method = RequestMethod.GET)
    public String systemStatus(){

        return "systemStatus";
    }
}
