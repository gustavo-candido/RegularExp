import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.DirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import org.apache.commons.collections15.Transformer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.Scanner;
import java.lang.Process;
import static java.awt.Frame.MAXIMIZED_BOTH;

public class main {

    public static BasicVisualizationServer<Node,Edge> setLayout(DirectedOrderedSparseMultigraph<Node,Edge> graph, Dimension viewArea){
        //Criar grafo randomizado normal:
        //Layout<Node,Edge> layout = new RandomLayout<Node,Edge>(graph, viewArea);

        //Criar grafo usando algoritmo de Fruchterman e Reingold
        Layout<Node,Edge> layout = new FRLayout<Node,Edge>(graph,viewArea);

        //Criar grafo usando algoritmo de Kamada e Kawai
        //Layout<Node,Edge> layout = new KKLayout<Node,Edge>(graph);

        //Criar grafo do tipo SpringLayout
        //Layout<Node,Edge> layout = new SpringLayout<Node,Edge>(graph);

        //Criar grafo do tipo CircleLayout
        //Layout<Node,Edge> layout = new CircleLayout<Node,Edge>(graph);

        BasicVisualizationServer<Node,Edge> panel = new BasicVisualizationServer<Node,Edge>(layout, viewArea);
        return panel;
    }

    private static DirectedOrderedSparseMultigraph<Node,Edge> createGraph() throws FileNotFoundException{
        DirectedOrderedSparseMultigraph<Node,Edge> g = new DirectedOrderedSparseMultigraph<Node,Edge>();
        Scanner sc = new Scanner(new FileInputStream("out.txt"));
        String sAux;

        //quantidade de vertices

        int iaux = sc.nextInt();

        //vertices

        Node[] vetorN = new Node[iaux];
        for(int i=0;i<iaux;i++){
            vetorN[i] = new Node();
            sAux = sc.next();
            vetorN[i].setNameN(sAux);
        }

        //arestas

        String sTransicao;
        iaux = sc.nextInt();
        Node nO = new Node(), nD = new Node();
        for(int i=0;i<iaux;i++){
            sAux = sc.next();
            for(int j=0;j<vetorN.length;j++){
                if (vetorN[j].equals(sAux)){
                    nO = vetorN[j];
                }

            }
            sAux = sc.next();
            for(int k=0;k<vetorN.length;k++){
                if (vetorN[k].equals(sAux)){
                    nD = vetorN[k];
                }
            }
            sTransicao = sc.nextLine();
            g.addEdge(new Edge(sTransicao),nO,nD);
        }
        return g;
    }

