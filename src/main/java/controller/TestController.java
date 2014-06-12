package controller;

import entity.Process;
import entity.Project;
import entity.RandomEntity;
import entity.Stage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import persist.ProcessDao;
import persist.ProjectDao;
import persist.StageDao;
import service.DBServiceImpl;
import xlsparser.XLSParser;

@Controller
public class TestController {

    @Autowired
    private DBServiceImpl service;

    @RequestMapping("/test")
    public ModelAndView test() {
        ModelAndView model = new ModelAndView("test");

        //service.addProject(RandomEntity.getRandomProject(3, 5));
        
        List<Project> projects = service.getAllProjects();

        //for (Project prj : projects) {
        Project prj = service.getProject(23);
            System.out.println("\n\nProject:" + prj.getName() + "\tid:"+prj.getProject_id());
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
        ModelAndView model = new ModelAndView("workspace");
        XLSParser.init("E://1.xls").printMe();
        HashMap map = XLSParser.init("E://1.xls").getOuts();
        ArrayList<entity.Process> processes = XLSParser.init("E://1.xls").getProcesses();
        model.addObject("processes", map);
        return model;
    }

}
