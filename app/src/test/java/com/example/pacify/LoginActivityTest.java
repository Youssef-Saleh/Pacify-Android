package com.example.pacify;

import android.widget.EditText;
import android.widget.TextView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class LoginActivityTest {
    @Mock
    EditText editTextEmail;
    @Mock
    EditText editTextPassword;
    @Mock
    TextView textViewErrorMsg;
    @InjectMocks
    LoginActivity loginActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loginActivity = new LoginActivity();
        editTextEmail= (EditText) loginActivity.findViewById(R.id.Email_logIn_text);
        editTextPassword = (EditText) loginActivity.findViewById(R.id.password_login_editText);
    }

    @Test
    void testPreconditions() {
        assertNotNull(editTextEmail);
        assertNotNull(editTextPassword);
    }

    @Test
    void testText() {
        editTextEmail.setText("hello");
        editTextPassword.setText("hello123");

        // Check if the EditText is properly set:
        assertEquals("hello", editTextEmail.getText());
        assertEquals("hello123", editTextPassword.getText());
    }

    @Test
    void testOnBackPressed() {
        loginActivity.onBackPressed();
    }
}