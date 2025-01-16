package dev.waterchick.velocityaliases.managers;


import dev.waterchick.velocityaliases.Alias;

import java.util.ArrayList;
import java.util.List;

public class AliasesManager {
    private final List<Alias> aliasList = new ArrayList<>();

    public void createAlias(Alias alias) {
        this.aliasList.add(alias);
    }

    public List<Alias> getAliasList() {
        return this.aliasList;
    }

    public void clearAliasList() {
        this.aliasList.clear();
    }
}
