package shared;

import java.io.Serializable;
import javafx.scene.paint.Color;

public class UserIdentifier implements Serializable {
    
    private static final long serialVersionUID = 239788423;
    private String userName, groupName;
    private double R, G, B, O;

    public UserIdentifier(String userName, String groupName, Color color) {
        this.userName = userName;
        this.groupName = groupName;
        this.R = color.getRed();
        this.G = color.getGreen();
        this.B = color.getBlue();
        this.O = color.getOpacity();
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public Color getColor() {
        return new Color(R, G, B, O);
    }
}
