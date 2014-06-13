package entity;

import entity.Process;
import java.util.ArrayList;
import java.util.List;

public class RandomEntity {

    private static String randomStr() {
        return Math.round(Math.random() * 1000) + "";
    }

    public static Process getRandomProcess() {
        Process pr = new Process();
        pr.setDescription("Desc:" + randomStr());
        pr.setInputs("Inputs:" + randomStr());
        pr.setName("Name:" + randomStr());
        pr.setOutputs("Outputs:" + randomStr());
        pr.setWorks("Works:" + randomStr());
        return pr;
    }

    public static Stage getRandomStage(int procCnt) {
        Stage st = new Stage();
        st.setName("Stage:" + randomStr());
        List procs = new ArrayList();
        for (int i = 0; i < procCnt; i++) {
            procs.add(getRandomProcess());
        }
        st.setProcess(procs);
        return st;
    }

    public static Project getRandomProject(int stgCnt, int procCnt) {
        Project prj = new Project();
        prj.setDescription("Description:" + randomStr());
        prj.setName("Name:" + randomStr());
        List stages = new ArrayList();
        for (int i = 0; i < stgCnt; i++) {
            Stage stage = getRandomStage(procCnt);
            stage.setProject(prj);
            stages.add(stage);
        }
        prj.setStages(stages);
        return prj;
    }
}
