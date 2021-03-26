package com.example.shakeball;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;

import static android.content.Context.SENSOR_SERVICE;


public class ball_fragment extends Fragment {
    private Context context;

    static interface NextValue{
        BallAdvice getAdviceText();
        void setBallParameters(ball_fragment fragment);
    }
    private NextValue nextValue;

    @Override
    public void onAttach(@NonNull Context _context) {
        super.onAttach(_context);
        context = _context;
        nextValue = (NextValue) context;
    }

    private boolean animating = false;
    private boolean disappearing = false;
    private SensorManager sensorManager;
    private SensorEventListener acceleration_listener;

    private String advice_text = "initial_text";
    //private int current_picture = R.drawable.question;

    private float x_initial_ball_coordinte;
    private float y_initial_ball_coordinate;

    private int bouncy = 250;
    private int sensibility = 10;
    private int friction;
    private int vibration;

    public void setVibration(int vibration_){
        vibration = vibration_;
    }

    public void setFriction(int friction_){
        friction = friction_;
    }

    public void setSensibility(int sensibility_){
        switch (sensibility_){
            case 0:
                sensibility = 20;
                break;
            case 1:
                sensibility = 10;
                break;
            case 2:
                sensibility = 2;
                break;
            default:
                break;
        }
    }

    public void setBouncy(int bouncy_) {
        bouncy = bouncy_*5;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ball_fragment, container, false);

        FrameLayout sphere = (FrameLayout) view.findViewById(R.id.ball_component);
        x_initial_ball_coordinte = sphere.getX();
        y_initial_ball_coordinate = sphere.getY();

        final TextView advice = (TextView) view.findViewById(R.id.texti);
        final ImageView advice_pic = (ImageView) view.findViewById(R.id.advice_picture);
        final Vibrator vibrator = (Vibrator) context.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        final SpringAnimation spring_anim_y = new SpringAnimation(sphere, DynamicAnimation.TRANSLATION_Y);
        final SpringAnimation spring_anim_x = new SpringAnimation(sphere, DynamicAnimation.TRANSLATION_X);

        final int[] number_of_exes = {0};

        SpringForce spring_force_x = new SpringForce();{
            spring_force_x.setFinalPosition(x_initial_ball_coordinte);
            spring_force_x.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);

            switch (friction){
                case 0:
                    spring_force_x.setStiffness(SpringForce.STIFFNESS_LOW);
                    break;
                case 1:
                    spring_force_x.setStiffness(SpringForce.STIFFNESS_MEDIUM);
                    break;
                case 2:
                    spring_force_x.setStiffness(SpringForce.STIFFNESS_HIGH);
                    break;
                default:
                    break;
            }
            spring_force_x.setStiffness(SpringForce.STIFFNESS_LOW);
            spring_anim_x.setSpring(spring_force_x);
        }
        SpringForce spring_force_y = new SpringForce();{
            spring_force_y.setFinalPosition(y_initial_ball_coordinate);
            spring_force_y.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
            switch (friction){
                case 0:
                    spring_force_y.setStiffness(SpringForce.STIFFNESS_LOW);
                    break;
                case 1:
                    spring_force_y.setStiffness(SpringForce.STIFFNESS_MEDIUM);
                    break;
                case 2:
                    spring_force_y.setStiffness(SpringForce.STIFFNESS_HIGH);
                    break;
                default:
                    break;
            }
            spring_anim_y.setSpring(spring_force_y);
        }
        spring_anim_x.start();

        final Animation disappear = AnimationUtils.loadAnimation(context, R.anim.disappear);
        final Animation appear = AnimationUtils.loadAnimation(context, R.anim.appear);

        spring_anim_x.addEndListener(
                new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation,
                                               boolean canceled,
                                               float value, float velocity) {
                        if (number_of_exes[0] == 1) {
                            setNewAdvice();
                            advice.setText(advice_text);
                            //advice_pic.setImageResource(current_picture);

                            advice.setAlpha(1f);
                            advice_pic.setAlpha(1f);

                            advice.startAnimation(appear);
                            advice_pic.startAnimation(appear);
                            disappearing = false;
                        }

                        number_of_exes[0] -= 1;
                    }
                });

        spring_anim_y.addEndListener(
                new DynamicAnimation.OnAnimationEndListener() {
                    @Override
                    public void onAnimationEnd(DynamicAnimation animation,
                                               boolean canceled,
                                               float value, float velocity) {
                        if (number_of_exes[0] == 1) {

                            setNewAdvice();
                            advice.setText(advice_text);

                            advice.setAlpha(1f);
                            advice.startAnimation(appear);

                            advice_pic.setAlpha(1f);
                            advice_pic.startAnimation(appear);

                            disappearing = false;
                        }

                        number_of_exes[0] -= 1;
                    }
                });


        appear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation)
            {
                animating = true;
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                animating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        disappear.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation)
            {
                disappearing = true;
            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                advice.setAlpha(0f);
                advice_pic.setAlpha(0f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);


        acceleration_listener = new SensorEventListener() {
            float prev_x_acceleration = 0;
            float prev_y_acceleration = 0;
            @Override
            public void onSensorChanged(SensorEvent event) {
                if ((event.values[0] - prev_x_acceleration > sensibility) ||
                        event.values[0] - prev_x_acceleration < -sensibility ||
                        event.values[1] - prev_y_acceleration < -sensibility ||
                        event.values[1] - prev_y_acceleration > sensibility) {
                    if (!animating) {
                        if (Build.VERSION.SDK_INT >= 26) {
                            switch (vibration){
                                case -1:
                                    break;
                                case 0:
                                    vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE));
                                    break;
                                case 1:
                                    vibrator.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE));
                                    break;
                                case 2:
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                        vibrator.vibrate(VibrationEffect.createOneShot(40, VibrationEffect.EFFECT_HEAVY_CLICK));
                                    }

                                    else
                                    {
                                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
                                    }
                                    break;
                                default:
                                    vibrator.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.DEFAULT_AMPLITUDE));
                                    break;
                            }
                        } else {
                            if (vibration != -1)
                            {
                                vibrator.vibrate(20);
                            }
                        }
                        number_of_exes[0] = 2;

                        if (!disappearing) {
                            disappearing = true;
                            advice.startAnimation(disappear);
                            advice_pic.startAnimation(disappear);
                        }
                        spring_anim_x.setStartVelocity((event.values[0] + prev_x_acceleration)*bouncy);
                        spring_anim_x.start();
                        spring_anim_y.setStartVelocity((event.values[1] + prev_y_acceleration)*bouncy);
                        spring_anim_y.start();
                    }
                }

                prev_x_acceleration = event.values[0];
                prev_y_acceleration = event.values[1];
            }



            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }
        };
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        nextValue.setBallParameters(this);

        sensorManager.registerListener(acceleration_listener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        animating = false;

        return_sphere_back();
    }


    @Override
    public void onPause(){
        super.onPause();

        return_sphere_back();
        sensorManager.unregisterListener(acceleration_listener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    private void return_sphere_back(){
        FrameLayout sphere = (FrameLayout) this.getView().findViewById(R.id.ball_component);

        sphere.setX(x_initial_ball_coordinte);
        sphere.setY(y_initial_ball_coordinate);
    }

    private void setNewAdvice()
    {
        advice_text = (String) nextValue.getAdviceText().getText();
    }
}