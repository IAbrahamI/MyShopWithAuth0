package com.example.menu.item;

import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableMapRepositories
public class ItemService {
    private final CrudRepository<Item, Long> repository;

    public ItemService(CrudRepository<Item, Long> repository) {
        this.repository = repository;
        this.repository.saveAll(defaultItems());
    }

    private static List<Item> defaultItems() {
        return List.of(
                new Item(1L, "Burger", 599L, "Tasty", "https://cdn.auth0.com/blog/whatabyte/burger-sm.png"),
                new Item(2L, "Pizza", 299L, "Cheesy", "https://cdn.auth0.com/blog/whatabyte/pizza-sm.png"),
                new Item(3L, "Tea", 199L, "Informative", "https://cdn.auth0.com/blog/whatabyte/tea-sm.png"),
                new Item(4L,"Pasta",399L,"Tasty","https://img3.mashed.com/img/gallery/this-is-why-you-should-finish-cooking-pasta-in-a-pan/l-intro-1605920347.jpg"),
                new Item(5L, "Chesscake", 299L, "Creamy", "https://www.nachhaltigleben.ch/images/stories/Rezepte/Vegan_Cheesecake_645.jpg"),
                new Item(6L, "Hotdog", 499L, "Tasty", "https://www.wiesenhof-kochclub.de/site/assets/files/118182/geflugel-hot-dog-wuerstchen-adobestock_192175218.jpeg"),
                new Item(7L, "Pancake", 399L, "Fluffy", "https://www.einfachbacken.de/sites/einfachbacken.de/files/styles/full_width_tablet_4_3/public/2020-08/american_pancakes.jpg?h=4521fff0&itok=yvzcMTED"),
                new Item(8L, "Muffins", 599L, "Fluffy", "https://www.oetker.de/Recipe/Recipes/oetker.de/de-de/baking/image-thumb__42747__RecipeDetail/muffins-mit-zweierlei-schokostueckchen.jpg")
        );
    }

    public List<Item> findAll() {
        List<Item> list = new ArrayList<>();
        Iterable<Item> items = repository.findAll();
        items.forEach(list::add);
        return list;
    }

    public Optional<Item> find(Long id) {
        return repository.findById(id);
    }

    public Item create(Item item) {
        // To ensure the item ID remains unique,
        // use the current timestamp.
        Item copy = new Item(
                new Date().getTime(),
                item.getName(),
                item.getPrice(),
                item.getDescription(),
                item.getImage()
        );
        return repository.save(copy);
    }

    public Optional<Item> update( Long id, Item newItem) {
        // Only update an item if it can be found first.
        return repository.findById(id)
                .map(oldItem -> {
                    Item updated = oldItem.updateWith(newItem);
                    return repository.save(updated);
                });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}