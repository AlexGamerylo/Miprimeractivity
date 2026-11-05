package com.example.miprimeractivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout layoutNombre, layoutTelefono, layoutCorreo;
    private TextInputEditText etNombre, etTelefono, etCorreo;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencias a las vistas del formulario
        layoutNombre = findViewById(R.id.layoutNombre);
        layoutTelefono = findViewById(R.id.layoutTelefono);
        layoutCorreo = findViewById(R.id.layoutCorreo);
        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etCorreo = findViewById(R.id.etCorreo);
        tvResultado = findViewById(R.id.tvResultado);
        Button btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(v -> enviarFormulario());
    }

    private void enviarFormulario() {
        String nombre = texto(etNombre);
        String telefono = texto(etTelefono);
        String correo = texto(etCorreo);

        boolean valido = true;

        // Nombre: no vacío
        if (nombre.isEmpty()) {
            layoutNombre.setError(getString(R.string.error_nombre));
            valido = false;
        } else {
            layoutNombre.setError(null);
        }

        // Teléfono: solo dígitos (se permite + al inicio), entre 7 y 15
        if (!telefono.matches("\\+?\\d{7,15}")) {
            layoutTelefono.setError(getString(R.string.error_telefono));
            valido = false;
        } else {
            layoutTelefono.setError(null);
        }

        // Correo: formato válido
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            layoutCorreo.setError(getString(R.string.error_correo));
            valido = false;
        } else {
            layoutCorreo.setError(null);
        }

        if (!valido) {
            tvResultado.setText("");
            return;
        }

        tvResultado.setText(getString(R.string.resultado, nombre, telefono, correo));
        Toast.makeText(this, R.string.msg_enviado, Toast.LENGTH_SHORT).show();
    }

    private String texto(TextInputEditText campo) {
        return campo.getText() == null ? "" : campo.getText().toString().trim();
    }
}
