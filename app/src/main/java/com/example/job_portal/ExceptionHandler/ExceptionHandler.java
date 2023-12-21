package package com.example.job_portal.ExceptionHandler;

import android.content.Context;
import android.widget.Toast;

public class ExceptionHandler {

    private static Context context; // Vous devrez définir ce contexte dans votre application

    // Méthode générique pour gérer les exceptions
    public static void handleException(Exception e) {
        // Log de l'exception
        e.printStackTrace();

        // Affichage d'un message d'erreur générique à l'utilisateur
        showToast("Une erreur s'est produite. Veuillez réessayer plus tard.");
    }

    // Méthode pour gérer les exceptions spécifiques
    public static void handleSpecificException(SpecificException e) {
        // Log de l'exception spécifique
        e.printStackTrace();

        // Affichage d'un message d'erreur spécifique à l'utilisateur
        showToast("Une erreur spécifique s'est produite. Veuillez réessayer plus tard.");
    }


    private static void showToast(String message) {
        // Vous pouvez remplacer cela par le mécanisme que vous utilisez pour afficher des messages à l'utilisateur
        // Par exemple, Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
