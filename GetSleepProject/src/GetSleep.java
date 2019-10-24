import javax.swing.*;
import java.text.DecimalFormat;

public class GetSleep {
    public static void main(String[] args) {
        int hourNow;
        do {
            hourNow = Integer.parseInt(JOptionPane.showInputDialog(null, "__:00", "What is the hour now?", JOptionPane.PLAIN_MESSAGE));
            if (hourNow < 1 || hourNow > 12)
                JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 12");
        } while (hourNow < 1 || hourNow > 12);

        int minuteNow;
        do {
            minuteNow = Integer.parseInt(JOptionPane.showInputDialog(null, hourNow + ":__", "How many minutes past the hour?", JOptionPane.PLAIN_MESSAGE));
            if (minuteNow < 0 || minuteNow > 59)
                JOptionPane.showMessageDialog(null, "Please enter a number between 0 and 59");
        } while (minuteNow < 0 || minuteNow > 59);

        String nowAmPm;
        do {
            nowAmPm = JOptionPane.showInputDialog(null, "Is it am or pm right now?");
            if (!(nowAmPm.equals("am") || nowAmPm.equals("pm")))
                JOptionPane.showMessageDialog(null, "Please enter either am or pm");
        } while (!(nowAmPm.equals("am") || nowAmPm.equals("pm")));

        int hourWakeUp;
        do {
            hourWakeUp = Integer.parseInt(JOptionPane.showInputDialog(null, "__:00", "At what hour do you want to wake up?", JOptionPane.PLAIN_MESSAGE));
            if (hourWakeUp < 1 || hourWakeUp > 12)
                JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 12");
        } while (hourWakeUp < 1 || hourWakeUp > 12);

        int minuteWakeUp;
        do {
            minuteWakeUp = Integer.parseInt(JOptionPane.showInputDialog(null, hourWakeUp + ":__", "At how many minutes into the hour do you want to wake up?", JOptionPane.PLAIN_MESSAGE));
            if (minuteWakeUp < 0 || minuteWakeUp > 59)
                JOptionPane.showMessageDialog(null, "Please enter a number between 0 and 59");
        } while (minuteWakeUp < 0 || minuteWakeUp > 59);

        String thenAmPm;
        do {
            thenAmPm = JOptionPane.showInputDialog(null, "Will it be am or pm when you wake up?");
            if (!(thenAmPm.equals("am") || thenAmPm.equals("pm")))
                JOptionPane.showMessageDialog(null, "Please enter either am or pm");
        } while (!(thenAmPm.equals("am") || thenAmPm.equals("pm")));

        //int hourNow = IO.getInt("What hour is it now?");
        //int minuteNow = IO.getInt("How many minutes past the hour?");
        //String nowAmPm = IO.getString("Is it am or pm right now?");
        //int hourWakeUp = IO.getInt("At what hour do you want to wake up?");
        //int minuteWakeUp = IO.getInt("At how many minutes into the hour do you want to wake up?");
        //String thenAmPm = IO.getString("Will it be am or pm when you wake up?");

        int timeNow = hourToMinute(hourNow, minuteNow, nowAmPm);
        int timeUp = hourToMinute(hourWakeUp, minuteWakeUp, thenAmPm);
        int totalSleep = calculateSleep(timeNow, timeUp, nowAmPm, thenAmPm);
        int toCycle = cycle(totalSleep);
        int hoursLeft = backToHour(toCycle);
        int minutesLeft = toCycle - (hoursLeft * 60);
        int numberOfCycles = numberCycles(totalSleep);
        output(hoursLeft, minutesLeft, numberOfCycles, hourNow, minuteNow, hourWakeUp, minuteWakeUp, nowAmPm, thenAmPm);

    }

    static int hourToMinute(int h, int m, String t) {
        if (t.equals("pm"))
            h += 12;
        int toMinutes = h * 60;
        return toMinutes + m;
    }

