package net.lnkshrt.lnkshrtmobile.ManageLinks;

public class ManageLinkItem {
    public final String id;
    public final String link;

    public ManageLinkItem(String id, String link) {
        this.id = id;
        this.link = link;
    }

    @Override
    public String toString() {
        return String.format("%d -> %s", id, link);
    }
}
