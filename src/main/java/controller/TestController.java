package controller;

import entity.Process;
import entity.Project;
import entity.RandomEntity;
import entity.Stage;
import entity.User;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;
import persist.ProcessDao;
import persist.ProjectDao;
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
        //System.out.println("ddd:"+new ProjectDao().addId(null));
        Project pr = RandomEntity.getRandomProject(3, 5);
        System.out.println("id 1:" + pr.getProject_id());
        service.addProject(pr);
        System.out.println("id 2:" + pr.getProject_id());
        List<Project> projects = service.getAllProjects();

        //for (Project prj : projects) {
        // Project prj = service.getProject(21);
        //  System.out.println("\n\nProject:" + prj.getName() + "\tid:" + prj.getProject_id());
        // for (Stage st : prj.getStages()) {
        //     System.out.println("\tStage:" + st.getName());
        //     for (Process proc : st.getProcess()) {
        //         System.out.println("\t\tProcess:" + proc.getName());
        //     }
        // }
        //}

        HashMap map = XLSParser.init(getResourcesPath()+"/xls/1.xls").getProcessToStageOut();
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

        TreeMap<String, ArrayList<Process>> processes = XLSParser.init(getResourcesPath()+"/xls/1.xls").getProcGroups();
        model.addObject("processes", processes);
        model.addObject("user", curUser);
        return model;
    }

    @RequestMapping("/result")
    public ModelAndView result(Project prj) {
        if (curUser == null) {
            System.out.println("workspace: login failed");
            return new ModelAndView("login");
        }
        ModelAndView model = new ModelAndView("result");
        if (prj.getStages() != null) {
            prj.setName(curUser.getUsername());
            prj.setDescription(curUser.getUsername() + new Date());
            prj.setProject_id(curUser.getProject_id());
            service.addProject(prj);
            new UserDao().delete(curUser);
            curUser.setProject_id(prj.getProject_id());
            new UserDao().add(curUser);
        }
        prj = service.getProject(curUser.getProject_id());
        for (int i = 0; i < prj.getStages().size(); i++) {
            Stage stage = prj.getStages().get(i);
            stage.setOutputs((List) XLSParser.init(getResourcesPath()+"/xls/" + (i + 1) + ".xls").getStageOutputs());
            HashMap map = XLSParser.init(getResourcesPath()+"/xls/" + (i + 1) + ".xls").getProcessToStageOutAsList();
            for (int j = 0; j < stage.getProcess().size(); j++) {
                Process process = stage.getProcess().get(j);

                if (map.containsKey(process.getName())) {
                    process.setT(true);
                    process.setOutputList((List) map.get(process.getName()));
                } else {
                    process.setT(false);
                }
            }

        }

        model.addObject("project", prj);

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
        ModelAndView model = new ModelAndView("workspace");
        TreeMap<String, ArrayList<Process>> processes = XLSParser.init(getResourcesPath()+"/xls/1.xls").getProcGroups();
        model.addObject("processes", processes);
        model.addObject("user", curUser);
        return model;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout() {
        curUser = null;
        return new ModelAndView("login");
    }
    
    private String getResourcesPath() {
        String filePath = "";
        URL url = TestController.class.getResource("TestController.class");
        String className = url.getFile();
        filePath = className.substring(0, className.indexOf("WEB-INF") + 7);
        return filePath;
    }
}
