package com.github.aloxc.plugin.restfulvv.restful.component;

import com.github.aloxc.plugin.restfulvv.restful.common.Consts;
import com.sun.xml.bind.v2.runtime.reflect.opt.Const;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 一个显示行号的JTextPane组件
 *
 * @author liyh
 */
public class VTextPane extends JTextPane {
    private SimpleAttributeSet keyAttr;
    private SimpleAttributeSet valueAttr;
    private SimpleAttributeSet normalAttr;
    int nowSeg = 0;
    int nowSegStart = 0;
    String lastKeyStr = null;
    boolean isWrapRow = false;
    boolean hasExists = false;
    private static final String FOUR_BLANK_STR = "    ";
    /**
     * 是否实现行号，默认不显示
     */
    private boolean showLineNumber = false;
    private int fontSize = 16;//默认为16号字体

    public VTextPane() {
        super();

        this.nowSeg = 0;
        this.nowSegStart = 0;
        this.lastKeyStr = null;
        this.isWrapRow = false;
        this.hasExists = false;

        this.keyAttr = new SimpleAttributeSet();
        this.valueAttr = new SimpleAttributeSet();
        this.normalAttr = new SimpleAttributeSet();
        StyleConstants.setForeground(this.keyAttr, Color.blue);
        StyleConstants.setFontSize(this.keyAttr, 14);
        StyleConstants.setFontFamily(this.keyAttr, "consolas");

        StyleConstants.setForeground(this.normalAttr, Color.gray);
        StyleConstants.setFontSize(this.normalAttr, 14);
        StyleConstants.setFontFamily(this.normalAttr, "consolas");

        StyleConstants.setForeground(this.valueAttr, Color.red);
        StyleConstants.setFontSize(this.valueAttr, 14);
        StyleConstants.setFontFamily(this.valueAttr, "consolas");
    }

    public void setShowLineNumber(boolean isShow) {
        this.showLineNumber = isShow;
    }

    public boolean getShowLineNumber() {
        return this.showLineNumber;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        StyleConstants.setFontSize(getInputAttributes(), getFontSize());
        if (getShowLineNumber()) {
            drawLineNumber(g);
        }
    }

    protected void drawLineNumber(Graphics g) {
        setMargin(new Insets(0, 35, 0, 0));
        // 绘制行号的背景色
        g.setColor(new Color(242, 242, 242));
        g.fillRect(0, 0, 30, getHeight());
        // 获得有多少行
        StyledDocument docu = getStyledDocument();
        Element element = docu.getDefaultRootElement();
        int rows = element.getElementCount();
        // 绘制行号的颜色
        //System.out.println("y:" + getY());
        g.setColor(Consts.MAIN_COLOR);
        g.setFont(new Font(getFont().getName(), getFont().getStyle(), 16));
        for (int row = 0; row < rows; row++) {
            g.drawString((row + 1) + "", 2, getPositionY(row + 1));
        }
    }

    public void setFontSize(int fontSize) {
        if (fontSize != 12 &&
                fontSize != 14 &&
                fontSize != 16 &&
                fontSize != 18 &&
                fontSize != 20 &&
                fontSize != 22 &&
                fontSize != 24) {
            throw new RuntimeException("该行号不能识别");
        }
        this.fontSize = fontSize;
    }

    public int getFontSize() {
        return fontSize;
    }

    /**
     * 获得行号中y坐标的位置<br/>
     * 在计算的过程中，有一个比率值，该比率值是根据getY()的返回值之差决定的。
     *
     * @param row 第几行
     * @return 该行的y坐标位置
     */
    private int getPositionY(int row) {
        int y = 0;
        switch (getFontSize()) {
            case 12:
                y = (row * 18) - 4;
                break;
            case 14:
                y = (row * 20) - 5;
                break;
            case 16:
                y = (row * 23) - 6;
                break;
            case 18:
                y = (row * 26) - 8;
                break;
            case 20:
                y = (row * 29) - 10;
                break;
            case 22:
                y = (row * 31) - 11;
                break;
            case 24:
                y = (row * 34) - 12;
                break;
        }
        return y;
    }


