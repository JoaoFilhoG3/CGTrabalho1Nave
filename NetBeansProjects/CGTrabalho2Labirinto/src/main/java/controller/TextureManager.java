package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;

public class TextureManager {

    public static int initTexture(String caminho) {

        glEnable(GL_TEXTURE_2D);

        int textura = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textura);
        try {
            BufferedImage bfImage = ImageIO.read(new File(caminho));

            // A R G B -> R G B A
            ByteBuffer colorBuffer = BufferUtils.createByteBuffer(bfImage.getWidth() * bfImage.getHeight() * 4);
            int[] pixels = new int[bfImage.getWidth() * bfImage.getHeight()];
            bfImage.getRGB(0, 0, bfImage.getWidth(), bfImage.getHeight(), pixels, 0, bfImage.getHeight());

            //0xaarrggbb
            for (int pixel : pixels) {
                byte a = (byte) (pixel >> 24);
                byte r = (byte) ((pixel >> 16) & 0xff);
                byte g = (byte) ((pixel >> 8) & 0xffff);
                byte b = (byte) (pixel & 0xffffff);

                colorBuffer.put(r);
                colorBuffer.put(g);
                colorBuffer.put(b);
                colorBuffer.put(a);
            }

            colorBuffer.flip();

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, bfImage.getWidth(), bfImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, colorBuffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textura;
    }
}
