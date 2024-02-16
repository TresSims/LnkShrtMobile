package net.lnkshrt.lnkshrtmobile.ManageLinks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageLinkItemManager {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<ManageLinkItem> ITEMS = new ArrayList<ManageLinkItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, ManageLinkItem> ITEM_MAP = new HashMap<String, ManageLinkItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(ManageLinkItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ManageLinkItem createPlaceholderItem(int position) {
        return new ManageLinkItem(String.valueOf(position), "Item " + position);
    }

    /**
     * An item representing a shortened link element from the database.
     */
    public static class ManageLinkItem{
        public final String id;
        public final String content;

        public ManageLinkItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}