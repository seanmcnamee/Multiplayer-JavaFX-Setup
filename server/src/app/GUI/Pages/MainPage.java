package app.GUI.Pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import app.GUI.GUI;
import app.GUI.GUIPage;

public class MainPage extends GUIPage {
    private HTML_Wrapper broadcastString;
    private HTML_Wrapper connectionString;


    public MainPage() {
        super();
        this.panel.setBackground(Color.BLACK);
    }

    @Override
    public VariableComponent[] createComponents() {

        broadcastString = new HTML_Wrapper("broadcast<br>");
        connectionString = new HTML_Wrapper("connection<br>");

        VariableComponent[] components = {
                new VariableComponent(new JLabel("Broadcasts", SwingConstants.CENTER), .25, .1, .5, .2),
                new VariableComponent(new JLabel("Connections", SwingConstants.CENTER), .75, .1, .5, .2),
                new VariableComponent(new JLabel(broadcastString.getHTML(), SwingConstants.CENTER), .25, .6,
                        .5, .9),
                new VariableComponent(new JLabel(connectionString.getHTML(), SwingConstants.CENTER), .75, .6, .5, .9) };
        this.setBackgroundAndTextOfComponentsInRange(components, 0, 3, Color.BLUE, Color.WHITE);
        // this.setBackgroundAndTextOfComponentsInRange(components, 1, 1, Color.WHITE,
        // Color.BLACK);
        ((JLabel) components[0].component).setFont(new Font("Verdana", Font.PLAIN, 20));
        ((JLabel) components[1].component).setFont(new Font("Verdana", Font.PLAIN, 20));

        ((JLabel) components[2].component).setVerticalTextPosition(SwingConstants.TOP);
        ((JLabel) components[3].component).setVerticalTextPosition(SwingConstants.TOP);
        // vB.component.setHorizontalTextPosition(AbstractButton.LEADING);
        return components;
    }

    @Override
    public void setButtonListeners(GUI page) {
        super.setButtonListeners(page);

        //Broadcasts List
        ((JLabel) this.components[2].component).addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                scroll(broadcastString, (JLabel) components[2].component, e);
            }
        });

        //Connections List
        ((JLabel) this.components[3].component).addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                scroll(connectionString, (JLabel) components[3].component, e);
            }
        });

    }

    private void scroll(HTML_Wrapper wrapper, JLabel component, MouseWheelEvent e) {
        wrapper.shift(e.getPreciseWheelRotation());
        component.setText(wrapper.getHTML());
    }

    public void addBroadcast(String text) {
        broadcastString.add(text);

        JLabel broadcastLabel = ((JLabel) this.components[2].component);
        broadcastLabel.setText(broadcastString.getHTML());
    }

    public void addConnection(String text) {
        connectionString.add(text);

        JLabel connectionLabel = ((JLabel) this.components[3].component);
        connectionLabel.setText(connectionString.getHTML());
    }

    @Override
    public void actionPerformed(Object obj, GUI main) {
        
    }


    private class HTML_Wrapper {
        private final String HTML_START = "<HTML>";
        private final String HTML_END = "<HTML>";
        private final String LINE_BREAK = "<BR>";
        private String beforeText = "";
        private String actualText;
        private String afterText = "";
        
        public HTML_Wrapper(String startString) {
            this.actualText = startString;
        }

        public void add(String data) {
            actualText += data + LINE_BREAK;
        }

        public void shift(double direction) {
            //Move text downward
            if (direction > 0) {
                shiftDown();
            } else {
                shiftUp();
            }
        }

        /**
         * Delete above or add below
         */
        private void shiftUp() {
            if (beforeText.contains(LINE_BREAK)) {
                beforeText = beforeText.substring(LINE_BREAK.length());
            } else {
                afterText += LINE_BREAK;
            }
        }

        /**
         * Delete below or add above
         */
        private void shiftDown() {
            if (afterText.contains(LINE_BREAK)) {
                afterText = afterText.substring(LINE_BREAK.length());
            } else {
                beforeText += LINE_BREAK;
            }
        }

        public String getHTML() {
            return HTML_START + beforeText + actualText + afterText + HTML_END;
        }
    }

}