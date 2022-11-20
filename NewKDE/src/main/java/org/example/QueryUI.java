package org.example;


import org.apache.log4j.BasicConfigurator;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;



public class QueryUI {

    private String[] query = {"1. If the teenager wants to go to Swords Library is there any bike stand near the library?",
            "2. If the teenager wants to go to the Swords Area, are there any bike parking places near the library?",
            "3. If students can get the location extended information from a bike stand area",
            "4. How many bike parking places including the bike stand and Bleeper bikes parking area around swords area in FCC?",
            "5. The list of all tennis courts in FCC.  ",
            "6. How many running tracks in FCC in all?",
            "7. The concrete information about Swords Town Park.",
            "8. If the teenager wants to go to Lusk United FC pitches is there any shared bike ",
            "9. As a student of Fingal Community College who uses a wheelchair, is there any wheelChair accessible library he/she can go to?",
            "10. If a student wants to choose a non-chain sports center including running track, when should he/she avoid going since there may be a lot of people?"
    };
    private String[] output = {"sdoanoasdmpoasjdpoajdapojdsoiasjd", "dsadoa", "cviuhosofn"};

    private JFrame jf;
    private JPanel jp1, jp2;
    private JScrollPane scrollPane1, scrollPane2;
    private JLabel label1;

    public static void main(String args[]){
//        String log4jConfPath = "/path/to/log4j.properties";
//        PropertyConfigurator.configure(log4jConfPath);
        BasicConfigurator.configure();
        QueryUI ui = new QueryUI();
        ui.createView();


    }

    private void createView(){
        jf = new JFrame("QueryUI of Knowledge and Data Eng");

        createQuery();
        resultPanel();

        jf.add(scrollPane1, BorderLayout.NORTH);
        jf.add(scrollPane2, BorderLayout.CENTER);

        jf.setSize(600, 500);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jf.setVisible(true);
    }

    private void createQuery(){
        jp1 = new JPanel();
        jp1.setLayout(new BoxLayout(jp1, BoxLayout.Y_AXIS));
        jp1.setAlignmentX(Component.CENTER_ALIGNMENT);
        jp1.setAlignmentY(Component.CENTER_ALIGNMENT);
        scrollPane1 = new JScrollPane(jp1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane1.setPreferredSize(new Dimension(300, 80));
        JLabel jl = new JLabel("Questions: ");

        jp1.add(jl);
        final JComboBox<String> cb = new JComboBox<String>(query);
        cb.setSelectedIndex(0);
        cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED){
                    System.out.println("selected: Q" + (cb.getSelectedIndex()+1)+ ". " + "\n" + LoadOnto.getQueryResult(cb.getSelectedIndex()));
                }
            }
        });
        cb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cb.getSelectedIndex()==0){
                    outputResult(LoadOnto.queryResult(LoadOnto.getQueryResult(0)));

                }else if(cb.getSelectedIndex()==1){
                    outputResult(LoadOnto.queryResult(LoadOnto.getQueryResult(1)));

                }else if(cb.getSelectedIndex()==2){
                    outputResult(LoadOnto.queryResult(LoadOnto.getQueryResult(2)));

                } else if (cb.getSelectedIndex()==3) {
                    outputResult(LoadOnto.queryResult(LoadOnto.getQueryResult(3)));

                }else if (cb.getSelectedIndex()==4){
                    outputResult(LoadOnto.queryResult(LoadOnto.getQueryResult(4)));

                }else if (cb.getSelectedIndex()==5){
                    outputResult(LoadOnto.queryResult(LoadOnto.getQueryResult(5)));

                }else if (cb.getSelectedIndex()==6){
                    outputResult(LoadOnto.queryResult(LoadOnto.getQueryResult(6)));

                }else if (cb.getSelectedIndex()==7){
                    outputResult(LoadOnto.queryResult(LoadOnto.getQueryResult(7)));

                }else if (cb.getSelectedIndex()==8){
                    outputResult(LoadOnto.queryResult(LoadOnto.getQueryResult(8)));

                }else if (cb.getSelectedIndex()==9){
                    outputResult(LoadOnto.queryResult(LoadOnto.getQueryResult(9)));

                }

            }
        });

        cb.setSize(new Dimension(600 ,300));
        jp1.add(cb);


    }

    private void resultPanel(){
        jp2 = new JPanel();
        jp2.setLayout(new BoxLayout(jp2, BoxLayout.Y_AXIS));

        scrollPane2 = new JScrollPane(jp2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane2.setPreferredSize(new Dimension(400, 300));
//        final JList<String> list = new JList<String>();
//        list.setPreferredSize(new Dimension(500, 300));
//        list.setAlignmentX(Component.LEFT_ALIGNMENT);
//        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        list.setListData(output);
//        list.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                int[] indices = list.getSelectedIndices();
//                ListModel<String> listModel = list.getModel();
//                for (int index : indices) {
//                    System.out.println("Output: " + index + " = " + listModel.getElementAt(index));
//                }
//                System.out.println();
//            }
//        });
//        list.setSelectedIndex(0);
//        jp2.add(list);
        label1 = new JLabel();
        label1.setText("You haven't attempted any queries");
        jp2.add(label1);

    }

    private void outputResult(String result){
        label1.setVisible(false);
        jp2.removeAll();
        jf.invalidate();
        String labelText = convertToHTML(result);
        JLabel label = new JLabel(labelText);
        label.setVisible(true);
        label.setBounds(10, 10, 300, 100);
        jp2.add(label);


        jf.revalidate();
        jf.repaint();

    }

    private String convertToHTML(String result) {

        ArrayList<String> title = new ArrayList<>();
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        String[] lines = result.split(System.lineSeparator());
        int count = 0;
        boolean titleFound = false;
        for (String line : lines) {
//            JLabel label = new JLabel(line);
//            System.out.println(line);
//            panel2.add(label);

            if (line.contains("---") || line.contains("===")) {
                continue;
            }

            ArrayList<String> content = new ArrayList<>();

            String[] contents = line.split("\\|");
            for (int i = 1; i < contents.length; i++) {

                contents[i] = contents[i].replace("|", "").trim();
                System.out.println(contents[i]);

                if (!contents[i].trim().equals("") || contents[i] != null) {
                    content.add(contents[i].trim());
//                    System.out.println(contents[i]);
                }
            }

            if (!titleFound) {
                System.out.println("Title " + content.size());
                title = content;
                titleFound = true;
            } else {
//                data[count] = contents;

                System.out.println("Data " + data.size());
                data.add(content);
                count++;
            }

        }

        System.out.println(title.toString());
        System.out.println(data.toString());


        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><table border='1'>");

        sb.append(getTitle(title));

        for (int i = 0; i < data.size(); i++) {
            sb.append(getContent(data.get(i)));
        }

        sb.append("</table></body>></html>");
        return sb.toString();
    }

    private String getTitle(ArrayList<String> title) {

        StringBuilder titletext = new StringBuilder();
        titletext.append("<tr>");
        for (int i = 0; i < title.size(); i++) {
            titletext.append("<th>");
            titletext.append(title.get(i));
            titletext.append("</th>");
        }
        titletext.append("<tr>");

        return titletext.toString();

    }

    private String getContent(ArrayList<String> content){
        StringBuilder contentText = new StringBuilder();
        contentText.append("<tr>");
        for (int i = 0; i < content.size(); i++) {
            contentText.append("<td>");
            if (content.get(i).contains("<")) {
                content.set(i, content.get(i).replace("<", "&lt;"));
            }
            contentText.append(content.get(i));
            contentText.append("</td>");
        }
        contentText.append("<tr>");

        return contentText.toString();

    }

}
