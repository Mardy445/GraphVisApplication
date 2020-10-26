package Views;

import javax.swing.*;
import java.awt.*;

public class ToolBarButton extends JButton {
    public ToolBarButton(String text, String iconName){
        super(text);
        Icon icon = new ImageIcon(iconName);
        Image scaledIcon = ((ImageIcon) icon).getImage().getScaledInstance(15,15,Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledIcon));
    }
}
