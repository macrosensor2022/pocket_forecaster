package model;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents the store that sells the mobiles
 * Each store has its name and the list of mobiles that is been sold.
 */
public class store {

    /**
     * Name of the store
     */
    private String name;

    /**
     * List of mobiles that is available in that store.
     */

    private List<phone>phones;

    /**
     * Construct the store with given name and an empty list of mobiles in it.
     * @param name the name of the store.
     */
    public store(String name) {
        this.name = name;
     //   this.website = website;
        this.phones= new ArrayList<>();
    }


    /**
     * Add a phone to a store inventory.
     *
     * @param phone the phone object to be added.
     */
    public void addPhone(phone phone) {
     phones.add(phone);
    }

    /**
     *
     * @return List of mobiles in that particular store.
     */

    public List<phone> getPhones() {
        return phones;
    }

    /**
     *
     * @return the store name where the mobile is available
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return   a string representation of the name of the store and the number of mobile it has in it.
     */
    public String toString()
    {
        return "Store: "+name+  "| Phones: "+phones.size();
    }

}


/**
 * This class utilizes the getters for getname(), getprice() ,  getphones().
 * Collections such as List<phone> and ArrayList<>() for storage of the multiple mobiles and helps for operations like add,size and iterate.
 * Encapsulation - Fields are private so they cant be directly accessed from outside
 */