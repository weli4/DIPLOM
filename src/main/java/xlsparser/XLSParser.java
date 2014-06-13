package xlsparser;

import entity.Process;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
        parser.procGroups = new TreeMap();
        //parser.processes = new ArrayList<Process>();
        parser.processToStageOut = new HashMap<String, String>();
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
                        parser.stageOutputs.add(val);
                    }
                    col++;
                    continue;
                }

                if (col == 0 && val != null && !val.isEmpty() && val.contains("6.")) {
                    if (parser.processes != null) {
                        parser.procGroups.put(curGroup, parser.processes);
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
                    String curOut = parser.stageOutputs.get(cell.getColumnIndex() - 9);
                    String check = null;

                    if (!parser.processToStageOut.containsKey(procName)) {
                        parser.processToStageOut.put(procName, curOut);
                    } else {
                        check = parser.processToStageOut.get(procName);
                        if (!check.contains(curOut)) {
                            parser.processToStageOut.put(procName, check + ";" + curOut);
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
            parser.procGroups.put(curGroup, parser.processes);
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
        return processToStageOut;
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
        return processToStageOut;
    }

    /**
     * печать содержимого парсера в консоль
     */
    public void printMe() {
        System.out.println("stageOutputs:");
        for (String str : stageOutputs) {
            System.out.println(str);
        }

        System.out.println("\n\nprocesses:");
        for (String group : procGroups.keySet()) {
            System.out.println("Group: " + group + "\tsize:"+procGroups.get(group).size()+"\n");
            ArrayList<Process> processes = procGroups.get(group);
            for (Process pr : processes) {
                System.out.println(pr.getName() + "\n\toutputs:");
                System.out.println("\t\t" + pr.getOutputs());
                if (processToStageOut.containsKey(pr.getName())) {
                    System.out.println("\t\tstageOuts:" + processToStageOut.get(pr.getName()));
                }
            }
        }

    }
}
