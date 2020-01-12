package com.formationandroid.intentslistenerstimers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	
	// Constantes :
	private static final int ID_RETOUR_SAISIE = 123;
	
	// Vues :
	private TextView textViewDecompte = null;
	private Button buttonEnvoyer = null;
	
	// Timer :
	private CountDownTimer countDownTimer = null;
	
	// Décompte :
	private int decompte = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// init :
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// vues :
		textViewDecompte = findViewById(R.id.texte_decompte);
		buttonEnvoyer = findViewById(R.id.bouton_envoyer);
		
		// listeners :
		textViewDecompte.setOnClickListener(this);
		buttonEnvoyer.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view)
	{
		if (view == textViewDecompte)
		{
			// ouverture d'une nouvelle activité avec attente de résultat :
			Intent intent = new Intent(this, SaisieActivity.class);
			startActivityForResult(intent, ID_RETOUR_SAISIE);
		}
		else if (view == buttonEnvoyer)
		{
			// préparation de l'envoi d'un message :
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.main_message_decompte, decompte));
			intent.setType("text/plain");
			
			// vérification et envoi :
			if (intent.resolveActivity(getPackageManager()) != null)
			{
				startActivity(intent);
			}
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// init :
		super.onActivityResult(requestCode, resultCode, data);
		
		// récupération du résultat de l'activité de saisie :
		if (requestCode == ID_RETOUR_SAISIE && resultCode == Activity.RESULT_OK)
		{
			// valeur du décompte, en secondes :
			decompte = data.getIntExtra(SaisieActivity.EXTRA_DECOMPTE, 0);
			
			// si timer en cours, on l'arrête :
			if (countDownTimer != null)
			{
				countDownTimer.cancel();
				countDownTimer = null;
			}
			
			// lancement du timer :
			countDownTimer = new CountDownTimer(decompte * 1000, 100)
			{
				@Override
				public void onTick(long millisUntilFinished)
				{
					String libelleDecompte = ((int) Math.floor(millisUntilFinished / 1000) + 1) + " s";
					textViewDecompte.setText(libelleDecompte);
				}
				
				@Override
				public void onFinish()
				{
					textViewDecompte.setText(R.string.main_libelle_creer_decompte);
				}
			};
			countDownTimer.start();
			
			// affichage du bouton envoyer :
			buttonEnvoyer.setVisibility(View.VISIBLE);
		}
	}
	
}
