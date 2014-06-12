
package controller;

import entity.Project;
import entity.RandomEntity;
import entity.Stage;
import entity.Process;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import persist.StageDao;
import service.DBServiceImpl;

@Controller
public class TestController {
    @RequestMapping("/test")
    public ModelAndView test()
    {
        ModelAndView model=new ModelAndView("test");
        /*
        Project pr = new Project();
        pr.setDescription("ffff");
        pr.setName("aaaa");
        
        StageDao dao=new StageDao();
        Stage stage=new Stage();
        stage.setName("koko");
        stage.setName("Стадия");
        stage.setProject(pr);
        dao.add(stage);
        */
        //Project pr = RandomEntity.getRandomProject(4, 5);
        DBServiceImpl db = new DBServiceImpl();
        List<Project> projects = db.getAllProjects();
        
        
        for (Project prj:projects){
            System.out.println("Project:"+prj.getName());
            for (Stage st:prj.getStages()){
                System.out.println("Stage:"+st.getName());
                for (Process prc:st.getProcess())
                    System.out.println("Process:"+prc.getName());
            }
        }
        
        return model;
    }
     @RequestMapping("/workspace")
    public ModelAndView ws()
    {
        ModelAndView model=new ModelAndView("workspace");  
        return model;
    }
    
}
