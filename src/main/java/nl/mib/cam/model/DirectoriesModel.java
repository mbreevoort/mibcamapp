package nl.mib.cam.model;

import java.util.ArrayList;
import java.util.List;

public class DirectoriesModel {

    private List<String> dirs = new ArrayList<>();

    public DirectoriesModel(List<String> dirs) {
        this.dirs = dirs;
    }

    public List<String> getDirs() {
        return this.dirs;
    }

}
