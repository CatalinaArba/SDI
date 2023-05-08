package com.example.Assignment2.Model;

public class AdoptionPetPriceStatistics {
    Integer suma=0;
    Integer count=0;


    public AdoptionPetPriceStatistics() {
    }


    public Integer getAvg(){
        return suma/count;
    }

    public Integer getSuma() {
        return suma;
    }

    public Integer getCount() {
        return count;
    }

    public void increaseSuma(Integer suma) {
        this.suma+= suma;
    }

    public void increaseCount() {
        this.count +=1;
    }
}
