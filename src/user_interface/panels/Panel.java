package user_interface.panels;

import java.util.Map;
import java.awt.*;

import javax.swing.*;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import backend.Backend;
import data.BaseItem;
import user_interface.MainMenu;
import user_interface.components.TitlePanel;


abstract class Panel<T extends BaseItem> extends JPanel {
    protected JPanel panel, titleButtonPanel, contentPanel;
    protected Map<String, T> items;
    protected Backend backend;

    public Panel(String title, MainMenu parent, Map<String, T> items, Backend backend) {
        this.items = items;
        this.backend = backend;

        // Title
        setLayout(new BorderLayout());
        JPanel titlePanel = new TitlePanel(title);
        add(titlePanel, BorderLayout.NORTH);

        // Title button panel
        titleButtonPanel = new JPanel(new FlowLayout());
        titlePanel.add(titleButtonPanel, BorderLayout.EAST);

        // Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            parent.showMainMenu();
        });
        titleButtonPanel.add(backButton, 0);

        // Content
        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);
    }
}
