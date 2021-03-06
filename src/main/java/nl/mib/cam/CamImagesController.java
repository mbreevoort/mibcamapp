package nl.mib.cam;

import nl.mib.cam.model.DirectoriesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CamImagesController {
    @Autowired
    Environment env;
    private String camImagesRootDir;

    @RequestMapping("/cam/api/v1/listdirs")
    public DirectoriesModel listDirs() {
        List<String> dirs = new ArrayList<>();
        Path path = Paths.get(URI.create(getCamImagesRootDir()));
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(path)) {
            paths.forEach(p -> dirs.add(p.getFileName().toString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new DirectoriesModel(dirs);
    }

    @RequestMapping(value = "/cam/api/v1/list/{dir}")
    public Map<String, List<String>> listdir(@PathVariable("dir") String startdir) throws IOException {
        Path rootDir = Paths.get(URI.create(getCamImagesRootDir() + startdir));
        List<Path> dirs = Collections.singletonList(rootDir);

        Map<String, List<String>> dirToPath = dirs.stream()//
                .collect(Collectors.toMap(dir -> dir.getFileName().toString(), this::listFilesSorted));

        return dirToPath;
    }

    @RequestMapping(value = "/cam/api/v1/listall")
    public Map<String, List<String>> listall() throws IOException {
        Path rootDir = Paths.get(URI.create(getCamImagesRootDir()));
        List<Path> dirs = listFiles(rootDir);

        Map<String, List<String>> dirToPath = dirs.stream()//
                .collect(Collectors.toMap(dir -> dir.getFileName().toString(), this::listFilesSorted));

        return dirToPath;
    }

    private List<String> listFilesSorted(Path dir) {
        return listFiles(dir).stream().map(p -> p.getFileName().toString()).sorted(String::compareTo).collect(Collectors.toList());
    }

    private List<Path> listFiles(Path path) {
        List<Path> dirs = new ArrayList();

        try (DirectoryStream<Path> paths = Files.newDirectoryStream(path)) {
            paths.forEach(dirs::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dirs;
    }

    private String getCamImagesRootDir() {
        if (camImagesRootDir != null) {
            return camImagesRootDir;
        }
        return env.getRequiredProperty(MibCamApplication.PROPERTY_CAM_IMAGES_ROOT_DIR);
    }

    public void setCamImagesRootDir(String path) {
        camImagesRootDir = path;
    }
}
