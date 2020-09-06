import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IMPane extends JPanel
{
    private BufferedImage sourceImage, resultImage;
    private final String[] imageActions = {"Invert","Flip Horizontal","Flip Vertical","Blur","Difference", "Lens", "Hypnotic"};
    private File myFile;

    public IMPane()
    {
        super();
        setBackground(Color.BLACK);
    };

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        int w = getWidth();
        if (sourceImage !=null)
            g.drawImage(sourceImage,0,25,null);
        else
        {
            g.setColor(Color.BLACK);
            g.fillRect(0,25,w/2,getHeight()-25);
        }
        if (resultImage!=null)
            g.drawImage(resultImage,w/2,25,null);
        else
        {
            g.setColor(Color.BLACK);
            g.fillRect(w/2,25,w/2,getHeight()-25);
        }
        g.setColor(Color.WHITE);
        g.drawLine(w/2,0,w/2,getHeight());
        g.drawString("Source",10,20);
        g.drawString("Result",10+w/2,20);
    }

    public BufferedImage getSourceImage()
    {
        return sourceImage;
    }

    public void setSourceImage(BufferedImage sourceImage)
    {
        this.sourceImage = sourceImage;
    }

    public BufferedImage getResultImage()
    {
        return resultImage;
    }

    public void setResultImage(BufferedImage resultImage)
    {
        this.resultImage = resultImage;
    }

    public String[] getImageActions()
    {
        return imageActions;
    }

    public void load()
    {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(myFile);
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.CANCEL_OPTION)
            return;
        myFile = chooser.getSelectedFile();

        sourceImage = ImageManager.loadImage(myFile);

        repaint();
    }

    public void save()
    {
        if (resultImage == null)
            return;

        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(myFile);
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.CANCEL_OPTION)
            return;
        myFile = chooser.getSelectedFile();

        try
        {
            ImageManager.saveImage(resultImage,myFile.getPath());
        }
        catch (IOException ioExp)
        {
            System.out.println("Could not save file at \""+myFile.getPath()+"\".");
        }
    }

    /**
     * swaps the source and result images.
     */
    public void swap()
    {
        BufferedImage temp = sourceImage;
        sourceImage = resultImage;
        resultImage = temp;
        repaint();
    }

    public void performProcessForIndex(int index)
    {

        switch(index)
        {
            case 0: // INVERT
                performInvert();
                break;
            case 1: //FLIP HORIZONTAL
                performHorizontalFlip();
                break;
            case 2: //FLIP VERTICAL
                performVerticalFlip();
                break;
            case 3: //BLUR
                performBlur();
                break;
            case 4: //DIFFERENCE
                performDifference();
                break;
            case 5: //LENS
                performLens();
                break;
            case 6: //HYPNOTIC FILTER
            performHypnotic();
                break;

        }
        repaint();
    }

    /**
     * creates a new buffered image based on the source image, swapping bright for dark pixels.
     */
    public void performInvert()
    {
        System.out.println("Performing Invert process.");
        if (sourceImage == null)
        {
            System.out.println("Cancelling. No image to process.");
        }

        int resultWidth = sourceImage.getWidth();
        int resultHeight = sourceImage.getHeight();

        int[][][] sourceImageArray = ImageManager.RGBArrayFromImage(sourceImage);
        int[][][] resultImageArray = null; // just enough to compile.

         resultImageArray = new int[resultHeight][resultWidth][3];


        for (int y = 0; y < sourceImageArray.length; y++){
            for (int x = 0; x < sourceImageArray[0].length; x++){
                for (int z = 0; z < 3; z++){
                    resultImageArray[y][x][z] = 255 - sourceImageArray[y][x][z];
                }
            }
        }

        BufferedImage newResult = ImageManager.ImageFromArray(resultImageArray);
        setResultImage(newResult);
    }


    /**
     * creates a new buffered image based on the source image, swapping left for right pixels.
     */
    public void performHorizontalFlip()
    {
        System.out.println("Performing Horizontal Flip process.");
        if (sourceImage == null)
        {
            System.out.println("Cancelling. No image to process.");
        }
        int resultWidth = sourceImage.getWidth();
        int resultHeight = sourceImage.getHeight();

        int[][][] sourceImageArray = ImageManager.RGBArrayFromImage(sourceImage);
        int[][][] resultImageArray = null;// just enough to compile.

         resultImageArray = new int[resultHeight][resultWidth][3];


        for (int y = 0; y < sourceImageArray.length; y++){
            for (int x = 0; x < sourceImageArray[0].length; x++){
                int flippedY = resultHeight - y - 1;

                for (int z = 0; z < 3; z++){
                    resultImageArray[y][x][z] = sourceImageArray[flippedY][x][z];
                }
            }
        }

        BufferedImage newResult = ImageManager.ImageFromArray(resultImageArray);
        setResultImage(newResult);
    }

    public void performVerticalFlip()
    {
        System.out.println("Performing Vertical Flip process.");
        if (sourceImage == null)
        {
            System.out.println("Cancelling. No image to process.");
        }
        int resultWidth = sourceImage.getWidth();
        int resultHeight = sourceImage.getHeight();

        int[][][] sourceImageArray = ImageManager.RGBArrayFromImage(sourceImage);
        int[][][] resultImageArray = null;// just enough to compile.

        resultImageArray = new int[resultHeight][resultWidth][3];


        for (int y = 0; y < sourceImageArray.length; y++){
            for (int x = 0; x < sourceImageArray[0].length; x++){
                int flippedX = resultWidth - x - 1;

                for (int z = 0; z < 3; z++){
                    resultImageArray[y][x][z] = sourceImageArray[y][flippedX][z];
                }
            }
        }

        BufferedImage newResult = ImageManager.ImageFromArray(resultImageArray);
        setResultImage(newResult);
    }

    public void performBlur()
    {
        System.out.println("Performing Blur process.");
        if (sourceImage == null)
        {
            System.out.println("Cancelling. No image to process.");
        }
        int resultWidth = sourceImage.getWidth();
        int resultHeight = sourceImage.getHeight();

        int[][][] sourceImageArray = ImageManager.RGBArrayFromImage(sourceImage);
        int[][][] resultImageArray = null;// just enough to compile.

        resultImageArray = new int[resultHeight][resultWidth][3];


        for (int y = 0; y < sourceImageArray.length; y++){
            for (int x = 0; x < sourceImageArray[0].length; x++){
                try {
                    for (int z = 0; z < 3; z++){
                        int blur0 = sourceImageArray[y][x][z];
                        int blur1 = sourceImageArray[y + 1][x + 1][z];
                        int blur2 = sourceImageArray[y + 1][x][z];
                        int blur3 = sourceImageArray[y][x + 1][z];

                        int blur4 = sourceImageArray[y - 1][x - 1][z];
                        int blur5 = sourceImageArray[y - 1][x][z];
                        int blur6 = sourceImageArray[y][x - 1][z];

                        int blur7 = sourceImageArray[y + 1][x - 1][z];
                        int blur8 = sourceImageArray[y - 1][x + 1][z];

                        int averageBlur = (blur0 + blur1 + blur2 + blur3 + blur4 + blur5 + blur6 + blur7 + blur8) / 9;
                        resultImageArray[y][x][z] = averageBlur;
                    }
                }

                catch (ArrayIndexOutOfBoundsException e){
                    for (int z = 0; z < 3; z++){
                        resultImageArray[y][x][z] = 0;
                    }
                }
            }
        }

        BufferedImage newResult = ImageManager.ImageFromArray(resultImageArray);
        setResultImage(newResult);
    }

    public void performHypnotic()
    {
        System.out.println("Performing Hypnotic process.");
        if (sourceImage == null)
        {
            System.out.println("Cancelling. No image to process.");
        }
        int resultWidth = sourceImage.getWidth();
        int resultHeight = sourceImage.getHeight();

        int[][][] sourceImageArray = ImageManager.RGBArrayFromImage(sourceImage);
        int[][][] resultImageArray = null;// just enough to compile.

        resultImageArray = new int[resultHeight][resultWidth][3];


        for (int y = 0; y < sourceImageArray.length; y++){
            for (int x = 0; x < sourceImageArray[0].length; x++){
                try {
                    for (int z = 0; z < 3; z++){
                        int blur0 = sourceImageArray[y][x][z];
                        int blur1 = sourceImageArray[y + 1][x + 1][z];
                        int blur2 = sourceImageArray[y + 1][x][z];
                        int blur3 = sourceImageArray[y][x + 1][z];

                        int blur4 = sourceImageArray[y - 1][x - 1][z];
                        int blur5 = sourceImageArray[y - 1][x][z];
                        int blur6 = sourceImageArray[y][x - 1][z];

                        int blur7 = sourceImageArray[y + 1][x - 1][z];
                        int blur8 = sourceImageArray[y - 1][x + 1][z];

                        int averageBlur = (blur0 + blur1 + blur2 + blur3 + blur4 + blur5 + blur6 + blur7 + blur8) / 9;
                        resultImageArray[y][x][z] = sourceImageArray[averageBlur][averageBlur][z];
                    }

                }

                catch (ArrayIndexOutOfBoundsException e){
                    for (int z = 0; z < 3; z++){
                        resultImageArray[y][x][z] = 0;
                    }

                }
            }
        }

        BufferedImage newResult = ImageManager.ImageFromArray(resultImageArray);
        setResultImage(newResult);
    }



    public void performDifference()
    {
        System.out.println("Performing Difference process.");
        if (sourceImage == null)
        {
            System.out.println("Cancelling. No image to process.");
        }
        int resultWidth = sourceImage.getWidth();
        int resultHeight = sourceImage.getHeight();

        int[][][] sourceImageArray = ImageManager.RGBArrayFromImage(sourceImage);
        int[][][] resultImageArray = null;// just enough to compile.

        resultImageArray = new int[resultHeight][resultWidth][3];


        for (int y = 0; y < sourceImageArray.length; y++){
            for (int x = 0; x < sourceImageArray[0].length; x++){
                try {
                    for (int z = 0; z < 3; z++){
                        resultImageArray[y][x][z] = Math.abs(sourceImageArray[y][x][z] - sourceImageArray[y][x+1][z]);
                    }
                }

                catch (ArrayIndexOutOfBoundsException e){
                    for (int z = 0; z < 3; z++){
                        resultImageArray[y][x][z] = 0;
                    }
                }
            }
        }

        BufferedImage newResult = ImageManager.ImageFromArray(resultImageArray);
        setResultImage(newResult);
    }


    public void performLens()
    {
        System.out.println("Performing Lens process.");
        if (sourceImage == null)
        {
            System.out.println("Cancelling. No image to process.");
        }
        int resultWidth = sourceImage.getWidth();
        int resultHeight = sourceImage.getHeight();

        int[][][] sourceImageArray = ImageManager.RGBArrayFromImage(sourceImage);
        int[][][] resultImageArray = null;// just enough to compile.

        resultImageArray = new int[resultHeight][resultWidth][3];


        for (int y = 0; y < sourceImageArray.length; y++){
            for (int x = 0; x < sourceImageArray[0].length; x++){

                int px = resultWidth/2;
                int py = resultHeight/2;

                int dx = x - px;
                int dy = y - py;

                int d = (int) Math.sqrt((dx * dx) + (dy * dy));

                if (d > 100 || d == 0){
                    resultImageArray[y][x] = sourceImageArray[y][x];
                }


                else {

                    int resultX = (int) (px + ((d / (10 * Math.sqrt(d))) * dx));
                    int resultY = (int) (py + ((d / (10 * Math.sqrt(d))) * dy));

                    for (int z = 0; z < 3; z++){
                        resultImageArray[y][x][z] = sourceImageArray[resultY][resultX][z];
                    }
                }
            }
        }

        BufferedImage newResult = ImageManager.ImageFromArray(resultImageArray);
        setResultImage(newResult);
    }


}
