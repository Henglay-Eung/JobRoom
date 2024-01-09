package com.admin.admin_service.controller;

import com.admin.admin_service.model.*;
import com.admin.admin_service.configuration.CommonUtils;
import com.admin.admin_service.model.*;
import com.admin.admin_service.rest.message.BaseApiResponse;
import com.admin.admin_service.rest.message.BaseApiResponseWithPage;
import com.admin.admin_service.rest.message.BaseApiResponseWithPagination;
import com.admin.admin_service.service.AdminService;
import com.admin.admin_service.service.CommentService;
import com.admin.admin_service.service.EmployeeService;
import com.admin.admin_service.service.PostService;
import com.admin.admin_service.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private PostService postService;
    private AdminService adminService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private EmployeeService employeeService;
    private CompanyService companyService;
    private FeedbackService feedbackService;

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Autowired
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String login() {
        return "page/login";
    }

    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }


    @GetMapping("/dashboard")
    public String dashbord(ModelMap modelMap) {
        modelMap.addAttribute("TotalEmployee", employeeService.getAllEmployee());
        modelMap.addAttribute("TotalComment", commentService.getAllComment());
        modelMap.addAttribute("TotalPost", postService.getAllPost());
        modelMap.addAttribute("TotalCompany",companyService.CountCompany().getBody().getData());
        modelMap.addAttribute("TotalFeedback",feedbackService.countFeedback().getBody().getData());
        return "page/dashboard";
    }

    @GetMapping(value = "/comment")
    public String comment(ModelMap modelMap) {
        System.out.println("hello from comment controller");
        BaseApiResponseWithPage<List<CommentResponse>> comments = commentService.getAllComment();
        modelMap.addAttribute("comments", comments);
        return "page/comment";
    }

    @GetMapping("/comment/{page}")
    public String getCommentByPage(ModelMap modelMap, @PathVariable(name = "page") String pageNumber)
    {
        BaseApiResponseWithPage<List<CommentResponse>> comments = commentService.getCommentByPage(pageNumber);
        modelMap.addAttribute("comments", comments);
        return "page/comment";
    }

    @RequestMapping(value = "/delete_comment/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteComment(@PathVariable Integer id)
    {
        commentService.deleteCommentById(id);
        return "redirect:/comment";
    }

    @RequestMapping("/get_single_comment/{id}")
    @ResponseBody
    public BaseApiResponse<CommentResponse> getCommentById(@PathVariable Integer id, ModelMap modelMap){
        BaseApiResponse<CommentResponse> single_comment = commentService.getCommentById(id);
        modelMap.addAttribute("single_comment", single_comment);
        System.out.println("from controller single comment " + single_comment);
        return single_comment;
    }

    @RequestMapping(value = "/set_comment_false/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String setCommentStatusFalse(@PathVariable Integer id)
    {
        commentService.setCommentStatusToFalseById(id);
        return "redirect:/comment";
    }

    @RequestMapping(value = "/set_comment_true/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String setCommentStatusTrue(@PathVariable Integer id)
    {
        commentService.setCommentStatusToTrueById(id);
        return "redirect:/comment";
    }

    @RequestMapping(value = "/find_comment/name/{name}", method = {RequestMethod.GET})
    public String findCommentByName(@PathVariable String name, ModelMap modelMap)
    {
        BaseApiResponseWithPage<List<CommentResponse>> comments = commentService.findCommentByName(name);
        modelMap.addAttribute("comments", comments);
        return "page/comment";
    }

    @GetMapping("/employee")
    public String employee(ModelMap modelMap)
    {
        BaseApiResponseWithPage<List<EmployeeResponse>> employees = employeeService.getAllEmployee();
        modelMap.addAttribute("employees", employees);
        return "page/employee";
    }

        @GetMapping("/employee/{page}")
        public String getEmployeeByPage(ModelMap modelMap, @PathVariable(name = "page") String pageNumber)
        {
            BaseApiResponseWithPage<List<EmployeeResponse>> employees = employeeService.getEmployeeByPage(pageNumber);
            modelMap.addAttribute("employees", employees);
            return "page/employee";
        }

    @RequestMapping("/get_single_employee/{id}")
    @ResponseBody
    public BaseApiResponse<EmployeeResponse> getEmployeeById(@PathVariable Integer id, ModelMap modelMap){
        BaseApiResponse<EmployeeResponse> single_employee = employeeService.getEmployeeById(id);
        modelMap.addAttribute("single_employee", single_employee);
        return single_employee;
    }

    @RequestMapping(value = "/set_employee_false/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String setEmployeeStatusFalse(@PathVariable Integer id)
    {
        employeeService.setEmployeeStatusToFalseById(id);
        return "redirect:/employee";
    }

    @RequestMapping(value = "/set_employee_true/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String setEmployeeStatusTrue(@PathVariable Integer id)
    {
        employeeService.setEmployeeStatusToTrueById(id);
        return "redirect:/employee";
    }

    @RequestMapping(value = "/find_employee/name/{name}", method = {RequestMethod.GET})
    public String findEmployeeByName(@PathVariable String name, ModelMap modelMap)
    {
        BaseApiResponseWithPage<List<EmployeeResponse>> employees = employeeService.findEmployeeByName(name);
        modelMap.addAttribute("employees", employees);
        return "page/employee";
    }

    @GetMapping("/post")
    public String getPost(ModelMap modelMap) {
        BaseApiResponseWithPage<List<PostResponse>> posts = postService.getAllPost();
        modelMap.addAttribute("posts", posts);
        return "page/posts";
    }

    @RequestMapping(value = "/delete_post/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deletePost(@PathVariable Integer id) {
        postService.deletePostById(id);
        return "redirect:/post";
    }

    //for some reason if i use @Getmapping alone, this method cant be called from javascript (error 500)
    @RequestMapping("/get_single_post/{id}")
    @ResponseBody
    public BaseApiResponse<PostResponse> getPostById(@PathVariable Integer id, ModelMap modelMap) {
        BaseApiResponse<PostResponse> single_post = postService.getPostById(id);
        modelMap.addAttribute("single_post", single_post);
        return single_post;
    }

    @RequestMapping(value = "/find_post/name/{name}", method = {RequestMethod.GET})
    public String findPostByName(@PathVariable String name, ModelMap modelMap)
    {
        BaseApiResponseWithPage<List<PostResponse>> posts = postService.findPostByName(name);
        modelMap.addAttribute("posts", posts);
        return "page/posts";
    }

    @RequestMapping(value = "/set_post_false/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String setPostStatusFalse(@PathVariable Integer id)
    {
        postService.setPostStatusToFalseById(id);
        return "redirect:/post";
    }

    @RequestMapping(value = "/set_post_true/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String setPostStatusTrue(@PathVariable Integer id)
    {
        postService.setPostStatusToTrueById(id);
        return "redirect:/post";
    }

    @GetMapping("/post/{page}")
    public String getPostByPage(ModelMap modelMap, @PathVariable(name = "page") String pageNumber) {
        BaseApiResponseWithPage<List<PostResponse>> posts = postService.getPostByPage(pageNumber);
        modelMap.addAttribute("posts", posts);
        return "page/posts";
    }

    @GetMapping("/admin/companies")
    public String getCompanyPage(ModelMap modelMap,
                                 @RequestParam(name = "page", defaultValue = "0", required = false) int pageNumber,
                                 @RequestParam(defaultValue = "", required = false) String name
    ) {

        ResponseEntity<BaseApiResponseWithPagination<List<Company>>> companies = companyService.getAllCompany(pageNumber, name);
        modelMap.addAttribute("companies", companies.getBody().getData());
        modelMap.addAttribute("pagination", companies.getBody().getPagination());
        modelMap.addAttribute("name", name);
        return "page/company";
    }

    @GetMapping("/admin/companies/view/{id}")
    public String viewCompany(@PathVariable int id, ModelMap modelMap) {

        CompanyResponse company = new CompanyResponse(1, "uuid1", "Piped Bits", "piped@gmail.com", "123"
                , "hr@pipedbits.com", "logo@logo.com", "pipedbits.com", "St322"
                , "Teuk Laak2", "Tuol Kork", "Phnom Penh", "0113355577", true, true);
        modelMap.addAttribute("companies", company);

        System.out.println(id);

        return "page/company";
    }

    @GetMapping("/admin/companies/{id}/announcements")
    public String viewAnnouncements(@PathVariable int id, ModelMap modelMap,
                                    @RequestParam(required = false, defaultValue = "") String position,
                                    @RequestParam(defaultValue = "0", required = false,name = "page") int pageNumber,
                                    @RequestParam(defaultValue = "2000-01-01",required = false) String startDate,
                                    @RequestParam(defaultValue = "2099-01-01",required = false)String endDate,
                                    @RequestParam(defaultValue = "",required = false) String open,
                                    @RequestParam(defaultValue = "",required = false) String close
                                    ) {
        CommonUtils commonUtils=new CommonUtils();
        ResponseEntity<BaseApiResponseWithPagination<List<AnnouncementResponse>>> result;
        if(open.contains("open")){
            result = companyService.getActiveAnnouncementByCompany(id, pageNumber);
            modelMap.addAttribute("announcements", result.getBody().getData());
            modelMap.addAttribute("pagination", result.getBody().getPagination());
            modelMap.addAttribute("id",id);
            modelMap.addAttribute("open",open);
            modelMap.addAttribute("isOpen",true);
            System.out.println("Open");
        }else if(close.contains("closed")){
            result = companyService.getClosedAnnouncementByCompany(id, pageNumber);
            modelMap.addAttribute("announcements", result.getBody().getData());
            modelMap.addAttribute("pagination", result.getBody().getPagination());
            modelMap.addAttribute("id",id);
            modelMap.addAttribute("closed",close);
            modelMap.addAttribute("isClosed",true);
            System.out.println("close");
        }else {
            result = companyService.getAnnouncementByCompany(id, pageNumber, position,startDate,endDate);
            modelMap.addAttribute("announcements", result.getBody().getData());
            modelMap.addAttribute("pagination", result.getBody().getPagination());
            modelMap.addAttribute("position", position);
            modelMap.addAttribute("start",startDate);
            modelMap.addAttribute("end",endDate);
            modelMap.addAttribute("id",id);
            modelMap.addAttribute("all",true);
            System.out.println("NOt");
        }

        return "page/announcement";
    }


    @GetMapping("/admin/companies/announcement/{id}")
    public String viewDetailAnnouncement(@PathVariable int id, ModelMap modelMap

    ) {
        ResponseEntity<BaseApiResponseWithPagination<AnnouncementResponse>> result=companyService.getAnnouncementById(id);
        modelMap.addAttribute("announcement",result.getBody().getData());
        modelMap.addAttribute("announcement_pdf",result.getBody().getData().getForm());
        return "page/view-detail-announcement";
    }



    @GetMapping("/admin/profile-setting/")
    public String profileSetting(){

        return "page/profile_setting";
    }



}
