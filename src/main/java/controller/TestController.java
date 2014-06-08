
package controller;

import entity.Stage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import persist.StageDao;

@Controller
public class TestController {
    @RequestMapping("/test")
    public ModelAndView test()
    {
        ModelAndView model=new ModelAndView("test");
        StageDao dao=new StageDao();
        Stage stage=new Stage();
        stage.setName("Стадия");
        dao.add(stage);
        return model;
    }
     @RequestMapping("/workspace")
    public ModelAndView ws()
    {
        ModelAndView model=new ModelAndView("workspace");  
        return model;
    }
    
}
