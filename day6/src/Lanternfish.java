public class Lanternfish {
    int daysToGiveABirth;

    Lanternfish(int daysToGiveABirth) {
        this.daysToGiveABirth = daysToGiveABirth;
    }

    Lanternfish() {
        this.daysToGiveABirth = 8;
    }

    public boolean nextDay() {
        if (this.daysToGiveABirth == 0) {
            this.daysToGiveABirth = 6;

            return true;
        }

        this.daysToGiveABirth--;
        return false;
    }
}
