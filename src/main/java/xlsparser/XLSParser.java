package xlsparser;

import entity.Process;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class XLSParser {

    private static boolean DEBUG = false;
    private static XLSParser instance = null;
    private ArrayList<Process> processes = null;
    private HashMap<String, String> processToStageOut = null;
    private ArrayList<String> stageOutputs = new ArrayList();
    private TreeMap<String, ArrayList<Process>> procGroups = null;

    private XLSParser() {
    }

    /**
     * инициализация парсера
     *
     * @param path абсолютный путь к .xls описанию стадии
     * @return
     */
    public static XLSParser init(String path) {
        instance = null;
        InputStream in = null;
        HSSFWorkbook wb = null;
        Sheet sheet = null;
        try {
            in = new FileInputStream(path);
            wb = new HSSFWorkbook(in);
            sheet = wb.getSheetAt(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        XLSParser parser = new XLSParser();
        Iterator<Row> it = sheet.iterator();
        Process proc = null;
        int row = 0;
        int col = 0;
        String procOutputs = null;
        String curGroup = null;
        parser.setProcGroups((TreeMap<String, ArrayList<Process>>) new TreeMap());
        //parser.processes = new ArrayList<Process>();
        parser.setProcessToStageOut(new HashMap<String, String>());
        while (it.hasNext()) {
            col = 0;
            Row curRow = it.next();
            Iterator<Cell> cells = curRow.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                String val = cell.getStringCellValue();
                if (val != null && !val.isEmpty() && !val.equals("x")) {
                    if (DEBUG) {
                        System.out.println("val::" + val + "\t col::" + col + "\t proc:" + (proc != null) + "\tval:" + (val != null) + "::" + !val.isEmpty());
                    }
                }

                if (row == 0) {
                    if (val != null && !val.isEmpty()) {
                        parser.getStageOutputs().add(val);
                    }
                    col++;
                    continue;
                }

                if (col == 0 && val != null && !val.isEmpty() && val.contains("6.")) {
                    if (parser.processes != null) {
                        parser.getProcGroups().put(curGroup, parser.processes);
                    }
                    curGroup = val;
                    parser.processes = new ArrayList<Process>();
                }

                if (col == 0 && val != null && !val.isEmpty() && !val.contains("Выходы") && !val.contains(".")) {
                    if (proc != null) {
                        parser.processes.add(proc);
                    }
                    proc = new Process();
                    proc.setName(val);
                    col++;
                    continue;
                }

                if (proc != null && val != null && !val.isEmpty() && !val.toLowerCase().contains("x")) {
                    procOutputs = proc.getOutputs();
                    if (DEBUG) {
                        System.out.println("setOutputs val::" + val);
                    }
                    if (procOutputs == null) {
                        proc.setOutputs(val);
                    } else {
                        proc.setOutputs(procOutputs + ";" + val);
                    }
                }

                if (row != 0 && proc != null && val != null && val.length() == 1 && val.toLowerCase().contains("x")) {

                    String procName = proc.getName();
                    String curOut = parser.getStageOutputs().get(cell.getColumnIndex() - 9);
                    String check = null;

                    if (!parser.processToStageOut.containsKey(procName)) {
                        parser.getProcessToStageOut().put(procName, curOut);
                    } else {
                        check = parser.getProcessToStageOut().get(procName);
                        if (!check.contains(curOut)) {
                            parser.getProcessToStageOut().put(procName, check + ";" + curOut);
                        }
                    }
                }
                col++;
            }
            row++;
        }
        if (proc != null) {
            parser.processes.add(proc);
        }

        if (parser.processes != null) {
            parser.getProcGroups().put(curGroup, parser.processes);
        }

        if (DEBUG) {
            instance.printMe();
        }
        instance = parser;
        return parser;
    }

    /**
     *
     * @return список всех процессов
     */
    public ArrayList<Process> getProcesses() {
        return processes;
    }

    /**
     * реинициализация парсера и выдача процессов
     *
     * @param абсолютный путь к .xls описанию стадии
     * @return список всех процессов
     */
    public ArrayList<Process> getProcesses(String path) {
        init(path);
        return processes;
    }

    /**
     *
     * @return HashMap[название_процесса,перечень_выходов_стадии], выходы стадии
     * разделены символом ";"
     */
    public HashMap<String, String> getOuts() {
        return getProcessToStageOut();
    }

    /**
     * реинициализация парсера и выдача выходов стадии
     *
     * @param path абсолютный путь к .xls описанию стадии
     * @return HashMap[название_процесса,перечень_выходов_стадии], выходы стадии
     * разделены символом ";"
     */
    public HashMap<String, String> getOuts(String path) {
        init(path);
        return getProcessToStageOut();
    }

    /**
     * печать содержимого парсера в консоль
     */
    public void printMe() {
        System.out.println("stageOutputs:");
        for (String str : getStageOutputs()) {
            System.out.println(str);
        }

        System.out.println("\n\nprocesses:");
        for (String group : getProcGroups().keySet()) {
            System.out.println("Group: " + group + "\tsize:" + getProcGroups().get(group).size() + "\n");
            ArrayList<Process> processes = getProcGroups().get(group);
            for (Process pr : processes) {
                System.out.println(pr.getName() + "\n\toutputs:");
                System.out.println("\t\t" + pr.getOutputs());
                if (getProcessToStageOut().containsKey(pr.getName())) {
                    System.out.println("\t\tstageOuts:" + getProcessToStageOut().get(pr.getName()));
                }
            }
        }

    }

    /**
     * @return the processToStageOut
     */
    public HashMap<String, String> getProcessToStageOut() {
        return processToStageOut;
    }

    public HashMap<String, ArrayList<String>> getProcessToStageOutAsList() {
        if (processToStageOut == null) {
            return null;
        }
        HashMap pts = new HashMap();
        for (String key : processToStageOut.keySet()) {
            ArrayList<String> procs = new ArrayList<String>();
            procs.addAll(Arrays.asList(processToStageOut.get(key).split(";")));
            pts.put(key, procs);
        }
        return pts;
    }

    /**
     * @param processToStageOut the processToStageOut to set
     */
    public void setProcessToStageOut(HashMap<String, String> processToStageOut) {
        this.processToStageOut = processToStageOut;
    }

    /**
     * @return the stageOutputs
     */
    public ArrayList<String> getStageOutputs() {
        return stageOutputs;
    }

    /**
     * @param stageOutputs the stageOutputs to set
     */
    public void setStageOutputs(ArrayList<String> stageOutputs) {
        this.stageOutputs = stageOutputs;
    }

    /**
     * @return the procGroups
     */
    public TreeMap<String, ArrayList<Process>> getProcGroups() {
        return procGroups;
    }

    /**
     * @param procGroups the procGroups to set
     */
    public void setProcGroups(TreeMap<String, ArrayList<Process>> procGroups) {
        this.procGroups = procGroups;
    }

}
