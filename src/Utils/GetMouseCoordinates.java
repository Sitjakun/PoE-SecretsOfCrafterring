package Utils;

import java.awt.*;

public class GetMouseCoordinates {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        int x = (int) b.getX();
        int y = (int) b.getY();
        System.out.println("X" + x);
        System.out.println("Y" + y);
    }

}
