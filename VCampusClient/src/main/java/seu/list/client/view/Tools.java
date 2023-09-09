package seu.list.client.view;

import javax.swing.*;
import java.awt.*;

public class Tools {
    //设置窗口居中
    public static void setWindowspos(int WIDTH, int HEIGHT, JFrame jframe) {
        //传递宽和高
        Toolkit kit = Toolkit.getDefaultToolkit();//获取当前屏幕大小
        Dimension screensize = kit.getScreenSize();
        int width = screensize.width;
        int height = screensize.height;
        int x = (width - WIDTH) / 2;
        int y = (height - HEIGHT) / 2;
        jframe.setBounds(x, y, WIDTH, HEIGHT);
    }
}
