package shared;
import java.io.Serializable;
import javafx.geometry.Point3D;

public class LocationUpdate implements Serializable {

    private static final long serialVersionUID = 239788423;
    private double X, Y, Z;
    private UserIdentifier userInfo;

    public LocationUpdate(Point3D position) {
        this(position, null);
    }

    public LocationUpdate(Point3D position, UserIdentifier userInfo) {
        this.X = position.getX();
        this.Y = position.getY();
        this.Z = position.getZ();
        this.userInfo = userInfo;
    }

    public UserIdentifier getUserInfo() {
        return userInfo;
    }
    public void setUserInfo(UserIdentifier userInfo) {
        this.userInfo = userInfo;
    }
    public Point3D getPosition() {
        return new Point3D(X, Y, Z);
    }
}
