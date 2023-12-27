package com.runescape.graphics.sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static java.nio.file.StandardOpenOption.READ;

public final class SpriteCache implements Closeable {

    private Sprite[] cache;

    private FileChannel dataChannel;
    private FileChannel metaChannel;

    public void init(File dataFile, File metaFile) throws IOException {
        if (!dataFile.exists()) {
            throw new IOException(String.format("Could not find data file= %s", dataFile.getName()));
        }

        if (!metaFile.exists()) {
            throw new IOException(String.format("Could not find meta file= %s", metaFile.getName()));
        }

        dataChannel = FileChannel.open(dataFile.toPath(), READ);
        metaChannel = FileChannel.open(metaFile.toPath(), READ);

        final int spriteCount = Math.toIntExact(metaChannel.size() / 10);

        cache = new Sprite[spriteCount];

        System.out.printf("com.client.graphics.sprite.Sprite cache size=%d%n", cache.length);
    }

    public Sprite lookup(int id) {
        try {
            if (contains(id)) {
                return cache[id];
            }

            if (!dataChannel.isOpen() || !metaChannel.isOpen()) {
                System.out.println("com.client.graphics.sprite.Sprite channels are closed!");
                return null;
            }

            final int entries = Math.toIntExact(metaChannel.size() / 10);

            if (id > entries) {
                System.out.printf("id=%d > size=%d%n", id, entries);
                return null;
            }

            metaChannel.position(id * 10);

            final ByteBuffer metaBuf = ByteBuffer.allocate(10);
            metaChannel.read(metaBuf);
            metaBuf.flip();

            final int pos = ((metaBuf.get() & 0xFF) << 16) | ((metaBuf.get() & 0xFF) << 8) | (metaBuf.get() & 0xFF);
            final int len = ((metaBuf.get() & 0xFF) << 16) | ((metaBuf.get() & 0xFF) << 8) | (metaBuf.get() & 0xFF);
            final int offsetX = metaBuf.getShort() & 0xFF;
            final int offsetY = metaBuf.getShort() & 0xFF;

            final ByteBuffer dataBuf = ByteBuffer.allocate(len);

            dataChannel.position(pos);
            dataChannel.read(dataBuf);
            dataBuf.flip();

            try (InputStream is = new ByteArrayInputStream(dataBuf.array())) {

                BufferedImage bimage = ImageIO.read(is);

                if (bimage == null) {
                    System.out.printf("Could not read image at %d%n", id);
                    return null;
                }

                if (bimage.getType() != BufferedImage.TYPE_INT_ARGB) {
                    bimage = convert(bimage);
                }

                final int[] pixels = ((DataBufferInt) bimage.getRaster().getDataBuffer()).getData();

                final Sprite sprite = new Sprite(bimage.getWidth(), bimage.getHeight(), offsetX, offsetY, pixels);

                // cache so we don't have to perform I/O calls again
                cache[id] = sprite;

                return sprite;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("No sprite found for id=%d%n", id);
        return null;
    }

    public boolean contains(int id) {
        return id < cache.length && cache[id] != null;
    }

    public void set(int id, Sprite sprite) {
        if (!contains(id)) {
            return;
        }

        cache[id] = sprite;
    }

    private static BufferedImage convert(BufferedImage bimage) {
        BufferedImage converted = new BufferedImage(bimage.getWidth(), bimage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        converted.getGraphics().drawImage(bimage, 0, 0, null);
        return converted;
    }

    public void close() throws IOException {
        dataChannel.close();
        metaChannel.close();
    }

}
