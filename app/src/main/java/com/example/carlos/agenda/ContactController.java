package com.example.carlos.agenda;

import android.content.Context;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.example.carlos.agenda.database.DatabaseHelper;
import com.example.carlos.agenda.models.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactController {

    public boolean addContact(Context context, String name, String phone){
        DatabaseHelper database = new DatabaseHelper(context);
        try{
            database.insertContent(name, phone);
            Toast.makeText(context, "Dados inseridos com sucesso.", Toast.LENGTH_SHORT).show();
            return true;
        }catch (Exception ex){
            Toast.makeText(context, "Não foi possível inserir os dados.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public ArrayList<Contact> listContacts(Context context) {
        DatabaseHelper database = new DatabaseHelper(context);
        ArrayList<Contact> contacts = database.selectAll();
        return contacts;
    }

    public static void delete(Context context, String id) {
        DatabaseHelper database = new DatabaseHelper(context);
        database.delete(id);
    }

    public void edit(Context context, Contact contact){
        DatabaseHelper database = new DatabaseHelper(context);
        database.update(contact.id, contact.name, contact.number);
    }
}