    static void go() throws FileNotFoundException,IOException {

        PrintStream console = System.out;

        File file = new File("C:/Users/gusta/OneDrive/Área de Trabalho/GITHUB/lfa-master/lfa/in_regular.txt");
        FileOutputStream fos = new FileOutputStream(file);
        PrintStream ps = new PrintStream(fos);
        System.setOut(ps);

        JFrame frameER = new JFrame("INPUT - ER");
        JPanel painel = new JPanel();
        JPanel painelER = new JPanel();
        JPanel painelWord = new JPanel();
        JPanel painelbutton = new JPanel();
        JTextField textER = new JTextField(30);
        JTextField textWord = new JTextField(30);
        JLabel labelER = new JLabel("EXPRESSAO:");
        JLabel labelWord = new JLabel("PALAVRA:");
        JButton buttonER = new JButton("OK");

        frameER.setSize(900,300);

        //set panels

        painel.setPreferredSize(new Dimension(900,350));
        painelER.setPreferredSize(new Dimension(900,80));
        painelWord.setPreferredSize(new Dimension(900,80));
        painelbutton.setPreferredSize(new Dimension(900,130));
        painelER.setBorder(new EmptyBorder(10,10,10,10));
        painelWord.setBorder(new EmptyBorder(10,10,10,10));
        painelbutton.setBorder(new EmptyBorder(10,10,10,10));

        //set textfields

        textER.setFont(textER.getFont().deriveFont(18.0f));
        textWord.setFont(textER.getFont().deriveFont(18.0f));
        textER.setPreferredSize(new Dimension(600,50));
        textWord.setPreferredSize(new Dimension(600,50));
        //set labels

        labelER.setFont(textER.getFont().deriveFont(18.0f));
        labelWord.setFont(textER.getFont().deriveFont(18.0f));
        labelER.setPreferredSize(new Dimension(150,50));
        labelWord.setPreferredSize(new Dimension(150,50));

        //set button

        buttonER.setPreferredSize(new Dimension(250,130));
        buttonER.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String stringER = textER.getText();
                String stringWord = textWord.getText();

                System.out.println(stringER);
                System.out.println(stringWord);
                frameER.dispose();
            }
        });

        painelER.add(labelER);
        painelER.add(textER);
        painelWord.add(labelWord);
        painelWord.add(textWord);
        painel.add(painelER);
        painel.add(painelWord);
        painelbutton.add(buttonER);

        painel.add(painelER);
        painel.add(painelWord);
        painel.add(painelbutton);

        frameER.add(painel);

        frameER.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameER.pack();
        frameER.setLocationRelativeTo(null);
        frameER.setResizable(false);
        frameER.setVisible(true);
    }

    public static void main(String[] args) throws FileNotFoundException,IOException, InterruptedException {
        go();
        Process process0 = new ProcessBuilder("C:/Users/gusta/OneDrive/Área de Trabalho/GITHUB/lfa-master/lfa/regular.exe").start();
        process0.waitFor();

        Process process = new ProcessBuilder("C:/Users/gusta/OneDrive/Área de Trabalho/GITHUB/lfa-master/lfa/code_aux.exe").start();
        process.waitFor();

        Dimension viewArea = new Dimension(1000,600);
        DirectedOrderedSparseMultigraph<Node,Edge> graph = createGraph();
        BasicVisualizationServer<Node,Edge> panel = setLayout(graph,viewArea);

        //Resetar o scanner

        Scanner sc = new Scanner(new FileInputStream("out.txt"));
        int aux = sc.nextInt();
        sc.nextLine();
        while(aux != 0){
            aux--;
            sc.nextLine();
        }
        aux = sc.nextInt();
        sc.nextLine();
        while(aux!=0){
            aux--;
            sc.nextLine();
        }

        //Setar o initialNode

        String initialNode;
        initialNode = sc.next();
        aux = sc.nextInt();

        //Setar os nodeF;

        String saux;
        String[] nodeF = new String[aux];

        for(int i=0;i<aux;i++){
            saux = sc.next();
            nodeF[i] = saux;
        }
        aux = sc.nextInt();

        //Setar os vT

        String[] vT = new String[aux];

        for(int a=0;a<vT.length;a++){
            saux = sc.next();
            vT[a] = saux;
        }

        //Transformer da cor dos vertices

        Transformer<Node,Paint> nodeColorLabeller = new Transformer<Node, Paint>() {
            public Paint transform(Node n) {
                if(n.equals(initialNode)) return Color.GREEN;
                for(int i=0;i<vT.length;i++){
                    if(n.toString().equals(vT[i])) return Color.YELLOW;
                }
                return Color.WHITE;
            }
        };

        //Transformer do formato dos vertices

        Transformer<Node,Shape> nodeShapeTransformer = new Transformer<Node,Shape>() {
            public Shape transform(Node n) {
                //if(n.equals(initialNode)) return new Rectangle(-15, -10, 50, 25);
                for(int i=0;i<nodeF.length;i++){
                    if(n.toString().equals(nodeF[i])) return new Rectangle(-15,-10,30,30);
                }
                return new Ellipse2D.Double(-15,-15,30,30);
            }
        };

        //Transformer dos nomes dos Vertices

        Transformer<Node,String> nodeLabeller = new Transformer<Node, java.lang.String>() {
            public java.lang.String transform(Node n) {
                return n.toString();
            }
        };

        //Transformer dos nomes das Arestas

        Transformer<Edge,String> edgeLabeller = new Transformer<Edge,String>(){
            public String transform(Edge e){
                return e.transicao;
            }
        };

        //Incluir no "painel"

        panel.getRenderContext().setVertexLabelTransformer(nodeLabeller);
        panel.getRenderContext().setEdgeLabelTransformer(edgeLabeller);
        //Se quiser definir as posicoes (N,NW,E,SE,S,SW,W,NW,CNTR):
        panel.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
        panel.getRenderContext().setVertexFillPaintTransformer(nodeColorLabeller);
        panel.getRenderContext().setVertexShapeTransformer(nodeShapeTransformer);

        //Setar o texto

        String labelText = new String();
        for(int d=0;d<vT.length;d++){
            labelText += " -> " + vT[d];

            if(d % 15 == 0) labelText+='\n';
        }
        System.out.println(labelText);

        //Setar o JPanel do texto

//        JLabel label = new JLabel(labelText);
//        label.setFont(new Font("Texto",Font.PLAIN,20));
//        JPanel painel2 = new JPanel();
//        painel2.add(label);

        //Setar o JFrame

        JFrame frame = new JFrame("TRABALHO - LFA");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(BorderLayout.CENTER,panel);
//        frame.add(BorderLayout.SOUTH,painel2);
        frame.setExtendedState(MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
    }
}