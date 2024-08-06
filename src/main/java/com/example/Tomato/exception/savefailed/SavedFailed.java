package com.example.Tomato.exception.savefailed;


public class SavedFailed extends RuntimeException{
    public SavedFailed()
    {
        super("Saving Failed Please Check the fields");
    }
}
