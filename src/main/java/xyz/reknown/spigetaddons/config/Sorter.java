package xyz.reknown.spigetaddons.config;

import gg.essential.vigilance.data.Category;
import gg.essential.vigilance.data.SortingBehavior;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Sorter extends SortingBehavior {
    private final List<String> categories = Arrays.asList("General", "Dungeons");

    @NotNull
    @Override
    public Comparator<? super Category> getCategoryComparator() {
        return (Comparator<Category>) (o1, o2) -> categories.indexOf(o1.getName()) - categories.indexOf(o2.getName());
    }
}
