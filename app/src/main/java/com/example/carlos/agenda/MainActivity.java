package com.example.carlos.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carlos.agenda.models.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnAdd;
    Button btnNext;
    Button btnEdit;
    Button btnDelete;
    Button btnPrev;
    int listPosition = 0;
    ArrayList<Contact> contactList;
    TextView lblName;
    TextView lblPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = (Button) findViewById(R.id.btnNewContact);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrev = (Button) findViewById(R.id.btnPrevious);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        addListeners();
    }

    private void addListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newContact();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listPosition > 0) {
                    listPosition--;
                    fillContactData(listPosition);
                }
                else {
                    Toast.makeText(MainActivity.this, "Não há mais contatos para exibir.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listPosition++;
                fillContactData(listPosition);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContact();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editContact();
            }
        });
    }

    private void editContact() {
        Bundle params = new Bundle();
        params.putString("contact_id", contactList.get(listPosition).id);
        params.putString("name", contactList.get(listPosition).name);
        params.putString("phone_number", contactList.get(listPosition).number);

        Intent itNewContact = new Intent(this, ContactAdding.class);
        itNewContact.putExtras(params);
        startActivity(itNewContact);
    }

    private void deleteContact() {
        String contactId = contactList.get(listPosition).id;
        if(!contactId.equals("")){
            ContactController.delete(MainActivity.this, contactId);
            onResume();
        }else{
            Toast.makeText(this, "Todos os contatos já foram deletados", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ContactController controller = new ContactController();
        contactList = controller.listContacts(this);
        if(!contactList.isEmpty()){
            fillContactData(0);
        }else{
            fillContactData(-1);
        }

    }

    private void fillContactData(int position) {
        lblName = findViewById(R.id.txtName);
        lblPhone = findViewById(R.id.txtPhone);
        if(position > -1){
            if(!contactList.isEmpty()){
                try{
                    Contact contact = contactList.get(position);
                    lblName.setText("Nome: " + contact.name);
                    lblPhone.setText("Telefone: " + contact.number);
                }catch (Exception ex){
                    listPosition--;
                    Toast.makeText(this, "Não foi possível resgatar mais contatos", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            lblName.setText("");
            lblPhone.setText("");
            Toast.makeText(this, "Sem contatos para exibir.", Toast.LENGTH_SHORT).show();
        }
    }

    private void newContact() {
        Intent itNewContact = new Intent(this, ContactAdding.class);
        startActivity(itNewContact);
    }
}
