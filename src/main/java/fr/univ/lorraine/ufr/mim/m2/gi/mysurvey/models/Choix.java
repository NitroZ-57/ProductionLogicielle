package fr.univ.lorraine.ufr.mim.m2.gi.mysurvey.models;

/**
 * Enumération représentant les choix possibles pour la disponibilité d'un participant
 * lors d'un sondage.
 */
public enum Choix {

    /**
     * Le participant est disponible pour la date proposée.
     */
    DISPONIBLE,

    /**
     * Le participant est indisponible pour la date proposée.
     */
    INDISPONIBLE,

    /**
     * Le participant n'est pas certain de sa disponibilité (peut-être).
     */
    PEUTETRE
}
