import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class InstallationEx {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
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
                System.out.println("Директория " + dir.getName() + " создана.");
                sb.append("Директория ").append(dir.getName()).append(" создана.").append(System.lineSeparator());
            } else if (dir.exists()) {
                System.out.println("Директория " + dir.getName() + " уже существует.");
                sb.append("Директория ").append(dir.getName()).append(" уже существует.").append(System.lineSeparator());
            } else {
                System.out.println("Не удалось создать директорию " + dir.getName() + ".");
                sb.append("Не удалось создать директорию ").append(dir.getName()).append(".").append(System.lineSeparator());
            }
        }

        // Создание файлов
        File srcMainFile = new File(basePath + "\\src\\main\\Main.java");
        File srcUtilsFile = new File(basePath + "\\src\\main\\Utils.java");
        File tempTemp = new File(basePath + "\\temp\\temp.txt");

        for (File file : new File[]{srcMainFile, srcUtilsFile, tempTemp}) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Файл " + file.getName() + " создан.");
                    sb.append("Файл ").append(file.getName()).append(" создан.").append(System.lineSeparator());
                } else if (file.exists()) {
                    System.out.println("Файл " + file.getName() + " уже существует.");
                    sb.append("Файл ").append(file.getName()).append(" уже существует.").append(System.lineSeparator());
                } else {
                    System.out.println("Не удалось создать файл " + file.getName() + ".");
                    sb.append("Не удалось создать файл ").append(file.getName()).append(".").append(System.lineSeparator());
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
                sb.append("Ошибка при создании файла ").append(file.getPath()).append(": ")
                        .append(e.getMessage()).append(System.lineSeparator());
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
