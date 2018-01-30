package com.example.json.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by usuario on 30/01/18.
 */

public class ContactosGSON {
    @SerializedName("contacts")
    @Expose
    private List<ContactoGSON> contacts = null;

    public List<ContactoGSON> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactoGSON> contacts) {
        this.contacts = contacts;
    }
}
