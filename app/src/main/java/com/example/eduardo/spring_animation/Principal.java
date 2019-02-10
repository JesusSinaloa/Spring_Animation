package com.example.eduardo.spring_animation;

import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.support.animation.SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY;
import static android.support.animation.SpringForce.DAMPING_RATIO_NO_BOUNCY;
import static android.support.animation.SpringForce.STIFFNESS_VERY_LOW;


public class Principal extends AppCompatActivity {
    //POSICION FINAL DE LA ANIMACION
    private static final int POSICION_FINAL_Y = 500;
    //DECLARACION DE WIDGETS
    Button buttonAnimar; //BOTON ANIMAR
    ImageView imgPikachu;//IMAGEN A ANIMAR
    private SpringAnimation animacionResorte;
    private float translacionInicio;

    private SeekBar ReboteBar;
    private SeekBar RigidezBar;

    private TextView ValorRebote;
    private TextView ValorRigidez;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //VINCULACION DE WIDGETS
        imgPikachu = findViewById(R.id.imagePikachu);
        buttonAnimar = findViewById(R.id.buttonAnimar);
        ReboteBar = (SeekBar) findViewById(R.id.SeekBarRebote);
        RigidezBar = (SeekBar) findViewById(R.id.SeekBarRigidez);
        ValorRigidez = (TextView) findViewById(R.id.txtValorRigidez);
        ValorRebote = (TextView) findViewById(R.id.txtValorRebote);

        ReboteBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {//EVENTO PARA LA BARRA DESLIZANTE DE REBOTE
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ValorRebote.setText("Relacion de amortiguación: " + (DAMPING_RATIO_MEDIUM_BOUNCY - (progress/200f)));//COLOCAR EL VALOR DE REBOTE EN LA ETIQUETA
                //DAMPING ... : VALOR FLOTANTE PARA Relación de amortiguamiento para un resorte de rebote medio.
                //proress: Progreso de la barra
                //200f:
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        RigidezBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ValorRigidez.setText("Rigidez: " + (STIFFNESS_VERY_LOW * (progress / 2)));//SE COLOCA LA RIGIDEZ EN LA ETIQUETA
                //STIFFNESS... : Constancia de rigidez para un resorte con rigidez muy baja.

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        translacionInicio = buttonAnimar.getTranslationY();//OBTENER PUNTO DE INICIO
        buttonAnimar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reiniciarAnimacion();
                animacionResorte.start();
            }
        });

    }

    //METODOS
    private void reiniciarAnimacion(){
        animacionResorte = new SpringAnimation(imgPikachu, DynamicAnimation.TRANSLATION_Y, POSICION_FINAL_Y);
        //SE CONTRUYE LA ANIMACION
        //OBJETO DE LA CALSE SPRINGANIMATION Y SE LE PASA A SU CONTRUCTOR:
        //-LA VISTA A ANIMAR(IMAGEN)
        //-EL TIPO DE ANIMACION (TRASLACION EN Y)
        //-POSICION FINAL DE LA ANIMACION (PUEDE SER 0)

        if(RigidezBar.getProgress() == 0){//SI LA RIGIDEZ ES 0 (SIN RESISTENCIA)
            //El valor de rigidez define la resistencia del resorte. Un resorte rígido aplica más fuerza
            // al objeto, lo que significa que habrá menos efecto de rebote
            animacionResorte.getSpring().setStiffness(STIFFNESS_VERY_LOW);//Constancia de rigidez para un resorte con rigidez muy baja.
        }else{
            animacionResorte.getSpring().setStiffness(STIFFNESS_VERY_LOW * (RigidezBar.getProgress()/2));
            //SINO CALCULA EL VALOR DE LA RIGIDEZ QUE SE APLICA
        }

        if(ReboteBar.getProgress() == 0){
            //El valor de amortiguamiento define qué tan dinámica será la animación.
            //Cuanto menor sea el valor de amortiguación, más rebote será. Esto se debe a que la relación de
            // amortiguamiento define la rapidez con que las oscilaciones decaen de un rebote al siguiente
            animacionResorte.getSpring().setDampingRatio(DAMPING_RATIO_NO_BOUNCY);//Relación de amortiguación para un resorte sin rebote.
        }else{
            animacionResorte.getSpring().setDampingRatio(DAMPING_RATIO_MEDIUM_BOUNCY - (ReboteBar.getProgress()/200f));
            //SINO SE CALCULA LA RALCION DE REBOTE
        }
        //LISTENER PARA QUE ESCUCHE Y SEPA CUANDO LA ANIMACION TERMINO Y REGRESE A SU PUNTO DE INICIO
        animacionResorte.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean b, float v, float v1) {
                imgPikachu.setTranslationY(translacionInicio);//REGRESAR AL PUNTO INICIAL
            }
        });
    }

}
