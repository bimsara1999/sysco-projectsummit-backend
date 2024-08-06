package com.example.Tomato.exception.deleteprohibited;

public class DeleteProhibited extends RuntimeException{
    public DeleteProhibited ()
    {
        super("Deletion is Prohibited");
    }
}
