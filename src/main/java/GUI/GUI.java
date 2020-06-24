package GUI;

import com.vtse.app.verification.FunctionVerification;
import com.vtse.app.verification.report.DefineFun;
import com.vtse.app.verification.report.VerificationReport;
import com.vtse.cfg.build.ASTFactory;
import com.vtse.cfg.build.VtseCFG;
import com.vtse.cfg.node.CFGNode;
import com.vtse.graph.GraphGenerator;
import com.vtse.visualize.AddMoreInformation;
import com.vtse.visualize.PathExecutionVisualize;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import helper.ImageResizer;
import helper.IsNumberic;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GUI extends JFrame{
    private JButton buttonGenerate;
    private JTextArea textCodeArea;
    private JCheckBox checkBoxSync;
    private JCheckBox checkBoxDetail;
    private JPanel panelEDT;
    private JPanel panelCode;
    private JPanel panelOption;
    private JPanel panelImage;
    private JPanel panelButton;
    private JLabel imageLabel;
    private JTextField textPostCondition;
    private JTextField textPreCondition;
    private JLabel labelPrecondition;
    private JLabel labelPostCondition;
    private JList listCounterExample;
    private JTextField textLoopCount;
    private JLabel labelLoopCount;
    private JScrollPane imagePanel;
    private JTextField textSelectFunction;
    private JLabel labelSelectFunction;
    private List<String> counterExample;
    private DefaultListModel<String> modelCounterExample;

    public GUI(){
        super("EDT gui");
        this.setPreferredSize(new Dimension(700, 700));
        this.setContentPane(this.panelEDT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.imagePanel.setBorder(null);
        this.counterExample = new ArrayList<>();
        this.modelCounterExample = new DefaultListModel<>();
        this.listCounterExample.setModel(modelCounterExample);
        buttonGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    takeCodeClick(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        this.panelImage.setSize(900, 700);
        checkBoxSync.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    takeCodeClick(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        checkBoxDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    takeCodeClick(e);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }
    public void takeCodeClick(ActionEvent e) throws IOException {
        String text = this.textCodeArea.getText();
        System.out.println(text);
        File tempfile = File.createTempFile("temp", ".c");
        FileOutputStream fo = new FileOutputStream(tempfile);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fo));
        out.append(text);
        out.flush();
        out.close();
        Boolean isShowSyncNode = checkBoxSync.isSelected();
        Boolean isShowDetail = checkBoxDetail.isSelected();
        String nLoopsStr = textLoopCount.getText();
        String functionStr = textSelectFunction.getText();
        if(nLoopsStr.length() == 0){
            nLoopsStr = "2";
        }
        int nLoops = Integer.parseInt(nLoopsStr);
        generate(tempfile, isShowSyncNode, isShowDetail, nLoops, functionStr);
        File imgFile = new File("./a1.png");
        BufferedImage img = ImageIO.read(imgFile);
        img = ImageResizer.resize(img, 300);
        imageLabel.setIcon(new ImageIcon(img));
    }
    public void generate(File tempfile, Boolean isShowSyncNode, Boolean isShowDetail, int nLoops, String functionStr) throws IOException {
        ASTFactory ast = new ASTFactory(tempfile.getAbsolutePath());
        IASTFunctionDefinition main_func;
        VtseCFG cfg;
        if(IsNumberic.isNumberic(functionStr)){
            int functionIndex = Integer.parseInt(functionStr) - 1;
            main_func = ast.getFunction(functionIndex);
            cfg = new VtseCFG(ast.getFunction(functionIndex), ast);
        } else {
            main_func = ast.getFunction(functionStr);
            cfg = new VtseCFG(ast.getFunction(functionStr), ast);
        }
//        System.out.println(main_func.toString());
        cfg.unfold(nLoops);
        cfg.index();
        String pre_condition = textPreCondition.getText();
        String post_condition = textPostCondition.getText();
        int mode = FunctionVerification.UNFOLD_MODE;
        File smtFile = File.createTempFile("temp", ".txt");
        System.out.println(smtFile.getAbsolutePath());
        VerificationReport vr = FunctionVerification.verify(ast, main_func, pre_condition, post_condition, nLoops, mode, smtFile);
        PathExecutionVisualize pathExecutionVisualize = new PathExecutionVisualize(cfg, vr);
        List<CFGNode> nodes = pathExecutionVisualize.findPathToFail();
        AddMoreInformation addMoreInformation = new AddMoreInformation(cfg, vr);
        Map<String, String> listParameters = addMoreInformation.parseParameters();
        GraphGenerator graphGenerator = new GraphGenerator(cfg, listParameters);
        graphGenerator.setShowSyncNode(isShowSyncNode);
        graphGenerator.setDetail(isShowDetail);
        graphGenerator.printGraph(false);
        if(vr.getStatus() == VerificationReport.ALWAYS_TRUE){
            graphGenerator.fillColor(nodes, false, true);
        } else {
            graphGenerator.fillColor(nodes, true, true);
        }
        try {
            File file = new File("./graph.dot");
            InputStream dot = new FileInputStream(file);
            MutableGraph g = new Parser().read(dot);
            Graphviz.fromGraph(g).width(700).render(Format.PNG).toFile(new File("./a1.png"));
        } catch(Exception exception){
            System.out.println(exception.toString());
        }
        this.printCounterExample(vr);
    }
    public void printCounterExample(VerificationReport vr){
        modelCounterExample.removeAllElements();
        if(vr.getStatus() == VerificationReport.ALWAYS_TRUE){
            modelCounterExample.addElement("Chua tim thay tham so khong thoa man voi bieu thuc dieu kien cua nguoi dung!");
        } else {
            modelCounterExample.addElement("Chuong trinh khong thoa man bieu thuc dieu kien cua nguoi dung!");
            modelCounterExample.addElement("Counter example: ");
        }
        if(vr.getParameters() != null) {
            for (DefineFun param : vr.getParameters()) {
                String paramString = param.getName() + " = " + param.getValue();
                modelCounterExample.addElement(paramString);
            }
            if (vr.getRet() != null) {
                modelCounterExample.addElement(vr.getRet().getExpression());
            }
        }
    }
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
