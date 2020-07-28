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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
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
    private JTextField textSelectFunction;
    private JLabel labelSelectFunction;
    private JSpinner spinnerPathIndex;
    private JButton buttonReset;
    private JLabel labelPathIndex;
    private JPanel panelResult;
    private JScrollPane imagePanel;
    private List<String> counterExample;
    private DefaultListModel<String> modelCounterExample;
    private int imageWidth;
    private int imageHeight;
    private BufferedImage image;
    private GraphGenerator graphGenerator;
    private List<List<CFGNode>> paths;
    private List<VerificationReport> vrs;

    public GUI(){
        super("VTTool");
        this.setPreferredSize(new Dimension(700, 700));
        this.setContentPane(this.panelEDT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.imagePanel.setBorder(null);
        this.counterExample = new ArrayList<>();
        this.modelCounterExample = new DefaultListModel<>();
        this.listCounterExample.setModel(modelCounterExample);
        this.imageWidth = 500;
        this.imageHeight = 700;
        this.graphGenerator = null;
        this.paths = new ArrayList<>();
        this.vrs = new ArrayList<>();
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
                Boolean isShowSyncNode = checkBoxSync.isSelected();
                Boolean isShowDetail = checkBoxDetail.isSelected();
                int index = (Integer) spinnerPathIndex.getValue();
                try {
                    printGraph(isShowSyncNode, isShowDetail, index);
                    printCounterExample(index);
                    File imgFile = new File("./a1.png");
                    BufferedImage img = ImageIO.read(imgFile);
                    setImage(img);
                    showImage();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        checkBoxDetail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean isShowSyncNode = checkBoxSync.isSelected();
                Boolean isShowDetail = checkBoxDetail.isSelected();
                int index = (Integer) spinnerPathIndex.getValue();
                try {
                    printGraph(isShowSyncNode, isShowDetail, index);
                    printCounterExample(index);
                    File imgFile = new File("./a1.png");
                    BufferedImage img = ImageIO.read(imgFile);
                    setImage(img);
                    showImage();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        imageLabel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
//                if (e.isAltDown()) {
                int oldWidth = getImageWidth();
                int oldHeight= getImageHeight();
                int newWidth = oldWidth;
                int newHeight= oldHeight;
                double amount = Math.pow(1.005, e.getScrollAmount());
                if (e.getWheelRotation() > 0) {
                    newWidth = (int)(oldWidth * amount);
                    newHeight= (int)(oldHeight* amount);
                } else {
                    newWidth = (int)(oldWidth / amount);
                    newHeight= (int)(oldHeight/ amount);
                }
                setImageWidth(newWidth);
                setImageHeight(newHeight);
                showImage();
//                } else {
//                    // if alt isn't down then propagate event to scrollPane
////                    JScrollPane scrollPane = getPanelScrollPane();
//                    imagePanel.getParent().dispatchEvent(e);
//                }
            }
        });

        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spinnerPathIndex.setValue(0);
                Boolean isShowSyncNode = checkBoxSync.isSelected();
                Boolean isShowDetail = checkBoxDetail.isSelected();
                try {
                    printGraph(isShowSyncNode, isShowDetail, 0);
                    printCounterExample(0);
                    File imgFile = new File("./a1.png");
                    BufferedImage img = ImageIO.read(imgFile);
                    setImage(img);
                    showImage();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        spinnerPathIndex.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int index = (Integer) spinnerPathIndex.getValue();
                Boolean isShowSyncNode = checkBoxSync.isSelected();
                Boolean isShowDetail = checkBoxDetail.isSelected();
                try {
                    printGraph(isShowSyncNode, isShowDetail, index);
                    printCounterExample(index);
                    File imgFile = new File("./a1.png");
                    BufferedImage img = ImageIO.read(imgFile);
                    setImage(img);
                    showImage();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        imageLabel.addComponentListener(new ComponentAdapter() {
        });
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(e.getClickCount() == 2){
                    File image = new File("./a1.png");
                    Desktop dt = Desktop.getDesktop();
                    try {
                        dt.open(image);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
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
        generate(tempfile, nLoops, functionStr);
        this.spinnerPathIndex.setModel(new SpinnerNumberModel(0,0,this.getVrs().size()-1,1));
        Integer index = (Integer)this.spinnerPathIndex.getValue();
        printGraph(isShowSyncNode, isShowDetail, index);
        this.printCounterExample(index);
        File imgFile = new File("./a1.png");
        BufferedImage img = ImageIO.read(imgFile);
        this.setImage(img);
        showImage();
    }
    public void generate(File tempfile, int nLoops, String functionStr) throws IOException {
        ASTFactory ast = new ASTFactory(tempfile.getAbsolutePath());
        IASTFunctionDefinition main_func;
        VtseCFG cfg;
        if(functionStr.equals("")){
            main_func = ast.getFunction(0);
            cfg = new VtseCFG(ast.getFunction(0), ast);
        } else if(IsNumberic.isNumberic(functionStr)){
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
        this.setPaths(new ArrayList<>());
        this.setVrs(new ArrayList<>());
        this.getPaths().add(new ArrayList<>());
        this.getVrs().add(vr);
        if(vr.getStatus().equals(VerificationReport.FALSE)){
            PathExecutionVisualize pathExecutionVisualize = new PathExecutionVisualize(cfg, vr);
            pathExecutionVisualize.findPathsToFail(pre_condition, post_condition);
            this.getPaths().addAll(pathExecutionVisualize.getListPaths());
            this.getVrs().addAll(pathExecutionVisualize.getListvr());
        }
        AddMoreInformation addMoreInformation = new AddMoreInformation(cfg, vr);
        Map<String, String> listParameters = addMoreInformation.parseParameters();
        this.setGraphGenerator(new GraphGenerator(cfg, listParameters));
    }
    public void printGraph(Boolean isShowSyncNode, Boolean isShowDetail, int index) throws IOException {
        List<CFGNode> nodes = this.getPaths().get(index);
        VerificationReport vr = this.getVrs().get(index);
        graphGenerator.setShowSyncNode(isShowSyncNode);
        graphGenerator.setDetail(isShowDetail);
        graphGenerator.printGraph(false);
        graphGenerator.fillColor(nodes, index != 0, true);
        try {
            File file = new File("./graph.dot");
            InputStream dot = new FileInputStream(file);
            MutableGraph g = new Parser().read(dot);
            Graphviz.fromGraph(g).width(1000).render(Format.PNG).toFile(new File("./a1.png"));
        } catch(Exception exception){
            System.out.println(exception.toString());
        }
    }
    public void printCounterExample(int index){
        VerificationReport vr = this.getVrs().get(0);
        VerificationReport thisVr = this.getVrs().get(index);
        int numOfFailPath = this.getVrs().size() - 1;
        modelCounterExample.removeAllElements();
        if(vr.getStatus().equals(VerificationReport.ALWAYS_TRUE)){
            modelCounterExample.addElement("Chua tim thay tham so khong thoa man voi bieu thuc dieu kien cua nguoi dung!");
        } else {
            modelCounterExample.addElement("Chuong trinh khong thoa man bieu thuc dieu kien cua nguoi dung!");
            modelCounterExample.addElement("Tim thay " + numOfFailPath + " duong thuc thi khong thoa man");
            if(index != 0){
                modelCounterExample.addElement("Phan vi du cho duong thuc thi sai " + index + ": ");
            }
        }
        if(index != 0){
            if(thisVr.getParameters() != null) {
                for (DefineFun param : thisVr.getParameters()) {
                    String paramString = param.getName() + " = " + param.getValue();
                    modelCounterExample.addElement(paramString);
                }
                if (thisVr.getRet() != null) {
                    modelCounterExample.addElement(thisVr.getRet().getExpression());
                }
            }
        }
    }
    public void showImage(){
        try{
            BufferedImage img = this.getImage();
            img = ImageResizer.resize(img, this.getImageWidth(), this.getImageHeight(), true);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public GraphGenerator getGraphGenerator() {
        return graphGenerator;
    }

    public void setGraphGenerator(GraphGenerator graphGenerator) {
        this.graphGenerator = graphGenerator;
    }

    public List<List<CFGNode>> getPaths() {
        return paths;
    }

    public void setPaths(List<List<CFGNode>> paths) {
        this.paths = paths;
    }

    public List<VerificationReport> getVrs() {
        return vrs;
    }

    public void setVrs(List<VerificationReport> vrs) {
        this.vrs = vrs;
    }
}
