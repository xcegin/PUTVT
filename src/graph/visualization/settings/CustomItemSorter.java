package graph.visualization.settings;

import prefuse.visual.DecoratorItem;
import prefuse.visual.VisualItem;
import prefuse.visual.sort.ItemSorter;
/**
 * Inspiration from https://plugins.jetbrains.com/plugin/8087-graph-database-support source code
 */
public class CustomItemSorter extends ItemSorter {

    // Value of 2^22, which is minimal score gap before the next category
    public static final int GAP = 4194304;

    @Override
    public int score(VisualItem item) {
        if (item instanceof DecoratorItem) {
            VisualItem decoratedItem = ((DecoratorItem) item).getDecoratedItem();
            return layerByRow(decoratedItem) + 1;
        }

        return layerByRow(item);
    }

    private int layerByRow(VisualItem item) {
        return super.score(item) + (item.getRow() * 2 % GAP);
    }
}
