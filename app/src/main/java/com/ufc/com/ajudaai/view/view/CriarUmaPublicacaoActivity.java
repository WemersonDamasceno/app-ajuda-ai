package com.ufc.com.ajudaai.view.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ufc.com.ajudaai.R;

public class CriarUmaPublicacaoActivity extends AppCompatActivity {
    Button imgBtnCriarPub;
    ImageView imgPerfilCriarPub;
    TextView txtNomeCriarPub;
    EditText edMensgCriarPub;
    ImageView imgPDF1Upload,imgPDF2Upload,imgPDF3Upload;
    EditText edTAGCriarPub;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_uma_publicacao);






        //Quando fizer o upload do PDF mudar o icon branco para o PDFCompleto
        //O texto nao poder ter mais que 175 caracteres.




    }
}
