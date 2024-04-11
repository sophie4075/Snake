import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Window extends JFrame implements Runnable {

    public static Window window = null;
    public boolean isRunning;


    //static in order to change state or scene from whatever Window you are in
    public  int currentState;

    public  Scene currentScene;
    public  KListener keyListener = new KListener();
    public  MListener mouseListener = new MListener();



    public Window(int width, int height, String title) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        setSize(width, height);
        setTitle(title);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(keyListener);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        isRunning = true;
        changeState(0);
    }

    public static Window getWindow() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(Window.window == null){
            Window.window = new Window(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, Constants.SCREEN_TITLE);
        }

        return Window.window;
    }



    public void changeState(int newState) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        currentState = newState;
        switch (currentState) {
            case 0 -> currentScene = new MenuScene(/*keyListener,*/ mouseListener);
            case 1 -> currentScene = new GameScene(keyListener);


            case 2 -> currentScene = new GameOverScene(mouseListener);
            case 3 -> currentScene = new PauseScene(mouseListener);
            case 4 -> currentScene = GameScene.getPausedScene();
            case 5 -> currentScene = new HowToScene(mouseListener);
            default -> {
                System.out.println("Unknown scene.");
                currentScene = null;
            }
        }
    }

    public void update(double deltaTime) {
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        getGraphics().drawImage(dbImage, 0, 0, this );

        try {
            currentScene.update(deltaTime);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void draw(Graphics graphic){
        //Graphics2D g2 = (Graphics2D)graphic;
        currentScene.draw(graphic);
    }

    @Override
    public void run() {
        double lastFrameTime = 0.0;
        try {
           while (isRunning){
               double time = Time.getTime();
               double deltaTime = time - lastFrameTime;
               lastFrameTime = time;
               update(deltaTime);

           }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        this.dispose();
    }

    public void close(){
        isRunning = false;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

}
