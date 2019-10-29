package com.example.presensimitratel;

import android.app.Application;
import android.content.Context;

public class GlobalVar extends Application {

    private String someVariable;

    public String getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(String someVariable) {
        this.someVariable = someVariable;
    }
}
