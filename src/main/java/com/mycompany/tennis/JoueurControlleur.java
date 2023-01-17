package com.mycompany.tennis;

import com.mycompany.tennis.entity.Joueur;
import com.mycompany.tennis.service.JoueurService;

import java.util.Scanner;

public class JoueurControlleur {

    private JoueurService joueurService;

    public JoueurControlleur(){
        this.joueurService = joueurService;
    }

    public void afficheDetailJoueur(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Quel est l'identifiant du joueur que tu veux afficher ?");
        long idfiant = scanner.nextLong();

        Joueur joueur = joueurService.getJoueur(idfiant);
        System.out.println("Le joueur sélectionné s'appelle ");
    }
}
