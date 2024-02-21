package net.lnkshrt.lnkshrtmobile.ManageLinks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageLinkItemManager {

    /**
     * An array of sample ManageLink items.
     */
    public static final List<ManageLinkItem> ITEMS = new ArrayList<ManageLinkItem>();

    /**
     * A map of sample ManageLink items, by ID.
     */
    public static final Map<String, ManageLinkItem> ITEM_MAP = new HashMap<String, ManageLinkItem>();

    private static final int COUNT = 25;


    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createManageLinkItem(i));
        }
    }

    private static void addItem(ManageLinkItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ManageLinkItem createManageLinkItem(int position) {
        return new ManageLinkItem(String.valueOf(position), "Item " + position);
    }
}