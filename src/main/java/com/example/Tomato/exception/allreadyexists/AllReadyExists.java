package com.example.Tomato.exception.allreadyexists;

public class AllReadyExists extends RuntimeException{
    public AllReadyExists(String id)
    {
        super("Already exists entity from this data "+id);
    }
}
