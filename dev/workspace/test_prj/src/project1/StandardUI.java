package project1;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StandardUI {
    private JFrame jf;
    private String msgMessage;
    private String cfMessage;
    public static final int EOF = -1;

    public StandardUI(JFrame jf) {
        this.jf = jf;
    }

    public void msgDialog() {
        JOptionPane.showMessageDialog(jf, msgMessage);
    }

    public int cfDialog() {
        return JOptionPane.showConfirmDialog(jf, cfMessage, "확인", JOptionPane.YES_NO_OPTION);
    }

    public void setMsgMessage(String msgMessage) { this.msgMessage = msgMessage; }
    public void setCfMessage(String cfMessage) { this.cfMessage = cfMessage; }
}