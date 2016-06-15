package com.bolyartech.forge.server.module;

public interface ModuleRegister {
    void registerModule(ForgeModule mod);
    boolean isModuleRegistered(ForgeModule mod);
}
