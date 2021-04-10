package shared;

import java.io.Serializable;

public class SimpleMessage implements Serializable {
    
    private static final long serialVersionUID = 239788423;
    private int someNum;
    private String someMessage;

    public SimpleMessage(int someNum, String someMessage) {
        this.someNum = someNum;
        this.someMessage = someMessage;
    }

    public int getSomeNum() {
        return someNum;
    }

    public void setSomeNum(int someNum) {
        this.someNum = someNum;
    }

    public String getSomeMessage() {
        return someMessage;
    }

    public void setSomeMessage(String someMessage) {
        this.someMessage = someMessage;
    }
}
