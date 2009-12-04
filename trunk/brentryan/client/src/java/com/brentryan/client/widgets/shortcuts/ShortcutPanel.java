/**
 * 
 */
package com.brentryan.client.widgets.shortcuts;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A composite that contains the shortcut stack panel on the left side. The
 * mailbox tree and shortcut lists don't actually do anything, but serve to show
 * how you can construct an interface using
 * {@link com.google.gwt.user.client.ui.StackPanel},
 * {@link com.google.gwt.user.client.ui.Tree}, and other custom widgets.
 */
public class ShortcutPanel extends Composite {

    private int nextHeaderIndex = 0;
    private HashMap stackMap = new HashMap();
    private StackPanel stackPanel = new StackPanel() {

        public void onBrowserEvent(Event event) {
            int oldIndex = getSelectedIndex();
            super.onBrowserEvent(event);
            int newIndex = getSelectedIndex();
            if (oldIndex != newIndex) {
                updateSelectedStyles(oldIndex, newIndex);
            }

            //Get the selected index from the stack
            Shortcut selectedShortcut = (Shortcut) stackMap.get(new Integer(stackPanel.getSelectedIndex()));

            //select the one we selected from the shortcut stack
            if (!selectedShortcut.isSelected()) {
                selectedShortcut.showDefault();
                selectedShortcut.setSelected(true);
            }

            //reset all the shortcuts so they're not selected
            for (Iterator i = stackMap.values().iterator(); i.hasNext();) {
                Shortcut shortcut = (Shortcut) i.next();
                if (!shortcut.equals(selectedShortcut)) {
                    shortcut.setSelected(false);
                }
            }
        }
    };

    /**
     * Constructs a new shortcuts widget using the specified images.
     * 
     * @param images
     *            a bundle that provides the images for this widget
     */
    public ShortcutPanel() {
        initWidget(stackPanel);
    }

    /**
     * Example of using the DOM class to do CSS class name tricks that have
     * become common to AJAX apps. In this case we add CSS class name for the
     * stack panel item that is below the selected item.
     */
    private void updateSelectedStyles(int oldIndex, int newIndex) {
        oldIndex++;
        if (oldIndex > 0 && oldIndex < stackPanel.getWidgetCount()) {
            Element elem = DOM.getElementById(computeHeaderId(oldIndex));
            DOM.setElementProperty(elem, "className", "");
        }

        newIndex++;
        if (newIndex > 0 && newIndex < stackPanel.getWidgetCount()) {
            Element elem = DOM.getElementById(computeHeaderId(newIndex));
            DOM.setElementProperty(elem, "className", "is-beneath-selected");
        }
    }

    public void add(Widget widget) {
        stackMap.put(new Integer(nextHeaderIndex), widget);
        stackPanel.add(widget, createHeaderHTML(widget.getTitle()), true);
    }

    protected void onLoad() {
        // Show the mailboxes group by default.
        stackPanel.showStack(0);
        updateSelectedStyles(-1, 0);
    }

    private String computeHeaderId(int index) {
        return "header-" + this.hashCode() + "-" + index;
    }

    /**
     * Creates an HTML fragment that places an image & caption together, for use
     * in a group header.
     * 
     * @param imageProto an image prototype for an image
     * @param caption the group caption
     * @return the header HTML fragment
     */
    private String createHeaderHTML(String caption) {

        boolean isTop = (nextHeaderIndex == 0);
        String cssId = computeHeaderId(nextHeaderIndex);
        nextHeaderIndex++;

        String captionHTML = "<table class='caption' cellpadding='4' cellspacing='4'>" + "<tr><td class='rcaption'><b style='white-space:nowrap'>" + caption + "</b></td></tr></table>";

        return "<table id='" + cssId + "' align='left' cellpadding='0' cellspacing='0'" + (isTop ? " class='is-top'" : "") + "><tbody>" + "<tr>" + "<td class='box-01'>&nbsp;</td>" + "<td class='box-11'>" + captionHTML + "</td>" + "<td class='box-21'>&nbsp;</td>" + "</tr></tbody></table>";
    }
}
