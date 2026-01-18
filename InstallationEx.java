import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InstallationEx {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        String ln = System.lineSeparator();
        String basePath = "E:\\Games";

        // Создание директорий
        File srcDir = new File(basePath + "\\src");
        File srcMain = new File(basePath + "\\src\\main");
        File srcTest = new File(basePath + "\\src\\test");
        File resDir = new File(basePath + "\\res");
        File resDrawables = new File(basePath + "\\res\\drawables");
        File resVectors = new File(basePath + "\\res\\vectors");
        File resIcons = new File(basePath + "\\res\\icons");
        File savegames = new File(basePath + "\\savegames");
        File temp = new File(basePath + "\\temp");

        for (File dir : new File[]{srcDir, srcMain, srcTest, resDir, resDrawables, resVectors, resIcons, savegames, temp}) {
            if (dir.mkdirs()) {
                System.out.println("Директория " + dir.getPath() + " создана.");
                sb.append("Директория ").append(dir.getPath()).append(" создана.").append(ln);
            } else if (dir.exists()) {
                System.out.println("Директория " + dir.getPath() + " уже существует.");
                sb.append("Директория ").append(dir.getPath()).append(" уже существует.").append(ln);
            } else {
                System.out.println("Не удалось создать директорию " + dir.getPath() + ".");
                sb.append("Не удалось создать директорию ").append(dir.getPath()).append(".").append(ln);
            }
        }

        // Создание файлов
        File srcMainFile = new File(basePath + "\\src\\main\\Main.java");
        File srcUtilsFile = new File(basePath + "\\src\\main\\Utils.java");
        File tempTemp = new File(basePath + "\\temp\\temp.txt");

        for (File file : new File[]{srcMainFile, srcUtilsFile, tempTemp}) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Файл " + file.getPath() + " создан.");
                    sb.append("Файл ").append(file.getPath()).append(" создан.").append(ln);
                } else if (file.exists()) {
                    System.out.println("Файл " + file.getPath() + " уже существует.");
                    sb.append("Файл ").append(file.getPath()).append(" уже существует.").append(ln);
                } else {
                    System.out.println("Не удалось создать файл " + file.getPath() + ".");
                    sb.append("Не удалось создать файл ").append(file.getPath()).append(".").append(ln);
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                sb.append("Ошибка при создании файла ").append(file.getPath()).append(": ")
                        .append(e.getMessage()).append(ln);
            }
        }

        // Запись лога в temp.txt (один раз, после всех операций)
        try (FileWriter writer = new FileWriter(tempTemp, false)) {
            writer.write(sb.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}


