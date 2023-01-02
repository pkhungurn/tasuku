package example;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tasuku.Workspace;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Random;

import static tasuku.TaskUtil.DO_NOTHING;

public class Tasks {
  private static final Logger logger = LoggerFactory.getLogger(Tasks.class);

  public static void define(Workspace workspace) {
    String aFileName = "data/a.txt";

    workspace.newFileTask(aFileName, ImmutableList.of(), () -> {
      Random random = new Random();
      int number = random.nextInt(100);
      String content = String.format("%d\n", number);
      writeTextFile(workspace.getFilePath(aFileName), content);
    });

    String cFileName = "data/b/c.txt";

    workspace.newFileTask(cFileName, ImmutableList.of(aFileName), () -> {
      String aFileContent = readTextFile(workspace.getFilePath(aFileName));
      int value = Integer.parseInt(aFileContent.trim());
      String content = String.format("%d\n", value * 2);
      writeTextFile(workspace.getFilePath(cFileName), content);
    });

    String eFileName = "data/b/d/e.txt";

    workspace.newFileTask(eFileName, ImmutableList.of(aFileName, cFileName), () -> {
      String aFileContent = readTextFile(workspace.getFilePath(aFileName));
      int aValue = Integer.parseInt(aFileContent.trim());
      String cFileContent = readTextFile(workspace.getFilePath(cFileName));
      int cValue = Integer.parseInt(cFileContent.trim());
      String content = String.format("%d\n", aValue + cValue);
      writeTextFile(workspace.getFilePath(eFileName), content);
    });

    workspace.newCommandTask("data/create_all", ImmutableList.of(aFileName, cFileName, eFileName), DO_NOTHING);

    workspace.newCommandTask("data/delete_all", ImmutableList.of(), () -> {
      deleteFile(workspace.getFilePath(aFileName));
      deleteFile(workspace.getFilePath(cFileName));
      deleteFile(workspace.getFilePath(eFileName));
    });
  }

  static void deleteFile(Path path) {
    if (!Files.exists(path)) {
      return;
    }
    try {
      Files.delete(path);
      logger.info(String.format("Deleted %s ...", path));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  static byte[] readByteArrayFromFile(Path path) {
    try {
      long fileSize = Files.size(path);
      ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
      SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ);
      channel.read(buffer);
      channel.close();
      return buffer.array();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  static String readTextFile(Path path) {
    return new String(readByteArrayFromFile(path), StandardCharsets.UTF_8);
  }

  static void writeByteArrayToFile(Path path, byte[] bytes) {
    try {
      if (path.getParent() != null) {
        Files.createDirectories(path.getParent());
      }
      if (!Files.exists(path)) {
        Files.createFile(path);
      }
      ByteChannel byteChannel = Files.newByteChannel(
          path,
          EnumSet.of(
              StandardOpenOption.WRITE,
              StandardOpenOption.TRUNCATE_EXISTING,
              StandardOpenOption.CREATE));
      byteChannel.write(ByteBuffer.wrap(bytes));
      byteChannel.close();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  static void writeTextFile(Path path, String content) {
    writeByteArrayToFile(path, content.getBytes(StandardCharsets.UTF_8));
  }
}
