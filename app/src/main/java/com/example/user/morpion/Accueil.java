package com.example.user.morpion;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.util.SparseIntArray;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class Accueil extends ActionBarActivity
{
    int  s_ronds, s_croix;
    SparseIntArray grid;
    boolean turn;//0 si rond 1 si croix
    ImageButton[] collection_boutons=new ImageButton[9];
    TextView text;
    Button bouton1,bouton2;
    boolean win;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        InitAndroid();
        ResetGame();
    }
    void InitAndroid()
    {
        //init zone de texte
        text = (TextView) findViewById(R.id.textView);
        //init score
        s_ronds=s_croix=0;
        //initialisation du tableau de boutons
        collection_boutons[0]=(ImageButton) findViewById((R.id.imageButton));
        collection_boutons[1]=(ImageButton) findViewById((R.id.imageButton2));
        collection_boutons[2]=(ImageButton) findViewById((R.id.imageButton3));
        collection_boutons[3]=(ImageButton) findViewById((R.id.imageButton4));
        collection_boutons[4]=(ImageButton) findViewById((R.id.imageButton5));
        collection_boutons[5]=(ImageButton) findViewById((R.id.imageButton6));
        collection_boutons[6]=(ImageButton) findViewById((R.id.imageButton7));
        collection_boutons[7]=(ImageButton) findViewById((R.id.imageButton8));
        collection_boutons[8]=(ImageButton) findViewById((R.id.imageButton9));

        //initialisation du tableau représentatif de la partie
        grid=new SparseIntArray();
        for (ImageButton ib:collection_boutons)
        {
            grid.put(ib.getId(),0);
        }
        for (final ImageButton button:collection_boutons)
        {
            button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                      public void onClick(View arg0) {
                                            if (grid.get(arg0.getId())!=0|win){return;}
                                            grid.put(arg0.getId(),(turn?1:0)+1);
                                            if(turn)
                                            {
                                                button.setImageResource(R.mipmap.ic_croix);
                                            }
                                            else
                                            {
                                                button.setImageResource(R.mipmap.ic_rond);
                                            }
                                            CheckWin();
                                            turn=!turn;
                                        }
                                  });
        }
        bouton1 = (Button)findViewById(R.id.button);
        bouton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (ImageButton ib:collection_boutons
                     ) {
                    ib.setImageResource(R.mipmap.ic_blanc);
                    grid.put(ib.getId(),0);
                }
                turn=win=false;
                text.setText(getString(R.string.text_score,s_ronds,s_croix));
            }
        });
        bouton2 = (Button)findViewById(R.id.button2);
        bouton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this, About.class);
                startActivity(intent);
            }
        });
    }
    void CheckWin()
    {
        int totest=(turn?1:0)+1, comp;

        win=false;
        //test horizontal
        for(comp=0;comp<7;comp+=3)
        {
            if (    (grid.get(collection_boutons[comp].getId())==totest) &
                    (grid.get(collection_boutons[1+comp].getId())==totest) &
                    (grid.get(collection_boutons[2+comp].getId())==totest)
                    )
            {
                //Gagné
                win=true;
            }
        }
        //test vertical
        for(comp=0;comp<3;comp++)
        {
            if (    (grid.get(collection_boutons[comp].getId())==totest) &
                    (grid.get(collection_boutons[3+comp].getId())==totest) &
                    (grid.get(collection_boutons[6+comp].getId())==totest)
                    )
            {
                //Gagné
                win=true;
            }
        }
        //test diagonal
        if (    (grid.get(collection_boutons[0].getId())==totest) &
                (grid.get(collection_boutons[4].getId())==totest) &
                (grid.get(collection_boutons[8].getId())==totest)
                )
        {
            //Gagné
            win=true;
        }
        if (    (grid.get(collection_boutons[2].getId())==totest) &
                (grid.get(collection_boutons[4].getId())==totest) &
                (grid.get(collection_boutons[6].getId())==totest)
                )
        {
            //Gagné
            win=true;
        }
        if(win)
        {
            text.setText(R.string.text_win);
            if (turn){
                s_croix+=1;
            }
            else{
                s_ronds+=1;
            }
        }
    }
    void ResetGame()
    {
        turn=false;
    }
}