    public void format() {
        StyledDocument doc = this.getStyledDocument();
        String old = this.getText().trim();
        int nowTabCount = 0;
        this.isWrapRow = false;
        this.hasExists = false;
        String[] lines = old.split("\n");
        StringBuffer tempStr = new StringBuffer();
        int count = lines.length;

        for(int i = 0; i < count; ++i) {
            String line = lines[i];
            nowTabCount = this.formatSingleLine(doc, line.trim(), nowTabCount, tempStr);
        }

        if (tempStr.length() != 0) {
            try {
                doc.insertString(doc.getLength(), tempStr.toString(), (AttributeSet)null);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

    private int formatSingleLine(StyledDocument doc, String line, int nowTabCount, StringBuffer tempStr) {
        boolean isLastBlank = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            switch (ch) {
                case '[':
                case '{':
                    tempStr.append(ch);
                    tempStr.append("\n");
                    nowTabCount++;
                    printBlanks(tempStr, nowTabCount);
                    isLastBlank = true;
                    try {
                        doc.insertString(doc.getLength(), tempStr.toString(), this.normalAttr);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    tempStr.delete(0, tempStr.length());

                    this.isWrapRow = true;
                    this.hasExists = false;
                    break;
                case ']':
                case '}':
                    nowTabCount--;
                    try {
                        doc.insertString(doc.getLength(), tempStr.toString(), this.valueAttr);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    tempStr.delete(0, tempStr.length());

                    tempStr.append("\n");
                    printBlanks(tempStr, nowTabCount);
                    tempStr.append(ch);
                    isLastBlank = true;
                    try {
                        doc.insertString(doc.getLength(), tempStr.toString(), this.normalAttr);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    tempStr.delete(0, tempStr.length());

                    this.isWrapRow = true;
                    this.hasExists = false;
                    break;
                case '\n':
                    break;
                case ',':
                    try {
                        doc.insertString(doc.getLength(), tempStr.toString(), this.valueAttr);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    tempStr.delete(0, tempStr.length());
                    tempStr.append(ch);
                    tempStr.append("\n");
                    printBlanks(tempStr, nowTabCount);
                    try {
                        doc.insertString(doc.getLength(), tempStr.toString(), this.normalAttr);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    tempStr.delete(0, tempStr.length());
                    this.isWrapRow = true;
                    this.hasExists = false;
                    isLastBlank = true;
                    break;
                case ':':
                case '=':
                    if (!this.isWrapRow && this.hasExists) {
                        tempStr.append(ch);
                    } else {
                        try {
                            doc.insertString(doc.getLength(), tempStr.toString(), this.keyAttr);
                            doc.insertString(doc.getLength(), String.valueOf(ch), this.normalAttr);
                        } catch (BadLocationException e) {
                            e.printStackTrace();
                        }
                        tempStr.delete(0, tempStr.length());
                    }
                    this.isWrapRow = false;
                    this.hasExists = true;
                    break;
                case ' ':
                    if (isLastBlank) {
                        break;
                    }
                default:
                    tempStr.append(ch);
                    isLastBlank = false;
                    break;
            }
        }
        return nowTabCount;
    }

    private static void printBlanks(StringBuffer buffer, int tabCount) {
        for (int j = 0; j < tabCount; j++)
            buffer.append(FOUR_BLANK_STR);
    }

    public void search(JTextArea input) throws BadLocationException {
        String key = input.getText().trim();
        if ("".equals(key.trim()))
            return;
        if (!key.equals(this.lastKeyStr)) {
            this.nowSeg = 0;
            this.nowSegStart = 0;
        }
        this.lastKeyStr = key;
        this.requestFocus();
        Document doc = this.getDocument();
        Element root ;
        for(root = doc.getDefaultRootElement(); this.nowSeg < root.getElementCount(); ++this.nowSeg) {
            Element seg = root.getElement(this.nowSeg);
            String line = doc.getText(seg.getStartOffset(), seg.getEndOffset() - seg.getStartOffset());
            this.nowSegStart = line.indexOf(key, this.nowSegStart);
            if (-1 != this.nowSegStart) {
                int realStart = seg.getStartOffset() + this.nowSegStart;
                int realEnd = realStart + key.length();
                this.nowSegStart += key.length();
                this.setSelectionStart(realStart);
                this.setSelectionEnd(realEnd);
                break;
            }

            this.nowSegStart = 0;
        }

        if (this.nowSeg == root.getElementCount()) {
            this.nowSeg = 0;
            this.nowSegStart = 0;
        }
    }
}
