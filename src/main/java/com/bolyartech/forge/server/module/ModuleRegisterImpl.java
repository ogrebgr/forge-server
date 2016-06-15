package com.bolyartech.forge.server.module;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class ModuleRegisterImpl implements ModuleRegister {
    private List<ForgeModule> mModules = new ArrayList<>();


    @Override
    public void registerModule(ForgeModule mod) {
        if (!isModuleRegistered(mod)) {
            mModules.add(mod);
            mod.registerEndpoints();
        } else {
            throw new IllegalStateException(MessageFormat.format("Module '{0}' already registered", mod.getSystemName()));
        }
    }


    @Override
    public boolean isModuleRegistered(ForgeModule mod) {
        boolean ret = false;

        for(ForgeModule m : mModules) {
            if (m.getSystemName().equalsIgnoreCase(mod.getSystemName())) {
                ret = true;
                break;
            }
        }

        return ret;
    }
}
