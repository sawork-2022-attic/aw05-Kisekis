package com.micropos.carts.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Component
public class Cart implements Serializable {

    private List<Item> items = new ArrayList<>();


    public List<Item> getItems() {
        return items;
    }

    public boolean addItem(Item item) {
        for(Item i : items) {
            if(i.getProduct().getId().equals(item.getProduct().getId())) {
                if(i.getQuantity()+item.getQuantity()<=0) {
                    items.remove(i);
                    return false;
                }
                i.setQuantity(i.getQuantity()+item.getQuantity());
                return true;
            }
        }

        return items.add(item);
    }

    public boolean emptyList() {
        items.clear();
        return true;
    };

    public Item getItem(String id) {
        for(Item i : items) {
            if(i.getProduct().getId().equals(id)) {
                return i;
            }
        }
        return null;
    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            total += items.get(i).getQuantity() * items.get(i).getProduct().getPrice();
        }
        return total;
    }

}
