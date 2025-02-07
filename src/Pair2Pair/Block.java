package Pair2Pair;

import java.util.ArrayList;

public class Block {

    private String mot;
    private int hash;
    private int AuteurID;
    private int nonce;
    public int predhash = 0;

    ArrayList<Lettre> lettres;

    public Block(String mot, ArrayList<Lettre> lettres, int AuteurID, int nonce) {
        this.mot = mot;
        this.lettres = new ArrayList<>(lettres);
        this.AuteurID = AuteurID;
        this.nonce = nonce;
        hash();
    }

    /**
     * @return the hash
     */
    public int getHash() {
        return hash;
    }

    /**
     * @return the lettres
     */
    public ArrayList<Lettre> getLettres() {
        return lettres;
    }

    /**
     * @return the mot
     */
    public String getMot() {
        return mot;
    }

    /**
     * @return the auteutID
     */
    public int getAuteurID() {
        return AuteurID;
    }

    /**
     * @return the nonce
     */
    public int getNonce() {
        return nonce;
    }

    public void hash() {
        try {

            int nhash = Sha.Sha256(new String(mot + "" + AuteurID + "" + nonce));
            this.hash = nhash;
        } catch (Exception e) {
            System.out.println("Erreur SHA dans LETTRE");
        }

    }

    public String toString() {
        String sortie = new String();
        sortie += "{";
        sortie += mot;
        // sortie += " ,AuteurID :" + AuteurID ;
        // sortie += " ,nonce :" + nonce ;
        sortie += "}";
        return sortie;
    }

}
