package example.com.famss10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}

/*    <?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".RegisterActivity">


<ScrollView
        android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent">

<RelativeLayout
            android:layout_width="match_parent"
                    android:layout_height="wrap_content">

<EditText
                android:id="@+id/etName"
                        android:layout_width="323dp"
                        android:layout_height="wrap_content"
                        android:layout_marginStart="8dp"
                        android:layout_marginLeft="8dp"
                        android:layout_marginTop="32dp"
                        android:layout_marginEnd="8dp"
                        android:layout_marginRight="8dp"
                        android:ems="10"
                        android:hint="Nom"
                        android:inputType="textPersonName"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintHorizontal_bias="0.511"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toTopOf="parent" />

<EditText
                android:id="@+id/etUsername"
                        android:layout_width="321dp"
                        android:layout_height="wrap_content"
                        android:layout_marginStart="8dp"
                        android:layout_marginLeft="8dp"
                        android:layout_marginTop="32dp"
                        android:layout_marginEnd="8dp"
                        android:layout_marginRight="8dp"
                        android:ems="10"
                        android:hint="Prénom"
                        android:inputType="textPersonName"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintHorizontal_bias="0.531"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toBottomOf="@+id/etName" />

<EditText
                android:id="@+id/etPassword"
                        android:layout_width="318dp"
                        android:layout_height="wrap_content"
                        android:layout_marginStart="8dp"
                        android:layout_marginLeft="8dp"
                        android:layout_marginTop="32dp"
                        android:layout_marginEnd="8dp"
                        android:layout_marginRight="8dp"
                        android:ems="10"
                        android:hint="Mot de Passe"
                        android:inputType="textPassword"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toBottomOf="@+id/etUsername" />

<EditText
                android:id="@+id/etAge"
                        android:layout_width="318dp"
                        android:layout_height="wrap_content"
                        android:layout_marginStart="8dp"
                        android:layout_marginLeft="8dp"
                        android:layout_marginTop="32dp"
                        android:layout_marginEnd="8dp"
                        android:layout_marginRight="8dp"
                        android:ems="10"
                        android:hint="Age"
                        android:inputType="number"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toBottomOf="@+id/etPassword" />

<Button
                android:id="@+id/bRegister"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginStart="8dp"
                        android:layout_marginLeft="8dp"
                        android:layout_marginTop="32dp"
                        android:layout_marginEnd="8dp"
                        android:layout_marginRight="8dp"
                        android:layout_marginBottom="8dp"
                        android:text="Register"
                        app:layout_constraintBottom_toBottomOf="parent"
                        app:layout_constraintEnd_toEndOf="parent"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toBottomOf="@+id/etAge"
                        app:layout_constraintVertical_bias="0.0" />


</RelativeLayout>
</ScrollView>


</android.support.constraint.ConstraintLayout>
*/