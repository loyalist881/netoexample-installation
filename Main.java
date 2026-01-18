import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        String basePath = "E:\\Games\\savegames";
        new File(basePath).mkdirs();

        String save1 = basePath + "\\save1.dat";
        String save2 = basePath + "\\save2.dat";
        String save3 = basePath + "\\save3.dat";
        String zipPath = basePath + "\\save.zip";

        GameProgress gp1 = new GameProgress(100, 1, 50, 243.45);
        GameProgress gp2 = new GameProgress(70, 2, 40, 230);
        GameProgress gp3 = new GameProgress(110, 4, 80, 391.5);

        saveGame(save1, gp1);
        saveGame(save2, gp2);
        saveGame(save3, gp3);

        List<String> saves = List.of(save1, save2, save3);

        zipFiles(zipPath, saves);

        //deleteFiles(saves);

        openZip(zipPath, basePath);

        GameProgress gp4 = openProgress(save1);
    }

    public static void saveGame(String fullPath, GameProgress gameProgress) {
        File file = new File(fullPath);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(gameProgress);
            System.out.println("Сохранение записано в: " + file.getName());
        } catch (IOException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> listFile) {
        byte[] buffer = new byte[4096];
        File zip = new File(zipPath);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip))) {
            for (String filePath : listFile) {
                File file = new File(filePath);

                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.getName());
                    zos.putNextEntry(entry);

                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        zos.write(buffer, 0, bytesRead);
                    }
                    zos.closeEntry();
                    System.out.println("Добавлен в архив: " + file.getName());
                } catch (IOException e) {
                    System.out.println("Ошибка упаковки файла " + file.getName() + ": " + e.getMessage());
                }
            }
            System.out.println("Архив создан: " + zip.getName());
        } catch (IOException e) {
            System.out.println("Ошибка создания архива: " + e.getMessage());
        }
    }


    public static void deleteFiles(List<String> files) {
        for (String filePath : files) {
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("Удалён файл: " + file.getName());
            } else {
                System.out.println("Не удалось удалить файл: " + file.getPath());
            }
        }
    }

    public static void openZip(String zipPath, String basePath) {
        byte[] buffer = new byte[4096];
        File zip = new File(zipPath);
        ZipEntry entry;

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zip))) {
            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                String outPath = basePath + File.separator + name;
                try (FileOutputStream fos = new FileOutputStream(outPath)) {
                    int bytesRead;
                    while ((bytesRead = zis.read(buffer)) != -1) {
                        fos.write(buffer, 0, bytesRead);
                    }
                }
                zis.closeEntry();
                System.out.println("Распакован файл: " + name);
            }
        } catch (IOException e) {
            System.out.println("Архив не распакован: " + e.getMessage());
        }
    }

    public static GameProgress openProgress(String save) {
        File file = new File(save);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            GameProgress result = (GameProgress) ois.readObject();
            System.out.println(result);
            return result;
        } catch (IOException e) {
            System.out.println("Не удалось получить сохранение: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Класс не найден при чтении сохранения: " + e.getMessage());
        }
        return null;
    }
}
