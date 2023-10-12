package fr.btn.contactsbook.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.util.List;

public class AppMenu {
    private Menu menu;
    private String menuId;
    private ObservableList<MenuItem> menuItems;

    public AppMenu(String name) {
        this.menu = new Menu(name);
        this.menuId = this.menu.getId();
        menuItems = FXCollections.observableArrayList();
    }

    public void addMenuItem(String itemLabel) {
        menuItems.add(new MenuItem(itemLabel));
        this.menu.getItems().addAll(menuItems);
    }
    public void addMenuItems(List<String> itemLabels) {
        int i = 0;
        while(i < itemLabels.size()) {
            menuItems.add(new MenuItem(itemLabels.get(i)));
        }
        menu.getItems().addAll(menuItems);
    }
    public Menu getMenu() {

        return this.menu;
    }

}
