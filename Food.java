import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public class Food {
    public Rectangle bg;

    public Rectangle food;
    public Snake snake;
    public int width, height;
    public Color color;

    public int xPadding;

    public boolean foodIsSpawned = false;


    public Food(Rectangle bg, Snake snake, int width, int height, Color color) {
        this.bg = bg;
        this.snake = snake;
        this.width = width;
        this.height = height;
        this.color = color;
        this.food = new Rectangle(0, 0, width, height);
        xPadding = (int)((Constants.TILE_WIDTH - this.width) / 2.0);
    }

    public void spawn(){
        do{
            //Generates a random number bewteen 0 and columns within the grid tile for the food to appear
            double randX = (int)(Math.random() * (int)(bg.width / Constants.TILE_WIDTH))*Constants.TILE_WIDTH + bg.x;
            double randY = (int)(Math.random() * (int)(bg.height / Constants.TILE_WIDTH))*Constants.TILE_WIDTH + bg.y;
            this.food.x = randX;
            this.food.y = randY;

        }while(snake.intersectingWithRect(this.food));

        this.foodIsSpawned = true;

    }

    public void update(double dt) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if(snake.intersectingWithRect(this.food)){
            snake.grow();
            GameScene.count();
            this.food.x = -100;
            this.food.y = -100;
            foodIsSpawned = false;
        }
    }

    public void draw(Graphics2D foodGraphic){
        foodGraphic.setColor(color);
        foodGraphic.fillRect((int)this.food.x + xPadding, (int)this.food.y + xPadding, width, height);
    }

}
