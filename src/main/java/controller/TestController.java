package controller;

import entity.Process;
import entity.Project;
import entity.Stage;
import entity.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import persist.UserDao;
import service.DBServiceImpl;
import xlsparser.XLSParser;

@Controller
public class TestController {

    User curUser = null;

    @Autowired
    private DBServiceImpl service;

    @RequestMapping("/test")
    public ModelAndView test() {
        if (curUser == null) {
            System.out.println("test: login failed");
            return new ModelAndView("login");
        }
        ModelAndView model = new ModelAndView("test");

        //service.addProject(RandomEntity.getRandomProject(3, 5));
        List<Project> projects = service.getAllProjects();

        //for (Project prj : projects) {
        Project prj = service.getProject(23);
        System.out.println("\n\nProject:" + prj.getName() + "\tid:" + prj.getProject_id());
        for (Stage st : prj.getStages()) {
            System.out.println("\tStage:" + st.getName());
            for (Process proc : st.getProcess()) {
                System.out.println("\t\tProcess:" + proc.getName());
            }
        }
        //}

        //service.deleteProject(projects.get(0).getProject_id());
        /*
         projects = service.getAllProjects(); 
         for (Project prj : projects) {
         System.out.println("\n\nProject:" + prj.getName() + "\tid:"+prj.getProject_id());
         for (Stage st : prj.getStages()) {
         System.out.println("\tStage:" + st.getName());
         for (Process proc : st.getProcess()) {
         System.out.println("\t\tProcess:" + proc.getName());
         }
         }
         }*/
        return model;
    }

    @RequestMapping("/workspace")
    public ModelAndView ws() {
        if (curUser == null) {
            System.out.println("workspace: login failed");
            return new ModelAndView("login");
        }
        ModelAndView model = new ModelAndView("workspace");
        XLSParser.init("D://1.xls").printMe();
        HashMap map = XLSParser.init("D://1.xls").getOuts();
        ArrayList<entity.Process> processes = XLSParser.init("D://1.xls").getProcesses();
        model.addObject("processes", map);
        model.addObject("user", curUser);
        return model;
    }
    @RequestMapping("/result")
    public ModelAndView result(){
          ModelAndView model = new ModelAndView("result");
          return model;
    }

    @RequestMapping(value = "/login.form", method = RequestMethod.POST)
    public ModelAndView login(String uname, String pwd) {
        System.out.println("Login:" + uname + "@" + pwd);
        User user = new UserDao().getLogin(uname, pwd);
        System.out.println("user:" + user);
        if (user == null) {
            return new ModelAndView("login");
        }
        curUser = user;
        return new ModelAndView("workspace");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout() {
        curUser = null;
        return new ModelAndView("login");
    }
}
