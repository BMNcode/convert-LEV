import util.FileUtil;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {
        FileUtil.save(FileUtil.collectDublicate(FileUtil.getLEV("D:\\Java\\pez.txt")));
    }
}
