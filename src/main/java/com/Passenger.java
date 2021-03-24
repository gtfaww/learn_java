package com;

public abstract class Passenger {

    abstract void passThroughImmigration();

    public static void main(String[] args) {
        // write your code here
        Passenger pa = new ChinesePassenger();
        Passenger pb = new ForeigrePassenger();
        long current = System.currentTimeMillis();
        for (int i = 1; i <= 2_000_000_000; i++) {
            if (i % 1_000_000_000 == 0){
                long now = System.currentTimeMillis();
                System.out.println(now - current);
                current = now;
            }
            Passenger c = i < 1_000_000_000 ? pa : pb;
            c.passThroughImmigration();
        }

    }
}

class ChinesePassenger extends Passenger {

    @Override
    void passThroughImmigration() {

    }
}

class ForeigrePassenger extends Passenger {

    @Override
    void passThroughImmigration() {

    }
}

