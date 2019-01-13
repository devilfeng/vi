package com.ctrip.framework.cs.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang.j on 2016/11/1.
 */
public class Menu {
    final String EMPTYSTATE = "empty";
    private String name;
    private String state;
    private String icon;
    private boolean isBuildIn = false;
    private List<SubMenu> submenu = null;

    public Menu(String name, String icon) {
        this.name = name;
        this.state = EMPTYSTATE;
        this.icon = icon;
        this.isBuildIn = true;
    }

    public Menu(CustomPage customPage) {
        this.name = customPage.getName();
        this.state = customPage.getId();
        this.icon = customPage.getIcon();
        this.isBuildIn = customPage instanceof BuildInPage;
    }

    public List<SubMenu> getSubmenu() {
        return submenu;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isBuildIn() {
        return this.isBuildIn;
    }

    public void addSubMenu(SubMenu subMenu) {
        if (this.submenu == null) {
            this.submenu = new ArrayList<>();
        }
        this.submenu.add(subMenu);
    }

    public void addSubMenu(CustomPage customPage) {
        if (this.submenu == null) {
            this.submenu = new ArrayList<>();
        }
        this.submenu.add(new SubMenu(customPage));
    }

    public static class SubMenu {
        private String name;
        private String state;
        private String icon;
        private boolean isBuildIn = false;

        public SubMenu(CustomPage customPage) {
            this.name = customPage.getName();
            this.state = customPage.getId();
            this.icon = customPage.getIcon();
            this.isBuildIn = customPage instanceof BuildInPage;
        }

        public String getName() {
            return name;
        }

        public String getState() {
            return state;
        }

        public String getIcon() {
            return icon;
        }

        public boolean isBuildIn() {
            return this.isBuildIn;
        }
    }
}