    static int calculateSleep(int n, int u, String tn, String tu) {
        if (!tn.equals(tu)) {
            if (tn.equals("am")) {
                return u - n;
            } else {
                int to12 = 1440 - n;
                int from12 = u;
                return to12 + from12;
            }
        } else
            return u - n;
    }

    static int cycle(int s) {
        do {
            s -= 90;
        } while (s >= 90);
        return s;
    }

    static int numberCycles(int s) {
        int toCycle = s;
        if (s > 90) {
            if (s % 90 != 0) {
                do {
                    s -= 90;
                } while (s > 90);
                return (toCycle - s) / 90;
            } else
                return s / 90;
        } else if (s == 90)
            return 1;
        else
            return 0;
    }

    static int backToHour(int m) {
        int totalTime = m;
        if (m > 60) {
            do {
                m -= 60;
            } while (m > 60);
            return (totalTime - m) / 60;
        } else
            return 0;
    }

    static void output(int h, int m, int c, int hn, int mn, int hu, int mu, String n, String t) {
        DecimalFormat formatter = new DecimalFormat("00");
        String time = "The time is now " + hn + ":" + formatter.format(mn) + n + " and you want to wake up at " + hu + ":" + formatter.format(mu) + t;
        String cycles = "\nYou will go through " + c + " sleep cycles";
        String cycle = "\nYou will go through 1 sleep cycle";
        if (c > 1 || c == 0) {
            if ((h < 0 && m < 0) || (m < 0))
                System.out.println(time + "\nYou will not complete a sleep cycle");
            else if (h == 1 && m == 1)
                System.out.println(time + "\nThe next sleep cycle will begin in " + h + " hour and " + m + " minute. " + cycles);
            else if (h == 1 && m == 0)
                System.out.println(time + "\nThe next sleep cycle will begin in " + h + " hour" + cycles);
            else if (h == 0 && m == 1)
                System.out.println(time + "\nThe next sleep cycle will begin in " + m + " minute" + cycles);
            else if (h == 0 && m == 0)
                System.out.println(time + "\nThe next sleep cycle will begin now" + cycles);
            else if (h == 1)
                System.out.println(time + "\nThe next sleep cycle will begin in " + h + " hour and " + m + " minutes. " + cycles);
            else if (m == 1)
                System.out.println(time + "\nThe next sleep cycle will begin in " + h + " hours and " + m + " minute. " + cycles);
            else if (h == 0)
                System.out.println(time + "\nThe next sleep cycle will begin in " + m + " minutes. " + cycles);
            else if (m == 0)
                System.out.println(time + "\nThe next sleep cycle will begin in " + h + " hours." + cycles);
            else
                System.out.println(time + "\nThe next sleep cycle will begin in " + h + " hours and " + m + " minutes. " + cycles);
        } else {
            if (h == 1 && m == 1)
                System.out.println(time + "\nThe next sleep cycle will begin in " + h + " hour and " + m + " minute. " + cycle);
            else if (h == 1 && m == 0)
                System.out.println(time + "\nThe next sleep cycle will begin in " + h + " hour" + cycle);
            else if (h == 0 && m == 1)
                System.out.println(time + "\nThe next sleep cycle will begin in " + m + " minute" + cycle);
            else if (h == 0 && m == 0)
                System.out.println(time + "\nThe next sleep cycle will begin now" + cycle);
            else if (h == 1)
                System.out.println(time + "\nThe next sleep cycle will begin in " + h + " hour and " + m + " minutes. " + cycle);
            else if (m == 1)
                System.out.println(time + "\nThe next sleep cycle will begin in " + h + " hours and " + m + " minute. " + cycle);
            else if (h == 0)
                System.out.println(time + "\nThe next sleep cycle will begin in " + m + " minutes. " + cycle);
            else if (m == 0)
                System.out.println(time + "\nThe next sleep cycle will begin in " + h + " hours." + cycle);
            else
                System.out.println(time + "\nThe next sleep cycle will begin in " + h + " hours and " + m + " minutes. " + cycle);
        }
    }
}