package hacker.l.emergency_help.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import hacker.l.emergency_help.R;

public class LoginActivity extends AppCompatActivity {
    Button id_bt_login;
    TextView signUpText, forgot_password;
    EditText id_et_username, id_et_password;
    CheckBox showCheck;
    LinearLayout layout_singup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id_bt_login = findViewById(R.id.id_bt_login);
        signUpText = findViewById(R.id.signUpText);
        forgot_password = findViewById(R.id.forgot_password);
        id_et_username = findViewById(R.id.id_et_username);
        id_et_password = findViewById(R.id.id_et_password);
        showCheck = findViewById(R.id.show_password);
        layout_singup = findViewById(R.id.layout_singup);
        id_bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFunction();
            }
        });
        layout_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        showCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showCheck.isChecked()) {
                    id_et_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    id_et_password.setSelection(id_et_password.length());
                } else {
                    id_et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    id_et_password.setSelection(id_et_password.length());
                }
            }
        });
    }

    private void loginFunction() {
        String userName = id_et_username.getText().toString();
        String userPass = id_et_password.getText().toString();
        if (userName.length() == 0) {
            id_et_username.setError("Enter user name");
        } else if (userPass.length() == 0) {
            id_et_password.setError("Enter password");
        } else {
            Intent intent = new Intent(LoginActivity.this, DashBoardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
