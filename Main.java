import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            Window window = Window.getWindow();
            Thread windowThread = new Thread(window);
            windowThread.start();

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
