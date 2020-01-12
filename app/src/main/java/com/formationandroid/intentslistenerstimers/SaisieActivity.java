package com.formationandroid.intentslistenerstimers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SaisieActivity extends AppCompatActivity
{
	
	// Constantes :
	public static final String EXTRA_DECOMPTE = "EXTRA_DECOMPTE";
	
	// Vues :
	private EditText editTextDecompte = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// init :
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saisie);
		
		// vues :
		editTextDecompte = findViewById(R.id.saisie_decompte);
	}
	
	/**
	 * Listener clic bouton valider.
	 * @param view Bouton valider
	 */
	public void onClickBoutonValider(View view)
	{
		// valeur du décompte :
		try
		{
			int decompte = Integer.parseInt(editTextDecompte.getText().toString());
			
			// renvoi du décompte à l'activité appelante :
			Intent intent = new Intent();
			intent.putExtra(EXTRA_DECOMPTE, decompte);
			setResult(Activity.RESULT_OK, intent);
			
			// fermeture de cette activité :
			finish();
		}
		catch (Exception e)
		{
			// message d'erreur :
			Toast.makeText(this, R.string.saisie_message_erreur, Toast.LENGTH_LONG).show();
		}
	}
	
}
