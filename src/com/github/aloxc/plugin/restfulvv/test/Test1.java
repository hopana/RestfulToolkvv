package com.github.aloxc.plugin.restfulvv.test;


import com.github.aloxc.plugin.restfulvv.restful.component.VTextPane;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class Test1
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        VTextPane jTextPane = new VTextPane();
        jTextPane.setShowLineNumber(true);
        jTextPane.setContentType("application/json;charset=UTF-8");
//        jTextPane.setContentType("text/html; charset=EUC-JP");
        jTextPane.setText("<html><body bgcolor=\"#F6FBFB\">");
        String text = "大家好：" + "<a href=\"#\" onclick=\"javascript:alert(0)\" ><font color=\"#0000ff\"><strong>"
                + "都程序惹的祸" + "</strong></font></a><br>";
        for (int i = 0; i < 50; i++) {
            text += "<br>aaaa" + i;
        }
        text += "<body></html>";
        jTextPane.setText(text);
        jTextPane.validate();
        frame.add(new JScrollPane(jTextPane));


        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
