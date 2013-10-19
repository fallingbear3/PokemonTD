package com.xkings.pokemontd.component;

import com.artemis.Component;
import com.xkings.pokemontd.map.Path;

/**
 * Created by Tomas on 10/5/13.
 */
public class PathComponent extends Component {

    private final Path path;

    public PathComponent(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
