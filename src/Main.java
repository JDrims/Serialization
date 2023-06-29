import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(1000, 600, 80, 400);
        GameProgress gameProgress2 = new GameProgress(800, 500, 70, 300);
        GameProgress gameProgress3 = new GameProgress(600, 400, 60, 200);
        saveGame("D:\\Games\\savegames\\save1.dat", gameProgress1);
        saveGame("D:\\Games\\savegames\\save2.dat", gameProgress2);
        saveGame("D:\\Games\\savegames\\save3.dat", gameProgress3);
        List<String> listFilesPath = new ArrayList<>();
        listFilesPath.add("D:\\Games\\savegames\\save1.dat");
        listFilesPath.add("D:\\Games\\savegames\\save2.dat");
        listFilesPath.add("D:\\Games\\savegames\\save3.dat");
        zipFiles("D:\\Games\\savegames\\save.zip", listFilesPath);
    }

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);) {
            objectOutputStream.writeObject(gameProgress);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void zipFiles(String zipFilePath, List<String> listFilesPath) {
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFilePath));) {
            for (String filePath : listFilesPath) {
                File dirFile = new File(filePath);
                try (FileInputStream fileInputStream = new FileInputStream(filePath);) {
                    String nameFile = dirFile.getName();
                    ZipEntry entry = new ZipEntry(nameFile);
                    zipOutputStream.putNextEntry(entry);
                    byte[] buffer = new byte[fileInputStream.available()];
                    fileInputStream.read(buffer);
                    zipOutputStream.write(buffer);
                } catch (Exception e) {
                    System.out.println(e);
                } finally {
                    dirFile.delete();
                }
            }
            zipOutputStream.closeEntry();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}