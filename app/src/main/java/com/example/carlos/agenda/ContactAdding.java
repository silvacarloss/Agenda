package com.example.carlos.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carlos.agenda.models.Contact;

public class ContactAdding extends AppCompatActivity {

    Button btnSave;
    EditText txtName;
    EditText txtPhone;
    boolean isEdit = false;
    Bundle receivedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_adding);
        txtName = (EditText) findViewById(R.id.txtName);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        btnSave = (Button) findViewById(R.id.btnSave);

        receivedData = getIntent().getExtras();

        if(receivedData != null){
            txtName.setText(receivedData.getString("name"));
            txtPhone.setText(receivedData.getString("phone_number"));
            isEdit = true;
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });
    }

    private void saveContact() {
        if(txtName.getText().toString().equals("") || txtPhone.getText().toString().equals("")){
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }else{
            String name = txtName.getText().toString();
            String phone = txtPhone.getText().toString();
            ContactController controller = new ContactController();
            if(!isEdit){
                controller.addContact(this, name, phone);
            }else{
                String id = receivedData.getString("contact_id");
                Contact editedContact = new Contact(id, name, phone);
                controller  .edit(ContactAdding.this, editedContact);
                Toast.makeText(this, "Contato editado com sucesso.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
